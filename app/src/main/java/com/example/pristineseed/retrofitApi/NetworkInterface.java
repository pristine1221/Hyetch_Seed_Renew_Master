

package com.example.pristineseed.retrofitApi;

import com.example.pristineseed.firebase_notification_service.InspectionNotificationModel;
import com.example.pristineseed.model.BookingOrder.BookingApprovalModel;
import com.example.pristineseed.model.BookingOrder.BookingResponseModel;
import com.example.pristineseed.model.BookingOrder.MarketingIndentApprovalModel;
import com.example.pristineseed.model.BookingOrder.MarketingIndentModel;
import com.example.pristineseed.model.BookingOrder.MarketingIndentModelSupplyQtyModel;
import com.example.pristineseed.model.BookingOrder.OrderBookingModel;
import com.example.pristineseed.model.BookingOrder.SalePlanLineModel;
import com.example.pristineseed.model.BookingOrder.ShipToAddressModel;
import com.example.pristineseed.model.Breeder.BreederInsertVistorModel;
import com.example.pristineseed.model.Breeder.BreederListVisitorModel;
import com.example.pristineseed.model.DailyActivity_Model.DailyActivityResponse;
import com.example.pristineseed.model.DailyActivity_Model.DailyActivityResponseModel;
import com.example.pristineseed.model.Dealer.DealerMasterModel;
import com.example.pristineseed.model.EmployeeModel.CurrentAttendanceModel;
import com.example.pristineseed.model.EmployeeModel.EmployeeLeaveMasterModel;
import com.example.pristineseed.model.EmployeeModel.EndClockAttendanceModel;
import com.example.pristineseed.model.EmployeeModel.StartClockAttendanceModel;
import com.example.pristineseed.model.GeoSetupModel.DispatchFarmerModel;
import com.example.pristineseed.model.GeoSetupModel.DispatchOrganizerModel;
import com.example.pristineseed.model.GeoSetupModel.GeoGraphicData;
import com.example.pristineseed.model.GeoSetupModel.UserLocationModel;
import com.example.pristineseed.model.LeaveApplicationModel.ApplyLeaveModel;
import com.example.pristineseed.model.LeaveApplicationModel.AvailableLeaveListModel;
import com.example.pristineseed.model.LeaveApplicationModel.LeaveAppliedSubmitModel;
import com.example.pristineseed.model.LeaveApplicationModel.LeaveCreateModel;
import com.example.pristineseed.model.LeaveApplicationModel.LeaveCreateViewModel;
import com.example.pristineseed.model.LeaveApplicationModel.LeaveDaysModel;
import com.example.pristineseed.model.LeaveApplicationModel.LeaveListModel;
import com.example.pristineseed.model.LeaveApplicationModel.LeavePendingApprovedModel;
import com.example.pristineseed.model.LeaveApplicationModel.LeavePendingForApprovalModel;
import com.example.pristineseed.model.LeaveApplicationModel.LeaveTypeSpinnerModel;
import com.example.pristineseed.model.LeaveApplicationModel.LevaeTypeModel;
import com.example.pristineseed.model.POG.POGModel;
import com.example.pristineseed.model.PlantingModel.PlantingFsio_bsio_model;
import com.example.pristineseed.model.PlantingModel.PlantingHeaderModel;
import com.example.pristineseed.model.PlantingModel.PlantingLotModel;
import com.example.pristineseed.model.PlantingModel.SeasonMasterModel;
import com.example.pristineseed.model.Plough_down_List.PloghDownListModel;
import com.example.pristineseed.model.ResponseModel;
import com.example.pristineseed.model.TspModel.TrialSeedModel;
import com.example.pristineseed.model.TspModel.TspResponseModel;
import com.example.pristineseed.model.YeildEstimateModel;
import com.example.pristineseed.model.collection.InsertCollectionResponse;
import com.example.pristineseed.model.distributor_master.CustomerType;
import com.example.pristineseed.model.distributor_master.DistributorListModel;
import com.example.pristineseed.model.distributor_master.InsertDistributorModel;
import com.example.pristineseed.model.event_managment_model.EventTypeMasterModel;
import com.example.pristineseed.model.event_managment_model.HitResponseModel;
import com.example.pristineseed.model.event_managment_model.SyncEventDetailModel;
import com.example.pristineseed.model.exceptions.ExceptionModel;
import com.example.pristineseed.model.fs_indent_requirement.FsIndentRequirementsModel;
import com.example.pristineseed.model.fs_return_harvesting.FSReturnHarvestingModel;
import com.example.pristineseed.model.fs_return_harvesting.FsRetrunAapprovalModel;
import com.example.pristineseed.model.fs_return_harvesting.FsReturnResponseModel;
import com.example.pristineseed.model.home.CollectionList;
import com.example.pristineseed.model.item.BankAccountModel;
import com.example.pristineseed.model.item.BankMaserModel;
import com.example.pristineseed.model.item.BookingMasterModel;
import com.example.pristineseed.model.item.CropMassterModel;
import com.example.pristineseed.model.item.FsioBsioSaleOrderNoModel;
import com.example.pristineseed.model.item.HybridItemMasterModel;
import com.example.pristineseed.model.item.OrganizerModel;
import com.example.pristineseed.model.item.PlantingProdcutionLotModel;
import com.example.pristineseed.model.item.RoleMasterModel;
import com.example.pristineseed.model.item.UnitOfMeasureModel;
import com.example.pristineseed.model.item.UnitPriceModel;
import com.example.pristineseed.model.landselection.LandSelectionDeleteModel;
import com.example.pristineseed.model.landselection.LandSelectionInsertModel;
import com.example.pristineseed.model.landselection.LandSelectionModel;
import com.example.pristineseed.model.landselection.LandSelectionUpdateModel;
import com.example.pristineseed.model.login.LoginModel;
import com.example.pristineseed.model.reportModel.LotsDueInspectionModel;
import com.example.pristineseed.model.reportModel.ProdAndQAInspectionStatusModel;
import com.example.pristineseed.model.reportModel.ReportSeedDispatchViewModel;
import com.example.pristineseed.model.reportModel.PLDandSownAcreViewModel;
import com.example.pristineseed.model.scheduler_inspection.CompleteGerminationInspectionModel;
import com.example.pristineseed.model.scheduler_inspection.GerminationInspectionHeaderModel;
import com.example.pristineseed.model.scheduler_inspection.SchedulerModel;
import com.example.pristineseed.model.seed_dispatch_note.DispatchResponseModel;
import com.example.pristineseed.model.seed_dispatch_note.SeedDispatchHeaderModel;
import com.example.pristineseed.model.tfa.TFAHeaderModel;
import com.example.pristineseed.model.tfa.TFAResponseModel;
import com.example.pristineseed.model.tfa.FAInsertLineResponseModel;
import com.example.pristineseed.model.travel.ta_da_model.CityMasterModel;
import com.example.pristineseed.model.travel.ta_da_model.ModeOfTravelModel;
import com.example.pristineseed.model.travel.ta_da_model.SyncTravelDetailModel;
import com.example.pristineseed.model.travel.ta_da_model.TADAInsertModel;
import com.example.pristineseed.model.travel.travel_view_header.EventCreateResponseModel;
import com.example.pristineseed.ui.adapter.LeaveApplicationAdapter.LeaveAppliedDeleteModel;
import com.example.pristineseed.ui.dashboard.newTheam.WeatherResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface NetworkInterface {
    @POST("/api/User/Login")
    Call<LoginModel> Login(@Body JsonObject jsonObject);  //todo if not deside what is response then use ResponseBody class

    @POST("/api/User/Logout")
    Call<List<ResponseModel>> logout(@Body JsonObject jsonObject);

    @POST("/api/BaseException/UIException")
    Call<List<ExceptionModel>> Exception(@Body JsonObject jsonObject);

    @Streaming
    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlAsync(@Url String fileUrl);

    //todo Collection..................
    @GET("/api/Collection/GetCollectionData")
    Call<List<CollectionList>> getCollectionData(@Query("email") String email);

    @POST("/api/Collection/InsertcollectionData")
    Call<List<InsertCollectionResponse>> insertCollection(@Body JsonObject jsonObject);

    //todo BreederVisitor List.........................
    @POST("/api/BreederVisitor/breeder_visitor_get")
    Call<List<BreederListVisitorModel>> getBreederList(@Body JsonObject jsonObject);

    @POST("/api/BreederVisitor/breeder_visitor_insert")
    Call<List<BreederInsertVistorModel>> insertBreederVisitor(@Body JsonObject jsonObject);

    //todo Distributor,Customer,Dealer and farmer Master List...............

    @POST("/api/DistributorMaster/distributor_master_get")
    Call<List<DistributorListModel>> getDistributorCustomerFarmerList(@Body JsonObject jsonObject);

    @POST("/api/DistributorMaster/distributor_master_insert")
    Call<List<InsertDistributorModel>> insertDistributorCustomerFarmer(@Body JsonObject jsonObject);


    @POST("/api/DistributorMaster/distributor_master_update")
    Call<List<InsertDistributorModel>> updateDistributorCustomerFarmer(@Body JsonObject jsonObject);

    @POST("/api/DistributorMaster/distributor_master_delete")
    Call<List<ResponseModel>> deleteDistributor_customer_farmer(@Body JsonObject jsonObject);

    //todo Geographical set up module..............
    @GET("/api/GeographicalSetup/GeographicalSetupData")
    Call<GeoGraphicData> getGeographical(@Query("lastSync") String last_Sync);

    //todo Land selection
    @POST("/api/LandSelection/landSelectionInsert")
    Call<List<LandSelectionInsertModel>> postLandSelectionInsert(@Body JsonObject jsonObject);

    @POST("/api/LandSelection/landSelectionGet")
    Call<List<LandSelectionModel>> postLandSelectionGet(@Body JsonObject jsonObject);

    @POST("/api/LandSelection/landSelectionUpdate")
    Call<List<LandSelectionUpdateModel>> postLandSelectionUpdate(@Body JsonObject jsonObject);

    @POST("/api/LandSelection/landSelectionDelete")
    Call<List<LandSelectionDeleteModel>> postLandSelectionDelete(@Body JsonObject jsonObject);

    //todo inspection apies..............

    @GET("/api/Inspection/InspectionScheduler_sync")
    Call<List<SchedulerModel>> getSchedulerInspection(@Query("user_email") String user_email, @Query("user_type") String user_type);

    @POST("/api/Inspection/scanProductionLotNo")
    Call<List<GerminationInspectionHeaderModel>> getScanInspction(@Body JsonObject jsonObject);

    @POST("/api/Inspection/insert_germination_inspection_one")
    Call<List<ResponseModel>> insertGermination(@Body JsonObject jsonObject);

    @GET("/api/Inspection/Complete_inspection")
    Call<List<CompleteGerminationInspectionModel>> completeInspection(@Query("inspection_type") String inspection_type, @Query("production_lot_no") String production_lot_no, @Query("schedule_no") String schedule_no);

    //todo insert Seedling Inspection...

    @POST("/api/Inspection/insert_seedling_inspection_two")
    Call<List<ResponseModel>> insertSeedlingInspection(@Body JsonObject jsonObject);


    // todo  insert vegitative inspection
    @POST("/api/Inspection/insert_vegetative_inspection_three_line")
    Call<List<ResponseModel>> insertVegitativeInspection(@Body JsonObject jsonObject);

    //todo insert Nicking inspection..
    @POST("/api/Inspection/insert_nicking_inspection_four_line")
    Call<List<ResponseModel>> insertNickingInspection(@Body JsonObject jsonObject);

    //todo insert Nicking2 Inspection...
    @POST("/api/Inspection/insert_nicking2_inspection_five_line")
    Call<List<ResponseModel>> insertNicking2Inspection(@Body JsonObject jsonObject);

    //todo insert flowering inspection..........
    @POST("/api/Inspection/insert_flowering_inspection_six_line")
    Call<List<ResponseModel>> insertFloweringInsection(@Body JsonObject jsonObject);

    //todo insert post flowering inspection...
    @POST("/api/Inspection/insert_post_flowering_inspection_seven_line")
    Call<List<ResponseModel>> insertPostFloweringInsection(@Body JsonObject jsonObject);

    //todo insert Maturity inspection...
    @POST("/api/Inspection/insert_Maturity_inspection_eight_line")
    Call<List<ResponseModel>> insertMaturityInsection(@Body JsonObject jsonObject);

    //todo insert harvesting inspection...
    @POST("/api/Inspection/insert_Harvesting_inspection_nine_line")
    Call<List<ResponseModel>> insertHarvestingInsection(@Body JsonObject jsonObject);

    //todo insert harvesting inspection...
    @POST("/api/Inspection/insert_Inspection_QC_line")
    Call<List<ResponseModel>> insertQCInsection(@Body JsonObject jsonObject);

    //todo Planting.....
    @GET("/api/Inspection/get_planting_header")
    Call<List<PlantingHeaderModel>> getPlantingDetail(@Query("email_id") String email, @Query("RowsPerPage") int raw_per_page, @Query("PageNumber") int PageNumber
            , @Query("total_rows") int total_rows);

    @GET("/api/PlantingFSPHSPDocNo/PlantingFSPHSPDocNo_sync")
    Call<List<PlantingFsio_bsio_model>> getPlantingFsioBsioData(@Query("flag") String flag, @Query("organiser_code") String organiser_code);

    @GET("/api/DistributorMaster/Season_sync")
    Call<List<SeasonMasterModel>> getSeasonMaster();

    @POST("/api/Inspection/insert_planting_header")
    Call<List<PlantingHeaderModel>> insertPlantingHeader(@Body JsonObject jsonObject);

    @POST("/api/Inspection/insert_planting_lines")
    Call<List<PlantingHeaderModel.PlantingLine>> insertPlantingLine(@Body JsonObject jsonObject);

    @GET("/api/Inspection/delete_planting_header")
    Call<List<ResponseModel>> deletePlantingHeader(@Query("code") String header_code);

    @GET("/api/Inspection/delete_planting_lines")
    Call<List<ResponseModel>> deletePlantingLines(@Query("code") String header_code, @Query("line_no") String line_no);

    @GET("/api/Inspection/complete_planting")
    Call<List<ResponseModel>> completePlantingHeaderLines(@Query("code") String code);

    @GET("/api/Users_Location/Users_Location_Sync")
    Call<List<UserLocationModel>> getUserLocation(@Query("user_ID") String email_id);

    @GET("/api/Dispatch/OrganizerList")
    Call<List<DispatchOrganizerModel>> getDispatchOrganizerList();

    //todo seedDispatchNot.........
    @GET("/api/Dispatch/FarmerList")
    Call<DispatchFarmerModel> getDispatchFarmerList(@Query("Village") String village);

    @GET("/api/Dispatch/Get_dispatch_header_line")
    Call<List<SeedDispatchHeaderModel>> getSeedDispatchNoteData(@Query("created_by") String created_by);


    @POST("/api/Dispatch/insert_dispatch_header")
    Call<List<SeedDispatchHeaderModel>> insertDispatchheader(@Body JsonObject jsonObject);


    @POST("/api/Dispatch/insert_dispatch_line")
    Call<List<SeedDispatchHeaderModel.SeedDispatchLineModel>> insertDispatchLine(@Body JsonObject jsonObject);

    @POST("/api/Dispatch/Delete_dispatch_header")
    Call<List<DispatchResponseModel>> deleteDispatchHeader(@Body JsonObject jsonObject);


    @POST("/api/Dispatch/Delete_dispatch_line")
    Call<List<DispatchResponseModel>> deleteDispatchLine(@Body JsonObject jsonObject);

    @GET("/api/Dispatch/OrganizerList")
    Call<OrganizerModel> getOrgnizerData(@Query("vendor_class") String vendor_class, @Query("Search_Name") String Search_Name);

    @GET("/api/Inspection/Lot_ListForPlanting")
    Call<PlantingLotModel> getPlantingLotData(@Query("Document_No") String doc_no, @Query("Document_SubType") String sub_type, @Query("flag") String flag);

    @GET("/api/Dispatch/Dispatch_complete_for_all")
    Call<List<DispatchResponseModel>> completeDispatchNote(@Query("dispatch_no") String dispatch_no);

    //todo Travel
    @POST("/api/Travel/insertTravelHeader")
    Call<List<TADAInsertModel>> insertTADA(@Body JsonObject jsonObject);

    @GET("/api/Travel/getTravelDeatil")
    Call<List<SyncTravelDetailModel>> getTravelUpdateApproveStatus(@Query("flag") String getExpense, @Query("email") String getApprove_id);

    @POST("/api/Travel/approveRejectTravel")
    Call<List<EventCreateResponseModel>> approve_reject_order(@Body JsonObject jsonObject);

    @POST("/api/Travel/insertTravelExpense")
    Call<List<SyncTravelDetailModel.Travel_line_Expense>> insertTravelExpenceResponseModel(@Body JsonArray jsonArray);

    @GET("/api/Travel/getTravelDeatil")
    Call<List<CityMasterModel>> getCityMasterModel(@Query("flag") String flag);
/*
    @GET("/api/Travel/CityMaster")
    Call<List<CityMasterModel.CityMaster>> getCityMaster();
    */
    @GET("/api/Travel/CityMaster")
    Call<CityMasterModel> getCityMaster();

    @GET("/api/Travel/getTravelDeatil")
    Call<List<ModeOfTravelModel>> getModeOfTravel(@Query("flag") String flag, @Query("email") String email, @Query("cities") String mod_of_cities, @Query("grade") String grade);

    @GET("/api/Travel/TravelLineDelete")
    Call<List<ResponseModel>> deleteTravelLines(@Query("travelcode") String travelcode, @Query("line_no") String line_no);

    @GET("/api/Travel/TravelLineSubmit")
    Call<List<ResponseModel>> travelLineSubmit(@Query("travelcode") String travelcode);

    //todo tfa
    @POST("/api/TFA/insert_tfa_header")
    Call<List<TFAResponseModel>> getTfaHeader(@Body JsonObject jsonObject);

    @GET("/api/TFA/get_tfa_approval_list")
    Call<List<TFAHeaderModel>> getTfaApproveList(@Query("email_id") String emailId);

    @GET("/api/TFA/get_tfa_header")
    Call<List<TFAHeaderModel>> getTFAHeaderLine(@Query("created_by") String createdBy);

    @POST("/api/TFA/tfa_approve")
    Call<List<TFAResponseModel>> TfaApproveRejectDetails(@Body JsonObject jsonObject);

    @POST("/api/TFA/insert_tfa_line")
    Call<List<FAInsertLineResponseModel>> TfaViewInsertLine(@Body JsonObject jsonObject);

    @POST("/api/TFA/update_tfa_header")
    Call<List<FAInsertLineResponseModel>> updateTfaHeader(@Body JsonObject jsonObject);

    @POST("/api/TFA/update_tfa_line")
    Call<List<FAInsertLineResponseModel.Tfa_lineModel>> updateTfaLine(@Body JsonObject jsonObject);

    //todo Event_managment...........

    @GET("/api/EventManagement/getEventDeatil")
    Call<List<EventTypeMasterModel>> getEventTypeData(@Query("flag") String flag);

    @POST("/api/EventManagement/insertEventManagement")
    Call<List<EventCreateResponseModel>> createEventSubmit(@Body JsonObject jsonObject);


    @Multipart
    @POST("/api/EventManagement/insertEventExpense")
    Call<List<HitResponseModel>> insertEventExpense(@Part("expancedata") RequestBody expancyArrayString, @Part List<MultipartBody.Part> filePart, @Part("actual_farmers") RequestBody actualFarmer
            , @Part("actual_distributers") RequestBody actual_distributers, @Part("actual_dealers") RequestBody actual_dealers);

    @GET("/api/EventManagement/getEventDeatil")
    Call<List<SyncEventDetailModel>> getEventApproveDetail(@Query("flag") String flag, @Query("email") String email);

    @POST("/api/EventManagement/updateEventStatusDeatil")
    Call<List<EventCreateResponseModel>> updateEventStatusDeatil(@Body JsonObject jsonObject);
    //todo Fs Retrun Harvesting...

    @GET("/api/FS_Return_Harvest/get_fs_return")
    Call<List<FSReturnHarvestingModel>> getFsRetrunHarvestingData(@Query("email_id") String email_id);

    @POST("/api/FS_Return_Harvest/Insert_Update_fs_return_header")
    Call<List<FsReturnResponseModel>> insertFsReturnHarvesting(@Body JsonObject jsonObject);

    @POST("/api/FS_Return_Harvest/Insert_Update_fs_return_line")
    Call<List<FSReturnHarvestingModel.FSReturnHarvestingLineModel>> insert_Update_fs_return_line(@Body JsonObject jsonObject);


    @POST("/api/FS_Return_Harvest/Update_fs_return_status")
    Call<List<FsReturnResponseModel>> updateApproveRejectStatus(@Body JsonObject jsonObject);

    @GET("api/FS_Return_Harvest/Get_fs_return_approval")
    Call<List<FsRetrunAapprovalModel>> getApprovalFsRequirementList(@Query("email_id") String email_id);


    //todo delete fs return line...

    @POST("api/FS_Return_Harvest/delete_fs_return")
    Call<List<ResponseModel>> deleteFsReturnLine(@Body JsonObject jsonObject);

    @GET("api/FS_Return_Harvest/fs_return_send_to_approval")
    Call<List<ResponseModel>> compelteFsReturn(@Query("fs_return_code") String fs_return_code);

    //todo FS_INDENT_Requirements.....

    @GET("/api/FS_Requirement/Get_fs_requirement")
    Call<List<FsIndentRequirementsModel>> getFsIndentRequirements(@Query("email_id") String email_id);


    @GET("/api/FS_Requirement/Get_fs_requirement_Approval")
    Call<List<FsIndentRequirementsModel>> getFsIndentApprovalList(@Query("email_id") String email_id);

    @POST("/api/FS_Requirement/Update_fs_requirement_status")
    Call<List<FsReturnResponseModel>> updateFsRequirementApprveStatus(@Body JsonObject jsonObject);


    @POST("/api/FS_Requirement/Insert_fs_requirement")
    Call<List<FsReturnResponseModel>> insertFsIndentRequirements(@Body JsonObject jsonObject);

    @POST("/api/FS_Requirement/Update_fs_requirement")
    Call<List<FsReturnResponseModel>> updateFsIndentRequirements(@Body JsonObject jsonObject);

    //todo POG...............

    @GET("/api/ProductOnGround/ProductOnGround_Get")
    Call<List<POGModel>> getProductionOnGrd(@Query("created_by") String created_by);

    @POST("/api/ProductOnGround/ProductOnGround_Insert")
    Call<List<ResponseModel>> insertPog(@Body JsonObject jsonObject);

    @POST("/api/ProductOnGround/ProductOnGround_Update")
    Call<List<ResponseModel>> updatePog(@Body JsonObject jsonObject);

    @GET("/api/ProductOnGround/ProductOnGround_Approval_Get")
    Call<List<POGModel>> getPogApprovalList(@Query("email_id") String email_id);

    @POST("/api/ProductOnGround/ProductOnGround_Status_Update")
    Call<List<ResponseModel>> updatePogAppravalStatus(@Body JsonObject jsonObject);

    //todo Plogh Down List..................

    @GET("/api/PloughDown/PloughDown_Get")
    Call<List<PloghDownListModel>> getPloughDownList(@Query("created_by") String created_by);

    @POST("/api/PloughDown/PloughDown_Insert")
    Call<List<ResponseModel>> insertPldwnList(@Body JsonObject jsonObject);

    @GET("/api/PloughDown/PloughDown_Approval_Get")
    Call<List<PloghDownListModel>> getApprovalList(@Query("email_id") String email_id);

    @POST("/api/PloughDown/PloughDown_Status_Update")
    Call<List<ResponseModel>> updatePldStatus(@Body JsonObject jsonObject);

    @POST("/api/PloughDown/PloughDown_Update")
    Call<List<ResponseModel>> updatePloughDownList(@Body JsonObject jsonObject);

    //todo Dealer..........
    @POST("/api/DistributorMaster/distributor_master_get")
    Call<List<DealerMasterModel>> getDealerMasterList(@Body JsonObject jsonObject);

    //todo daily Activity....
    @POST("/api/DailyActivity/dailyActivityInsertHeaderLines")
    Call<List<DailyActivityResponse>> insertLineDailyActivity(@Body JsonObject jsonObject);

    @GET("/api/DailyActivity/getDailyActivityDetail")
    Call<List<DailyActivityResponseModel>> getDailyActivityDetail(@Query("email") String email);

    //todo orderbooking..................

    @GET("/api/Booking/Booking_Get")
    Call<List<OrderBookingModel>> getOrderBooking(@Query("created_by") String created_by);

    @POST("/api/Booking/Booking_Insert")
    Call<List<BookingResponseModel>> inserBookingOrder(@Body JsonObject jsonObject);

    @POST("/api/Booking/Booking_Update")
    Call<List<BookingResponseModel>> updateBookingOrder(@Body JsonObject jsonObject);

    @GET("/api/Booking/Booking_Delete")
    Call<List<BookingResponseModel>> deleteBooking(@Query("booking_no") String booking_no);

    @GET("/api/Booking/Booking_Get_Approval")
    Call<List<BookingApprovalModel>> getBookingApprovalList(@Query("email_id") String email_id);

    @POST("/api/Booking/Booking_Status_Update")
    Call<List<BookingResponseModel>> updateBookingStatus(@Body JsonObject jsonObject);

    //todo bank_master......
    @GET("/api/Inspection/CustomerBankAccountList")
    Call<BankMaserModel> getBankMasterData();

    @GET("/api/DistributorMaster/CustumerTypeMaster")
    Call<CustomerType> getCustType();

    //todo Marketing Indent...
    @GET("/api/MarketingIndent/MarketingIndent_Get")
    Call<List<MarketingIndentModel>> getMarketingIndent(@Query("created_by") String created_by);

    @POST("/api/MarketingIndent/MarketingIndent_Header_Insert")
    Call<List<BookingResponseModel>> insertMarketingHeader(@Body JsonObject jsonObject);

    @GET("/api/MarketingIndent/MarketingIndent_Header_Delete")
    Call<List<BookingResponseModel>> deleteBookingHeader(@Query("marketing_indent_no") String marketing_indent_no);

    @POST("/api/MarketingIndent/MarketingIndent_Line_Insert")
    Call<List<MarketingIndentModel.MarketingIndentLine>> insertBookingLine(@Body JsonObject jsonObject);

    @POST("/api/MarketingIndent/MarketingIndent_Line_Update")
    Call<List<MarketingIndentModel.MarketingIndentLine>> updateBookingLine(@Body JsonObject jsonObject);

    @POST("/api/MarketingIndent/MarketingIndent_Line_Delete")
    Call<List<BookingResponseModel>> deleteBookingLine(@Body JsonObject jsonObject);

    @GET("/api/MarketingIndent/MarketingIndent_Send_Approval")
    Call<List<BookingResponseModel>> sendToApprvalBooking(@Query("marketing_indent_no") String marketing_indent_no);


    @GET("/api/MarketingIndent/MarketingIndent_Approval_Get")
    Call<List<MarketingIndentApprovalModel>> getMarketingIndentApprovalList(@Query("email_id") String email_id);


    @POST("/api/MarketingIndent/MarketingIndent_Status_update")
    Call<List<BookingResponseModel>> updateMarketingIndentBookingStatus(@Body JsonObject jsonObject);

    //todo Planting List Data API......
    @GET("/api/Inspection/PlantingListLine")
    Call<List<PlantingProdcutionLotModel>> getPlantingLineListData();

    @GET("/api/Inspection/PlantingListLineLotNo")
    Call<PlantingProdcutionLotModel.PlantingLineLotwise> getPlantingLineLotWiseListData(@Query("lot_no") String lot_no);

    //todo Hybrid Item Master API....
    @GET("/api/Inspection/MyItemsUserWise")
    Call<HybridItemMasterModel> getHybridItemMaster(@Query("email") String email);

    //todo customer/distributor master...
    @GET("/api/DistributorMaster/distributor_sync")
    Call<RoleMasterModel> getDistributorSync(@Query("Customer_Type") String Customer_Type, @Query("city") String city, @Query("salespersoncode") String salePersonCode);

    @GET("/api/DistributorMaster/distributor_sync")
    Call<RoleMasterModel> getCitySync(@Query("Customer_Type") String Customer_Type, @Query("districtCode") String districtCode , @Query("salespersoncode") String salePersonCode);

    @GET("/api/DistributorMaster/distributor_sync")
    Call<RoleMasterModel> getDistributor(@Query("Customer_Type") String Customer_Type, @Query("Search_Name") String search_name,  @Query("salespersoncode") String salePersonCode);


    //todo cropMaster..........
    @GET("/api/CropMaster/CropList")
    Call<CropMassterModel> getCropMasterData();


    //todo deposit bank master..........
    @GET("/api/Collection/BankAccountList")
    Call<BankAccountModel> getBankAccountList();


    //todo ship_to_address
    @GET("/api/MarketingIndent/ShipToAddressList")
    Call<ShipToAddressModel> getShipToAddressData(@Query("Search_Name") String Search_Name, @Query("Customer_No") String cust_no);


    //todo uom list
    @GET("/api/Inspection/UnitsofMeasureList")
    Call<UnitOfMeasureModel> getUnitsOfMeasureList();


    //todo booking master
    @GET("/api/MarketingIndent/BookingMaster")
    Call<BookingMasterModel> getBookingMassterNo(@Query("Distributor_Code") String Distributor_Code,@Query("App_Booking_No") String app_booking_no );

    @GET("/api/MarketingIndent/MarketingIndentUnitPrice")
    Call<UnitPriceModel> getunitPriceModelList();

/*
    https://localhost:5001/api/MarketingIndent/MarketingIndentUnitPrice?Booking_Indent_No=INDT000001&App_No=1

    @GET("/api/MarketingIndent/MarketingIndentUnitPrice")
    Call<UnitPriceModel> getUnitPrice*/

    //todo  tsp...
    @GET("/api/TrialSeedProduction/TrialSeedProduction_get")
    Call<List<TrialSeedModel>> getTspList(@Query("created_by") String created_by);

    @POST("/api/TrialSeedProduction/TrialSeedProduction_insert")
    Call<List<TspResponseModel>> insertTrialSeedProd(@Body JsonObject jsonObject);

    /**
     * Employee Attendance APi
     * todo Employee Attendance
     **/
    @POST("/api/Attendance/Start")
    Call<List<StartClockAttendanceModel>> Start(@Body JsonObject jsonObject);

    @POST("/api/Attendance/End")
    Call<List<EndClockAttendanceModel>> End(@Body JsonObject jsonObject);  //todo if not deside what is response then use ResponseBody class

    @POST("/api/Attendance/Current")
    Call<List<CurrentAttendanceModel>> Current(@Body JsonObject jsonObject);

    /**
     * Leave Application Api
     * todo Leave
     **/
    @GET("/api/Leave/leave_type")
    Call<List<LevaeTypeModel>> getLeaveType();

    @GET("/api/Leave/LeaveForApproval")
    Call<List<LeavePendingForApprovalModel>> getLeavePendingForApproval(@Query("emp_code") String emp_code);

    @POST("/api/Leave/LeaveApproval")
    Call<List<LeavePendingApprovedModel>> getLeaveApproved(@Body JsonObject jsonObject);


    @GET("/api/Leave/leave_type")
    Call<List<LeaveListModel>> leave_type();

    @POST("/api/Leave/LeaveList")
    Call<List<AvailableLeaveListModel>> availableLeaveList(@Body JsonObject jsonObject);

    @POST("/api/Leave/ApplyLeave")
    Call<List<ApplyLeaveModel>> applyLeaveList(@Body JsonObject jsonObject);

    @GET("/api/Leave/leave_approval_days")
    Call<List<LeaveDaysModel>> getLeaveApprovalDays();

    @GET("/api/Leave/LeaveAppliedLineDelete")
    Call<List<LeaveAppliedDeleteModel>> getLeaveAppliedDelete(@Query("leave_id") String leave_id, @Query("line_no") String line_no);

    @POST("/api/Leave/LeaveAppliedCreate")
    Call<List<LeaveCreateModel>> getLeaveAppliedCreate(@Body JsonObject jsonObject);

    @GET("/api/Leave/leave_type")
    Call<List<LeaveTypeSpinnerModel>> getLeaveTypeSpiner();

    @GET("/api/Leave/LeaveAppliedView")
    Call<List<LeaveCreateViewModel>> getLeaveAppliedCreateView(@Query("leave_id") String leave_id);

    @GET("/api/Leave/LeaveAppliedSubmited")
    Call<List<LeaveAppliedSubmitModel>> getLeaveAppliedSubmit(@Query("leave_id") String leave_id);

    //todo yield estimate...........

    @POST("/api/YieldEstimate/Yield_estimate_insert")
    Call<List<ResponseModel>> insertYieldEsitimate(@Body JsonObject jsonObject);

    @GET("/api/YieldEstimate/Yield_estimate_get")
    Call<List<YeildEstimateModel>> getYieldEstimateList(@Query("created_by") String created_by);


    @GET("/api/FS_Return_Harvest/fsioSalesOrderNoFSReturnHarvesting")
    Call<FsioBsioSaleOrderNoModel> getFsioBsioSaleOrderNo(@Query("doc_type") String doc_type);

    //todo marketing indent header update....

    @POST("/api/MarketingIndent/MarketingIndent_Header_Update")
    Call<List<BookingResponseModel>> marketing_indent_header_update(@Body JsonObject jsonObject);

    @GET("/v1/current.json")
    Call<WeatherResponse> getCurrentWeatherData(@Query("key") String key, @Query("q") String q);


    @GET("/api/Leave/EmployeeLeaveMaster")
    Call<EmployeeLeaveMasterModel> getEmployeeLeaveMaster(@Query("type") String type);


    @GET("/api/Inspection/ListForPlantinglot")
    Call<PlantingLotModel> getPlantingLotListForBreeder(@Query("season_code") String season_code, @Query("zone_code") String zone_code, @Query("no") String hybrid_code);


    @GET("/api/Inspection/InspectionNotificationGet")
    Call<List<InspectionNotificationModel>> getLocationNo(@Query("date") String date, @Query("name") String name);

    @GET("/api/Booking/SalesPlanLine")
    Call<SalePlanLineModel> getSalePlanModel(@Query("Crop_Code") String Crop_Code, @Query("Variety") String variety, @Query("Season_Code") String Season_Code);

    @POST("/api/MarketingIndent/MarketingIndentSupplyQtyNAV")
    Call<List<MarketingIndentModelSupplyQtyModel>> getMarketingIndentSupplyQty(@Body JsonObject jsonObject);

    //todo Reports api...

    //todo lot due inspection api...
    @GET("/api/Reports/LotsDueForInspectionView")
    Call<ArrayList<LotsDueInspectionModel>> lotsDueInspectionList(@Query("organiser_name") String organiser_name, @Query("item_name") String item_name, @Query("email") String email);

    //todo report SDN seed dispatch api...
    @GET("/api/Reports/ReportSeedDispatchNoteView")
    Call<ArrayList<ReportSeedDispatchViewModel>> reportSeedDispatchList(@Query("organiser_name") String organiser_name, @Query("hybrid_name") String hybrid_name, @Query("date") String date, @Query("email") String email);

    //todo sown acre list api....
    @GET("/api/Reports/SownAcreView")
    Call<List<PLDandSownAcreViewModel>> sownAcreViewList(@Query("organiser_name") String organiser_name, @Query("item_name") String item_name, @Query("village") String village, @Query("email") String email);

    //todo prod inspection status list api....
    @GET("/api/Reports/Prod_InspectionStatusView")
    Call<List<ProdAndQAInspectionStatusModel>> prodInspectionStatus(@Query("organiser_nam") String organiser_name, @Query("item_name") String item_name, @Query("email") String email);

    //todo Qc inspection status list api....
    @GET("/api/Reports/QC_InspectionStatusView")
    Call<List<ProdAndQAInspectionStatusModel>> qcInspectionStatusView(@Query("organiser_name") String organiser_name, @Query("item_name") String item_name, @Query("email") String email);

    //todo Qc inspection status list api....
    @GET("/api/Reports/QC_InspectionFlagWiseView")
    Call<List<ProdAndQAInspectionStatusModel>> qcInspectionFlagWiseView(@Query("organiser_name") String organiser_name, @Query("item_name") String item_name, @Query("email") String email);

    //todo pld and sown acre list view api...
    @GET("/api/Reports/PLDandSownAcreView")
    Call<List<PLDandSownAcreViewModel>> pldAndSownAcreList(@Query("item_name") String item_name, @Query("email") String email);

    /*todo download api...*/

    //todo reports file download api....
    @Streaming
    @GET("/api/Reports/LotsDueForInspectionReport")
    Call<ResponseBody> downloadFileStreaming(@Query("organiser_name") String customer_name, @Query("item_name") String item_name, @Query("email") String email);

    @Streaming
    @GET("/api/Reports/SeedDispatchNoteReport")
    Call<ResponseBody> downloadSdnFile(@Query("organiser_name") String customer_name, @Query("hybrid_name") String item_name, @Query("date") String date, @Query("email") String email);

    @Streaming
    @GET("/api/Reports/SownAcreReport")
    Call<ResponseBody> downloadSownAcreFile(@Query("organiser_name") String customer_name, @Query("item_name") String item_name, @Query("village") String village, @Query("email") String email);

    @Streaming
    @GET("/api/Reports/Prod_InspectionStatusReport")
    Call<ResponseBody> downloadProdInspectionFile(@Query("organiser_name") String customer_name, @Query("item_name") String item_name, @Query("email") String email);

    @Streaming
    @GET("/api/Reports/QC_InspectionStatusReport")
    Call<ResponseBody> downloadQCInspectionFile(@Query("organiser_name") String customer_name, @Query("item_name") String item_name, @Query("email") String email);

    @Streaming
    @GET("/api/Reports/QC_InspectionFlagWiseReport")
    Call<ResponseBody> downloadQCInspectionFlagWise(@Query("organiser_name") String customer_name, @Query("item_name") String item_name, @Query("email") String email);

    @Streaming
    @GET("/api/Reports/PLDandSownAcreReport")
    Call<ResponseBody> downloadPLDandSownAcre( @Query("item_name") String item_name, @Query("email") String email);

}
