package com.example.pristineseed.DataSyncingBackgraundProcess;

public class SyncMasterData {
    public String All_table_name;
    public int inReady;
    public String inLastDateTime;
    public int outReady;
    public String outLastDateTime;

    public SyncMasterData(String All_table_name,int inReady, String inLastDateTime, int outReady, String outLastDateTime) {
        this.All_table_name = All_table_name;
        this.inReady = inReady;
        this.inLastDateTime = inLastDateTime;
        this.outReady = outReady;
        this.outLastDateTime = outLastDateTime;
    }
}
