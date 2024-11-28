package com.credoapp.parent.adapter;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.credoapp.parent.R;
import com.credoapp.parent.model.Fees;
import com.credoapp.parent.ui.FeesDetailsScreen;
import com.credoapp.parent.databinding.ItemFeeDeailBinding;

import java.util.List;

public class FeesAdapter extends RecyclerView.Adapter<FeesAdapter.ViewHolder> {

    private final FeesDetailsScreen feesDetailsScreen;
    private final List<Fees> fees;

    public FeesAdapter(FeesDetailsScreen feesDetailsScreen, List<Fees> fees) {
        this.feesDetailsScreen = feesDetailsScreen;
        this.fees = fees;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(feesDetailsScreen), R.layout.item_fee_deail, parent, false);
        ItemFeeDeailBinding binding = (ItemFeeDeailBinding) viewDataBinding;
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fees fee = fees.get(position);
        holder.binding.feeType.setText(fee.getFee_label_name());
        holder.binding.priceText.setText("\u20B9 " + fee.getTotal_amount());
        holder.binding.paidAmountValueText.setText("\u20B9 " + fee.getPaid_amount());
        holder.binding.balanceAmountValueText.setText("\u20B9 " + fee.getBalance());
    }

    @Override
    public int getItemCount() {
        return fees.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ItemFeeDeailBinding binding;

        public ViewHolder(ItemFeeDeailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
