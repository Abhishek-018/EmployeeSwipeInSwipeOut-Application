package com.example.demo.Model;

import org.springframework.http.HttpStatus;

public class ApiResponseEntity {
    String message;
    HttpStatus status;
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

    public Object getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(Object responseBody) {
        this.responseBody = responseBody;
    }

    public ApiResponseEntity(String message, HttpStatus status, Object responseBody) {
        this.message = message;
        this.status = status;
        this.responseBody = responseBody;
    }

}
