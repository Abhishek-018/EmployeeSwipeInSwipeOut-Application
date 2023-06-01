package com.example.demo.Service.Implementation;
import com.example.demo.Model.Employee;
import com.example.demo.Service.EmployeeService;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImplementation implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;





    public List<Employee> getEmployeeDetails() {

        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(int employeeId) {

        Employee emp = employeeRepository.findByEmployeeId(employeeId);
        return  emp;
    }





}
