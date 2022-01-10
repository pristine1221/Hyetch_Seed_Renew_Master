package com.example.pristineseed.ui.adapter.order_book;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.BookingUnitPriceDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.BookingUnitPriceTable;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.BookingOrder.MarketingIndentModel;
import com.example.pristineseed.model.BookingOrder.OrderBookingModel;

import java.util.List;

public class MarketingIndentLineAdapter extends BaseAdapter {

    public Activity activity;
    public List<MarketingIndentModel.MarketingIndentLine> bookinglineList;
    private String marketing_indent_no;

    public MarketingIndentLineAdapter(Activity activity,List<MarketingIndentModel.MarketingIndentLine> bookinglineList,String marketing_indent_no){
        this.activity = activity;
        this.bookinglineList = bookinglineList;
        this.marketing_indent_no=marketing_indent_no;
    }

    @Override
    public int getCount() {
        return bookinglineList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookinglineList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder","InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        convertView = inflater.inflate(R.layout.marketing_indent_line_listview_layout,null);
        FrameLayout circuler_text_section_farmer = convertView.findViewById(R.id.circuler_text_land_selection);
        TextView tv_character_ofImageView_farmer_line = convertView.findViewById(R.id.tv_character_ofImageView_land_selection);
        TextView tv_order_book_no = convertView.findViewById(R.id.tv_order_book_no);
        TextView tv_crop_code = convertView.findViewById(R.id.tv_crop_code);
        TextView tv_variety_grop = convertView.findViewById(R.id.tv_variety_grop);
        TextView tv_balance_qty = convertView.findViewById(R.id.tv_balance_qty);
        TextView tv_supply_qty=convertView.findViewById(R.id.tv_supply_qty);
        TextView tv_created_date=convertView.findViewById(R.id.tv_created_date);
        TextView unit_price=convertView.findViewById(R.id.unit_price);

        //todo set the data into list
        tv_order_book_no.setText(bookinglineList.get(position).booking_indent_no);

        tv_crop_code.setText("Crop code : " + bookinglineList.get(position).crop_code);

        tv_variety_grop.setText("Vriety Grp : " +bookinglineList.get(position).variety_group+"("+bookinglineList.get(position).variety_code+")");
        tv_balance_qty.setText("Pack size : "+bookinglineList.get(position).variety_package_size);

      //  tv_character_ofImageView_farmer_line.setText(String.valueOf("O"));

        tv_supply_qty.setText("Unit of measure code : "+bookinglineList.get(position).unit_of_measure_code);

        if(bookinglineList.get(position).created_on!=null && !bookinglineList.get(position).created_on.equals("")) {
            tv_created_date.setText(DateTimeUtilsCustome.getDateOnlyChange(bookinglineList.get(position).created_on));
        }
//        tv_created_date.setText(bookinglineList.get(position).created_on);
        PristineDatabase pristineDatabase=PristineDatabase.getAppDatabase(activity);
        try{
            BookingUnitPriceDao unitPriceDao=pristineDatabase.bookingUnitPriceDao();
            BookingUnitPriceTable bookingUnitPriceTable=unitPriceDao.fetchAllDataBybookingNowithUnitPrice(marketing_indent_no,bookinglineList.get(position).booking_indent_no);
            if(bookingUnitPriceTable!=null){
                if(bookingUnitPriceTable.getUnit_Price()!=null &&! bookingUnitPriceTable.getUnit_Price().equalsIgnoreCase("")) {
                    unit_price.setText("Unit Price : " + bookingUnitPriceTable.getUnit_Price());
                }
            }else {
                unit_price.setText("Unit Price : "+bookinglineList.get(position).unit_price);
            }

        }catch (Exception e){
           e.printStackTrace();
        }finally {
            pristineDatabase.close();
            pristineDatabase.destroyInstance();
        }

        Drawable unwrappedDrawable = AppCompatResources.getDrawable(activity, R.drawable.circle_yellow_icon);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, StaticMethods.getRandomColor());
        circuler_text_section_farmer.setBackground(unwrappedDrawable);

        return convertView;
    }

}
