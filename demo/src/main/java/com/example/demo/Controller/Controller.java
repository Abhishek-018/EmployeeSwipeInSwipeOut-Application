package com.example.demo.Controller;

import com.example.demo.Exceptions.EmployeeEmailExistException;
import com.example.demo.Exceptions.PasswordMismatchException;
import com.example.demo.Model.ApiResponseEntity;
import com.example.demo.Model.Employee;
import com.example.demo.Model.Swipe;
import com.example.demo.Service.EmployeeService;
import com.example.demo.Service.SwipeService;
//import com.example.demo.repository.EmployeeJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.ObjectError;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
//import java.util.stream.Collectors;

//REpresentative State Transfer
@RestController

public class Controller {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    SwipeService swipeService;




    @GetMapping(path = "/getEmployees")
    public List<Employee> getEmployeeDetails(){
        return employeeService.getEmployeeDetails();
    }

//    @PostMapping(path = "/addJdbc")
//    public Employee addEmployeeJdbc(@RequestBody Employee employee){
//        return employeeService.addEmployeeJdbc(employee);
//    }



    @PostMapping(path ="/out")
    public ResponseEntity<String> swipeOut(@RequestBody Employee employee) {
        try {
            swipeService.swipeOut(employee);
            return ResponseEntity.ok("Swipe out successful.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error swiping out: " + e.getMessage());
        }
    }

    @PostMapping(path ="/in")
    public ResponseEntity<String> swipeIn(@RequestBody Swipe swipe) {

        try {
            swipeService.swipeIn(swipe);
            return ResponseEntity.ok("Swipe in successful.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error swiping in: " + e.getMessage());
        }
    }


    @GetMapping(path = "/getEmployeesByDate")
    //public List<Swipe> getEmployeeByDate(@PathVariable Date date, @PathVariable int id){
    public List<Swipe> getEmployeeByDate(@RequestBody Swipe swipe){

       return swipeService.getAllEmployeesForDate(swipe);
//        Swipe swipeRequest = new Swipe();
//        swipeRequest.setEmployee(swipe.getEmployee());
//        swipeRequest.setDate(swipe.getDate());
//        return swipeService.getAllEmployeesForDate(swipeRequest);

    }


    @GetMapping(path = "/getEmployeesActualWorkingHours")

    public String getEmployeeActualWorkingHours(@RequestBody Swipe swipe){
        try {
            return swipeService.calculateActualWorkingHours(swipe);
        } catch (Exception e) {
            return e.getMessage();
        }


    }

    @GetMapping(path = "/getEmployeesTotalWorkingHours")

    public String getEmployeeTotalWorkingHours(@RequestBody Swipe swipe)  {
        try {
            return swipeService.calculateTotalWorkingHours(swipe);
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    @GetMapping(path = "/getEmployeeOutTime")

    public String getEmployeeOutTime(@RequestBody Swipe swipe){
        try {
            return swipeService.totalOutTime(swipe);
        }
        catch (Exception e){
            return e.getMessage();
        }

    }
}
