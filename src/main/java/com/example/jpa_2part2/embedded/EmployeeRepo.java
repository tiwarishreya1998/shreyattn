package com.example.jpa_2part2.embedded;

import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepo extends CrudRepository<Employee,Integer> {
}
