package com.example.pristineseed.DataBaseRepository.GeographicalRepo;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PlantingLineLotListDao {

    @Insert
    Long insert(PlantingLineLotListTable plantingLineLotListTable);
    @Insert
    void insert(List<PlantingLineLotListTable> plantingLineLotListTableList );

    @Insert
      List<Long> insertList(List<PlantingLineLotListTable> plantingLineLotListTableList );

    @Query("SELECT * FROM planting_line_lot_table WHERE production_Lot_No not null and production_Lot_No not in('',' ') ")
    List<PlantingLineLotListTable> fetchAllData();

    @Query("SELECT * FROM planting_line_lot_table WHERE production_Lot_No  like :filter_lot_num ")
    List<PlantingLineLotListTable> fetchFilterAllData(String filter_lot_num);

    @Query("SELECT * FROM planting_line_lot_table WHERE production_Lot_No  like :filter_lot_num LIMIT 10 ")
    List<PlantingLineLotListTable> fetchFilter10AllData(String filter_lot_num);
    @Update
    void update(PlantingLineLotListTable plantingLineLotListTable);

    @Query("delete from planting_line_lot_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(1) FROM planting_line_lot_table")
    int getRowCount();

    @Query("SELECT * FROM planting_line_lot_table WHERE  plD_Error='False' and document_SubType=:doc_type and organizer_Code=:org_code")/*and variety_Code=:varity_code */
    List<PlantingLineLotListTable> fetchLineLotData(String doc_type,String org_code);/*String varity_code,posted='True' and*/

    @Query("SELECT COUNT(production_Lot_No) from planting_line_lot_table WHERE  production_Lot_No=:LotNo and plD_Error='True'")
    int checkPldMark(String LotNo);

    @Query("SELECT * from planting_line_lot_table WHERE  production_Lot_No=:production_Lot_No and variety_Code=:varity_code")
    PlantingLineLotListTable  getVaityCodeAliasName(String production_Lot_No, String varity_code);

    @Query("SELECT * from planting_line_lot_table WHERE  production_Lot_No=:production_Lot_No")
    PlantingLineLotListTable  getSowingAcre(String production_Lot_No);



    @Query("SELECT * from planting_line_lot_table WHERE production_Lot_No=:production_lot_no ") /*and crop_Code='Corn' OR crop_Code='Millet' OR crop_Code='SSG' OR crop_Code='Mustard' OR crop_Code='F.Sorghum'*/
    PlantingLineLotListTable getFemaleSowingDate(String production_lot_no);

    @Query("SELECT * from planting_line_lot_table WHERE production_Lot_No=:production_lot_no ") /*and crop_Code='Corn' OR crop_Code='Millet' OR crop_Code='SSG' OR crop_Code='Mustard' OR crop_Code='F.Sorghum'*/
    PlantingLineLotListTable getAllDetail(String production_lot_no);

}
