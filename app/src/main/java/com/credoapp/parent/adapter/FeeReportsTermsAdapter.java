package com.credoapp.parent.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.credoapp.parent.R;

import com.credoapp.parent.model.feeModels.FeeModelsInnerResults;
import java.util.List;

public class FeeReportsTermsAdapter extends RecyclerView.Adapter<FeeReportsTermsAdapter.MyViewHolder> {

    private List<FeeModelsInnerResults> feeModelsResults;
    private Context mContext;

    public FeeReportsTermsAdapter(Context applicationContext, List<FeeModelsInnerResults> feeModelsResults) {
        this.mContext = applicationContext;
        this.feeModelsResults = feeModelsResults;
    }

    @NonNull
    @Override
    public FeeReportsTermsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fee_data, null);
        return new FeeReportsTermsAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FeeReportsTermsAdapter.MyViewHolder myViewHolder, int i) {
        FeeModelsInnerResults list = feeModelsResults.get(i);
        myViewHolder.titleText.setText(list.getExamCategory());
        FeeReportsTermsAdapterInner mAdapter = new FeeReportsTermsAdapterInner(mContext, list.getFeeModelsInners());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        myViewHolder.titleRecyclerView.setLayoutManager(mLayoutManager);
        myViewHolder.titleRecyclerView.setItemAnimator(new DefaultItemAnimator());
        myViewHolder.titleRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public int getItemCount() {
        return feeModelsResults.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titleText;
        RecyclerView titleRecyclerView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleRecyclerView = itemView.findViewById(R.id.titleRecyclerView);
            titleText = itemView.findViewById(R.id.termText);

        }
    }
}
