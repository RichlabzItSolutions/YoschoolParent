package com.credoapp.parent.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.credoapp.parent.R;


public class PaymentScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_screen);
        Toolbar toolbar = findViewById(R.id.toolbarr);
//        setSupportActionBar(toolbar);

//        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
//        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("PAYMENT STATUS");

        if (getSupportActionBar() != null){
            Log.d("=======>", "onCreate: ");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }



//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // perform whatever you want on back arrow click
//                Toast.makeText(PaymentScreen.this, "testing.....", Toast.LENGTH_SHORT).show();
//            }
//        });
//        toolbar.setTitle("PAYMENT STATUS");


        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),ParentStudentsScreen.class);
                startActivity(in);
                finish();
            }
        });
        String value;



        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                value = null;
            } else {
                value = extras.getString("value");
            }
        } else {
            value = (String) savedInstanceState.getSerializable("value");
        }



        Log.d("", "onCreate: "+value);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }


        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),ParentStudentsScreen.class);
                startActivity(in);
                finish();
            }
        });


        LinearLayout failureLayout = findViewById(R.id.failure_layout);
        LinearLayout successLayout = findViewById(R.id.success_layout);

        failureLayout.setVisibility(View.GONE);
        successLayout.setVisibility(View.GONE);

        if (value != null) {
            if (value.equals("1")){
                successLayout.setVisibility(View.VISIBLE);
            }else {
                failureLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onBackPressed(){
        Intent in = new Intent(getApplicationContext(),ParentStudentsScreen.class);
        startActivity(in);
        finish();
//        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent in = new Intent(getApplicationContext(),ParentStudentsScreen.class);
        startActivity(in);
        finish();
        return true;
    }

}
