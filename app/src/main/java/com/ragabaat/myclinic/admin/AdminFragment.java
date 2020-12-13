package com.ragabaat.myclinic.admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ragabaat.myclinic.R;


public class AdminFragment extends Fragment {
    private Button adminLoginfBtn;
    private EditText adminLoginEmail,adminLoginPassword;
    private FirebaseAuth mAuth;
    private String password,email;
    private DatabaseReference firebaseDatabase;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_admin, container, false);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Booking");



        adminLoginfBtn = view. findViewById(R.id.adminLoginfBtn);
        adminLoginEmail = view.findViewById(R.id.adminLoginEmail);
        adminLoginPassword = view.findViewById(R.id.adminLoginPassword);

        mAuth  = FirebaseAuth.getInstance();


        adminLoginfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                password = adminLoginPassword.getText().toString();
                email= adminLoginEmail.getText().toString();

                if (email.isEmpty()){
                    adminLoginEmail.setError("Please Enter Email");
                    adminLoginEmail.requestFocus();
                }
                else   if (password.isEmpty()){
                    adminLoginPassword.setError("Please Enter Password");
                    adminLoginPassword.requestFocus();
                }
                else {

                    loginfunc();
                }


            }
        });
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(getContext(),CustomersActivity.class));

        }


        return view;
    }

    private void loginfunc() {

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            Toast.makeText(getContext(),"تم تسجيل الدخول بنجاح",Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(getContext(),CustomersActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            getActivity().finish();


                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(getContext(), "الرجاء إدخال البيانات الصحيحة.....",
                                    Toast.LENGTH_LONG).show();

                            // ...
                        }
                    }

                });


    }
}