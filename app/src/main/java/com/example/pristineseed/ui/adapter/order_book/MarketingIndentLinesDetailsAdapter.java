package com.example.pristineseed.ui.adapter.order_book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.R;
import com.example.pristineseed.model.BookingOrder.MarketingIndentApprovalModel;

import java.util.List;

public class MarketingIndentLinesDetailsAdapter extends RecyclerView.Adapter<MarketingIndentLinesDetailsAdapter.ViewHolder>{
    private Context context;
    private List<MarketingIndentApprovalModel.Marketing_Approvalindent_line> marketingApprovalindentLine;

    public MarketingIndentLinesDetailsAdapter(Context context, List<MarketingIndentApprovalModel.Marketing_Approvalindent_line> marketingApprovalindentLine){
        this.context = context;
        this.marketingApprovalindentLine = marketingApprovalindentLine;
    }

    @NonNull
    @Override
    public MarketingIndentLinesDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.marketing_indent_lines_details_adapter_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MarketingIndentLinesDetailsAdapter.ViewHolder holder, int position) {
        MarketingIndentApprovalModel.Marketing_Approvalindent_line listModel = marketingApprovalindentLine.get(position);
        holder.tv_indent_qty.setText(listModel.indent_qty);
        holder.tv_booking_qty.setText(listModel.booking_qty);
        holder.tv_balance_qty.setText(listModel.balance_qty);
        holder.tv_variety_code.setText(listModel.variety_code);
    }

    @Override
    public int getItemCount() {
        return marketingApprovalindentLine.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_indent_qty, tv_booking_qty, tv_balance_qty, tv_variety_code;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_indent_qty = itemView.findViewById(R.id.tv_indent_qty);
            tv_booking_qty = itemView.findViewById(R.id.tv_booking_qty);
            tv_balance_qty = itemView.findViewById(R.id.tv_balance_qty);
            tv_variety_code = itemView.findViewById(R.id.tv_variety_code);
        }
    }
}
