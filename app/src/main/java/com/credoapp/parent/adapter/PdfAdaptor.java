package com.credoapp.parent.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.credoapp.parent.R;
import com.credoapp.parent.model.pdfModels.PdfResults;

import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class PdfAdaptor extends RecyclerView.Adapter<PdfAdaptor.MyViewHolder> {
    private ArrayList<PdfResults> pdfResults;
    private Context mContext;
    public PdfAdaptor(Context activity, ArrayList<PdfResults> pdfResults) {
         this.pdfResults = pdfResults;
         this.mContext = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pdf_adapter, parent, false);

        return new PdfAdaptor.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        PdfResults list = pdfResults.get(i);
        myViewHolder.pdfNameText.setText(list.getPdfName());
    }

    @Override
    public int getItemCount() {
        return pdfResults.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView pdfNameText;
        RelativeLayout pdfLayout;
        ImageView buttonViewPdf;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pdfLayout = itemView.findViewById(R.id.pdfLayout);
            pdfNameText = itemView.findViewById(R.id.pdfNameText);
            buttonViewPdf = itemView.findViewById(R.id.buttonViewPdf);


            buttonViewPdf.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdfResults.get(getAdapterPosition()).getPdfFile()));
                browserIntent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(browserIntent);
            });

            pdfLayout.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdfResults.get(getAdapterPosition()).getPdfFile()));
                browserIntent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(browserIntent);
            });

        }
    }
}
