package com.example.demo.Service;

import com.example.demo.Model.Employee;

import java.util.List;

public interface EmployeeService {
    public List<Employee> getEmployeeDetails();


    public Employee getEmployeeById(int employeeId);

}
