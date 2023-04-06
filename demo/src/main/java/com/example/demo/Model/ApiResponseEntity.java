package com.example.demo.Model;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class ApiResponseEntity {
    String message;
    HttpStatus status;
    int statusCode;
    List<String> invalidInput = new ArrayList<>();
    Object responseBody;


    public ApiResponseEntity() {

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

    public List<String> getInvalidInput() {
        return invalidInput;
    }

    public void setInvalidInput(List<String> invalidInput) {
        this.invalidInput = invalidInput;
    }

    public Object getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(Object responseBody) {
        this.responseBody = responseBody;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public ApiResponseEntity(String message, HttpStatus status, int statusCode, List<String> invalidInput, Object responseBody) {
        this.message = message;
        this.status = status;
        this.statusCode = statusCode;
        this.invalidInput = invalidInput;
        this.responseBody = responseBody;
    }
}
