package com.ragabaat.myclinic.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ragabaat.myclinic.R;

import java.util.ArrayList;
import java.util.List;

public class TeamActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private DatabaseReference reference,dbRef;
    private List<TeamData> list;
    private TeamAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        fab = findViewById(R.id.fabTeam);
        recyclerView = findViewById(R.id.teamList);
        progressBar = findViewById(R.id.progressBar);

        reference = FirebaseDatabase.getInstance().getReference().child("team");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(TeamActivity.this,AddTeamActivity.class));
            }
        });
        uploadTeamDetails();

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
                recyclerView.setLayoutManager(new LinearLayoutManager(TeamActivity.this));
                adapter = new TeamAdapter(list, TeamActivity.this);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

                recyclerView.setAdapter(adapter);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                progressBar.setVisibility(View.GONE);
                Toast.makeText(TeamActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
