package com.example.pristineseed.DataBaseRepository.GeographicalRepo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pristineseed.model.item.UnitOfMeasureModel;
    @Entity(tableName = "uom_table")
    public class UomTable {
        @PrimaryKey(autoGenerate = true)
        private int android_id;

        @ColumnInfo(name = "Code")
        private String Code;

        @ColumnInfo(name = "Description")
        private String Description;

        public int getAndroid_id() {
            return android_id;
        }

        public void setAndroid_id(int android_id) {
            this.android_id = android_id;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String code) {
            Code = code;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public static UomTable insertUOMData(UnitOfMeasureModel.UnitOfMeasureModelList unitOfMeasureModel){
            UomTable uomTable = new UomTable();
            uomTable.Code= unitOfMeasureModel.Code;
            uomTable.Description=unitOfMeasureModel.Description;
            return uomTable;
        }

}
