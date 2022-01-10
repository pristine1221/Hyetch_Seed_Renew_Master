package com.example.pristineseed.ui.dashboard.newTheam;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pristineseed.R;

public class DashBoard3Fragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dash_board3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView seeMore_category = view.findViewById(R.id.seeMore_category);
        seeMore_category.setOnClickListener(v -> {
        });
    }

    public void AddNewItemPopup() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View popupView = inflater.inflate(R.layout.all_category_display_popup, null);
        Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar);
        dialog.setContentView(popupView);
        dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_FullScreenDialog;
        dialog.show();

        ImageView close_dilog_bt = popupView.findViewById(R.id.close_dilog_bt);
        Button filter_apply_bt = popupView.findViewById(R.id.filter_apply_bt);
        LinearLayout general_id=popupView.findViewById(R.id.general_id);
       // LinearLayout ms_item=popupView.findViewById(R.id.ms_item);
        filter_apply_bt.setOnClickListener(view -> {
            dialog.dismiss();
        });
        close_dilog_bt.setOnClickListener(view -> {
            dialog.dismiss();
        });

        general_id.setOnClickListener(v -> {
            generalSettingPopUp();
        });
    }


    void generalSettingPopUp(){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View popupView = inflater.inflate(R.layout.general_seeting_popup_layout, null);
        Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar);
        dialog.setContentView(popupView);
        dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_FullScreenDialog;
        dialog.show();

        ImageView close_dilog_bt=popupView.findViewById(R.id.close_dilog_bt);
        close_dilog_bt.setOnClickListener(v -> {
            dialog.dismiss();
        });

    }


    void msItemPopUp(){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View popupView = inflater.inflate(R.layout.m_and_s_seeting_popup_layout, null);
        Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar);
        dialog.setContentView(popupView);
        dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_FullScreenDialog;
        dialog.show();

        ImageView close_dilog_bt=popupView.findViewById(R.id.close_dilog_bt);
        close_dilog_bt.setOnClickListener(v -> {
            dialog.dismiss();
        });
    }
}