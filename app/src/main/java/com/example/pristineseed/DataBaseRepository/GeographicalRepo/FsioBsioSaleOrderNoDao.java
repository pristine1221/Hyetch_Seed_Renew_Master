package com.example.pristineseed.DataBaseRepository.GeographicalRepo;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FsioBsioSaleOrderNoDao {

    @Insert
    Long insert(FsioBsioSaleOrderNoTable fsioBsioSaleOrderNoTable);
    @Insert
    void insert(List<FsioBsioSaleOrderNoTable> fsioBsioSaleOrderNoTableList);

    @Query("SELECT * FROM fsio_bsio_sale_order_no_table WHERE doc_sale_no  not null")
    List<FsioBsioSaleOrderNoTable> fetchAllData();

    @Update
    void update(FsioBsioSaleOrderNoTable bankMasterTable);

    @Query("delete from fsio_bsio_sale_order_no_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(1) FROM fsio_bsio_sale_order_no_table")
    int getRowCount();

    @Query("SELECT * FROM fsio_bsio_sale_order_no_table WHERE Document_SubType=:doc_sub_type")
    List<FsioBsioSaleOrderNoTable> fetchSaleOrderData(String doc_sub_type);

}
