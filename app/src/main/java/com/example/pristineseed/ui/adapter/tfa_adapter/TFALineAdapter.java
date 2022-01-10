package com.example.pristineseed.ui.adapter.tfa_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pristineseed.R;
import com.example.pristineseed.model.tfa.TFAHeaderModel;

import java.util.List;

public class    TFALineAdapter  extends RecyclerView.Adapter<TFALineAdapter.MyViewModel> {
    List<TFAHeaderModel.Tfa_lineModel> listdata;
    Context context;
    public TfaItemClicListner tfaItemClicListner;

    public interface TfaItemClicListner {
        void onItemClick(int pos);

    }

    public TFALineAdapter(Context context, List<TFAHeaderModel.Tfa_lineModel> listdata) {
        super();
        this.listdata = listdata;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewModel onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tfa_line_layout_list, parent, false);
        MyViewModel vh = new MyViewModel(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewModel holder, int position) {

        TFAHeaderModel.Tfa_lineModel tfa_lineModel=listdata.get(position);

      holder.tv_salary_amt.setText(tfa_lineModel.salary_amount);
      holder.tv_salry_mnth.setText(tfa_lineModel.salary_month);
      holder.tv_remaks.setText(tfa_lineModel.remarks);
      holder.tv_created_on.setText(tfa_lineModel.created_on);
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public class MyViewModel extends RecyclerView.ViewHolder {

        private TextView tv_salary_amt;
        private TextView tv_salry_mnth;
        private TextView tv_remaks;
        private TextView tv_created_on;

        public MyViewModel(View itemView) {
            super(itemView);

           tv_salary_amt=itemView.findViewById(R.id.tv_salary_amt);
           tv_salry_mnth=itemView.findViewById(R.id.salary_mnth);
           tv_remaks=itemView.findViewById(R.id.tv_remarks);
           tv_created_on= itemView.findViewById(R.id.tv_date_);

        }
    }

}