package com.credoapp.parent.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.credoapp.parent.R;
import com.credoapp.parent.model.monthlySllabusModels.GetMonthlySyllabusListResults;
import com.credoapp.parent.ui.FullImageViewScreen;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MonthlySyllabusAdaptor extends RecyclerView.Adapter<MonthlySyllabusAdaptor.MyViewHolder> {
    private ArrayList<GetMonthlySyllabusListResults> getMonthlySyllabusListResults;
    private Context mContext;
    public MonthlySyllabusAdaptor(Context activity, ArrayList<GetMonthlySyllabusListResults> getMonthlySyllabusListResults) {
        this.mContext = activity;
        this.getMonthlySyllabusListResults = getMonthlySyllabusListResults;
    }

    @NonNull
    @Override
    public MonthlySyllabusAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.monthly_syllabus_adapter, parent, false);

        return new MonthlySyllabusAdaptor.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MonthlySyllabusAdaptor.MyViewHolder holder, int position) {
        GetMonthlySyllabusListResults list = getMonthlySyllabusListResults.get(position);
        holder.subjectName.setText(list.getSubject());

        if (list.getSyllabusDescription().equals("")){
            if (list.getImageUrl().equals("")){
                holder.textSyllabusDescription.setVisibility(View.VISIBLE);
                holder.imageViewMonthlyAdaptor.setVisibility(View.GONE);
                holder.textSyllabusDescription.setText(list.getSyllabusDescription());
            }else {
                holder.textSyllabusDescription.setVisibility(View.GONE);
                holder.imageViewMonthlyAdaptor.setVisibility(View.VISIBLE);
                Picasso.with(mContext).load(list.getImageUrl()).placeholder(R.drawable.place_holder)
                        .into(holder.imageViewMonthlyAdaptor);
            }
        }else {
            holder.textSyllabusDescription.setVisibility(View.VISIBLE);
            holder.imageViewMonthlyAdaptor.setVisibility(View.GONE);
            holder.textSyllabusDescription.setText(list.getSyllabusDescription());
        }
    }

    @Override
    public int getItemCount() {
        return getMonthlySyllabusListResults.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textSyllabusDescription,subjectName;
        ImageView imageViewMonthlyAdaptor;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectName = itemView.findViewById(R.id.subjectName);
            textSyllabusDescription = itemView.findViewById(R.id.textSyllabusDescription);
            imageViewMonthlyAdaptor = itemView.findViewById(R.id.imageViewMonthlyAdaptor);


            imageViewMonthlyAdaptor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    showFullImageInDialog(getMonthlySyllabusListResults.get(getAdapterPosition()).getImageUrl(),v);

                    Intent in = new Intent(mContext, FullImageViewScreen.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    in.putExtra("cameFrom","monthlySyllabus");
                    in.putExtra("imageUrl", getMonthlySyllabusListResults.get(getAdapterPosition()).getImageUrl());
                    mContext.startActivity(in);
                }
            });
        }
    }

    private void showFullImageInDialog(String imageUrl, View view) {

//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());


        @SuppressLint("InflateParams") View myView = LayoutInflater.from(mContext).inflate(R.layout.image_dialog_layout, null);
//        LayoutInflater inflater = LayoutInflater.from(context);
////                getLayoutInflater();
//        View dialoglayout = inflater.inflate(R.layout.image_dialog_layout, null);
        ImageView imgOriginal = myView.findViewById(R.id.imgOriginal);


        if ((imageUrl==null)||(imageUrl.equals(""))){
            imgOriginal.setImageResource(R.drawable.download_p);
        }else {
            Glide.with(mContext).load(imageUrl)
                    .placeholder(R.drawable.download_p)
                    .thumbnail(0.5f)
                    .into(imgOriginal);
        }


//         .crossFade()
//                .centerCrop()
//                .fitCenter()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)

        builder.setView(myView);
        builder.create();
        builder.show();
    }

}
