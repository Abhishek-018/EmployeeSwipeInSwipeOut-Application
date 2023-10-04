package com.example.demo.Service.Implementation;

import com.example.demo.Model.*;
import com.example.demo.Model.ApiResponseEntity;
import com.example.demo.Service.SwipeService;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.SwipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import java.time.format.DateTimeFormatter;


import java.util.ArrayList;
import java.util.List;


@Service
public class SwipeServiceImplementation implements SwipeService {

    @Autowired
    SwipeRepository swipeRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public ApiResponseEntity swipeIn(int employeeId) {
        ApiResponseEntity response = new ApiResponseEntity();
        Employee emp = employeeRepository.findByEmployeeId(employeeId);
        java.sql.Date newDate = new java.sql.Date(new java.util.Date().getTime());

        Swipe swipeRecord = swipeRepository.findFirstByEmployeeOrderByDateDesc(emp);
        Date lastSwipeDate = swipeRecord.getDate();
        Swipe lastSwipe = swipeRepository.findFirstByEmployeeAndDateOrderBySwipeInTimeDesc(emp, lastSwipeDate);


        List<Swipe> getAllSwipe = swipeRepository.findAll();

        // Check if database isEmpty
        if (getAllSwipe.isEmpty()) {
            Swipe newSwipe = new Swipe();
            newSwipe.setEmployee(emp);

            newSwipe.setDate(newDate);
            LocalTime localTime = LocalTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            String st1 = localTime.format(dateTimeFormatter);
            LocalTime lt
                    = LocalTime
                    .parse(st1, dateTimeFormatter);

            newSwipe.setSwipeInTime(lt);
            swipeRepository.save(newSwipe);

            response.setMessage("Employee Swiped-in Successfully");
            response.setStatus(HttpStatus.OK);
            response.setStatusCode(HttpStatus.OK.value());
            response.setResponseBody(emp);
            return response;
        } else {
            //Inserting FirstSwipeIn record of an Employee for the day
            if (lastSwipe == null) {

                Swipe newSwipe = new Swipe();
                newSwipe.setEmployee(emp);

                //Date newDate = new Date(new java.util.Date().getTime());
                newSwipe.setDate(newDate);
                LocalTime localTime = LocalTime.now();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
                String st1 = localTime.format(dateTimeFormatter);
                LocalTime lt
                        = LocalTime
                        .parse(st1, dateTimeFormatter);

                newSwipe.setSwipeInTime(lt);
                swipeRepository.save(newSwipe);

                response.setMessage("Employee Swiped-in Successfully");
                response.setStatus(HttpStatus.OK);
                response.setStatusCode(HttpStatus.OK.value());
                response.setResponseBody(emp);
                return response;


            } else if (lastSwipe.getSwipeOutTime() != null) {

                Swipe newSwipe = new Swipe();
                newSwipe.setEmployee(emp);

                newSwipe.setDate(newDate);
                LocalTime localTime = LocalTime.now();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
                String st1 = localTime.format(dateTimeFormatter);
                LocalTime lt
                        = LocalTime
                        .parse(st1, dateTimeFormatter);

                newSwipe.setSwipeInTime(lt);
                swipeRepository.save(newSwipe);

                response.setMessage("Employee Swiped-in Successfully");
                response.setStatus(HttpStatus.OK);
                response.setStatusCode(HttpStatus.OK.value());
                response.setResponseBody(emp);
                return response;
            } else {

                return returnUnauthorizedResponse("Cannot swipe-in. Employee already swiped in and has not swiped out.",emp);
            }

        }
    }

    private ApiResponseEntity returnUnauthorizedResponse(String message,Employee emp){
        ApiResponseEntity response  =new ApiResponseEntity();
        response.setMessage(message);
        response.setStatus(HttpStatus.UNAUTHORIZED);
        response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        response.setResponseBody(emp);
        return response;
    }


    @Override
    public ApiResponseEntity getEmployeeSwipeRecordForDate(int employeeId, Date date) {

        ApiResponseEntity response = new ApiResponseEntity();

        Employee emp = employeeRepository.findByEmployeeId(employeeId);

        List<Swipe> swipes = swipeRepository.findByEmployeeAndDate(emp, date);

        if (swipes.isEmpty()) {

            response.setMessage("No Data Found For Employee And Date");
            response.setStatus(HttpStatus.NO_CONTENT);
            response.setStatusCode(HttpStatus.NO_CONTENT.value());
            return response;

        } else {
            response.setMessage("Swipe Log: ");
            response.setStatus(HttpStatus.OK);
            response.setStatusCode(HttpStatus.OK.value());
            response.setResponseBody(swipes);
            return response;
        }

    }


