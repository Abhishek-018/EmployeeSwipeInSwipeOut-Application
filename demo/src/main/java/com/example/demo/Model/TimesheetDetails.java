package com.example.demo.Model;

import java.time.LocalTime;

public class TimesheetDetails {

    LocalTime firstSwipe;
    LocalTime lastSwipe;
    String actualWorkingHours;
    String totalWorkingHours;
    String totalTimeSpentOutside;

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

    public String getActualWorkingHours() {
        return actualWorkingHours;
    }

    public void setActualWorkingHours(String actualWorkingHours) {
        this.actualWorkingHours = actualWorkingHours;
    }

    public String getTotalWorkingHours() {
        return totalWorkingHours;
    }

    public void setTotalWorkingHours(String totalWorkingHours) {
        this.totalWorkingHours = totalWorkingHours;
    }

    public String getTotalTimeSpentOutside() {
        return totalTimeSpentOutside;
    }

    public void setTotalTimeSpentOutside(String totalTimeSpentOutside) {
        this.totalTimeSpentOutside = totalTimeSpentOutside;
    }

    public TimesheetDetails( ) {

    }

    public TimesheetDetails(LocalTime firstSwipe, LocalTime lastSwipe, String actualWorkingHours, String totalWorkingHours, String totalTimeSpentOutside) {
        this.firstSwipe = firstSwipe;
        this.lastSwipe = lastSwipe;
        this.actualWorkingHours = actualWorkingHours;
        this.totalWorkingHours = totalWorkingHours;
        this.totalTimeSpentOutside = totalTimeSpentOutside;
    }
}



