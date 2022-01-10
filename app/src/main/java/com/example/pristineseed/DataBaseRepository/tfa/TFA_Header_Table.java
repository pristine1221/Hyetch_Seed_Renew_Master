package com.example.pristineseed.DataBaseRepository.tfa;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pristineseed.model.tfa.CreateTFAHeaderModel;


@Entity(tableName = "Tfa_Header_Table")
public class TFA_Header_Table {

  public int getAndroid_id() { return android_id; }
  public void setAndroid_id(int android_id) { this.android_id = android_id; }
  @PrimaryKey(autoGenerate = true)
  private int android_id;

  @ColumnInfo(name = "tfa_code")
  private String tfa_code;
  public String getTfa_code() { return tfa_code; }
  public void setTfa_code(String tfa_code) { this.tfa_code = tfa_code; }

  @ColumnInfo(name = "name")
  private String name;
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  @ColumnInfo(name = "place")
  private String place;
  public String getPlace() { return place; }
  public void setPlace(String place) { this.place = place; }

  @ColumnInfo(name = "district")
  private String district;
  public String getDistrict() { return district; }
  public void setDistrict(String district) { this.district = district; }

  @ColumnInfo(name = "state")
  private String state;
  public String getState() { return state; }
  public void setState(String state) { this.state = state; }

  @ColumnInfo(name = "date_of_joining")
  private String date_of_joining;
  public String getDate_of_joining() { return date_of_joining; }
  public void setDate_of_joining(String date_of_joining) { this.date_of_joining = date_of_joining; }

  @ColumnInfo(name = "date_of_discontinue")
  private String date_of_discontinue;
  public String getDate_of_discontinue() { return date_of_discontinue; }
  public void setDate_of_discontinue(String date_of_discontinue) { this.date_of_discontinue = date_of_discontinue; }

  @ColumnInfo(name = "aadhaar_card")
  private String aadhaar_card;
  public String getAadhaar_card() { return aadhaar_card; }
  public void setAadhaar_card(String aadhaar_card) { this.aadhaar_card = aadhaar_card; }

  @ColumnInfo(name = "month_salary")
  private String month_salary;
  public String getMonth_salary() { return month_salary; }
  public void setMonth_salary(String month_salary) { this.month_salary = month_salary; }


  @ColumnInfo(name = "bank_name")
  private String bank_name;
  public String getBank_name() { return bank_name; }
  public void setBank_name(String bank_name) { this.bank_name = bank_name; }

  @ColumnInfo(name = "account_no")
  private String account_no;
  public String getAccount_no() { return account_no; }
  public void setAccount_no(String account_no) { this.account_no = account_no; }

  @ColumnInfo(name = "ifsc_code")
  private String ifsc_code;
  public String getIfsc_code() { return ifsc_code; }
  public void setIfsc_code(String ifsc_code) { this.ifsc_code = ifsc_code; }

  @ColumnInfo(name = "mobile_no")
  private String mobile_no;
  public String getMobile_no() { return mobile_no; }
  public void setMobile_no(String mobile_no) { this.mobile_no = mobile_no; }

  @ColumnInfo(name = "created_by")
  private String created_by;
  public String getCreated_by() { return created_by; }
  public void setCreated_by(String created_by) { this.created_by = created_by; }

  @ColumnInfo(name = "created_on")
  private String created_on;
  public String getCreated_on() { return created_on; }
  public void setCreated_on(String created_on) { this.created_on = created_on; }

  @ColumnInfo(name = "status")
  private String status;
  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }


  public String getApprover_id_1() {
    return approver_id_1;
  }

  public void setApprover_id_1(String approver_id_1) {
    this.approver_id_1 = approver_id_1;
  }

  public String getApprover_id_2() {
    return approver_id_2;
  }

  public void setApprover_id_2(String approver_id_2) {
    this.approver_id_2 = approver_id_2;
  }

  @ColumnInfo(name = "approver_id_1")
  private String approver_id_1;

  @ColumnInfo(name = "approver_id_2")
  private String approver_id_2 ;




  public static TFA_Header_Table getTfaHeaderFromServer(CreateTFAHeaderModel createTFAModelModel){

    TFA_Header_Table tfa_header_table = new TFA_Header_Table();
    tfa_header_table.tfa_code = createTFAModelModel.tfa_code;
    tfa_header_table.name = createTFAModelModel.name;
    tfa_header_table.place = createTFAModelModel.place;
    tfa_header_table.district = createTFAModelModel.district;
    tfa_header_table.state = createTFAModelModel.state;
    tfa_header_table.date_of_joining = createTFAModelModel.date_of_joining;
    tfa_header_table.date_of_discontinue = createTFAModelModel.date_of_discontinue;
    tfa_header_table.aadhaar_card = createTFAModelModel.aadhaar_card;
    tfa_header_table.month_salary = createTFAModelModel.month_salary;
    tfa_header_table.bank_name = createTFAModelModel.bank_name;
    tfa_header_table.account_no = createTFAModelModel.account_no;
    tfa_header_table.ifsc_code = createTFAModelModel.ifsc_code;
    tfa_header_table.mobile_no = createTFAModelModel.mobile_no;
    tfa_header_table.created_by = createTFAModelModel.created_by;
    tfa_header_table.created_on = createTFAModelModel.created_on;
    tfa_header_table.status = createTFAModelModel.status;
    tfa_header_table.approver_id_1=createTFAModelModel.approver_id_1;
    tfa_header_table.approver_id_2=createTFAModelModel.approver_id_2;

    return tfa_header_table;
  }
}
