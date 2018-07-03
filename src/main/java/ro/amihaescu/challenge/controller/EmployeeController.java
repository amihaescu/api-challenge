package ro.amihaescu.challenge.controller;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ro.amihaescu.challenge.model.Employee;

@RepositoryRestResource(collectionResourceRel = "employee", path = "employee")
public interface EmployeeController extends PagingAndSortingRepository<Employee, Long> {
}
