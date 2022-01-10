package com.example.pristineseed.SessionManageMent;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.pristineseed.global.AESUtils;

public class SessionManagement {
    SharedPreferences sp;

    public SessionManagement(Context context) {
        sp = context.getSharedPreferences("pristine_seeds", Context.MODE_PRIVATE);
    }

    //todo set session....
    public void setUserName(String userName) throws Exception {
        setSharedPreferences("userName", AESUtils.encrypt(userName));
    }

    public void setUserEmail(String userEmail) throws Exception {
        setSharedPreferences("userEmail", AESUtils.encrypt(userEmail));
    }

    public String getUserName() {
        return getDataFromSharedPreferences("userName");
    }

    public String getUserEmail() {
        return getDataFromSharedPreferences("userEmail");
    }

    public String getSelectedGroupMenuName() {
        return getDataFromSharedPreferences("groupMenuName");
    }

    public String getSelectedSubGroupMenuName() {
        return getDataFromSharedPreferences("subGroupMenuName");
    }

    public void setSelectedGroupMenuName(String groupMenuName) throws Exception {
        setSharedPreferences("groupMenuName", AESUtils.encrypt(groupMenuName));
    }

    public void setSelectedSubGroupMenuName(String subGroupMenuName) throws Exception {
        setSharedPreferences("subGroupMenuName", AESUtils.encrypt(subGroupMenuName));
    }

    private String getDataFromSharedPreferences(String Key) {
        try {
            String returnString = sp.getString(Key, null);
            return AESUtils.decrypt(returnString);
        } catch (Exception e) {
            return "";
        }
    }

    public String getMenu() {
        return getDataFromSharedPreferences("menu");
    }

    public void setMenu(String menu) throws Exception {
        setSharedPreferences("menu", AESUtils.encrypt(menu));
    }

    public void setApprover_id(String approver_id) throws Exception {
        setSharedPreferences("approver_id", AESUtils.encrypt(approver_id));
    }

    public String getUser_type() {
        return getDataFromSharedPreferences("user_type");
    }
    //todo set sessison

    public void setUser_type(String user_type) throws Exception {
        setSharedPreferences("user_type", AESUtils.encrypt(user_type));
    }

    public void setLastSync(String lastSync) throws Exception {
        setSharedPreferences("lastSync_", AESUtils.encrypt(lastSync));
    }


    public String getLastSync() {
        return getDataFromSharedPreferences("lastSync_");
    }


    public String getApprover_id() {
        return getDataFromSharedPreferences("approver_id");
    }


    public String getLastSession() {
        return getDataFromSharedPreferences("lastSession");
    }

    public void setLastSession(String lastSync) throws Exception {
        setSharedPreferences("lastSession", AESUtils.encrypt(lastSync));
    }


    private void setSharedPreferences(String key, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void ClearSession() {
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();

    }

    public void setemp_id(String userEmail) throws Exception {
        setSharedPreferences("emp_id", AESUtils.encrypt(userEmail));
    }

    public String getemp_id() {
        return getDataFromSharedPreferences("emp_id");
    }

    public void setuser_app_inspection_type(String user_app_inspection_type) throws Exception {
        setSharedPreferences("user_app_inspection_type", AESUtils.encrypt(user_app_inspection_type));
    }

    public String getuser_app_inspection_type() {
        return getDataFromSharedPreferences("user_app_inspection_type");
    }


    public void setUserPhone(String user_phone) throws Exception {
        setSharedPreferences("user_phone", AESUtils.encrypt(user_phone));
    }

    public String getUserPhone() {
        return getDataFromSharedPreferences("user_phone");
    }

    public void setCurrentLocation(String current_location) throws Exception {
        setSharedPreferences("current_location", AESUtils.encrypt(current_location));
    }

    public String getCurrentLocation() {
        return getDataFromSharedPreferences("current_location");
    }

    public void setCurrentCity(String current_city) throws Exception {
        setSharedPreferences("current_city", AESUtils.encrypt(current_city));
    }

    public String getCurrentCity() {
        return getDataFromSharedPreferences("current_city");
    }

    public void setCurrentState(String current_state) throws Exception {
        setSharedPreferences("current_state", AESUtils.encrypt(current_state));
    }

    public String getCurrentState() {
        return getDataFromSharedPreferences("current_state");
    }

    public void setAddress(String current_address) throws Exception {
        setSharedPreferences("current_address", AESUtils.encrypt(current_address));
    }

    public String getAddress() {
        return getDataFromSharedPreferences("current_address");
    }


    public void setTempF(String temp_f) throws Exception {
        setSharedPreferences("temp_f", AESUtils.encrypt(temp_f));
    }

    public String getTempF() {
        return getDataFromSharedPreferences("temp_f");
    }


    public void setRainFall(String rain_fall) throws Exception {
        setSharedPreferences("rain_fall", AESUtils.encrypt(rain_fall));
    }

    public String getRainFall() {
        return getDataFromSharedPreferences("rain_fall");
    }

    public void sethumidity(String humidity) throws Exception {
        setSharedPreferences("humidity", AESUtils.encrypt(humidity));
    }

    public String gethumidity() {
        return getDataFromSharedPreferences("humidity");
    }


    public void setwindSpeed(String windSpeed) throws Exception {
        setSharedPreferences("windSpeed", AESUtils.encrypt(windSpeed));
    }

    public String getwindSpeed() {
        return getDataFromSharedPreferences("windSpeed");
    }

    public void setweatherCondition(String weather_condition) throws Exception {
        setSharedPreferences("weather_condition", AESUtils.encrypt(weather_condition));
    }

    public String getweatherCondition() {
        return getDataFromSharedPreferences("weather_condition");
    }

    public void setFcmToken(String fcm_token) throws Exception {
        setSharedPreferences("fcm_token", AESUtils.encrypt(fcm_token));
    }

    public String getFcmToken() {
        return getDataFromSharedPreferences("fcm_token");
    }

    public void setPassword(String pass) throws Exception {
        setSharedPreferences("pass", AESUtils.encrypt(pass));
    }

    public String getPasword() {
        return getDataFromSharedPreferences("pass");
    }

    public void setattendance(String attendance) throws Exception {
        setSharedPreferences("is_running", AESUtils.encrypt(attendance));
    }

    public String getattendance() {
        return getDataFromSharedPreferences("is_running");
    }

    public void setGender(String empGender) throws Exception {
        setSharedPreferences("empGender", AESUtils.encrypt(empGender));
    }

    public String getEmpGender() {
        return getDataFromSharedPreferences("empGender");
    }

    public void setEmpGrade(String grade) throws Exception {
        setSharedPreferences("emp_grade", AESUtils.encrypt(grade));
    }

    public String getEmpGrade() {
        return getDataFromSharedPreferences("emp_grade");
    }

    public void setSalePersonCode(String salePersonCode) throws Exception {
        setSharedPreferences("sale_person_code", AESUtils.encrypt(salePersonCode));
    }

    public String getSalePersonCode() {
        return getDataFromSharedPreferences("sale_person_code");
    }

}

