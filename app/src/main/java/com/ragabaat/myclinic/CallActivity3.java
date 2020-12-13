package com.ragabaat.myclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CallActivity3 extends AppCompatActivity {

    private TextView textViewName,textViewPhone,Category;
    private String name,phone,categ;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call3);

        imageView = findViewById(R.id.casCall);
        textViewName= findViewById(R.id.casNam);
        Category= findViewById(R.id.casCategory);
        textViewPhone= findViewById(R.id.casphone);

        categ = getIntent().getStringExtra("category");
        name = getIntent().getStringExtra("name");
        phone = getIntent().getStringExtra("phone");
        textViewPhone.setText(phone);
        textViewName.setText(name);
        Category.setText(categ);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent. setData(Uri.parse("tel:" + phone.toString()));
                startActivity(dialIntent);
            }
        });


    }
}