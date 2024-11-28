package com.credoapp.parent.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import com.credoapp.parent.R;
import com.credoapp.parent.adapter.MessageAdapter;
import com.credoapp.parent.model.MessageResponse;
import com.credoapp.parent.model.StudentInfo;
import com.credoapp.parent.preference.SharedPref;
import com.credoapp.parent.presenter.ParentPresenter;
import com.credoapp.parent.utils.Utils;
import com.google.gson.JsonObject;

import org.parceler.Parcels;

public class MessagesScreen extends ParentActivity {

    private static final String TAG = MessagesScreen.class.getSimpleName();
    //    private ViewPager viewPager;
//    private TabLayout tabLayout;
    RecyclerView messages_list;
    private StudentInfo studentInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages_layout);
        setupActionBar("Messages");
        SharedPref sharedPref = SharedPref.getmSharedPrefInstance(this);
        messages_list = findViewById(R.id.messages_list);
        studentInfo = Parcels.unwrap(getIntent().getParcelableExtra("student_info"));

        //Toast.makeText(this, "Hey Amrutha", Toast.LENGTH_SHORT).show();
        Utils.showProgressBar(this);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("parent_id", sharedPref.getParentId());
        jsonObject.addProperty("admin_id", sharedPref.getAdminID());
        ParentPresenter parentPresenter = new ParentPresenter(this);
        parentPresenter.onRequestParentMessages(jsonObject);

//        viewPager = (ViewPager) findViewById(R.id.viewPager);
//        tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.addTab(tabLayout.newTab().setText("SMS Messages"));
//        tabLayout.addTab(tabLayout.newTab().setText("My Messages"));
//        tabLayout.setupWithViewPager(viewPager);
//        viewPager.addOnPageChangeListener(this);
    }

    public void onSuccessParentMessages(MessageResponse messageResponse) {

        String response = String.valueOf(messageResponse.getCode());
        String description = String.valueOf(messageResponse.getDescription());
        if (response.equals("200")){
            Log.d(TAG, "messageResponse " + messageResponse);
            Utils.hideProgressBar();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            messages_list.setLayoutManager(linearLayoutManager);
            MessageAdapter examDatesAdapter = new MessageAdapter(this, messageResponse.getMessages());
            messages_list.setAdapter(examDatesAdapter);
        }else {
            Utils.hideProgressBar();
            Toast.makeText(this, description, Toast.LENGTH_SHORT).show();
        }

    }


//    class ViewPagerAdapter extends FragmentPagerAdapter {
//        private final List<Fragment> mFragmentList = new ArrayList<>();
//        private final List<String> mFragmentTitleList = new ArrayList<>();
//
//        public ViewPagerAdapter(FragmentManager manager) {
//            super(manager);
//        }
//
//        @Override
//        public android.support.v4.app.Fragment getItem(int position) {
//            return mFragmentList.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return mFragmentList.size();
//        }
//
//        public void addFragment(Fragment fragment, String title) {
//            mFragmentList.add(fragment);
//            mFragmentTitleList.add(title);
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return mFragmentTitleList.get(position);
//        }
//    }
}
