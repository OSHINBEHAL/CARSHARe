package com.example.raman.carshare.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.raman.carshare.R;

public class ChooseActivity extends AppCompatActivity {
    private Button ChoosePlanRide,ChooseBookRide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        ChoosePlanRide= (Button) findViewById(R.id.ChoosePlanRide);
        ChooseBookRide= (Button) findViewById(R.id.ChooseBookRide);

        ChoosePlanRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseActivity.this,DriverTripDetails.class);
                startActivity(intent);
            }
        });

        ChooseBookRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseActivity.this,BookRide.class);
                startActivity(intent);
            }
        });
    }
}
