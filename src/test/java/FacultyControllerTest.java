import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String baseUrl = "/faculties";

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testCreateFaculty() {
        Faculty faculty = new Faculty("Mathematics", "Math Department");
        ResponseEntity<Faculty> response = restTemplate.postForEntity(baseUrl, faculty, Faculty.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Mathematics");
    }

    @Test
    public void testGetFacultyById() {

        Faculty faculty = new Faculty("Physics", "Physics Department");
        ResponseEntity<Faculty> createdResponse = restTemplate.postForEntity(baseUrl, faculty, Faculty.class);

        Long facultyId = createdResponse.getBody().getId();

        ResponseEntity<Faculty> response = restTemplate.getForEntity(baseUrl + "/" + facultyId, Faculty.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(facultyId);
    }

    @Test
    public void testUpdateFaculty() {

        Faculty faculty = new Faculty("Chemistry", "Chemistry Department");
        ResponseEntity<Faculty> createdResponse = restTemplate.postForEntity(baseUrl, faculty, Faculty.class);

        Long facultyId = createdResponse.getBody().getId();


        Faculty updatedFaculty = new Faculty("Updated Chemistry", "Updated Chemistry Department");
        restTemplate.put(baseUrl + "/" + facultyId, updatedFaculty);

        ResponseEntity<Faculty> response = restTemplate.getForEntity(baseUrl + "/" + facultyId, Faculty.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Updated Chemistry");
    }

    @Test
    public void testDeleteFaculty() {

        Faculty faculty = new Faculty("Biology", "Biology Department");
        ResponseEntity<Faculty> createdResponse = restTemplate.postForEntity(baseUrl, faculty, Faculty.class);

        Long facultyId = createdResponse.getBody().getId();


        restTemplate.delete(baseUrl + "/" + facultyId);

        ResponseEntity<Faculty> response = restTemplate.getForEntity(baseUrl + "/" + facultyId, Faculty.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }
}
