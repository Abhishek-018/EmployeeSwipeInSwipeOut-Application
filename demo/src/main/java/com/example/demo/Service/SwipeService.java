package com.example.demo.Service;

import com.example.demo.Model.Employee;
import com.example.demo.Model.Swipe;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.SwipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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


    public void swipeIn(Swipe swipe) {
        int id = swipe.getEmployee().getEmployeeId();
        Employee emp = employeeRepository.findByEmployeeId(id);
        Swipe lastSwipe = swipeRepository.findFirstByEmployeeOrderBySwipeInTimeDesc(emp);
        List<Swipe> getAllSwipe = swipeRepository.findAll();

        // Check if database isEmpty
        if (getAllSwipe.isEmpty()) {
            Swipe newSwipe = new Swipe();
            newSwipe.setEmployee(emp);

            Date date= new Date(new java.util.Date().getTime());
            newSwipe.setDate(date);
            LocalTime localTime = LocalTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            String st1 = localTime.format(dateTimeFormatter);
            LocalTime lt
                    = LocalTime
                    .parse(st1, dateTimeFormatter);

            newSwipe.setSwipeInTime(lt);
            swipeRepository.save(newSwipe);
            System.out.println(newSwipe.getSwipeInTime());
        }
        if (!getAllSwipe.isEmpty()){

            if (lastSwipe == null) {
                Swipe newSwipe = new Swipe();
                newSwipe.setEmployee(emp);

                Date date = new Date(new java.util.Date().getTime());
                newSwipe.setDate(date);
                LocalTime localTime = LocalTime.now();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
                String st1 = localTime.format(dateTimeFormatter);
                LocalTime lt
                        = LocalTime
                        .parse(st1, dateTimeFormatter);

                newSwipe.setSwipeInTime(lt);
                swipeRepository.save(newSwipe);
                System.out.println(newSwipe.getSwipeInTime());

            } else if (lastSwipe !=null && lastSwipe.getSwipeOutTime()!=null ) {
                Swipe newSwipe = new Swipe();
                newSwipe.setEmployee(emp);

                Date date = new Date(new java.util.Date().getTime());
                newSwipe.setDate(date);
                LocalTime localTime = LocalTime.now();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
                String st1 = localTime.format(dateTimeFormatter);
                LocalTime lt
                        = LocalTime
                        .parse(st1, dateTimeFormatter);

                newSwipe.setSwipeInTime(lt);
                swipeRepository.save(newSwipe);
                System.out.println(newSwipe.getSwipeInTime());

            } else {
                throw new RuntimeException("Cannot swipe in. Employee already swiped in and has not swiped out.");
            }


        }

    }


       public List<Swipe> getEmployeeSwipeRecordForDate(int employeeId, Date date) {



           Employee emp = employeeRepository.findByEmployeeId(employeeId);


           List<Swipe> swipes = swipeRepository.findByEmployeeAndDate(emp, date);
           return swipes;

       }



    public void swipeOut(Employee employee) {
        // Find the most recent swipe record for this employee
        int id = employee.getEmployeeId();
        Employee emp = employeeRepository.findByEmployeeId(id);
        Swipe lastSwipe = swipeRepository.findFirstByEmployeeOrderBySwipeInTimeDesc(emp);

        // Check if there is an open swipe record

        if (lastSwipe.getSwipeOutTime() != null) {
            throw new RuntimeException("Cannot swipe out. Employee has not swiped in or already swiped out.");
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
    }

    public String calculateActualWorkingHours(Swipe swipe)throws Exception {
        int id = swipe.getEmployee().getEmployeeId();
        Employee emp= employeeRepository.findByEmployeeId(id);
        Date date = swipe.getDate();
        List<Swipe> swipes = swipeRepository.findByEmployeeAndDate(emp, date);
        int swipeSize = swipes.size();
        Duration actualWorkingHours = Duration.ZERO;

        if(swipes.isEmpty()){
            throw new RuntimeException("No swipes found for the specified employee and date.");
        }





        for (int i = 0; i < swipes.size(); i++) {
            Swipe swipeIn = swipes.get(i);
            Swipe swipeOut = swipes.get(i);


            if (swipeOut == null) {
                // employee forgot to swipe out
                continue;
            }

            LocalTime swipeInTime = swipeIn.getSwipeInTime();
            LocalTime swipeOutTime = swipeOut.getSwipeOutTime();
            actualWorkingHours = actualWorkingHours.plus(Duration.between(swipeInTime, swipeOutTime));
                    }

        String result = actualWorkingHours
                .toHoursPart() + " hours, "
                + actualWorkingHours.toMinutesPart() + " minutes, "
                + actualWorkingHours.toSecondsPart() + " seconds";

        return "Actual Working Hours: "+result;

    }


    public String calculateTotalWorkingHours(Swipe swipe) throws Exception {
        int id = swipe.getEmployee().getEmployeeId();
        Employee emp = employeeRepository.findByEmployeeId(id);
        Date date = swipe.getDate();



        List<Swipe> swipes = swipeRepository.findByEmployeeAndDate(emp, date);


        //  null check ,Do this by exception handling
        if(swipes.isEmpty()){
            throw new Exception("No swipes found for the specified employee and date.");
        }


        Swipe firstSwipeIn = swipeRepository.findFirstByEmployeeAndDateOrderBySwipeInTimeAsc(emp,date);
        Swipe lastSwipeOut = swipeRepository.findFirstByEmployeeAndDateOrderBySwipeOutTimeDesc(emp,date);
        LocalTime firstSwipeInTime = firstSwipeIn.getSwipeInTime();
        LocalTime lastSwipeOutTime = lastSwipeOut.getSwipeOutTime();
        Duration totalWorkingHours = Duration.ZERO;
        LocalTime t;
        totalWorkingHours = Duration.between(firstSwipeInTime,lastSwipeOutTime);


        String result =

                  totalWorkingHours.toHoursPart() + " hours, "
                + totalWorkingHours.toMinutesPart() + " minutes, "
                + totalWorkingHours.toSecondsPart() + " seconds";


        return  "FirstSwipe: " +firstSwipeInTime +
                "\nLastSwipe: "  +lastSwipeOutTime +
                "\nTotal Working Hours : " +result ;



    }


    public  String totalOutTime(Swipe swipe)throws Exception{

        int id = swipe.getEmployee().getEmployeeId();
        Employee emp = employeeRepository.findByEmployeeId(id);
        Date date = swipe.getDate();

        Swipe firstSwipeIn = swipeRepository.findFirstByEmployeeAndDateOrderBySwipeInTimeAsc(emp,date);
        Swipe lastSwipeOut = swipeRepository.findFirstByEmployeeAndDateOrderBySwipeOutTimeDesc(emp,date);

        if (firstSwipeIn == null || lastSwipeOut == null) {
            throw new RuntimeException("No swipes found for the specified employee and date.");

        }

        LocalTime firstSwipeInTime = firstSwipeIn.getSwipeInTime();
        LocalTime lastSwipeOutTime = lastSwipeOut.getSwipeOutTime();
        Duration totalWorkingHours = Duration.ZERO;
        totalWorkingHours = Duration.between(firstSwipeInTime,lastSwipeOutTime);

        List<Swipe> swipes = swipeRepository.findByEmployeeAndDate(emp, date);

        Duration actualWorkingHours = Duration.ZERO;


        for (int i = 0; i < swipes.size(); i++) {
            Swipe swipeIn = swipes.get(i);
            Swipe swipeOut = swipes.get(i);


            if (swipeOut == null) {
                // employee forgot to swipe out
                continue;
            }

            LocalTime swipeInTime = swipeIn.getSwipeInTime();
            LocalTime swipeOutTime = swipeOut.getSwipeOutTime();
            actualWorkingHours = actualWorkingHours.plus(Duration.between(swipeInTime, swipeOutTime));
        }

        Duration totalOutTime = Duration.ZERO;
        totalOutTime = totalWorkingHours.minus(actualWorkingHours);


        String result =

                          totalOutTime.toHoursPart() + " hours, "
                        + totalOutTime.toMinutesPart() + " minutes, "
                        + totalOutTime.toSecondsPart() + " seconds";
        return "OutTime: " + result;

    }




//    public List<Swipe> getAllEmployee(Swipe swipe) {
//
//
//        int id = swipe.getEmployee().getEmployeeId();
//        Employee emp= employeeRepository.findByEmployeeId(id);
//        int empId=emp.getEmployeeId();
//        int id1 = swipe.getEmployee().getEmployeeId();
//        Date date = swipe.getDate();
//        List<Swipe> empSwipe=swipeRepository.findByEmployeeAndDate(id1, date);
//        return empSwipe;
//
//    }
}
