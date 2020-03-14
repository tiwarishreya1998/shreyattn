package com.shreya.JPAtest.repos;

import com.shreya.JPAtest.Entity.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
//Ques1
//public interface EmployeeRepository extends CrudRepository<Employee,Integer>
public  interface EmployeeRepository extends PagingAndSortingRepository<Employee,Integer>

{
    List<Employee> findByName(String name);
    List<Employee> findByAge(int age);
    List<Employee> findByLocation(String location);
    List<Employee> findByNameLike(String name);
    List<Employee> findByAgeBetween(int age1, int age2);
}
