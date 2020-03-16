package com.example.jpa_2part2;

import com.example.jpa_2part2.TablePerClass.Employee2Repo;
import com.example.jpa_2part2.TablePerClass.PermanentEmployee2;
import com.example.jpa_2part2.TablePerClass.TemporaryEmployee2;
import com.example.jpa_2part2.embedded.Employee;
import com.example.jpa_2part2.embedded.EmployeeRepo;
import com.example.jpa_2part2.embedded.EmployeeSalary;
import com.example.jpa_2part2.joinedInheritence.Address;
import com.example.jpa_2part2.joinedInheritence.Employee3Repo;
import com.example.jpa_2part2.joinedInheritence.PermanentEmployee3;
import com.example.jpa_2part2.joinedInheritence.TemporaryEmployee3;
import com.example.jpa_2part2.singleTableInheritence.Employee1Repo;
import com.example.jpa_2part2.singleTableInheritence.PermanentEmployee;
import com.example.jpa_2part2.singleTableInheritence.TemporaryEmployee;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Jpa2part2Application {

	public static void main(String[] args) {

		ApplicationContext applicationContext=SpringApplication.run(Jpa2part2Application.class, args);

        //Single inheritence
		Employee1Repo employee1Repo=applicationContext.getBean(Employee1Repo.class);

		employee1Repo.save(new PermanentEmployee("Ram",1000,"project1"));
		employee1Repo.save(new TemporaryEmployee("Raj",1323,"42"));


		//table per class
		Employee2Repo employee2Repo=applicationContext.getBean(Employee2Repo.class);

		employee2Repo.save(new PermanentEmployee2("Rahull",23232,"project2"));
		employee2Repo.save(new TemporaryEmployee2("shyam",4234,"34"));

		//joined
		Employee3Repo employee3Repo=applicationContext.getBean(Employee3Repo.class);

		employee3Repo.save(new PermanentEmployee3("radha",42345,"project3",new Address("haridwar","kankhal")));
		employee3Repo.save(new TemporaryEmployee3("sita",45476,"65",new Address("delhi","cp")));

		//embedded

		EmployeeRepo employeeRepo=applicationContext.getBean(EmployeeRepo.class);
		employeeRepo.save(new Employee("radha","kumar",22,new EmployeeSalary(23200l,224l,2134l,3423l)));

		System.exit(-1);
	}

}
