package com.example.pristineseed.DataBaseRepository.Planting;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "planting_lot_master_table")
public class Planting_Lot_master_Table {
    @NonNull
@PrimaryKey(autoGenerate = true)
private int android_id;


    @NonNull
    public String getLot_No() {
        return Lot_No;
    }

    public void setLot_No(@NonNull String lot_No) {
        Lot_No = lot_No;
    }

    @ColumnInfo(name = "Lot_No")
    private String Lot_No;

    public int getAndroid_id() {
        return android_id;
    }

    public void setAndroid_id(int android_id) {
        this.android_id = android_id;
    }

    public String getMale_Female_Other() {
        return Male_Female_Other;
    }

    public void setMale_Female_Other(String male_Female_Other) {
        Male_Female_Other = male_Female_Other;
    }

    public String getDocument_SubType() {
        return Document_SubType;
    }

    public void setDocument_SubType(String document_SubType) {
        Document_SubType = document_SubType;
    }

    public String getDocument_No() {
        return Document_No;
    }

    public void setDocument_No(String document_No) {
        Document_No = document_No;
    }

    @ColumnInfo(name = "Male_Female_Other")
    private String Male_Female_Other;
    @ColumnInfo(name = "Document_SubType")
    private String Document_SubType;

    @ColumnInfo(name = "Document_No")
    public String Document_No;

    @ColumnInfo(name = "Receipt_No")
    private String Receipt_No;

    @ColumnInfo(name = "No")
    private String No;

    @ColumnInfo(name = "Quantity")
    private String Quantity;

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getReceipt_No() {
        return Receipt_No;
    }

    public void setReceipt_No(String receipt_No) {
        Receipt_No = receipt_No;
    }

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }





}
