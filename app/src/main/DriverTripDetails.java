package com.example.raman.carshare.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raman.carshare.ApiConfiguration.ApiConfiguration;
import com.example.raman.carshare.R;
import com.example.raman.carshare.httpRequestProcessor.HttpRequestProcessor;
import com.example.raman.carshare.httpRequestProcessor.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DriverTripDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, urlDriverTripDetail;
    private String jsonPostString, jsonResponseString;

    private boolean success;
    private String message;
    // private boolean success;
    private int responseData;
    private int userID;
    private Button Tripbtn1;
    private EditText edtDate, edtStartTime, edtseats, edtFare,edtFrom,edtTo;
    private String PoolDate, Time, SeatsAvailable, Charges, Source, Destination;
   // private Spinner edtSource, edtDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_trip_details);

        Tripbtn1 = (Button) findViewById(R.id.Tripbtn1);
        edtDate = (EditText) findViewById(R.id.edtDate);
        edtStartTime = (EditText) findViewById(R.id.edtStartTime);
        edtseats = (EditText) findViewById(R.id.edtseats);
        edtFare = (EditText) findViewById(R.id.edtFare);
        edtFrom = (EditText) findViewById(R.id.edtFrom);
        edtTo = (EditText) findViewById(R.id.edtTo);

       /* String[] name = getResources().getStringArray(R.array.locations);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,name );
        edtSource.setAdapter(adapter1);
        edtSource.setOnItemSelectedListener(this);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, name);
        edtDestination.setAdapter(adapter2);
        edtDestination.setOnItemSelectedListener(this);*/


//Initialization
        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();

        //Getting BaseURL
        baseURL = apiConfiguration.getApi();
        urlDriverTripDetail = baseURL + "CarPoolRegistrationAPI/SaveCarPoolApplication";

        Tripbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Source = edtSource.getText().toString();
               // Source = edtSource.getText().toString();
                PoolDate = edtDate.getText().toString();
                Time = edtStartTime.getText().toString();
                Charges = edtFare.getText().toString();
                SeatsAvailable = edtseats.getText().toString();
                Source  = edtFrom.getText().toString();
                Destination = edtTo.getText().toString();

                new DriverTripDetailsTask().execute(Source, Destination, PoolDate, Time, Charges, SeatsAvailable);
                Intent intent = new Intent(DriverTripDetails.this, DriverCurrentStatus.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class DriverTripDetailsTask extends AsyncTask<String, String, String> {
            @Override
            protected String doInBackground(String... params) {
                Source = params[0];
                Log.e("Source", Source);
                Destination = params[1];
                Log.e("Destination", Destination);
                PoolDate = params[2];
                Log.e("PoolDate", PoolDate);
                Time = params[3];
                Log.e("Time", Time);
                Charges = params[4];
                Log.e("Charges", Charges);
                SeatsAvailable = params[5];
                Log.e("SeatsAvailable", SeatsAvailable);


                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("Source", Source);
                    jsonObject.put("Destination", Destination);
                    jsonObject.put("PoolDate", PoolDate);
                    jsonObject.put("Time", Time);
                    jsonObject.put("Charges", Charges);
                    jsonObject.put("SeatsAvailable", SeatsAvailable);

                    jsonPostString = jsonObject.toString();
                    response = httpRequestProcessor.pOSTRequestProcessor(jsonPostString, urlDriverTripDetail);
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

                    if (Source.equals("")||Destination.equals("")||PoolDate.equals("")||Time.equals("")||Charges.equals("")||SeatsAvailable.equals("")) {
                        Toast.makeText(DriverTripDetails.this, "Empty Field not allowed", Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        Toast.makeText(DriverTripDetails.this, "Trip Planned Successfully!!!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(DriverTripDetails.this, DriverCurrentStatusNoRequest.class);
                        startActivity(intent);
                    }

                    if (responseData==1) {

                        JSONArray responseData = jsonObject.getJSONArray("responseData");
                        for (int i = 0; i < responseData.length(); i++) {
                            JSONObject object = responseData.getJSONObject(i);
                            //userID = object.getInt("UserId");
                            //Log.d("userId", String.valueOf(userID));
                            Source = object.getString("Source");
                            Log.d("Source", Source);
                            Destination = object.getString("Destination");
                            Log.d("Destination", Destination);
                            PoolDate = object.getString("PoolDate");
                            Log.d("PoolDate",PoolDate);
                            Time = object.getString("Time");
                            Log.d("Time", Time);
                            Charges = object.getString("Charges");
                            Log.d("Charges", Charges);
                            SeatsAvailable = object.getString("SeatsAvailable");
                            Log.d("SeatsAvailable", SeatsAvailable);
                        }
                        // startActivity(new Intent(Registration.this, Login.class));
                        //Toast.makeText(Registration.this,"user registered", Toast.LENGTH_LONG).show();


                    } else {
                        Toast.makeText(DriverTripDetails.this,"Ooops!! Invalid Details", Toast.LENGTH_LONG).show();
                        //startActivity(new Intent(Registration.this, Login.class));
                    }


                /*if (success) {
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                } else {
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                }*/

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }


/*<LinearLayout
android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:paddingLeft="15sp"
        android:paddingRight="15sp"
        android:paddingTop="15sp"
        android:weightSum="2">

<TextView
android:id="@+id/startLoctn"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginRight="140sp"
        android:layout_weight="1"
        android:background="@drawable/my_button_gray"
        android:gravity="center"
        android:text="From"
        android:textColor="#ffff"
        android:textSize="20sp"
        android:textStyle="bold" />

<Spinner
android:id="@+id/fromspinner"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_weight="1">
</Spinner>

</LinearLayout>

<LinearLayout
android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:paddingLeft="10sp"
        android:paddingRight="10sp"
        android:paddingTop="10sp"
        android:weightSum="2">

<TextView
android:id="@+id/EndLoctn"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginRight="120sp"
        android:layout_weight="1"
        android:background="@drawable/my_button_gray"
        android:gravity="center"
        android:text="To"
        android:textColor="#ffff"
        android:textSize="20sp"
        android:textStyle="bold" />

<Spinner
android:id="@+id/tospinner"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_weight="1"></Spinner>

</LinearLayout>*/
