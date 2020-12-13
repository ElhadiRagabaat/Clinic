package com.ragabaat.myclinic.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ragabaat.myclinic.R;

public class CustomersActivity extends AppCompatActivity {

    private Button adminTeam,adminCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);

        adminTeam = findViewById(R.id.adminTeam);
        adminCustomer = findViewById(R.id.adminCustomer);

        adminCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomersActivity.this,CustomerBookingActivity.class));
            }
        });
        adminTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomersActivity.this,TeamActivity.class));
            }
        });
    }
}