    @Override
    public ApiResponseEntity swipeOut(int employeeId) {
        ApiResponseEntity response = new ApiResponseEntity();
        System.out.println("employee id: "+ employeeId);
        Employee emp = employeeRepository.findByEmployeeId(employeeId);

        Swipe swipeRecord = swipeRepository.findFirstByEmployeeOrderByDateDesc(emp);

        Date lastSwipeDate = swipeRecord.getDate();
        Swipe lastSwipe = swipeRepository.findFirstByEmployeeAndDateOrderBySwipeInTimeDesc(emp, lastSwipeDate);

        // Find the most recent swipe record for this employee
        Date newDate = new Date(new java.util.Date().getTime());

        Swipe todaysSwipeData = swipeRepository.findFirstByEmployeeAndDateOrderBySwipeInTimeDesc(emp, newDate);

        LocalDate databaseDate = lastSwipe.getDate().toLocalDate();
        LocalDate todaysDate = newDate.toLocalDate();
        if (databaseDate.isBefore(todaysDate) && lastSwipe.getSwipeOutTime() == null) {

            lastSwipe.setSwipeOutTime(LocalTime
                    .of(23, 59, 59));
//                swipeRepository.save(lastSwipe);
            Swipe todaysSwipe = new Swipe();
            todaysSwipe.setEmployee(emp);
            todaysSwipe.setDate(newDate);
            LocalTime localTime = LocalTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            String st1 = localTime.format(dateTimeFormatter);
            LocalTime lt
                    = LocalTime
                    .parse(st1, dateTimeFormatter);

            todaysSwipe.setSwipeOutTime(lt);
            todaysSwipe.setSwipeInTime(LocalTime
                    .of(0, 0, 0));
            List<Swipe> allSwipes = new ArrayList<>();
            allSwipes.add(lastSwipe);
            allSwipes.add(todaysSwipe);
            swipeRepository.saveAll(allSwipes);

            response.setMessage("Employee Swiped-in Successfully");
            response.setStatus(HttpStatus.OK);
            response.setStatusCode(HttpStatus.OK.value());
            response.setResponseBody(emp);
            return response;
        }
        if (todaysSwipeData == null) {

            return returnUnauthorizedResponse("Cannot swipe-out. Employee has not swiped in or already swiped out.",emp);


        }


        // Check if there is an open swipe record

        if (todaysSwipeData.getSwipeOutTime() != null) {
            return returnUnauthorizedResponse("Cannot swipe-out. Employee has not swiped in or already swiped out.",emp);

        }

        // Update the last swipe record with swipe-out time

        LocalTime localTime = LocalTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        String st1 = localTime.format(dateTimeFormatter);
        LocalTime lt
                = LocalTime
                .parse(st1, dateTimeFormatter);
        lastSwipe.setSwipeOutTime(lt);

        swipeRepository.save(lastSwipe);

        response.setMessage("Employee Swiped-Out Successfully");
        response.setStatus(HttpStatus.OK);
        response.setStatusCode(HttpStatus.OK.value());
        response.setResponseBody(emp);
        return response;
    }

    private Duration findActualWorkingHours(List<Swipe> swipes) {
        Duration actualWorkingHours = Duration.ZERO;
        for (Swipe swipe : swipes) {
            LocalTime swipeOutTime = swipe.getSwipeOutTime();
            LocalTime swipeInTime = swipe.getSwipeInTime();
            if (swipeOutTime != null) {
                actualWorkingHours = actualWorkingHours.plus(Duration.between(swipeInTime, swipeOutTime));
            }
        }
        return actualWorkingHours;
    }


