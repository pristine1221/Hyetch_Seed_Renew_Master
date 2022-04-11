package com.example.pristineseed.ui.bootmMainScreen.ui.OrderBookingRenew.marketing_indent;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.HybridItemMasterDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.Hybrid_Item_Table;
import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.common_data.CommonData;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.CustomDatePicker;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.model.BookingOrder.BookingResponseModel;
import com.example.pristineseed.model.BookingOrder.MarketingIndentModel;
import com.example.pristineseed.model.BookingOrder.MarketingIndentModelSupplyQtyModel;
import com.example.pristineseed.model.BookingOrder.ShipToAddressModel;
import com.example.pristineseed.model.GeoSetupModel.UserLocationModel;
import com.example.pristineseed.model.PlantingModel.SeasonMasterModel;
import com.example.pristineseed.model.distributor_master.CustomerType;
import com.example.pristineseed.model.item.BookingMasterModel;
import com.example.pristineseed.model.item.RoleMasterModel;
import com.example.pristineseed.model.item.UnitOfMeasureModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.ItemAdapter;
import com.example.pristineseed.ui.adapter.PlantingAdapter.SeasonMasterAdapter;
import com.example.pristineseed.ui.adapter.item.BookingMasterAdapter;
import com.example.pristineseed.ui.adapter.item.LocationMasterAdapter;
import com.example.pristineseed.ui.adapter.item.RoleMasterAdapter;
import com.example.pristineseed.ui.adapter.item.UnitOfMeasureAdapter;
import com.example.pristineseed.ui.adapter.order_book.MarketingIndentLineAdapter;
import com.example.pristineseed.ui.adapter.order_book.ShipToDetailAdapter;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateMarketingIndentFragment extends Fragment implements RoleMasterAdapter.OnItemClickListner, ShipToDetailAdapter.ShipOnItemClickListner, BookingMasterAdapter.OnItemClickListner {
    private SessionManagement sessionManagement = null;
    private MarketingIndentModel marketingIndentModel = null;
    private TextView tv_name, tv_name2, tv_address, tv_addres_2, tv_cust_name, tv_date, tv_sale_type, tv_address_detail, tv_ship_date,tv_season_code,
            tv_ship_to, tv_header_no, tv_cust_no, tv_cust_type, name, name_2, address, address_2;
    private TextInputEditText ed_state, ed_zone, ed_resgion, ac_area, ed_tluka, ed_dispatch_date, ed_date, ed_ship_toname, ed_ship_to_address, ed_ship_post_code, ed_ship_to_city, ed_ship_to_method,
            ed_ship_gst_reg, ed_shipment_date, ed_ship_to_contact, ed_ship_to_address2, ed_ship_toname2, ac_ship_to;
    private AutoCompleteTextView ac_customer_type, ac_dispatch_loc, ac_season, ac_sales, ac_umo;
    private RadioGroup posted_radio_grp;
    private RadioButton rd_posted_yes, rd_posted_no,  line_posted_yes, line_posted_no;;
    private List<String> customer_type_list = new ArrayList<>();
    private RoleMasterModel.Data roleMasterTable = null;
    private List<RoleMasterModel.Data> role_no_list;
    private LinearLayout back_button_go_topreviousPage, name_detail_layout, header_create_section, getting_order_detail_header_section, line_cardview;
    private List<SeasonMasterModel> seasonMasterTableList = null;
    private String season_code = "", zone_code, state_code, taluka_code, region_code, area_code, ship_name2 = "", ship_address2, ship_contact, ship_region = "",
            ship_state_code = "", ship_agent_code = "", ship_agent_service_code = "", cust_name = "", dispatch_loc_code = "", flag_execute = "", postedString;
    private ShipToAddressModel.Data shipToAddressTable = null;
    private List<UserLocationModel> locationMasterTableList = null;
    private Button create_header_btn;
    private Chip delete_header, chip_add_line_booking, complete_header, chip_update_header;
    private ListView line_listview;
    private static int series = 0;
    private static int no_series;
    private MarketingIndentLineAdapter marketingIndentLineAdapter = null;
    private List<MarketingIndentModel.MarketingIndentLine> marketingIndentLineList = new ArrayList<>();
    private List<BookingMasterModel.Data> booking_no_list = null;
    private List<ShipToAddressModel.Data> shipToAddressDataTables_list = null;
    private List<UnitOfMeasureModel.UnitOfMeasureModelList> unitPriceTableList = null;
    private ProgressBar loading_content, loading_item, loading_item_ship;

    private MaterialCardView frame_layout_org_list, ship_code_search_layout;
    private RecyclerView lv_cust_dist_list, ship_address_list;
    private TextInputLayout search_cust_input_layout, ship_to_input_layout;
    private TextInputEditText ed_class_of_seed, ed_varity_product_group_code, ed_no_of_bags, ed_indent_qty, ed_booking_qty, ed_varity_grp, ed_alloted_qty, ed_suppply_qty, ed_unit_price, ed_variety_name,
            ed_varity_pack_size, ed_balance_qty, ed_line_discount_per, ed_alloted_per, ac_crop_code, ac_variety_code, ac_marketing_indent_no, ac_cust_no;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_marketing_indent_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            marketingIndentModel = new Gson().fromJson(bundle.getString("data", ""), MarketingIndentModel.class);
            if (marketingIndentModel != null) {
                header_create_section.setVisibility(View.GONE);
                getting_order_detail_header_section.setVisibility(View.VISIBLE);
                line_cardview.setVisibility(View.VISIBLE);
                setHeaderDetail();
            } else {
                header_create_section.setVisibility(View.VISIBLE);
            }
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        sessionManagement = new SessionManagement(getActivity());
        initView(view);
        getCustomerTypeData();
        getSeasonMaster();
//        getDispatchLocation();
        insertHeader();

     /* ac_dispatch_loc.setOnItemClickListener((parent, view1, position, id) -> {
          UserLocationModel userLocationModel=locationMasterTableList.get(position);
          if(userLocationModel!=null){
              ac_dispatch_loc.setText(userLocationModel.location_name);
              dispatch_loc_code= userLocationModel.location_code;
          }
      });*/

        ac_season.setOnItemClickListener((parent, view1, position, id) -> {
            if (seasonMasterTableList != null && seasonMasterTableList.size() > 0) {
                SeasonMasterModel seasonMasterModel = seasonMasterTableList.get(position);
                if (seasonMasterModel != null) {
                    ac_season.setText(seasonMasterModel.description);
                    season_code = seasonMasterModel.season_Code;
                } else {
                    ac_season.setText("");
                }
            }
        });

        chip_update_header.setOnClickListener(v -> {
            header_create_section.setVisibility(View.VISIBLE);
            name_detail_layout.setVisibility(View.VISIBLE);
            getting_order_detail_header_section.setVisibility(View.GONE);
            complete_header.setVisibility(View.GONE);
            create_header_btn.setText("Update Header");

            setUpdateHeaderDetails(marketingIndentModel);
            create_header_btn.setOnClickListener(v1 -> {

                if (ac_customer_type.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please select customer.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_date.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please select date", Toast.LENGTH_SHORT).show();
                    return;
                } else if (posted_radio_grp.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getActivity(), "Please select posted", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ac_cust_no.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please select cust. no", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_dispatch_date.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please select dispatch_date", Toast.LENGTH_SHORT).show();
                    return;
                } /*else if (ac_dispatch_loc.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Pleasse select dispatch location", Toast.LENGTH_SHORT).show();
                    return;
                }*/ else if (ac_sales.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please select sales type", Toast.LENGTH_SHORT).show();
                    return;
                }
               /* else if(ed_zone.getText().toString().trim().equalsIgnoreCase("")){
                    Toast.makeText(getActivity(), "Please select zone code.", Toast.LENGTH_SHORT).show();
                    return;
                } */
                else {
                    MarketingIndentModel marketingIndentModel1 = new MarketingIndentModel();
                    marketingIndentModel1.marketing_indent_no = marketingIndentModel.marketing_indent_no;
                    marketingIndentModel1.date = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_date.getText().toString().trim());
                    marketingIndentModel1.posted = postedString;
                    marketingIndentModel1.season = season_code;
                    marketingIndentModel1.no_series = marketingIndentModel.no_series;
                    marketingIndentModel1.customer_no = roleMasterTable.no != null ? roleMasterTable.no : "";
                    marketingIndentModel1.customer_name = ac_cust_no.getText().toString().trim();
                    marketingIndentModel1.name = name.getText().toString().trim();
                    marketingIndentModel1.name_2 = name_2.getText().toString().trim();
                    marketingIndentModel1.address = address.getText().toString().trim();
                    marketingIndentModel1.address_2 = address_2.getText().toString().trim();
                    if (zone_code != null && !zone_code.equalsIgnoreCase("")) {
                        marketingIndentModel1.zone = zone_code;
                    } else {
                        marketingIndentModel1.zone = "0";
                    }
                    if (state_code != null && !state_code.equalsIgnoreCase("")) {
                        marketingIndentModel1.state = state_code;
                    } else {
                        marketingIndentModel1.state = "0";
                    }

                    if (region_code != null && !region_code.equalsIgnoreCase("")) {
                        marketingIndentModel1.region = region_code;
                    } else {
                        marketingIndentModel1.region = "0";
                    }

                    if (area_code != null && !area_code.equalsIgnoreCase("")) {
                        marketingIndentModel1.area = area_code;
                    } else {
                        marketingIndentModel1.area = "0";
                    }
                    if (taluka_code != null && !taluka_code.equalsIgnoreCase("")) {
                        marketingIndentModel1.territory = taluka_code;
                    } else {
                        marketingIndentModel1.territory = "0";
                    }
                    marketingIndentModel1.location_code = dispatch_loc_code;
                    marketingIndentModel1.zone_name = ed_zone.getText().toString().trim();
                    marketingIndentModel1.state_name = ed_state.getText().toString().trim();
                    marketingIndentModel1.territory_name = ed_tluka.getText().toString().trim();
                    marketingIndentModel1.region_name = ed_resgion.getText().toString().trim();
                    marketingIndentModel1.area_name = ac_area.getText().toString().trim();
                    marketingIndentModel1.customer_type = ac_customer_type.getText().toString().trim();
                    marketingIndentModel1.ship_to_code = ac_ship_to.getText().toString().trim();
                    marketingIndentModel1.ship_to_name = ed_ship_toname.getText().toString().trim();
                    marketingIndentModel1.ship_to_name_2 = ed_ship_toname2.getText().toString().trim();
                    marketingIndentModel1.ship_to_address = ed_ship_to_address.getText().toString().trim();
                    marketingIndentModel1.ship_to_address_2 = ed_ship_to_address2.getText().toString().trim();
                    marketingIndentModel1.ship_to_postcode = ed_ship_post_code.getText().toString().trim();
                    marketingIndentModel1.ship_to_city = ed_ship_to_city.getText().toString().trim();
                    marketingIndentModel1.ship_to_contact = ed_ship_to_contact.getText().toString().trim();
                    marketingIndentModel1.dispatch_by_date = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_date.getText().toString().trim());
                    if (ship_region != null && !ship_region.equalsIgnoreCase("")) {
                        marketingIndentModel1.region_code = ship_region;
                    } else {
                        marketingIndentModel1.region_code = "0";
                    }
                    marketingIndentModel1.ship_to_county = "IN";
                    marketingIndentModel1.shipment_date = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_shipment_date.getText().toString().trim());
                    marketingIndentModel1.GST_ship_to_state_code = !ship_state_code.equalsIgnoreCase("") ? ship_state_code : "null";
                    marketingIndentModel1.ship_to_gst_reg_no = !ed_ship_gst_reg.getText().toString().trim().equalsIgnoreCase("") ? ed_ship_gst_reg.getText().toString().trim() : "null";
                    marketingIndentModel1.shipment_method_code = !ed_ship_to_method.getText().toString().trim().equalsIgnoreCase("") ? ed_ship_to_method.getText().toString().trim() : "null";
                    marketingIndentModel1.shipping_agent_code = !ship_agent_code.equalsIgnoreCase("") ? ship_agent_code : "null";
                    marketingIndentModel1.shipping_agent_service_code = !ship_agent_service_code.equalsIgnoreCase("") ? ship_agent_service_code : "null";
                    marketingIndentModel1.dispatch_location_code = "";
                    marketingIndentModel1.sales_type = ac_sales.getText().toString().trim();
                    marketingIndentModel1.dispatch_by_date = DateTimeUtilsCustome.getDateFormate("dd-MM-YYYY", ed_dispatch_date.getText().toString().trim(), "YYYY-MM-dd");
                    marketingIndentModel1.created_by = sessionManagement.getUserEmail();
                    marketingIndentModel1.created_on = DateTimeUtilsCustome.getCurrentTime();
                    String jsonString = new Gson().toJson(marketingIndentModel1);
                    JsonObject asJsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
                    if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
                        LoadingDialog progressDialog = new LoadingDialog();
                        progressDialog.showLoadingDialog(getActivity());
                        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                        Call<List<BookingResponseModel>> call = mAPIService.marketing_indent_header_update(asJsonObject);
                        call.enqueue(new Callback<List<BookingResponseModel>>() {
                            @Override
                            public void onResponse(Call<List<BookingResponseModel>> call, Response<List<BookingResponseModel>> response) {
                                try {
                                    if (response.isSuccessful()) {
                                        progressDialog.hideDialog();
                                        List<BookingResponseModel> bookingResponseList = response.body();
                                        if (bookingResponseList != null && bookingResponseList.get(0).condition) {
                                            marketingIndentModel = marketingIndentModel1;
                                            getting_order_detail_header_section.setVisibility(View.VISIBLE);
                                            complete_header.setVisibility(View.VISIBLE);
                                            header_create_section.setVisibility(View.GONE);
                                            if (marketingIndentModel.marketing_indent_no != null) {
                                                tv_header_no.setText("(" + marketingIndentModel1.marketing_indent_no + ")");
                                            }
                                            tv_cust_name.setText(marketingIndentModel1.name);
                                            tv_date.setText(marketingIndentModel1.date);
                                            tv_sale_type.setText(marketingIndentModel1.sales_type);
                                            tv_address_detail.setText(marketingIndentModel1.address);
                                            tv_ship_date.setText(marketingIndentModel1.shipment_date);
                                            tv_ship_to.setText(marketingIndentModel1.ship_to_code);
                                            tv_cust_no.setText(marketingIndentModel1.customer_no);
                                            tv_cust_type.setText(marketingIndentModel1.customer_type);
                                            tv_season_code.setText(marketingIndentModel1.season);
                                            setUpdateHeaderDetails(marketingIndentModel1);

                                            Toast.makeText(getActivity(), bookingResponseList.get(0).message, Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getActivity(), bookingResponseList.size() > 0 ? response.message() : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        progressDialog.hideDialog();
                                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    progressDialog.hideDialog();
                                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "update_order_header", getActivity());
                                }
                            }

                            @Override
                            public void onFailure(Call<List<BookingResponseModel>> call, Throwable t) {
                                progressDialog.hideDialog();
                                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "update_order_header", getActivity());
                            }
                        });
                    } else {
                        Toast.makeText(getActivity(), "please wait for internet connection", Toast.LENGTH_SHORT).show();
                    }
                }

            });

        });
    }

    private void setUpdateHeaderDetails(MarketingIndentModel marketingIndentModel) {
        if (marketingIndentModel != null && marketingIndentModel.marketing_indent_no != null) {
            if (marketingIndentModel.marketing_indent_no != null) {
                tv_header_no.setText(marketingIndentModel.marketing_indent_no);
            }
            ed_state.setText(marketingIndentModel.state);
            ed_zone.setText(marketingIndentModel.zone);
            ed_resgion.setText(marketingIndentModel.region);
            ac_area.setText(marketingIndentModel.area);
            ed_tluka.setText(marketingIndentModel.territory);
            ac_customer_type.setText(marketingIndentModel.customer_type);
            tv_name.setText(marketingIndentModel.name);
            tv_name2.setText(marketingIndentModel.name_2);
            tv_address.setText(marketingIndentModel.address);
            tv_addres_2.setText(marketingIndentModel.address_2);
//            ac_dispatch_loc.setText(marketingIndentModel.location_code);
            if (marketingIndentModel.dispatch_by_date != null && !marketingIndentModel.dispatch_by_date.equalsIgnoreCase("")) {
                ed_dispatch_date.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(marketingIndentModel.dispatch_by_date));
            } else {
                ed_dispatch_date.setText("");
            }
            ac_season.setText(marketingIndentModel.season_name);
            ac_sales.setText(marketingIndentModel.sales_type);
            ed_date.setText(marketingIndentModel.date);
            if (marketingIndentModel.posted.equalsIgnoreCase("1")) {
                rd_posted_yes.setChecked(true);
                postedString = "1";
            } else if (marketingIndentModel.posted.equalsIgnoreCase("0")) {
                rd_posted_no.setChecked(true);
                postedString = "0";
            }
            if (marketingIndentModel.ship_to_code != null && !marketingIndentModel.ship_to_code.equalsIgnoreCase("")) {
                ship_to_input_layout.setStartIconDrawable(null);
                ac_ship_to.setText(marketingIndentModel.ship_to_code);
            } else {
                ship_to_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
            }
            ed_ship_toname.setText(marketingIndentModel.ship_to_name);
            ed_ship_to_address.setText(marketingIndentModel.ship_to_address);
            ed_ship_post_code.setText(marketingIndentModel.ship_to_address_2);
            ed_ship_to_city.setText(marketingIndentModel.ship_to_city);
            ed_ship_to_method.setText(marketingIndentModel.shipment_method_code);
            ed_ship_gst_reg.setText(marketingIndentModel.ship_to_gst_reg_no);
            if (marketingIndentModel.customer_no != null && !marketingIndentModel.customer_no.equals("")) {
                search_cust_input_layout.setStartIconDrawable(null);
                ac_cust_no.setText(marketingIndentModel.customer_name);

            } else {
                search_cust_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
            }

            if (marketingIndentModel.shipment_date != null && !marketingIndentModel.shipment_date.equalsIgnoreCase("")) {
                ed_shipment_date.setText(DateTimeUtilsCustome.splitDateInYYYMMDDslsh(marketingIndentModel.shipment_date));
            } else {
                ed_shipment_date.setText("");
            }
        }
    }

    private void initView(View view) {
        header_create_section = view.findViewById(R.id.header_create_section);
        getting_order_detail_header_section = view.findViewById(R.id.getting_order_detail_header_section);
        back_button_go_topreviousPage = view.findViewById(R.id.back_button_go_topreviousPage);
        line_cardview = view.findViewById(R.id.line_cardview);
        ed_state = view.findViewById(R.id.ed_state);
        ed_zone = view.findViewById(R.id.ed_zone);
        ed_resgion = view.findViewById(R.id.ed_resgion);
        ac_area = view.findViewById(R.id.ac_area);
        ed_tluka = view.findViewById(R.id.ed_tluka);
        ac_customer_type = view.findViewById(R.id.ac_customer_type);
        tv_name = view.findViewById(R.id.tv_name);
        tv_name2 = view.findViewById(R.id.tv_name2);
        tv_address = view.findViewById(R.id.tv_address);
        tv_addres_2 = view.findViewById(R.id.tv_addres_2);
//        ac_dispatch_loc=view.findViewById(R.id.ac_location);
        ed_dispatch_date = view.findViewById(R.id.ed_dispatch_date);
        ac_season = view.findViewById(R.id.ac_season);
        ac_sales = view.findViewById(R.id.ac_sales);
        ed_date = view.findViewById(R.id.ed_date_);
        posted_radio_grp = view.findViewById(R.id.posted_radio_grp);
        ac_ship_to = view.findViewById(R.id.ac_ship_to);
        ed_ship_toname = view.findViewById(R.id.ed_ship_toname);
        ed_ship_to_address = view.findViewById(R.id.ed_ship_to_address);
        ed_ship_post_code = view.findViewById(R.id.ed_ship_post_code);
        ed_ship_to_city = view.findViewById(R.id.ed_ship_to_city);
        ed_ship_to_method = view.findViewById(R.id.ed_ship_to_method);
        ed_ship_gst_reg = view.findViewById(R.id.ed_ship_gst_reg);
        name = view.findViewById(R.id.name);
        name_2 = view.findViewById(R.id.name_2);
        address = view.findViewById(R.id.address);
        address_2 = view.findViewById(R.id.address_2);
        ac_cust_no = view.findViewById(R.id.ac_cust_no);
        name_detail_layout = view.findViewById(R.id.name_detail_layout);
        create_header_btn = view.findViewById(R.id.create_header_btn);
        ed_shipment_date = view.findViewById(R.id.ed_shipment_date);
        delete_header = view.findViewById(R.id.delete_header);
        chip_add_line_booking = view.findViewById(R.id.chip_add_line_booking);
        line_listview = view.findViewById(R.id.line_listview);
        complete_header = view.findViewById(R.id.complete_header);
        tv_header_no = view.findViewById(R.id.tv_header_no);
        chip_update_header = view.findViewById(R.id.update_header);
        rd_posted_yes = view.findViewById(R.id.posted_yes);
        rd_posted_no = view.findViewById(R.id.posted_no);
        frame_layout_org_list = view.findViewById(R.id.frame_layout_org_list);
        loading_item = view.findViewById(R.id.loading_item);
        search_cust_input_layout = view.findViewById(R.id.search_cust_input_layout);
        lv_cust_dist_list = view.findViewById(R.id.lv_cust_dist_list);
        ship_address_list = view.findViewById(R.id.ship_address_list);
        loading_item_ship = view.findViewById(R.id.loading_item_ship);
        ship_code_search_layout = view.findViewById(R.id.ship_code_search_layout);
        ship_to_input_layout = view.findViewById(R.id.ship_to_input_layout);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        lv_cust_dist_list.setLayoutManager(layoutManager);
        LinearLayoutManager ship_layout_manager
                = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

        ship_address_list.setLayoutManager(ship_layout_manager);
        //showing_header_detail..........

        tv_cust_name = view.findViewById(R.id.tv_cust_name);
        tv_date = view.findViewById(R.id.tv_date);
        tv_sale_type = view.findViewById(R.id.tv_sale_type);
        tv_address_detail = view.findViewById(R.id.tv_address_detail);
        tv_ship_date = view.findViewById(R.id.tv_ship_date);
        tv_season_code = view.findViewById(R.id.tv_season_code);
        tv_ship_to = view.findViewById(R.id.tv_ship_to);
        ed_ship_to_contact = view.findViewById(R.id.ed_ship_to_contact);
        ed_ship_to_address2 = view.findViewById(R.id.ed_ship_to_address2);
        ed_ship_toname2 = view.findViewById(R.id.ed_ship_toname2);
        tv_cust_no = view.findViewById(R.id.tv_cust_no);
        tv_cust_type = view.findViewById(R.id.tv_cust_type);
        loading_content = view.findViewById(R.id.loading_content);


        back_button_go_topreviousPage.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });

        delete_header.setOnClickListener(v -> {
            deleteBookingHeader();
        });

        chip_add_line_booking.setOnClickListener(v -> {
            loading_item.setVisibility(View.VISIBLE);
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<MarketingIndentModel.MarketingIndentLine>> call = mAPIService.marketingIndentLineShow(marketingIndentModel.season, marketingIndentModel.customer_no);
            call.enqueue(new Callback<List<MarketingIndentModel.MarketingIndentLine>>() {
                @Override
                public void onResponse(Call<List<MarketingIndentModel.MarketingIndentLine>> call, Response<List<MarketingIndentModel.MarketingIndentLine>> response) {
                    loading_item.setVisibility(View.GONE);
                    try {
                        if (response.isSuccessful()) {
                            List<MarketingIndentModel.MarketingIndentLine> responseList = response.body();
                            if (responseList != null && responseList.get(0).condition && responseList.size() > 0) {
                                MarketingIndentModel.MarketingIndentLine marketingIndentLineModel = responseList.get(0);
                                addLine("Insert", marketingIndentLineModel);
                            } else {
                                MDToast.makeText(getActivity(), responseList.get(0).message, Toast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
                            }
                        } else {
                            MDToast.makeText(getActivity(), "No Response ", Toast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        loading_item.setVisibility(View.GONE);
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "Error!", getActivity());
                    }
                }

                @Override
                public void onFailure(Call<List<MarketingIndentModel.MarketingIndentLine>> call, Throwable t) {
                    loading_item.setVisibility(View.GONE);
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "No_line_bind", getActivity());
                }
            });

        });
        line_listview.setOnItemClickListener((parent, view1, position, id) -> {
            if (marketingIndentModel.status.equalsIgnoreCase("Approve") || marketingIndentModel.status.equalsIgnoreCase("Sent For Approval")) {
                Toast.makeText(getActivity(), "You can't update line as status is sent for approval/approve.", Toast.LENGTH_SHORT).show();
            } else {
                if (marketingIndentLineList != null && marketingIndentLineList.size() > 0) {
                    addLine("Update", marketingIndentLineList.get(position));
                    card_booking_search_layout.setVisibility(View.GONE);
                }
            }
        });

        line_listview.setOnItemLongClickListener((parent, view1, position, id) -> {
            if (marketingIndentModel.status.equalsIgnoreCase("Approve") || marketingIndentModel.status.equalsIgnoreCase("Sent For Approval")) {
                Toast.makeText(getActivity(), "You can't delete line as status is sent for approval/approve.", Toast.LENGTH_SHORT).show();
            } else {
                new MaterialAlertDialogBuilder(getActivity())
                        .setTitle("Line : " + marketingIndentLineList.get(position).line_no + " (" + marketingIndentModel.marketing_indent_no + ")")
                        .setMessage("Do you really want to delete this Line ?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteBookingLine(marketingIndentModel.marketing_indent_no, marketingIndentLineList.get(position).line_no, position);
                            }
                        })
                        .setNegativeButton("CANCEL", null)
                        .show();
            }
            return true;
        });

        complete_header.setOnClickListener(v -> {
            if (marketingIndentModel.marketing_indent_no != null && !marketingIndentModel.marketing_indent_no.equalsIgnoreCase("")) {
                new MaterialAlertDialogBuilder(getActivity())
                        .setTitle("Order" + "(" + marketingIndentModel.marketing_indent_no + ")")
                        .setMessage("Do you really want to submit this header for approval ?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                completeSendToAprovalBooking();
                            }
                        })
                        .setNegativeButton("CANCEL", null)
                        .show();
            }
        });

    }


    private void getCustomerTypeData() {
        loading_content.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<CustomerType> call = mAPIService.getCustType();
        call.enqueue(new Callback<CustomerType>() {
            @Override
            public void onResponse(Call<CustomerType> call, Response<CustomerType> response) {
                try {
                    if (response.isSuccessful()) {
                        loading_content.setVisibility(View.GONE);
                        CustomerType jsonObject = response.body();
                        if (jsonObject != null && jsonObject.Condition) {
                            List<String> cust_type_list = jsonObject.data;
                            if (cust_type_list != null && cust_type_list.size() > 0) {
                                for (int i = 0; i < cust_type_list.size(); i++) {
                                    if (!cust_type_list.get(i).equalsIgnoreCase("Prod Distributor"))
                                        customer_type_list.add(cust_type_list.get(i));
                                }
                                ItemAdapter itemAdapter = new ItemAdapter(getActivity(), R.layout.android_item_view, customer_type_list);
                                ac_customer_type.setAdapter(itemAdapter);
                            }
                        } else {
                            loading_content.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Role master record not found !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        loading_content.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    loading_content.setVisibility(View.GONE);
                    Log.e("exception database", e.getMessage() + "cause");
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "get_customer_type", getActivity());
                } finally {
                    loading_content.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<CustomerType> call, Throwable t) {
                loading_content.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_customer_type", getActivity());
            }
        });
    }


    private void insertHeader() {
        ac_cust_no.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                search_cust_input_layout.setStartIconDrawable(null);
            } else {
                if (!ac_cust_no.getText().toString().trim().equalsIgnoreCase("")) {
                    search_cust_input_layout.setStartIconDrawable(null);
                } else {
                    search_cust_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                }
            }
        });

        ac_customer_type.setOnItemClickListener((parent, view, position, id) -> {
            String customer_type = customer_type_list.get(position);
            ac_customer_type.setText(customer_type);
        });

        ac_cust_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ac_cust_no.setSelection(s.toString().length());
                if (!ac_customer_type.getText().toString().trim().equalsIgnoreCase("")) {
                    if (!s.toString().equalsIgnoreCase("")) {
                        frame_layout_org_list.setVisibility(View.VISIBLE);
                        name_detail_layout.setVisibility(View.VISIBLE);
                        getRoleMasterData(ac_customer_type.getText().toString().trim(), s.toString());
                    } else {
                        tv_name.setText("");
                        tv_name2.setText("");
                        tv_address.setText("");
                        tv_addres_2.setText("");
                        ed_state.setText("");
                        ed_zone.setText("");
                        ed_resgion.setText("");
                        ac_area.setText("");
                        ed_tluka.setText("");
                        ac_ship_to.setText("");
                        ed_ship_toname.setText("");
                        ed_ship_to_address.setText("");
                        ed_ship_to_address2.setText("");
                        ed_ship_post_code.setText("");
                        ed_ship_to_city.setText("");
                        ed_ship_gst_reg.setText("");
                        ed_date.setText("");
                        name_detail_layout.setVisibility(View.GONE);
                        frame_layout_org_list.setVisibility(View.GONE);
                    }
                } else {
                    name_detail_layout.setVisibility(View.GONE);
                    ed_state.setText("");
                    ed_zone.setText("");
                    ed_resgion.setText("");
                    ac_area.setText("");
                    ed_tluka.setText("");
                    tv_name.setText("");
                    tv_name2.setText("");
                    tv_address.setText("");
                    tv_addres_2.setText("");
                    ac_ship_to.setText("");
                    ed_ship_toname.setText("");
                    ed_ship_to_address.setText("");
                    ed_ship_to_address2.setText("");
                    ed_ship_post_code.setText("");
                    ed_ship_to_city.setText("");
                    ed_ship_gst_reg.setText("");
                    ed_date.setText("");
                    ac_cust_no.setError("Select First customer Type !");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        ac_ship_to.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                ship_to_input_layout.setStartIconDrawable(null);
            } else {
                if (!ac_ship_to.getText().toString().trim().equalsIgnoreCase("")) {
                    ship_to_input_layout.setStartIconDrawable(null);
                } else {
                    ship_to_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                }
            }
        });

        ac_ship_to.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (roleMasterTable != null) {
                    if (roleMasterTable.no != null && !roleMasterTable.no.equalsIgnoreCase("")) {
                        if (!s.toString().equalsIgnoreCase("")) {
                            ship_code_search_layout.setVisibility(View.VISIBLE);
                            getShipmentToAddressData(roleMasterTable.no, s.toString());
                        } else {
                            ship_code_search_layout.setVisibility(View.GONE);
                            ed_ship_toname.setText("");
                            ed_ship_to_address.setText("");
                            ed_ship_to_address2.setText("");
                            ed_ship_post_code.setText("");
                            ed_ship_to_city.setText("");
                            ed_ship_to_method.setText("");
                            ed_ship_gst_reg.setText("");
                            ship_address2 = "";
                            ship_contact = "";
                            ship_state_code = "";
                            ship_agent_code = "";
                            ship_agent_service_code = "";
                        }
                    } else {
                        ac_ship_to.setError("Please select first cust no. ! ");
                        ship_code_search_layout.setVisibility(View.GONE);
                        ed_ship_toname.setText("");
                        ed_ship_to_address.setText("");
                        ed_ship_to_address2.setText("");
                        ed_ship_post_code.setText("");
                        ed_ship_to_city.setText("");
                        ed_ship_to_method.setText("");
                        ed_ship_gst_reg.setText("");
                        ship_address2 = "";
                        ship_contact = "";
                        ship_state_code = "";
                        ship_agent_code = "";
                        ship_agent_service_code = "";
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        ItemAdapter sale_adapter = new ItemAdapter(getActivity(), R.layout.android_item_view, Arrays.asList(CommonData.sales_type));
        ac_sales.setAdapter(sale_adapter);

        ed_dispatch_date.setOnTouchListener((v, event) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_dispatch_date);
            return true;
        });
        ed_shipment_date.setOnTouchListener((v, event) -> {
            new CustomDatePicker(getActivity()).showDatePickerDialog(ed_shipment_date);
            return true;
        });

        posted_radio_grp.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.posted_yes:
                    postedString = "1";
                    break;
                case R.id.posted_no:
                    postedString = "0";
                    break;
            }
        });
        create_header_btn.setOnClickListener(v -> {
            if (ac_customer_type.getText().toString().trim().equalsIgnoreCase("")) {
                Toast.makeText(getActivity(), "Please select customer.", Toast.LENGTH_SHORT).show();
                return;
            } else if (ed_date.getText().toString().trim().equalsIgnoreCase("")) {
                Toast.makeText(getActivity(), "Please select date", Toast.LENGTH_SHORT).show();
                return;
            } else if (posted_radio_grp.getCheckedRadioButtonId() == -1) {
                Toast.makeText(getActivity(), "Please select posted", Toast.LENGTH_SHORT).show();
                return;
            } else if (ac_cust_no.getText().toString().trim().equalsIgnoreCase("")) {
                Toast.makeText(getActivity(), "Please select cust. no", Toast.LENGTH_SHORT).show();
                return;
            } else if (ed_dispatch_date.getText().toString().trim().equalsIgnoreCase("")) {
                Toast.makeText(getActivity(), "Please select dispatch_date", Toast.LENGTH_SHORT).show();
                return;
            } /*else if (ac_dispatch_loc.getText().toString().trim().equalsIgnoreCase("")) {
                Toast.makeText(getActivity(), "Pleasse select dispatch location", Toast.LENGTH_SHORT).show();
                return;
            }*/ else if (ac_sales.getText().toString().trim().equalsIgnoreCase("")) {

                Toast.makeText(getActivity(), "Please select sales type", Toast.LENGTH_SHORT).show();
                return;
            } else if (ed_zone.getText().toString().trim().equalsIgnoreCase("")) {
                Toast.makeText(getActivity(), "Please enter zone code.", Toast.LENGTH_SHORT).show();
                return;
            } else {
                MarketingIndentModel marketingIndentModel = new MarketingIndentModel();
                marketingIndentModel.date = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_date.getText().toString().trim());
                marketingIndentModel.posted = postedString;
                marketingIndentModel.season = season_code;
                no_series = no_series + 1;
                marketingIndentModel.no_series = String.valueOf(no_series);
                marketingIndentModel.customer_no = roleMasterTable.no != null ? roleMasterTable.no : "";
                marketingIndentModel.customer_name = ac_cust_no.getText().toString().trim();
                marketingIndentModel.name = name.getText().toString().trim();
                marketingIndentModel.name_2 = name_2.getText().toString().trim();
                marketingIndentModel.address = address.getText().toString().trim();
                marketingIndentModel.address_2 = address_2.getText().toString().trim();
                marketingIndentModel.zone = !zone_code.equalsIgnoreCase("") ? zone_code : "";
                marketingIndentModel.state = !state_code.equalsIgnoreCase("") ? state_code : "";
                marketingIndentModel.region = !region_code.equalsIgnoreCase("") ? region_code : "";
                marketingIndentModel.area = !area_code.equalsIgnoreCase("") ? area_code : "";
                marketingIndentModel.territory = !taluka_code.equalsIgnoreCase("") ? taluka_code : "";
                marketingIndentModel.location_code = "";
                marketingIndentModel.zone_name = ed_zone.getText().toString().trim();
                marketingIndentModel.state_name = ed_state.getText().toString().trim();
                marketingIndentModel.territory_name = ed_tluka.getText().toString().trim();
                marketingIndentModel.region_name = ed_resgion.getText().toString().trim();
                marketingIndentModel.area_name = ac_area.getText().toString().trim();
                marketingIndentModel.customer_type = ac_customer_type.getText().toString().trim();
                marketingIndentModel.ship_to_code = ac_ship_to.getText().toString().trim();
                marketingIndentModel.ship_to_name = ed_ship_toname.getText().toString().trim();
                marketingIndentModel.ship_to_name_2 = ed_ship_toname2.getText().toString().trim();
                marketingIndentModel.ship_to_address = ed_ship_to_address.getText().toString().trim();
                marketingIndentModel.ship_to_address_2 = ed_ship_to_address2.getText().toString().trim();
                marketingIndentModel.ship_to_postcode = ed_ship_post_code.getText().toString().trim();
                marketingIndentModel.ship_to_city = ed_ship_to_city.getText().toString().trim();
                marketingIndentModel.ship_to_contact = ed_ship_to_contact.getText().toString().trim();
                marketingIndentModel.date = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_date.getText().toString().trim());
                marketingIndentModel.region_code = !ship_region.equalsIgnoreCase("") ? ship_region : "null";
                marketingIndentModel.ship_to_county = "IN";
                if (ed_shipment_date.getText().toString().trim().equalsIgnoreCase("")) {
                    marketingIndentModel.shipment_date = "";
                } else {
                    marketingIndentModel.shipment_date = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_shipment_date.getText().toString().trim());
                }
                marketingIndentModel.GST_ship_to_state_code = !ship_state_code.equalsIgnoreCase("") ? ship_state_code : "null";
                marketingIndentModel.ship_to_gst_reg_no = !ed_ship_gst_reg.getText().toString().trim().equalsIgnoreCase("") ? ed_ship_gst_reg.getText().toString().trim() : "null";
                marketingIndentModel.shipment_method_code = !ed_ship_to_method.getText().toString().trim().equalsIgnoreCase("") ? ed_ship_to_method.getText().toString().trim() : "null";
                marketingIndentModel.shipping_agent_code = !ship_agent_code.equalsIgnoreCase("") ? ship_agent_code : "null";
                marketingIndentModel.shipping_agent_service_code = !ship_agent_service_code.equalsIgnoreCase("") ? ship_agent_service_code : "null";
                marketingIndentModel.dispatch_location_code = "";
                marketingIndentModel.sales_type = ac_sales.getText().toString().trim();
                marketingIndentModel.dispatch_by_date = DateTimeUtilsCustome.splitDateInYYYMMDD(ed_dispatch_date.getText().toString().trim());
                marketingIndentModel.created_by = sessionManagement.getUserEmail();
                marketingIndentModel.approver_id = sessionManagement.getApprover_id();
                insertUpdateHeaderApi(marketingIndentModel);
            }
        });
    }

    private void insertUpdateHeaderApi(MarketingIndentModel marketingIndentModel) {
        String jsonString = new Gson().toJson(marketingIndentModel);
        JsonObject asJsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            LoadingDialog progressDialog = new LoadingDialog();
            progressDialog.showLoadingDialog(getActivity());
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<BookingResponseModel>> call = mAPIService.insertMarketingHeader(asJsonObject);
            call.enqueue(new Callback<List<BookingResponseModel>>() {
                @Override
                public void onResponse(Call<List<BookingResponseModel>> call, Response<List<BookingResponseModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialog.hideDialog();
                            List<BookingResponseModel> bookingResponseList = response.body();
                            if (bookingResponseList != null && bookingResponseList.get(0).condition) {
                                getParentFragmentManager().popBackStack();
                                Toast.makeText(getActivity(), bookingResponseList.get(0).message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), bookingResponseList.size() > 0 ? "No data found" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialog.hideDialog();
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        progressDialog.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "insert_order_header", getActivity());
                    }
                }

                @Override
                public void onFailure(Call<List<BookingResponseModel>> call, Throwable t) {
                    progressDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "insert_order_header", getActivity());
                }
            });
        } else {
            Toast.makeText(getActivity(), "please wait for internet connection", Toast.LENGTH_SHORT).show();
        }
    }


    private void getRoleMasterData(String cust_type, String filter_key) {
        loading_item.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<RoleMasterModel> call = mAPIService.getDistributor(cust_type, filter_key, sessionManagement.getSalePersonCode());
        call.enqueue(new Callback<RoleMasterModel>() {
            @Override
            public void onResponse(Call<RoleMasterModel> call, Response<RoleMasterModel> response) {
                try {
                    if (response.isSuccessful()) {
                        loading_item.setVisibility(View.GONE);
                        RoleMasterModel roleMasterModelList = response.body();
                        if (roleMasterModelList != null && roleMasterModelList.condition) {
                            List<RoleMasterModel.Data> rolemasterList = roleMasterModelList.data;
                            if (rolemasterList != null && rolemasterList.size() > 0 && rolemasterList.get(0).no != null) {
                                role_no_list = rolemasterList;
                               /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                    List<RoleMasterModel.Data> roleMasterModelslist = role_no_list.stream().filter(data -> data.district_Name.equalsIgnoreCase(dist_name))
                                            .collect(Collectors.toList());*/
                                setAdapter(role_no_list);
                                // }
                            } else {
                                lv_cust_dist_list.setAdapter(null);
                                frame_layout_org_list.setVisibility(View.GONE);
                                loading_item.setVisibility(View.GONE);
                            }
                        } else {
                            lv_cust_dist_list.setAdapter(null);
                            loading_item.setVisibility(View.GONE);
                            frame_layout_org_list.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Role master record not found !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        frame_layout_org_list.setVisibility(View.GONE);
                        loading_item.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    loading_item.setVisibility(View.GONE);
                    Log.e("exception database", e.getMessage() + "cause");
                    //   Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "get_customer_no", getActivity());
                }
            }

            @Override
            public void onFailure(Call<RoleMasterModel> call, Throwable t) {
                loading_item.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_customer_no", getActivity());
            }
        });
    }


    private void setAdapter(List<RoleMasterModel.Data> role_no_list) {
        RoleMasterAdapter roleMasterAdapter = new RoleMasterAdapter(getActivity(), role_no_list);
        lv_cust_dist_list.setAdapter(roleMasterAdapter);
        roleMasterAdapter.setOnClick(this);

    }


    private void getSeasonMaster() {
        loading_content.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<List<SeasonMasterModel>> call = mAPIService.getSeasonMaster();
        call.enqueue(new Callback<List<SeasonMasterModel>>() {
            @Override
            public void onResponse(Call<List<SeasonMasterModel>> call, Response<List<SeasonMasterModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        loading_content.setVisibility(View.GONE);
                        List<SeasonMasterModel> templantingList_season = response.body();
                        if (templantingList_season != null && templantingList_season.size() > 0) {
                            seasonMasterTableList = templantingList_season;
                            SeasonMasterAdapter seasonMasterAdapter = new SeasonMasterAdapter(getActivity(), R.layout.android_item_view, seasonMasterTableList);
                            ac_season.setAdapter(seasonMasterAdapter);
                        } else {
                            loading_content.setVisibility(View.GONE);
                            ac_season.setText("");
                            Toast.makeText(getActivity(), templantingList_season != null && templantingList_season.size() > 0 ? "Season Record not found !" : "Api not respoding.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        loading_content.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    loading_content.setVisibility(View.GONE);
                    Log.e("exception database", e.getMessage() + "cause");
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "get_season", getActivity());
                } finally {
                    loading_content.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<SeasonMasterModel>> call, Throwable t) {
                loading_content.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_season", getActivity());
            }
        });
    }

    private void getShipmentToAddressData(String cust_no, String filter_key) {
        loading_item_ship.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<ShipToAddressModel> call = mAPIService.getShipToAddressData(cust_no, filter_key);
        call.enqueue(new Callback<ShipToAddressModel>() {
            @Override
            public void onResponse(Call<ShipToAddressModel> call, Response<ShipToAddressModel> response) {
                try {
                    if (response.isSuccessful()) {
                        ShipToAddressModel shipToAddressModel = response.body();
                        if (shipToAddressModel != null && shipToAddressModel.condition) {
                            shipToAddressDataTables_list = shipToAddressModel.data;
                            if (shipToAddressDataTables_list != null && shipToAddressDataTables_list.size() > 0) {
                                setShipCodeAdapter();
                            } else {
                                ship_code_search_layout.setVisibility(View.GONE);
                                ship_address_list.setAdapter(null);
                                loading_item_ship.setVisibility(View.GONE);
                            }
                        } else {
                            ship_code_search_layout.setVisibility(View.GONE);
                            loading_item_ship.setVisibility(View.GONE);

//                            ac_dispatch_loc.setAdapter(null);
                        }
                    } else {
                        ship_code_search_layout.setVisibility(View.GONE);
                        loading_item_ship.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    loading_item_ship.setVisibility(View.GONE);
                    ship_code_search_layout.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "ship_to_address", getActivity());
                }
            }

            @Override
            public void onFailure(Call<ShipToAddressModel> call, Throwable t) {
                loading_item_ship.setVisibility(View.GONE);
                ship_code_search_layout.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "ship_to_address", getActivity());
            }
        });

    }

    private void setShipCodeAdapter() {
        ShipToDetailAdapter shipToDetailAdapter = new ShipToDetailAdapter(getActivity(), shipToAddressDataTables_list);
        ship_address_list.setAdapter(shipToDetailAdapter);
        shipToDetailAdapter.setOnClick(this);
    }

    private void getDispatchLocation() {
        loading_content.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<List<UserLocationModel>> call = mAPIService.getUserLocation(sessionManagement.getUserEmail());
        call.enqueue(new Callback<List<UserLocationModel>>() {
            @Override
            public void onResponse(Call<List<UserLocationModel>> call, Response<List<UserLocationModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        List<UserLocationModel> user_locastion_masterlist = response.body();
                        if (user_locastion_masterlist != null && user_locastion_masterlist.size() > 0) {
                            locationMasterTableList = user_locastion_masterlist;
                            LocationMasterAdapter locationMasterAdapter = new LocationMasterAdapter(getActivity(), R.layout.android_item_view, locationMasterTableList);
                            ac_dispatch_loc.setAdapter(locationMasterAdapter);
                        } else {
                            ac_dispatch_loc.setAdapter(null);
                        }
                    } else {
                        loading_content.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    loading_content.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "user_location_master", getActivity());
                }
            }

            @Override
            public void onFailure(Call<List<UserLocationModel>> call, Throwable t) {
                loading_content.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "user_location_master", getActivity());
            }
        });
    }


    private void setHeaderDetail() {
        if (marketingIndentModel != null) {
            if (marketingIndentModel.marketing_indent_no != null) {
                tv_header_no.setText("(" + marketingIndentModel.marketing_indent_no + ")");
            } else {
                tv_header_no.setText("");
            }
            tv_cust_name.setText(marketingIndentModel.name);
            tv_date.setText(marketingIndentModel.date);
            tv_sale_type.setText(marketingIndentModel.sales_type);
            tv_address_detail.setText(marketingIndentModel.address);
            tv_ship_date.setText(marketingIndentModel.shipment_date);
            tv_season_code.setText(marketingIndentModel.season);
            tv_ship_to.setText(marketingIndentModel.ship_to_code);
            tv_cust_no.setText(marketingIndentModel.customer_no);
            tv_cust_type.setText(marketingIndentModel.customer_type);


            if (marketingIndentModel == null && marketingIndentModel.marketing_indent_no == null) {
                header_create_section.setVisibility(View.VISIBLE);
            }

            if (marketingIndentModel.marketing_indent_line != null && marketingIndentModel.marketing_indent_line.size() > 0 && marketingIndentModel.marketing_indent_line.get(0).line_no != null) {

                complete_header.setVisibility(View.VISIBLE);
                if (marketingIndentModel.status.equalsIgnoreCase("Approve") ||
                        marketingIndentModel.status.equalsIgnoreCase("Sent For Approval")) {
                    chip_add_line_booking.setVisibility(View.GONE);
                    delete_header.setVisibility(View.GONE);
                    complete_header.setVisibility(View.GONE);
                } else if (marketingIndentModel.status.equalsIgnoreCase("Reject")) {
                    chip_add_line_booking.setVisibility(View.GONE);
                    delete_header.setVisibility(View.GONE);
                    complete_header.setVisibility(View.VISIBLE);
                    chip_update_header.setVisibility(View.VISIBLE);
                }

                for (int i = 0; i < marketingIndentModel.marketing_indent_line.size(); i++) {
                    MarketingIndentModel.MarketingIndentLine marketingIndentLine = new MarketingIndentModel().new MarketingIndentLine();
                    marketingIndentLine.marketing_indent_no = marketingIndentModel.marketing_indent_line.get(i).marketing_indent_no;
                    marketingIndentLine.crop_code = marketingIndentModel.marketing_indent_line.get(i).crop_code;
                    marketingIndentLine.variety_group = marketingIndentModel.marketing_indent_line.get(i).variety_group;
                    marketingIndentLine.do_created = marketingIndentModel.marketing_indent_line.get(i).do_created;
                    marketingIndentLine.posted = marketingIndentModel.marketing_indent_line.get(i).posted;
                    marketingIndentLine.unit_of_measure_code = marketingIndentModel.marketing_indent_line.get(i).unit_of_measure_code;
                    marketingIndentLine.variety_code = marketingIndentModel.marketing_indent_line.get(i).variety_code;
                    marketingIndentLine.variety_name = marketingIndentModel.marketing_indent_line.get(i).variety_name;
                    marketingIndentLine.variety_package_size = marketingIndentModel.marketing_indent_line.get(i).variety_package_size;
                    marketingIndentLine.variety_class_of_seeds = marketingIndentModel.marketing_indent_line.get(i).variety_class_of_seeds;
                    marketingIndentLine.variety_product_group_code = marketingIndentModel.marketing_indent_line.get(i).variety_product_group_code;
                    marketingIndentLine.no_of_bags = marketingIndentModel.marketing_indent_line.get(i).no_of_bags;
                    marketingIndentLine.booking_indent_no = marketingIndentModel.marketing_indent_line.get(i).booking_indent_no;
                    marketingIndentLine.nav_booking_indent_no = marketingIndentModel.marketing_indent_line.get(i).nav_booking_indent_no;
                    marketingIndentLine.indent_qty = marketingIndentModel.marketing_indent_line.get(i).indent_qty;
                    marketingIndentLine.unit_price = marketingIndentModel.marketing_indent_line.get(i).unit_price;
                    marketingIndentLine.line_discount_percent = marketingIndentModel.marketing_indent_line.get(i).line_discount_percent;
                    marketingIndentLine.created_by = marketingIndentModel.marketing_indent_line.get(i).created_by;
                    marketingIndentLine.line_no = marketingIndentModel.marketing_indent_line.get(i).line_no;
                    marketingIndentLine.supplies_qty = marketingIndentModel.marketing_indent_line.get(i).supplies_qty;
                    marketingIndentLine.balance_qty = marketingIndentModel.marketing_indent_line.get(i).balance_qty;
                    marketingIndentLine.alotted_qty = marketingIndentModel.marketing_indent_line.get(i).alotted_qty;
                    marketingIndentLine.alotted_percent = marketingIndentModel.marketing_indent_line.get(i).alotted_percent;
                    marketingIndentLine.created_on = marketingIndentModel.marketing_indent_line.get(i).created_on;
                    marketingIndentLine.booking_qty = marketingIndentModel.marketing_indent_line.get(i).booking_qty;
                    marketingIndentLineList.add(marketingIndentLine);
                }
                bindDataWithAdapter(marketingIndentModel.marketing_indent_no, marketingIndentLineList);
            }
        }
    }

    private void deleteBookingHeader() {
        if (marketingIndentModel.marketing_indent_line.get(0).line_no != null
                && !marketingIndentModel.marketing_indent_line.get(0).line_no.equalsIgnoreCase("") && marketingIndentModel.marketing_indent_line.size() > 0) {
            Toast.makeText(getActivity(), "You can't delete header as lines exist ! ", Toast.LENGTH_SHORT).show();
            return;
        } else {
            new MaterialAlertDialogBuilder(getActivity())
                    .setTitle("Header" + "(" + marketingIndentModel.marketing_indent_no + ")")
                    .setMessage("Do you want to delete this header ?")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
                                LoadingDialog progressDialog = new LoadingDialog();
                                progressDialog.showLoadingDialog(getActivity());
                                NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                                Call<List<BookingResponseModel>> call = mAPIService.deleteBookingHeader(marketingIndentModel.marketing_indent_no);
                                call.enqueue(new Callback<List<BookingResponseModel>>() {
                                    @Override
                                    public void onResponse(Call<List<BookingResponseModel>> call, Response<List<BookingResponseModel>> response) {
                                        try {
                                            if (response.isSuccessful()) {
                                                progressDialog.hideDialog();
                                                List<BookingResponseModel> bookingResponseModelList = response.body();
                                                if (bookingResponseModelList != null && bookingResponseModelList.get(0).condition) {
                                                    if (bookingResponseModelList.size() > 0) {
                                                        marketingIndentModel = null;
                                                        tv_header_no.setText("");
                                                        header_create_section.setVisibility(View.VISIBLE);
                                                        getting_order_detail_header_section.setVisibility(View.GONE);
                                                        line_cardview.setVisibility(View.GONE);
                                                        clearAllValues();
                                                    }
                                                } else {
                                                    Toast.makeText(getActivity(), bookingResponseModelList.size() > 0 ? bookingResponseModelList.get(0).message : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                progressDialog.hideDialog();
                                                Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (Exception e) {
                                            progressDialog.hideDialog();
                                            ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "delete_booking_header", getActivity());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<BookingResponseModel>> call, Throwable t) {
                                        progressDialog.hideDialog();
                                        ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "delete_booking_header", getActivity());
                                    }
                                });
                            } else {
                                Toast.makeText(getActivity(), "please wait for internet connection", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton("CANCEL", null)
                    .show();
        }
    }

    private void clearAllValues() {
        ed_date.setText("");
        posted_radio_grp.clearCheck();
        ac_season.setText("");
        ac_cust_no.setText("");
        name_detail_layout.setVisibility(View.GONE);
//        ac_dispatch_loc.setText("");
        ed_zone.setText("");
        ed_state.setText("");
        ed_tluka.setText("");
        ed_resgion.setText("");
        ac_area.setText("");
        ac_customer_type.setText("");
        ac_ship_to.setText("");
        ed_ship_toname.setText("");
        ed_ship_to_address.setText("");
        ed_ship_post_code.setText("");
        ed_ship_to_city.setText("");
        ed_shipment_date.setText("");
        ed_ship_gst_reg.setText("");
        ed_ship_to_method.setText("");
        ac_sales.setText("");
        ed_dispatch_date.setText("");
    }

    private MaterialCardView card_booking_search_layout;
    private TextInputLayout search_booking_input_layout;

    private void addLine(String flag, MarketingIndentModel.MarketingIndentLine marketingIndentLineModel) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View popupView = inflater.inflate(R.layout.add_marketing_indent_line_layout_pop_up, null);
        Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar);
        dialog.setContentView(popupView);
        dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_FullScreenDialog;
        dialog.show();
        RadioGroup posted_radio_grp;
        Button create_header_btn, view_update_line;
        LinearLayout back_button_go_topreviousPage;
        ImageView close_line = popupView.findViewById(R.id.close_line);
        ed_class_of_seed = popupView.findViewById(R.id.ed_class_of_seed);
        ed_varity_product_group_code = popupView.findViewById(R.id.ed_varity_product_group_code);
        ed_no_of_bags = popupView.findViewById(R.id.ed_no_of_bags);
        ed_indent_qty = popupView.findViewById(R.id.ed_indent_qty);
        ed_booking_qty = popupView.findViewById(R.id.ed_booking_qty);
        ed_alloted_qty = popupView.findViewById(R.id.ed_alloted_qty);
        ed_suppply_qty = popupView.findViewById(R.id.ed_suppply_qty);
        ed_unit_price = popupView.findViewById(R.id.ed_unit_price);
        ac_variety_code = popupView.findViewById(R.id.ac_variety_code);
        ed_variety_name = popupView.findViewById(R.id.ed_variety_name);
        ed_varity_pack_size = popupView.findViewById(R.id.ed_varity_pack_size);
        ed_balance_qty = popupView.findViewById(R.id.ed_balance_qty);
        ac_marketing_indent_no = popupView.findViewById(R.id.ac_marketing_indent_no);
        ac_crop_code = popupView.findViewById(R.id.ac_crop_code);
        ed_varity_grp = popupView.findViewById(R.id.ed_varity_grp);
        ac_umo = popupView.findViewById(R.id.ac_umo);
        create_header_btn = popupView.findViewById(R.id.create_header_btn);
        ed_line_discount_per = popupView.findViewById(R.id.ed_line_discount_per);
        posted_radio_grp = popupView.findViewById(R.id.posted_radio_grp);
        back_button_go_topreviousPage = popupView.findViewById(R.id.back_button_go_topreviousPage);
        line_posted_yes = popupView.findViewById(R.id.posted_yes);
        line_posted_yes = popupView.findViewById(R.id.posted_no);
        view_update_line = popupView.findViewById(R.id.view_update_line);
        ed_alloted_per = popupView.findViewById(R.id.ed_alloted_per);
        card_booking_search_layout = popupView.findViewById(R.id.card_booking_search_layout);
        ProgressBar search_loading_item = popupView.findViewById(R.id.loading_item);
        RecyclerView lv_bookingno_list = popupView.findViewById(R.id.lv_bookingno_list);
        search_booking_input_layout = popupView.findViewById(R.id.search_booking_input_layout);

        close_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        MaterialProgressBar content_loading = popupView.findViewById(R.id.content_loading);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        lv_bookingno_list.setLayoutManager(layoutManager);

        if (marketingIndentLineModel != null || !marketingIndentLineModel.No.equalsIgnoreCase("")) {
            ac_marketing_indent_no.setText(marketingIndentLineModel.No);
            ac_marketing_indent_no.setSelection(ac_marketing_indent_no.getText().length());
            getBookingIndentNo(search_loading_item, lv_bookingno_list,tv_cust_no.getText().toString(), ac_marketing_indent_no);
        }

       /* ac_marketing_indent_no.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                search_booking_input_layout.setStartIconDrawable(null);
            } else {
                if (!ac_marketing_indent_no.getText().toString().trim().equalsIgnoreCase("")) {
                    search_booking_input_layout.setStartIconDrawable(null);
                } else {
                    search_booking_input_layout.setStartIconDrawable(R.drawable.ic_baseline_search_24);
                }
            }
        });

        ac_marketing_indent_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ac_marketing_indent_no.setSelection(s.toString().length());
                if (!s.toString().equals("")) {
                    card_booking_search_layout.setVisibility(View.VISIBLE);
                    getBookingIndentNo(search_loading_item, lv_bookingno_list,tv_cust_no.getText().toString(),s.toString());

                } else {
                    ac_crop_code.setText("");
                    ac_variety_code.setText("");
                    ed_varity_grp.setText("");
                    ed_variety_name.setText("");
                    ed_varity_pack_size.setText("");
                    ed_class_of_seed.setText("");
                    ed_varity_product_group_code.setText("");
                    ed_no_of_bags.setText("");
                    ed_alloted_per.setText("");
                    ed_balance_qty.setText("");
                    ed_booking_qty.setText("");
                    ed_indent_qty.setText("");
                    ed_suppply_qty.setText("");
                    ed_unit_price.setText("");
                    ed_line_discount_per.setText("");
                    ed_alloted_qty.setText("");
                    ac_umo.setText("");
                    card_booking_search_layout.setVisibility(View.GONE);
                    posted_yes.setChecked(false);
                    posted_no.setChecked(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

        getUnitOfMeasureData(content_loading, ac_umo);
        back_button_go_topreviousPage.setOnClickListener(v -> {
            dialog.dismiss();
        });

       /* posted_radio_grp.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.posted_yes:
                    postedString = "1";
                    break;

                case R.id.posted_no:
                    postedString = "0";
                    break;
            }
        });*/

        ed_indent_qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!ed_indent_qty.getText().toString().trim().equalsIgnoreCase("") &&
                        !ed_indent_qty.getText().toString().equalsIgnoreCase(".") &&
                        !ed_indent_qty.getText().toString().trim().equalsIgnoreCase(".0") &&
                        !ed_indent_qty.getText().toString().trim().equalsIgnoreCase("0.")) {

                    if (!ed_alloted_qty.getText().toString().trim().equalsIgnoreCase("") &&
                            !ed_alloted_qty.getText().toString().trim().equalsIgnoreCase(".0") &&
                            !ed_alloted_qty.getText().toString().trim().equalsIgnoreCase("0.")) {

//                         float indent_qty = Float.parseFloat(ed_indent_qty.getText().toString().trim());
                        float allotd_qty = Float.parseFloat(ed_alloted_qty.getText().toString().trim());
                        if (!ed_balance_qty.getText().toString().trim().equalsIgnoreCase("")) {
                            try {
//                                float suply_qty = Float.parseFloat(!ed_suppply_qty.getText().toString().trim().equals("") ? ed_suppply_qty.getText().toString().trim() : "0");
                                //todo new change for balance calculation...
                                float indent_qty = Float.parseFloat(!ed_indent_qty.getText().toString().trim().equals("") ? ed_indent_qty.getText().toString().trim() : "0");
                                float balance_qty = allotd_qty - indent_qty;
                                ed_balance_qty.setText(String.valueOf(balance_qty));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
//                                float suply_qty = Float.parseFloat(!ed_suppply_qty.getText().toString().trim().equals("") ? ed_suppply_qty.getText().toString().trim() : "0");
                                float indent_qty = Float.parseFloat(!ed_indent_qty.getText().toString().trim().equals("") ? ed_indent_qty.getText().toString().trim() : "0");
                                float balance_qty = allotd_qty - indent_qty;
                                ed_balance_qty.setText(String.valueOf(balance_qty));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    ed_balance_qty.setText("0");
                }
            }
        });

        if (flag.equalsIgnoreCase("Insert")) {
            create_header_btn.setOnClickListener(v -> {
                if (posted_radio_grp.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getActivity(), "Please select posted", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ac_marketing_indent_no.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please select marketing indent no.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ac_crop_code.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please select crop code", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_indent_qty.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "please enter indent qty.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ac_variety_code.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please select varity code", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_no_of_bags.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please select no. of bags.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ac_umo.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please select unit of measure code.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (order_booking_indent_no == null || order_booking_indent_no.equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Booking indent no is blank.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (marketingIndentLineList.size() == 0) {
                        insertMarketingIndentLine(dialog);
                    } else {
                        // if (!ed_varity_grp.getText().toString().trim().equalsIgnoreCase("")) {
                        for (int k = 0; k < marketingIndentLineList.size(); k++) {
                            if (marketingIndentLineList.get(k).variety_group != null) {
                                if (marketingIndentLineList.get(k).variety_group.equalsIgnoreCase(ed_varity_grp.getText().toString().trim())) {
                                    flag_execute = "0";
                                    Toast.makeText(getActivity(), "You can't select same variety group twice", Toast.LENGTH_SHORT).show();
                                    break;
                                } else {
                                    flag_execute = "1";
                                    continue;
                                }
                            }
                        }
                    }
                    if (flag_execute.equalsIgnoreCase("1")) {
                        insertMarketingIndentLine(dialog);
                    }
                }
            });
        } else if (flag.equalsIgnoreCase("Update")) {
            card_booking_search_layout.setVisibility(View.GONE);
            view_update_line.setVisibility(View.VISIBLE);
            create_header_btn.setVisibility(View.GONE);

            if (marketingIndentLineModel.nav_booking_indent_no != null && !marketingIndentLineModel.nav_booking_indent_no.equalsIgnoreCase("")) {
                order_booking_indent_no = marketingIndentLineModel.nav_booking_indent_no;
            } else {
                order_booking_indent_no = "";
            }

            ac_crop_code.setText(marketingIndentLineModel.crop_code);
            ed_varity_grp.setText(marketingIndentLineModel.variety_group);
            tv_header_no.setText("(" + marketingIndentLineModel.booking_indent_no + ")");

            if (!marketingIndentLineModel.posted.equalsIgnoreCase("")) {
                if (marketingIndentLineModel.posted.equalsIgnoreCase("1")) {
                    line_posted_yes.setChecked(true);
                } else if (marketingIndentLineModel.posted.equalsIgnoreCase("0")) {
                    line_posted_yes.setChecked(true);
                }
            }
            ac_umo.setText(marketingIndentLineModel.unit_of_measure_code);
            ac_variety_code.setText(marketingIndentLineModel.variety_code);
            ed_variety_name.setText(marketingIndentLineModel.variety_name);
            ed_varity_pack_size.setText(marketingIndentLineModel.variety_package_size);
            ed_class_of_seed.setText(marketingIndentLineModel.variety_class_of_seeds);
            ed_varity_product_group_code.setText(marketingIndentLineModel.variety_product_group_code);
            ed_no_of_bags.setText(marketingIndentLineModel.no_of_bags);
            ac_marketing_indent_no.setText(marketingIndentLineModel.booking_indent_no);
            ed_indent_qty.setText(marketingIndentLineModel.indent_qty);
            ed_unit_price.setText(marketingIndentLineModel.unit_price);
            ed_line_discount_per.setText(marketingIndentLineModel.line_discount_percent);
            ed_alloted_qty.setText(marketingIndentLineModel.alotted_qty);
            ed_balance_qty.setText(marketingIndentLineModel.balance_qty);
            ed_suppply_qty.setText(marketingIndentLineModel.supplies_qty);
            ed_booking_qty.setText(marketingIndentLineModel.booking_qty);
            ed_alloted_per.setText(marketingIndentLineModel.alotted_percent);

            if (!ed_indent_qty.getText().toString().trim().equalsIgnoreCase("")) {
                if (!ed_alloted_qty.getText().toString().trim().equalsIgnoreCase("")) {
                    float indent_qty = Float.parseFloat(ed_indent_qty.getText().toString().trim());
                    float allotd_qty = Float.parseFloat(ed_alloted_qty.getText().toString().trim());

                    float balance_qty = indent_qty - allotd_qty;
                    ed_balance_qty.setText(String.valueOf(balance_qty));

                    if (!ed_balance_qty.getText().toString().trim().equalsIgnoreCase("") &&
                            !ed_booking_qty.getText().toString().trim().equalsIgnoreCase("")) {
                        float booking_qty = Float.parseFloat(ed_booking_qty.getText().toString().trim());
                        float blc_qty = Float.parseFloat(ed_balance_qty.getText().toString().trim());
                        float suply_qty = booking_qty - blc_qty;
                        ed_suppply_qty.setText(String.valueOf(suply_qty));
                    }
                }
            }

            view_update_line.setOnClickListener(v -> {
                if (posted_radio_grp.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getActivity(), "Please select posted", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ac_marketing_indent_no.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please select marketing indent no.", Toast.LENGTH_SHORT).show();
                } else if (ac_crop_code.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please select crop code", Toast.LENGTH_SHORT).show();
                } else if (ed_indent_qty.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "please enter indent qty.", Toast.LENGTH_SHORT).show();
                } else if (order_booking_indent_no == null || order_booking_indent_no.equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Booking indent no is blank.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    ac_marketing_indent_no.setText(marketingIndentLineModel.booking_indent_no);
                    if (marketingIndentLineList.size() == 1) {
                        updateMarketingIndentLine(dialog, marketingIndentLineModel);
                    } else {
                        // if (!ed_varity_grp.getText().toString().trim().equalsIgnoreCase("")) {
                        // try {
                        String varity_grp = marketingIndentLineModel.variety_group;

                        for (int k = 0; k < marketingIndentLineList.size(); k++) {
                            if (marketingIndentLineList.get(k).variety_group != null) {
                                if (varity_grp.equalsIgnoreCase(marketingIndentLineModel.variety_group)) {
                                    flag_execute = "1";
                                    break;
                                }
                                if (marketingIndentLineList.get(k).variety_group.equalsIgnoreCase(ed_varity_grp.getText().toString().trim())) {
                                    flag_execute = "0";
                                    Toast.makeText(getActivity(), "You can't select same variety group twice", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            } else {
                                flag_execute = "1";
                                continue;
                            }
                        }
                    }
                        /*}catch (Exception e){
                            e.printStackTrace();
                        }*/
                }
                  /*  else {
                        updateMarketingIndentLine(dialog,marketingIndentLineModel, ac_marketing_indent_no, ed_class_of_seed,
                                ed_varity_product_group_code, ed_no_of_bags, ed_indent_qty, ed_booking_qty,
                                ed_varity_grp, ed_unit_price, ed_variety_name, ed_varity_pack_size, ed_line_discount_per,
                                ac_crop_code, ac_umo, ac_variety_code);
                    }*/
                //    }
                if (flag_execute.equalsIgnoreCase("1")) {
                    updateMarketingIndentLine(dialog, marketingIndentLineModel);
                }
                //}
            });
        }
    }


    private void insertMarketingIndentLine(Dialog dialog) {
        MarketingIndentModel.MarketingIndentLine marketingIndentLine = new MarketingIndentModel().new MarketingIndentLine();
        marketingIndentLine.marketing_indent_no = marketingIndentModel.marketing_indent_no;
        marketingIndentLine.crop_code = ac_crop_code.getText().toString().trim();
        marketingIndentLine.variety_group = ed_varity_grp.getText().toString().trim();
        marketingIndentLine.do_created = "0";
        marketingIndentLine.posted = postedString;
        marketingIndentLine.unit_of_measure_code = ac_umo.getText().toString().trim();
        marketingIndentLine.variety_code = ac_variety_code.getText().toString().trim();
        marketingIndentLine.variety_name = ed_variety_name.getText().toString().trim();
        marketingIndentLine.variety_package_size = !ed_varity_pack_size.getText().toString().trim().equalsIgnoreCase("") ? ed_varity_pack_size.getText().toString().trim() : "0";
        marketingIndentLine.variety_class_of_seeds = ed_class_of_seed.getText().toString().trim();
        marketingIndentLine.variety_product_group_code = ed_varity_product_group_code.getText().toString().trim();
        marketingIndentLine.no_of_bags = ed_no_of_bags.getText().toString().trim();
        marketingIndentLine.booking_indent_no = order_booking_indent_no;
        marketingIndentLine.indent_qty = ed_indent_qty.getText().toString().trim();
        marketingIndentLine.unit_price = ed_unit_price.getText().toString().trim().equals("") ? "0" : ed_unit_price.getText().toString().trim();
        marketingIndentLine.line_discount_percent = ed_line_discount_per.getText().toString().trim().equalsIgnoreCase("") ? "0" : ed_line_discount_per.getText().toString().trim();
        marketingIndentLine.created_by = sessionManagement.getUserEmail();
        marketingIndentLine.line_amount = "0";
        String jsonString = new Gson().toJson(marketingIndentLine);
        JsonObject asJsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            LoadingDialog progressDialog = new LoadingDialog();
            progressDialog.showLoadingDialog(getActivity());
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<MarketingIndentModel.MarketingIndentLine>> call = mAPIService.insertBookingLine(asJsonObject);
            call.enqueue(new Callback<List<MarketingIndentModel.MarketingIndentLine>>() {
                @Override
                public void onResponse(Call<List<MarketingIndentModel.MarketingIndentLine>> call, Response<List<MarketingIndentModel.MarketingIndentLine>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialog.hideDialog();
                            List<MarketingIndentModel.MarketingIndentLine> insertBookingResponseList = response.body();
                            if (insertBookingResponseList != null && insertBookingResponseList.size() > 0 && insertBookingResponseList.get(0).condition) {
                                complete_header.setVisibility(View.VISIBLE);
                                marketingIndentLineList = insertBookingResponseList;
                                bindDataWithAdapter("", marketingIndentLineList);
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getActivity(), insertBookingResponseList.size() > 0 ? insertBookingResponseList.get(0).message : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialog.hideDialog();
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        progressDialog.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "insert_booking_line", getActivity());
                    }
                }

                @Override
                public void onFailure(Call<List<MarketingIndentModel.MarketingIndentLine>> call, Throwable t) {
                    progressDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "insert_booking_line", getActivity());
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please wait for internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateMarketingIndentLine(Dialog dialog, MarketingIndentModel.MarketingIndentLine marketingIndentLineModel) {
        MarketingIndentModel.MarketingIndentLine marketingIndentLine = new MarketingIndentModel().new MarketingIndentLine();
        marketingIndentLine.marketing_indent_no = marketingIndentModel.marketing_indent_no;
        marketingIndentLine.line_no = marketingIndentLineModel.line_no;
        marketingIndentLine.crop_code = ac_crop_code.getText().toString().trim();
        marketingIndentLine.variety_group = ed_varity_grp.getText().toString().trim();
        marketingIndentLine.do_created = "0";
        marketingIndentLine.posted = postedString;
        marketingIndentLine.unit_of_measure_code = ac_umo.getText().toString().trim();
        marketingIndentLine.variety_code = ac_variety_code.getText().toString().trim();
        marketingIndentLine.variety_name = ed_variety_name.getText().toString().trim();
        marketingIndentLine.variety_package_size = ed_varity_pack_size.getText().toString().trim();
        marketingIndentLine.variety_class_of_seeds = ed_class_of_seed.getText().toString().trim();
        marketingIndentLine.variety_product_group_code = ed_varity_product_group_code.getText().toString().trim();
        marketingIndentLine.no_of_bags = ed_no_of_bags.getText().toString().trim();
        marketingIndentLine.booking_indent_no = order_booking_indent_no;
        marketingIndentLine.indent_qty = ed_indent_qty.getText().toString().trim();
        marketingIndentLine.unit_price = ed_unit_price.getText().toString().trim().equals("") ? "0" : ed_unit_price.getText().toString().trim();
        marketingIndentLine.line_discount_percent = ed_line_discount_per.getText().toString().trim();
        marketingIndentLine.created_by = sessionManagement.getUserEmail();
        marketingIndentLine.booking_qty = ed_booking_qty.getText().toString().trim();
        marketingIndentLine.line_amount = "0";
        String jsonString = new Gson().toJson(marketingIndentLine);
        JsonObject asJsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            LoadingDialog progressDialog = new LoadingDialog();
            progressDialog.showLoadingDialog(getActivity());
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<MarketingIndentModel.MarketingIndentLine>> call = mAPIService.updateBookingLine(asJsonObject);
            call.enqueue(new Callback<List<MarketingIndentModel.MarketingIndentLine>>() {
                @Override
                public void onResponse(Call<List<MarketingIndentModel.MarketingIndentLine>> call, Response<List<MarketingIndentModel.MarketingIndentLine>> response) {
                    if (response.isSuccessful()) {
                        progressDialog.hideDialog();
                        List<MarketingIndentModel.MarketingIndentLine> updateBookingResponseList = response.body();
                        if (updateBookingResponseList != null && updateBookingResponseList.size() > 0 && updateBookingResponseList.get(0).condition) {
                            marketingIndentModel.marketing_indent_no = updateBookingResponseList.get(0).marketing_indent_no;
                            marketingIndentLineList = updateBookingResponseList;
                            bindDataWithAdapter("", updateBookingResponseList);
                            setUpdateHeaderDetails(marketingIndentModel);
                            dialog.dismiss();
                            Toast.makeText(getActivity(), updateBookingResponseList.get(0).message, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), updateBookingResponseList.size() > 0 ? updateBookingResponseList.get(0).message : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        progressDialog.hideDialog();
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<MarketingIndentModel.MarketingIndentLine>> call, Throwable t) {
                    progressDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "update_booking_line", getActivity());
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please wait for internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void bindDataWithAdapter(String marketing_indent, List<MarketingIndentModel.MarketingIndentLine> marketingIndentLines) {
        marketingIndentLineAdapter = new MarketingIndentLineAdapter(getActivity(), marketingIndentLines, marketing_indent);
        line_listview.setAdapter(marketingIndentLineAdapter);
        marketingIndentLineAdapter.notifyDataSetChanged();
    }


    private void completeSendToAprovalBooking() {
        if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            LoadingDialog progressDialog = new LoadingDialog();
            progressDialog.showLoadingDialog(getActivity());
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<List<BookingResponseModel>> call = mAPIService.sendToApprvalBooking(marketingIndentModel.marketing_indent_no);
            call.enqueue(new Callback<List<BookingResponseModel>>() {
                @Override
                public void onResponse(Call<List<BookingResponseModel>> call, Response<List<BookingResponseModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialog.hideDialog();
                            List<BookingResponseModel> booking_cmplete_response = response.body();
                            if (booking_cmplete_response != null && booking_cmplete_response.size() > 0 && booking_cmplete_response.get(0).condition) {
                                marketingIndentModel.status = "Sent For Approval";
                                delete_header.setVisibility(View.GONE);
                                complete_header.setVisibility(View.GONE);
                                chip_add_line_booking.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(getActivity(), booking_cmplete_response.size() > 0 ? booking_cmplete_response.get(0).message : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialog.hideDialog();
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        progressDialog.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "send_to_server_booking", getActivity());
                    }
                }

                @Override
                public void onFailure(Call<List<BookingResponseModel>> call, Throwable t) {
                    progressDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "send_to_server_booking", getActivity());
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please wait for internet connection", Toast.LENGTH_SHORT).show();
        }

    }


    private void deleteBookingLine(String marketing_indent_no, String line_no, int position) {
        if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            LoadingDialog progressDialog = new LoadingDialog();
            progressDialog.showLoadingDialog(getActivity());
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("marketing_indent_no", marketing_indent_no);
            jsonObject.addProperty("line_no", line_no);
            Call<List<BookingResponseModel>> call = mAPIService.deleteBookingLine(jsonObject);
            call.enqueue(new Callback<List<BookingResponseModel>>() {
                @Override
                public void onResponse(Call<List<BookingResponseModel>> call, Response<List<BookingResponseModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            progressDialog.hideDialog();
                            List<BookingResponseModel> booking_delete_response = response.body();
                            if (booking_delete_response != null && booking_delete_response.size() > 0 && booking_delete_response.get(0).condition) {
                                marketingIndentLineList.remove(position);
                                marketingIndentLineAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getActivity(), booking_delete_response.size() > 0 ? booking_delete_response.get(0).message : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialog.hideDialog();
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        progressDialog.hideDialog();
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "delete_booking_line", getActivity());
                    }
                }

                @Override
                public void onFailure(Call<List<BookingResponseModel>> call, Throwable t) {
                    progressDialog.hideDialog();
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "delete_booking_line", getActivity());
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please wait for internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void getBookingIndentNo(ProgressBar search_loading_, RecyclerView bookingnolistview, String Distributor_Code, TextInputEditText app_booking_no ) {
        if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {
            search_loading_.setVisibility(View.VISIBLE);
            String no = app_booking_no.getText().toString();
            NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
            Call<BookingMasterModel> call = mAPIService.getBookingMassterNo(Distributor_Code, no);
            call.enqueue(new Callback<BookingMasterModel>() {
                @Override
                public void onResponse(Call<BookingMasterModel> call, Response<BookingMasterModel> response) {
                    try {
                        if (response.isSuccessful()) {
                            search_loading_.setVisibility(View.GONE);
                            BookingMasterModel bookingMasterModel = response.body();
                            if (bookingMasterModel != null && bookingMasterModel.condition) {
                                List<BookingMasterModel.Data> booking_master_list = bookingMasterModel.data;
                                if (booking_master_list != null && booking_master_list.size() > 0) {
                                    booking_no_list = booking_master_list;
                                    bindIndentLineFields();
//                                    setBookingAdapter(bookingnolistview);
                                    //todo testing..
                                } else {
                                    card_booking_search_layout.setVisibility(View.GONE);
                                    bookingnolistview.setAdapter(null);
                                }
                            } else {
                                search_loading_.setVisibility(View.GONE);
                                card_booking_search_layout.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), !bookingMasterModel.condition ? "No data found" : ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            card_booking_search_layout.setVisibility(View.GONE);
                            search_loading_.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        card_booking_search_layout.setVisibility(View.GONE);
                        search_loading_.setVisibility(View.GONE);
                        ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "get_booking_master", getActivity());
                    }
                }

                @Override
                public void onFailure(Call<BookingMasterModel> call, Throwable t) {
                    search_loading_.setVisibility(View.GONE);
                    card_booking_search_layout.setVisibility(View.GONE);
                    ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_booking_master", getActivity());
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please wait for internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    //todo bind lines fields...
    public void bindIndentLineFields(){
        try {
            card_booking_search_layout.setVisibility(View.GONE);
            BookingMasterModel.Data booking_data = new BookingMasterModel().new Data();
            booking_data = booking_no_list.get(0);
            if (booking_data != null) {
                order_booking_indent_no = booking_data.No;

                if (booking_data.Variety_Class_of_Seeds != null && !booking_data.Variety_Class_of_Seeds.equalsIgnoreCase("")) {
                    ed_class_of_seed.setText(booking_data.Variety_Class_of_Seeds);
                    card_booking_search_layout.setVisibility(View.GONE);
                    search_booking_input_layout.setStartIconDrawable(null);
                }
                if (booking_data.Variety_Product_Group_Code == null ||
                        booking_data.Variety_Product_Group_Code.equalsIgnoreCase("")) {
                    ed_varity_product_group_code.setText(booking_data.Variety_Product_Group_Code == null ? "0" : "0");
                } else {
                    ed_varity_product_group_code.setText(booking_data.Variety_Product_Group_Code.equals("NULL") ? "0" : booking_data.Variety_Product_Group_Code);
                }
                if (booking_data.Variety_Name != null && !booking_data.Variety_Name.equalsIgnoreCase("")) {
                    ed_variety_name.setText(booking_data.Variety_Name);
                }
                if (booking_data.Variety_Package_Size != null && !booking_data.Variety_Package_Size.equalsIgnoreCase("")) {
                    ed_varity_pack_size.setText(booking_data.Variety_Package_Size);
                } else {
                    ed_varity_pack_size.setText("");
                }
                if (booking_data.Crop_Code != null && !booking_data.Crop_Code.equalsIgnoreCase("")) {
                    ac_crop_code.setText(booking_data.Crop_Code);
                }
                if (booking_data.Variety_Code != null && !booking_data.Variety_Code.equalsIgnoreCase("")) {
                    ac_variety_code.setText(booking_data.Variety_Code);
                    // getVarityGroupName(booking_data.Variety_Code, ed_varity_grp);
                    getMarketingIndentSupplyQty(marketingIndentModel.customer_no, booking_data.Variety_Code);
                } else {
                    ac_variety_code.setText("");
                }
                if (booking_data.VarietyGroup == null && booking_data.VarietyGroup.equals("")) {
                    ed_varity_grp.setText("");
                } else {
                    ed_varity_grp.setText(booking_data.VarietyGroup);
                }
                if (booking_data.Alloted_Percent != null && !booking_data.Alloted_Percent.equalsIgnoreCase("")) {
                    ed_alloted_per.setText(booking_data.Alloted_Percent);
                }
                if (booking_data.Booking_Qty != null && !booking_data.Booking_Qty.equalsIgnoreCase("")) {
                    ed_booking_qty.setText(booking_data.Booking_Qty);
                }
                if (booking_data.Alloted_Qty != null && !booking_data.Alloted_Qty.equalsIgnoreCase("")) {
                    ed_alloted_qty.setText(booking_data.Alloted_Qty);
                } else {
                    ed_alloted_qty.setText("0");
                } if (!booking_data.Posted.equalsIgnoreCase("")) {
                    if (booking_data.Posted.equalsIgnoreCase("Yes")) {
                        line_posted_yes.setChecked(true);
                    } else if (booking_data.Posted.equalsIgnoreCase("NO")) {
                        line_posted_yes.setChecked(true);
                    }
                }
            } else {
                card_booking_search_layout.setVisibility(View.GONE);
                ed_class_of_seed.setText("");
                ac_variety_code.setText("");
                ac_umo.setText("");
                ed_no_of_bags.setText("");
                ac_crop_code.setText("");
                ed_line_discount_per.setText("");
                ed_varity_pack_size.setText("");
                ed_varity_grp.setText("");
                ed_variety_name.setText("");
                ed_varity_product_group_code.setText("0");
                ed_class_of_seed.setText("");
                ed_alloted_per.setText("");
                ed_booking_qty.setText("");
                ed_alloted_qty.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
            card_booking_search_layout.setVisibility(View.GONE);
        }
    }

    private void setBookingAdapter(RecyclerView bookingnolistview) {
        BookingMasterAdapter itemAdapter = new BookingMasterAdapter(getActivity(), booking_no_list);
        bookingnolistview.setAdapter(itemAdapter);
        itemAdapter.setOnClick(this);
    }

    private void getVarityGroupName(String item_code, TextInputEditText ed_item_group) {
        Hybrid_Item_Table hybrid_item_table = null;
        PristineDatabase pristineDatabase = PristineDatabase.getAppDatabase(getActivity());
        try {
            HybridItemMasterDao hybridItemMasterDao = pristineDatabase.hybridItemMasterDao();
            hybrid_item_table = hybridItemMasterDao.getVarityGroup(item_code);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
            if (hybrid_item_table != null) {
                ed_item_group.setText(hybrid_item_table.getItem_Group());
            } else {
                ed_item_group.setText("");
            }
        }
    }

    private void getUnitOfMeasureData(ProgressBar loading_content, AutoCompleteTextView ac_unit_of_measure) {
        loading_content.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<UnitOfMeasureModel> call = mAPIService.getUnitsOfMeasureList();
        call.enqueue(new Callback<UnitOfMeasureModel>() {
            @Override
            public void onResponse(Call<UnitOfMeasureModel> call, Response<UnitOfMeasureModel> response) {
                try {
                    if (response.isSuccessful()) {
                        UnitOfMeasureModel unitOfMeasureModelList = response.body();
                        loading_content.setVisibility(View.GONE);
                        if (unitOfMeasureModelList != null) {
                            List<UnitOfMeasureModel.UnitOfMeasureModelList> uomList = unitOfMeasureModelList.data;
                            if (uomList != null && uomList.size() > 0) {
                                unitPriceTableList = uomList;
                                UnitOfMeasureAdapter unitOfMeasureAdapter = new UnitOfMeasureAdapter(getActivity(), R.layout.android_item_view, unitPriceTableList);
                                ac_unit_of_measure.setAdapter(unitOfMeasureAdapter);

                            } else {
                                loading_content.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "Unit of Measure Record not found !", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            loading_content.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<UnitOfMeasureModel> call, Throwable t) {
                loading_content.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "get_uom_master", getActivity());
            }
        });
    }

    @Override
    public void onItemClick(int pos) {
        try {
            if (role_no_list != null && role_no_list.size() > 0) {
                roleMasterTable = role_no_list.get(pos);
                if (roleMasterTable != null) {
                    ac_cust_no.setText(roleMasterTable.name);
                    CommonData.hideSoftKeyBoard(getActivity());
                    ac_cust_no.clearFocus();
                    frame_layout_org_list.setVisibility(View.GONE);
                    name_detail_layout.setVisibility(View.VISIBLE);
                    ed_date.setText(DateTimeUtilsCustome.getCurrentDateBY_());
                    cust_name = roleMasterTable.name;
                    state_code = roleMasterTable.state_Code;
                    zone_code = roleMasterTable.zone;
                    taluka_code = roleMasterTable.territory;
                    region_code = roleMasterTable.region;
                    area_code = roleMasterTable.area;

                    ship_contact = roleMasterTable.contact != null ? roleMasterTable.contact : "";
                    ship_state_code = roleMasterTable.state_Code != null ? roleMasterTable.state_Code : "";
                    ship_agent_code = "null";
                    ship_agent_service_code = "null";
                    ship_address2 = roleMasterTable.address_2 != null ? roleMasterTable.address_2 : "";
                    ship_contact = roleMasterTable.contact != null ? roleMasterTable.contact : "";

                    if (roleMasterTable.name != null && !roleMasterTable.name.equalsIgnoreCase("")) {
                        name.setText(roleMasterTable.name);
                        ed_ship_toname.setText(roleMasterTable.name);
                    }

                    if (roleMasterTable.name_2 != null && !roleMasterTable.name_2.equalsIgnoreCase("")) {
                        name_2.setText(roleMasterTable.name_2);
                        ed_ship_toname2.setText(roleMasterTable.name_2);
                    }

                    if (roleMasterTable.address != null && !roleMasterTable.address.equalsIgnoreCase("")) {
                        address.setText(roleMasterTable.address);
                        ed_ship_to_address.setText(roleMasterTable.address);

                    }

                    if (roleMasterTable.address_2 != null && !roleMasterTable.address_2.equalsIgnoreCase("")) {
                        address_2.setText(roleMasterTable.address_2);
                        ed_ship_to_address2.setText(roleMasterTable.address_2);
                    }

                    if (roleMasterTable.city != null && !roleMasterTable.city.equalsIgnoreCase("")) {
                        ed_ship_to_city.setText(roleMasterTable.city);
                    }

                    if (roleMasterTable.contact != null && !roleMasterTable.contact.equalsIgnoreCase("")) {
                        ed_ship_to_contact.setText(roleMasterTable.contact);
                    }
                    if (roleMasterTable.post_Code != null && roleMasterTable.post_Code.equalsIgnoreCase("")) {
                        ed_ship_post_code.setText(roleMasterTable.post_Code);
                    }
                    if (roleMasterTable.gsT_Registration_No != null && !roleMasterTable.gsT_Registration_No.equalsIgnoreCase("")) {
                        ed_ship_gst_reg.setText(roleMasterTable.gsT_Registration_No);
                    }

                    if (roleMasterTable.zone_Name != null && !roleMasterTable.zone_Name.equalsIgnoreCase("")) {
                        ed_zone.setText(roleMasterTable.zone_Name);
                        ed_zone.setFocusable(false);
                        ed_zone.setFocusableInTouchMode(false);
                        ed_ship_post_code.setText(roleMasterTable.post_Code);
                    }

                    if (roleMasterTable.state_Name != null && !roleMasterTable.state_Name.equalsIgnoreCase("")) {
                        ed_state.setText(roleMasterTable.state_Name);
                        ed_state.setFocusable(false);
                        ed_state.setFocusableInTouchMode(false);
                    }

                    if (roleMasterTable.territory != null && !roleMasterTable.territory.equalsIgnoreCase("")) {
                        ed_tluka.setText(roleMasterTable.territory_Name);
                        ed_tluka.setFocusable(false);
                        ed_tluka.setFocusableInTouchMode(false);
                    }

                    if (roleMasterTable.region_Name != null && !roleMasterTable.region_Name.equalsIgnoreCase("")) {
                        ed_resgion.setText(roleMasterTable.territory_Name);
                        ed_resgion.setFocusable(false);
                        ed_resgion.setFocusableInTouchMode(false);
                    }
                    if (roleMasterTable.area_Name != null && !roleMasterTable.area_Name.equalsIgnoreCase("")) {
                        ac_area.setText(roleMasterTable.area_Name);
                        ac_area.setFocusable(false);
                        ac_area.setFocusableInTouchMode(false);
                    }
                } else {
                    frame_layout_org_list.setVisibility(View.GONE);
                    ed_state.setText("");
                    ed_zone.setText("");
                    ed_resgion.setText("");
                    ac_area.setText("");
                    ed_tluka.setText("");
                    tv_name.setText("");
                    tv_name2.setText("");
                    tv_address.setText("");
                    tv_addres_2.setText("");
                    ac_ship_to.setText("");
                    ed_ship_toname.setText("");
                    ed_ship_to_address.setText("");
                    ed_ship_to_address2.setText("");
                    ed_ship_post_code.setText("");
                    ed_ship_to_city.setText("");
                    ed_ship_gst_reg.setText("");

                }
            } else {
                frame_layout_org_list.setVisibility(View.GONE);
                ed_state.setText("");
                ed_zone.setText("");
                ed_resgion.setText("");
                ac_area.setText("");
                ed_tluka.setText("");
                tv_name.setText("");
                tv_name2.setText("");
                tv_address.setText("");
                tv_addres_2.setText("");
                ac_ship_to.setText("");
                ed_ship_toname.setText("");
                ed_ship_to_address.setText("");
                ed_ship_to_address2.setText("");
                ed_ship_post_code.setText("");
                ed_ship_to_city.setText("");
                ed_ship_gst_reg.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void shipOnItemClick(int pos) {
        shipToAddressTable = shipToAddressDataTables_list.get(pos);
        if (shipToAddressTable != null) {
            ed_ship_toname.setText(shipToAddressTable.Name != null ? shipToAddressTable.Name : "");
            ed_ship_to_address.setText(shipToAddressTable.Address != null ? shipToAddressTable.Address : "");
            ed_ship_to_address2.setText(shipToAddressTable.Address_2 != null ? shipToAddressTable.Address_2 : "");
            ed_ship_post_code.setText(shipToAddressTable.Post_Code != null ? shipToAddressTable.Post_Code : "");
            ed_ship_to_city.setText(shipToAddressTable.City != null ? shipToAddressTable.City : "");
            ed_ship_to_method.setText(shipToAddressTable.Shipment_Method_Code != null ? shipToAddressTable.Shipment_Method_Code : "");
            ed_ship_gst_reg.setText(shipToAddressTable.GST_Registration_No != null ? shipToAddressTable.GST_Registration_No : "");
            ship_address2 = shipToAddressTable.Address_2 != null ? shipToAddressTable.Address_2 : "";
            ship_contact = shipToAddressTable.Contact != null ? shipToAddressTable.Contact : "";
            ship_state_code = shipToAddressTable.State != null ? shipToAddressTable.State : "";
            ship_agent_code = shipToAddressTable.Shipping_Agent_Code != null ? shipToAddressTable.Shipping_Agent_Code : "";
            ship_agent_service_code = shipToAddressTable.Shipping_Agent_Service_Code != null ? shipToAddressTable.Shipping_Agent_Service_Code : "";
        }
    }

    private String order_booking_indent_no = "";

    @Override
    public void bookingonItemClick(int pos) {
        try {
            card_booking_search_layout.setVisibility(View.GONE);
            BookingMasterModel.Data booking_data = new BookingMasterModel().new Data();
            for (int i = 0; i < booking_no_list.size(); i++) {
                if (booking_no_list.get(pos).App_Booking_No.equalsIgnoreCase(booking_no_list.get(i).App_Booking_No)) {
                    booking_data = booking_no_list.get(i);
                }
            }
            if (booking_data != null) {
                ac_marketing_indent_no.setText(booking_data.App_Booking_No);
                order_booking_indent_no = booking_data.No;

                if (booking_data.Variety_Class_of_Seeds != null && !booking_data.Variety_Class_of_Seeds.equalsIgnoreCase("")) {
                    ed_class_of_seed.setText(booking_data.Variety_Class_of_Seeds);
                    card_booking_search_layout.setVisibility(View.GONE);
                    search_booking_input_layout.setStartIconDrawable(null);
                }
                if (booking_data.Variety_Product_Group_Code == null ||
                        booking_data.Variety_Product_Group_Code.equalsIgnoreCase("")) {
                    ed_varity_product_group_code.setText(booking_data.Variety_Product_Group_Code == null ? "0" : "0");
                } else {
                    ed_varity_product_group_code.setText(booking_data.Variety_Product_Group_Code.equals("NULL") ? "0" : booking_data.Variety_Product_Group_Code);
                }
                if (booking_data.Variety_Name != null && !booking_data.Variety_Name.equalsIgnoreCase("")) {
                    ed_variety_name.setText(booking_data.Variety_Name);
                }
                if (booking_data.Variety_Package_Size != null && !booking_data.Variety_Package_Size.equalsIgnoreCase("")) {
                    ed_varity_pack_size.setText(booking_data.Variety_Package_Size);
                } else {
                    ed_varity_pack_size.setText("");
                }
                if (booking_data.Crop_Code != null && !booking_data.Crop_Code.equalsIgnoreCase("")) {
                    ac_crop_code.setText(booking_data.Crop_Code);
                }
                if (booking_data.Variety_Code != null && !booking_data.Variety_Code.equalsIgnoreCase("")) {
                    ac_variety_code.setText(booking_data.Variety_Code);
                    // getVarityGroupName(booking_data.Variety_Code, ed_varity_grp);
                    getMarketingIndentSupplyQty(marketingIndentModel.customer_no, booking_data.Variety_Code);
                } else {
                    ac_variety_code.setText("");
                }
                if (booking_data.VarietyGroup == null && booking_data.VarietyGroup.equals("")) {
                    ed_varity_grp.setText("");
                } else {
                    ed_varity_grp.setText(booking_data.VarietyGroup);
                }
                if (booking_data.Alloted_Percent != null && !booking_data.Alloted_Percent.equalsIgnoreCase("")) {
                    ed_alloted_per.setText(booking_data.Alloted_Percent);
                }
                if (booking_data.Booking_Qty != null && !booking_data.Booking_Qty.equalsIgnoreCase("")) {
                    ed_booking_qty.setText(booking_data.Booking_Qty);
                }
                if (booking_data.Alloted_Qty != null && !booking_data.Alloted_Qty.equalsIgnoreCase("")) {
                    ed_alloted_qty.setText(booking_data.Alloted_Qty);
                } else {
                    ed_alloted_qty.setText("0");
                }
            } else {
                card_booking_search_layout.setVisibility(View.GONE);
                ed_class_of_seed.setText("");
                ac_variety_code.setText("");
                ac_umo.setText("");
                ed_no_of_bags.setText("");
                ac_crop_code.setText("");
                ed_line_discount_per.setText("");
                ed_varity_pack_size.setText("");
                ed_varity_grp.setText("");
                ed_variety_name.setText("");
                ed_varity_product_group_code.setText("0");
                ed_class_of_seed.setText("");
                ed_alloted_per.setText("");
                ed_booking_qty.setText("");
                ed_alloted_qty.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
            card_booking_search_layout.setVisibility(View.GONE);
        }
    }

    private void getMarketingIndentSupplyQty(String customer_no, String varity_code) {
        loading_content.setVisibility(View.VISIBLE);
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("customer_no", customer_no);
        jsonObject.addProperty("varietyCode", varity_code);
        Call<List<MarketingIndentModelSupplyQtyModel>> call = mAPIService.getMarketingIndentSupplyQty(jsonObject);
        call.enqueue(new Callback<List<MarketingIndentModelSupplyQtyModel>>() {
            @Override
            public void onResponse(Call<List<MarketingIndentModelSupplyQtyModel>> call, Response<List<MarketingIndentModelSupplyQtyModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        List<MarketingIndentModelSupplyQtyModel> marketingIndentModelSupplyQtyModelList = response.body();
                        loading_content.setVisibility(View.GONE);
                        if (marketingIndentModelSupplyQtyModelList != null && marketingIndentModelSupplyQtyModelList.size() > 0
                                && marketingIndentModelSupplyQtyModelList.get(0).condition) {
                            ed_suppply_qty.setText(marketingIndentModelSupplyQtyModelList.get(0).nav_response);
                        } else {
                            ed_suppply_qty.setText("");
                        }
                    } else {
                        ed_suppply_qty.setText("");
                        loading_content.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Supply qty not found !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<MarketingIndentModelSupplyQtyModel>> call, Throwable t) {
                loading_content.setVisibility(View.GONE);
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "getSupply_qty", getActivity());
            }
        });

    }

   /* private void getUnitOfMeasureCode(AutoCompleteTextView ac_umo){
        PristineDatabase pristineDatabase=PristineDatabase.getAppDatabase(getActivity());
        try{
            UOMDao unitPriceDao=pristineDatabase.uomDao();
         unitPriceTableList=unitPriceDao.fetchAllData();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
            if(unitPriceTableList!=null && unitPriceTableList.size()>0){
                UnitOfMeasureAdapter unitOfMeasureAdapter=new UnitOfMeasureAdapter(getActivity(),R.layout.android_item_view,unitPriceTableList);
                ac_umo.setAdapter(unitOfMeasureAdapter);
            }
        }
    }*/
}
