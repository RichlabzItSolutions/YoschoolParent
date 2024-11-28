package com.credoapp.parent.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sangcomz.fishbun.util.TouchImageView;
import com.credoapp.parent.R;


public class FullImageViewAssignments extends AppCompatActivity {
    String imageUrl,cameFrom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image_view_assignments);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                imageUrl = null;
            } else {
                cameFrom = extras.getString("cameFrom");
                imageUrl = extras.getString("imageUrl");
            }
        } else {
            imageUrl = (String) savedInstanceState.getSerializable("imageUrl");
        }
        LinearLayout backLayoutHolidaysList = findViewById(R.id.backLayoutHolidaysList);
        TextView textHeader = findViewById(R.id.textHeader);
        backLayoutHolidaysList.setOnClickListener(v -> onBackPressed());

        if (cameFrom.equals("assignments")){
            textHeader.setText("ASSIGNMENTS");
        }else {
            textHeader.setText("MONTHLY SYLLABUS");
        }

        TouchImageView fullImageView = findViewById(R.id.fullImageView);


        //ImageView fullImageView =findViewById(R.id.fullImageView);


        if ((imageUrl==null)||(imageUrl.equals(""))){
            fullImageView.setImageResource(R.drawable.download_p);
        }else {
            Glide.with(getApplicationContext()).load(imageUrl)
                    .placeholder(R.drawable.download_p)
                    .thumbnail(0.5f)
                    .into(fullImageView);
        }
    }


}