    private Duration findTotalWorkingHours(int employeeId, Date date) {

        Employee emp = employeeRepository.findByEmployeeId(employeeId);
        Swipe firstSwipeIn = swipeRepository.findFirstByEmployeeAndDateOrderBySwipeInTimeAsc(emp, date);
        Swipe lastSwipeOut = swipeRepository.findFirstByEmployeeAndDateOrderBySwipeOutTimeDesc(emp, date);
        LocalTime firstSwipeInTime = firstSwipeIn.getSwipeInTime();
        LocalTime lastSwipeOutTime = lastSwipeOut.getSwipeOutTime();
        Duration totalWorkingHours = Duration.ZERO;

        totalWorkingHours = Duration.between(firstSwipeInTime, lastSwipeOutTime);
        return totalWorkingHours;

    }


    private Duration findRemainingWorkingHours(int employeeId) {
        Duration actualWorkingHours = Duration.ZERO;
        Duration remainingWorkingHours = Duration.ZERO;

        Employee emp = employeeRepository.findByEmployeeId(employeeId);
        Date todaysDate = new Date(new java.util.Date().getTime());
        List<Swipe> swipes = swipeRepository.findByEmployeeAndDate(emp, todaysDate);
        Swipe firstSwipeInRecord = swipeRepository.findFirstByEmployeeAndDate(emp, todaysDate);
        if (firstSwipeInRecord.getSwipeOutTime() == null) {
            LocalTime localTime = LocalTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            String st1 = localTime.format(dateTimeFormatter);
            LocalTime lt
                    = LocalTime
                    .parse(st1, dateTimeFormatter);
            firstSwipeInRecord.setSwipeOutTime(lt);
            LocalTime temporarySwipeOutTime = firstSwipeInRecord.getSwipeOutTime();
            Duration presentActualWorkingHour = Duration.between(firstSwipeInRecord.getSwipeInTime(), temporarySwipeOutTime);
            remainingWorkingHours = Duration.ofHours(8).minus(presentActualWorkingHour);
            return remainingWorkingHours;


        }
        actualWorkingHours = findActualWorkingHours(swipes);
        remainingWorkingHours = Duration.ofHours(8).minus(actualWorkingHours);
        return remainingWorkingHours;
    }


    @Override
    public ApiResponseEntity calculateActualWorkingHours(int employeeId, Date date) {
        Duration actualWorkingHours = Duration.ZERO;
        ApiResponseEntity response = new ApiResponseEntity();
        TimesheetDetails timesheetDetails = new TimesheetDetails();

        Employee emp = employeeRepository.findByEmployeeId(employeeId);
        List<Swipe> swipes = swipeRepository.findByEmployeeAndDate(emp, date);
        Swipe firstSwipe = swipeRepository.findFirstByEmployeeAndDateOrderBySwipeInTimeAsc(emp, date);


        if (firstSwipe == null) {


            response.setMessage("No Swipe Data Available For Employee And Date");
            response.setStatus(HttpStatus.NO_CONTENT);
            response.setStatusCode(HttpStatus.NO_CONTENT.value());
            return response;
        }

        if (firstSwipe.getSwipeOutTime() == null) {


            response.setMessage("Please Swipe out first");
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return response;
        }

        if (swipes.isEmpty()) {
            response.setMessage("No Swipe Data Available For Employee And Date");
            response.setStatus(HttpStatus.NO_CONTENT);
            response.setStatusCode(HttpStatus.NO_CONTENT.value());
            return response;
        }
        actualWorkingHours = findActualWorkingHours(swipes);


        String result = actualWorkingHours
                .toHoursPart() + ":"
                + actualWorkingHours.toMinutesPart() + ":"
                + actualWorkingHours.toSecondsPart();


        response.setMessage("Actual working hours calculated successfully.");
        response.setStatus(HttpStatus.OK);
        response.setStatusCode(HttpStatus.OK.value());

        Swipe firstSwipeInRecord = swipeRepository.findFirstByEmployeeAndDate(emp, date);
        timesheetDetails.setFirstSwipe(firstSwipeInRecord.getSwipeInTime());
        Swipe lastSwipeOutRecord = swipeRepository.findFirstByEmployeeAndDateOrderBySwipeOutTimeDesc(emp, date);
        timesheetDetails.setLastSwipe(lastSwipeOutRecord.getSwipeOutTime());
        timesheetDetails.setActualWorkingHours(result);


        response.setResponseBody(timesheetDetails);
        return response;


    }


