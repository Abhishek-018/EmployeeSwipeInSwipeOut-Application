package com.example.demo.Exceptions;

public class EmployeeEmailExistException extends RuntimeException{
    public EmployeeEmailExistException (String message) {
        super(message);
    }
}
