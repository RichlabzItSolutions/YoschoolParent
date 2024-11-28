package com.credoapp.parent.events;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.credoapp.parent.R;
import com.credoapp.parent.preference.SharedPref;
import com.squareup.picasso.Picasso;

import java.util.List;


public class EventsAdaptor extends RecyclerView.Adapter<EventsAdaptor.MyViewHolder> {


    private final List<EventsResultsList> eventsResultsLists;
    private Context context;
    private int height;


    EventsAdaptor(Context applicationContext, List<EventsResultsList> eventsResultsListList) {
        this.context = applicationContext;
        this.eventsResultsLists = eventsResultsListList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        SharedPref sharedPref = SharedPref.getmSharedPrefInstance(context);
        String heightFromDb = sharedPref.getHeightOfScreen();
        height = Integer.parseInt(heightFromDb);

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.events_adaptor, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        EventsResultsList list = eventsResultsLists.get(position);
        holder.eventTitle.setText(list.getEventTitle());
        holder.eventDate.setText(list.getEventDate());
        String photo = list.getEventImageUrl();
        final String eventId = list.getEventId();
        final String photoUrl = list.getEventImageUrl();

//        Glide.with(context).load(photo)
//                .thumbnail(0.5f)
//                .into(holder.eventImageView);

        if ((photo==null)||(photo.equals(""))){
            holder.eventImageView.setImageResource(R.drawable.place_holder);
        }else {
            Picasso.with(context).load(photo)
                    .placeholder(R.drawable.place_holder)
                    .into(holder.eventImageView);
        }

        holder.relativeLayoutEvent.setOnClickListener(view -> {
            Intent in = new Intent(context, EventDisplayActivity.class);
            in.putExtra("value", eventId);
            in.putExtra("photoUrl", photoUrl);
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(in);

//                EventDetailsRequest request = new EventDetailsRequest();
//                request.setEventId(eventId);
//                Toast.makeText(context, eventId, Toast.LENGTH_SHORT).show();
//                CredoSource.getRestAPI().callDonation(request).enqueue(new Callback<EventDetailsResponse>() {
//                    @Override
//                    public void onResponse(@NonNull Call<EventDetailsResponse> call, @NonNull Response<EventDetailsResponse> response) {
//                        Log.d(TAG, "onResponse : login " + response);
//                        callDonationResponse(Objects.requireNonNull(response.body()),phoneNo);
//                    }
//                    @Override
//                    public void onFailure(@NonNull Call<EventDetailsResponse> call, @NonNull Throwable t) {
//                        t.printStackTrace();
//                        if (t instanceof SocketTimeoutException){
////                                    Snackbar.make(findViewById(android.R.id.content),"internal error try again",Snackbar.LENGTH_SHORT).show();
//                        }
//                    }
//                });
        });
    }
    @Override
    public int getItemCount() {
        return eventsResultsLists.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView eventImageView;
        private TextView eventTitle, eventDate;
        RelativeLayout relativeLayoutEvent;
        MyViewHolder(View itemView) {
            super(itemView);
            eventDate = itemView.findViewById(R.id.eventDate);
            eventTitle = itemView.findViewById(R.id.eventTitle);

            eventImageView = itemView.findViewById(R.id.eventImageView);
            relativeLayoutEvent = itemView.findViewById(R.id.relativeLayoutEvent);
            ViewGroup.LayoutParams params = itemView.getLayoutParams();
            params.height = height / 3;
//            Log.d("height====>", height + "");
//            Log.d("height/3====>", height / 3 + "");
            itemView.setLayoutParams(params);
        }
    }
}
