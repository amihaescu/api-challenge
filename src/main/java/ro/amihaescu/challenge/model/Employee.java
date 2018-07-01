package ro.amihaescu.challenge.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Employee {

    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String fullName;
    private Date birthday;
    @ElementCollection
    @CollectionTable(name = "hobbies")
    private List<String> hobbies;

}
