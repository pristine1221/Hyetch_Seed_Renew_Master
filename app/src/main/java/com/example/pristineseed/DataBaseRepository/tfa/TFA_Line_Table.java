package com.example.pristineseed.DataBaseRepository.tfa;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Tfa_Line_Table")
public class TFA_Line_Table {

    @PrimaryKey(autoGenerate = true)
    public String line_no;
    public String getLine_no() { return line_no; }
    public void setLine_no(String line_no) { this.line_no = line_no; }

    @ColumnInfo(name = "salary_month")
    private String salary_month;
    public String getSalary_month() { return salary_month; }
    public void setSalary_month(String salary_month) { this.salary_month = salary_month; }

    @ColumnInfo(name = "salary_amount")
    private String salary_amount;
    public String getSalary_amount() { return salary_amount;}
    public void setSalary_amount(String salary_amount) { this.salary_amount = salary_amount; }

    @ColumnInfo(name = "created_on")
    private String created_on;
    public String getCreated_on() { return created_on; }
    public void setCreated_on(String created_on) { this.created_on = created_on; }
}
