package com.example.Jpa_2;

import com.example.Jpa_2.entities.Employee;
import com.example.Jpa_2.repository.EmployeeRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class Jpa2Application {

	public static void main(String[] args) {
		ApplicationContext applicationContext=SpringApplication.run(Jpa2Application.class, args);

		EmployeeRepository employeeRepository=applicationContext.getBean(EmployeeRepository.class);

		Employee employee=new Employee();
		employee.setId(1);
		employee.setFirstName("Ram");
		employee.setLastName("Sharma");
		employee.setSalary(34234d);
		employee.setAge(23);


		Employee employee1=new Employee();
		employee1.setId(2);
		employee1.setFirstName("shyam");
		employee1.setLastName("Sharma");
		employee1.setSalary(32334d);
		employee1.setAge(24);

		Employee employee2=new Employee();
		employee2.setId(3);
		employee2.setFirstName("vidya");
		employee2.setLastName("verma");
		employee2.setSalary((double) 2574);
		employee2.setAge(18);

		employeeRepository.save(employee);
		employeeRepository.save(employee1);
		employeeRepository.save(employee2);


		//Ques1
		List<Object[]> employeeList = employeeRepository.salaryGreaterThanAvg();
		for (Object[] e: employeeList) {
			System.out.println(e[0]);
		}
		System.out.println(employeeRepository.findAll());


		//ques2
		List<Object[]> objects =employeeRepository.avgSalLess();
		for (Object[] e: objects) {
			employeeRepository.updateSalary(String.valueOf(e[0]),25000d);
		}


		//Ques3
		int minSalary=employeeRepository.selectMinSal();
		employeeRepository.deleteMinSal(minSalary);

		System.out.println(employeeRepository.findAll());

		//Ques4

		Employee employee5 = new Employee();
		employee5.setFirstName("Rita");
		employee5.setLastName("Singh");
		employee5.setAge(29);
		employee5.setSalary(10000d);

		employeeRepository.save(employee5);

		List<Object[]> objects1 = employeeRepository.lastNameSingh();
		for(Object[] o: objects1) {
			System.out.println(o[0]);
			System.out.println(o[1]);
			System.out.println(o[2]);
		}

		//Ques5


		Employee employee6 = new Employee();
		employee6.setFirstName("Tina");
		employee6.setLastName("Mishra");
		employee6.setAge(50);
		employee6.setSalary(10000d);

		employeeRepository.save(employee6);
		employeeRepository.deleteEmpByAgeFactor(45);

	}

}
