package com.example.pristineseed.DataBaseRepository.GeographicalRepo;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pristineseed.model.item.HybridItemMasterModel;

import java.util.List;

@Dao
public interface HybridItemMasterDao {

    @Insert
    Long insert(Hybrid_Item_Table hybrid_item_table);
    @Insert
    void insert(List<Hybrid_Item_Table> hybrid_item_tableList);

    @Query("SELECT * FROM hybrid_item_table")
    List<Hybrid_Item_Table> fetchAllData();

    @Update
    void update(Hybrid_Item_Table hybrid_item_table);

    @Query("delete from hybrid_item_table")
    int deleteAllRecord();

    @Query("SELECT COUNT(1) FROM hybrid_item_table")
    int getRowCount();

    @Query("SELECT * FROM hybrid_item_table WHERE item_no=:no")
    boolean isDataExist(String no);

    @Query("SELECT * FROM hybrid_item_table WHERE Class_of_Seeds='Hybrid' and item_no not null and item_no not in('',' ')")
    List<Hybrid_Item_Table> getHybridAliasItem();

    @Query("SELECT * FROM hybrid_item_table WHERE Class_of_Seeds='Foundation' and lower(User_ID)=:user_id and Item_Name not null and Alias_Name not in ('',' ')")
    List<Hybrid_Item_Table> getFoundationItem(String user_id);

    @Query("SELECT * FROM hybrid_item_table WHERE Class_of_Seeds='Hybrid' and crop=:crop_code")
    List<Hybrid_Item_Table> getHybridItemForEvent(String crop_code);

    @Query("SELECT * FROM hybrid_item_table WHERE Class_of_Seeds='Hybrid'")
    List<Hybrid_Item_Table> getHybridItem();

    @Query(" SELECT * FROM hybrid_item_table " )  // todo remove all class of seed type for lot number and also removed document number of lot number , change lot number manual field in Visitor Remark  as per instructions by navision.(28-12-2021).
    List<Hybrid_Item_Table> getHybridItemLot();

    @Query("SELECT * FROM hybrid_item_table WHERE item_no=:variety_no")
    Hybrid_Item_Table getHybridItemNameByCode(String variety_no);

    @Query("SELECT * FROM hybrid_item_table WHERE Class_of_Seeds =:class_of_seed and crop=:crop_code and item_no not null and item_no not in ('',' ')")
    List<Hybrid_Item_Table> getVarietyItem(String class_of_seed,String crop_code);

    @Query("SELECT * FROM hybrid_item_table WHERE lower(User_ID) =:user_id and crop=:crop and crop not null and Item_Type=:item_type and Item_Type not null and Item_Type not in ('',' ') and item_no not null  and item_no not in ('',' ') and crop not in('',' ')")
    List<Hybrid_Item_Table> getVarietyItemDeatilsUserwise(String user_id,String crop,String item_type);

    @Query("SELECT * FROM hybrid_item_table WHERE lower(User_ID) =:user_id and crop=:crop and crop not null  and Class_of_Seeds=:classOfSeed and Class_of_Seeds not null  and Class_of_Seeds not in ('',' ') and crop not in('',' ')")
    Hybrid_Item_Table getDocProductGrpCode(String user_id,String crop,String classOfSeed);

    @Query("SELECT * FROM hybrid_item_table WHERE crop=:item_code")
    Hybrid_Item_Table getVarityGroup(String item_code);

    @Query("SELECT * FROM hybrid_item_table WHERE item_no=:item_code")
    Hybrid_Item_Table getHybridName(String item_code);

    @Query("SELECT * FROM hybrid_item_table WHERE  item_no=:parent_male")
    Hybrid_Item_Table getAliasNameParentMale(String parent_male);

    @Query("SELECT * FROM hybrid_item_table WHERE item_no=:parent_female")
    Hybrid_Item_Table getAliasNameParentFemale(String parent_female);


}
