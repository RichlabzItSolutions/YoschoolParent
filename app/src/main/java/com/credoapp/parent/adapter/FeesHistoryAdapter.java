package com.credoapp.parent.adapter;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.credoapp.parent.R;
import com.credoapp.parent.model.PaymentHistory;
import com.credoapp.parent.ui.FeeHistoryScreen;
import com.credoapp.parent.databinding.ItemFeeHistoryDetailsBinding;

import java.util.List;

public class FeesHistoryAdapter extends RecyclerView.Adapter<FeesHistoryAdapter.ViewHolder> {

    private FeeHistoryScreen feesDetailsScreen;
    private List<PaymentHistory> paymentHistories;

    public FeesHistoryAdapter(FeeHistoryScreen feesDetailsScreen, List<PaymentHistory> paymentHistories) {
        this.feesDetailsScreen = feesDetailsScreen;
        this.paymentHistories = paymentHistories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(feesDetailsScreen), R.layout.item_fee_history_details, parent, false);
        ItemFeeHistoryDetailsBinding binding = (ItemFeeHistoryDetailsBinding) viewDataBinding;
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PaymentHistory paymentHistory = paymentHistories.get(position);
        holder.binding.termsText.setText(paymentHistory.getTerm_title());
        holder.binding.feeType.setText(paymentHistory.getFee_lable_title());
        holder.binding.priceText.setText("\u20B9 " + paymentHistory.getPaid_amount());
        holder.binding.paidAmountDateText.setText(paymentHistory.getPaid_on());
        //holder.binding.paymentModeValueText.setText(paymentHistory.getPay_mode());
    }

    @Override
    public int getItemCount() {
        return paymentHistories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ItemFeeHistoryDetailsBinding binding;

        public ViewHolder(ItemFeeHistoryDetailsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
