package com.credoapp.parent.adapter;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.credoapp.parent.ui.OTPScreen;
import com.credoapp.parent.R;
import com.credoapp.parent.databinding.ItemNumberSymbolBinding;

public class OTPAdapter extends RecyclerView.Adapter<OTPAdapter.ViewHolder> {

    private final OTPScreen otpScreen;
    private final String[] numberOptions;

    public OTPAdapter(OTPScreen otpScreen, String[] numberOptions) {
        this.otpScreen = otpScreen;
        this.numberOptions = numberOptions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_number_symbol, parent, false);
        ItemNumberSymbolBinding binding = (ItemNumberSymbolBinding) viewDataBinding;
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (numberOptions[position].equals("10")) {
            holder.binding.numberText.setText("");
            holder.binding.numberText.setVisibility(View.VISIBLE);
            holder.binding.backOption.setVisibility(View.GONE);
        } else if (numberOptions[position].equals("11")) {
            holder.binding.backOption.setVisibility(View.VISIBLE);
            holder.binding.backOption.setImageResource(R.drawable.back_image_);
            holder.binding.numberText.setVisibility(View.GONE);
        } else {
            holder.binding.backOption.setVisibility(View.GONE);
            holder.binding.numberText.setText(numberOptions[position]);
            holder.binding.numberText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return numberOptions.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ItemNumberSymbolBinding binding;

        public ViewHolder(ItemNumberSymbolBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.numberOptionsLayout.setOnClickListener(v -> {
                if (getAdapterPosition() == numberOptions.length - 1) {
                    otpScreen.removeValue();
                } else if (getAdapterPosition() == numberOptions.length - 3) {

                } else {
                    otpScreen.updateValue(numberOptions[getAdapterPosition()]);
                }
            });
        }
    }
}
