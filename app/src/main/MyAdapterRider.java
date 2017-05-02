package com.example.raman.carshare.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raman.carshare.ApiConfiguration.ApiConfiguration;
import com.example.raman.carshare.R;
import com.example.raman.carshare.httpRequestProcessor.HttpRequestProcessor;
import com.example.raman.carshare.httpRequestProcessor.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.example.raman.carshare.R.id.buttonRequest;
import static com.example.raman.carshare.R.id.txtDesc;

/**
 * Created by Raman on 5/1/2017.
 */

public class MyAdapterRider extends BaseAdapter implements View.OnClickListener {

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

    public MyAdapterRider(Context context, ArrayList<SingleRowRider> singleRowList) {
        this.context = context;
        this.singleRowList = singleRowList;
    }

    @Override
    public int getCount() {
        return singleRowList.size();
    }

    @Override
    public Object getItem(int position) {
        return singleRowList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.activity_single_row_rider, parent, false);

        //Getting views of single row
        TextView txtName = (TextView) convertView.findViewById(R.id.txtName);
        TextView txtSource = (TextView) convertView.findViewById(R.id.txtSource);
        TextView txtDest = (TextView) convertView.findViewById(R.id.txtDest);
        TextView txtTime = (TextView) convertView.findViewById(R.id.txtTime);
        TextView txtCharges = (TextView) convertView.findViewById(R.id.txtCharges);
        TextView txtPhone = (TextView) convertView.findViewById(R.id.txtPhone);
        Button buttonRequest = (Button) convertView.findViewById(R.id.buttonRequest);

        SingleRowRider singleRow = singleRowList.get(position);

        name = singleRow.getName();
        source = singleRow.getSource();
        dest = singleRow.getDest();
        time = singleRow.getTime();
        charges = singleRow.getCharges();
        phone = singleRow.getPhone();


        //Setting values in views
        txtName.setText(name);
        txtSource.setText(source);
        txtDest.setText(dest);
        txtTime.setText(time);
        txtCharges.setText(charges);
        txtPhone.setText(phone);


        buttonRequest.setOnClickListener(this);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonRequest:

                httpRequestProcessor = new HttpRequestProcessor();
                response = new Response();
                apiConfiguration = new ApiConfiguration();

                baseURL = apiConfiguration.getApi();
                urlSubmitPool = baseURL + "CarPoolAssociation/SubmitCarPoolRequest";

                break;
        }
    }

    public class SubmitCarPoolTask extends AsyncTask<String, String, String> {


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
                jsonObject.put("MemberId",MemberId);
                jsonObject.put("CarPoolId",CarPoolId);
                jsonObject.put("DDate", DDate);
                jsonObject.put("Descrption",Description);


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
                    Toast.makeText(context.getApplicationContext(),message,Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(context.getApplicationContext(),"Unsuccessful",Toast.LENGTH_LONG).show();
                }
        }
            catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    }





