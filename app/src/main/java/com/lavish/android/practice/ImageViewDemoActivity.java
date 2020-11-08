package com.lavish.android.practice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.lavish.android.practice.databinding.ActivityImageViewDemoBinding;

public class ImageViewDemoActivity extends AppCompatActivity {

    private ActivityImageViewDemoBinding b;

    //adjustViewBounds
    //Glide

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityImageViewDemoBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        Glide.with(this)
                .load("https://cdn.pixabay.com/photo/2016/05/05/02/37/sunset-1373171__480.jpg")
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        b.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
        .into(b.imageView2);
    }
}