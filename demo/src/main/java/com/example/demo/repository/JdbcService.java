//package com.example.demo.repository;
//
//import com.example.demo.Model.Employee;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//
//
//
//@Repository
//public class JdbcService implements EmployeeJdbcRepository {
//
//
//    @Autowired
//    JdbcTemplate jdbcTemplate;
//
//
//    @Override
//    public Employee insertEmployee(Employee employee) {
//        String query = "INSERT INTO employee_swipe_details(employee_designation,employee_dob,employee_email," +
//                "employee_name,swipe_in_time) values (?,?,?,?,?)";
//
//        String qry = "insert into employee_swipe_details (employee_designation, employee_dob, employee_email, employee_name, swipe_in_time, employee_id) values (?, ?, ?, ?, ?, ?)";
//        this.jdbcTemplate.update(query,employee.getEmployeeDesignation(),employee.getEmployeeDob(),employee.getEmployeeEmail(),
//                employee.getEmployeeName());
//        return employee;
//    }
//}
