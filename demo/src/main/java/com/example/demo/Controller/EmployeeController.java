package com.example.demo.Controller;

import com.example.demo.Model.Employee;
import com.example.demo.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping(path = "/getEmployees")
    public List<Employee> getEmployeeDetails(){
        return employeeService.getEmployeeDetails();
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(path = "/getEmployeeById")
    public Employee getEmployeeById(@RequestParam int employeeId){
        Employee emp = employeeService.getEmployeeById(employeeId);
        return emp;
    }
}
