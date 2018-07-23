package ro.amihaescu.challenge;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ro.amihaescu.challenge.model.Account;
import ro.amihaescu.challenge.model.Employee;
import ro.amihaescu.challenge.repository.AccountRepository;
import ro.amihaescu.challenge.repository.EmployeeRepository;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.Collections;

@SpringBootApplication
@EnableSwagger2
public class Main {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AccountRepository accountRepository;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public CommandLineRunner createMockData(){
        return args -> {
            employeeRepository.save(new Employee(null, "employee1@gmail.com", "John Doe 1", LocalDate.of(1991, 01, 02), Collections.singletonList("Dancing")));
            employeeRepository.save(new Employee(null, "employee2@gmail.com", "John Doe 2", LocalDate.of(1991, 01, 02), Collections.singletonList("Dancing")));
            employeeRepository.save(new Employee(null, "employee3@gmail.com", "John Doe 3", LocalDate.of(1991, 01, 02), Collections.singletonList("Dancing")));
        };
    }

    @Bean
    public CommandLineRunner createMockUsers(){
        return args -> {
            accountRepository.save(new Account("user", "password"));
        };
    }
}
