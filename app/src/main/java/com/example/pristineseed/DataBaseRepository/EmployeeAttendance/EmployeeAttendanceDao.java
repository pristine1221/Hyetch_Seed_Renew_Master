package com.example.pristineseed.DataBaseRepository.EmployeeAttendance;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EmployeeAttendanceDao {

    @Insert
    Long insert(EmployeeAttendanceTable u );

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    Long insert(EmployeeAttendanceTable employeeAttendanceTable);

    @Query("SELECT * FROM employee_attendance_table ORDER BY id DESC")
    List<EmployeeAttendanceTable> getAllData();

    @Query("SELECT * FROM employee_attendance_table where send_to_server=:sendtoserver")
    List<EmployeeAttendanceTable> getSendToServerPendingData(int sendtoserver);

    @Update
    void update(EmployeeAttendanceTable employeeAttendanceTable);

    @Query("delete from employee_attendance_table where send_to_server=1")
    int deleteAllRecord();
}
