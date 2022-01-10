package com.example.pristineseed.model.tfa;

import java.util.List;

public class FAInsertLineResponseModel {

    public boolean condition;
    public String message;
    public int line_no;
    public String tfa_code;

 public String name;
 public String place;
 public String district;
 public String state;
 public String target_of_all_crop;
 public String date_of_joining;
 public String date_of_discontinue;
 public String aadhaar_card;
 public String month_salary;
 public String bank_name;
 public String account_no;
 public String ifsc_code;
 public String mobile_no;
 public String created_by;
 public String created_on;
 public String status;
 public String approver_id_1;
 public String approver_id_2;
 public String reason;
 public String approve_on;
 public String nav_sync;
 public String nav_message;
 public String completed_on;
 public String nav_request;
 public String nav_response;
 public String is_updated;
 public List<TFAHeaderModel.Tfa_lineModel> tfa_line;

 public class Tfa_lineModel {
  public boolean condition;
  public String line_no;
  public String salary_month;
  public String salary_amount;
  public String created_on;
  public String message;
  public String tfa_code;
  public String remarks;
 }

}
