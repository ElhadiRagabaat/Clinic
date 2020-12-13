package com.ragabaat.myclinic;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;

public class MainActivity2 extends AppCompatActivity {

   DatabaseReference reference;

    private BottomNavigationView bottomNavigationView;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        navController= Navigation.findNavController(this,R.id.fragment_layout);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
//
//        reference = FirebaseDatabase.getInstance().getReference().child("Booking");
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                navController= Navigation.findNavController(MainActivity2,R.id.fragment_layout);
//                NavigationUI.setupWithNavController(bottomNavigationView,navController);
//            }
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });




    }

}