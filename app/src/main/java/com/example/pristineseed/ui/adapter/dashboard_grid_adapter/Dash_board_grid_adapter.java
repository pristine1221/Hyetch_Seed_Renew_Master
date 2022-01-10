package com.example.pristineseed.ui.adapter.dashboard_grid_adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.pristineseed.R;
import com.example.pristineseed.global.DateTimeUtilsCustome;
import com.example.pristineseed.model.DailyActivity_Model.DailyActivityResponseModel;
import com.example.pristineseed.ui.adapter.activity_adapter.DailyActivityListAdapter;
import com.example.pristineseed.ui.adapter.item.PlantingProductionLotNumberAdapter;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class Dash_board_grid_adapter extends RecyclerView.Adapter<Dash_board_grid_adapter.ViewHolder> {

    List<DashImageModel> listdata;
    OnItemClicListner onItemClicListner;
    Context activity;
    private List<DashImageModel> dataListFull;

    public interface OnItemClicListner {
        void onClick(int pos);
    }


    public Dash_board_grid_adapter(Context activity, List<DashImageModel> listdata) {
        super();
        this.listdata = listdata;
        this.activity = activity;
        // this.onItemClicListner = onItemClicListner;
        dataListFull = listdata;

    }

    @NonNull
    @Override
    public Dash_board_grid_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_image_view_adapter_layout, parent, false);
        Dash_board_grid_adapter.ViewHolder vh = new Dash_board_grid_adapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.blure_img.setVisibility(View.VISIBLE);
        Glide.with(activity)
                .load(listdata.get(position).img_url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.blure_img.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.blure_img.setVisibility(View.GONE);
                        return false;
                    }
                })
                .error(R.drawable.default_img)
                .into(holder.grid_image);

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView grid_image;
        private ImageView blure_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            grid_image = itemView.findViewById(R.id.grid_image);
            blure_img = itemView.findViewById(R.id.blure_img);
        }
    }

}