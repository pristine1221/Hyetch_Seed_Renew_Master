package com.example.pristineseed.model.home;

public class CollectionList {

           private boolean condition;
           private String message;
           private String collection_code;
           private String date;
           private String party_name;
           private String place;
           private String chq_dd_rtgs_no;
           private String drawn_on_bank_name;
           private String deposited_bank;
           private String deposited_at;
           private String payment_type;
           private String bank;
           private String date_of_receipt;
           private String remark;


    public boolean isCondition() {
        return condition;
    }

    public void setCondition(boolean condition) {
        this.condition = condition;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCollection_code() {
        return collection_code;
    }

    public void setCollection_code(String collection_code) {
        this.collection_code = collection_code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getParty_name() {
        return party_name;
    }

    public void setParty_name(String party_name) {
        this.party_name = party_name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getChq_dd_rtgs_no() {
        return chq_dd_rtgs_no;
    }

    public void setChq_dd_rtgs_no(String chq_dd_rtgs_no) {
        this.chq_dd_rtgs_no = chq_dd_rtgs_no;
    }

    public String getDrawn_on_bank_name() {
        return drawn_on_bank_name;
    }

    public void setDrawn_on_bank_name(String drawn_on_bank_name) {
        this.drawn_on_bank_name = drawn_on_bank_name;
    }

    public String getDeposited_bank() {
        return deposited_bank;
    }

    public void setDeposited_bank(String deposited_bank) {
        this.deposited_bank = deposited_bank;
    }

    public String getDeposited_at() {
        return deposited_at;
    }

    public void setDeposited_at(String deposited_at) {
        this.deposited_at = deposited_at;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getDate_of_receipt() {
        return date_of_receipt;
    }

    public void setDate_of_receipt(String date_of_receipt) {
        this.date_of_receipt = date_of_receipt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

           private String created_by;
           private String created_on;



    public String title;
    public String subtitle;
    public String subtitle2;
    public String datetime;
    public CollectionList(String title, String subtitle,String subtitle2, String datetime){
        this.title=title;
        this.subtitle=subtitle;
        this.datetime=datetime;
        this.subtitle2=subtitle2;
    }
}
