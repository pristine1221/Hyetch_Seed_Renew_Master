package com.example.pristineseed.ui.bootmMainScreen.ui.DailyActivtiy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.res.ResourcesCompat;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.global.ApiRequestFailure;

import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.DailyActivity_Model.DailyActivityModel;
import com.example.pristineseed.model.DailyActivity_Model.DailyActivityResponse;

import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.adapter.activity_adapter.DailyActivityAdapter;
import com.example.pristineseed.ui.bootmMainScreen.BottomMainActivity;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DailyActivityFragment  extends Fragment{
        private   AppCompatEditText contact_no, order_collected, payment_collected;
     private   ImageView add_Contact_bt;
     private   ChipGroup selected_contact_chipgroup;
      private Button next_buttonForAddLines, clear_Design, submitPage;

    private RecyclerView listview_add_line;
    private SessionManagement sessionManagement;
    private DailyActivityAdapter adapter;
    boolean isdataPass = false;
    boolean hideAllActions = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_daily_activity, container, false);

        return root;
    }

    public  DailyActivityModel dailyActivityModel = new DailyActivityModel();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement = new SessionManagement(getContext());
        bindUi_InIt(view);
        bindUi_InIt();
        /*  set swipe touch listener */
        SwipeDismissListViewTouchListener swipeTouchListener = new SwipeDismissListViewTouchListener(listview_add_line, new SwipeDismissListViewTouchListener.SwipeListener() {
            @Override
            public boolean canSwipeLeft(int position){
                //enable/disable left swipe on checkbox base else use true/false
                return true;
            }

            @Override
            public boolean canSwipeRight(int position) {
                //enable/disable right swipe on checkbox base else use true/false
                return true;
            }

            @Override
            public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                if (!hideAllActions){
                    for (int position : reverseSortedPositions) {
                        dailyActivityModel.addlines.remove(position);
                        adapter.notifyDataSetChanged();
                        Bundle bundle = new Bundle();
                        bundle.putString("dataPass", new Gson().toJson(dailyActivityModel));
                        setArguments(bundle);
                    }
                }
            }

            @Override
            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                //on cardview swipe right dismiss update adapter
                if (!hideAllActions){
                    for (int position : reverseSortedPositions) {
                        dailyActivityModel.addlines.remove(position);
                        adapter.notifyDataSetChanged();
                        Bundle bundle = new Bundle();
                        bundle.putString("dataPass", new Gson().toJson(dailyActivityModel));
                        setArguments(bundle);
                    }
                }
            }
        });

        //add item touch listener to recycler view
        listview_add_line.addOnItemTouchListener(swipeTouchListener);

        add_Contact_bt.setOnClickListener(view1 -> {
            if (contact_no.length() == 10) {
                contactList.add(contact_no.getText().toString());
                bindContactListNo();
                contact_no.setText("");
            } else {
                contact_no.setError("Contact Lenght must be 10 degit.");
            }
        });

        next_buttonForAddLines.setOnClickListener(view1 -> {
            if (contactList.size() <= 0) {
                contact_no.setError("Please Add minimum one contact.");
                return;
            } else if (order_collected.getText().toString().contentEquals("")) {
                order_collected.setError("Please Enter Order Collected.");
                return;
            } else if (payment_collected.getText().toString().contentEquals("")) {
                payment_collected.setError("Please Enter Payment Collected");
                return;
            }
            dailyActivityModel.contact = contactList.get(0);
            dailyActivityModel.contact1 = contactList.size() == 2 ? contactList.get(1) : "0";
            dailyActivityModel.order_collected = order_collected.getText().toString();
            dailyActivityModel.payment_collected = payment_collected.getText().toString();
            Bundle bundle=new Bundle();
            bundle.putString("dataPass", new Gson().toJson(dailyActivityModel));
            DailyActivty_AddLine_Fragment dailyActivty_addLine_fragment=new DailyActivty_AddLine_Fragment();
            dailyActivty_addLine_fragment.setArguments(bundle);
            StaticMethods.loadFragmentsWithBackStack(getActivity(),dailyActivty_addLine_fragment,"Daily_Activity_AddLine_Fragment");
        });
        clear_Design.setOnClickListener(view1 -> {
            clear_Screen();
        });
        submitPage.setOnClickListener(view1 -> {
            try {
                if (dailyActivityModel.addlines == null || dailyActivityModel.addlines.size() <= 0) {
                    Snackbar.make(submitPage, "Please Insert Minimun Single Line", Snackbar.LENGTH_INDEFINITE).setAction("Cancel", view2 -> {
                    }).show();
                    return;
                }
                    boolean networkUtil = NetworkUtil.getConnectivityStatusBoolean(getContext());
                    if (networkUtil) {
                        JsonObject postedJson = new JsonObject();
                        postedJson.addProperty("contact", dailyActivityModel.contact);
                        postedJson.addProperty("contact1", dailyActivityModel.contact1);
                        postedJson.addProperty("order_collected", dailyActivityModel.order_collected);
                        postedJson.addProperty("payment_collected", dailyActivityModel.payment_collected);
                        postedJson.addProperty("email_id", sessionManagement.getUserEmail());
                        JsonArray jsonArray = new JsonArray();
                        for (int i = 0; i < dailyActivityModel.addlines.size(); i++) {
                            JsonObject temp = new JsonObject();
                            temp.addProperty("farmer_name", dailyActivityModel.addlines.get(i).farmer_name);
                            temp.addProperty("district", dailyActivityModel.addlines.get(i).district);
                            temp.addProperty("village", dailyActivityModel.addlines.get(i).village);

                            temp.addProperty("ajeet_crop_and_verity", dailyActivityModel.addlines.get(i).ajeet_crop_and_verity);
                            temp.addProperty("ajeet_crop_age", dailyActivityModel.addlines.get(i).ajeet_crop_age);
                            temp.addProperty("ajeet_fruits_per", dailyActivityModel.addlines.get(i).ajeet_fruits_per);
                            temp.addProperty("ajeet_pest", dailyActivityModel.addlines.get(i).ajeet_pest);
                            temp.addProperty("ajeet_disease", dailyActivityModel.addlines.get(i).ajeet_disease);

                            temp.addProperty("check_crop_and_verity", dailyActivityModel.addlines.get(i).check_crop_and_variety);
                            temp.addProperty("check_crop_age", dailyActivityModel.addlines.get(i).check_crop_age);
                            temp.addProperty("check_fruits_per", dailyActivityModel.addlines.get(i).check_fruits_per);
                            temp.addProperty("check_pest", dailyActivityModel.addlines.get(i).check_pest);
                            temp.addProperty("check_disease", dailyActivityModel.addlines.get(i).check_disease);
                            jsonArray.add(temp);
                        }
                        postedJson.add("addlines", jsonArray);
                        callDailyActiviy(postedJson);

                    } else {
                        Toast.makeText(getActivity(),"Please wait for internet connection",Toast.LENGTH_SHORT).show();
                        clear_Screen();
                    }

            } catch (Exception e) {
            }
        });
    }


    private void callDailyActiviy(JsonObject jsonObject){
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        LoadingDialog progressDialogLoading = new LoadingDialog();
        progressDialogLoading.showLoadingDialog(getActivity());
        Call<List<DailyActivityResponse>> call = mAPIService.insertLineDailyActivity(jsonObject);
        call.enqueue(new Callback<List<DailyActivityResponse>>(){
            @Override
            public void onResponse(Call<List<DailyActivityResponse>> call, Response<List<DailyActivityResponse>> response) {
                try {
                    List<DailyActivityResponse> serverResponseList = response.body();
                    progressDialogLoading.hideDialog();
                    if (serverResponseList!=null && serverResponseList.size() > 0 && serverResponseList.get(0).condition) {
                        Toast.makeText(getActivity(),serverResponseList.get(0).message,Toast.LENGTH_SHORT).show();
                        clear_Screen();
                    }
                } catch (Exception e) {
                    progressDialogLoading.hideDialog();
                    e.printStackTrace();
                    Log.e("daily_activity Error", e.getMessage());
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "insert_daily_activty_line", getActivity());
                }
            }

            @Override
            public void onFailure(Call<List<DailyActivityResponse>> call, Throwable t){
                progressDialogLoading.hideDialog();
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "insert_daily_activty_line", getActivity());
            }
        });
    }

    private void bindUi_InIt(View view) {
        contact_no = view.findViewById(R.id.contact_no);
        order_collected = view.findViewById(R.id.order_collected);
        payment_collected = view.findViewById(R.id.payment_collected);
        selected_contact_chipgroup = view.findViewById(R.id.selected_contact_chipgroup);
        next_buttonForAddLines = view.findViewById(R.id.next_buttonForAddLines);
        clear_Design = view.findViewById(R.id.clear_Design);
        submitPage = view.findViewById(R.id.submitPage);
        add_Contact_bt = view.findViewById(R.id.add_Contact_bt);
        listview_add_line = view.findViewById(R.id.listview_add_line);
        listview_add_line.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        listview_add_line.setItemAnimator(new DefaultItemAnimator());
        listview_add_line.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    private void bindContactListNo() {
        selected_contact_chipgroup.removeAllViews();
        for (int index = 0; index < contactList.size(); index++) {
            final String tagName = contactList.get(index);
            final Chip chip = new Chip(getActivity());
            int paddingDp = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 10,
                    getResources().getDisplayMetrics()
            );
            chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
            chip.setText(tagName);
            Typeface typeface = ResourcesCompat.getFont(getActivity(), R.font.product_sans_regular);
            chip.setTypeface(typeface);
            chip.setChipIconResource(R.drawable.ic_baseline_call_24);
            chip.setCloseIconResource(R.drawable.circle_close);
            if (isdataPass) {
                chip.setCloseIconEnabled(false);
            } else {
                chip.setCloseIconEnabled(true);
            }
            //Added click listener on close icon to remove tag from ChipGroup
            chip.setOnCloseIconClickListener(view -> {
                contactList.remove(tagName);
                selected_contact_chipgroup.removeView(chip);
            });
            selected_contact_chipgroup.addView(chip);
        }
    }


    void bindUi_InIt() {
        try {
            dailyActivityModel = new Gson().fromJson(getArguments().getString("dataPass", ""), DailyActivityModel.class);
            if (!contactList.isEmpty())
                contactList.clear();
            contactList.add(dailyActivityModel.contact);
            if (!dailyActivityModel.contact1.contentEquals("0") && !dailyActivityModel.contact1.contentEquals("")) {
                contactList.add(dailyActivityModel.contact1);
            }
            order_collected.setText(dailyActivityModel.order_collected);
            payment_collected.setText(String.valueOf(dailyActivityModel.payment_collected));

            contact_no.setEnabled(false);
            order_collected.setEnabled(false);
            payment_collected.setEnabled(false);
            isdataPass = true;
            //bind add list Data...
            Log.e("list_size", String.valueOf(dailyActivityModel.addlines.size()));
            adapter = new DailyActivityAdapter(getActivity(), dailyActivityModel.addlines);
            listview_add_line.setAdapter(adapter);
            clear_Design.setVisibility(View.VISIBLE);
            if (getArguments().getBoolean("hideAllActions", false)) {
                hideAllActions = true;
                add_Contact_bt.setVisibility(View.GONE);
                next_buttonForAddLines.setVisibility(View.GONE);
                clear_Design.setVisibility(View.GONE);
                submitPage.setVisibility(View.GONE);
            }
        } catch (Exception e) {
        }
    }
    public List<String> contactList = new ArrayList<>();
    void clear_Screen() {
        try {
            setArguments(null);
            if (!contactList.isEmpty())
                contactList.clear();
            contact_no.setEnabled(true);
            order_collected.setEnabled(true);
            payment_collected.setEnabled(true);
            dailyActivityModel.contact = "";
            dailyActivityModel.contact1 = "";
            dailyActivityModel.payment_collected = "";
            dailyActivityModel.order_collected = "";
            dailyActivityModel.addlines.clear();
            bindUi_InIt();
            bindContactListNo();
            DailyActivityAdapter adapter = new DailyActivityAdapter(getActivity(), dailyActivityModel.addlines);
            listview_add_line.setAdapter(adapter);
            contact_no.setText("");
            order_collected.setText("");
            payment_collected.setText("");
            clear_Design.setVisibility(View.GONE);
        } catch (Exception e) {}
    }
    @Override
    public void onDestroy() {
        super.onDestroy();}
    @Override
    public void onPause() {
        super.onPause();}
    @Override
    public void onResume() {
        super.onResume();
        bindUi_InIt();
        bindContactListNo();}

}
