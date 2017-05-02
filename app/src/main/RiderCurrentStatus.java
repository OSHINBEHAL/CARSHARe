package com.example.raman.carshare.activity;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.Toast;

import com.example.raman.carshare.ApiConfiguration.ApiConfiguration;
import com.example.raman.carshare.R;
import com.example.raman.carshare.httpRequestProcessor.HttpRequestProcessor;
import com.example.raman.carshare.httpRequestProcessor.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.jar.Attributes;

public class RiderCurrentStatus extends AppCompatActivity {

private SingleRowDriver singleRow;
private ListView lv;
        MyAdapterRider myAdapter;


private HttpRequestProcessor httpRequestProcessor;
private Response response;
private ApiConfiguration apiConfiguration;
private String baseURL, urlSubmitPool;
private String jsonPostString, jsonResponseString, message;
private boolean success;

private String name, source, dest, time, charges, phone, DDate, Description;
private int MemberId, CarPoolId, responseData;


private Context context;
private ArrayList<SingleRowRider> singleRowList;
private LayoutInflater inflater;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_current_status);

        //findViewByID
        lv = (ListView) findViewById(R.id.lv);

        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();

        baseURL = apiConfiguration.getApi();
        urlSubmitPool = baseURL + "CarPoolAssociation/SubmitCarPoolRequest";
        singleRowList = new ArrayList<>();
        new RiderCurrentStatusTask.execute();

        MyAdapterRider myAdapterRider = new MyAdapterRider(this, singleRowList);
        lv.setAdapter(myAdapterRider);
        }

public class RiderCurrentStatusTask extends AsyncTask<String,String,String>
{
    @Override
    protected String doInBackground(String... params) {

        MemberId = Integer.parseInt(params[0]);
        Log.e("MemberId", String.valueOf(MemberId));

        CarPoolId = Integer.parseInt(params[1]);
        Log.e("CarPoolId", String.valueOf(CarPoolId));

        DDate = params[2];
        Log.e("DDate", DDate);


        Description = params[3];
        Log.e("Description", Description);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("MemberId", MemberId);
            jsonObject.put("CarPoolId", CarPoolId);
            jsonObject.put("DDate", DDate);
            jsonObject.put("Descrption", Description);


            jsonPostString = jsonObject.toString();
            response = httpRequestProcessor.pOSTRequestProcessor(jsonPostString, urlSubmitPool);
            jsonResponseString = response.getJsonResponseString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonResponseString;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("Response String",s);

        try {
            JSONObject jsonObject = new JSONObject(s);
            responseData = jsonObject.getInt("responseData");
            Log.d("responseData", String.valueOf(responseData));
            message = jsonObject.getString("message");
            Log.d("message", message);

            if(success)
            {
                JSONArray responseData = jsonObject.getJSONArray("responseData");
                for (int i = 0; i < responseData.length(); i++) {
                    JSONObject object = responseData.getJSONObject(i);
                    name= object.getInt("Name");
                    Log.d("Name", String.valueOf(name));
                     source= object.getString("Source");
                    Log.d("Source", source);
                    dest = object.getString("Dest");
                    Log.d("Dest", dest);
                    time = object.getString("Time");
                    Log.d("Time",time);
                    phone = object.getString("Phone");
                    Log.d("Phone", phone);
                    charges = object.getString("Charges");
                    Log.d("Charges", charges);
                    MemberId=object.getInt("MemberId");
                    SingleRowRider singleRowRider=new SingleRowRider(name,source,dest,phone,charges,time);
                    singleRowList.add(singleRowRider);

                }

                MyAdapterRider.notifyDataSetChanged();
                Toast.makeText(this,message,Toast.LENGTH_LONG).show();
            }
    }
}

