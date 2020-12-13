package com.ragabaat.myclinic.doctor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ragabaat.myclinic.R;
import com.ragabaat.myclinic.admin.TeamData;

import java.util.ArrayList;
import java.util.List;


public class DoctorFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private DatabaseReference reference,dbRef;
    private List<TeamData> list;
    private DoctorAdapter adapter;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_doctor, container, false);


        recyclerView = view. findViewById(R.id.doctorList);
        progressBar =  view.findViewById(R.id.progressBar1);

        reference = FirebaseDatabase.getInstance().getReference().child("team");

        uploadTeamDetails();
        return view;
    }
    private void uploadTeamDetails() {

        dbRef = reference;
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list = new ArrayList<TeamData>();

                for (DataSnapshot snapshot:dataSnapshot.getChildren()){

                    TeamData data = snapshot.getValue(TeamData.class);
                    list.add(data);
                }
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new DoctorAdapter(list, getContext());
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

                recyclerView.setAdapter(adapter);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}