    @Override
    public ApiResponseEntity calculateTotalWorkingHours(int employeeId, Date date) {
        Duration totalWorkingHours = Duration.ZERO;
        ApiResponseEntity response = new ApiResponseEntity();
        TimesheetDetails timesheetDetails = new TimesheetDetails();
        Employee emp = employeeRepository.findByEmployeeId(employeeId);
        List<Swipe> swipes = swipeRepository.findByEmployeeAndDate(emp, date);


        //  Swipes List is Empty check
        if (swipes.isEmpty()) {
            response.setMessage("No Swipe Data Available For Employee And Date");
            response.setStatus(HttpStatus.NO_CONTENT);
            response.setStatusCode(HttpStatus.NO_CONTENT.value());
            return response;
        }

        totalWorkingHours = findTotalWorkingHours(employeeId, date);


        String result =

                totalWorkingHours.toHoursPart() + ":"
                        + totalWorkingHours.toMinutesPart() + ":"
                        + totalWorkingHours.toSecondsPart();

        response.setMessage("Total Working Hours Calculated Successfully");
        response.setStatus(HttpStatus.OK);
        response.setStatusCode(HttpStatus.OK.value());

        Swipe firstSwipeInRecord = swipeRepository.findFirstByEmployeeAndDate(emp, date);
        timesheetDetails.setFirstSwipe(firstSwipeInRecord.getSwipeInTime());
        Swipe lastSwipeOutRecord = swipeRepository.findFirstByEmployeeAndDateOrderBySwipeOutTimeDesc(emp, date);
        timesheetDetails.setLastSwipe(lastSwipeOutRecord.getSwipeOutTime());
        timesheetDetails.setTotalWorkingHours(result);

        response.setResponseBody(timesheetDetails);
        return response;


    }


    @Override
    public ApiResponseEntity calculateTotalOutTime(int employeeId, Date date) {
        ApiResponseEntity response = new ApiResponseEntity();
        TimesheetDetails timesheetDetails = new TimesheetDetails();

        Employee emp = employeeRepository.findByEmployeeId(employeeId);
        Swipe firstSwipeIn = swipeRepository.findFirstByEmployeeAndDateOrderBySwipeInTimeAsc(emp, date);
        Swipe lastSwipeOut = swipeRepository.findFirstByEmployeeAndDateOrderBySwipeOutTimeDesc(emp, date);
        List<Swipe> swipes = swipeRepository.findByEmployeeAndDate(emp, date);

        if (firstSwipeIn == null || lastSwipeOut == null) {
            response.setMessage("No Swipe Data Available For Employee And Date");
            response.setStatus(HttpStatus.NO_CONTENT);
            response.setStatusCode(HttpStatus.NO_CONTENT.value());
            return response;

        }
        if (date == null && swipes.isEmpty()) {
            response.setMessage("No Swipe Data Available For Employee And Date");
            response.setStatus(HttpStatus.NO_CONTENT);
            response.setStatusCode(HttpStatus.NO_CONTENT.value());
            return response;
        }

        LocalTime firstSwipeInTime = firstSwipeIn.getSwipeInTime();
        LocalTime lastSwipeOutTime = lastSwipeOut.getSwipeOutTime();
        Duration totalWorkingHours = Duration.ZERO;
        totalWorkingHours = Duration.between(firstSwipeInTime, lastSwipeOutTime);


        Duration actualWorkingHours = Duration.ZERO;
        Duration totalOutTime = Duration.ZERO;

        actualWorkingHours = findActualWorkingHours(swipes);
        totalOutTime = totalWorkingHours.minus(actualWorkingHours);


        String result =

                totalOutTime.toHoursPart() + ":"
                        + totalOutTime.toMinutesPart() + ":"
                        + totalOutTime.toSecondsPart();

        response.setMessage("Total Time Spent Outside Calculated Successfully");
        response.setStatus(HttpStatus.OK);
        response.setStatusCode(HttpStatus.OK.value());

        Swipe firstSwipeInRecord = swipeRepository.findFirstByEmployeeAndDate(emp, date);
        timesheetDetails.setFirstSwipe(firstSwipeInRecord.getSwipeInTime());
        Swipe lastSwipeOutRecord = swipeRepository.findFirstByEmployeeAndDateOrderBySwipeOutTimeDesc(emp, date);
        timesheetDetails.setLastSwipe(lastSwipeOutRecord.getSwipeOutTime());
        timesheetDetails.setTotalTimeSpentOutside(result);

        response.setResponseBody(timesheetDetails);
        return response;

    }

