package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class AvatarService {
    private static final Logger logger = LoggerFactory.getLogger(AvatarService.class);

    private final AvatarRepository avatarRepository;
    private final StudentService studentService;
    private final Path rootLocation = Paths.get("avatars");

    @Autowired
    public AvatarService(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
        initStorageDir();
    }

    private void initStorageDir() {
        try {
            Files.createDirectories(rootLocation);
            logger.info("Directory for avatars created at {}", rootLocation);
        } catch (IOException e) {
            logger.error("Could not create upload directory!", e);
            throw new RuntimeException("Could not create upload directory!");
        }
    }

    public Avatar uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        logger.info("Uploading avatar for student ID: {}", studentId);
        Student student = studentService.findStudent(studentId);

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = UUID.randomUUID() + extension;
        Path targetPath = rootLocation.resolve(newFilename);

        Files.copy(file.getInputStream(), targetPath);
        logger.info("Avatar uploaded to {}", targetPath);

        Avatar avatar = new Avatar();
        avatar.setFilePath(targetPath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());
        avatar.setStudent(student);

        student.setAvatar(avatar);
        logger.info("Saving avatar for student ID: {}", studentId);
        return avatarRepository.save(avatar);
    }

    public Avatar getAvatarFromDb(Long id) {
        logger.info("Fetching avatar with ID: {}", id);
        return avatarRepository.findById(id).orElseThrow(() -> {
            logger.warn("Avatar not found with ID: {}", id);
            return new RuntimeException("Avatar not found");
        });
    }

    public byte[] getAvatarFromFile(Long id) throws IOException {
        logger.info("Reading avatar from file with ID: {}", id);
        Avatar avatar = avatarRepository.findById(id).orElseThrow(() -> {
            logger.warn("Avatar not found with ID: {}", id);
            return new RuntimeException("Avatar not found");
        });
        return Files.readAllBytes(Paths.get(avatar.getFilePath()));
    }

    public Page<Avatar> getAvatars(Pageable pageable) {
        logger.info("Fetching avatars with pagination: {}", pageable);
        return avatarRepository.findAll(pageable);
    }
}
