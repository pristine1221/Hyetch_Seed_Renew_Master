
package com.example.pristineseed.ui.ManualSyncingMaster;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.pristineseed.BackgroundTask;
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
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.LocationMasterTable;
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
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.UOMDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.UomTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.ZoneMasterTable;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.ZoneMaterDao;
import com.example.pristineseed.DataBaseRepository.Planting.PlantingFSIO_BSIO_Table;
import com.example.pristineseed.DataBaseRepository.Planting.Planting_Lot_master_Table;
import com.example.pristineseed.DataBaseRepository.Planting.Planting_fsio_bsio_Dao;
import com.example.pristineseed.DataBaseRepository.Planting.Planting_lot_Dao;
import com.example.pristineseed.DataBaseRepository.Planting.SeasonDao;
import com.example.pristineseed.DataBaseRepository.Planting.SeasonMasterTable;
import com.example.pristineseed.DataBaseRepository.seed_dispatch_note.SeedFarmerMasterDao;
import com.example.pristineseed.DataBaseRepository.seed_dispatch_note.Organizer_master_Dao;
import com.example.pristineseed.DataBaseRepository.seed_dispatch_note.Organizer_master_Table;
import com.example.pristineseed.DataBaseRepository.seed_dispatch_note.Seed_Farmer_master_Table;
import com.example.pristineseed.DataBaseRepository.travel.CityMasterTable;
import com.example.pristineseed.DataBaseRepository.travel.City_master_Dao;
import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.AreaDao;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.BookingOrder.ShipToAddressModel;
import com.example.pristineseed.model.GeoSetupModel.AreaMasterModel;
import com.example.pristineseed.model.GeoSetupModel.CountModel;
import com.example.pristineseed.model.GeoSetupModel.DispatchFarmerModel;
import com.example.pristineseed.model.GeoSetupModel.DistrictMaster;
import com.example.pristineseed.model.GeoSetupModel.GeoGraphicData;
import com.example.pristineseed.model.GeoSetupModel.GeographicModel;
import com.example.pristineseed.model.GeoSetupModel.RegionMaster;
import com.example.pristineseed.model.GeoSetupModel.StateMaster;
import com.example.pristineseed.model.GeoSetupModel.TalukaMaster;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.UserLocationMasterDao;
import com.example.pristineseed.model.GeoSetupModel.UserLocationModel;
import com.example.pristineseed.model.GeoSetupModel.ZoneMaster;
import com.example.pristineseed.model.PlantingModel.PlantingFsio_bsio_model;
import com.example.pristineseed.model.PlantingModel.PlantingLotModel;
import com.example.pristineseed.model.PlantingModel.SeasonMasterModel;
import com.example.pristineseed.model.item.BankMaserModel;
import com.example.pristineseed.model.item.CropMassterModel;
import com.example.pristineseed.model.item.FsioBsioSaleOrderNoModel;
import com.example.pristineseed.model.item.HybridItemMasterModel;
import com.example.pristineseed.model.item.OrganizerModel;
import com.example.pristineseed.model.item.PlantingProdcutionLotModel;
import com.example.pristineseed.model.item.RoleMasterModel;
import com.example.pristineseed.model.item.UnitOfMeasureModel;
import com.example.pristineseed.model.item.UnitPriceModel;
import com.example.pristineseed.model.travel.ta_da_model.CityMasterModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.item.LocationMasterAdapter;
import com.example.pristineseed.ui.adapter.item.PlantingProductionLotLineListAdapter;
import com.example.pristineseed.ui.bootmMainScreen.BottomMainActivity;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeographicalSyncing extends Fragment {
    private LinearLayout sync_data_btn;
    private SessionManagement sessionManagement;
    private ImageView sync_img_;
    private ProgressBar geo_graphic_loading, zone_master_loading, state_master_loading, region_master_loading, distric_master_loading,
            taluka_master_loading, area_master_loading, user_location_master_loading, sesaon_master_loading,
            planting_master_loading, orgnizer_master_loading, farmer_master_loading, planting_lot_master_loading, city_master_loading,
            hybrid_item_master_loading, hytech_crop_master_loading,
            hytech_role_master_loading, bank_master_loading, uom_master_loading, unit_price_master_loading, ship_to_master_loading, planting_line_lot_list_master_loading, sale_order_no_loading;

    private TextView geo_count, zonecount, state_count, region_count, distric_count_text, taluka_count, display_area_count, display_count_user_location, display_planting_count_fsio_bsio, display_count_seson_master, display_count_organizer, display_count_farmer, display_count_planting_lot, display_count_city_master, display_count_hybrid, display_count_crop_hytech,
            display_count_bank, display_count_umo, display_count_unitPrce, display_count_ship_address, display_count_planting_line_lot_master, display_count_sale_order_no,
            display_count_dealer, display_count_distributor, display_count_farmer_role, display_count_farmer_customer, display_count_farmer_prod_dist;

    private Animation mRotateAnimation;
    private Chip manual_sync_back_btn;
    private TextView text_syncing;
    private String lastSync;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.geo_setup_fragment, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intView(view);
    }


    private class BG_async extends AsyncTask<Void, Void, Void> {

        String fla_of_action;
        Activity activity;
        GeoGraphicData geoGraphicData = null;
        CountModel countmodel_gl;
        HybridItemMasterModel hybridItemMasterModel;
        List<PlantingProdcutionLotModel> planting_lot_list_data = new ArrayList<>();

        BG_async() {

        }

        BG_async(String fla_of_action, Activity activity, GeoGraphicData geoGraphicData) {
            this.fla_of_action = fla_of_action;
            this.activity = activity;
            this.geoGraphicData = geoGraphicData;
        }

        BG_async(String fla_of_action, Activity activity, HybridItemMasterModel hybridItemMasterModel) {
            this.fla_of_action = fla_of_action;
            this.activity = activity;
            this.hybridItemMasterModel = hybridItemMasterModel;
        }

        BG_async(String fla_of_action, Activity activity, List<PlantingProdcutionLotModel> planting_lot_list_data) {
            this.fla_of_action = fla_of_action;
            this.activity = activity;
            this.planting_lot_list_data = planting_lot_list_data;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                PristineDatabase db = PristineDatabase.getAppDatabase(this.activity);
                if (fla_of_action.equalsIgnoreCase("bind_geo")) {
                    try {
                        if (geoGraphicData != null) {
                            CountModel countmodel = new CountModel();
                            countmodel.stateCount = setStateMasterData(geoGraphicData.state_master, db);
                            countmodel.zoneCount = setzoneMasterData(geoGraphicData.zone_master, db);
                            countmodel.districCount = setDistricMasterData(geoGraphicData.district_master, db);
                            countmodel.regionCount = setRegionMasterData(geoGraphicData.region_master, db);
                            countmodel.talukaCount = setTalukaMaster(geoGraphicData.taluka_master, db);
                            countmodel.areaCount = setArea(geoGraphicData.area_master, db);
                            countmodel_gl = countmodel;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        db.close();
                        db.destroyInstance();
                    }
                } else if (fla_of_action.equalsIgnoreCase("bind_hybrid_master")) {
                        List<HybridItemMasterModel.Data> hybrid_item_list = hybridItemMasterModel.data;
                        CountModel countmodel = new CountModel();
                        try {
                            if (hybrid_item_list != null && hybrid_item_list.size() > 0) {
                                countmodel.hybrid_count = bindHybridItemList(hybrid_item_list, db);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            db.close();
                            db.destroyInstance();
                        }
                } else if (fla_of_action.equalsIgnoreCase("planting_lot_master_api")) {
                    CountModel countModel = new CountModel();
                    try {
                        countModel.planting_line_lot_list_count = bindPlantingLineLotData(planting_lot_list_data, db);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        db.close();
                        db.destroyInstance();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            bindGeoGraphicApiData(countmodel_gl);
            if(fla_of_action.equalsIgnoreCase("planting_lot_master_api")) {

                clickedButton = false;
            }
        }

    }


    private void bindGeoGraphicApiData(CountModel countmodel) {
        //todo (EXCEPTION FOUND that is why use run method .)android.view.ViewRootImpl$CalledFromWrongThreadException: Only the original thread that created a view hierarchy can touch its views...
        new Thread(new Runnable() {
            @Override
            public void run() {
                // geo_count.setText(String.valueOf(countmodel.geocount));
                state_count.setText(String.valueOf(countmodel.stateCount));
                zonecount.setText(String.valueOf(countmodel.zoneCount));
                distric_count_text.setText(String.valueOf(countmodel.districCount));
                region_count.setText(String.valueOf(countmodel.regionCount));
                taluka_count.setText(String.valueOf(countmodel.talukaCount));
                display_area_count.setText(String.valueOf(countmodel.areaCount));
                geo_graphic_loading.setVisibility(View.GONE);
                zone_master_loading.setVisibility(View.GONE);
                state_master_loading.setVisibility(View.GONE);
                region_master_loading.setVisibility(View.GONE);
                distric_master_loading.setVisibility(View.GONE);
                taluka_master_loading.setVisibility(View.GONE);
                area_master_loading.setVisibility(View.GONE);

                //todo bind_hybrid_master refreshUI which were in finally method..
                hybrid_item_master_loading.setVisibility(View.GONE);
                display_count_hybrid.setText(String.valueOf(countmodel.hybrid_count));

                //todo planting_lot_master_api refreshUI which were define in finally method...
                display_count_planting_line_lot_master.setText(String.valueOf(countmodel.planting_line_lot_list_count));
                planting_line_lot_list_master_loading.setVisibility(View.GONE);
                mRotateAnimation.cancel();
                text_syncing.setVisibility(View.GONE);
            }
        });

    }

    private boolean clickedButton = false;

    private void intView(View view) {
        sync_data_btn = view.findViewById(R.id.sync_data_btn);
        geo_graphic_loading = view.findViewById(R.id.geo_graphic_loading);
        zone_master_loading = view.findViewById(R.id.zone_master_loading);
        state_master_loading = view.findViewById(R.id.state_master_loading);
        region_master_loading = view.findViewById(R.id.region_master_loading);
        distric_master_loading = view.findViewById(R.id.destric_master_loading);
        taluka_master_loading = view.findViewById(R.id.taluka_master_loading);
        manual_sync_back_btn = view.findViewById(R.id.manual_sync_back_btn);
        area_master_loading = view.findViewById(R.id.area_loading);
        geo_count = view.findViewById(R.id.display_count_geo);
        state_count = view.findViewById(R.id.display_count_state);
        zonecount = view.findViewById(R.id.display_count_zone);
        region_count = view.findViewById(R.id.display_count_region);
        distric_count_text = view.findViewById(R.id.display_count_distric);
        taluka_count = view.findViewById(R.id.display_count_taluka);
        display_area_count = view.findViewById(R.id.display_area_count);
        sync_img_ = view.findViewById(R.id.sync_img_);
        text_syncing = view.findViewById(R.id.text_syncing);
        user_location_master_loading = view.findViewById(R.id.user_location_master_loading);
        display_count_user_location = view.findViewById(R.id.display_count_user_location);
        sesaon_master_loading = view.findViewById(R.id.sesaon_master_loading);
        planting_master_loading = view.findViewById(R.id.planting_master_loading);
        display_planting_count_fsio_bsio = view.findViewById(R.id.display_planting_count_fsio_bsio);
        display_count_seson_master = view.findViewById(R.id.display_count_seson_master);
        orgnizer_master_loading = view.findViewById(R.id.orgnizer_master_loading);
        display_count_organizer = view.findViewById(R.id.display_count_organizer);

        farmer_master_loading = view.findViewById(R.id.farmer_master_loading);
        display_count_farmer = view.findViewById(R.id.display_count_farmer);
        planting_lot_master_loading = view.findViewById(R.id.planting_lot_master_loading);
        display_count_planting_lot = view.findViewById(R.id.display_count_planting_lot);
        display_count_city_master = view.findViewById(R.id.display_count_city_master);
        city_master_loading = view.findViewById(R.id.city_master_loading);

        hybrid_item_master_loading = view.findViewById(R.id.hybrid_item_master_loading);
        display_count_hybrid = view.findViewById(R.id.display_count_hybrid);
        display_count_crop_hytech = view.findViewById(R.id.display_count_crop_hytech);
        hytech_crop_master_loading = view.findViewById(R.id.hytech_crop_master_loading);
        hytech_role_master_loading = view.findViewById(R.id.hytech_role_master_loading);
        display_count_bank = view.findViewById(R.id.display_count_bank);
        bank_master_loading = view.findViewById(R.id.bank_master_loading);
        display_count_umo = view.findViewById(R.id.display_count_umo);
        uom_master_loading = view.findViewById(R.id.uom_master_loading);
        unit_price_master_loading = view.findViewById(R.id.unit_price_master_loading);
        display_count_unitPrce = view.findViewById(R.id.display_count_unitPrce);
        display_count_ship_address = view.findViewById(R.id.display_count_ship_address);
        ship_to_master_loading = view.findViewById(R.id.ship_to_master_loading);
        planting_line_lot_list_master_loading = view.findViewById(R.id.planting_line_lot_list_master_loading);
        display_count_planting_line_lot_master = view.findViewById(R.id.display_count_planting_line_lot_master);
        display_count_sale_order_no = view.findViewById(R.id.display_count_sale_order_no);
        sale_order_no_loading = view.findViewById(R.id.sale_order_no_loading);
        display_count_dealer = view.findViewById(R.id.display_count_dealer);
        display_count_distributor = view.findViewById(R.id.display_count_distributor);
        display_count_farmer_role = view.findViewById(R.id.display_count_farmer_role);
        display_count_farmer_customer = view.findViewById(R.id.display_count_farmer_customer);
        display_count_farmer_prod_dist = view.findViewById(R.id.display_count_farmer_prod_dist);

        mRotateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_image_anim);

        sessionManagement = new SessionManagement(getActivity());
        manual_sync_back_btn.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });

        getAllRowCountFirstIfBackgraundSyncDone();


            sync_data_btn.setOnClickListener(v -> {
                if(!clickedButton) {
                try {
                    clickedButton = true;
                    getServerData();
              /*  if (!BottomMainActivity.backgraund_syncing_is_running) {
                    sync_data_btn.setEnabled(true);
             
                    getHybridItemMasterData();
                    getPlantingLineListLot();
                //  getUserLocationMasterData();
                    getPlantingFsio_BsioORGData("FSIO");
                    getPlantingFsio_BsioORGData("BSIO");
                getSelectSeason();
                  getOrganizerCode();
                  getFarmermaster();
                  getCityMaster();
                getPlantingLotList();

                getCropMasterData();
                getBankMasterData();
                    getRoleMasterData("Dealer");
                    getRoleMasterData("Customer");
                    getRoleMasterData("Farmer");
                    getRoleMasterData("Distributor");
                    getRoleMasterData("Prod Distributor");
                getUnitOfMeasureData();
                 getBookingUnitOfPriceData();
                getShipmentToAddressData();
//
                getFsioBsioSaleOrderNo();
                }else {
                    sync_data_btn.setEnabled(false);
                    StaticMethods.showMDToastOnTop(getActivity(),"Backgraund syncing is running!", MDToast.TYPE_INFO);
                }*/

                }
               catch (Exception e) {
                    e.printStackTrace();
                }
                }else {
                    MDToast.makeText(getActivity(), "Please Wait Until Data Sync!", Toast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
//                    Toast.makeText(getActivity(), "Please Wait Until Data Sync!", Toast.LENGTH_SHORT).show();
                }
            });


    }

    private void getServerData() {
        geo_graphic_loading.setVisibility(View.VISIBLE);
        zone_master_loading.setVisibility(View.VISIBLE);
        state_master_loading.setVisibility(View.VISIBLE);
        region_master_loading.setVisibility(View.VISIBLE);
        distric_master_loading.setVisibility(View.VISIBLE);
        taluka_master_loading.setVisibility(View.VISIBLE);
        area_master_loading.setVisibility(View.VISIBLE);
        /* start Animation */
       /* sync_img_.startAnimation(mRotateAnimation);
        text_syncing.setVisibility(View.VISIBLE);*/
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        if (sessionManagement.getLastSync() == null || sessionManagement.getLastSync().equalsIgnoreCase("")) {
            lastSync = "2020-02-27T00:00:42.387";    //DateTimeUtilsCustome.getCurrentTime();
        } else {
            lastSync = sessionManagement.getLastSync();
            try {
                sessionManagement.setLastSync(DateTimeUtilsCustome.getCurrentTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Call<GeoGraphicData> call = mAPIService.getGeographical(lastSync);
        call.enqueue(new Callback<GeoGraphicData>() {
            @Override
            public void onResponse(Call<GeoGraphicData> call, Response<GeoGraphicData> response) {
                try {
                    if (response.isSuccessful()) {
                        GeoGraphicData geoGraphicData = response.body();
                        new BG_async("bind_geo", getActivity(), geoGraphicData).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    } else {
                        // geo_graphic_loading.setVisibility(View.GONE);
                        zone_master_loading.setVisibility(View.GONE);
                        state_master_loading.setVisibility(View.GONE);
                        region_master_loading.setVisibility(View.GONE);
                        distric_master_loading.setVisibility(View.GONE);
                        taluka_master_loading.setVisibility(View.GONE);
                        area_master_loading.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "sync_data_exception", getActivity());
                } finally {
                    //  geo_graphic_loading.setVisibility(View.GONE);
                    zone_master_loading.setVisibility(View.GONE);
                    state_master_loading.setVisibility(View.GONE);
                    region_master_loading.setVisibility(View.GONE);
                    distric_master_loading.setVisibility(View.GONE);
                    taluka_master_loading.setVisibility(View.GONE);
                    area_master_loading.setVisibility(View.GONE);

                    getHybridItemMasterData();
                }

            }

            @Override
            public void onFailure(Call<GeoGraphicData> call, Throwable t) {
                //sync_img_.clearAnimation();
                // text_syncing.setVisibility(View.GONE);
                //  geo_graphic_loading.setVisibility(View.GONE);

                zone_master_loading.setVisibility(View.GONE);
                state_master_loading.setVisibility(View.GONE);
                region_master_loading.setVisibility(View.GONE);
                distric_master_loading.setVisibility(View.GONE);
                area_master_loading.setVisibility(View.GONE);
                taluka_master_loading.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "sync_data", getActivity());
                getHybridItemMasterData();
            }

        });

    }

    // Set  Up data into local Database...............................................


    private int insertGeographicalSetUp(List<GeographicModel> geoGraphicData, PristineDatabase db) {
        GeographicDao geographicDao = db.geographicDao();
        geographicDao.deleteAllRecord();
        List<GeoghraphicalTable> geoghraphicalTableList = new ArrayList<>();
        try {
            for (int i = 0; i < geoGraphicData.size(); i++) {
                GeoghraphicalTable geoghraphicalTable = new GeoghraphicalTable();
                geoghraphicalTable.setId(geoGraphicData.get(i).id);
                geoghraphicalTable.setZone(geoGraphicData.get(i).zone);
                geoghraphicalTable.setState(geoGraphicData.get(i).state);
                geoghraphicalTable.setRegion(geoGraphicData.get(i).region);
                geoghraphicalTable.setDistrict(geoGraphicData.get(i).district);
                geoghraphicalTable.setTaluka(geoGraphicData.get(i).taluka);
                geoghraphicalTable.setManager(geoGraphicData.get(i).managers);
                geoghraphicalTable.setUpdated_on(geoGraphicData.get(i).updated_on);
                geoghraphicalTable.setActive(geoGraphicData.get(i).active);
                geoghraphicalTableList.add(geoghraphicalTable);
            }
            geographicDao.insert(geoghraphicalTableList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return geographicDao.getRowCount();

    }


    private int setzoneMasterData(List<ZoneMaster> zoneMasters, PristineDatabase db) {
        ZoneMaterDao zoneMaterDao = db.zoneMaterDao();
        List<ZoneMasterTable> zoneMasterTableList = new ArrayList<>();
        for (int i = 0; i < zoneMasters.size(); i++) {
            ZoneMasterTable zoneMasterTable = new ZoneMasterTable();
            zoneMasterTable.setCode(zoneMasters.get(i).code);
            zoneMasterTable.setDescription(zoneMasters.get(i).description);
            zoneMasterTable.setActive(zoneMasters.get(i).active);
            zoneMasterTable.setUpdated_on(zoneMasters.get(i).updated_on);
            zoneMasterTableList.add(zoneMasterTable);
        }
        zoneMaterDao.insert(zoneMasterTableList);
        return zoneMaterDao.getRowCount();
    }

    private int setStateMasterData(List<StateMaster> stateMasters, PristineDatabase db) {
        StateMasterDao stateMasterDao = db.stateMasterDao();
        List<StateMasterTable> stateMasterTableList = new ArrayList<>();
        for (int i = 0; i < stateMasters.size(); i++) {
            StateMasterTable stateMasterTable = new StateMasterTable();
            stateMasterTable.setCode(stateMasters.get(i).code);
            stateMasterTable.setName(stateMasters.get(i).name);
            stateMasterTable.setActive(stateMasters.get(i).active);
            stateMasterTable.setUpdated_on(stateMasters.get(i).updated_on);
            stateMasterTableList.add(stateMasterTable);
        }
        stateMasterDao.insert(stateMasterTableList);
        return stateMasterDao.getRowCount();
    }


    private int setRegionMasterData(List<RegionMaster> regionMasters, PristineDatabase db) {
        RegionMasterDao regionMasterDao = db.regionMasterDao();
        List<RegionMasterTable> regionMasterTableList = new ArrayList<>();

        for (int i = 0; i < regionMasters.size(); i++) {
            RegionMasterTable regionMasterTable = new RegionMasterTable();
            regionMasterTable.setCode(regionMasters.get(i).code);
            regionMasterTable.setName(regionMasters.get(i).name);
            regionMasterTable.setActive(regionMasters.get(i).active);
            regionMasterTable.setUpdated_on(regionMasters.get(i).updated_on);
            regionMasterTable.setRegional_head(regionMasters.get(i).regional_head);
            regionMasterTable.setRegional_head_email_id(regionMasters.get(i).regional_head_email_id);
            regionMasterTable.setRegional_head_emp_code(regionMasters.get(i).regional_head_emp_code);
            regionMasterTable.setRegional_manager(regionMasters.get(i).regional_manager);
            regionMasterTable.setRegional_manager_email_id(regionMasters.get(i).regional_manager_email_id);
            regionMasterTable.setRegional_manager_emp_code(regionMasters.get(i).regional_manager_emp_code);
            regionMasterTable.setRegional_manager_mobile(regionMasters.get(i).regional_manager_mobile);
            regionMasterTableList.add(regionMasterTable);
        }
        regionMasterDao.insert(regionMasterTableList);
        return regionMasterDao.getRowCount();
    }

    private int setDistricMasterData(List<DistrictMaster> districtMasters, PristineDatabase db) {
        DistricMasterDao districMasterDao = db.districMasterDao();

        List<DistricMasterTable> districMasterTableList = new ArrayList<>();
        for (int i = 0; i < districtMasters.size(); i++) {
            DistricMasterTable districMasterTable = new DistricMasterTable();
            districMasterTable.setCode(districtMasters.get(i).code);
            districMasterTable.setName(districtMasters.get(i).name);
            districMasterTable.setActive(districtMasters.get(i).active);
            districMasterTable.setUpdated_on(districtMasters.get(i).updated_on);
            districMasterTable.setClass_of_city(districtMasters.get(i).class_of_city);
            districMasterTableList.add(districMasterTable);
        }
        districMasterDao.insert(districMasterTableList);
        Log.e("list", new Gson().toJson(districMasterTableList));
        return districMasterDao.getRowCount();
    }

    private int setTalukaMaster(List<TalukaMaster> talukaMasters, PristineDatabase db) {
        TalukaMasterDao talukaMasterDao = db.talukaMasterDao();
        List<TalukaMasterTable> talukaMasterList = new ArrayList<>();

        for (int i = 0; i < talukaMasters.size(); i++) {
            TalukaMasterTable talukaMasterTable = new TalukaMasterTable();
            talukaMasterTable.setCode(talukaMasters.get(i).code);
            talukaMasterTable.setDescription(talukaMasters.get(i).description);
            talukaMasterTable.setUpdated_on(talukaMasters.get(i).updated_on);
            talukaMasterTable.setActive(talukaMasters.get(i).active);
            talukaMasterList.add(talukaMasterTable);
        }
        talukaMasterDao.insert(talukaMasterList);
        return talukaMasterDao.getRowCount();
    }


    private int setArea(List<AreaMasterModel> areaMasterList, PristineDatabase pristineDatabase) {
        AreaDao areaDao = pristineDatabase.areaDao();
        List<AreaMasterTable> areaMasterTableList = new ArrayList<>();
        for (int i = 0; i < areaMasterList.size(); i++) {
            AreaMasterTable areaMasterTable = new AreaMasterTable();
            areaMasterTable.setArea_name(areaMasterList.get(i).area_name);
            areaMasterTable.setArea_code(areaMasterList.get(i).area_code);
            areaMasterTable.setUpdated_on(areaMasterList.get(i).updated_on);
            areaMasterTableList.add(areaMasterTable);
        }
        areaDao.insert(areaMasterTableList);
        return areaDao.getRowCount();
    }

    private void getUserLocationMasterData() {
        user_location_master_loading.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<List<UserLocationModel>> call = mAPIService.getUserLocation(sessionManagement.getUserEmail());
        call.enqueue(new Callback<List<UserLocationModel>>() {
            @Override
            public void onResponse(Call<List<UserLocationModel>> call, Response<List<UserLocationModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        List<UserLocationModel> user_locastion_masterlist = response.body();
                        if (user_locastion_masterlist != null && user_locastion_masterlist.size() > 0) {
                            CountModel countmodel = new CountModel();
                            Log.e("sucess", "success");
                            try {
                                PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
                                try {
                                    // insert data into database..................
                                    countmodel.user_location_count = setUserLocation(user_locastion_masterlist, db);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    db.close();
                                    db.destroyInstance();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                display_count_user_location.setText(String.valueOf(countmodel.user_location_count));
                                user_location_master_loading.setVisibility(View.GONE);
                                // sync_img_.clearAnimation();
                            }
                        }
                    } else {
                        user_location_master_loading.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    user_location_master_loading.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "user_location_master", getActivity());
                }
            }

            @Override
            public void onFailure(Call<List<UserLocationModel>> call, Throwable t) {
                user_location_master_loading.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "user_location_master", getActivity());
            }
        });
    }

    private int setUserLocation(List<UserLocationModel> userLocationModelList, PristineDatabase pristineDatabase) {
        UserLocationMasterDao userLocationMasterDao = pristineDatabase.userLocationMasterDao();
        userLocationMasterDao.deleteAllRecord();
        List<LocationMasterTable> userLocationTableList = new ArrayList<>();
        for (int i = 0; i < userLocationModelList.size(); i++) {
            if (userLocationModelList.get(i).location_code != null) {
                LocationMasterTable locationMasterTable = new LocationMasterTable();
                locationMasterTable.setEmail_id(userLocationModelList.get(i).email_id);
                locationMasterTable.setLocation_code(userLocationModelList.get(i).location_code);
                locationMasterTable.setLocation_name(userLocationModelList.get(i).location_name);
                userLocationTableList.add(locationMasterTable);
            }
        }
        userLocationMasterDao.insert(userLocationTableList);
        return userLocationMasterDao.getRowCount();
    }

    private void getPlantingFsio_BsioORGData(String doc_typ) {
        planting_master_loading.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<List<PlantingFsio_bsio_model>> call = mAPIService.getPlantingFsioBsioData(doc_typ, "");
        call.enqueue(new Callback<List<PlantingFsio_bsio_model>>() {
            @Override
            public void onResponse(Call<List<PlantingFsio_bsio_model>> call, Response<List<PlantingFsio_bsio_model>> response) {
                try {
                    if (response.isSuccessful()) {
                        List<PlantingFsio_bsio_model> templantingList_fsio = response.body();
                        if (templantingList_fsio != null && templantingList_fsio.size() > 0 && templantingList_fsio.get(0).condition) {
                            CountModel countmodel = new CountModel();
                            PristineDatabase pristinedb = PristineDatabase.getAppDatabase(getActivity());
                            try {
                                Log.e("sucess", "success");
                                countmodel.planting_fsio_bsio_count = bindPlantingFsio_data_IntoLocal(templantingList_fsio, pristinedb);
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                pristinedb.close();
                                pristinedb.destroyInstance();
                                planting_master_loading.setVisibility(View.GONE);
                                display_planting_count_fsio_bsio.setText(String.valueOf(countmodel.planting_fsio_bsio_count));
                            }
                        } else {
                            planting_master_loading.setVisibility(View.GONE);
                            // Toast.makeText(getActivity(), templantingList_fsio==null && templantingList_fsio.size() > 0 && templantingList_fsio.get(0).No==null ? "Planting Fsio/Bsio Record not found !" : "Api not respoding.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        planting_master_loading.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    planting_master_loading.setVisibility(View.GONE);
                    Log.e("exception database", e.getMessage() + "cause");
                    //   Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "planting_fsio_bsio_fragment_", getActivity());
                } finally {
                    planting_master_loading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<PlantingFsio_bsio_model>> call, Throwable t) {
                planting_master_loading.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "planting_fsio_bsio_fragment_", getActivity());
            }
        });
    }

    private int bindPlantingFsio_data_IntoLocal(List<PlantingFsio_bsio_model> templantingList_fsio, PristineDatabase pristineDatabase) {
        Planting_fsio_bsio_Dao planting_fsio_bsio_dao = pristineDatabase.planting_fsio_bsio_dao();
        planting_fsio_bsio_dao.deleteAllRecord();
        List<PlantingFSIO_BSIO_Table> plantingFSIOBsioTableList = new ArrayList<>();
        for (int i = 0; i < templantingList_fsio.size(); i++) {
            PlantingFSIO_BSIO_Table plantingFSIO_bsio_table = PlantingFSIO_BSIO_Table.getPlanting_fsio_bsio_data(templantingList_fsio.get(i));
            if (plantingFSIO_bsio_table != null && plantingFSIO_bsio_table.getNo() != null) {
                plantingFSIOBsioTableList.add(plantingFSIO_bsio_table);
            }
        }
        planting_fsio_bsio_dao.insertList(plantingFSIOBsioTableList);
        return planting_fsio_bsio_dao.getRowCount();
    }

    private void getSelectSeason() {
        sesaon_master_loading.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<List<SeasonMasterModel>> call = mAPIService.getSeasonMaster();
        call.enqueue(new Callback<List<SeasonMasterModel>>() {
            @Override
            public void onResponse(Call<List<SeasonMasterModel>> call, Response<List<SeasonMasterModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        List<SeasonMasterModel> templantingList_season = response.body();
                        if (templantingList_season != null && templantingList_season.size() > 0) {
                            CountModel countmodel = new CountModel();
                            PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
                            try {
                                // insert data into database..................
                                countmodel.season_master_count = bindSeaonListIntoLocal(templantingList_season, db);
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                db.close();
                                db.destroyInstance();
                                sesaon_master_loading.setVisibility(View.GONE);
                                display_count_seson_master.setText(String.valueOf(countmodel.season_master_count));
                            }
                        } else {
                            sesaon_master_loading.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), templantingList_season.size() > 0 ? "Season Record not found !" : "Api not respoding.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        sesaon_master_loading.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    sesaon_master_loading.setVisibility(View.GONE);
                    Log.e("exception database", e.getMessage() + "cause");
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "planting_season_", getActivity());
                } finally {
                    sesaon_master_loading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<SeasonMasterModel>> call, Throwable t) {
                sesaon_master_loading.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "planting_season_fragment_", getActivity());
            }
        });
    }


    private int bindSeaonListIntoLocal(List<SeasonMasterModel> templantingList_season, PristineDatabase pristineDatabase) {
        SeasonDao seasonDao = pristineDatabase.seasonDao();
        for (int i = 0; i < templantingList_season.size(); i++) {
            if (templantingList_season.get(i).sync_To_App.equalsIgnoreCase("True")) {
                SeasonMasterTable seasonMasterTable = SeasonMasterTable.getSeasonMasterTable(templantingList_season.get(i));
                if (seasonDao.isDataExist(templantingList_season.get(i).season_Code.toLowerCase()) > 0) {
                    seasonDao.update(seasonMasterTable);
                } else {
                    seasonDao.insert(seasonMasterTable);
                }
            }
        }

        return seasonDao.getRowCount();
    }

    private void getOrganizerCode() {
        orgnizer_master_loading.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<OrganizerModel> call = mAPIService.getOrgnizerData("", "");
        call.enqueue(new Callback<OrganizerModel>() {
            @Override
            public void onResponse(Call<OrganizerModel> call, Response<OrganizerModel> response) {
                try {
                    if (response.isSuccessful()) {
                        OrganizerModel organizerModel = response.body();
                        if (organizerModel != null && organizerModel.condition) {
                            List<OrganizerModel.Data> organizerList = organizerModel.data;
                            if (organizerList != null && organizerList.size() > 0) {
                                CountModel countmodel = new CountModel();
                                Log.e("sucess", "success");
                                PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
                                try {
                                    // insert data into database..................
                                    countmodel.organizer_count = bindOrganizerList(organizerList, db);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    db.close();
                                    db.destroyInstance();
                                    orgnizer_master_loading.setVisibility(View.GONE);
                                    display_count_organizer.setText(String.valueOf(countmodel.organizer_count));
                                }
                            }
                        } else {
                            orgnizer_master_loading.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Organizer Record not found !", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        orgnizer_master_loading.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    orgnizer_master_loading.setVisibility(View.GONE);
                    Log.e("exception database", e.getMessage() + "cause");
                    //   Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "organizer_list", getActivity());
                } finally {
                    orgnizer_master_loading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<OrganizerModel> call, Throwable t) {
                orgnizer_master_loading.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "organizer_list", getActivity());
            }
        });
    }

    private int bindOrganizerList(List<OrganizerModel.Data> orginer_list, PristineDatabase pristineDatabase) {
        Organizer_master_Dao organizer_master_dao = pristineDatabase.organizer_master_dao();
        for (int i = 0; i < orginer_list.size(); i++) {
            Organizer_master_Table organizer_master_table = Organizer_master_Table.insertOrganiserData(orginer_list.get(i));
            if (organizer_master_dao.isDataExist(orginer_list.get(i).No.toLowerCase()) > 0) {
                organizer_master_dao.update(organizer_master_table);
            } else {
                organizer_master_dao.insert(organizer_master_table);
            }
        }
        return organizer_master_dao.getRowCount();
    }


    private void getFarmermaster() {
        farmer_master_loading.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<DispatchFarmerModel> call = mAPIService.getDispatchFarmerList("");
        call.enqueue(new Callback<DispatchFarmerModel>() {
            @Override
            public void onResponse(Call<DispatchFarmerModel> call, Response<DispatchFarmerModel> response) {
                try {
                    if (response.isSuccessful()) {
                        DispatchFarmerModel farmerMasterModel = response.body();
                        if (farmerMasterModel != null && farmerMasterModel.condition) {
                            List<DispatchFarmerModel.Data> farmermaster_list = farmerMasterModel.data;
                            if (farmermaster_list != null && farmermaster_list.size() > 0) {
                                CountModel countmodel = new CountModel();
                                PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
                                try {
                                    SeedFarmerMasterDao seedFarmerMasterDao = db.seedFarmerMasterDao();
                                    for (int i = 0; i < farmermaster_list.size(); i++) {
                                        Seed_Farmer_master_Table seed_farmer_master_table = Seed_Farmer_master_Table.insertFarmerMasterTable(farmermaster_list.get(i));
                                        if (seedFarmerMasterDao.isDataExist(farmermaster_list.get(i).No.toLowerCase()) > 0) {
                                            seedFarmerMasterDao.update(seed_farmer_master_table);
                                        } else {
                                            seedFarmerMasterDao.insert(seed_farmer_master_table);
                                        }
                                    }
                                    countmodel.farmer_master_count = seedFarmerMasterDao.getRowCount();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    db.close();
                                    db.destroyInstance();
                                    farmer_master_loading.setVisibility(View.GONE);
                                    display_count_farmer.setText(String.valueOf(countmodel.farmer_master_count));
                                }
                            }
                        } else {
                            farmer_master_loading.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Farmer Record not found !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        farmer_master_loading.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    farmer_master_loading.setVisibility(View.GONE);
                    Log.e("exception database", e.getMessage() + "cause");
                    //   Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "farmer_list", getActivity());
                } finally {
                    farmer_master_loading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<DispatchFarmerModel> call, Throwable t) {
                farmer_master_loading.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "farmer_list", getActivity());
            }
        });

    }


    private int bindFarmerList(List<DispatchFarmerModel.Data> farmermaster_list, PristineDatabase pristineDatabase) {
        SeedFarmerMasterDao seedFarmerMasterDao = pristineDatabase.seedFarmerMasterDao();
        for (int i = 0; i < farmermaster_list.size(); i++) {
            Seed_Farmer_master_Table seed_farmer_master_table = Seed_Farmer_master_Table.insertFarmerMasterTable(farmermaster_list.get(i));
            if (seedFarmerMasterDao.isDataExist(farmermaster_list.get(i).No.toLowerCase()) > 0) {
                seedFarmerMasterDao.update(seed_farmer_master_table);
            } else {
                seedFarmerMasterDao.insert(seed_farmer_master_table);
            }
        }
        return seedFarmerMasterDao.getRowCount();
    }


    private void getPlantingLotList() {
        planting_lot_master_loading.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<PlantingLotModel> call = mAPIService.getPlantingLotData("", "", "");
        call.enqueue(new Callback<PlantingLotModel>() {
            @Override
            public void onResponse(Call<PlantingLotModel> call, Response<PlantingLotModel> response) {
                try {
                    if (response.isSuccessful()) {
                        PlantingLotModel plantingLotModel = response.body();
                        if (plantingLotModel != null && plantingLotModel.condition) {
                            List<PlantingLotModel.Data> planting_lot_list = plantingLotModel.data;
                            if (planting_lot_list != null && planting_lot_list.size() > 0) {
                                CountModel countmodel = new CountModel();
                                Log.e("sucess", "success");
                                PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
                                try {
                                    countmodel.planting_lot_count = bindPlantingLotList(planting_lot_list, db);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    db.close();
                                    db.destroyInstance();
                                    planting_lot_master_loading.setVisibility(View.GONE);
                                    display_count_planting_lot.setText(String.valueOf(countmodel.planting_lot_count));
                                }

                            }
                        } else {
                            planting_lot_master_loading.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Planting Lot List Record not found !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        planting_lot_master_loading.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    planting_lot_master_loading.setVisibility(View.GONE);
                    Log.e("exception database", e.getMessage() + "cause");
                    //   Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "planting_lot_list", getActivity());
                } finally {
                    planting_lot_master_loading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<PlantingLotModel> call, Throwable t) {
                planting_lot_master_loading.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "planting_lot_list", getActivity());
            }
        });

    }

    private int bindPlantingLotList(List<PlantingLotModel.Data> planting_lot_list, PristineDatabase pristineDatabase) {
        Planting_lot_Dao planting_lot_dao = pristineDatabase.planting_lot_dao();
        planting_lot_dao.deleteAllRecord();
        for (int i = 0; i < planting_lot_list.size(); i++) {
            Planting_Lot_master_Table planting_lot_master_table = new Planting_Lot_master_Table();
            planting_lot_master_table.setLot_No(planting_lot_list.get(i).Lot_No);
            planting_lot_master_table.setDocument_No(planting_lot_list.get(i).Document_No);
            planting_lot_master_table.setDocument_SubType(planting_lot_list.get(i).Document_SubType);
            planting_lot_master_table.setMale_Female_Other(planting_lot_list.get(i).Male_Female_Other);
            planting_lot_master_table.setReceipt_No(planting_lot_list.get(i).Receipt_No);
            planting_lot_master_table.setNo(planting_lot_list.get(i).No);
            planting_lot_master_table.setQuantity(planting_lot_list.get(i).Quantity);
            planting_lot_dao.insert(planting_lot_master_table);
        }
        return planting_lot_dao.getRowCount();
    }

    private void getCityMaster() {
        city_master_loading.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<List<CityMasterModel>> call = mAPIService.getCityMasterModel("getCityMaster");
        call.enqueue(new Callback<List<CityMasterModel>>() {
            @Override
            public void onResponse(Call<List<CityMasterModel>> call, Response<List<CityMasterModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        List<CityMasterModel> cityMasterModels = response.body();
                        if (cityMasterModels != null && cityMasterModels.size() > 0 && cityMasterModels.get(0).condition) {
                            CountModel countmodel = new CountModel();
                            Log.e("sucess", "success");
                            PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
                            try {
                                // insert data into database..................
                                countmodel.city_count = bindCityMasterList(cityMasterModels, db);
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                db.close();
                                db.destroyInstance();
                                city_master_loading.setVisibility(View.GONE);
                                display_count_city_master.setText(String.valueOf(countmodel.city_count));
                            }
                        } else {
                            city_master_loading.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "City Master Record not found !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        city_master_loading.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    city_master_loading.setVisibility(View.GONE);
                    Log.e("exception database", e.getMessage() + "cause");
                    //   Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "city_master_list", getActivity());
                } finally {
                    city_master_loading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<CityMasterModel>> call, Throwable t) {
                city_master_loading.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "city_master_list", getActivity());
            }
        });
    }

    private int bindCityMasterList(List<CityMasterModel> cityMasterModelList, PristineDatabase pristineDatabase) {
        City_master_Dao city_master_dao = pristineDatabase.city_master_dao();
        city_master_dao.deleteAllRecord();
        for (int i = 0; i < cityMasterModelList.size(); i++) {
            CityMasterTable cityMasterTable = CityMasterTable.getCityMastertTableData(cityMasterModelList.get(i));
            cityMasterTable.setClass_of_city(cityMasterModelList.get(i).class_of_city);
            cityMasterTable.setCode(cityMasterModelList.get(i).code);
            cityMasterTable.setCountry_region_code(cityMasterModelList.get(i).country_region_code);
            cityMasterTable.setName(cityMasterModelList.get(i).name);
            cityMasterTable.setActive(cityMasterModelList.get(i).active);
            city_master_dao.insert(cityMasterTable);
        }

        return city_master_dao.getRowCount();
    }


    private void getHybridItemMasterData() {
        hybrid_item_master_loading.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<HybridItemMasterModel> call = mAPIService.getHybridItemMaster(sessionManagement.getUserEmail());
        call.enqueue(new Callback<HybridItemMasterModel>() {
            @Override
            public void onResponse(Call<HybridItemMasterModel> call, Response<HybridItemMasterModel> response) {
                try {
                    if (response.isSuccessful()) {
                        HybridItemMasterModel hybridItemMasterModel = response.body();
                        if (hybridItemMasterModel != null && hybridItemMasterModel.condition) {
                            new BG_async("bind_hybrid_master", getActivity(), hybridItemMasterModel).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            /*List<HybridItemMasterModel.Data> hybrid_item_list= hybridItemMasterModel.data;
                            if(hybrid_item_list!=null && hybrid_item_list.size()>0) {
                                CountModel countmodel = new CountModel();
                                Log.e("sucess", "success");
                                PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
                                try {
                                    countmodel.hybrid_count = bindHybridItemList(hybrid_item_list, db);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    db.close();
                                    db.destroyInstance();
                                    hybrid_item_master_loading.setVisibility(View.GONE);
                                    display_count_hybrid.setText(String.valueOf(countmodel.hybrid_count));
                                }
                            }*/
                        } else {
                            hybrid_item_master_loading.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), " Crop Varity Record not found !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        hybrid_item_master_loading.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    hybrid_item_master_loading.setVisibility(View.GONE);
                    Log.e("exception database", e.getMessage() + "cause");
                    //   Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "hybrid_item_list", getActivity());
                } finally {
                    hybrid_item_master_loading.setVisibility(View.GONE);
                    getPlantingLineListLot();
                }
            }

            @Override
            public void onFailure(Call<HybridItemMasterModel> call, Throwable t) {
                hybrid_item_master_loading.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "hybrid_item_list", getActivity());
                getPlantingLineListLot();
            }
        });
    }

    private int bindHybridItemList(List<HybridItemMasterModel.Data> hybrid_item_list, PristineDatabase db) {
        HybridItemMasterDao hybridItemMasterDao = db.hybridItemMasterDao();
        hybridItemMasterDao.deleteAllRecord();
        for (int i = 0; i < hybrid_item_list.size(); i++) {
            Hybrid_Item_Table hybrid_item_table = Hybrid_Item_Table.insertHybridItem(hybrid_item_list.get(i));
            hybridItemMasterDao.insert(hybrid_item_table);
        }
        return hybridItemMasterDao.getRowCount();
    }


    private void getCropMasterData() {
        hytech_crop_master_loading.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<CropMassterModel> call = mAPIService.getCropMasterData();
        call.enqueue(new Callback<CropMassterModel>() {
            @Override
            public void onResponse(Call<CropMassterModel> call, Response<CropMassterModel> response) {
                try {
                    if (response.isSuccessful()) {
                        CropMassterModel cropMassterModel = response.body();
                        if (cropMassterModel != null && cropMassterModel.condition) {
                            List<CropMassterModel.Data> crop_master_list = cropMassterModel.data;
                            if (crop_master_list != null && crop_master_list.size() > 0) {
                                CountModel countmodel = new CountModel();
                                Log.e("sucess", "success");
                                PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
                                try {
                                    countmodel.hytech_crop_count = bindCropMasterData(crop_master_list, db);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    db.close();
                                    db.destroyInstance();
                                    display_count_crop_hytech.setText(String.valueOf(countmodel.hytech_crop_count));
                                    hytech_crop_master_loading.setVisibility(View.GONE);
                                }

                            }
                        } else {
                            hytech_crop_master_loading.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Crop Master Record not found !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        hytech_crop_master_loading.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    hytech_crop_master_loading.setVisibility(View.GONE);
                    Log.e("exception database", e.getMessage() + "cause");
                    //   Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "get_crop_master", getActivity());
                } finally {
                    hybrid_item_master_loading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<CropMassterModel> call, Throwable t) {
                hytech_crop_master_loading.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_crop_master", getActivity());
            }
        });
    }

    private int bindCropMasterData(List<CropMassterModel.Data> cropmaster_model_list, PristineDatabase db) {
        CropHytechMasterDao cropHytechMasterDao = db.cropHytechMasterDao();
        cropHytechMasterDao.deleteAllRecord();
        for (int i = 0; i < cropmaster_model_list.size(); i++) {
            CropHytechMasterTable cropHytechMasterTable = new CropHytechMasterTable();
            cropHytechMasterTable.setCode(cropmaster_model_list.get(i).Code);
            cropHytechMasterTable.setDescription(cropmaster_model_list.get(i).Description);
            cropHytechMasterDao.insert(cropHytechMasterTable);
        }
        return cropHytechMasterDao.getRowCount();
    }

    private void getRoleMasterData(String customer_type) {
        hytech_role_master_loading.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<RoleMasterModel> call = mAPIService.getDistributorSync(customer_type, "", "");
        call.enqueue(new Callback<RoleMasterModel>() {
            @Override
            public void onResponse(Call<RoleMasterModel> call, Response<RoleMasterModel> response) {
                try {
                    if (response.isSuccessful()) {
                        RoleMasterModel roleMasterModelList = response.body();
                        if (roleMasterModelList != null && roleMasterModelList.condition) {
                            List<RoleMasterModel.Data> rolemasterList = roleMasterModelList.data;
                            if (rolemasterList != null && rolemasterList.size() > 0) {

                                CountModel countModel = new CountModel();
                                PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
                                try {
                                    RoleMasterDao roleMasterDao = db.roleMasterDao();
                                    roleMasterDao.deleteAllRecord();
                                    for (int i = 0; i < rolemasterList.size(); i++) {
                                        RoleMasterTable roleMasterTable = RoleMasterTable.insertRoleMasterData(rolemasterList.get(i));
                                        roleMasterDao.insert(roleMasterTable);
                                    }
                                    countModel.role_dealer_count = roleMasterDao.getRowCountRoleType("Dealer");
                                    countModel.role_farmer_count = roleMasterDao.getRowCountRoleType("Farmer");
                                    countModel.role_customer_count = roleMasterDao.getRowCountRoleType("Customer");
                                    countModel.role_distributor_count = roleMasterDao.getRowCountRoleType("Distributor");
                                    countModel.role_prod_distributor_count = roleMasterDao.getRowCountRoleType("Prod Distributor");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    db.close();
                                    db.destroyInstance();
                                    //todo display_count..........
                                    display_count_dealer.setText(String.valueOf(countModel.role_dealer_count));
                                    display_count_distributor.setText(String.valueOf(countModel.role_distributor_count));
                                    display_count_farmer_role.setText(String.valueOf(countModel.role_farmer_count));
                                    display_count_farmer_customer.setText(String.valueOf(countModel.role_customer_count));
                                    display_count_farmer_prod_dist.setText(String.valueOf(countModel.role_prod_distributor_count));
                                    hytech_role_master_loading.setVisibility(View.GONE);
                                }
                            }
                        } else {
                            hytech_role_master_loading.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Role Master Record not found !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        hytech_role_master_loading.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    hytech_role_master_loading.setVisibility(View.GONE);
                    Log.e("exception database", e.getMessage() + "cause");
                    //   Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "get_crop_master", getActivity());
                } finally {
                    hytech_role_master_loading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<RoleMasterModel> call, Throwable t) {
                hytech_role_master_loading.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_crop_master", getActivity());
            }
        });
    }

    private void getBankMasterData() {
        bank_master_loading.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<BankMaserModel> call = mAPIService.getBankMasterData();
        call.enqueue(new Callback<BankMaserModel>() {
            @Override
            public void onResponse(Call<BankMaserModel> call, Response<BankMaserModel> response) {
                try {
                    if (response.isSuccessful()) {
                        BankMaserModel bankMaserModel = response.body();
                        if (bankMaserModel != null) {
                            CountModel countmodel = new CountModel();
                            Log.e("sucess", "success");
                            List<BankMaserModel.Data> bankMasterList = bankMaserModel.data;
                            if (bankMasterList != null && bankMasterList.size() > 0) {
                                PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
                                try {
                                    BankMasterDao bankMasterDao = db.bankMasterDao();
                                    bankMasterDao.deleteAllRecord();
                                    for (int i = 0; i < bankMasterList.size(); i++) {
                                        BankMasterTable bankMasterTable = BankMasterTable.insertBankMasterData(bankMasterList.get(i));
                                        bankMasterDao.insert(bankMasterTable);
                                    }
                                    countmodel.bank_master_count = bankMasterDao.getRowCount();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    db.close();
                                    db.destroyInstance();
                                    display_count_bank.setText(String.valueOf(countmodel.bank_master_count));
                                    bank_master_loading.setVisibility(View.GONE);
                                }
                            } else {
                                bank_master_loading.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "Banking Master Record not found !", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            bank_master_loading.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (Exception e) {
                    bank_master_loading.setVisibility(View.GONE);
                    Log.e("exception database", e.getMessage() + "cause");
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "get_bank_master", getActivity());
                } finally {
                    bank_master_loading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<BankMaserModel> call, Throwable t) {
                bank_master_loading.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_bank_master", getActivity());
            }
        });

    }

    private int bindBankMasterData(List<BankMaserModel.Data> bankMasterList, PristineDatabase db) {
        BankMasterDao bankMasterDao = db.bankMasterDao();
        bankMasterDao.deleteAllRecord();
        for (int i = 0; i < bankMasterList.size(); i++) {
            BankMasterTable bankMasterTable = BankMasterTable.insertBankMasterData(bankMasterList.get(i));
            bankMasterDao.insert(bankMasterTable);
        }
        return bankMasterDao.getRowCount();
    }


    private void getAllRowCountFirstIfBackgraundSyncDone() {
        CountModel countModel = new CountModel();
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
        try {
            // GeographicDao geographicDao = pristineDatabase.geographicDao();
            StateMasterDao stateMasterDao = pristineDatabase.stateMasterDao();
            ZoneMaterDao zoneMaterDao = pristineDatabase.zoneMaterDao();
            DistricMasterDao districMasterDao = pristineDatabase.districMasterDao();
            RegionMasterDao regionMasterDao = pristineDatabase.regionMasterDao();
            TalukaMasterDao talukaMasterDao = pristineDatabase.talukaMasterDao();
            AreaDao areaDao = pristineDatabase.areaDao();
            // UserLocationMasterDao userLocationMasterDao = pristineDatabase.userLocationMasterDao();
            // SeasonDao seasonDao = pristineDatabase.seasonDao();
            // Organizer_master_Dao organizer_master_dao = pristineDatabase.organizer_master_dao();
            //SeedFarmerMasterDao seedFarmerMasterDao = pristineDatabase.seedFarmerMasterDao();
            // City_master_Dao city_master_dao = pristineDatabase.city_master_dao();

            // Planting_lot_Dao planting_lot_dao = pristineDatabase.planting_lot_dao();
            HybridItemMasterDao hybridItemMasterDao = pristineDatabase.hybridItemMasterDao();

            Planting_fsio_bsio_Dao planting_fsio_bsio_dao = pristineDatabase.planting_fsio_bsio_dao();

            // CropHytechMasterDao hytechCropMasterDao = pristineDatabase.cropHytechMasterDao();
            //   RoleMasterDao roleMasterDao = pristineDatabase.roleMasterDao();
            //   BankMasterDao bankMasterDao=pristineDatabase.bankMasterDao();
            //  UOMDao uomDao=pristineDatabase.uomDao();
            // BookingUnitPriceDao bookingUnitPriceDao= pristineDatabase.bookingUnitPriceDao();

            //  ShipToAddressDao shipToAddressDao=pristineDatabase.shipToAddressDao();
            PlantingLineLotListDao plantingLineLotListDao = pristineDatabase.plantingLineLotListDao();
            //FsioBsioSaleOrderNoDao fsioBsioSaleOrderNoDao=pristineDatabase.fsioBsioSaleOrderNoDao();

            countModel.planting_fsio_bsio_count = planting_fsio_bsio_dao.getRowCount();
            // countModel.geocount = geographicDao.getRowCount();
            countModel.stateCount = stateMasterDao.getRowCount();
            countModel.zoneCount = zoneMaterDao.getRowCount();
            countModel.districCount = districMasterDao.getRowCount();
            countModel.regionCount = regionMasterDao.getRowCount();
            countModel.talukaCount = talukaMasterDao.getRowCount();
            countModel.areaCount = areaDao.getRowCount();

            // countModel.user_location_count = userLocationMasterDao.getRowCount();
            // countModel.season_master_count = seasonDao.getRowCount();
            // countModel.organizer_count = organizer_master_dao.getRowCount();
            // countModel.farmer_master_count = seedFarmerMasterDao.getRowCount();
            //  countModel.city_count = city_master_dao.getRowCount();
            // countModel.planting_lot_count = planting_lot_dao.getRowCount();
            countModel.hybrid_count = hybridItemMasterDao.getRowCount();

            //   countModel.hytech_crop_count = hytechCropMasterDao.getRowCount();

            // countModel.bank_master_count=bankMasterDao.getRowCount();
            // countModel.uom_count=uomDao.getRowCount();
            //countModel.unit_price_count=bookingUnitPriceDao.getRowCount();
            // countModel.ship_address_count=shipToAddressDao.getRowCount();
            countModel.planting_line_lot_list_count = plantingLineLotListDao.getRowCount();
            // countModel.fsio_bsio_sale_order_no_count=fsioBsioSaleOrderNoDao.getRowCount();
/*
            countModel. role_dealer_count= roleMasterDao.getRowCountRoleType("Dealer");
            countModel. role_farmer_count= roleMasterDao.getRowCountRoleType("Farmer");
            countModel. role_customer_count= roleMasterDao.getRowCountRoleType("Customer");
            countModel. role_distributor_count= roleMasterDao.getRowCountRoleType("Distributor");
            countModel. role_prod_distributor_count =roleMasterDao.getRowCountRoleType("Prod Distributor");*/


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
            //  geo_count.setText(String.valueOf(countModel.geocount));
            state_count.setText(String.valueOf(countModel.stateCount));
            zonecount.setText(String.valueOf(countModel.zoneCount));
            distric_count_text.setText(String.valueOf(countModel.districCount));
            region_count.setText(String.valueOf(countModel.regionCount));
            taluka_count.setText(String.valueOf(countModel.talukaCount));
            display_area_count.setText(String.valueOf(countModel.areaCount));
            display_count_hybrid.setText(String.valueOf(countModel.hybrid_count));
            // display_count_city_master.setText(String.valueOf(countModel.city_count));
            // display_count_farmer.setText(String.valueOf(countModel.farmer_master_count));

            //    display_count_planting_lot.setText(String.valueOf(countModel.planting_lot_count));
            //  display_count_seson_master.setText(String.valueOf(countModel.season_master_count));
            //  display_count_user_location.setText(String.valueOf(countModel.user_location_count));

            display_planting_count_fsio_bsio.setText(String.valueOf(countModel.planting_fsio_bsio_count));
            // display_count_organizer.setText(String.valueOf(countModel.organizer_count));
            // display_count_crop_hytech.setText(String.valueOf(countModel.hytech_crop_count));
            // display_count_bank.setText(String.valueOf(countModel.bank_master_count));
            // display_count_umo.setText(String.valueOf(countModel.uom_count));
            // display_count_unitPrce.setText(String.valueOf(countModel.unit_price_count));
            //  display_count_ship_address.setText(String.valueOf(countModel.ship_address_count));
            display_count_planting_line_lot_master.setText(String.valueOf(countModel.planting_line_lot_list_count));
            // display_count_sale_order_no.setText(String.valueOf(countModel.fsio_bsio_sale_order_no_count));

         /*   display_count_dealer.setText(String.valueOf(countModel.role_dealer_count));
            display_count_distributor.setText(String.valueOf(countModel.role_distributor_count));
            display_count_farmer_role.setText(String.valueOf(countModel.role_farmer_count));
            display_count_farmer_customer.setText(String.valueOf(countModel.role_customer_count));
            display_count_farmer_prod_dist.setText(String.valueOf(countModel.role_prod_distributor_count));*/

        }
    }


    private void getUnitOfMeasureData() {
        uom_master_loading.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<UnitOfMeasureModel> call = mAPIService.getUnitsOfMeasureList();
        call.enqueue(new Callback<UnitOfMeasureModel>() {
            @Override
            public void onResponse(Call<UnitOfMeasureModel> call, Response<UnitOfMeasureModel> response) {
                try {
                    if (response.isSuccessful()) {
                        UnitOfMeasureModel unitOfMeasureModelList = response.body();
                        if (unitOfMeasureModelList != null) {
                            CountModel countModel = new CountModel();
                            List<UnitOfMeasureModel.UnitOfMeasureModelList> uomList = unitOfMeasureModelList.data;
                            if (uomList != null && uomList.size() > 0) {
                                PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
                                try {
                                    countModel.uom_count = bindUOMData(uomList, db);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    db.close();
                                    db.destroyInstance();
                                    display_count_umo.setText(String.valueOf(countModel.uom_count));
                                    uom_master_loading.setVisibility(View.GONE);
                                }

                            } else {
                                uom_master_loading.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "Unit of Measure Record not found !", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            uom_master_loading.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }

            @Override
            public void onFailure(Call<UnitOfMeasureModel> call, Throwable t) {
                uom_master_loading.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_uom_master", getActivity());
            }
        });
    }

    private void getBookingUnitOfPriceData() {
        unit_price_master_loading.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<UnitPriceModel> call = mAPIService.getunitPriceModelList();
        call.enqueue(new Callback<UnitPriceModel>() {
            @Override
            public void onResponse(Call<UnitPriceModel> call, Response<UnitPriceModel> response) {
                try {
                    if (response.isSuccessful()) {
                        UnitPriceModel unitOfMeasureModel = response.body();
                        if (unitOfMeasureModel != null) {
                            CountModel countModel = new CountModel();
                            List<UnitPriceModel.Data> unitPricelist = unitOfMeasureModel.data;
                            if (unitPricelist != null && unitPricelist.size() > 0) {
                                PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
                                try {
                                    countModel.unit_price_count = bindBookingUnitPriceData(unitPricelist, db);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    db.close();
                                    db.destroyInstance();
                                    display_count_unitPrce.setText(String.valueOf(countModel.unit_price_count));
                                    unit_price_master_loading.setVisibility(View.GONE);

                                }
                            } else {
                                unit_price_master_loading.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "Booking Unit Price Record not found !", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            unit_price_master_loading.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                }
            }

            @Override
            public void onFailure(Call<UnitPriceModel> call, Throwable t) {
                unit_price_master_loading.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_booking_unit_price", getActivity());

            }
        });
    }

    private int bindBookingUnitPriceData(List<UnitPriceModel.Data> unitPricelist, PristineDatabase db) {
        BookingUnitPriceDao bookingUnitPriceDao = db.bookingUnitPriceDao();
        bookingUnitPriceDao.deleteAllRecord();
        for (int i = 0; i < unitPricelist.size(); i++) {
            BookingUnitPriceTable bookingUnitPriceTable = BookingUnitPriceTable.getBookingUnitPrice(unitPricelist.get(i));
            bookingUnitPriceDao.insert(bookingUnitPriceTable);
        }
        return bookingUnitPriceDao.getRowCount();

    }

    private int bindUOMData(List<UnitOfMeasureModel.UnitOfMeasureModelList> uomList, PristineDatabase db) {
        UOMDao uomDao = db.uomDao();
        uomDao.deleteAllRecord();
        for (int i = 0; i < uomList.size(); i++) {
            UomTable uomTable = UomTable.insertUOMData(uomList.get(i));
            uomDao.insert(uomTable);
        }
        return uomDao.getRowCount();
    }

    private void getShipmentToAddressData() {
        ship_to_master_loading.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<ShipToAddressModel> call = mAPIService.getShipToAddressData("", "");
        call.enqueue(new Callback<ShipToAddressModel>() {
            @Override
            public void onResponse(Call<ShipToAddressModel> call, Response<ShipToAddressModel> response) {
                try {
                    if (response.isSuccessful()) {
                        ShipToAddressModel shipToAddressModel = response.body();
                        if (shipToAddressModel != null && shipToAddressModel.condition) {
                            List<ShipToAddressModel.Data> ship_address_list = shipToAddressModel.data;
                            if (ship_address_list != null && ship_address_list.size() > 0) {
                                ship_to_master_loading.setVisibility(View.GONE);
                                CountModel countModel = new CountModel();
                                PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
                                try {
                                    countModel.ship_address_count = bindShipAdressData(ship_address_list, db);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    db.close();
                                    db.destroyInstance();
                                    display_count_ship_address.setText(String.valueOf(countModel.ship_address_count));
                                    ship_to_master_loading.setVisibility(View.GONE);
                                }
                            } else {
                                ship_to_master_loading.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } catch (Exception e) {
                    ship_to_master_loading.setVisibility(View.GONE);
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "get_ship_details", getActivity());
                }
            }

            @Override
            public void onFailure(Call<ShipToAddressModel> call, Throwable t) {
                ship_to_master_loading.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_ship_details", getActivity());
            }
        });

    }

    private int bindShipAdressData(List<ShipToAddressModel.Data> ship_address_list, PristineDatabase db) {
        ShipToAddressDao shipToAddressDao = db.shipToAddressDao();
        shipToAddressDao.deleteAllRecord();
        for (int i = 0; i < ship_address_list.size(); i++) {
            ShipToAddressDataTable shipToAddressDataTable = ShipToAddressDataTable.insertShipToAddressData(ship_address_list.get(i));
            shipToAddressDao.insert(shipToAddressDataTable);
        }
        return shipToAddressDao.getRowCount();
    }

    private void getPlantingLineListLot() {
        planting_line_lot_list_master_loading.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<List<PlantingProdcutionLotModel>> call = mAPIService.getPlantingLineListData();
        call.enqueue(new Callback<List<PlantingProdcutionLotModel>>() {
            @Override
            public void onResponse(Call<List<PlantingProdcutionLotModel>> call, Response<List<PlantingProdcutionLotModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        List<PlantingProdcutionLotModel> planting_lot_list_data = response.body();
                        if (planting_lot_list_data != null && planting_lot_list_data.size() > 0) {
                            planting_line_lot_list_master_loading.setVisibility(View.GONE);
                            new BG_async("planting_lot_master_api", getActivity(), planting_lot_list_data ).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                            /*PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
                            CountModel countModel = new CountModel();
                            try {
                                countModel.planting_line_lot_list_count = bindPlantingLineLotData(planting_lot_list_data, db);
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                db.close();
                                db.destroyInstance();
                                display_count_planting_line_lot_master.setText(String.valueOf(countModel.planting_line_lot_list_count));
                                planting_line_lot_list_master_loading.setVisibility(View.GONE);
                                mRotateAnimation.cancel();
                                text_syncing.setVisibility(View.GONE);
                            }*/

                        } else {
                            planting_line_lot_list_master_loading.setVisibility(View.GONE);
                            mRotateAnimation.cancel();
                            text_syncing.setVisibility(View.GONE);
                            clickedButton = false;
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    planting_line_lot_list_master_loading.setVisibility(View.GONE);
                    mRotateAnimation.cancel();
                    text_syncing.setVisibility(View.GONE);
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "getPlanting_line_lotList", getActivity());
                    clickedButton = false;
                }
            }

            @Override
            public void onFailure(Call<List<PlantingProdcutionLotModel>> call, Throwable t) {
                planting_line_lot_list_master_loading.setVisibility(View.GONE);
                mRotateAnimation.cancel();
                text_syncing.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "getPlanting_line_lotList", getActivity());
                clickedButton = false;
            }
        });
    }

    private int bindPlantingLineLotData(List<PlantingProdcutionLotModel> planting_line_lot_list_, PristineDatabase db) {
        PlantingLineLotListDao plantingLineLotListDao = db.plantingLineLotListDao();
        plantingLineLotListDao.deleteAllRecord();
        for (int i = 0; i < planting_line_lot_list_.size(); i++) {
            PlantingLineLotListTable plantingLostParentTable = PlantingLineLotListTable.bindPLantingLotDetail(planting_line_lot_list_.get(i));
            plantingLineLotListDao.insert(plantingLostParentTable);
        }
        return plantingLineLotListDao.getRowCount();
    }

    private void getFsioBsioSaleOrderNo() {
        sale_order_no_loading.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<FsioBsioSaleOrderNoModel> call = mAPIService.getFsioBsioSaleOrderNo("");
        call.enqueue(new Callback<FsioBsioSaleOrderNoModel>() {
            @Override
            public void onResponse(Call<FsioBsioSaleOrderNoModel> call, Response<FsioBsioSaleOrderNoModel> response) {
                try {
                    if (response.isSuccessful()) {
                        FsioBsioSaleOrderNoModel fsioBsioSaleOrderNoModel = response.body();
                        if (fsioBsioSaleOrderNoModel != null && fsioBsioSaleOrderNoModel.condition) {
                            List<FsioBsioSaleOrderNoModel.Data> fsio_bsio_sale_order_no_list = fsioBsioSaleOrderNoModel.data;
                            if (fsio_bsio_sale_order_no_list != null && fsio_bsio_sale_order_no_list.size() > 0) {
                                sale_order_no_loading.setVisibility(View.GONE);
                                BackgraundThread backgraundThread = new BackgraundThread(getActivity());
                                backgraundThread.datalist = fsio_bsio_sale_order_no_list;
                                backgraundThread.execute();

                                    /*PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
                                    CountModel countModel = new CountModel();
                                    try {
                                        FsioBsioSaleOrderNoDao fsioBsioSaleOrderNoDao= db.fsioBsioSaleOrderNoDao();
                                        fsioBsioSaleOrderNoDao.deleteAllRecord();
                                        for(int i=0;i<fsio_bsio_sale_order_no_list.size();i++){
                                            FsioBsioSaleOrderNoTable fsioBsioSaleOrderNoTable = FsioBsioSaleOrderNoTable.insertFsioBsioSaleOrderNoData(fsio_bsio_sale_order_no_list.get(i));
                                            fsioBsioSaleOrderNoDao.insert(fsioBsioSaleOrderNoTable);
                                        }
                                        countModel.fsio_bsio_sale_order_no_count = fsioBsioSaleOrderNoDao.getRowCount();;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    } finally {
                                        db.close();
                                        db.destroyInstance();
                                        sale_order_no_loading.setVisibility(View.GONE);
                                        display_count_sale_order_no.setText(String.valueOf(countModel.fsio_bsio_sale_order_no_count));
                                    }*/
                            } else {
                                sale_order_no_loading.setVisibility(View.GONE);
                            }
                        } else {
                            sale_order_no_loading.setVisibility(View.GONE);

                            Toast.makeText(getActivity(), fsioBsioSaleOrderNoModel == null ? "No data found" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        sale_order_no_loading.setVisibility(View.GONE);

                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    sale_order_no_loading.setVisibility(View.GONE);
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "fsio_bsio_sale_order_no_list", getActivity());
                }
            }

            @Override
            public void onFailure(Call<FsioBsioSaleOrderNoModel> call, Throwable t) {
                sale_order_no_loading.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "fsio_bsio_sale_order_no_list", getActivity());
            }

        });
    }


    public class BackgraundThread extends BackgroundTask {
        private Activity activity;
        private PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
        private List<FsioBsioSaleOrderNoModel.Data> datalist = null;
        CountModel countModel = new CountModel();

        public BackgraundThread(Activity activity) {
            super(activity);
            this.activity = activity;
        }

        @Override
        public void doInBackground() {
            try {
                FsioBsioSaleOrderNoDao fsioBsioSaleOrderNoDao = pristineDatabase.fsioBsioSaleOrderNoDao();
                fsioBsioSaleOrderNoDao.deleteAllRecord();
                for (int i = 0; i < datalist.size(); i++) {
                    FsioBsioSaleOrderNoTable fsioBsioSaleOrderNoTable = FsioBsioSaleOrderNoTable.insertFsioBsioSaleOrderNoData(datalist.get(i));
                    fsioBsioSaleOrderNoDao.insert(fsioBsioSaleOrderNoTable);
                }
                countModel.fsio_bsio_sale_order_no_count = fsioBsioSaleOrderNoDao.getRowCount();
                ;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                pristineDatabase.close();
                pristineDatabase.destroyInstance();
            }
        }

        @Override
        public void onPostExecute() {
            display_count_sale_order_no.setText(String.valueOf(countModel.fsio_bsio_sale_order_no_count));
            sale_order_no_loading.setVisibility(View.GONE);
        }
    }


}


