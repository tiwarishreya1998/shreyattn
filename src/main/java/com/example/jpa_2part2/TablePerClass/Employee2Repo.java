package com.example.jpa_2part2.TablePerClass;

import org.springframework.data.repository.CrudRepository;

public interface Employee2Repo extends CrudRepository<EmployeeType2,Integer> {
}
