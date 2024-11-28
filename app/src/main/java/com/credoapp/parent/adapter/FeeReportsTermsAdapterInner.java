package com.credoapp.parent.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.credoapp.parent.R;
import com.credoapp.parent.model.feeModels.FeeModelsInner;

import java.util.List;


public class FeeReportsTermsAdapterInner extends RecyclerView.Adapter<FeeReportsTermsAdapterInner.MyViewHolder> {

    public List<FeeModelsInner> feeModelsInners;
    Context mContext;
    public FeeReportsTermsAdapterInner(Context mContext, List<FeeModelsInner> feeModelsInners) {
        this.mContext=mContext;
        this.feeModelsInners = feeModelsInners;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inner_fee_data, null);
        return new FeeReportsTermsAdapterInner.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        FeeModelsInner list = feeModelsInners.get(i);
        myViewHolder.feeType.setText(list.getFeeLabelName());
        myViewHolder.totalPriceText.setText(list.getTotalAmount());
        myViewHolder.paidAmountValueText.setText(list.getPaidAmount());
        myViewHolder.balanceAmountValueText.setText(list.getBalance());
    }

    @Override
    public int getItemCount() {
        return feeModelsInners.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView feeType,totalPriceText,paidAmountValueText,balanceAmountValueText;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            feeType = itemView.findViewById(R.id.feeType);
            totalPriceText = itemView.findViewById(R.id.totalPriceText);
            paidAmountValueText = itemView.findViewById(R.id.paidAmountValueText);
            balanceAmountValueText = itemView.findViewById(R.id.balanceAmountValueText);
        }
    }
}
