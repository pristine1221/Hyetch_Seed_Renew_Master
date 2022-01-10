package com.example.pristineseed.ui.bootmMainScreen.ui.menuHandler;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.pristineseed.R;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.model.menu.MenuData;
import com.example.pristineseed.ui.inspection.planting.schedualer_inspection.CreateInspectionFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

public class MenuMainPageFragment extends Fragment {
    public static MenuMainPageFragment newInstance() {
        return new MenuMainPageFragment();
    }

    public String pass_key_to_child_fragment;
    String pass_data_to_child_fragment;
    public static MenuMainPageFragment newInstance(String pass_key_to_child_fragment) {
        return new MenuMainPageFragment(pass_key_to_child_fragment);
    }
    public MenuMainPageFragment(){

    }
    private MenuMainPageFragment(String pass_key_to_child_fragment){
       this.pass_key_to_child_fragment=pass_key_to_child_fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.menu_main_page_fragment, container, false);
    }

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    MenuData selected_Group;
    String selected_Group_child;
    private int selected_position = 0;
    String group_child;
    //todo  view pager scroll
    private int lastPosition = 0;
    private SessionManagement sessionManagement;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement = new SessionManagement(getActivity());
        if (getArguments() != null) {
            selected_Group_child = getArguments().getString("selected_Group_child", "");
            selected_Group = new Gson().fromJson(getArguments().getString("selected_Group", ""), MenuData.class);
            pass_data_to_child_fragment = getArguments().getString(pass_key_to_child_fragment, "");
        }

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        for (int i = 0; i < selected_Group.children.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(selected_Group.children.get(i).title));
            if (selected_Group.children.get(i).title.equalsIgnoreCase(selected_Group_child)) {
                selected_position = i;
            }
        }
        //tabLayout.addTab(tabLayout.newTab().setText(group_child));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        MenuTabAdapter adapter = new MenuTabAdapter(getActivity(), getChildFragmentManager(), tabLayout, sessionManagement,pass_key_to_child_fragment,pass_data_to_child_fragment);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(selected_position);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                try {
                    sessionManagement.setSelectedSubGroupMenuName(selected_Group.children.get(tab.getPosition()).title);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }
        });
    }

}
