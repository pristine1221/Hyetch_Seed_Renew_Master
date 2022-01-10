package com.example.pristineseed.DataBaseRepository.GeographicalRepo;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookingUnitPriceDao {
    @Insert
    Long insert(BookingUnitPriceTable bookingUnitPriceTable);

    @Insert
    void insert(List<BookingUnitPriceTable> uomTablesList);

    @Query("delete from booking_unit_price_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(1) FROM booking_unit_price_table")
    int getRowCount();


    @Query("SELECT * FROM booking_unit_price_table")
    List<BookingUnitPriceTable> fetchAllData();

    @Query("SELECT * FROM booking_unit_price_table where  Booking_Indent_No=:booking_indent_no and Booking_Indent_No not null and Booking_Indent_No not in('',' ') ")
    BookingUnitPriceTable   fetchAllDataBybookingNo(String booking_indent_no);


    @Query("SELECT * FROM booking_unit_price_table where App_No=:appNo and App_No not null and App_No not in('',' ') and  Booking_Indent_No=:booking_indent_no and Booking_Indent_No not null and Booking_Indent_No not in('',' ') ")
    BookingUnitPriceTable   fetchAllDataBybookingNowithUnitPrice(String appNo, String booking_indent_no);
}