    @Override
    public ApiResponseEntity calculateRemainingWorkingHours(int employeeId) {

        Duration remainingWorkingHours = Duration.ZERO;

        ApiResponseEntity response = new ApiResponseEntity();
        TimesheetDetails timesheetDetails = new TimesheetDetails();
        Employee emp = employeeRepository.findByEmployeeId(employeeId);
        Date todaysDate = new Date(new java.util.Date().getTime());
        List<Swipe> swipes = swipeRepository.findByEmployeeAndDate(emp, todaysDate);

        if (swipes.isEmpty()) {
            response.setMessage("No Swipe Data Available For Employee And Date");
            response.setStatus(HttpStatus.NO_CONTENT);
            response.setStatusCode(HttpStatus.NO_CONTENT.value());
            return response;
        }


        remainingWorkingHours = findRemainingWorkingHours(employeeId);

        String result =

                remainingWorkingHours.toHoursPart() + ":"
                        + remainingWorkingHours.toMinutesPart() + ":"
                        + remainingWorkingHours.toSecondsPart();

        response.setMessage("Remaining Working Hours Calculated Successfully");
        response.setStatus(HttpStatus.OK);
        response.setStatusCode(HttpStatus.OK.value());

        Swipe firstSwipeInRecord = swipeRepository.findFirstByEmployeeAndDate(emp, todaysDate);
        timesheetDetails.setFirstSwipe(firstSwipeInRecord.getSwipeInTime());
        Swipe lastSwipeOutRecord = swipeRepository.findFirstByEmployeeAndDateOrderBySwipeOutTimeDesc(emp, todaysDate);
        timesheetDetails.setLastSwipe(lastSwipeOutRecord.getSwipeOutTime());
        timesheetDetails.setRemainingWorkingHours(result);

        response.setResponseBody(timesheetDetails);
        return response;


    }

    @Override
    public ApiResponseEntity calculateEndOfDay(int employeeId) {
        Duration remainingWorkingHours = Duration.ZERO;
        Duration endOfDay = Duration.ZERO;
        ApiResponseEntity response = new ApiResponseEntity();
        TimesheetDetails timesheetDetails = new TimesheetDetails();
        Employee emp = employeeRepository.findByEmployeeId(employeeId);
        Date todaysDate = new Date(new java.util.Date().getTime());
        List<Swipe> swipes = swipeRepository.findByEmployeeAndDate(emp, todaysDate);

        if (swipes.isEmpty()) {
            response.setMessage("No Swipe Data Available For Employee And Date");
            response.setStatus(HttpStatus.NO_CONTENT);
            response.setStatusCode(HttpStatus.NO_CONTENT.value());
            return response;
        }

        LocalTime localTime = LocalTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        String st1 = localTime.format(dateTimeFormatter);
        LocalTime lt
                = LocalTime
                .parse(st1, dateTimeFormatter);
        int hours = lt.getHour();
        int minutes = lt.getMinute();
        int seconds = lt.getSecond();
        Duration liveTime = Duration.ofHours(hours).plusMinutes(minutes).plusSeconds(seconds);
        remainingWorkingHours = findRemainingWorkingHours(employeeId);
        endOfDay = liveTime.plus(remainingWorkingHours);

        String result =

                endOfDay.toHoursPart() + ":"
                        + endOfDay.toMinutesPart() + ":"
                        + endOfDay.toSecondsPart();
        timesheetDetails.setEndOfDayTime(result);
        Swipe firstSwipeInRecord = swipeRepository.findFirstByEmployeeAndDate(emp, todaysDate);
        timesheetDetails.setFirstSwipe(firstSwipeInRecord.getSwipeInTime());
        Swipe lastSwipeOutRecord = swipeRepository.findFirstByEmployeeAndDateOrderBySwipeOutTimeDesc(emp, todaysDate);
        timesheetDetails.setLastSwipe(lastSwipeOutRecord.getSwipeOutTime());

        response.setMessage("End of Day Calculated Successfully");
        response.setStatus(HttpStatus.OK);
        response.setStatusCode(HttpStatus.OK.value());
        response.setResponseBody(timesheetDetails);

        return response;


    }


}