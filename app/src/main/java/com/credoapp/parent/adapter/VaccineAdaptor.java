package com.credoapp.parent.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.credoapp.parent.R;
import com.credoapp.parent.model.vaccineModels.VaccineModelResults;

import java.util.List;

public class VaccineAdaptor extends RecyclerView.Adapter<VaccineAdaptor.MyViewHolder> {
    private List<VaccineModelResults> vaccineModelResults;
    public VaccineAdaptor(List<VaccineModelResults> vaccineModelResults) {

        this.vaccineModelResults = vaccineModelResults;
    }

    @NonNull
    @Override
    public VaccineAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vaccine_adaptor, parent, false);

        return new VaccineAdaptor.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull VaccineAdaptor.MyViewHolder holder, int position) {
        VaccineModelResults list = vaccineModelResults.get(position);
        String description =  list.getVaccineDescription();
//        description = description.replaceAll("[,]",",\n");
        holder.vaccineDescription.setText(" "+description);
        holder.vaccineTitle.setText(list.getVaccineMonth());
    }

    @Override
    public int getItemCount() {
        return vaccineModelResults.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView vaccineDescription,vaccineTitle;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            vaccineTitle = itemView.findViewById(R.id.vaccineTitle);
            vaccineDescription = itemView.findViewById(R.id.vaccineDescription);

        }
    }
}
