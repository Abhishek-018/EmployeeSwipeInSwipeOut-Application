package com.example.demo.Controller;

import com.example.demo.Model.ApiResponseEntity;
import com.example.demo.Model.Employee;
import com.example.demo.Service.Implementation.LoginServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LoginController {

    private LoginServiceImplementation loginServiceImplementation;

    @Autowired
    public LoginController(LoginServiceImplementation loginServiceImplementation) {
        this.loginServiceImplementation = loginServiceImplementation;
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<ApiResponseEntity> login(@RequestBody Employee employee) {

        ApiResponseEntity response = loginServiceImplementation.Login(employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}


