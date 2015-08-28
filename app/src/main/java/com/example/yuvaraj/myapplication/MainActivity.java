package com.example.yuvaraj.myapplication;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText username, password;
    Button login;

    String tag_json_obj = "json_obj_req";
    ProgressDialog pDialog;
    String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);

        login.setOnClickListener(this);


    }

    private void Volleyoperation() {

        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Validating...");
        pDialog.show();

        StringRequest sr = new StringRequest(Request.Method.POST,"http://webservice.site90.net/login.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("track", response);

                if (response.equals("success"))
                {
                    Toast.makeText(MainActivity.this, "Login Successfull",Toast.LENGTH_LONG).show();
                } else if (response.equals("failed"))
                {
                    Toast.makeText(MainActivity.this, "Login failed",Toast.LENGTH_LONG).show();

                }
                pDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("track", "error occured");
                pDialog.dismiss();

                Toast.makeText(MainActivity.this, "Login failed",Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("name",username.getText().toString());
                params.put("password",password.getText().toString());
                return params;
            }
        };


        sr.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(sr, tag_json_obj);

    }


    @Override
    public void onClick(View v) {
        Volleyoperation();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
