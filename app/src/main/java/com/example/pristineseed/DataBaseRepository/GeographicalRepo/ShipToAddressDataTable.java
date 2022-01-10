package com.example.pristineseed.DataBaseRepository.GeographicalRepo;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pristineseed.model.BookingOrder.ShipToAddressModel;

@Entity(tableName = "ship_to_address_table")
public class ShipToAddressDataTable  {

    @NonNull
 @PrimaryKey(autoGenerate = true)
 private int android_id;

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

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getAddress_2() {
        return Address_2;
    }

    public void setAddress_2(String address_2) {
        Address_2 = address_2;
    }

    public String getPost_Code() {
        return Post_Code;
    }

    public void setPost_Code(String post_Code) {
        Post_Code = post_Code;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCountry_Region_Code() {
        return Country_Region_Code;
    }

    public void setCountry_Region_Code(String country_Region_Code) {
        Country_Region_Code = country_Region_Code;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getPhone_No() {
        return Phone_No;
    }

    public void setPhone_No(String phone_No) {
        Phone_No = phone_No;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getLocation_Code() {
        return Location_Code;
    }

    public void setLocation_Code(String location_Code) {
        Location_Code = location_Code;
    }

    public String getShipment_Method_Code() {
        return Shipment_Method_Code;
    }

    public void setShipment_Method_Code(String shipment_Method_Code) {
        Shipment_Method_Code = shipment_Method_Code;
    }

    public String getShipping_Agent_Code() {
        return Shipping_Agent_Code;
    }

    public void setShipping_Agent_Code(String shipping_Agent_Code) {
        Shipping_Agent_Code = shipping_Agent_Code;
    }

    public String getShipping_Agent_Service_Code() {
        return Shipping_Agent_Service_Code;
    }

    public void setShipping_Agent_Service_Code(String shipping_Agent_Service_Code) {
        Shipping_Agent_Service_Code = shipping_Agent_Service_Code;
    }

    public String getService_Zone_Code() {
        return Service_Zone_Code;
    }

    public void setService_Zone_Code(String service_Zone_Code) {
        Service_Zone_Code = service_Zone_Code;
    }

    public String getLast_Date_Modified() {
        return Last_Date_Modified;
    }

    public void setLast_Date_Modified(String last_Date_Modified) {
        Last_Date_Modified = last_Date_Modified;
    }

    public String getGST_Registration_No() {
        return GST_Registration_No;
    }

    public void setGST_Registration_No(String GST_Registration_No) {
        this.GST_Registration_No = GST_Registration_No;
    }

    public String getConsignee() {
        return Consignee;
    }

    public void setConsignee(String consignee) {
        Consignee = consignee;
    }

    public String getARN_No() {
        return ARN_No;
    }

    public void setARN_No(String ARN_No) {
        this.ARN_No = ARN_No;
    }

    public String getFax_No() {
        return Fax_No;
    }

    public void setFax_No(String fax_No) {
        Fax_No = fax_No;
    }

    public String getE_Mail() {
        return E_Mail;
    }

    public void setE_Mail(String e_Mail) {
        E_Mail = e_Mail;
    }

    public String getHome_Page() {
        return Home_Page;
    }

    public void setHome_Page(String home_Page) {
        Home_Page = home_Page;
    }

    public String getCreated_By() {
        return Created_By;
    }

    public void setCreated_By(String created_By) {
        Created_By = created_By;
    }

    public String getCreation_Type() {
        return Creation_Type;
    }

    public void setCreation_Type(String creation_Type) {
        Creation_Type = creation_Type;
    }

    @ColumnInfo(name = "Customer_No")
   private String Customer_No;
    @ColumnInfo(name = "Code")
    private String Code;
    @ColumnInfo(name = "Name")
    private String Name;
    @ColumnInfo(name = "Address")
    private String Address;
    @ColumnInfo(name = "Address_2")
    private String Address_2;
    @ColumnInfo(name = "Post_Code")
    private String Post_Code;
    @ColumnInfo(name = "City")
    private String City;
    @ColumnInfo(name = "Country_Region_Code")
    private String Country_Region_Code;
    @ColumnInfo(name = "State")
    private String State;
    @ColumnInfo(name = "Phone_No")
    private String Phone_No;
    @ColumnInfo(name = "Contact")
    private String Contact;
    @ColumnInfo(name = "Location_Code")
    private String Location_Code;
    @ColumnInfo(name = "Shipment_Method_Code")
    private String Shipment_Method_Code;
    @ColumnInfo(name = "Shipping_Agent_Code")
    private String Shipping_Agent_Code;
    @ColumnInfo(name = "Shipping_Agent_Service_Code")
    private String Shipping_Agent_Service_Code;
    @ColumnInfo(name = "Service_Zone_Code")
    private String Service_Zone_Code;
    @ColumnInfo(name = "Last_Date_Modified")
    private String Last_Date_Modified;
    @ColumnInfo(name = "GST_Registration_No")
    private String GST_Registration_No;
    @ColumnInfo(name = "Consignee")
    private String Consignee;
    @ColumnInfo(name = "ARN_No")
    private String ARN_No;
    @ColumnInfo(name = "Fax_No")
    private String Fax_No;
    @ColumnInfo(name = "E_Mail")
    private String E_Mail;
    @ColumnInfo(name = "Home_Page")
    private String Home_Page;
    @ColumnInfo(name = "Created_By")
    private String Created_By;
    @ColumnInfo(name = "Creation_Type")
    private String Creation_Type;



  public static ShipToAddressDataTable insertShipToAddressData(ShipToAddressModel.Data shiptoaddress_model){
      ShipToAddressDataTable shipToAddressDataTable=new ShipToAddressDataTable();

shipToAddressDataTable.Customer_No =shiptoaddress_model. Customer_No;
shipToAddressDataTable.Code =shiptoaddress_model. Code;
shipToAddressDataTable.Name =shiptoaddress_model. Name;
shipToAddressDataTable.Address =shiptoaddress_model. Address;
shipToAddressDataTable.Address_2 =shiptoaddress_model. Address_2;
shipToAddressDataTable.Post_Code =shiptoaddress_model. Post_Code;
shipToAddressDataTable.City =shiptoaddress_model. City;
shipToAddressDataTable.Country_Region_Code =shiptoaddress_model. Country_Region_Code;
shipToAddressDataTable.State =shiptoaddress_model. State;
shipToAddressDataTable.Phone_No =shiptoaddress_model. Phone_No;
shipToAddressDataTable.Contact =shiptoaddress_model. Contact;
shipToAddressDataTable.Location_Code =shiptoaddress_model. Location_Code;
shipToAddressDataTable.Shipment_Method_Code =shiptoaddress_model. Shipment_Method_Code;
shipToAddressDataTable.Shipping_Agent_Code =shiptoaddress_model. Shipping_Agent_Code;
shipToAddressDataTable.Shipping_Agent_Service_Code =shiptoaddress_model. Shipping_Agent_Service_Code;
shipToAddressDataTable.Service_Zone_Code =shiptoaddress_model. Service_Zone_Code;
shipToAddressDataTable.Last_Date_Modified =shiptoaddress_model. Last_Date_Modified;
shipToAddressDataTable.GST_Registration_No =shiptoaddress_model. GST_Registration_No;
shipToAddressDataTable.Consignee =shiptoaddress_model. Consignee;
shipToAddressDataTable.ARN_No =shiptoaddress_model. ARN_No;
shipToAddressDataTable.Fax_No =shiptoaddress_model. Fax_No;
shipToAddressDataTable.E_Mail =shiptoaddress_model. E_Mail;
shipToAddressDataTable.Home_Page =shiptoaddress_model. Home_Page;
shipToAddressDataTable.Created_By =shiptoaddress_model. Created_By;
shipToAddressDataTable.Creation_Type =shiptoaddress_model. Creation_Type;

return shipToAddressDataTable;


  }
}
