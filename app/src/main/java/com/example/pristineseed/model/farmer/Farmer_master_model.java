package com.example.pristineseed.model.farmer;

public class Farmer_master_model {

    //todo take the variable
    public boolean Condition;
    public String message;
    public String distributor_code;
    public String  Distributor_name;
    public String doa;
    public String forward_by_TSE_TSM_DSM_ASM;
    public String contact_person;
    public String phone_no;
    public String mobile_no;
    public String address;
    public String place;
    public String pincode;
    public String state;
    public String zone;
    public String district;
    public String taluka;
    public String gstin;
    public String pan_or_adhar;
    public String total_land;
    public String crop;
    public String hybrid;
    public String sd;
    public String sd_chq_no;
    public String s_chqs;
    public String gst_registration_no;
    public String bank;
    public String seed_license;
    public String validity;
    public String file_no;
    public String status;
    public String code;
    public String remarks;
    public String created_by;
    public String created_on;
    public String nav_sync;
    public String nav_error;
    public String nav_count;
    public String isType;


    //todo set and get value for each variable

    public boolean isCondition() { return Condition; }
    public void setCondition(boolean Condition) { this.Condition = Condition; }

    public String getMessage() { return message;}
    public void setMessage(String message) { this.message = message; }

    public String getDistributor_code() { return distributor_code; }
    public void setDistributor_code(String distributor_code) { this.distributor_code = distributor_code; }

    public String getDistributor_name() { return Distributor_name; }
    public void setDistributor_name(String Distributor_name) { this.Distributor_name = Distributor_name; }

    public String getDoa() { return doa; }
    public void setDoa(String doa) { this.doa = doa; }


    public String getForward_by_TSE_TSM_DSM_ASM() { return forward_by_TSE_TSM_DSM_ASM; }
    public void setForward_by_TSE_TSM_DSM_ASM(String forward_by_TSE_TSM_DSM_ASM) { this.forward_by_TSE_TSM_DSM_ASM = forward_by_TSE_TSM_DSM_ASM; }

    public String getContact_person() { return contact_person; }
    public void setContact_person(String contact_person) { this.contact_person = contact_person; }

    public String getPhone_no() { return phone_no; }
    public void setPhone_no(String phone_no) { this.phone_no = phone_no; }

    public String getMobile_no() { return mobile_no; }
    public void setMobile_no(String mobile_no) { this.mobile_no = mobile_no; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPincode() { return pincode; }

    public void setPincode(String pincode) { this.pincode = pincode; }

    public String getZone() { return zone; }
    public void setZone(String zone) { this.zone = zone; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getTaluka() { return taluka; }

    public void setTaluka(String taluka) { this.taluka = taluka; }

    public String getGstin() { return gstin; }
    public void setGstin(String gstin) { this.gstin = gstin; }

    public String getPan_or_adhar() { return pan_or_adhar; }
    public void setPan_or_adhar(String pan_or_adhar) { this.pan_or_adhar = pan_or_adhar; }

    public String getTotal_land() { return total_land; }
    public void setTotal_land(String total_land) { this.total_land = total_land; }

    public String getCrop() { return crop;}
    public void setCrop(String crop) { this.crop = crop; }

    public String getHybrid() { return hybrid; }
    public void setHybrid(String hybrid) { this.hybrid = hybrid; }

    public String getPlace() { return place; }
    public void setPlace(String place) { this.place = place; }

    public String getBank() { return bank; }
    public void setBank(String bank) { this.bank = bank; }

    public String getSd() { return sd; }
    public void setSd(String sd) { this.sd = sd; }

    public String getSd_chq_no() { return sd_chq_no; }
    public void setSd_chq_no(String sd_chq_no) { this.sd_chq_no = sd_chq_no; }

    public String getS_chqs() { return s_chqs; }
    public void setS_chqs(String s_chqs) { this.s_chqs = s_chqs; }

    public String getGst_registration_no() { return gst_registration_no; }
    public void setGst_registration_no(String gst_registration_no) { this.gst_registration_no = gst_registration_no; }

    public String getSeed_license() { return seed_license; }
    public void setSeed_license(String seed_license) { this.seed_license = seed_license; }

    public String getValidity() { return validity; }
    public void setValidity(String validity) { this.validity = validity; }

    public String getFile_no() { return file_no; }
    public void setFile_no(String file_no) { this.file_no = file_no; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getCreated_by() { return created_by; }
    public void setCreated_by(String created_by) { this.created_by = created_by; }

    public String getCreated_on() { return created_on; }
    public void setCreated_on(String created_on) { this.created_on = created_on; }

    public String getNav_count() { return nav_count; }
    public void setNav_count(String nav_count) { this.nav_count = nav_count; }

    public String getNav_error() { return nav_error; }
    public void setNav_error(String nav_error) { this.nav_error = nav_error; }

    public String getNav_sync() { return nav_sync; }
    public void setNav_sync(String nav_sync) { this.nav_sync = nav_sync; }

    public String getIsType() { return isType; }
    public void setIsType(String isType) { this.isType = isType; }

    //todo now create parameter constructor
    public String title;
    public String subtitle;
    public String subtitle2;
    public String datetime;

    public Farmer_master_model(String title, String subtitle, String subtitle2, String datetime){
        this.title= title;
        this.subtitle = subtitle;
        this.subtitle2 = subtitle2;
        this.datetime = datetime;
    }
}
