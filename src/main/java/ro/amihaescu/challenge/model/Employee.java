package ro.amihaescu.challenge.model;

import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;

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

}
