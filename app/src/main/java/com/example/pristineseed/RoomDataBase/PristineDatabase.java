package com.example.pristineseed.RoomDataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.pristineseed.DataBaseRepository.EmployeeAttendance.EmployeeAttendanceDao;
import com.example.pristineseed.DataBaseRepository.EmployeeAttendance.EmployeeAttendanceTable;
import com.example.pristineseed.DataBaseRepository.EventManagementTable.EventManagemantTable;
import com.example.pristineseed.DataBaseRepository.EventManagementTable.EventManagementExpenseLineTable;
import com.example.pristineseed.DataBaseRepository.EventManagementTable.EventManagmentDao;
import com.example.pristineseed.DataBaseRepository.EventManagementTable.EventManagmentExpanceLineDao;
import com.example.pristineseed.DataBaseRepository.EventManagementTable.EventTypeMasterDao;
import com.example.pristineseed.DataBaseRepository.EventManagementTable.EventTypeMasterTable;
import com.example.pristineseed.DataBaseRepository.EventManagementTable.ImageMasterDao;
import com.example.pristineseed.DataBaseRepository.EventManagementTable.ImageMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.AreaMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.BankMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.BankMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.BookingUnitPriceDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.BookingUnitPriceTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.CropHytechMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.CropHytechMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.DistricMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.DistricMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.FsioBsioSaleOrderNoDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.FsioBsioSaleOrderNoTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.GeoghraphicalTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.GeographicDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.HybridItemMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.Hybrid_Item_Table;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.RegionMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.RegionMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.RoleMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.RoleMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.ShipToAddressDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.ShipToAddressDataTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.StateMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.StateMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.TalukaMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.TalukaMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.LocationMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.UOMDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.UomTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.ZoneMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.ZoneMaterDao;
import com.example.pristineseed.DataBaseRepository.Planting.PlantingDetailHeaderTable;
import com.example.pristineseed.DataBaseRepository.Planting.PlantingFSIO_BSIO_Table;
import com.example.pristineseed.DataBaseRepository.Planting.PlantingHeaderDao;
import com.example.pristineseed.DataBaseRepository.Planting.PlantingLineDao;
import com.example.pristineseed.DataBaseRepository.Planting.PlantingLineTable;
import com.example.pristineseed.DataBaseRepository.Planting.Planting_Lot_master_Table;
import com.example.pristineseed.DataBaseRepository.Planting.Planting_fsio_bsio_Dao;
import com.example.pristineseed.DataBaseRepository.Planting.Planting_lot_Dao;
import com.example.pristineseed.DataBaseRepository.Planting.SeasonDao;
import com.example.pristineseed.DataBaseRepository.Planting.SeasonMasterTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.FloweringInspectionTable.FloweringInspectionDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.FloweringInspectionTable.FloweringInspectionTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.GerminationInspection1_Table;
import com.example.pristineseed.DataBaseRepository.Scheduler.GerminationInspectionDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.HarvestingInspection.HarvestingInspectionDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.HarvestingInspection.HarvestingInspectionTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.MaturityInspection.MaturityInspectionDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.MaturityInspection.MaturityInspectionTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.NickingInpection.Nickin2InspectionDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.NickingInpection.Nicking2InspectionTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.NickingInpection.NickingInspInsertDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.NickingInpection.NickingInspectionTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.PostFloweringInspection.PostFloweringDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.PostFloweringInspection.PostfloweringInspectionTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.Qc_Inspection.QcInspectionDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.Qc_Inspection.QcInspectionTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.ScheduleInspectionHeaderDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.ScheduleInspectionLineDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.SchedulerInspectionLineTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.Scheduler_Header_Table;
import com.example.pristineseed.DataBaseRepository.Scheduler.Seedling.SeedlingInspectionTable;
import com.example.pristineseed.DataBaseRepository.Scheduler.Seedling.Seedling_InspectionDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.Vegitative.VegitativeInspectionDao;
import com.example.pristineseed.DataBaseRepository.Scheduler.Vegitative.VegitativeInspectionTable;
import com.example.pristineseed.DataBaseRepository.distribution_master_table.Distribution_master_table;
import com.example.pristineseed.DataBaseRepository.distributor_dao.DistributorDao;
import com.example.pristineseed.DataBaseRepository.seed_dispatch_note.Organizer_master_Dao;
import com.example.pristineseed.DataBaseRepository.seed_dispatch_note.Organizer_master_Table;
import com.example.pristineseed.DataBaseRepository.seed_dispatch_note.SeedDispatchHeaderDao;
import com.example.pristineseed.DataBaseRepository.seed_dispatch_note.SeedDispatchNoteHeaderTable;
import com.example.pristineseed.DataBaseRepository.seed_dispatch_note.SeedDispatchNoteLineDao;
import com.example.pristineseed.DataBaseRepository.seed_dispatch_note.SeedDispatchNoteLineTable;
import com.example.pristineseed.DataBaseRepository.seed_dispatch_note.SeedFarmerMasterDao;
import com.example.pristineseed.DataBaseRepository.seed_dispatch_note.Seed_Farmer_master_Table;
import com.example.pristineseed.DataBaseRepository.tfa.CreateTFADao;
import com.example.pristineseed.DataBaseRepository.tfa.TFA_Header_Table;
import com.example.pristineseed.DataBaseRepository.travel.CityMasterTable;
import com.example.pristineseed.DataBaseRepository.travel.City_master_Dao;
import com.example.pristineseed.DataBaseRepository.travel.ModeOfTravelMasterTable;
import com.example.pristineseed.DataBaseRepository.travel.Mode_of_travel_master_Dao;
import com.example.pristineseed.DataBaseRepository.travel.TravelHeaderTable;
import com.example.pristineseed.DataBaseRepository.travel.TravelInsertDao;
import com.example.pristineseed.DataBaseRepository.travel.TravelLineExpenseTable;
import com.example.pristineseed.DataBaseRepository.travel.TravelViewHeaderDao;
import com.example.pristineseed.DataBaseRepository.travel.Travel_line_exapnce_Dao;
import com.example.pristineseed.DataSyncingBackgraundProcess.SyncMasterDao;
import com.example.pristineseed.DataSyncingBackgraundProcess.SyncMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.AreaDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.UserLocationMasterDao;
import com.example.pristineseed.sql_lite_process.dao.ExceptionDao;
import com.example.pristineseed.sql_lite_process.dao.UserDao;
import com.example.pristineseed.sql_lite_process.model.ExceptionTableModel;
import com.example.pristineseed.sql_lite_process.model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, ExceptionTableModel.class, SyncMasterTable.class, Distribution_master_table.class
        , GeoghraphicalTable.class, RegionMasterTable.class, StateMasterTable.class, TalukaMasterTable.class,
        ZoneMasterTable.class, DistricMasterTable.class, AreaMasterTable.class, Scheduler_Header_Table.class
        , SchedulerInspectionLineTable.class, GerminationInspection1_Table.class, SeedlingInspectionTable.class,
        VegitativeInspectionTable.class, Nicking2InspectionTable.class, FloweringInspectionTable.class
        , NickingInspectionTable.class, PostfloweringInspectionTable.class, MaturityInspectionTable.class, HarvestingInspectionTable.class
        , QcInspectionTable.class, PlantingDetailHeaderTable.class, PlantingLineTable.class, PlantingFSIO_BSIO_Table.class, SeasonMasterTable.class
        , LocationMasterTable.class, TravelLineExpenseTable.class, TravelHeaderTable.class, ModeOfTravelMasterTable.class,
        CityMasterTable.class, TFA_Header_Table.class, SeedDispatchNoteHeaderTable.class, SeedDispatchNoteLineTable.class, Organizer_master_Table.class,
        Seed_Farmer_master_Table.class, Planting_Lot_master_Table.class, EventManagemantTable.class, EventManagementExpenseLineTable.class
        , EventTypeMasterTable.class, ImageMasterTable.class, Hybrid_Item_Table.class,
        CropHytechMasterTable.class, RoleMasterTable.class, BankMasterTable.class, UomTable.class, BookingUnitPriceTable.class,
        ShipToAddressDataTable.class, PlantingLineLotListTable.class, FsioBsioSaleOrderNoTable.class
        , EmployeeAttendanceTable.class
}, version = 1, exportSchema = false)  //52 table classes....
public abstract class PristineDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract ExceptionDao exceptionDao();

    public abstract DistributorDao distributorDao();

    public abstract GeographicDao geographicDao();

    public abstract DistricMasterDao districMasterDao();

    public abstract RegionMasterDao regionMasterDao();

    public abstract ZoneMaterDao zoneMaterDao();

    public abstract StateMasterDao stateMasterDao();

    public abstract TalukaMasterDao talukaMasterDao();

    public abstract AreaDao areaDao();

    public abstract ScheduleInspectionHeaderDao scheduleInspectionHeaderDao();

    public abstract ScheduleInspectionLineDao scheduleInspectionLineDao();

    public abstract GerminationInspectionDao germinationInspectionDao();

    public abstract Seedling_InspectionDao seedling_inspectionDao();

    public abstract VegitativeInspectionDao vegitativeInspectionDao();

    public abstract Nickin2InspectionDao nickin2InspectionDao();

    public abstract FloweringInspectionDao floweringInspectionDao();

    public abstract NickingInspInsertDao nickingInspInsertDao();

    public abstract PostFloweringDao postFloweringDao();

    public abstract MaturityInspectionDao maturityInspectionDao();

    public abstract HarvestingInspectionDao harvestingInspectionDao();

    public abstract QcInspectionDao qcInspectionDao();

    public abstract PlantingHeaderDao plantingHeaderDao();

    public abstract PlantingLineDao plantingLineDao();

    public abstract Planting_fsio_bsio_Dao planting_fsio_bsio_dao();

    public abstract SeasonDao seasonDao();

    public abstract UserLocationMasterDao userLocationMasterDao();

    public abstract Mode_of_travel_master_Dao mode_of_travel_master_dao();

    public abstract City_master_Dao city_master_dao();

    public abstract SeedDispatchHeaderDao seedDispatchHeaderDao();

    public abstract SeedDispatchNoteLineDao seedDispatchNoteLineDao();

    public abstract Organizer_master_Dao organizer_master_dao();

    public abstract SeedFarmerMasterDao seedFarmerMasterDao();

    public abstract Planting_lot_Dao planting_lot_dao();

    public abstract EventTypeMasterDao eventTypeMasterDao();

    public abstract HybridItemMasterDao hybridItemMasterDao();

    public abstract CropHytechMasterDao cropHytechMasterDao();

    public abstract RoleMasterDao roleMasterDao();

    public abstract BankMasterDao bankMasterDao();

    public abstract UOMDao uomDao();

    public abstract BookingUnitPriceDao bookingUnitPriceDao();

    public abstract ShipToAddressDao shipToAddressDao();

    public abstract PlantingLineLotListDao plantingLineLotListDao();

    public abstract FsioBsioSaleOrderNoDao fsioBsioSaleOrderNoDao();

    public abstract EmployeeAttendanceDao employeeAttendanceDao();


    private static PristineDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

//todo in geographical Syncing class offline api hitting..
    public static PristineDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (PristineDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PristineDatabase.class, "pristine_hytech_database").fallbackToDestructiveMigration().allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        if (INSTANCE != null) {
            if (INSTANCE.isOpen()) {
                INSTANCE.close();
            }

            INSTANCE = null;
        }
    }
}