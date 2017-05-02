package com.example.raman.carshare.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ListView;

import com.example.raman.carshare.ApiConfiguration.ApiConfiguration;
import com.example.raman.carshare.R;
import com.example.raman.carshare.httpRequestProcessor.HttpRequestProcessor;
import com.example.raman.carshare.httpRequestProcessor.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DriverCurrentStatus extends AppCompatActivity {

    private SingleRowDriver singleRow;
    private ListView lv;
    MyAdapterDriver myAdapter;


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
        setContentView(R.layout.activity_driver_current_status);

        //findViewByID
        lv = (ListView) findViewById(R.id.lv);

        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();

        baseURL = apiConfiguration.getApi();
        urlSubmitPool = baseURL + "CarPoolAssociation/SubmitCarPoolRequest";
        singleRowList = new ArrayList<>();
        new DriverCurrentStatusTask.execute();

        MyAdapterRider myAdapterRider = new MyAdapterRider(this, singleRowList);
        lv.setAdapter(myAdapterRider);
    }

public class DriverCurrentStatusTask extends AsyncTask<String,String,String>
{
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



    }
}
}


