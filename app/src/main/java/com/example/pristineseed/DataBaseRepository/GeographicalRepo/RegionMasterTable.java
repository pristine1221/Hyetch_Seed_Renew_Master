package com.example.pristineseed.DataBaseRepository.GeographicalRepo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "region_master_table")
public class RegionMasterTable {
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @PrimaryKey()
    @ColumnInfo(name = "code")
    private String code;

    @ColumnInfo(name = "name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getCode() {
        return code;
    }

    public void setCode( String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegional_manager() {
        return regional_manager;
    }

    public void setRegional_manager(String regional_manager) {
        this.regional_manager = regional_manager;
    }

    public String getRegional_manager_email_id() {
        return regional_manager_email_id;
    }

    public void setRegional_manager_email_id(String regional_manager_email_id) {
        this.regional_manager_email_id = regional_manager_email_id;
    }

    public String getRegional_manager_emp_code() {
        return regional_manager_emp_code;
    }

    public void setRegional_manager_emp_code(String regional_manager_emp_code) {
        this.regional_manager_emp_code = regional_manager_emp_code;
    }

    public String getRegional_head_emp_code() {
        return regional_head_emp_code;
    }

    public void setRegional_head_emp_code(String regional_head_emp_code) {
        this.regional_head_emp_code = regional_head_emp_code;
    }

    public String getRegional_head() {
        return regional_head;
    }

    public void setRegional_head(String regional_head) {
        this.regional_head = regional_head;
    }

    public String getRegional_head_email_id() {
        return regional_head_email_id;
    }

    public void setRegional_head_email_id(String regional_head_email_id) {
        this.regional_head_email_id = regional_head_email_id;
    }

    public String getRegional_manager_mobile() {
        return regional_manager_mobile;
    }

    public void setRegional_manager_mobile(String regional_manager_mobile) {
        this.regional_manager_mobile = regional_manager_mobile;
    }

    public String getUpdated_on() {
        return updated_on;
    }

    public void setUpdated_on(String updated_on) {
        this.updated_on = updated_on;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    @ColumnInfo(name = "regional_manager")
    private String regional_manager;

    @ColumnInfo(name = "regional_manager_email_id")
    private String regional_manager_email_id;

    @ColumnInfo(name = "regional_manager_emp_code")
    private String regional_manager_emp_code;

    @ColumnInfo(name = "regional_head_emp_code")
    private String regional_head_emp_code;

    @ColumnInfo(name = "regional_head")
    private String regional_head;

    @ColumnInfo(name = "regional_head_email_id")
    private String regional_head_email_id;

    @ColumnInfo(name = "regional_manager_mobile")
    private String regional_manager_mobile;
    @ColumnInfo(name = "updated_on")
    private String updated_on;

    @ColumnInfo(name = "active")
    private String active;
}
