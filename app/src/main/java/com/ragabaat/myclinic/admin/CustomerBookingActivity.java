package com.ragabaat.myclinic.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ragabaat.myclinic.R;
import com.ragabaat.myclinic.booking.BookingData;

import java.util.ArrayList;
import java.util.List;

public class CustomerBookingActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView csDepartment,ChemistryDepartment,phisicDepartment,MechanicalDepartment;
    private LinearLayout csNoData,mechNoData,physicsNoData,ChemistryNoData;
    private List<BookingData> list1,list2,list3,list4;
    private BookingAdapter adapter;
    private LinearLayoutManager layoutManager;


    private DatabaseReference reference,dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_booking);

        ChemistryDepartment = findViewById(R.id.ChemistryDepartment);
        ChemistryNoData = findViewById(R.id.ChemistryNoData);
        phisicDepartment = findViewById(R.id.phisicDepartment);
        physicsNoData = findViewById(R.id.physicsNoData);
        MechanicalDepartment = findViewById(R.id.MechanicalDepartment);
        mechNoData = findViewById(R.id.mechNoData);
        csDepartment = findViewById(R.id.csDepartment);
        csNoData = findViewById(R.id.csNoData);
//
//        fab = findViewById(R.id.fab);


        reference = FirebaseDatabase.getInstance().getReference().child("Booking");

        csDepartment();
        MechanicalDepartment();
        phisicDepartment();
        ChemistryDepartment();
    }

    private void csDepartment() {

        dbRef = reference.child("إزالة الرواسب الكلسية");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list1 = new ArrayList<>();
                if (!dataSnapshot.exists()){

                    csNoData.setVisibility(View.VISIBLE);
                    csDepartment.setVisibility(View.GONE);

                }else {

                    csNoData.setVisibility(View.GONE);
                    csDepartment.setVisibility(View.VISIBLE);

                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){

                        BookingData data = snapshot.getValue(BookingData.class);
                        list1.add(data);
                    }
                    csDepartment.setHasFixedSize(true);
                    layoutManager =(new LinearLayoutManager(CustomerBookingActivity.this));
                    csDepartment.setLayoutManager(layoutManager);
                    layoutManager.setReverseLayout(true);
                    layoutManager.setStackFromEnd(true);;
                    adapter = new BookingAdapter(CustomerBookingActivity.this,list1,"إزالة الرواسب الكلسية");
                    csDepartment.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(CustomerBookingActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }


    private void MechanicalDepartment() {

        dbRef = reference.child("عمليات حشو للأسنان");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list2 = new ArrayList<>();
                if (!dataSnapshot.exists()){

                    mechNoData.setVisibility(View.VISIBLE);
                    MechanicalDepartment.setVisibility(View.GONE);

                }else {

                    mechNoData.setVisibility(View.GONE);
                    MechanicalDepartment.setVisibility(View.VISIBLE);

                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){

                        BookingData data = snapshot.getValue(BookingData.class);
                        list2.add(data);
                    }
                    MechanicalDepartment.setHasFixedSize(true);
                    layoutManager =(new LinearLayoutManager(CustomerBookingActivity.this));
                    MechanicalDepartment.setLayoutManager(layoutManager);
                    layoutManager.setReverseLayout(true);
                    layoutManager.setStackFromEnd(true);;
                    adapter = new BookingAdapter(CustomerBookingActivity.this,list2,"عمليات حشو للأسنان");
                    MechanicalDepartment.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(CustomerBookingActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }



    private void phisicDepartment() {

        dbRef = reference.child("عمليات إستئصال للأسنان");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list3 = new ArrayList<>();
                if (!dataSnapshot.exists()){

                    physicsNoData.setVisibility(View.VISIBLE);
                    phisicDepartment.setVisibility(View.GONE);

                }else {

                    physicsNoData.setVisibility(View.GONE);
                    phisicDepartment.setVisibility(View.VISIBLE);

                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){

                        BookingData data = snapshot.getValue(BookingData.class);
                        list3.add(data);
                    }
                    phisicDepartment.setHasFixedSize(true);
                    layoutManager =(new LinearLayoutManager(CustomerBookingActivity.this));
                    phisicDepartment.setLayoutManager(layoutManager);
                    layoutManager.setReverseLayout(true);
                    layoutManager.setStackFromEnd(true);
                    adapter = new BookingAdapter(CustomerBookingActivity.this,list3,"عمليات إستئصال للأسنان");
                    phisicDepartment.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(CustomerBookingActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ChemistryDepartment() {

        dbRef = reference.child("اخري");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list4 = new ArrayList<>();
                if (!dataSnapshot.exists()){

                    ChemistryNoData.setVisibility(View.VISIBLE);
                    ChemistryDepartment.setVisibility(View.GONE);

                }else {

                    ChemistryNoData.setVisibility(View.GONE);
                    ChemistryDepartment.setVisibility(View.VISIBLE);

                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){

                        BookingData data = snapshot.getValue(BookingData.class);
                        list4.add(data);
                    }
                    ChemistryDepartment.setHasFixedSize(true);
                    layoutManager =(new LinearLayoutManager(CustomerBookingActivity.this));
                    ChemistryDepartment.setLayoutManager(layoutManager);
                    layoutManager.setReverseLayout(true);
                    layoutManager.setStackFromEnd(true);
                    adapter = new BookingAdapter(CustomerBookingActivity.this,list4,"اخري");
                    ChemistryDepartment.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(CustomerBookingActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}