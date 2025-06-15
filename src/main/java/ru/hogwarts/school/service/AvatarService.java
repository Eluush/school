package ru.hogwarts.school.service;

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

@Service
public class AvatarService {
    private final AvatarRepository avatarRepository;
    private final StudentService studentService;
    private final Path rootLocation = Paths.get("avatars");

    public AvatarService(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
        initStorageDir();
    }

    private void initStorageDir() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory!");
        }
    }

    public Avatar uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        Student student = studentService.findStudentById(studentId);


        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = UUID.randomUUID() + extension;
        Path targetPath = rootLocation.resolve(newFilename);


        Files.copy(file.getInputStream(), targetPath);


        Avatar avatar = new Avatar();
        avatar.setFilePath(targetPath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());
        avatar.setStudent(student);


        student.setAvatar(avatar);
        return avatarRepository.save(avatar);
    }

    public Avatar getAvatarFromDb(Long id) {
        return avatarRepository.findById(id).orElseThrow();
    }

    public byte[] getAvatarFromFile(Long id) throws IOException {
        Avatar avatar = avatarRepository.findById(id).orElseThrow();
        return Files.readAllBytes(Paths.get(avatar.getFilePath()));
    }
}
