package com.example.raman.carshare.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.raman.carshare.ApiConfiguration.ApiConfiguration;
import com.example.raman.carshare.R;
import com.example.raman.carshare.httpRequestProcessor.HttpRequestProcessor;
import com.example.raman.carshare.httpRequestProcessor.Response;

public class VehicleDetails extends AppCompatActivity {
    private Button btn1;
    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, urlVehicleDetail, jsonStringToPost, jsonResponseString;
    private boolean success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicle_details);

        btn1 = (Button) findViewById(R.id.btn1);

        //Initialization
        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();

        //Getting BaseURL
        baseURL = apiConfiguration.getApi();
        urlVehicleDetail = baseURL + "CarPoolRegistrationAPI/SaveCarPoolApplication";


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
