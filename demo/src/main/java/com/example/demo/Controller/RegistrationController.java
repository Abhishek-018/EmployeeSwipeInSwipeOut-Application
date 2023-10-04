package com.example.demo.Controller;

import com.example.demo.Model.ApiResponseEntity;
import com.example.demo.Model.Employee;
import com.example.demo.Service.Implementation.RegistrationServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;


@Controller
public class RegistrationController {


    private RegistrationServiceImplementation registrationServiceImplementation;

    @Autowired
    public RegistrationController(RegistrationServiceImplementation registrationServiceImplementation) {
        this.registrationServiceImplementation = registrationServiceImplementation;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(path = "/add")
    public ResponseEntity<ApiResponseEntity> addEmployee(@Valid @RequestBody Employee employee, BindingResult result) {
        ApiResponseEntity response = registrationServiceImplementation.addEmployee(employee, result);

        return ResponseEntity.status(response.getStatus()).body(response);


    }
}
