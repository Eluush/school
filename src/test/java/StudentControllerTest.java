import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Student;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String baseUrl = "/students";

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void testGetStudentById() {

        Student student = new Student("John Doe", 20, null);
        ResponseEntity<Student> createdResponse = restTemplate.postForEntity(baseUrl, student, Student.class);

        Long studentId = createdResponse.getBody().getId();

        ResponseEntity<Student> response = restTemplate.getForEntity(baseUrl + "/" + studentId, Student.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(studentId);
        assertThat(response.getBody().getName()).isEqualTo("John Doe");
    }

    @Test
    public void testCreateStudent() {
        Student student = new Student("Jane Doe", 22, null);
        ResponseEntity<Student> response = restTemplate.postForEntity(baseUrl, student, Student.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Jane Doe");
    }

    @Test
    public void testUpdateStudent() {

        Student student = new Student("Alice", 21, null);
        ResponseEntity<Student> createdResponse = restTemplate.postForEntity(baseUrl, student, Student.class);

        Long studentId = createdResponse.getBody().getId(); // Получаем ID созданного студента


        Student updatedStudent = new Student("Alice Smith", 21, null);
        restTemplate.put(baseUrl + "/" + studentId, updatedStudent);

        ResponseEntity<Student> response = restTemplate.getForEntity(baseUrl + "/" + studentId, Student.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Alice Smith");
    }

    @Test
    public void testDeleteStudent() {

        Student student = new Student("Bob", 23, null);
        ResponseEntity<Student> createdResponse = restTemplate.postForEntity(baseUrl, student, Student.class);

        Long studentId = createdResponse.getBody().getId();

        restTemplate.delete(baseUrl + "/" + studentId);

        ResponseEntity<Student> response = restTemplate.getForEntity(baseUrl + "/" + studentId, Student.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }
}

