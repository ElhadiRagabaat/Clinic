package com.ragabaat.myclinic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    private TextView textView1,textView2;
    private Animation animation1,animation2;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);

//        button = findViewById(R.id.goBtn);

        animation1 = AnimationUtils.loadAnimation(this,R.anim.btgone);
        animation2 = AnimationUtils.loadAnimation(this,R.anim.btgtowe);
        textView1.startAnimation(animation2);
        textView2.startAnimation(animation2);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,MainActivity2.class));
            }
        },5000);
//
//        button.startAnimation(animation1);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//            }
//        });

    }
}