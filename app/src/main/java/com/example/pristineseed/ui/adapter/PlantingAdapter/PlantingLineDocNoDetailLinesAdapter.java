package com.example.pristineseed.ui.adapter.PlantingAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pristineseed.R;
import com.example.pristineseed.model.PlantingModel.PlantingLineDocNoDetails;

import java.util.List;

public class PlantingLineDocNoDetailLinesAdapter extends ArrayAdapter {
    private Context context;
    private int resourceid;
    private List<PlantingLineDocNoDetails.Line> plantingDocNoLies;
    public PlantingLineDocNoDetailLinesAdapter(@NonNull Context context, int resource,List<PlantingLineDocNoDetails.Line> plantingDocNoLies) {
        super(context, resource,plantingDocNoLies);
        this.context=context;
        this.resourceid=resource;
        this.plantingDocNoLies=plantingDocNoLies;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        PlantingLineDocNoDetails.Line line=plantingDocNoLies.get(position);
        TextView tv_line_no,tv_lot_no,tv_item_no,tv_receipt_no,tv_variant_code,tv_bin_coe,tv_no_of_bags,
                            tv_quantity,tv_measure_in,tv_landIn_acre,tv_description,tv_line_message;
        View view = convertView;

        try {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                view = inflater.inflate(resourceid, parent, false);
            }
            //todo initialize....................................
            tv_line_no = view.findViewById(R.id.tv_line_no);
            tv_lot_no=view.findViewById(R.id.tv_lot_no);
            tv_item_no=view.findViewById(R.id.tv_item_no);
            tv_receipt_no=view.findViewById(R.id.tv_receipt_no);
            tv_variant_code=view.findViewById(R.id.tv_variant_code);
            tv_bin_coe=view.findViewById(R.id.tv_bin_coe);
            tv_no_of_bags=view.findViewById(R.id.tv_no_of_bags);
            tv_quantity=view.findViewById(R.id.tv_quantity);
            tv_measure_in=view.findViewById(R.id.tv_measure_in);
            tv_landIn_acre=view.findViewById(R.id.tv_landIn_acre);
            tv_description=view.findViewById(R.id.tv_description);
            tv_line_message=view.findViewById(R.id.tv_line_message);

            //todo bind.................................................
            tv_line_no.setText(line.getLineNo());
            tv_lot_no.setText(line.getLotNo());
            tv_item_no.setText(line.getNo());
            tv_receipt_no.setText(line.getReceiptNo());
            tv_variant_code.setText(line.getVariantCode());
            tv_bin_coe.setText(line.getBinCode());
            tv_no_of_bags.setText(line.getNoOfBags());
            tv_quantity.setText(line.getQuantity());
            tv_measure_in.setText(line.getUnitOfMeasure());
            tv_landIn_acre.setText(line.getLandInAcre());
            tv_description.setText(line.getDescription());
            tv_line_message.setText(line.getMessage());


        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Nullable
    @Override
    public PlantingLineDocNoDetails.Line getItem(int position) {
        return plantingDocNoLies.get(position);
    }

    @Override
    public int getCount() {
        return plantingDocNoLies.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
