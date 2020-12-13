package com.ragabaat.myclinic.admin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ragabaat.myclinic.R;
import com.ragabaat.myclinic.booking.BookingData;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<com.ragabaat.myclinic.admin.BookingAdapter.BookingViewHolder> {
    private Context context;
    private List<BookingData> list;
    private String category;

    public BookingAdapter(Context context, List<BookingData> list, String category) {
        this.context = context;
        this.list = list;
        this.category = category;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.booking_item_layout,parent,false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {

        BookingData bookingData = list.get(position);
        holder.textViewName.setText(bookingData.getName());
        holder.textViewPhone.setText(bookingData.getPhone());
        holder.bookingDate.setText(bookingData.getDate());
        holder.date.setText(bookingData.getDate1());
        holder.time.setText(bookingData.getTime1());


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent. setData(Uri.parse("tel:"+ bookingData.getPhone()));
               context. startActivity(dialIntent);
//
//                Intent intent = new Intent(context, CallActivity3.class);
//                intent.putExtra("phone",bookingData.getPhone());
//                intent.putExtra("category",category);
//                intent.putExtra("name",bookingData.getName());
//                context.startActivity(intent);

//
            }
        });


        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("هل أنت متأكد  تريد الحذف.!!");
                builder.setCancelable(true);
                builder.setPositiveButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Booking").child(category);

                                reference.child(bookingData.getKey()).removeValue()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                Toast.makeText(context,"تم الحذف",Toast.LENGTH_LONG).show();
                                            }

                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Toast.makeText(context,"Error"+e.getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                });

                                notifyItemRemoved(position);
                            }
                        }
                );
                builder.setNegativeButton(
                        "Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }
                );

                AlertDialog dialog = null;
                try {
                    dialog = builder.create();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BookingViewHolder extends RecyclerView.ViewHolder {


        private TextView textViewName,textViewPhone,bookingDate;
        private TextView date, time;
        private Button button;
        private ImageView  imageView;
        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.customerName);
            imageView = itemView.findViewById(R.id.call_imageView);
            textViewPhone = itemView.findViewById(R.id.customerPhone);
           bookingDate = itemView.findViewById(R.id.customerdate);
            button = itemView.findViewById(R.id.deleteBooking);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time1);
        }
    }

}
