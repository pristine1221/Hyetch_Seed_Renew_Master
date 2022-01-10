
package com.example.pristineseed.ui.adapter.menu_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.pristineseed.R;
import com.example.pristineseed.model.menu.MenuData;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class MenuGroupCategoryAdapter extends RecyclerView.Adapter<MenuGroupCategoryAdapter.MyViewHolder> {
    Context context;
    List<MenuData>menuGroupList;
    public OnItemClickListner onItemClickListner;
    public interface  OnItemClickListner{
        void onItemGroupClick(int pos,List<MenuData> menuData);
    }

    public MenuGroupCategoryAdapter(Context context, List<MenuData> menuGroupList){
        super();
        this.context=context;
        this.menuGroupList=menuGroupList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_category_display_popup, parent, false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       /* for(int i=0;i<menuGroupList.size();i++){
            if(!menuGroupList.get(i).title.equalsIgnoreCase("Setup")) {
                MenuData menuData=new MenuData();
                menuData.id=menuGroupList.get(i).id;
                menuData.title=menuGroupList.get(i).title;
                menuData.translate=menuGroupList.get(i).translate;
                menuData.type=menuGroupList.get(i).type;
                tempMenuList.add(menuData);
            }
        }*/
        holder.bindGroupCategory(menuGroupList, position);
    }


    @Override
    public int getItemCount() {
        return menuGroupList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title_group;
        public ImageView group_img;
        public MaterialCardView layout_container;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            group_img=itemView.findViewById(R.id.group_img);
            title_group=itemView.findViewById(R.id.title_group);
            layout_container=itemView.findViewById(R.id.layout_container_menu);

        }
        public void bindGroupCategory(List<MenuData> tempMenuList, int position) {
            String title= tempMenuList.get(position).title;
            title_group.setText(tempMenuList.get(position).title);

            switch (title){
                case "App Setting":
                    setImage(R.drawable.app_setting);
                    break;
                case "General":
                    setImage( R.drawable.general_setting);
                    break;
                case "M&S":
                    setImage(R.drawable.ms);
                    break;
                case "FSP&HSP":
                    setImage( R.drawable.trial_seeds);
                    break;
                case "HSP":
                    setImage( R.drawable.hsp);
                    break;
                case "FSP":
                    setImage(R.drawable.fsp);
                    break;
                case "TSP":
                    setImage( R.drawable.tsp);
                    break;
                case "Getting Order":
                    setImage( R.drawable.sale_marketing);
                    break;
                case "Setup":
                    setImage( R.drawable.sale_marketing);
                    break;
                case "Reports":
                    setImage(R.drawable.fs_indent);
                    break;
                default:
                    setImage( R.drawable.default_img);
            }

            layout_container.setOnClickListener(v -> {
                if(tempMenuList.get(position).title.equalsIgnoreCase("Setup")){
                    Toast.makeText(context,"The is available on Web",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    onItemClickListner.onItemGroupClick(position,tempMenuList);
                }
            });
        }

        private void setImage(int img_id){
            Glide.with(context)
                    .load(img_id)
                    .transition(DrawableTransitionOptions.withCrossFade(1000))
                    .fitCenter()
                    .placeholder(R.color.my_app_background_color)
                    .into(group_img);
        }

    }

    public void setOnItemClick(OnItemClickListner onItemClickListner){
        this.onItemClickListner=onItemClickListner;
    }



}
