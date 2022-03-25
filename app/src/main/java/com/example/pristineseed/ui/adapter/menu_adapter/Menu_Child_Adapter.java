


package com.example.pristineseed.ui.adapter.menu_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.pristineseed.R;
import com.example.pristineseed.model.menu.MenuData;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class Menu_Child_Adapter extends RecyclerView.Adapter<Menu_Child_Adapter.MyViewHolder> {
    List<MenuData.SubMenu> child_list;
    Context context;
    OnChildItemClick  childItemClick;
    MenuData menuData;

    private int lastPosition = -1;
    int row_index = -1;
    public interface OnChildItemClick{
        void onChildItemClick(int pos, String child_title, MenuData grop_data);
    }
    public Menu_Child_Adapter(Context context, List<MenuData.SubMenu> child_list, MenuData data_menu) {
        this.context=context;
        this.child_list=child_list;
        this.menuData=data_menu;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_menu_list_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    /*    String child_title= child_list.get(position).title;
        String group_title=menuData.title;*/
        holder.bindModelAdapter(child_list,position);
    }

    @Override
    public int getItemCount() {
        return child_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView child_img_view;
        private TextView title_text;
        private MaterialCardView child_item_click;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title_text=itemView.findViewById(R.id.title_text);
            child_img_view=itemView.findViewById(R.id.child_img_view);
            child_item_click=itemView.findViewById(R.id.layout_container);

        }

        public void bindModelAdapter (List<MenuData.SubMenu> child_list,int position){
            title_text.setText(child_list.get(position).title);
            String child_menu=child_list.get(position).title;
          //  setImage(R.drawable.trial_seeds);
            switch (child_menu) {
                case "Employee Attendance":
                    setImage(R.drawable.emp_attendance);
                    break;
                case "Leave Application":
                    setImage( R.drawable.leave_application);
                    break;
                case "Leave Approval":
                    setImage( R.drawable.leave_approve);
                    break;
                case "View leave":
                    setImage( R.drawable.general_setting);
                    break;
                case "Leave Applied":
                    setImage( R.drawable.general_setting);
                    break;
                case "Add TA/DA Bill":
                    setImage( R.drawable.travel_plan);
                    break;
                case "TA/DA Approve":
                    setImage( R.drawable.general_setting);
                    break;
                case "View TA/DA List":
                    setImage( R.drawable.general_setting);
                    break;
                case "Add Daily Activity":
                    setImage( R.drawable.general_setting);
                    break;
                case "Daily Activity List":
                    setImage( R.drawable.general_setting);
                    break;
                case "Order Book":
                    setImage( R.drawable.order_book);
                    break;
                case "Order Approve":
                    setImage(R.drawable.order_approve);
                    break;
                /*case "Order List":
                    setImage( R.drawable.order_list);
                    break;*/
                case "Collection Master":
                    setImage( R.drawable.collection_master);
                    break;
                case "Distribution Master":
                    setImage( R.drawable.distributor_master);
                    break;
                case "Dealer Master":
                    setImage( R.drawable.dealer_master);
                    break;
                case "Product On Ground":
                    setImage( R.drawable.product_on_ground);
                    break;
                case "POG Approve":
                    setImage( R.drawable.sale_marketing);
                    break;
                case "Create Event":
                    setImage( R.drawable.create_event);
                    break;
                case "Approve Events":
                    setImage( R.drawable.approve_event);
                    break;
                case "View Events":
                    setImage( R.drawable.view_event);
                    break;
                case "Create TFA":
                    setImage( R.drawable.create_tfa);
                    break;
                case "Approve TFA":
                    setImage(R.drawable.approve_tfa);
                    break;
                case "View TFA":
                    setImage(R.drawable.view_tfa);
                    break;
                case "Farmer Master":
                    setImage(R.drawable.framer_master);
                    break;
                case "Customer Master":
                    setImage(R.drawable.sale_marketing);
                    break;
                case "Planting List":
                    setImage(R.drawable.planting_list);
                    break;
                case "Scheduler":
                    setImage(R.drawable.scheduler);
                    break;
                case "Create Inspection":
                    setImage(R.drawable.create_inspection);
                    break;
                case "Seed Dispatch Note":
                    setImage(R.drawable.seed_dispatch_note);
                    break;
                case "Yield Estimates":
                    setImage(R.drawable.yield_esitimate);
                    break;
                case "Plough Down List":
                    setImage(R.drawable.plought_down_list);
                    break;
                case "Plough Down List Approve":
                    setImage(R.drawable.plought_dwn_approval);
                    break;
                case "Land Selection":
                    setImage(R.drawable.land_selection);
                    break;
                case "FS Return":
                    setImage(R.drawable.fs_return);
                    break;
                case "FS Return Approval":
                    setImage(R.drawable.fs_return_aproval);
                    break;
                case "Indent for FS Requirement":
                    setImage(R.drawable.fs_indent);
                    break;
                case "FS Requirement Approval":
                    setImage(R.drawable.fs_requirement_approval);
                    break;
                case "Visitor Remarks":
                    setImage(R.drawable.visitor_remarks);
                    break;
                case "Trial Seed Production":
                    setImage(R.drawable.trial_seeds);
                    break;
                case "Upgrade app":
                    setImage(R.drawable.app_setting);
                    break;
                case "Manual Sync":
                    setImage(R.drawable.sync_custom);
                    break;
                case "Marketing Indent":
                    setImage( R.drawable.fs_indent);
                    break;
                case "Marketing Indent Approval":
                    setImage( R.drawable.marketing_indent_approve);
                    break;
                case "QA":
                    setImage(R.drawable.qa_img);
                    break;
                case "Lots Due for Inspection":
                    setImage(R.drawable.order_list);
                    break;
                case "SDN Details":
                    setImage(R.drawable.seed_dispatch_note);
                    break;
                case "Sown Acres":
                    setImage(R.drawable.musterd_img);
                    break;
                case "Prod-Inspection Status":
                    setImage(R.drawable.trial_seeds);
                    break;
                case "QA Inspection Status":
                    setImage(R.drawable.qa_img);
                    break;
                case "QA Inspection Flag Wise":
                    setImage(R.drawable.qa_img);
                    break;
                case "Prod-Sown & PLD Acreage":
                    setImage(R.drawable.plought_down_list);
                    break;
                case "Zone Or Distributor wise Mkt Indent Details":
                    setImage(R.drawable.fs_indent);
                    break;
                case "Zone Or Distributor wise Order Details":
                    setImage(R.drawable.distributor_master);
                    break;
                case "Zone Or Distributor wise Supply Details":
                    setImage(R.drawable.scheduler);

                default:
                    if(child_menu.equalsIgnoreCase("")){
                        setImage(R.drawable.sale_marketing);
                    }
            }

            child_item_click.setOnClickListener(v -> {
            /*    row_index=position;
                notifyDataSetChanged();*/
                childItemClick.onChildItemClick(position,child_list.get(position).title,menuData);
            });

/*
            if (row_index==position) {
                child_item_click.setCardBackgroundColor(context.getResources().getColor(R.color.chip_ripple));
            } else {
                child_item_click.setCardBackgroundColor(context.getResources().getColor(R.color.my_app_background_color));
            }*/
        }

        private void setImage(int img_id){
            if(img_id!=0) {
                Glide.with(context)
                        .load(img_id)
                        .transition(DrawableTransitionOptions.withCrossFade(1000))
                        .fitCenter()
                        .error(R.drawable.default_img)
                        .placeholder(R.color.my_app_background_color)
                        .into(child_img_view);
            }
        }
    }

    public void setchildItemClick( OnChildItemClick  childItemClick){
        this.childItemClick=childItemClick;

    }

}
