package com.credoapp.parent.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.credoapp.parent.R;
import com.credoapp.parent.adapter.MonthlySyllabusAdaptor;
import com.credoapp.parent.preference.SharedPref;


public class SplashScreen extends AppCompatActivity {

    private static final String TAG = SplashScreen.class.getSimpleName();
    private SharedPref sharedPref;
    String stringExtra;
    private String student_id,cameFrom,parentId,classId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        //int aaa = 100/0;
        stringExtra = getIntent().getStringExtra("type");
        student_id = getIntent().getStringExtra("student_id");
        parentId = getIntent().getStringExtra("parent_id");
        classId = getIntent().getStringExtra("class_id");
        cameFrom = getIntent().getStringExtra("cameFrom");
        Log.d(TAG, "stringExtra : " + stringExtra);
        sharedPref = SharedPref.getmSharedPrefInstance(this);
        startHome();
    }

    private void startHome() {
        new Handler().postDelayed(() -> {
            Intent intent;
            if (sharedPref.getParentId().equals("")) {
                intent = new Intent(SplashScreen.this, IntroScreenActivity.class);
                startActivity(intent);
            } else {
                if (cameFrom!=null&&cameFrom.equals("push")){
                    if (stringExtra.equals("testing")){
                        intent = new Intent(SplashScreen.this, ParentStudentsScreen.class);
                        intent.putExtra("student_id", student_id);
                        intent.putExtra("parentId", parentId);
                        intent.putExtra("cameFrom","splash");
                        startActivity(intent);
                    }else if (stringExtra.equals("monthly_syllabus")) {
                        intent = new Intent(SplashScreen.this, PdfActivity.class);
                        intent.putExtra("student_id", student_id);
                        intent.putExtra("cameFrom","splash");
                        intent.putExtra("classId", classId);
                        startActivity(intent);
                    }else {
                        intent = new Intent(SplashScreen.this, AssignementsScreen.class);
                        intent.putExtra("student_id", student_id);
                        intent.putExtra("classId", classId);
                        intent.putExtra("cameFrom","splash");
                        startActivity(intent);
                    }
                }else {
                    if (stringExtra != null && student_id != null) {
                        if (stringExtra.equals("assignment")) {
                            intent = new Intent(SplashScreen.this, AssignementsScreen.class);
                            intent.putExtra("student_id", student_id);
                            startActivity(intent);
                        } else {
                            intent = new Intent(SplashScreen.this, ParentStudentsScreen.class);
                            startActivity(intent);
                        }
                    } else {
                        intent = new Intent(SplashScreen.this, ParentStudentsScreen.class);
                        startActivity(intent);
                    }
                }
            }
            finish();
        }, 3 * 1000);
    }
}
