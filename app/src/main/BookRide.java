package com.example.raman.carshare.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.raman.carshare.ApiConfiguration.ApiConfiguration;
import com.example.raman.carshare.R;
import com.example.raman.carshare.httpRequestProcessor.HttpRequestProcessor;
import com.example.raman.carshare.httpRequestProcessor.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BookRide extends AppCompatActivity {
    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, urlBookRide;
    private String jsonResponseString,jsonStringToPost,ErrorMessage;
    private boolean success;
    private String message;
    private int responseData;
    private Button Search;
    private EditText edtDate, edtSource1, edtDestination1;
    private String SourceD, DestinationD, DateD;
    private String Source,Destination,Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ride);

        edtSource1 = (EditText) findViewById(R.id.edtSource1);
        edtDestination1 = (EditText) findViewById(R.id.edtDestination1);
        edtDate = (EditText) findViewById(R.id.edtDate);
        Search = (Button) findViewById(R.id.SearchButton1);

        //Initialization
        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();

        //Getting base url
        baseURL = apiConfiguration.getApi();
        urlBookRide = baseURL + "CarPoolRegistrationAPI/SearchCarPoolApplication";

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Getting Source,Destination and Date
                SourceD = edtSource1.getText().toString();
                DestinationD = edtDestination1.getText().toString();
                DateD = edtDate.getText().toString();

                if (SourceD.equals("")) {
                    Toast.makeText(BookRide.this, "Please enter VALID SOURCE POINT!!!", Toast.LENGTH_LONG).show();
                } else if (DestinationD.equals("")) {
                    Toast.makeText(BookRide.this, "Please enter VALID DESTINATION POINT!!!", Toast.LENGTH_LONG).show();
                } else if (DateD.equals("")) {
                    Toast.makeText(BookRide.this, "Please enter VALID DATE!!!", Toast.LENGTH_LONG).show();
                } else {
                    //.makeText(Login.this, ErrorMessage, Toast.LENGTH_LONG).show();
                    new BookRideTask().execute(SourceD, DestinationD, DateD);
                }
            }
        });
    }

    public class BookRideTask extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            Source = params[0];
            Destination = params[1];
            Date = params[2];


            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("Source", Source);
                jsonObject.put("Destination", Destination);
                jsonObject.put("Date", Date);


                jsonStringToPost = jsonObject.toString();
                response = httpRequestProcessor.pOSTRequestProcessor(jsonStringToPost, urlBookRide);
                jsonResponseString = response.getJsonResponseString();


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonResponseString;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Response String", s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                responseData = jsonObject.getInt("responseData");
                Log.d("responseData", String.valueOf(responseData));
                message = jsonObject.getString("message");
                Log.d("message", message);


            if (Source.equals("")||Destination.equals("")||Date.equals("")) {
                Toast.makeText(BookRide.this, "Empty Field not allowed", Toast.LENGTH_LONG).show();

            }
            else
            {
                Toast.makeText(BookRide.this, "SEARCH BEGINS !!!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(BookRide.this, DriverCurrentStatusNoRequest.class);
                startActivity(intent);
            }
            if (success) {

                JSONArray responseData = jsonObject.getJSONArray("responseData");
                for (int i = 0; i < responseData.length(); i++) {
                    JSONObject object = responseData.getJSONObject(i);
                    Source = object.getString("Source");
                    Log.d("source", String.valueOf(Source));
                    Destination = object.getString("Destination");
                    Log.d("destination", Destination);
                    Date = object.getString("Date");
                    Log.d("date", Date);

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}}

