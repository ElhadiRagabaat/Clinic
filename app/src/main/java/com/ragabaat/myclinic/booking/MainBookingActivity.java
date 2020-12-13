package com.ragabaat.myclinic.booking;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ragabaat.myclinic.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainBookingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText addCosName;
    private EditText addCosPhone;
    private Spinner addCosCategory;
    private Button addCosBtn,date;

    private String category;
    private String name, phone,data;
    private ProgressDialog pd;

    private StorageReference storageReference;
    private DatabaseReference reference, dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_booking);




        addCosName = findViewById(R.id.addCosName);
        addCosPhone = findViewById(R.id.addCosPhone);
        date = findViewById(R.id.addDate);
        addCosBtn = findViewById(R.id.addCosBtn);
        addCosCategory =findViewById(R.id.addCosCategory);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment dialogPicker = new DaitePickerFragment();
                dialogPicker.show(getSupportFragmentManager(),"date picker");

            }
        });


        pd = new ProgressDialog(this);

        reference = FirebaseDatabase.getInstance().getReference().child("Booking");
        storageReference = FirebaseStorage.getInstance().getReference();


        //////for the Spiner
        String[] items = new String[]{"اختر الفئة", "إزالة الرواسب الكلسية", "عمليات حشو للأسنان", "عمليات إستئصال للأسنان", "اخري"};

        addCosCategory.setAdapter(new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, items));

        addCosCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = addCosCategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ////clickListenerAddTeacher
        addCosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                checkValidation();
            }
        });





    }




    private void checkValidation() {

        name = addCosName.getText().toString();
        data = date.getText().toString();
        phone = addCosPhone.getText().toString();


        if (name.isEmpty()) {
            addCosName.setError("فارغ");
            addCosName.requestFocus();
        } else if (phone.isEmpty()) {
            addCosPhone.setError("فارغ");
            addCosPhone.requestFocus();
        } else if (data.isEmpty()) {
            date.setError("فارغ");
            date.requestFocus();
        } else if (category.equals("اختر الفئة")) {

            Toast.makeText(this, "الرجاء تحديد فئة ", Toast.LENGTH_LONG).show();


            //Toast.makeText(AddTeachers.this,"Please upload Teacher  Image",Toast.LENGTH_LONG).show();


        } else {

            insertData();
        }
    }


    private void insertData() {


        //String title = noticeTitle.getText().toString();
        pd.setTitle("جاري ارسال البيانات ");
        pd.show();

        dbRef = reference.child(category);
        final String uniqueKey = dbRef.push().getKey();

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM");
        String date1 = currentDate.format(calForDate.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        String time1 = currentTime.format(calForTime.getTime());


        BookingData teacherData = new BookingData(name, phone, uniqueKey,data,date1,time1);

        dbRef.child(uniqueKey).setValue(teacherData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                pd.dismiss();

                AlertDialog.Builder builder = new AlertDialog.Builder(MainBookingActivity.this);
                builder.setMessage("تم ارسال بياناتك بنجاح");
                builder.setCancelable(true);
                builder.setPositiveButton(" تم", null);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                },4000);

                createNotification();
                addCosName.setText("");
                addCosPhone.setText("");
                date.setText("");


                AlertDialog dialog = null;
                try {
                    dialog = builder.create();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                dialog.show();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                pd.dismiss();
                Toast.makeText(MainBookingActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        });



    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String currentDat = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        date.setText(currentDat);

    }
    private void createNotification(){
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel channel = new NotificationChannel(category,"n", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,category)
                    .setContentTitle("تم الحجز الي " + category)
                    .setSmallIcon(R.drawable.teth)
                    .setAutoCancel(true)
                    .setSound(soundUri)
                    .setContentText(data);
            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
            managerCompat.notify(999,builder.build());
        }
    }
}

