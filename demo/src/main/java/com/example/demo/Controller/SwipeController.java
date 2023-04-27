package com.example.demo.Controller;

import com.example.demo.Model.ApiResponseEntity;
import com.example.demo.Model.TimesheetDetails;
import com.example.demo.Service.SwipeService;
//import com.example.demo.repository.EmployeeJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
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


    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(path ="/out")
    public ResponseEntity<ApiResponseEntity> swipeOut(@RequestParam int employeeId ) {
            ApiResponseEntity response = swipeService.swipeOut(employeeId);
            return ResponseEntity.status(response.getStatus()).body(response);

    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(path ="/in")
    public ResponseEntity<ApiResponseEntity> swipeIn(@RequestParam int employeeId) {


          ApiResponseEntity response =   swipeService.swipeIn(employeeId);
            return ResponseEntity.status(response.getStatus()).body(response);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(path = "/getEmployeeSwipeRecordForDate")

    public ResponseEntity<ApiResponseEntity> getEmployeeSwipeRecordForDate(@RequestParam int employeeId, @RequestParam String date){
        Date sqlDate = Date.valueOf(date);
        ApiResponseEntity response =  swipeService.getEmployeeSwipeRecordForDate(employeeId,sqlDate);
        return  ResponseEntity.status(response.getStatus()).body(response);


    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(path = "/getEmployeesActualWorkingHours")

    public ResponseEntity<ApiResponseEntity> getEmployeeActualWorkingHours(@RequestParam int employeeId,@RequestParam String date){
        Date sqlDate = Date.valueOf(date);
        ApiResponseEntity swipeApiResponseEntity = swipeService.calculateActualWorkingHours(employeeId,sqlDate);
        return ResponseEntity.status(swipeApiResponseEntity.getStatus()).body(swipeApiResponseEntity);




    }
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(path = "/getEmployeesTotalWorkingHours")

    public ResponseEntity<ApiResponseEntity> getEmployeeTotalWorkingHours(@RequestParam int employeeId,@RequestParam String date)  {
        Date sqlDate = Date.valueOf(date);
        ApiResponseEntity response = swipeService.calculateTotalWorkingHours(employeeId,sqlDate);
        return ResponseEntity.status(response.getStatus()).body(response);


    }
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(path = "/getEmployeeOutTime")

    public ResponseEntity<ApiResponseEntity> getEmployeeOutTime(@RequestParam int employeeId,@RequestParam String date) {
        Date sqlDate = Date.valueOf(date);
        ApiResponseEntity response = swipeService.calculateTotalOutTime(employeeId, sqlDate);
        return ResponseEntity.status(response.getStatus()).body(response);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(path="/getRemainingWorkingHours")
    public ResponseEntity<ApiResponseEntity> getEmployeeRemainingWorkingHours(@RequestParam int employeeId){
        ApiResponseEntity response = swipeService.calculateRemainingWorkingHours(employeeId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(path="/getEndOfDay")
    public ResponseEntity<ApiResponseEntity> getEndOfDay(@RequestParam int employeeId){
        ApiResponseEntity response = swipeService.calculateEndOfDay(employeeId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
