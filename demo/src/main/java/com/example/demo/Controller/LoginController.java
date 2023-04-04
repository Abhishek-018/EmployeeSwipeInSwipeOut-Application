package com.example.demo.Controller;

import com.example.demo.Model.ApiResponseEntity;
import com.example.demo.Model.Employee;
import com.example.demo.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LoginController {
    @Autowired
    LoginService loginService;
    @PostMapping("/login")
    public ResponseEntity<ApiResponseEntity> login(@RequestBody Employee employee) {
//        String result = loginService.Login(employee);
        ApiResponseEntity response = loginService.Login(employee);
        HttpStatus status = HttpStatus.OK;

//        if (result.equals("Incorrect Password")) {
//            status = HttpStatus.UNAUTHORIZED;
//        } else if (result.equals("Employee not registered with this email")) {
//            status = HttpStatus.NOT_FOUND;
//        } else if (result.equals("Database is Empty")) {
//            status = HttpStatus.NO_CONTENT;
//        }

        return ResponseEntity.status(response.getStatus()).body(response);
    }
}


