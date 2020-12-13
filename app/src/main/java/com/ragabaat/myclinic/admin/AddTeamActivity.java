package com.ragabaat.myclinic.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ragabaat.myclinic.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddTeamActivity extends AppCompatActivity {

    private CircleImageView addTeamImage;
    private EditText addTeamName;
    private EditText addTeamBio;

    private Button addTeamBtn;

    private final int REQ = 1;
    private Bitmap bitmap = null;

    private String name, Bio, downLoadUrl = "";
    private ProgressDialog pd;

    private StorageReference storageReference;
    private DatabaseReference reference, dbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_team);


        addTeamImage = findViewById(R.id.addTeamImage);
        addTeamName = findViewById(R.id.addTeamName);
        addTeamBio = findViewById(R.id.addTeamBio);
        addTeamBtn = findViewById(R.id.addTeamBtn);


        pd = new ProgressDialog(this);

        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();


        addTeamImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openGallery();
            }
        });
        addTeamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });

    }



    private void openGallery() {

        Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage,REQ);

    }



    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode ==RESULT_OK){
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            addTeamImage.setImageBitmap(bitmap);
        }
    }





    private void checkValidation() {

        name = addTeamName.getText().toString();
        Bio = addTeamBio.getText().toString();


        if (name.isEmpty()){
            addTeamName.setError("فارغ");
            addTeamName.requestFocus();
        }
        else if (Bio.isEmpty()) {
            addTeamBio.setError("فارغ");
            addTeamBio.requestFocus();
        }





        else if (bitmap == null){

            //Toast.makeText(AddTeachers.this,"Please upload Teacher  Image",Toast.LENGTH_LONG).show();

            uploadData();

        }else {

            uploadImage();
        }
    }
    private void uploadData() {



        reference = reference.child("team");

        final String uniqueKey = reference.push().getKey();
        String bio = addTeamBio.getText().toString();
        String name = addTeamName.getText().toString();



        TeamData teamData = new TeamData(name,bio,downLoadUrl,uniqueKey);


        reference.child(uniqueKey).setValue(teamData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                pd.dismiss();
                Toast.makeText(AddTeamActivity.this,"تم تحميل صورة العضو بنجاح",Toast.
                        LENGTH_SHORT).show();

                startActivity(new Intent(AddTeamActivity.this,TeamActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                pd.dismiss();
                Toast.makeText(AddTeamActivity.this,"هل هناك خطب ما",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void uploadImage() {
        pd.setMessage("جارٍ التحميل ...");
        pd.show();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalImage = baos.toByteArray();
        final StorageReference filePath;
        filePath = storageReference.child("team").child(finalImage+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalImage);
        uploadTask.addOnCompleteListener(AddTeamActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                if (task.isSuccessful()){

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    downLoadUrl = String.valueOf(uri);
                                    uploadData();
                                }
                            });
                        }
                    });
                }
                else {
                    pd.dismiss();
                    Toast.makeText(AddTeamActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}