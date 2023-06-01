package com.example.demo.Service;

import com.example.demo.Model.ApiResponseEntity;
import com.example.demo.Model.Employee;
import org.springframework.validation.BindingResult;

public interface RegistrationService {
    public ApiResponseEntity addEmployee(Employee employee, BindingResult result);

}
