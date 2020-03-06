package com.example.demo3.Ques2;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeManagement employeeManagement;

    //retrieve all employee
    @GetMapping(path = "/employees")
    public List<Employee> retrieveAll(){
        return employeeManagement.getAll();
    }


    //retrieve one employee
    @GetMapping(path = "/employees/path-variable/{id}")
    public Employee retrieveOne( @PathVariable Integer id){
        Employee employee= employeeManagement.findOne(id);
         if(employee==null)//for handling 404 not found exception
            throw new EmployeeNotFoundException("id-"+id);
        return employee;
    }




    //delete one employee
    @DeleteMapping(path = "/employees/path-variable/{id}")
    public void  deleteOne(@PathVariable Integer id){
        Employee employee= employeeManagement.deleteEmp(id);

        if(employee==null)                                  //for handling 404 not found exception
            throw new EmployeeNotFoundException("id-"+id);
    }



    //save one employee
    @PostMapping(path = "/employees")
    public ResponseEntity<Object> saveEmp(@Valid @RequestBody Employee employee) {
        Employee saveEmployee1 = employeeManagement.addEmployee(employee);

        //201 created status will come
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(saveEmployee1).toUri();
        return ResponseEntity.created(location).build();

    }

    //put mapping
    @PutMapping("/employees/{id}")
    public ResponseEntity<Object> putEmp(@PathVariable Integer id,@RequestBody Employee employee) {
        Employee saveEmployee1 = employeeManagement.findId(id);
        saveEmployee1.setName(employee.getName());
        saveEmployee1.setAge(employee.getAge());
        Employee employees=employeeManagement.addEmployee(saveEmployee1);

        //201 created status will come
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(saveEmployee1).toUri();
        return ResponseEntity.created(location).build();

    }


}
