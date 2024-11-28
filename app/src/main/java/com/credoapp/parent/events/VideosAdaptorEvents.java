package com.credoapp.parent.events;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.credoapp.parent.R;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class VideosAdaptorEvents extends RecyclerView.Adapter<VideosAdaptorEvents.MyViewHolder> {

    private Context context;
    private final List<EventDetailsVideos> eventDetailsVideosList;

    public VideosAdaptorEvents(Context applicationContext, List<EventDetailsVideos> eventDetailsVideosList) {
        this.context = applicationContext;
        this.eventDetailsVideosList = eventDetailsVideosList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.events_adaptor_videos, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        EventDetailsVideos list = eventDetailsVideosList.get(position);
        String vId = null;
        final String youTubeUrl = list.getYoutubeUrl();

        String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";

        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(youTubeUrl);

        if (matcher.find()) {
            vId = matcher.group();
            Log.d("vId", vId + "");

            String url = "https://img.youtube.com/vi/" + vId + "/0.jpg";

            Glide.with(context).load(url)
                    .thumbnail(0.5f)
                    .into(holder.imageViewVideo);
//              .crossFade()
//                    .centerCrop()
//                    .fitCenter()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
        }

        final String finalVId = vId;
        holder.imageViewVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.setData(Uri.parse(youTubeUrl));
//                context.startActivity(intent);

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + finalVId));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


//                Intent in = new Intent(context, YoutubeVideos.class);
//                in.putExtra("value", finalVId);
//                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(in);
            }
        });

    }


    @Override
    public int getItemCount() {
        return eventDetailsVideosList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewVideo;

        MyViewHolder(View itemView) {
            super(itemView);
            imageViewVideo = itemView.findViewById(R.id.imageViewVideo);
        }
    }
}
