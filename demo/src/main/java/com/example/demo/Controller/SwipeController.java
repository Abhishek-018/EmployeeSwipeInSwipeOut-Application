package com.example.demo.Controller;

import com.example.demo.Model.Employee;
import com.example.demo.Model.Swipe;
import com.example.demo.Model.SwipeApiResponseEntity;
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


    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(path ="/out")
    public ResponseEntity<SwipeApiResponseEntity> swipeOut(@RequestParam int employeeId ) {
            SwipeApiResponseEntity response = swipeService.swipeOut(employeeId);
            return ResponseEntity.status(response.getStatus()).body(response);

    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(path ="/in")
    public ResponseEntity<SwipeApiResponseEntity> swipeIn(@RequestParam int employeeId) {


          SwipeApiResponseEntity response =   swipeService.swipeIn(employeeId);
            return ResponseEntity.status(response.getStatus()).body(response);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(path = "/getEmployeeSwipeRecordForDate")

    public ResponseEntity<SwipeApiResponseEntity> getEmployeeSwipeRecordForDate(@RequestParam int employeeId, @RequestParam String date){
        Date sqlDate = Date.valueOf(date);
        SwipeApiResponseEntity swipeApiResponseEntity =  swipeService.getEmployeeSwipeRecordForDate(employeeId,sqlDate);
        return  ResponseEntity.status(swipeApiResponseEntity.getStatus()).body(swipeApiResponseEntity);


    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(path = "/getEmployeesActualWorkingHours")

    public ResponseEntity<SwipeApiResponseEntity> getEmployeeActualWorkingHours(@RequestParam int employeeId,@RequestParam String date){
        Date sqlDate = Date.valueOf(date);
        SwipeApiResponseEntity swipeApiResponseEntity = swipeService.calculateActualWorkingHours(employeeId,sqlDate);
        return ResponseEntity.status(swipeApiResponseEntity.getStatus()).body(swipeApiResponseEntity);




    }
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(path = "/getEmployeesTotalWorkingHours")

    public ResponseEntity<SwipeApiResponseEntity> getEmployeeTotalWorkingHours(@RequestParam int employeeId,@RequestParam String date)  {
        Date sqlDate = Date.valueOf(date);
        SwipeApiResponseEntity response = swipeService.calculateTotalWorkingHours(employeeId,sqlDate);
        return ResponseEntity.status(response.getStatus()).body(response);


    }
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(path = "/getEmployeeOutTime")

    public ResponseEntity<SwipeApiResponseEntity> getEmployeeOutTime(@RequestParam int employeeId,@RequestParam String date) {
        Date sqlDate = Date.valueOf(date);
        SwipeApiResponseEntity response = swipeService.totalOutTime(employeeId, sqlDate);
        return ResponseEntity.status(response.getStatus()).body(response);

    }
}
