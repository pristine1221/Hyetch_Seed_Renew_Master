package com.example.pristineseed.DataSyncingBackgraundProcess;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "syncMatserDataTable")
public class SyncMasterTable {

    @NonNull
    public String getAll_table_name() {
        return all_table_name;
    }

    public void setAll_table_name(@NonNull String all_table_name) {
        this.all_table_name = all_table_name;
    }

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "all_table_name")
    private String all_table_name;

    @ColumnInfo(name = "table_type")
    private String table_type;
    @ColumnInfo(name = "inReady")
    private int inReady;
    @ColumnInfo(name = "outReady")
    private int outReady;
    @ColumnInfo(name = "initLastDateTime")
    private String initLastDateTime;



    public String getTable_type() {
        return table_type;
    }

    public void setTable_type(String table_type) {
        this.table_type = table_type;
    }

    public int getInReady() {
        return inReady;
    }

    public void setInReady(int inReady) {
        this.inReady = inReady;
    }

    public int getOutReady() {
        return outReady;
    }

    public void setOutReady(int outReady) {
        this.outReady = outReady;
    }

    public String getInitLastDateTime() {
        return initLastDateTime;
    }

    public void setInitLastDateTime(String initLastDateTime) {
        this.initLastDateTime = initLastDateTime;
    }

    public String getOutLastDateTime() {
        return outLastDateTime;
    }

    public void setOutLastDateTime(String outLastDateTime) {
        this.outLastDateTime = outLastDateTime;
    }

    public String getUser_login() {
        return user_login;
    }

    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }

    @ColumnInfo(name = "outLastDateTime")
    private String outLastDateTime;
    @ColumnInfo(name = "user_login")
    private String user_login;


    public static SyncMasterTable insertMasterData(String All_table_name, String inLastDateTime){
        SyncMasterTable syncMasterTable =new SyncMasterTable();
        syncMasterTable.all_table_name=All_table_name;
        syncMasterTable.initLastDateTime=inLastDateTime;
        return syncMasterTable;
    }


}
