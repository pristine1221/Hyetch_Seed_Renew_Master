package com.example.pristineseed.sql_lite_process.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exception_mst")
public class ExceptionTableModel {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "Exception")
    private String myException;

    @ColumnInfo(name = "ExceptionType")
    private String ExceptionType;

    @ColumnInfo(name = "lineNo")
    private String lineNo;


    @ColumnInfo(name = "fragment")
    private String fragment;

    @ColumnInfo(name = "method")
    private String method;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getExceptionType() {
        return ExceptionType;
    }

    public void setExceptionType(String exceptionType) {
        ExceptionType = exceptionType;
    }

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    public String getFragment() {
        return fragment;
    }

    public void setFragment(String fragment) {
        this.fragment = fragment;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMyException() {
        return myException;
    }

    public void setMyException(String myException) {
        this.myException = myException;
    }
}
