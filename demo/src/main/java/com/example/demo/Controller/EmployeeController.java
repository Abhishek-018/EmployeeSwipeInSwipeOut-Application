package com.example.demo.Controller;

import com.example.demo.Model.Employee;
import com.example.demo.Service.Implementation.EmployeeServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {


    private EmployeeServiceImplementation employeeServiceImplementation;

    @Autowired
    public EmployeeController(EmployeeServiceImplementation employeeServiceImplementation) {
        this.employeeServiceImplementation = employeeServiceImplementation;
    }

    @GetMapping(path = "/getEmployees")
    public List<Employee> getEmployeeDetails() {
        return employeeServiceImplementation.getEmployeeDetails();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(path = "/getEmployeeById")
    public Employee getEmployeeById(@RequestParam int employeeId) {
        Employee emp = employeeServiceImplementation.getEmployeeById(employeeId);
        return emp;
    }
}
