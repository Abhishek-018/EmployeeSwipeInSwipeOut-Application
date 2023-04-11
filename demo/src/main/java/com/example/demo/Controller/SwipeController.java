package com.example.demo.Controller;

import com.example.demo.Model.Employee;
import com.example.demo.Model.Swipe;
import com.example.demo.Service.SwipeService;
//import com.example.demo.repository.EmployeeJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
//import java.util.stream.Collectors;

//REpresentative State Transfer
@RestController

public class SwipeController {


    @Autowired
    SwipeService swipeService;






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

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(path = "/getEmployeeSwipeRecordForDate")

    public List<Swipe> getEmployeeSwipeRecordForDate(@RequestParam int employeeId, @RequestParam String date){
        Date sqlDate = Date.valueOf(date);
        return swipeService.getEmployeeSwipeRecordForDate(employeeId,sqlDate);


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
