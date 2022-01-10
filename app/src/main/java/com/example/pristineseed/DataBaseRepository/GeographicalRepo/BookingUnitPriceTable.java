package com.example.pristineseed.DataBaseRepository.GeographicalRepo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pristineseed.model.item.UnitPriceModel;

@Entity(tableName = "booking_unit_price_table")
public class BookingUnitPriceTable {

    public int getAndroid_id() {
        return android_id;
    }

    public void setAndroid_id(int android_id) {
        this.android_id = android_id;
    }

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int android_id;
    @ColumnInfo(name = "Document_No")
  private String Document_No;
    @ColumnInfo(name = "Line_No")
  private String Line_No;

    public String getDocument_No() {
        return Document_No;
    }

    public void setDocument_No(String document_No) {
        Document_No = document_No;
    }

    public String getLine_No() {
        return Line_No;
    }

    public void setLine_No(String line_No) {
        Line_No = line_No;
    }

    public String getCrop_Code() {
        return Crop_Code;
    }

    public void setCrop_Code(String crop_Code) {
        Crop_Code = crop_Code;
    }

    public String getVariety_Group() {
        return Variety_Group;
    }

    public void setVariety_Group(String variety_Group) {
        Variety_Group = variety_Group;
    }

    public String getVariety_Code() {
        return Variety_Code;
    }

    public void setVariety_Code(String variety_Code) {
        Variety_Code = variety_Code;
    }

    public String getBooking_Indent_No() {
        return Booking_Indent_No;
    }

    public void setBooking_Indent_No(String booking_Indent_No) {
        Booking_Indent_No = booking_Indent_No;
    }

    public String getBooking_Qty() {
        return Booking_Qty;
    }

    public void setBooking_Qty(String booking_Qty) {
        Booking_Qty = booking_Qty;
    }

    public String getAlloted_Qty() {
        return Alloted_Qty;
    }

    public void setAlloted_Qty(String alloted_Qty) {
        Alloted_Qty = alloted_Qty;
    }

    public String getAlloted_Percent() {
        return Alloted_Percent;
    }

    public void setAlloted_Percent(String alloted_Percent) {
        Alloted_Percent = alloted_Percent;
    }

    public String getIndent_Qty() {
        return Indent_Qty;
    }

    public void setIndent_Qty(String indent_Qty) {
        Indent_Qty = indent_Qty;
    }

    public String getBalance_Qty() {
        return Balance_Qty;
    }

    public void setBalance_Qty(String balance_Qty) {
        Balance_Qty = balance_Qty;
    }

    public String getSupplies_Qty() {
        return Supplies_Qty;
    }

    public void setSupplies_Qty(String supplies_Qty) {
        Supplies_Qty = supplies_Qty;
    }

    public String getUnit_of_Measure_Code() {
        return Unit_of_Measure_Code;
    }

    public void setUnit_of_Measure_Code(String unit_of_Measure_Code) {
        Unit_of_Measure_Code = unit_of_Measure_Code;
    }

    public String getUnit_Price() {
        return Unit_Price;
    }

    public void setUnit_Price(String unit_Price) {
        Unit_Price = unit_Price;
    }

    public String getNo_of_Bags() {
        return No_of_Bags;
    }

    public void setNo_of_Bags(String no_of_Bags) {
        No_of_Bags = no_of_Bags;
    }

    public String getBooking_Rate() {
        return Booking_Rate;
    }

    public void setBooking_Rate(String booking_Rate) {
        Booking_Rate = booking_Rate;
    }

    @ColumnInfo(name = "Crop_Code")
  private String Crop_Code;
    @ColumnInfo(name = "Variety_Group")
  private String Variety_Group;
    @ColumnInfo(name = "Variety_Code")
  private String Variety_Code;
    @ColumnInfo(name = "Booking_Indent_No")
  private String Booking_Indent_No;
    @ColumnInfo(name = "Booking_Qty")
  private String Booking_Qty;
    @ColumnInfo(name = "Alloted_Qty")
  private String Alloted_Qty;
    @ColumnInfo(name = "Alloted_Percent")
  private String Alloted_Percent;
    @ColumnInfo(name = "Indent_Qty")
  private String Indent_Qty;
    @ColumnInfo(name = "Balance_Qty")
  private String Balance_Qty;
    @ColumnInfo(name = "Supplies_Qty")
  private String Supplies_Qty;
    @ColumnInfo(name = "Unit_of_Measure_Code")
  private String Unit_of_Measure_Code;
    @ColumnInfo(name = "Unit_Price")
  private String Unit_Price;
    @ColumnInfo(name = "No_of_Bags")
  private String No_of_Bags;
    @ColumnInfo(name = "Booking_Rate")
  private String Booking_Rate;

