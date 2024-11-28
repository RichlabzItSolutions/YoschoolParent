package com.credoapp.parent.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.credoapp.parent.fragment.intro.IntroOneFragment;
import com.credoapp.parent.fragment.intro.IntroThreeFragment;
import com.credoapp.parent.fragment.intro.IntroTwoFragment;
import com.credoapp.parent.R;

import me.relex.circleindicator.CircleIndicator;


public class IntroScreenActivity extends AppCompatActivity {

    private ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro_screen);
        SamplePagerAdapter samplePagerAdapter = new SamplePagerAdapter(getSupportFragmentManager());
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        viewpager.setAdapter(samplePagerAdapter);
        viewpager.setOffscreenPageLimit(3);
        indicator.setViewPager(viewpager);
        samplePagerAdapter.registerDataSetObserver(indicator.getDataSetObserver());

        findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewpager.getCurrentItem() + 1 == 3) {
                    finish();
                    startActivity(new Intent(IntroScreenActivity.this, LoginScreen.class));
                } else {
                    changeNextToLetsStart(viewpager.getCurrentItem() + 1);
                    moveToPage(viewpager.getCurrentItem() + 1);
                }
            }
        });


        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeNextToLetsStart(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void changeNextToLetsStart(int position) {
        if (position == 2) {
            ((TextView) findViewById(R.id.next_button)).setText("Let's Start");
        } else {
            ((TextView) findViewById(R.id.next_button)).setText("Next");
        }
    }

    public void moveToFirstPage() {
        viewpager.setCurrentItem(0);
    }

    public void moveToPage(int position) {
        viewpager.setCurrentItem(position);
    }

    class SamplePagerAdapter extends FragmentPagerAdapter {

        private FragmentManager fm;

        public SamplePagerAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new IntroOneFragment();
                case 1:
                    return new IntroTwoFragment();
                case 2:
                    return new IntroThreeFragment();
                default:
                    return new IntroOneFragment();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
