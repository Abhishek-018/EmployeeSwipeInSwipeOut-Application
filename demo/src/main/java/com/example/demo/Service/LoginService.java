package com.example.demo.Service;

import com.example.demo.Model.ApiResponseEntity;
import com.example.demo.Model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {

    @Autowired
    EmployeeRepository employeeRepository;

//    public String Login(Employee employee) {
    public ApiResponseEntity Login(Employee employee) {
        ApiResponseEntity response = new ApiResponseEntity();
        List<Employee> employeeDetails = employeeRepository.findAll();
        String message = "Login Successful";
        boolean matchFound = false;
        String employeeEmail = employee.getEmployeeEmail();

        if (employeeDetails.isEmpty()) {
//            return "Database is Empty";
            response.setMessage("Database is Empty");
            response.setStatus(HttpStatus.NO_CONTENT);
            return response;
        } else {
            for(Employee emp: employeeDetails){
                if (employee.getEmployeeEmail().equals(emp.getEmployeeEmail())) {
                    if (employee.getPassword() != null && employee.getPassword().equals(emp.getPassword())) {
                        matchFound = true;
                        response.setResponseBody(emp);
                        response.setMessage("Login Successful");
                        response.setStatus(HttpStatus.OK);
                        return response;
//                        break;
                    }else{
                        response.setMessage("Incorrect Password");
                        response.setStatus(HttpStatus.UNAUTHORIZED);
//                        return "Incorrect Password";
                        return response;
                    }
                }
//                else {
//                    matchFound = false;
//                    return "Employee not registered with this email";
//                }
            }
        }
        if (matchFound) {
            return response;
        } else {
//            return "Incorrect Password";
            response.setMessage("Could not find account with this email.");
            response.setStatus(HttpStatus.NOT_FOUND);
            return  response;
//            return "Could not find account with this email.";
        }


    }

}

