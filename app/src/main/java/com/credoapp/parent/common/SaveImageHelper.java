package com.credoapp.parent.common;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.lang.ref.WeakReference;

public class SaveImageHelper implements Target {
    private Context mContext;
    private WeakReference<ContentResolver> contentResolverWeakReference;
    private String name;

    public SaveImageHelper(Context mContext,ContentResolver contentResolver, String name) {
        this.contentResolverWeakReference = new WeakReference<ContentResolver>(contentResolver);
        this.name = name;
        this.mContext=mContext;
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        ContentResolver t = contentResolverWeakReference.get();
        if (t!=null){
            MediaStore.Images.Media.insertImage(t,bitmap,name,"");
//            alertDialog.dismiss();


            Toast.makeText(mContext, "Image downloaded successfully", Toast.LENGTH_SHORT).show();

//            //open gallery after download
//            Intent in = new Intent();
//            in.setType("image/*");
//            in.setAction(Intent.ACTION_GET_CONTENT);
//            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            mContext.startActivity(Intent.createChooser(in,"VIEW PICTURE"));

        }
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
}
