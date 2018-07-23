package ro.amihaescu.challenge.controller;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.web.client.ResourceAccessException;
import ro.amihaescu.challenge.Main;
import ro.amihaescu.challenge.dto.EmployeeDTO;
import ro.amihaescu.challenge.model.Employee;
import ro.amihaescu.challenge.repository.EmployeeRepository;


import java.net.HttpRetryException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Main.class)
public class EmployeeControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {
        /**
         * No initialization done, because of command line runner in the Main.class
         */
        employeeRepository.deleteAll();
        employeeRepository.save(new Employee(null, "employee1@gmail.com", "John Doe 1", LocalDate.of(1991, 01, 02), Collections.singletonList("Dancing")));
        employeeRepository.save(new Employee(null, "employee2@gmail.com", "John Doe 2", LocalDate.of(1991, 01, 02), Collections.singletonList("Dancing")));
        employeeRepository.save(new Employee(null, "employee3@gmail.com", "John Doe 3", LocalDate.of(1991, 01, 02), Collections.singletonList("Dancing")));
    }

    @Test
    public void getEmployeesNoAuthentication() throws Exception {
        List<Map<String, Object>> list = restTemplate.getForObject("http://localhost:" + port + "/employee", List.class);
        assertEquals(3, list.size());

        Map<String, Object> responseMap = list.get(0);
        assertEquals("John Doe 1", responseMap.get("fullName"));
        assertEquals("employee1@gmail.com", responseMap.get("email"));
        assertTrue(responseMap.get("hobbies") instanceof List);

        List<String> hobbies = (List<String>) responseMap.get("hobbies");

        assertEquals(1, hobbies.size());
        assertEquals("Dancing", hobbies.get(0));
    }

    @Test
    public void getEmployeeNoAuthentication() throws Exception {
        List<Map<String, Object>> list = restTemplate.getForObject("http://localhost:" + port + "/employee", List.class);
        Long id = Long.valueOf((Integer)list.get(1).get("id"));
        Map<String, Object> responseMap = restTemplate.getForObject("http://localhost:" + port + "/employee/"+id, Map.class);

        assertEquals("John Doe 2", responseMap.get("fullName"));
        assertEquals("employee2@gmail.com", responseMap.get("email"));
        assertTrue(responseMap.get("hobbies") instanceof List);

        List<String> hobbies = (List<String>) responseMap.get("hobbies");

        assertEquals(1, hobbies.size());
        assertEquals("Dancing", hobbies.get(0));
    }

    @Test(expected = ResourceAccessException.class)
    public void createEmployeeNoAuthentication() throws Exception {
        restTemplate.postForEntity("http://localhost:" + port + "employee", null, EmployeeDTO.class);
    }

    @Test
    public void createEmployeeAuthenticated() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO(null, "employee4@gmail.com", "John Doe 4", LocalDate.of(1991, 01, 02), Collections.singletonList("Dancing"));
        HttpHeaders headers = createAuthenticationHeaders();
        HttpEntity<EmployeeDTO> employee = new HttpEntity<>(employeeDTO, headers);

        ResponseEntity<EmployeeDTO> response = restTemplate.postForEntity("http://localhost:" + port + "employee", employee, EmployeeDTO.class);

        assertEquals(response.getStatusCode(), HttpStatus.CREATED);

        EmployeeDTO responseBody = response.getBody();
        assertEquals("John Doe 4", responseBody.getFullName());
    }

    private HttpHeaders createAuthenticationHeaders() {
        String plainCreds = "user:password";
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        return headers;
    }

    @Test
    public void updateEmployeeAuthenticated() {
        List<Map<String, Object>> list = restTemplate.getForObject("http://localhost:" + port + "/employee", List.class);
        Map<String, Object> responseMap = list.get(0);
        Long id = Long.valueOf((Integer)list.get(0).get("id"));

        HttpHeaders headers = createAuthenticationHeaders();
        EmployeeDTO employee = new EmployeeDTO(id, "employee5@gmail.com", "John Doe 4", LocalDate.of(1991, 01, 05), Collections.singletonList("Dancing"));
        HttpEntity<EmployeeDTO> requestEntity = new HttpEntity<>(employee, headers);

        ResponseEntity<EmployeeDTO> responseEntity = restTemplate.exchange("http://localhost:"+port+"employee/"+id, HttpMethod.PUT, requestEntity, EmployeeDTO.class);

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());

        EmployeeDTO employeeDTO = requestEntity.getBody();
        assertEquals("John Doe 4", employeeDTO.getFullName());
        assertEquals(LocalDate.of(1991, 01, 05), employeeDTO.getBirthday());
    }

    @Test(expected = ResourceAccessException.class)
    public void updateEmployeeNoAuthentication(){
        EmployeeDTO employee = new EmployeeDTO(3L, "employee3@gmail.com", "John Doe 4", LocalDate.of(1991, 01, 05), Collections.singletonList("Dancing"));
        HttpEntity<EmployeeDTO> requestEntity = new HttpEntity<>(employee);
        restTemplate.exchange("http://localhost:"+port+"employee/3", HttpMethod.PUT, requestEntity, EmployeeDTO.class);
    }

}

