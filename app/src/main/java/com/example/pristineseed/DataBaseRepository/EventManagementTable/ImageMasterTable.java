package com.example.pristineseed.DataBaseRepository.EventManagementTable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "event_image_master_tabe")
public class ImageMasterTable {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @ColumnInfo(name = "code")
    private String code;
    @ColumnInfo(name = "image_url")
    private String image_url;
}
