package com.example.demo.Service.Implementation;

import com.example.demo.Model.ApiResponseEntity;
import com.example.demo.Model.Employee;
import com.example.demo.Service.RegistrationService;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegistrationServiceImplementation implements RegistrationService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public ApiResponseEntity addEmployee(Employee employee, BindingResult result) {
        ApiResponseEntity response = new ApiResponseEntity();
        boolean EmployeeEmailExist = false;
        boolean PasswordMisMatch = false;
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            List<String> errorMessages = new ArrayList<>();

            for (ObjectError error : errors) {
                errorMessages.add(error.getDefaultMessage());
                response.setMessage("Error adding employee. Invalid input data.");
                response.setStatus(HttpStatus.BAD_REQUEST);
                response.setStatusCode(HttpStatus.BAD_REQUEST.value());
                response.setInvalidInput(errorMessages);

            }
            return response;

        }

        List<Employee> employees = employeeRepository.findAll();
        for (Employee emp : employees) {
            if (emp.getEmployeeEmail().equals(employee.getEmployeeEmail())) {
                EmployeeEmailExist = true;
                break;
//                throw new EmployeeEmailExistException("Error: Email already exists in database.");
            }

        }

        if (!employee.getPassword().equals(employee.getConfirmPassword())) {
            PasswordMisMatch = true;


        }


        if (EmployeeEmailExist) {
            response.setMessage("Email already exists in database.");
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return response;

        }

        if (PasswordMisMatch) {
            response.setMessage("Password and Confirm Password do not match.");
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return response;
        }


        employeeRepository.save(employee);
        response.setMessage("Registration Successful");
        response.setStatus(HttpStatus.OK);
        response.setStatusCode(HttpStatus.OK.value());
        response.setResponseBody(employee);
        return response;


    }
}
