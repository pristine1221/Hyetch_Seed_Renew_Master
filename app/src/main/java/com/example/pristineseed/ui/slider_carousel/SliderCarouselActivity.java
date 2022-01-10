package com.example.pristineseed.ui.slider_carousel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.pristineseed.LoginActivity;
import com.example.pristineseed.R;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.ui.bootmMainScreen.BottomMainActivity;
import com.example.pristineseed.ui.slider_carousel.slider_crosal.SliderFragment;


public class SliderCarouselActivity extends AppCompatActivity {
    MyPagerAdapter myPagerAdapter;
    Button btn_next, btn_skip;
    ViewPager view_pager;
    TextView indicator1, indicator2, indicator3;
    SharedPreferences sharedpreferences;
    SessionManagement sessionManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_carousel);
        sessionManagement=new SessionManagement(this);
        sharedpreferences = getSharedPreferences("init_crosal_images", Context.MODE_PRIVATE);
       /* if (sessionManagement.getLastSession().equalsIgnoreCase("1")) {
            Intent intent = new Intent(SliderCarouselActivity.this, BottomMainActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Intent intent = new Intent(SliderCarouselActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }*/

        view_pager = findViewById(R.id.view_pager);
        btn_next = findViewById(R.id.btn_next);
        indicator1 = findViewById(R.id.indicator1);
        indicator2 = findViewById(R.id.indicator2);
        indicator3 = findViewById(R.id.indicator3);
        btn_skip = findViewById(R.id.btn_skip);
        myPagerAdapter = new MyPagerAdapter(SliderCarouselActivity.this.getSupportFragmentManager());
        view_pager.setAdapter(myPagerAdapter);
        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == myPagerAdapter.getCount() - 1) {
                    btn_next.setText("DONE");
                } else {
                    btn_next.setText("NEXT");
                }
                switch (position) {
                    case 0: {
                        indicator1.setTextColor(Color.BLACK);
                        indicator2.setTextColor(Color.GRAY);
                        indicator3.setTextColor(Color.GRAY);
                        break;
                    }
                    case 1: {
                        indicator1.setTextColor(Color.GRAY);
                        indicator2.setTextColor(Color.BLACK);
                        indicator3.setTextColor(Color.GRAY);
                        break;
                    }
                    case 2: {
                        indicator1.setTextColor(Color.GRAY);
                        indicator2.setTextColor(Color.GRAY);
                        indicator3.setTextColor(Color.BLACK);
                        break;
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                view_pager.setCurrentItem(view_pager.getCurrentItem());
            }
        });

        btn_next.setOnClickListener(view -> {
            if (btn_next.getText().toString().equalsIgnoreCase("DONE")) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean("crosalScreen", true);
                editor.commit();
                Intent loginIntent = new Intent(SliderCarouselActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            } else {
                view_pager.setCurrentItem(view_pager.getCurrentItem() + 1);
            }
        });
        btn_skip.setOnClickListener(view -> {

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean("crosalScreen", true);
            editor.commit();
            Intent loginIntent = new Intent(SliderCarouselActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        });
    }


    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    SliderFragment fragment1 = new SliderFragment();
                    bundle.putInt("flag", R.drawable.ecommerce);
                    bundle.putString("heading", "Omni Channel E-Commerce Solution?");
                    bundle.putString("sub_text", "Omnichannel transformation requires adopting the view that breaking down boundaries is a necessity. It’s not an event; rather, it’s a journey");
                    fragment1.setArguments(bundle);
                    return fragment1;
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    SliderFragment fragment2 = new SliderFragment();
                    bundle.putInt("flag", R.drawable.comple_wms);
                    bundle.putString("heading", "PristineFulFil a complete mobile WMS");
                    bundle.putString("sub_text", "PristineFulFil is complete Warehouse Management System provides configurable Supply Chain processes required by companies to provide better customer service and control operational costs.");
                    fragment2.setArguments(bundle);
                    return fragment2;
                case 2: // Fragment # 1 - This will show SecondFragment
                    SliderFragment fragment3 = new SliderFragment();
                    bundle.putInt("flag", R.drawable.pristine_seeds);
                    bundle.putString("heading", "Challenges of Seed Industry");
                    bundle.putString("sub_text", "Pristine has created a Seed Module for Seed Industry in Microsoft Dynamics NAV Our Module covers following important processes: Production Processing Research & Development Sales & Marketing Warehouse and Inventory ");
                    fragment3.setArguments(bundle);
                    return fragment3;
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }
}