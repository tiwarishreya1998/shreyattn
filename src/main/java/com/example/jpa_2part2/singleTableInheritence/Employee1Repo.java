package com.example.jpa_2part2.singleTableInheritence;

import org.springframework.data.repository.CrudRepository;

public interface Employee1Repo extends CrudRepository<EmployeeType,Integer> {
}
