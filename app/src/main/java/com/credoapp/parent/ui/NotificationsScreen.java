package com.credoapp.parent.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.widget.Toast;
import com.credoapp.parent.R;
import com.credoapp.parent.common.Constants;
import com.google.gson.JsonObject;
import com.credoapp.parent.adapter.NotificationAdapter;
import com.credoapp.parent.databinding.ActivityNotificationsBinding;
import com.credoapp.parent.model.NotificationResponse;
import com.credoapp.parent.model.StudentInfo;
import com.credoapp.parent.preference.SharedPref;
import com.credoapp.parent.presenter.ParentPresenter;
import com.credoapp.parent.utils.Utils;

import org.parceler.Parcels;
public class NotificationsScreen extends ParentActivity {
    private ActivityNotificationsBinding binding;
    String cameFrom,parentId,studentId,adminId,classId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_notifications);
        binding = (ActivityNotificationsBinding) viewDataBinding;
        setupActionBar("MESSAGES");
        SharedPreferences sp = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        adminId = sp.getString(Constants.ADMIN_ID, adminId);
        classId = sp.getString(Constants.CLASS_ID, classId);

        ParentPresenter parentPresenter = new ParentPresenter(this);
        StudentInfo studentInfo = Parcels.unwrap(getIntent().getParcelableExtra("student_info"));
        SharedPref sharedPref = SharedPref.getmSharedPrefInstance(this);
        cameFrom = getIntent().getStringExtra("cameFrom");
        parentId = getIntent().getStringExtra("parentId");
        studentId = getIntent().getStringExtra("student_id");
        try {
            Utils.showProgressBar(this);
            JsonObject jsonObject = new JsonObject();
            if (cameFrom!=null&&cameFrom.equals("splash")){
                jsonObject.addProperty("parent_id", parentId);
                jsonObject.addProperty("student_id", "" + studentId);
                jsonObject.addProperty("admin_id", "" + adminId);
            }else {
                jsonObject.addProperty("parent_id", sharedPref.getParentId());
                jsonObject.addProperty("student_id", "" + studentInfo.getStudent_id());
                jsonObject.addProperty("admin_id", "" + adminId);
                jsonObject.addProperty("class_id", "" + classId);
            }
            parentPresenter.onGetNotifications(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void onSuccessNotifications(NotificationResponse notificationResponse) {
        Utils.hideProgressBar();
        if (notificationResponse.getCode() == 200) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            binding.notificationsList.setLayoutManager(linearLayoutManager);
            NotificationAdapter notificationAdapter = new NotificationAdapter(this, notificationResponse.getNotifications());
            binding.notificationsList.setAdapter(notificationAdapter);
        } else if (notificationResponse.getCode() == 204) {
            Toast.makeText(this, notificationResponse.getDescription(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed(){
        if (cameFrom!=null&&cameFrom.equals("splash")){
            Intent in = new Intent(getApplicationContext(),SplashScreen.class);
            startActivity(in);
            finish();
        }else {
            super.onBackPressed();
        }
    }
}