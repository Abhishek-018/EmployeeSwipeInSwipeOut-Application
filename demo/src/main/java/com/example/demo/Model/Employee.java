package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

import java.util.List;



@Entity
@Table(name = "employee_details")
public class Employee implements Serializable {
    @Id
    @SequenceGenerator(
            name="employee_sequence",
            sequenceName = "employee_sequence",
            allocationSize = 1

    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "employee_sequence"
    )
    @Column(name = "employee_id")
    private int employeeId;


    @Column(name = "firstname", nullable = false,length = 20)
    @NotNull(message = "FirstName Cannot be null")
    @NotEmpty(message = "FirstName cannot be empty")
    @Size(min = 3, max = 20, message = "First name length should be between 3 to 20")
    private String firstName;

    @Column(name = "lastname", nullable = false)
    @NotNull(message = "LastName Cannot be null")
    @NotEmpty(message = "LastName cannot be empty")
    @Size(min = 3, max = 20, message = "Last name length should be between 3 to 20")
    private String lastName;

    @Column(name="employee_email",nullable = false, unique = true, length = 55)
    @NotNull(message = "Email Cannot be null")
    @NotEmpty(message = "Email cannot be empty")
    @Email(regexp = "[a-zA-Z0-9.-_]{1,}@[a-zA-Z.-]{2,}[.]{1}[a-zA-Z]{2,}",message = "Invalid email address")
    private String employeeEmail;

    @Column(name = "employee_designation")
    @NotNull(message = "Employee Designation Cannot be null")
    @NotEmpty(message = "Employee Designation cannot be empty")
    private String employeeDesignation;

    @Column(name = "employee_dob",nullable = false)
    @NotNull(message = "Date of birth cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate employeeDob;

    @Column(name = "password",nullable = false)
    @NotNull(message = "Password cannot be null")
    @NotEmpty(message = "Password cannot be empty")
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&].{8,}", message = "Invalid Password")
    private String password;

    @Column(name = "confirm_password",nullable = false)
    @NotNull(message = "Confirm password cannot be null")
    @NotEmpty(message = "Confirm Password cannot be empty")
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&].{8,}", message = "Invalid Confirm Password")
    private String confirmPassword;

    @JsonIgnore
    @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL)
    private List<Swipe> swipe;


    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeeDesignation() {
        return employeeDesignation;
    }

    public void setEmployeeDesignation(String employeeDesignation) {
        this.employeeDesignation = employeeDesignation;
    }

    public LocalDate getEmployeeDob() {
        return employeeDob;
    }

    public void setEmployeeDob(LocalDate employeeDob) {
        this.employeeDob = employeeDob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public List<Swipe> getSwipe() {
        return swipe;
    }

    public void setSwipe(List<Swipe> swipe) {
        this.swipe = swipe;
    }


    public Employee() {
    }

    public Employee(int employeeId, String firstName, String lastName, String employeeEmail, String employeeDesignation, LocalDate employeeDob, String password, String confirmPassword, List<Swipe> swipe) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeEmail = employeeEmail;
        this.employeeDesignation = employeeDesignation;
        this.employeeDob = employeeDob;
        this.password = password;
        this.confirmPassword=confirmPassword;
        this.swipe = swipe;
    }


}
