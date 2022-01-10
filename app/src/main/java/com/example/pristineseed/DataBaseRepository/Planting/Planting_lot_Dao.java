package com.example.pristineseed.DataBaseRepository.Planting;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Planting_lot_Dao {


    @Insert
    Long insert(Planting_Lot_master_Table planting_lot_master_table);

    @Insert
    void insertList(List<Planting_Lot_master_Table> plantingLotMasterTableList);

    @Query("SELECT * FROM planting_lot_master_table ORDER BY Document_No DESC")
    List<Planting_Lot_master_Table> getAllData();

    @Update
    void update(Planting_Lot_master_Table plantingFSIO_bsio_table);

    @Query("delete from planting_lot_master_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(1) FROM planting_lot_master_table")
    int getRowCount();

    @Query("SELECT 1 FROM planting_lot_master_table WHERE Document_No = :no")
    boolean isDataExist(String no);


    @Query("SELECT * FROM planting_lot_master_table WHERE Male_Female_Other='Male' and Document_SubType=:doc_type OR Document_SubType=:doc_type1")
        List<Planting_Lot_master_Table> getPlantingLotListOfMaleBulk(String doc_type,String doc_type1);

    @Query("SELECT * FROM planting_lot_master_table WHERE Male_Female_Other='Male' and Document_SubType=:documenT_subType")
    List<Planting_Lot_master_Table> getPlantingLotListOfMale_byDocumentType(String documenT_subType);

    @Query("SELECT * FROM planting_lot_master_table WHERE Male_Female_Other='Female' and Document_SubType=:documenT_subType")
    List<Planting_Lot_master_Table> getPlantingLotListOfFemale(String documenT_subType);


    @Query("SELECT * FROM planting_lot_master_table WHERE Male_Female_Other='Female' and Document_SubType=:document_subType")
    List<Planting_Lot_master_Table> getPlantingLotListOfFemale_byDocumentType(String document_subType);

    @Query("SELECT * FROM planting_lot_master_table WHERE Male_Female_Other='' and Male_Female_Other='Other' and Document_SubType=:document_subType")
    List<Planting_Lot_master_Table> getPlantingLotListOfOther_byDocumentType(String document_subType);


    @Query("SELECT * FROM planting_lot_master_table WHERE  Male_Female_Other=:Male_female_other_Type and Document_No=:Document_no")
    List<Planting_Lot_master_Table> getPlantingParentDetail(String Male_female_other_Type,String Document_no);

}
