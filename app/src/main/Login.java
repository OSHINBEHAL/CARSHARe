package com.example.raman.carshare.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


import com.example.raman.carshare.ApiConfiguration.ApiConfiguration;
import com.example.raman.carshare.httpRequestProcessor.HttpRequestProcessor;
import com.example.raman.carshare.httpRequestProcessor.Response;
import com.example.raman.carshare.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    private Button Signupbutton, LoginButton;
    private EditText editName, editPassword;
    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration ApiConfiguration;
    private String baseURL, urlLogin, jsonStringToPost, jsonResponseString;
    private String userName, passwrd;
    private String UserName, Password, ErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Signupbutton = (Button) findViewById(R.id.Signupbutton);
        LoginButton = (Button) findViewById(R.id.LoginButton);
        editName = (EditText) findViewById(R.id.username);
        editPassword = (EditText) findViewById(R.id.passwrd);

        //Initialization
        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        ApiConfiguration = new ApiConfiguration();

        //Getting base url
        baseURL = ApiConfiguration.getApi();
        urlLogin = baseURL + "AccountAPI/GetLoginUser";


        Signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registration.class);
                startActivity(intent);
            }
        });


        //On clicking login button
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Getting name and password
                userName = editName.getText().toString();
                passwrd = editPassword.getText().toString();

                if (userName.equals("")) {
                    Toast.makeText(Login.this, "Empty Field not allowed", Toast.LENGTH_LONG).show();
                } else if (passwrd.equals("")) {
                    Toast.makeText(Login.this, "Empty Field not allowed", Toast.LENGTH_LONG).show();
                } else {
                    //.makeText(Login.this, ErrorMessage, Toast.LENGTH_LONG).show();
                    new LoginTask().execute(userName, passwrd);
                }
            }
        });
    }

    public class LoginTask extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            UserName = params[0];
            Password = params[1];

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("UserName", UserName);
                jsonObject.put("Password", Password);

                jsonStringToPost = jsonObject.toString();
                response = httpRequestProcessor.pOSTRequestProcessor(jsonStringToPost, urlLogin);
                jsonResponseString = response.getJsonResponseString();


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonResponseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Log.d("Response String", s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                ErrorMessage = jsonObject.getString("ErrorMessage");
                Log.d("ErrorMessage", ErrorMessage);
                if (ErrorMessage.equals("User Authenticated!!")) {
                    Toast.makeText(Login.this, "Valid user", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Login.this, ChooseActivity.class));
                } else {
                    Toast.makeText(Login.this, ErrorMessage, Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}