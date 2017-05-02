package com.example.raman.carshare.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.raman.carshare.ApiConfiguration.ApiConfiguration;
import com.example.raman.carshare.httpRequestProcessor.HttpRequestProcessor;
import com.example.raman.carshare.R;
import com.example.raman.carshare.httpRequestProcessor.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Registration extends AppCompatActivity{
    private Button SignupButton1;
    private EditText editName, editAddress, editEmailId, editMobNo, editusrname, editPwd;
    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, urlRegister;
    private String Name, EmailId, Phone, Address, UserName, Password;
    private String jsonPostString, jsonResponseString;

    private String message;
    private int responseData;
    private int userID;
    private boolean success;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        SignupButton1 = (Button) findViewById(R.id.SignupButton1);
        editName = (EditText) findViewById(R.id.edtName);
        editAddress = (EditText) findViewById(R.id.edtAddress);
        editEmailId = (EditText) findViewById(R.id.edtEmail);
        editMobNo = (EditText) findViewById(R.id.edtNumber);
        editusrname = (EditText) findViewById(R.id.edtUsername);
        editPwd = (EditText) findViewById(R.id.edtPassword);

        //Initialization
        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();

        //Getting BaseURL
        baseURL = apiConfiguration.getApi();
        urlRegister = baseURL + "AccountAPI/SaveApplicationUser";


        SignupButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Name = editName.getText().toString();
                EmailId = editEmailId.getText().toString();
                Phone = editMobNo.getText().toString();
                Address = editAddress.getText().toString();
                UserName = editusrname.getText().toString();
                Password = editPwd.getText().toString();


                new RegistrationTask().execute(Name, Address, EmailId, Phone, UserName, Password);



            }
        });
    }
    private class RegistrationTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            Name = params[0];
            Log.e("Name", Name);
            Address = params[1];
            Log.e("Address", Address);
            EmailId = params[2];
            Log.e("EmailId", EmailId);
            Phone = params[3];
            Log.e("Phone", Phone);
            UserName = params[4];
            Log.e("UserName", UserName);
            Password = params[5];
            Log.e("Password", Password);


            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("Name", Name);
                jsonObject.put("Address", Address);
                jsonObject.put("EmailId", EmailId);
                jsonObject.put("Phone", Phone);
                jsonObject.put("UserName", UserName);
                jsonObject.put("Password", Password);

                jsonPostString = jsonObject.toString();
                response = httpRequestProcessor.pOSTRequestProcessor(jsonPostString, urlRegister);
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

                if (Name.equals("")||EmailId.equals("")||Phone.equals("")||Address.equals("")||UserName.equals("")||Password.equals("")) {
                    Toast.makeText(Registration.this, "Empty Field not allowed", Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(Registration.this, "user registered", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Registration.this, Login.class);
                    startActivity(intent);
                }


                if (success) {

                    JSONArray responseData = jsonObject.getJSONArray("responseData");
                    for (int i = 0; i < responseData.length(); i++) {
                        JSONObject object = responseData.getJSONObject(i);
                        userID = object.getInt("UserId");
                        Log.d("userId", String.valueOf(userID));
                        Name = object.getString("Name");
                        Log.d("name", Name);
                        Address = object.getString("Address");
                        Log.d("address", Address);
                        EmailId = object.getString("EmailId");
                        Log.d("emailId",EmailId);
                        Phone = object.getString("Phone");
                        Log.d("phone", Phone);
                        UserName = object.getString("UserName");
                        Log.d("userName", UserName);
                        Password = object.getString("Password");
                        Log.d("password", Password);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
