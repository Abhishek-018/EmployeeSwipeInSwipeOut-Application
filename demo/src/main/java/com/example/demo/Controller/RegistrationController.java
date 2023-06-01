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

//        Employee savedEmployee =  employeeService.addEmployee(employee);
//        if(savedEmployee != null) {
//            return ResponseEntity.ok("Employee added successfully");
//        } else {
//            return ResponseEntity.badRequest().body("Unable to add employee");
//        }

//        if (result.hasErrors()) {
//
//            List<String> errors = result.getAllErrors().stream()
//                    .map(ObjectError::getDefaultMessage)
//                    .collect(Collectors.toList());
//            return ResponseEntity.badRequest().body(errors);
//        }
//        Employee savedEmployee = employeeService.addEmployee(employee);
//        return ResponseEntity.ok(savedEmployee);

//        if (result.hasErrors()){
//            List<ObjectError> errors = result.getAllErrors();
//            List<String>errorMessages = new ArrayList<>();
//
//            for (ObjectError error : errors) {
//                errorMessages.add(error.getDefaultMessage());
//                response.setValidationErrors(errorMessages);
//            }
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error adding employee. Invalid input data. " +"\n"+ response.getValidationErrors());
//
//        }
//
//        try {
//            return ResponseEntity.ok().body(registrationService.addEmployee(employee,result));
//
//        } catch (EmployeeEmailExistException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//        catch (PasswordMismatchException e){
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }

        return ResponseEntity.status(response.getStatus()).body(response);


    }
}
