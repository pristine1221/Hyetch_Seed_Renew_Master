package com.example.pristineseed.DataBaseRepository.GeographicalRepo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.pristineseed.model.item.HybridItemMasterModel;

@Entity(tableName = "hybrid_item_table")
public class    Hybrid_Item_Table {

    public int getAndroid_id() {
        return android_id;
    }

    public void setAndroid_id(int android_id) {
        this.android_id = android_id;
    }

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int android_id;

    @ColumnInfo(name = "item_no")
    private String No;

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }

    public String getItem_Name() {
        return Item_Name;
    }

    public void setItem_Name(String item_Name) {
        Item_Name = item_Name;
    }

    @ColumnInfo(name = "Item_Name")
    private String Item_Name;

    public String getDescription_2() {
        return Description_2;
    }

    public void setDescription_2(String description_2) {
        Description_2 = description_2;
    }

    @ColumnInfo(name = "Alias_Name")
    private String Alias_Name;

    public String getAlias_Name() {
        return Alias_Name;
    }

    public void setAlias_Name(String alias_Name) {
        Alias_Name = alias_Name;
    }

    public String getClass_of_Seeds() {
        return Class_of_Seeds;
    }

    public void setClass_of_Seeds(String class_of_Seeds) {
        Class_of_Seeds = class_of_Seeds;
    }

    @Ignore
    @ColumnInfo(name = "Description_2")
    private String Description_2;


    @ColumnInfo(name = "Class_of_Seeds")
    private String Class_of_Seeds;

    public String getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(String user_ID) {
        User_ID = user_ID;
    }

    @ColumnInfo(name = "User_ID")
    private String User_ID;


    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    @ColumnInfo(name = "crop")
    private String crop;

    public String getItem_Product_Group_Code() {
        return Item_Product_Group_Code;
    }

    public void setItem_Product_Group_Code(String item_Product_Group_Code) {
        Item_Product_Group_Code = item_Product_Group_Code;
    }

    @ColumnInfo(name = "Item_Product_Group_Code")
    private String Item_Product_Group_Code;

    public String getItem_Group() {
        return Item_Group;
    }

    public void setItem_Group(String item_Group) {
        Item_Group = item_Group;
    }

    public String getItem_FG_Pack_Size() {
        return Item_FG_Pack_Size;
    }

    public void setItem_FG_Pack_Size(String item_FG_Pack_Size) {
        Item_FG_Pack_Size = item_FG_Pack_Size;
    }

    @ColumnInfo(name = "Item_Group")
    private String Item_Group;

    @ColumnInfo(name = "Item_FG_Pack_Size")
    private String Item_FG_Pack_Size;

    public String getItem_Type() {
        return Item_Type;
    }

    public void setItem_Type(String item_Type) {
        Item_Type = item_Type;
    }

    @ColumnInfo(name = "Item_Type")
    private String Item_Type;

    public static Hybrid_Item_Table insertHybridItem(HybridItemMasterModel.Data hybridItemMasterModel) {

        Hybrid_Item_Table hybrid_item_table = new Hybrid_Item_Table();
        hybrid_item_table.No = hybridItemMasterModel.Item_No;
        hybrid_item_table.Item_Name = hybridItemMasterModel.Item_Name;
        hybrid_item_table.Description_2 = hybridItemMasterModel.Description_2;
        hybrid_item_table.Alias_Name = hybridItemMasterModel.Item_Alias_Name;
        hybrid_item_table.Class_of_Seeds = hybridItemMasterModel.Item_Class_of_Seeds;
        hybrid_item_table.User_ID = hybridItemMasterModel.User_ID;
        hybrid_item_table.crop = hybridItemMasterModel.Item_Crop_Code;
        hybrid_item_table.Item_Product_Group_Code = hybridItemMasterModel.Item_Product_Group_Code;
        hybrid_item_table.Item_Group = hybridItemMasterModel.Item_Group;
        hybrid_item_table.Item_FG_Pack_Size = hybridItemMasterModel.Item_FG_Pack_Size;
        hybrid_item_table.Item_Type = hybridItemMasterModel.Item_Type;

        return hybrid_item_table;
    }
}
