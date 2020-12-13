package com.ragabaat.myclinic.booking;

import android.app.AlertDialog;

import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ragabaat.myclinic.R;

import java.text.DateFormat;
import java.util.Calendar;


public class BookingFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private EditText addCosName;
    private EditText addCosPhone;
    private Spinner addCosCategory;
    private Button addCosBtn,date;

    private String category;
    private String name, phone;
    private ProgressDialog pd;

    private StorageReference storageReference;
    private DatabaseReference reference, dbRef;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking, container, false);


        addCosName = view.findViewById(R.id.addCosName);
        addCosPhone = view.findViewById(R.id.addCosPhone);
        date = view.findViewById(R.id.addDate);
        addCosBtn = view.findViewById(R.id.addCosBtn);
        addCosCategory = view.findViewById(R.id.addCosCategory);

//        date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               showDatePickerDialog(v);
//             }
//        });


        pd = new ProgressDialog(getContext());

        reference = FirebaseDatabase.getInstance().getReference().child("Booking");
        storageReference = FirebaseStorage.getInstance().getReference();


        //////for the Spiner
        String[] items = new String[]{"اختر الفئة", "إزالة الرواسب الكلسية", "عمليات حشو للأسنان", "عمليات إستئصال للأسنان", "اخري"};

        addCosCategory.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, items));

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

                startActivity(new Intent(getContext(),MainBookingActivity.class));

                //checkValidation();
            }
        });


        return view;


    }

    public void showDatePickerDialog(View v)
    {
        DialogFragment newFragment = new DaitePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");

    }



    private void checkValidation() {

        name = addCosName.getText().toString();
        phone = addCosPhone.getText().toString();


        if (name.isEmpty()) {
            addCosName.setError("فارغ");
            addCosName.requestFocus();
        } else if (phone.isEmpty()) {
            addCosPhone.setError("فارغ");
            addCosPhone.requestFocus();
        } else if (category.equals("اختر الفئة")) {

            Toast.makeText(getContext(), "الرجاء تحديد فئة ", Toast.LENGTH_LONG).show();


            //Toast.makeText(AddTeachers.this,"Please upload Teacher  Image",Toast.LENGTH_LONG).show();


        } else {

            //insertData();
        }
    }


//    private void insertData() {
//
//
//        //String title = noticeTitle.getText().toString();
//        pd.setTitle("please wait");
//        pd.show();
//
//        dbRef = reference.child(category);
//        final String uniqueKey = dbRef.push().getKey();
//
//        BookingData teacherData = new BookingData(name, phone, uniqueKey);
//
//        dbRef.child(uniqueKey).setValue(teacherData).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                pd.dismiss();
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                builder.setMessage("تم ارسال بياناتك بنجاح");
//                builder.setCancelable(true);
//                builder.setPositiveButton("اغلاغ", null);
//
//
//                addCosName.setText("");
//                addCosPhone.setText("");
//
//
//                AlertDialog dialog = null;
//                try {
//                    dialog = builder.create();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                dialog.show();
//
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//                pd.dismiss();
//                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
//            }
//        });
//
//
//    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String currentDat = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        //date.setText(currentDat);

    }

}

