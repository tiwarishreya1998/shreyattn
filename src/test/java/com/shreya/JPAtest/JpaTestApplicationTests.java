package com.shreya.JPAtest;

import com.shreya.JPAtest.Entity.Employee;
import com.shreya.JPAtest.repos.EmployeeRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class JpaTestApplicationTests {

	@Autowired
	EmployeeRepository employeeRepository;
	@Test
	void contextLoads() {
	}
	//Ques-3
	@Test
	public void testCreate_1(){
		Employee employee=new Employee();
		employee.setId(1);
		employee.setName("Shreya");
		employee.setAge(21);
		employee.setLocation("Haridwar");
		employeeRepository.save(employee);
	}
	@Test
	public void testCreate_2(){
		Employee employee=new Employee();
		employee.setId(2);
		employee.setName("Riya");
		employee.setAge(14);
		employee.setLocation("Haridwar");
		employeeRepository.save(employee);
	}

	//@Test
	public void testUpdate()
	{
		Optional<Employee> employee=employeeRepository.findById(1);

		employee.get().setAge(22);
		employeeRepository.save(employee.get());
	}

	@Test
	public void testDelete()
	{
		if(employeeRepository.existsById(2));//repository.delete(1);
		System.out.println("Deleting");
		employeeRepository.deleteById(2);
	}

	@Test
	public void testRead()
	{
		Optional<Employee> employee=employeeRepository.findById(1);
		assertNotNull(employee);
		assertEquals(21,employee.get().getAge());
		System.out.println("The employee is "+employee.get().getName());
	}

	@Test
	public void testCount()
	{
		System.out.println("The count os employee is"+employeeRepository.count());
	}

	@Test
	public void testPagingAndSorting()
	{
		Pageable pageable= PageRequest.of(0,5, Sort.Direction.ASC,"Age");
		employeeRepository.findAll(pageable).forEach(p-> System.out.println());

	}
	@Test
	public void testFindByName()
	{
		List<Employee> employeeList=employeeRepository.findByName("Shreya");
		employeeList.forEach(p-> System.out.println(p.getId()+" "+p.getAge()+" "+p.getName()+" "+p.getLocation()));
	}

	@Test
	public void testFindByNameLike()
	{
		List<Employee>employeeList=employeeRepository.findByNameLike("A%");
		employeeList.forEach(p-> System.out.println(p.getId()+" "+p.getAge()+" "+p.getName()+" "+p.getLocation()));
	}

	@Test
	public void testFindBetweenAge()
	{
		List<Employee>employeeList=employeeRepository.findByAgeBetween(28,32);
		employeeList.forEach(p-> System.out.println(p.getId()+" "+p.getAge()+" "+p.getName()+" "+p.getLocation()));
	}





}
