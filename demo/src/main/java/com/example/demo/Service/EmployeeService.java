package com.example.demo.Service;

import com.example.demo.Exceptions.EmployeeEmailExistException;
import com.example.demo.Exceptions.PasswordMismatchException;
import com.example.demo.Model.ApiResponseEntity;
import com.example.demo.Model.Employee;


import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

//    @Autowired
//    EmployeeJdbcRepository employeeJdbcRepository;

    @Autowired
    SwipeRepository swipeRepository;

    public List<Employee> getEmployeeDetails() {
        return employeeRepository.findAll();
    }

//    public Employee addEmployeeJdbc(Employee employee) {
//        //employee.getSwipeInTime();
//        System.out.println("From addEmployeeJdbc(): " + employee.toString());
//        return employeeJdbcRepository.insertEmployee(employee);
//    }




}