    public String getLine_Discount_Percent() {
        return Line_Discount_Percent;
    }

    public void setLine_Discount_Percent(String line_Discount_Percent) {
        Line_Discount_Percent = line_Discount_Percent;
    }

    public String getLine_Discount_Amount() {
        return Line_Discount_Amount;
    }

    public void setLine_Discount_Amount(String line_Discount_Amount) {
        Line_Discount_Amount = line_Discount_Amount;
    }

    @ColumnInfo(name = "Line_Discount_Percent")
    private String Line_Discount_Percent;
    @ColumnInfo(name = "Line_Discount_Amount")
    private String Line_Discount_Amount;

    @ColumnInfo(name = "Variety_Name")
   private String Variety_Name;

    public String getVariety_Name() {
        return Variety_Name;
    }

    public void setVariety_Name(String variety_Name) {
        Variety_Name = variety_Name;
    }

    public String getVariety_Class_of_Seeds() {
        return Variety_Class_of_Seeds;
    }

    public void setVariety_Class_of_Seeds(String variety_Class_of_Seeds) {
        Variety_Class_of_Seeds = variety_Class_of_Seeds;
    }

    public String getVariety_Product_Group_Code() {
        return Variety_Product_Group_Code;
    }

    public void setVariety_Product_Group_Code(String variety_Product_Group_Code) {
        Variety_Product_Group_Code = variety_Product_Group_Code;
    }

    public String getVariety_Package_Size() {
        return Variety_Package_Size;
    }

    public void setVariety_Package_Size(String variety_Package_Size) {
        Variety_Package_Size = variety_Package_Size;
    }

    @ColumnInfo(name = "Variety_Class_of_Seeds")
   private String Variety_Class_of_Seeds;
    @ColumnInfo(name = "Variety_Product_Group_Code")
   private String Variety_Product_Group_Code;
    @ColumnInfo(name = "Variety_Package_Size")
   private String Variety_Package_Size;

    public String getApp_No() {
        return App_No;
    }

    public void setApp_No(String app_No) {
        App_No = app_No;
    }

    @ColumnInfo(name = "App_No")
    private String App_No;

    public static BookingUnitPriceTable getBookingUnitPrice(UnitPriceModel.Data unitpricemodel){

        BookingUnitPriceTable bookingUnitPriceTable=new BookingUnitPriceTable();

    bookingUnitPriceTable.Document_No =unitpricemodel.Document_No;
    bookingUnitPriceTable.Line_No =unitpricemodel.Line_No;
    bookingUnitPriceTable.Crop_Code =unitpricemodel.Crop_Code;
    bookingUnitPriceTable.Variety_Group =unitpricemodel.Variety_Group;
    bookingUnitPriceTable.Variety_Code =unitpricemodel.Variety_Code;
    bookingUnitPriceTable.Booking_Indent_No =unitpricemodel.Booking_Indent_No;
    bookingUnitPriceTable.Booking_Qty =unitpricemodel.Booking_Qty;
    bookingUnitPriceTable.Alloted_Qty =unitpricemodel.Alloted_Qty;
    bookingUnitPriceTable.Alloted_Percent =unitpricemodel.Alloted_Percent;
    bookingUnitPriceTable.Indent_Qty =unitpricemodel.Indent_Qty;
    bookingUnitPriceTable.Balance_Qty =unitpricemodel.Balance_Qty;
    bookingUnitPriceTable.Supplies_Qty =unitpricemodel.Supplies_Qty;
    bookingUnitPriceTable.Unit_of_Measure_Code =unitpricemodel.Unit_of_Measure_Code;
    bookingUnitPriceTable.Unit_Price =unitpricemodel.Unit_Price;
    bookingUnitPriceTable.No_of_Bags =unitpricemodel.No_of_Bags;
    bookingUnitPriceTable.Booking_Rate =unitpricemodel.Booking_Rate;
     bookingUnitPriceTable. Line_Discount_Percent=unitpricemodel.Line_Discount_Percent;
     bookingUnitPriceTable. Line_Discount_Amount=unitpricemodel.Line_Discount_Amount;

    bookingUnitPriceTable. Variety_Name=unitpricemodel.Variety_Name;
    bookingUnitPriceTable. Variety_Class_of_Seeds=unitpricemodel.Variety_Class_of_Seeds;
    bookingUnitPriceTable. Variety_Product_Group_Code=unitpricemodel.Variety_Product_Group_Code;
    bookingUnitPriceTable. Variety_Package_Size=unitpricemodel.Variety_Package_Size;
    bookingUnitPriceTable.App_No=unitpricemodel.App_No;


    return bookingUnitPriceTable;

    }
}
