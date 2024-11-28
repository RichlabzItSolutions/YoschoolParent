package com.credoapp.parent.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.credoapp.parent.common.SaveImageHelper;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;
import com.credoapp.parent.R;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ImageFullView extends AppCompatActivity {

    private final String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private String position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_full);


        ArrayList<String> arrayListImages;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                arrayListImages = null;
            } else {
//                cameFrom = extras.getString("cameFrom");
                position= extras.getString("position");
                arrayListImages = getIntent().getStringArrayListExtra("arrayListImages");
            }
        } else {
            arrayListImages = getIntent().getStringArrayListExtra("arrayListImages");

        }

        checkPermissions();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        LinearLayout backLayoutFullImage = findViewById(R.id.backLayoutFullImage);
        backLayoutFullImage.setOnClickListener(v -> onBackPressed());



//        Log.d("arrayListImages======>", arrayListImages + "");
        ViewPager viewPager = findViewById(R.id.viewPagerFullImage);
        FullImageViewAdaptor mAdaptor = new FullImageViewAdaptor(this, arrayListImages);
        viewPager.setAdapter(mAdaptor);
        viewPager.setCurrentItem(Integer.parseInt(position));
    }


    class FullImageViewAdaptor extends PagerAdapter {
        private Context mContext;
        private ArrayList arrayListImages;
        FullImageViewAdaptor(ImageFullView imageFullView,
                             ArrayList arrayListImages) {
            this.arrayListImages = arrayListImages;
            this.mContext = imageFullView;
        }
        @Override
        public int getCount() {
            return arrayListImages.size();
        }
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = layoutInflater.inflate(R.layout.image_view, container, false);
            PhotoView imageViewFullImage = itemView.findViewById(R.id.imageViewFullImage);


            ImageView imageViewDownload = itemView.findViewById(R.id.imageViewDownload);
            String url = (String) arrayListImages.get(position);

            imageViewDownload.setOnClickListener(v -> {


                int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
                if (result == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(ImageFullView.this, "You need to accept READ_EXTERNAL_STORAGE permission to complete this task", Toast.LENGTH_SHORT).show();

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            checkPermissions();
                        }
                    }, 2000);
                }else {
                    String fileName = UUID.randomUUID().toString()+".jpg";
                    Picasso.with(getBaseContext()).load(url).
                            into(new SaveImageHelper(ImageFullView.this,getApplicationContext().getContentResolver(),fileName));

                }



//                BitmapDrawable draw = (BitmapDrawable) imageViewFullImage.getDrawable();
//                Bitmap bitmap = draw.getBitmap();
//
//                FileOutputStream outStream = null;
//                File sdCard = Environment.getExternalStorageDirectory();
//                File dir = new File(sdCard.getAbsolutePath() + "/Credo");
//                dir.mkdirs();
//                String fileName = String.format("%d.jpg", System.currentTimeMillis());
//                File outFile = new File(dir, fileName);
//                try {
//                    outStream = new FileOutputStream(outFile);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
//                try {
//                    if (outStream != null) {
//                        outStream.flush();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    outStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                intent.setData(Uri.fromFile(outFile));
//                sendBroadcast(intent);
//                Toast.makeText(mContext, "Downloaded", Toast.LENGTH_SHORT).show();

            });
            Glide.with(mContext).load(url)
                    .placeholder(R.drawable.download_p).
                    into(imageViewFullImage);
            container.addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((RelativeLayout) object);
            //super.destroyItem(container, position, object);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(getApplicationContext(), p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            int MULTIPLE_PERMISSIONS = 10;
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
        }
    }

}
