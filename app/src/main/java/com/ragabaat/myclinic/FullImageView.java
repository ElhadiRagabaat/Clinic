package com.ragabaat.myclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

public class FullImageView extends AppCompatActivity {

    private PhotoView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image_view);

        imageView =findViewById(R.id.imageView2);
        String image = getIntent().getStringExtra("image");


        try {
            Picasso.get().load(image).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}