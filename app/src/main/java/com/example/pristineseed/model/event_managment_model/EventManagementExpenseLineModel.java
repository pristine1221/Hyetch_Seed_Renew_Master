package com.example.pristineseed.model.event_managment_model;

public class EventManagementExpenseLineModel {
    public String event_code;
    public String line_no;
    public String expense_type;
    public String quantity;
    public String rate_unit_cost;
    public String amount;
    public String created_on;
    public String sendToServer;
    public String expense_type_name;

    public EventManagementExpenseLineModel(String event_code, String line_no, String expense_type, String quantity, String rate_unit_cost, String amount, String created_on, String sendToServer) {
        this.event_code = event_code;
        this.line_no = line_no;
        this.expense_type = expense_type;
        this.quantity = quantity;
        this.rate_unit_cost = rate_unit_cost;
        this.amount = amount;
        this.created_on = created_on;
        this.sendToServer = sendToServer;
    }


}
