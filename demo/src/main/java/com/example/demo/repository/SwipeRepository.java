package com.example.demo.repository;

import com.example.demo.Model.Employee;
import com.example.demo.Model.Swipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.sql.Date;
import java.util.List;

@Repository
public interface SwipeRepository extends JpaRepository<Swipe, Integer> {
    Swipe findFirstByEmployeeOrderBySwipeInTimeDesc(Employee employee);

    Swipe findFirstByEmployeeAndDateOrderBySwipeInTimeAsc(Employee employee, Date date);

    Swipe findFirstByEmployeeAndDateOrderBySwipeOutTimeDesc(Employee employee, Date date);

    Swipe findFirstByEmployeeAndDateOrderBySwipeInTimeDesc(Employee employee, Date date);

    Swipe findByDate(Date date);

    List<Swipe> findByEmployeeAndDate(Employee employee, Date date);

    List<Swipe> findLastByEmployeeAndDateOrderByDateDesc(Employee employee, Date date);

    Swipe findFirstByEmployeeAndDate(Employee emp, Date date);

    Swipe findFirstByEmployeeOrderByDateDesc(Employee emp);
    //This method  Swipe findFirstByEmployeeOrderByDateDesc(Employee emp); returns swipe record of
    //employee which is passed as argument to method in descending order.

//    select * from  emp_swipe_details where employee_id=2 and
//    date = (select * from  emp_swipe_details where employee_id=2 Order by date desc )
//    Order by swipe_in_time desc limit 1;

    //Swipe findFirstByEmployeeAndDateOrderBySwipeInTimeDesc(Employee emp,Date date);

    //Swipe findByEmployeeAndDate
//    @Query(value = "select * from emp_swipe_details where employee_id=?1 and date=?2", nativeQuery = true)
//    List<Swipe> findByEmployeeAndDate(int empId, Date date);


}
