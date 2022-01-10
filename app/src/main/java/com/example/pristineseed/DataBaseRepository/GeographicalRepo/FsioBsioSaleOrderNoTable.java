package com.example.pristineseed.DataBaseRepository.GeographicalRepo;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pristineseed.model.item.FsioBsioSaleOrderNoModel;

@Entity(tableName = "fsio_bsio_sale_order_no_table")
public class FsioBsioSaleOrderNoTable {

    public int getAndroid_id() {
        return android_id;
    }

    public void setAndroid_id(int android_id) {
        this.android_id = android_id;
    }

    @PrimaryKey(autoGenerate = true)
    private int android_id;
    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }

    public String getDocument_SubType() {
        return Document_SubType;
    }

    public void setDocument_SubType(String document_SubType) {
        Document_SubType = document_SubType;
    }

    @ColumnInfo(name = "doc_sale_no")
    private   String No;
    @ColumnInfo(name = "Document_SubType")
    private String  Document_SubType;



    public static FsioBsioSaleOrderNoTable insertFsioBsioSaleOrderNoData(FsioBsioSaleOrderNoModel.Data saleOrderNo){
        FsioBsioSaleOrderNoTable saleOrderNoTable=new FsioBsioSaleOrderNoTable();
        saleOrderNoTable.No=saleOrderNo.No;
        saleOrderNoTable.Document_SubType=saleOrderNo.Document_SubType;

        return saleOrderNoTable;


    }
}
