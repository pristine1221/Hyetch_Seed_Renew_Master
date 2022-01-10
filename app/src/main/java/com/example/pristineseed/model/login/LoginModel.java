package com.example.pristineseed.model.login;

import java.util.List;

import retrofit2.http.PUT;

public class LoginModel {
    public String name;
    public String email;
    public String password;
    public String roleId;
    public boolean condition;
    public String message;
    public String locationId;
    public String location_name;
    public String user_type;
    public String approver_id;
    public String emp_id;
    public String empGender;
    public String user_app_inspection_type;
    public String empGrade;
    public String  approver_emp_id;
    public String  salespersoncode;

    public List<MenuModel> menu;

    public class MenuModel {
        public String id;
        public String title;
        public String translate;
        public String type;
        public String icon;
        public List<ChildrenModel> children;
    }

    public class ChildrenModel {
        public String id;
        public String title;
        public String translate;
        public String type;
        public String icon;
    }

}
