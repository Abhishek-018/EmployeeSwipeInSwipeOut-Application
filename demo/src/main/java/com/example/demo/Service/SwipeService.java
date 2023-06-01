package com.example.demo.Service;

import com.example.demo.Model.ApiResponseEntity;

import java.sql.Date;

public interface SwipeService {
    public ApiResponseEntity swipeIn(int employeeId);

    public ApiResponseEntity getEmployeeSwipeRecordForDate(int employeeId, Date date);

    public ApiResponseEntity swipeOut(int employeeId);

    public ApiResponseEntity calculateActualWorkingHours(int employeeId, Date date);

    public ApiResponseEntity calculateTotalWorkingHours(int employeeId, Date date);

    public ApiResponseEntity calculateTotalOutTime(int employeeId, Date date);

    public ApiResponseEntity calculateRemainingWorkingHours(int employeeId);

    public ApiResponseEntity calculateEndOfDay(int employeeId);
}
