package com.example.demo.Service;

import com.example.demo.Exceptions.EmployeeEmailExistException;
import com.example.demo.Exceptions.PasswordMismatchException;
import com.example.demo.Model.Employee;


import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.validation.Valid;
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

    public String addEmployee( Employee employee) throws EmployeeEmailExistException, PasswordMismatchException {
        System.out.println("From Service:");

        System.out.println(employee.toString());

        List<Employee> emp = employeeRepository.findAll();
        for (int i = 0;i<emp.size();i++){
            if (emp.get(i).getEmployeeEmail().equals(employee.getEmployeeEmail())){
                throw new EmployeeEmailExistException("Error: Email already exists in database.");
            }
        }

        if (!employee.getPassword().equals(employee.getConfirmPassword())){
              throw new PasswordMismatchException("Error: Password and Confirm Password do not match.");
        }


         employeeRepository.save(employee);
         return "Employee Added Successfully";

    }


}
