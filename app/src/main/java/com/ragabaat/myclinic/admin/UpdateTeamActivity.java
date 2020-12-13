package com.ragabaat.myclinic.admin;

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
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateTeamActivity extends AppCompatActivity {

    private CircleImageView updateTeacherImage;
    private EditText updateTeacherName,updateTeacherPost;
    private Button updateTeacherBtn,deleteTeacherBtn;

    private String name,email,image,post;
    private final int REQ= 1;
    private Bitmap bitmap = null;

    private StorageReference storageReference;
    private DatabaseReference reference;
    private String  downLoadUrl,category,uniqukey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_team);

        ///// for revieve data by intent to this activity

        name = getIntent().getStringExtra("name");

        uniqukey = getIntent().getStringExtra("key");
        post = getIntent().getStringExtra("bio");
        image = getIntent().getStringExtra("image");






        reference = FirebaseDatabase.getInstance().getReference().child("team");
        storageReference = FirebaseStorage.getInstance().getReference();


        updateTeacherImage =findViewById(R.id.updateTeacherImage);
        updateTeacherName = findViewById(R.id.updateTeacherName);
        updateTeacherPost = findViewById(R.id.updateTeacherPost);
        updateTeacherBtn  = findViewById(R.id.updateTeacherBtn);
        deleteTeacherBtn  = findViewById(R.id.deleteTeacherBtn);



        //// for the retrieve image
        try {
            Picasso.get().load(image).into(updateTeacherImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //// for the all info

        updateTeacherName.setText(name);
        updateTeacherPost.setText(post);

        updateTeacherImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                openGallery();

                return false;
            }
        });

        updateTeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = updateTeacherName.getText().toString();

                post = updateTeacherPost.getText().toString();

                checkValidation();
            }
        });

        deleteTeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                deleteData();
            }
        });

    }

    private void deleteData() {

        reference.child(uniqukey).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(UpdateTeamActivity.this, "تم حذف معلومات العضو بنجاح", Toast.LENGTH_LONG)
                            .show();
                    Intent intent = new Intent(UpdateTeamActivity.this, TeamActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(UpdateTeamActivity.this," هناك خطأ ما ",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void checkValidation() {

        if (name.isEmpty()){
            updateTeacherName.setError("فارغ");
            updateTeacherName.requestFocus();



        }else if (post.isEmpty()){
            updateTeacherPost.setError("فارغ");
            updateTeacherPost.requestFocus();

        }else if (bitmap == null){

            updateData(image);

        }else {

            uploadImage();
        }


    }

    private void updateData(String s) {

        HashMap hp = new HashMap();
        hp.put("name",name);
        hp.put("bio",post);
        hp.put("image",s);



        reference.child(uniqukey).updateChildren(hp)
                .addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {

                        Toast.makeText(UpdateTeamActivity.this,"تم التعديل بنجاح ",Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(UpdateTeamActivity.this,TeamActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }

                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(UpdateTeamActivity.this," هناك خطأ ما ",Toast.LENGTH_LONG).show();
            }
        });

    }


    private void uploadImage() {

//        pd.setMessage("Uploading");
//        pd.show();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalImage = baos.toByteArray();
        final StorageReference filePath;
        filePath = storageReference.child("team").child(finalImage+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalImage);
        uploadTask.addOnCompleteListener(UpdateTeamActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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

                                    updateData(downLoadUrl);
                                }
                            });
                        }
                    });
                }
                else {
//                    pd.dismiss();
                    Toast.makeText(UpdateTeamActivity.this," هناك خطأ ما",Toast.LENGTH_LONG).show();
                }
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
            updateTeacherImage.setImageBitmap(bitmap);
        }
    }
}