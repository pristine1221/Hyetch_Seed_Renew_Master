package com.example.pristineseed.DataBaseRepository.GeographicalRepo;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pristineseed.model.item.BankMaserModel;

@Entity(tableName = "bank_master_table")
public class BankMasterTable {
    @PrimaryKey(autoGenerate = true)
    private int android_id;
    @ColumnInfo(name = "Customer_No")
  private String Customer_No;
    @ColumnInfo(name = "Code")
  private String Code;

    public int getAndroid_id() {
        return android_id;
    }

    public void setAndroid_id(int android_id) {
        this.android_id = android_id;
    }

    public String getCustomer_No() {
        return Customer_No;
    }

    public void setCustomer_No(String customer_No) {
        Customer_No = customer_No;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBank_Account_No() {
        return Bank_Account_No;
    }

    public void setBank_Account_No(String bank_Account_No) {
        Bank_Account_No = bank_Account_No;
    }

    public String getSWIFT_Code() {
        return SWIFT_Code;
    }

    public void setSWIFT_Code(String SWIFT_Code) {
        this.SWIFT_Code = SWIFT_Code;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    @ColumnInfo(name = "Name")
  private String Name;
    @ColumnInfo(name = "Bank_Account_No")
  private String Bank_Account_No;
    @ColumnInfo(name = "SWIFT_Code")
  private String SWIFT_Code;
    @ColumnInfo(name = "IBAN")
  private String IBAN;




    public static BankMasterTable insertBankMasterData(BankMaserModel.Data bankMaserModel){
        BankMasterTable bankMasterTable=new BankMasterTable();
   bankMasterTable. Customer_No =bankMaserModel.Customer_No;
   bankMasterTable. Code =bankMaserModel.Code;
   bankMasterTable. Name =bankMaserModel.Name;
   bankMasterTable. Bank_Account_No =bankMaserModel.Bank_Account_No;
   bankMasterTable. SWIFT_Code =bankMaserModel.SWIFT_Code;
   bankMasterTable. IBAN =bankMaserModel.IBAN;

   return bankMasterTable;
    }
}
