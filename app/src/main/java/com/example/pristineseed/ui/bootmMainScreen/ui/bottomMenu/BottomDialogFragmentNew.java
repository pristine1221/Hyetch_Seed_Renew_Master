package com.example.pristineseed.ui.bootmMainScreen.ui.bottomMenu;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MotionEventCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.R;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.model.menu.MenuData;
import com.example.pristineseed.ui.adapter.menu_adapter.MenuGroupCategoryAdapter;
import com.example.pristineseed.ui.adapter.menu_adapter.Menu_Child_Adapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class BottomDialogFragmentNew extends BottomSheetDialogFragment implements MenuGroupCategoryAdapter.OnItemClickListner,Menu_Child_Adapter.OnChildItemClick {
    public static final String TAG = "ActionBottomDialogNew";

    BottomSheetBehavior bottomSheetBehavior;
    Chip close_dilog_bt;
    RecyclerView group_reyclerview;
    RecyclerView child_menu_recycelrview;
    Context context;
    SessionManagement sessionManagement;
    private TextView title_text;
    private ImageView back_press;
    private ItemClickListener mListener;
    public BottomDialogFragmentNew() {
    }

    public BottomDialogFragmentNew newInstance() {
        return new BottomDialogFragmentNew();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Dialog d = super.onCreateDialog(savedInstanceState);
        d.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                BottomSheetDialog d = (BottomSheetDialog) dialogInterface;
                FrameLayout bottomSheet = d.findViewById(R.id.design_bottom_sheet);
                bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
                bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View bottomSheet, int newState) {
                        if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                            //Log.e("BottomSheet", "Expanded");
                            bottomSheetBehavior.setDraggable(false);
                        } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                           //   Log.e("BottomSheet", "Collapsed");
                        } else if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                            dismiss();
                        }
                    }
                    @Override
                    public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                        // React to dragging events
                        bottomSheet.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                             //   Log.e("Touch",v.getId())
                                int action = MotionEventCompat.getActionMasked(event);
                                switch (action) {
                                    case MotionEvent.ACTION_DOWN:
                                        bottomSheetBehavior.setDraggable(true);
                                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                                        return false;
                                    default:
                                        return true;
                                }
                            }

                        });
                    }
                });
//                BottomSheetBehavior.from(bottomSheet)
//                        .setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
        return d;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_new_layout,container, false);
        return view;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement = new SessionManagement(getActivity());
        group_reyclerview = view.findViewById(R.id.menu_group_list);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        group_reyclerview.setLayoutManager(mGridLayoutManager);
        child_menu_recycelrview=view.findViewById(R.id.child_menu);
        GridLayoutManager mGridLayoutManager2 = new GridLayoutManager(getActivity(), 2);
        child_menu_recycelrview.setLayoutManager(mGridLayoutManager2);
        close_dilog_bt = view.findViewById(R.id.close_dilog_bt);
        back_press=view.findViewById(R.id.back_press);
        title_text=view.findViewById(R.id.title_text);
        close_dilog_bt.setOnClickListener(view1 -> {
            dismiss();
        });
        setAllCategory();
        back_press.setOnClickListener(v -> {
            callBackpress();
        });
    }

    List<MenuData> getmenuDataList() {
        return new Gson().fromJson(sessionManagement.getMenu(),new TypeToken<List<MenuData>>(){}.getType());
    }

    void setAllCategory(){
        if(getmenuDataList()!=null) {
            MenuGroupCategoryAdapter menuGroupCategoryAdapter = new MenuGroupCategoryAdapter(getActivity(), getmenuDataList());
            group_reyclerview.setAdapter(menuGroupCategoryAdapter);
            menuGroupCategoryAdapter.setOnItemClick(this);
        }
    }


    @Override
    public void onItemGroupClick(int pos, List<MenuData> menuData){
            group_reyclerview.setVisibility(View.GONE);
            title_text.setText(menuData.get(pos).title);
            child_menu_recycelrview.setVisibility(View.VISIBLE);
            back_press.setVisibility(View.VISIBLE);
            Menu_Child_Adapter menu_child_adapter = new Menu_Child_Adapter(getActivity(), menuData.get(pos).children, menuData.get(pos));
            child_menu_recycelrview.setAdapter(menu_child_adapter);
            menu_child_adapter.setchildItemClick(this);
    }


    private void callBackpress(){
        group_reyclerview.setVisibility(View.VISIBLE);
        title_text.setText("All Categories");
        back_press.setVisibility(View.GONE);
        child_menu_recycelrview.setVisibility(View.GONE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ItemClickListener) {
            mListener = (ItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ItemClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onChildItemClick(int pos, String child_title, MenuData menuData) {
        try {
            sessionManagement.setSelectedGroupMenuName(menuData.title);
            sessionManagement.setSelectedSubGroupMenuName(child_title);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mListener.onItemClick(child_title, menuData);
        dismiss();
    }

    public interface ItemClickListener {
        void onItemClick(String child_title, MenuData menuData);
    }
}
