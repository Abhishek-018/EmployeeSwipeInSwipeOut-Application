package com.example.demo.Model;

import org.springframework.http.HttpStatus;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class SwipeApiResponseEntity {
    String message;
    HttpStatus status;
    int statusCode;
    LocalTime firstSwipe;
    LocalTime lastSwipe;
    Employee employee;
    List<Swipe> responseBody = new ArrayList<>();

    public SwipeApiResponseEntity() {
    }

    public SwipeApiResponseEntity(String message, HttpStatus status, int statusCode, LocalTime firstSwipe, LocalTime lastSwipe, Employee employee, List<Swipe> responseBody) {
        this.message = message;
        this.status = status;
        this.statusCode = statusCode;
        this.firstSwipe = firstSwipe;
        this.lastSwipe = lastSwipe;
        this.employee = employee;
        this.responseBody = responseBody;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public LocalTime getFirstSwipe() {
        return firstSwipe;
    }

    public void setFirstSwipe(LocalTime firstSwipe) {
        this.firstSwipe = firstSwipe;
    }

    public LocalTime getLastSwipe() {
        return lastSwipe;
    }

    public void setLastSwipe(LocalTime lastSwipe) {
        this.lastSwipe = lastSwipe;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Swipe> getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(List<Swipe> responseBody) {
        this.responseBody = responseBody;
    }
}



