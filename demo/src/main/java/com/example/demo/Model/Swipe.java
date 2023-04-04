package com.example.demo.Model;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name="emp_swipe_details")
public class Swipe {

    @Id
    @SequenceGenerator(
            name="swipe_sequence",
            sequenceName = "swipe_sequence",
            allocationSize = 1

    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "swipe_sequence"
    )
    @Column(name="swipe_id")
    private int swipeId;

    @Column(name="swipe_in_time")
    private LocalTime swipeInTime;

    @Column(name="swipe_out_time")
    private LocalTime swipeOutTime;

    @Column(name="date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Swipe() {
    }

    public Swipe(int swipeId, LocalTime swipeInTime, LocalTime swipeOutTime, Date date) {
        this.swipeId = swipeId;
        this.swipeInTime = swipeInTime;
        this.swipeOutTime = swipeOutTime;
        this.date = date;
    }

    public int getSwipeId() {
        return swipeId;
    }

    public void setSwipeId(int swipeId) {
        this.swipeId = swipeId;
    }

    public LocalTime getSwipeInTime() {
//        LocalTime localTime = LocalTime.now();
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
//        String st1 = localTime.format(dateTimeFormatter);
//        LocalTime lt
//                = LocalTime
//                .parse(st1,dateTimeFormatter);
//        this.setSwipeInTime(lt);
        return swipeInTime;
    }

    public void setSwipeInTime(LocalTime swipeInTime) {
        this.swipeInTime = swipeInTime;
    }

    public LocalTime getSwipeOutTime() {
        return swipeOutTime;
    }

    public void setSwipeOutTime(LocalTime swipeOutTime) {
        this.swipeOutTime = swipeOutTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
