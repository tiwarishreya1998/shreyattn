package com.example.Jpa_2.repository;

import com.example.Jpa_2.entities.Employee;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

public interface EmployeeRepository extends CrudRepository<Employee,Double> {

    @Query("select firstName,lastName from Employee where salary>(select avg(salary) from Employee) ORDER BY age ASC,salary DESC")
    List<Object[]>salaryGreaterThanAvg();

    @Query("select salary from Employee where salary<(select avg(salary) from Employee)")
    List<Object[]>avgSalLess();

    @Transactional
    @Modifying
    @Query("update Employee emp set emp.salary=:sal where emp.firstName=:name")
    void updateSalary(@Param("name") String name,@Param("sal") double sal);

    @Query("select min(salary) from Employee")
    int selectMinSal();

    @Transactional
    @Modifying
    @Query("delete from Employee where salary=:minSal")
    void deleteMinSal(@Param("minSal")double minSal);

    //native Queries
    @Query(value = "select empId,empFirstName,empAge from employeeTable where empLastName like '%Singh'",nativeQuery = true)
    List<Object[]> lastNameSingh();

    @Transactional
    @Modifying
    @Query(value = "delete from employeeTable where empAge>:age",nativeQuery = true)
    void deleteEmpByAgeFactor(@Param("age") int age);



}
