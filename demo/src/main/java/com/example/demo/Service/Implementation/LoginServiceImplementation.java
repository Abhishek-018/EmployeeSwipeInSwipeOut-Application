package com.example.demo.Service.Implementation;

import com.example.demo.Model.ApiResponseEntity;
import com.example.demo.Model.Employee;
import com.example.demo.Service.LoginService;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceImplementation implements LoginService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public ApiResponseEntity Login(Employee employee) {
        ApiResponseEntity response = new ApiResponseEntity();
        List<Employee> employeeDetails = employeeRepository.findAll();
        boolean matchFound = false;


        if (employeeDetails.isEmpty()) {
//            return "Database is Empty";

            response.setMessage("Database is Empty");
            response.setStatus(HttpStatus.NO_CONTENT);
            return response;
        } else {
            for (Employee emp : employeeDetails) {
                if (employee.getEmployeeEmail().equals(emp.getEmployeeEmail())) {
                    if (employee.getPassword() != null && employee.getPassword().equals(emp.getPassword())) {
                        matchFound = true;
                        response.setResponseBody(emp);
                        response.setMessage("Login Successful");
                        response.setStatus(HttpStatus.OK);
                        response.setStatusCode(HttpStatus.OK.value());
                        //return response;
                        break;
                    } else {
                        response.setMessage("Incorrect Password");
                        response.setStatus(HttpStatus.UNAUTHORIZED);
                        response.setStatusCode(HttpStatus.UNAUTHORIZED.value());

                        return response;
                    }
                }

            }
        }
        if (matchFound) {
            return response;
        } else {

            response.setMessage("Could not find account with this email.");
            response.setStatus(HttpStatus.NOT_FOUND);
            return response;

        }


    }

}

