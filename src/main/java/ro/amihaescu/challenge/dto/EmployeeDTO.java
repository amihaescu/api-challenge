package ro.amihaescu.challenge.dto;

import lombok.Data;
import ro.amihaescu.challenge.model.Employee;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class EmployeeDTO {

    private Long id;
    private String email;
    private String fullName;
    private LocalDate birthday;
    private List<String> hobbies;

}
