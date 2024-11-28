package com.credoapp.parent.events;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.credoapp.parent.retrofit.ITutorSource;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.credoapp.parent.R;
import com.credoapp.parent.presenter.ParentPresenter;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDisplayActivity extends AppCompatActivity {

    private static final String TAG = EventDisplayActivity.class.getSimpleName();
    LinearLayout photosLayout, videosLayout;
    RelativeLayout photosButtonRelative, videosButtonRelative;
    RecyclerView recyclerViewPhotosInEvents, recyclerViewVideosInEvents;
    private final List<EventDetailsPhotos> eventDetailsPhotosList = new ArrayList<>();
    private final List<EventDetailsVideos> eventDetailsVideosList = new ArrayList<>();
    private final ArrayList<String> photosList = new ArrayList<>();
    ImagesAdaptorEvents iAdaptor;
    VideosAdaptorEvents vAdaptor;
    ImageView mainImageInDetails;
    TextView titleText, dateTextEventDetails, descriptionInEventDetails;

    private ProgressDialog progress;
    String eventId, photoUrl;
    int numberOfColumns = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_display);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                eventId = null;
            } else {
                eventId = extras.getString("value");
                photoUrl = extras.getString("photoUrl");
            }
        } else {
            eventId = (String) savedInstanceState.getSerializable("value");
        }
        LinearLayout backLayoutEventDetails = findViewById(R.id.back_layout_event_details);
        backLayoutEventDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        photosButtonRelative = findViewById(R.id.photosButtonRelative);
        videosButtonRelative = findViewById(R.id.videosButtonRelative);

        photosLayout = findViewById(R.id.photosLayout);
        videosLayout = findViewById(R.id.videosLayout);

        videosLayout.setVisibility(View.GONE);
        photosButtonRelative.setBackgroundResource(R.drawable.image_video_back_ground);

        mainImageInDetails = findViewById(R.id.mainImageInDetails);
        titleText = findViewById(R.id.titleText);
        dateTextEventDetails = findViewById(R.id.dateTextEventDetails);
        descriptionInEventDetails = findViewById(R.id.descriptionInEventDetails);
        getEventDetails();

        recyclerViewPhotosInEvents = findViewById(R.id.recyclerViewPhotosInEvents);
        recyclerViewPhotosInEvents.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        iAdaptor = new ImagesAdaptorEvents(getApplicationContext(), eventDetailsPhotosList,photosList);
        //recyclerView_dashboard.setItemAnimator(new DefaultItemAnimator());
        recyclerViewPhotosInEvents.setAdapter(iAdaptor);
        //prepareDashBoardList();

        recyclerViewVideosInEvents = findViewById(R.id.recyclerViewVideosInEvents);
        recyclerViewVideosInEvents.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        vAdaptor = new VideosAdaptorEvents(getApplicationContext(), eventDetailsVideosList);
        //recyclerView_dashboard.setItemAnimator(new DefaultItemAnimator());
        recyclerViewVideosInEvents.setAdapter(vAdaptor);
        //prepareDashBoardList();

        photosButtonRelative.setOnClickListener(view -> {
            photosLayout.setVisibility(View.VISIBLE);
            videosLayout.setVisibility(View.GONE);
            videosButtonRelative.setBackgroundDrawable(getResources().getDrawable(R.drawable.image_video_back_ground_null));
            photosButtonRelative.setBackgroundResource(R.drawable.image_video_back_ground);
        });

        videosButtonRelative.setOnClickListener(view -> {
            photosLayout.setVisibility(View.GONE);
            videosLayout.setVisibility(View.VISIBLE);
            photosButtonRelative.setBackgroundDrawable(getResources().getDrawable(R.drawable.image_video_back_ground_null));
            videosButtonRelative.setBackgroundResource(R.drawable.image_video_back_ground);
        });


    }

    private void getEventDetails() {
//        showLoadingDialog();
//        EventDetailsRequest request = new EventDetailsRequest();
//        request.setEventId(eventId);
//        //Toast.makeText(getApplicationContext(), eventId, Toast.LENGTH_SHORT).show();
//
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("event_id", eventId);
//
//        ParentPresenter parentPresenter = new ParentPresenter(this);
//        parentPresenter.onRequestEventsDetails(jsonObject);


        showLoadingDialog();
        EventDetailsRequest request = new EventDetailsRequest();
        request.setEventId(eventId);
        //Toast.makeText(getApplicationContext(), eventId, Toast.LENGTH_SHORT).show();
        ITutorSource.getRestAPI().getEventsDetails(request).enqueue(new Callback<EventDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<EventDetailsResponse> call, @NonNull Response<EventDetailsResponse> response) {
                Log.d(TAG, "onResponse : login " + response);
                dismissLoadingDialog();

                if (response.isSuccessful()){
                    getEventList(Objects.requireNonNull(response.body()));
                }else {
                    Toast.makeText(EventDisplayActivity.this, R.string.unexpected_error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<EventDetailsResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                dismissLoadingDialog();
                Toast.makeText(EventDisplayActivity.this, R.string.unexpected_error, Toast.LENGTH_SHORT).show();
//                if (t instanceof SocketTimeoutException) {
//                }
            }
        });
//        });
    }

    private void getEventList(EventDetailsResponse body) {
        String response = body.getResponseCode();
        String description = body.getDescription();
        if ("200".equals(response)) {//String event = body.getResults().getEventId();
            Log.e(TAG, "getEventList: ");
            String descriptionOfEvent = body.getResults().getDescription();
            String dateEvent = body.getResults().getEventDate();
            String eventTitle = body.getResults().getTitle();

            descriptionInEventDetails.setText(descriptionOfEvent);
            dateTextEventDetails.setText(dateEvent);
            titleText.setText(eventTitle);

            if ((photoUrl == null) || (photoUrl.equals(""))) {
                mainImageInDetails.setImageResource(R.drawable.download_p);
            } else {
                Picasso.with(this).load(photoUrl).placeholder(R.drawable.download_p)
                        .into(mainImageInDetails);
            }


//                Glide.with(getApplicationContext()).load(photoUrl)
//                        .thumbnail(0.5f)
//                        .centerCrop()
//                        .fitCenter()
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .into(mainImageInDetails);


            if (!(body.getPhotos().isEmpty())) {
                Log.e(TAG, "getPhotos: ");

                getPhotosList(body);
            }


            if (!(body.getVideos().isEmpty())) {
                Log.e(TAG, "getVideos: ");
                eventDetailsVideosList.clear();
                eventDetailsVideosList.addAll(body.getVideos());
                vAdaptor.notifyDataSetChanged();
            }
        } else {
            Snackbar.make(findViewById(android.R.id.content), description, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void getPhotosList(EventDetailsResponse body) {
        List<EventDetailsPhotos> profileGetCategories = body.getPhotos();


        for (int i = 0; i < profileGetCategories.size(); i++) {

            String imageName = profileGetCategories.get(i).getPhoto();
            photosList.add(imageName);
//            String imageId = profileGetCategories.get(i).getPhotoId();
//            imageIdInList.add(imageId);
        }

        eventDetailsPhotosList.clear();
        eventDetailsPhotosList.addAll(body.getPhotos());
        iAdaptor.notifyDataSetChanged();
    }

    private void showLoadingDialog() {
        if (progress == null) {
            progress = new ProgressDialog(this);
            progress.setTitle(R.string.app_name);
            progress.setMessage("Loading......");
        }
        progress.show();
        progress.setCancelable(false);
    }

    private void dismissLoadingDialog() {
        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }


    public void onSuccessEventDetails(EventDetailsResponse eventDetailsResponse) {
        dismissLoadingDialog();
        getEventList(eventDetailsResponse);
    }
}
