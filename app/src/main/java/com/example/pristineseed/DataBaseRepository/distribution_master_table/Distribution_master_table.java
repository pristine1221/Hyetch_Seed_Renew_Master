package com.example.pristineseed.DataBaseRepository.distribution_master_table;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pristineseed.model.distributor_master.DistributorListModel;

@Entity(tableName = "distribution_master_table")
public class Distribution_master_table {
   // @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "android_id")
    private int android_id;

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "distributor_code")
    private String distributor_code;
    @ColumnInfo(name = "Distributor_name")
    private String Distributor_name;
    @ColumnInfo(name = "doa")
    private String doa;
    @ColumnInfo(name = "forward_by_TSE_TSM_DSM_ASM")
    private String forward_by_TSE_TSM_DSM_ASM;
    @ColumnInfo(name = "contact_person")
    private String contact_person;
    @ColumnInfo(name = "phone_no")
    private String phone_no;
    @ColumnInfo(name = "mobile_no")
    private String mobile_no;
    @ColumnInfo(name = "address")
    private String address;
    @ColumnInfo(name = "place")
    private String place;
    @ColumnInfo(name = "pincode")
    private String pincode;
    @ColumnInfo(name = "state")
    private String state;
    @ColumnInfo(name = "zone")
    private String zone;
    @ColumnInfo(name = "district")
    private String district;
    @ColumnInfo(name = "taluka")
    private String taluka;
    @ColumnInfo(name = "gstin")
    private String gstin;
    @ColumnInfo(name = "pan_or_adhar")
    private String pan_or_adhar;
    @ColumnInfo(name = "sd")
    private int sd;
    @ColumnInfo(name = "sd_chq_no")
    private String sd_chq_no;
    @ColumnInfo(name = "s_chqs")
    private String s_chqs;
    @ColumnInfo(name = "gst_registration_no")
    private String gst_registration_no;
    @ColumnInfo(name = "bank")
    private String bank;
    @ColumnInfo(name = "seed_license")
    private String seed_license;
    @ColumnInfo(name = "validity")
    private String validity;
    @ColumnInfo(name = "file_no")
    private String file_no;

    public int getAndroid_id() {
        return android_id;
    }

    public void setAndroid_id(int android_id) {
        this.android_id = android_id;
    }

    public String getDistributor_code() {
        return distributor_code;
    }

    public void setDistributor_code(String distributor_code) {
        this.distributor_code = distributor_code;
    }

    public String getDistributor_name() {
        return Distributor_name;
    }

    public void setDistributor_name(String distributor_name) {
        Distributor_name = distributor_name;
    }

    public String getDoa() {
        return doa;
    }

    public void setDoa(String doa) {
        this.doa = doa;
    }

    public String getForward_by_TSE_TSM_DSM_ASM() {
        return forward_by_TSE_TSM_DSM_ASM;
    }

    public void setForward_by_TSE_TSM_DSM_ASM(String forward_by_TSE_TSM_DSM_ASM) {
        this.forward_by_TSE_TSM_DSM_ASM = forward_by_TSE_TSM_DSM_ASM;
    }

    public String getContact_person() {
        return contact_person;
    }

    public void setContact_person(String contact_person) {
        this.contact_person = contact_person;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTaluka() {
        return taluka;
    }

    public void setTaluka(String taluka) {
        this.taluka = taluka;
    }

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public String getPan_or_adhar() {
        return pan_or_adhar;
    }

    public void setPan_or_adhar(String pan_or_adhar) {
        this.pan_or_adhar = pan_or_adhar;
    }

    public int getSd() {
        return sd;
    }

    public void setSd(int sd) {
        this.sd = sd;
    }

    public String getSd_chq_no() {
        return sd_chq_no;
    }

    public void setSd_chq_no(String sd_chq_no) {
        this.sd_chq_no = sd_chq_no;
    }

    public String getS_chqs() {
        return s_chqs;
    }

    public void setS_chqs(String s_chqs) {
        this.s_chqs = s_chqs;
    }

    public String getGst_registration_no() {
        return gst_registration_no;
    }

    public void setGst_registration_no(String gst_registration_no) {
        this.gst_registration_no = gst_registration_no;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getSeed_license() {
        return seed_license;
    }

    public void setSeed_license(String seed_license) {
        this.seed_license = seed_license;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getFile_no() {
        return file_no;
    }

    public void setFile_no(String file_no) {
        this.file_no = file_no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public String getNav_sync() {
        return nav_sync;
    }

    public void setNav_sync(String nav_sync) {
        this.nav_sync = nav_sync;
    }

    public String getNav_error() {
        return nav_error;
    }

    public void setNav_error(String nav_error) {
        this.nav_error = nav_error;
    }

    public String getNav_count() {
        return nav_count;
    }

    public void setNav_count(String nav_count) {
        this.nav_count = nav_count;
    }

    @ColumnInfo(name = "status")
    private String status;
    @ColumnInfo(name = "code")
    private String code;
    @ColumnInfo(name = "remarks")
    private String remarks;
    @ColumnInfo(name = "created_by")
    private String created_by;
    @ColumnInfo(name = "created_on")
    private String created_on;
    @ColumnInfo(name = "nav_sync")
    private String nav_sync;
    @ColumnInfo(name = "nav_error")
    private String nav_error;
    @ColumnInfo(name = "nav_count")
    private String nav_count;

    public String getIsType() {
        return isType;
    }

    public void setIsType(String isType) {
        this.isType = isType;
    }

    @ColumnInfo(name = "istype")
    private String isType;

    public String getAadhaar_no() {
        return aadhaar_no;
    }

    public void setAadhaar_no(String aadhaar_no) {
        this.aadhaar_no = aadhaar_no;
    }

    @ColumnInfo(name = "aadhaar_no")
    private String aadhaar_no;

    @ColumnInfo(name = "gst_type")
    private String gst_type;

    public String getGst_type() {
        return gst_type;
    }

    public void setGst_type(String gst_type) {
        this.gst_type = gst_type;
    }

    public String getTotal_land() {
        return total_land;
    }

    public void setTotal_land(String total_land) {
        this.total_land = total_land;
    }

    @ColumnInfo(name = "total_land")
    private String total_land;

    public static Distribution_master_table getDataFromServer(DistributorListModel distributor_master_model){
        Distribution_master_table distribution_master_table=new Distribution_master_table();

     distribution_master_table.setDistributor_code(distributor_master_model.distributor_code);
     distribution_master_table.setDistributor_name(distributor_master_model.distributor_name);
     distribution_master_table.setDoa(distributor_master_model.date_of_joining);
     distribution_master_table.setForward_by_TSE_TSM_DSM_ASM(distributor_master_model.forward_by);
     distribution_master_table.setContact_person(distributor_master_model.contact);
     distribution_master_table.setPhone_no(distributor_master_model.phone_no);
     distribution_master_table.setMobile_no(distributor_master_model.mobile);
     distribution_master_table.setAddress(distributor_master_model.address);
     distribution_master_table.setPlace(distributor_master_model.place);
     distribution_master_table.setPincode(distributor_master_model.postcode);
     distribution_master_table.setState(distributor_master_model.state);
     distribution_master_table.setZone(distributor_master_model.zone);
     distribution_master_table.setDistrict(distributor_master_model.district);
     distribution_master_table.setTaluka(distributor_master_model.taluka);
     distribution_master_table.setGstin(distributor_master_model.gst_number);
     distribution_master_table.setPan_or_adhar(distributor_master_model.pan_no);
     distribution_master_table.setSd(distributor_master_model.security_deposit);
     distribution_master_table.setSd_chq_no(distributor_master_model.security_deposit_chq_no);
     distribution_master_table.setS_chqs(distributor_master_model.security_deposit_chqs);
     distribution_master_table.setGst_registration_no(distributor_master_model.gst_registration_no);
     distribution_master_table.setBank(distributor_master_model.bank);
     distribution_master_table.setSeed_license(distributor_master_model.seed_license);
     distribution_master_table.setValidity(distributor_master_model.validity);
     distribution_master_table.setFile_no(distributor_master_model.file_no);
     distribution_master_table.setStatus(distributor_master_model.status);
     distribution_master_table.setCode(distributor_master_model.code);
     distribution_master_table.setRemarks(distributor_master_model.remarks);
     distribution_master_table.setCreated_by(distributor_master_model.created_by);
     distribution_master_table.setCreated_on(distributor_master_model.created_on);
     distribution_master_table.setNav_sync(distributor_master_model.nav_sync);
     distribution_master_table.setNav_error(distributor_master_model.nav_error);
     distribution_master_table.setNav_count(distributor_master_model.nav_count);
     distribution_master_table.setIsType(distributor_master_model.isType);
     distribution_master_table.setAadhaar_no(distributor_master_model.aadhaar_no);
     distribution_master_table.setTotal_land(distributor_master_model.total_land);
     distribution_master_table.setGst_type(distributor_master_model.gst_type);

     return distribution_master_table;
    }
}
