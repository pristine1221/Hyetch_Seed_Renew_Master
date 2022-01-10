package com.example.pristineseed.DataBaseRepository.EmployeeAttendance;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pristineseed.model.EmployeeModel.EmployeeAttendModel;

@Entity(tableName = "employee_attendance_table")
public class EmployeeAttendanceTable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String emp_code;

    @ColumnInfo
    private String working_date;

    @ColumnInfo
    private String attendance_type;

    @ColumnInfo
    private String start_time;

    @ColumnInfo
    private String end_time;

    @ColumnInfo
    private String from_latitude;

    @ColumnInfo
    private String from_longitude;

    @ColumnInfo
    private String from_locality;

    @ColumnInfo
    private String from_area;

    @ColumnInfo
    private String from_postal_code;

    @ColumnInfo
    private String from_country;

    @ColumnInfo
    private String to_latitude;

    @ColumnInfo
    private String to_longitude;

    @ColumnInfo
    private String to_locality;

    @ColumnInfo
    private String to_area;

    @ColumnInfo
    private String to_postal_code;

    @ColumnInfo
    private String to_country;

    @ColumnInfo
    private String created_by;

    @ColumnInfo
    private String created_on;

    @ColumnInfo
    private String updated_by;

    @ColumnInfo
    private String updated_on;

    public int getSend_to_server() {
        return send_to_server;
    }

    public void setSend_to_server(int send_to_server) {
        this.send_to_server = send_to_server;
    }

    @ColumnInfo
    private int send_to_server;


    @ColumnInfo
    private String online_offline;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmp_code() {
        return emp_code;
    }

    public void setEmp_code(String emp_code) {
        this.emp_code = emp_code;
    }

    public String getWorking_date() {
        return working_date;
    }

    public void setWorking_date(String working_date) {
        this.working_date = working_date;
    }

    public String getAttendance_type() {
        return attendance_type;
    }

    public void setAttendance_type(String attendance_type) {
        this.attendance_type = attendance_type;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getFrom_latitude() {
        return from_latitude;
    }

    public void setFrom_latitude(String from_latitude) {
        this.from_latitude = from_latitude;
    }

    public String getFrom_longitude() {
        return from_longitude;
    }

    public void setFrom_longitude(String from_longitude) {
        this.from_longitude = from_longitude;
    }

    public String getFrom_locality() {
        return from_locality;
    }

    public void setFrom_locality(String from_locality) {
        this.from_locality = from_locality;
    }

    public String getFrom_area() {
        return from_area;
    }

    public void setFrom_area(String from_area) {
        this.from_area = from_area;
    }

    public String getFrom_postal_code() {
        return from_postal_code;
    }

    public void setFrom_postal_code(String from_postal_code) {
        this.from_postal_code = from_postal_code;
    }

    public String getFrom_country() {
        return from_country;
    }

    public void setFrom_country(String from_country) {
        this.from_country = from_country;
    }

    public String getTo_latitude() {
        return to_latitude;
    }

    public void setTo_latitude(String to_latitude) {
        this.to_latitude = to_latitude;
    }

    public String getTo_longitude() {
        return to_longitude;
    }

    public void setTo_longitude(String to_longitude) {
        this.to_longitude = to_longitude;
    }

    public String getTo_locality() {
        return to_locality;
    }

    public void setTo_locality(String to_locality) {
        this.to_locality = to_locality;
    }

    public String getTo_area() {
        return to_area;
    }

    public void setTo_area(String to_area) {
        this.to_area = to_area;
    }

    public String getTo_postal_code() {
        return to_postal_code;
    }

    public void setTo_postal_code(String to_postal_code) {
        this.to_postal_code = to_postal_code;
    }

    public String getTo_country() {
        return to_country;
    }

    public void setTo_country(String to_country) {
        this.to_country = to_country;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public String getUpdated_on() {
        return updated_on;
    }

    public void setUpdated_on(String updated_on) {
        this.updated_on = updated_on;
    }

    public String getOnline_offline() {
        return online_offline;
    }

    public void setOnline_offline(String online_offline) {
        this.online_offline = online_offline;
    }


    public static EmployeeAttendanceTable getEmpAttendanceTypeData(EmployeeAttendModel employeeAttendanceTableMaster){
        EmployeeAttendanceTable employeeAttendanceTable=new EmployeeAttendanceTable();
        employeeAttendanceTable.emp_code = employeeAttendanceTableMaster.emp_code;
        employeeAttendanceTable.from_latitude = employeeAttendanceTableMaster.from_latitude;
        employeeAttendanceTable.from_longitude = employeeAttendanceTableMaster.from_longitude;
        employeeAttendanceTable.from_locality = employeeAttendanceTableMaster.from_locality;
        employeeAttendanceTable.from_area = employeeAttendanceTableMaster.from_area;
        employeeAttendanceTable.from_postal_code = employeeAttendanceTableMaster.from_postal_code;
        employeeAttendanceTable.from_country = employeeAttendanceTableMaster.from_country;
        employeeAttendanceTable.updated_on = employeeAttendanceTableMaster.updated_on;
        employeeAttendanceTable.updated_by = employeeAttendanceTableMaster.updated_by;
        employeeAttendanceTable.send_to_server = employeeAttendanceTableMaster.send_to_server;
        employeeAttendanceTable.created_by = employeeAttendanceTableMaster.created_by;
        employeeAttendanceTable.created_on = employeeAttendanceTableMaster.created_on;

        return employeeAttendanceTable;

    }
}
