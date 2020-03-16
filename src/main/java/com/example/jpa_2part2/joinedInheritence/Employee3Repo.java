package com.example.jpa_2part2.joinedInheritence;

import org.springframework.data.repository.CrudRepository;

public interface Employee3Repo extends CrudRepository<EmployeeType3,Long> {
}
