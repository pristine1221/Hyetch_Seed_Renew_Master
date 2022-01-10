package com.example.pristineseed.DataBaseRepository.EventManagementTable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "event_expence_line_table")
public class EventManagementExpenseLineTable {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int android_event_code;

    @NonNull
    @ColumnInfo(name = "event_code")
    private String event_code;
    @NonNull
    @ColumnInfo(name = "line_no")
    private int line_no;

    public int getAndroid_event_code() {
        return android_event_code;
    }

    public void setAndroid_event_code(int android_event_code) {
        this.android_event_code = android_event_code;
    }

    public String getEvent_code() {
        return event_code;
    }

    public void setEvent_code(String event_code) {
        this.event_code = event_code;
    }

    public int getLine_no() {
        return line_no;
    }

    public void setLine_no(int line_no) {
        this.line_no = line_no;
    }

    public String getExpense_type() {
        return expense_type;
    }

    public void setExpense_type(String expense_type) {
        this.expense_type = expense_type;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRate_unit_cost() {
        return rate_unit_cost;
    }

    public void setRate_unit_cost(String rate_unit_cost) {
        this.rate_unit_cost = rate_unit_cost;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getSendToServer() {
        return sendToServer;
    }

    public void setSendToServer(String sendToServer) {
        this.sendToServer = sendToServer;
    }

   /* public String getExpense_type_name() {
        return expense_type_name;
    }

    public void setExpense_type_name(String expense_type_name) {
        this.expense_type_name = expense_type_name;
    }*/

    @ColumnInfo(name = "expense_type")
    private String expense_type;
    @ColumnInfo(name = "quantity")
    private String quantity;
    @ColumnInfo(name = "rate_unit_cost")
    private String rate_unit_cost;
    @ColumnInfo(name = "amount")
    private String amount;
    @ColumnInfo(name = "created_on")
    private String created_on;

    @NonNull
    @ColumnInfo(name = "sendToServer")
    private String sendToServer;

    public String expense_type_name;



}
