package com.credoapp.parent.events;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.credoapp.parent.R;
import com.credoapp.parent.ui.ImageFullView;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class ImagesAdaptorEvents extends RecyclerView.Adapter<ImagesAdaptorEvents.MyViewHolder> {
    private Context mContext;
    private final List<EventDetailsPhotos> eventDetailsPhotosList;
    private ArrayList<String> imagesList = new ArrayList<>();
    public ImagesAdaptorEvents(Context context, List<EventDetailsPhotos> eventDetailsPhotosList, ArrayList<String> imagesList) {
        this.eventDetailsPhotosList = eventDetailsPhotosList;
        this.mContext = context;
        this.imagesList = imagesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.events_details_photos, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        EventDetailsPhotos list = eventDetailsPhotosList.get(position);
        final String imageUrl = list.getPhoto();

        if ((imageUrl==null)||(imageUrl.equals(""))){
            holder.imageViewPhotos.setImageResource(R.drawable.download_p);
        }else {
            Glide.with(mContext).load(imageUrl)
                    .thumbnail(0.5f)
                    .into(holder.imageViewPhotos);
        }

//         .crossFade()
//                .centerCrop()
//                .fitCenter()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//        holder.imageViewPhotos.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showFullImageInDialog(imageUrl, view);
//            }
//        });
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
                    .thumbnail(0.5f)
                    .placeholder(R.drawable.place_holder)
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

    @Override
    public int getItemCount() {
        return eventDetailsPhotosList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewPhotos;

        MyViewHolder(View itemView) {
            super(itemView);
            imageViewPhotos = itemView.findViewById(R.id.imageViewPhotos);


            imageViewPhotos.setOnClickListener(v -> {
                Intent in = new Intent(mContext, ImageFullView.class);
                in.putExtra("position", getAdapterPosition()+"");
                Log.d("position=====>",getAdapterPosition()+"");
                in.putExtra("value","1");
                in.putStringArrayListExtra("arrayListImages", imagesList);
                in.addFlags(FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(in);
            });

        }
    }
}
