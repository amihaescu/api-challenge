package ro.amihaescu.challenge.model;

import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;
import ro.amihaescu.challenge.dto.EmployeeDTO;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Employee {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true, length = 100)
    private String email;
    private String fullName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    @ElementCollection
    @CollectionTable(name = "hobbies")
    private List<String> hobbies;

    public static EmployeeDTO toEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setBirthday(employee.getBirthday());
        employeeDTO.setFullName(employee.getFullName());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setHobbies(employee.getHobbies());
        return  employeeDTO;
    }

}
