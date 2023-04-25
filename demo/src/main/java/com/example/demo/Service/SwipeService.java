package com.example.demo.Service;

import com.example.demo.Model.*;
import com.example.demo.Model.ApiResponseEntity;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.SwipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalTime;

import java.time.format.DateTimeFormatter;



import java.util.List;


@Service
public class SwipeService {

    @Autowired
    SwipeRepository swipeRepository;

    @Autowired
    EmployeeRepository employeeRepository;


    public ApiResponseEntity swipeIn(int employeeId) {
        ApiResponseEntity response = new ApiResponseEntity();

        boolean SwipeInSuccessful = false;
        Employee emp = employeeRepository.findByEmployeeId(employeeId);
        Date newDate = new Date(new java.util.Date().getTime());
        Swipe lastSwipe = swipeRepository.findFirstByEmployeeAndDateOrderBySwipeInTimeDesc(emp, newDate);
        List<Swipe> getAllSwipe = swipeRepository.findAll();

        // Check if database isEmpty
        if (getAllSwipe.isEmpty()) {
            Swipe newSwipe = new Swipe();
            newSwipe.setEmployee(emp);

//            Date newDate= new Date(new java.util.Date().getTime());
            newSwipe.setDate(newDate);
            LocalTime localTime = LocalTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            String st1 = localTime.format(dateTimeFormatter);
            LocalTime lt
                    = LocalTime
                    .parse(st1, dateTimeFormatter);

            newSwipe.setSwipeInTime(lt);
            swipeRepository.save(newSwipe);

            SwipeInSuccessful = true;
            response.setMessage("Employee Swiped-in Successfully");
            response.setStatus(HttpStatus.OK);
            response.setStatusCode(HttpStatus.OK.value());
            response.setResponseBody(emp);
            //return response;

            System.out.println(newSwipe.getSwipeInTime());
        }
        if (!getAllSwipe.isEmpty()) {
            //Inserting FirstSwipeIn record of an Employee for the day

            if (lastSwipe == null) {
//                LocalTime fourPM = LocalTime.of(16, 00);
//                if (LocalTime.now){
//
//                }
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

                SwipeInSuccessful = true;

                response.setMessage("Employee Swiped-in Successfully");
                response.setStatus(HttpStatus.OK);
                response.setStatusCode(HttpStatus.OK.value());
                response.setResponseBody(emp);
                //return response;

                System.out.println(newSwipe.getSwipeInTime());

            } else if (lastSwipe != null && lastSwipe.getSwipeOutTime() != null) {
//                LocalTime fourPM = LocalTime.of(16, 00);
//                if (LocalTime.now().isAfter(fourPM)){
//
//
//                }
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

                SwipeInSuccessful = true;

                response.setMessage("Employee Swiped-in Successfully");
                response.setStatus(HttpStatus.OK);
                response.setStatusCode(HttpStatus.OK.value());
                response.setResponseBody(emp);
                //return response;

                System.out.println(newSwipe.getSwipeInTime());

            } else {

                response.setMessage("Cannot swipe-in. Employee already swiped in and has not swiped out.");
                response.setStatus(HttpStatus.UNAUTHORIZED);
                response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
                response.setResponseBody(emp);


            }

        }

        if (SwipeInSuccessful) {
            return response;
        }
        return response;
    }


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
//            Swipe firstSwipeInRecord = swipeRepository.findFirstByEmployeeAndDateOrderBySwipeInTimeAsc(emp, date);
//            response.setFirstSwipe(firstSwipeInRecord.getSwipeInTime());
//            Swipe lastSwipeOutRecord = swipeRepository.findFirstByEmployeeAndDateOrderBySwipeOutTimeDesc(emp, date);
//            response.setLastSwipe(lastSwipeOutRecord.getSwipeOutTime());
            response.setResponseBody(swipes);
            return response;
        }

    }


    public ApiResponseEntity swipeOut(int employeeId) {
        ApiResponseEntity response = new ApiResponseEntity();

        // Find the most recent swipe record for this employee

        Employee emp = employeeRepository.findByEmployeeId(employeeId);
        Date newDate = new Date(new java.util.Date().getTime());
        Swipe lastSwipe = swipeRepository.findFirstByEmployeeAndDateOrderBySwipeInTimeDesc(emp, newDate);

        if (lastSwipe == null) {

            response.setMessage("Cannot swipe-out. Employee has not swiped in or already swiped out.");
            response.setStatus(HttpStatus.UNAUTHORIZED);
            response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            response.setResponseBody(emp);
            return response;
        }
        // Check if there is an open swipe record

        if (lastSwipe.getSwipeOutTime() != null) {

            response.setMessage("Cannot swipe-out. Employee has not swiped in or already swiped out.");
            response.setStatus(HttpStatus.UNAUTHORIZED);
            response.setStatusCode(HttpStatus.UNAUTHORIZED.value());

//            Swipe firstSwipeInRecord = swipeRepository.findFirstByEmployeeAndDate(emp, newDate);
//            response.setFirstSwipe(firstSwipeInRecord.getSwipeInTime());
//            Swipe lastSwipeOutRecord = swipeRepository.findFirstByEmployeeAndDateOrderBySwipeOutTimeDesc(emp, newDate);
//            response.setLastSwipe(lastSwipeOutRecord.getSwipeOutTime());
            response.setResponseBody(emp);

            return response;
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

//        Swipe firstSwipeInRecord = swipeRepository.findFirstByEmployeeAndDate(emp, newDate);
//        response.setFirstSwipe(firstSwipeInRecord.getSwipeInTime());
//        Swipe lastSwipeOutRecord = swipeRepository.findFirstByEmployeeAndDateOrderBySwipeOutTimeDesc(emp, newDate);
//        response.setLastSwipe(lastSwipeOutRecord.getSwipeOutTime());

        response.setResponseBody(emp);

        return response;
    }

    private Duration findActualWorkingHours(List<Swipe> swipes) {
        Duration actualWorkingHours = Duration.ZERO;
        for (int i = 0; i < swipes.size(); i++) {
            LocalTime swipeOutTime = swipes.get(i).getSwipeOutTime();
            LocalTime swipeInTime = swipes.get(i).getSwipeInTime();
            if (swipeOutTime != null) {
                actualWorkingHours = actualWorkingHours.plus(Duration.between(swipeInTime, swipeOutTime));
            }
        }
        return actualWorkingHours;
    }


    private Duration findTotalWorkingHours(int employeeId, Date date) {

        Employee emp = employeeRepository.findByEmployeeId(employeeId);
        List<Swipe> swipes = swipeRepository.findByEmployeeAndDate(emp, date);

        Swipe firstSwipeIn = swipeRepository.findFirstByEmployeeAndDateOrderBySwipeInTimeAsc(emp, date);
        Swipe lastSwipeOut = swipeRepository.findFirstByEmployeeAndDateOrderBySwipeOutTimeDesc(emp, date);
        LocalTime firstSwipeInTime = firstSwipeIn.getSwipeInTime();
        LocalTime lastSwipeOutTime = lastSwipeOut.getSwipeOutTime();
        Duration totalWorkingHours = Duration.ZERO;
        LocalTime t;
        totalWorkingHours = Duration.between(firstSwipeInTime, lastSwipeOutTime);
        return totalWorkingHours;

    }


    public ApiResponseEntity calculateActualWorkingHours(int employeeId, Date date) {

        ApiResponseEntity response = new ApiResponseEntity();
        TimesheetDetails timesheetDetails = new TimesheetDetails();

        Employee emp = employeeRepository.findByEmployeeId(employeeId);
        List<Swipe> swipes = swipeRepository.findByEmployeeAndDate(emp, date);
        Swipe firstSwipe = swipeRepository.findFirstByEmployeeAndDateOrderBySwipeInTimeAsc(emp, date);
        Duration actualWorkingHours = Duration.ZERO;

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


    public ApiResponseEntity calculateTotalWorkingHours(int employeeId, Date date) {
        ApiResponseEntity response = new ApiResponseEntity();
        TimesheetDetails timesheetDetails = new TimesheetDetails();
        Employee emp = employeeRepository.findByEmployeeId(employeeId);
        List<Swipe> swipes = swipeRepository.findByEmployeeAndDate(emp, date);


        //  null check ,Do this by exception handling
        if (swipes.isEmpty()) {
            response.setMessage("No Swipe Data Available For Employee And Date");
            response.setStatus(HttpStatus.NO_CONTENT);
            response.setStatusCode(HttpStatus.NO_CONTENT.value());
            return response;
        }


        Swipe firstSwipeIn = swipeRepository.findFirstByEmployeeAndDateOrderBySwipeInTimeAsc(emp, date);
        Swipe lastSwipeOut = swipeRepository.findFirstByEmployeeAndDateOrderBySwipeOutTimeDesc(emp, date);
        LocalTime firstSwipeInTime = firstSwipeIn.getSwipeInTime();
        LocalTime lastSwipeOutTime = lastSwipeOut.getSwipeOutTime();
        Duration totalWorkingHours = Duration.ZERO;
        LocalTime t;
        totalWorkingHours = Duration.between(firstSwipeInTime, lastSwipeOutTime);


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


    public ApiResponseEntity totalOutTime(int employeeId, Date date) {
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

        actualWorkingHours = findActualWorkingHours(swipes);


        Duration totalOutTime = Duration.ZERO;
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

    public ApiResponseEntity remainingWorkingHours(int employeeId, Date date) {
        ApiResponseEntity response = new ApiResponseEntity();
        TimesheetDetails timesheetDetails = new TimesheetDetails();
        Employee emp = employeeRepository.findByEmployeeId(employeeId);
        List<Swipe> swipes = swipeRepository.findByEmployeeAndDate(emp, date);

        if (swipes.isEmpty()) {
            response.setMessage("No Swipe Data Available For Employee And Date");
            response.setStatus(HttpStatus.NO_CONTENT);
            response.setStatusCode(HttpStatus.NO_CONTENT.value());
            return response;
        }
        Duration actualWorkingHours = Duration.ZERO;


        actualWorkingHours = findActualWorkingHours(swipes);
        Duration remainingWorkingHours = Duration.ofHours(8).minus(actualWorkingHours);
        String result =

                remainingWorkingHours.toHoursPart() + ":"
                        + remainingWorkingHours.toMinutesPart() + ":"
                        + remainingWorkingHours.toSecondsPart();

        response.setMessage("Remaining Working Hours Calculated Successfully");
        response.setStatus(HttpStatus.OK);
        response.setStatusCode(HttpStatus.OK.value());

        Swipe firstSwipeInRecord = swipeRepository.findFirstByEmployeeAndDate(emp, date);
        timesheetDetails.setFirstSwipe(firstSwipeInRecord.getSwipeInTime());
        Swipe lastSwipeOutRecord = swipeRepository.findFirstByEmployeeAndDateOrderBySwipeOutTimeDesc(emp, date);
        timesheetDetails.setLastSwipe(lastSwipeOutRecord.getSwipeOutTime());
        timesheetDetails.setRemainingWorkingHours(result);

        response.setResponseBody(timesheetDetails);
        return response;


    }

    public ApiResponseEntity endOfDay(int employeeId, Date date) {
        ApiResponseEntity response = new ApiResponseEntity();
        TimesheetDetails timesheetDetails = new TimesheetDetails();
        Employee emp = employeeRepository.findByEmployeeId(employeeId);
        List<Swipe> swipes = swipeRepository.findByEmployeeAndDate(emp, date);

        if (swipes.isEmpty()) {
            response.setMessage("No Swipe Data Available For Employee And Date");
            response.setStatus(HttpStatus.NO_CONTENT);
            response.setStatusCode(HttpStatus.NO_CONTENT.value());
            return response;
        }

        Duration actualWorkingHours = Duration.ZERO;
        Duration totalWorkingHours = Duration.ZERO;
        Duration totalOutTime = Duration.ZERO;
        Duration endOfDay = Duration.ZERO;


        actualWorkingHours = findActualWorkingHours(swipes);
        totalWorkingHours = findTotalWorkingHours(employeeId, date);
        totalOutTime = totalWorkingHours.minus(actualWorkingHours);
        Swipe firstSwipeInRecord = swipeRepository.findFirstByEmployeeAndDate(emp, date);
        int hours = firstSwipeInRecord.getSwipeInTime().getHour();
        int minutes = firstSwipeInRecord.getSwipeInTime().getMinute();
        int seconds = firstSwipeInRecord.getSwipeInTime().getSecond();
        Duration firstSwipeInTime = Duration.ofHours(hours).plusMinutes(minutes).plusSeconds(seconds);
        endOfDay = Duration.ofHours(8).plus(firstSwipeInTime).plus(totalOutTime);

        String result =

                endOfDay.toHoursPart() + ":"
                        + endOfDay.toMinutesPart() + ":"
                        + endOfDay.toSecondsPart();


        response.setMessage("End of Day Calculated Successfully");
        response.setStatus(HttpStatus.OK);
        response.setStatusCode(HttpStatus.OK.value());

        //Swipe firstSwipeInRecord = swipeRepository.findFirstByEmployeeAndDate(emp, date);
        timesheetDetails.setFirstSwipe(firstSwipeInRecord.getSwipeInTime());
        Swipe lastSwipeOutRecord = swipeRepository.findFirstByEmployeeAndDateOrderBySwipeOutTimeDesc(emp, date);
        timesheetDetails.setLastSwipe(lastSwipeOutRecord.getSwipeOutTime());
        timesheetDetails.setEndOfDayTime(result);

        response.setResponseBody(timesheetDetails);
        return response;

    }
}