package com.ragabaat.myclinic.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.ragabaat.myclinic.R;
import com.ragabaat.myclinic.booking.MainBookingActivity;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;


public class HomeFragment extends Fragment {

    private SliderLayout sliderViewl;
    private Button addCosBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);



        sliderViewl = view.findViewById(R.id.slider);
        sliderViewl.setIndicatorAnimation(IndicatorAnimations.FILL);
        sliderViewl.setSliderTransformAnimation(SliderAnimations.ZOOMOUTTRANSFORMATION);
        sliderViewl.setScrollTimeInSec(1);

        addCosBtn = view.findViewById(R.id.addCosBtn);

        addCosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), MainBookingActivity.class));

                //checkValidation();
            }
        });



        setSliderViews();

        return view;
    }
    private void setSliderViews() {

        for (int i = 0; i <= 5; i++){

            DefaultSliderView sliderView = new DefaultSliderView(getContext());



            switch (i){

                case 0 :
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/clinic-f2218.appspot.com/o/Copy%20of%20Copy%20of%20Untitled.png?alt=media&token=249448e1-5fd8-4b25-b66e-36dedd388c8a");
                    break;

                case 1 :
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/clinic-f2218.appspot.com/o/%D8%A7%D9%94%D9%86%D9%88%D8%A7%D8%B9-%D8%AD%D8%B4%D9%88-%D8%A7%D9%84%D8%A7%D9%94%D8%B3%D9%86%D8%A7%D9%86-1.jpg?alt=media&token=8b55a0f7-335b-4896-bd7d-d6ed7c3d2b5f");


                case 3 :
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/clinic-f2218.appspot.com/o/%D8%AA%D9%86%D8%B8%D9%8A%D9%81%20%D8%A7%D9%84%D8%A7%D8%B3%D9%86%D8%A7%D9%86%20clean%20teeth.jpg?alt=media&token=3249b327-7745-4c0f-a410-875921ef878c");
                    break;

                case 4 :
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/clinic-f2218.appspot.com/o/image.jpg?alt=media&token=61796110-3b5a-41cc-99db-badf99c07bc8");
                    break;


                case 5 :
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/clinic-f2218.appspot.com/o/%D8%A7%D9%94%D9%86%D9%88%D8%A7%D8%B9-%D8%AD%D8%B4%D9%88-%D8%A7%D9%84%D8%A7%D9%94%D8%B3%D9%86%D8%A7%D9%86-1.jpg?alt=media&token=8b55a0f7-335b-4896-bd7d-d6ed7c3d2b5f");
                    break;


            }
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            sliderViewl.addSliderView(sliderView);



        }
    }
}