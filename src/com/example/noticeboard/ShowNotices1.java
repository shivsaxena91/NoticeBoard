package com.example.noticeboard;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ShowNotices1 extends Activity{

	TextView tv;
	 private ProgressDialog pDialog;
	ServiceHandler sh=new ServiceHandler();
    JsonParser jsonParser = new JsonParser();
   String url_notice_details = "http://www.shrijay.com/Rushit/updatenot.php";
   String id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_notices1);
		SharedPreferences mydata = getSharedPreferences("ids", 0);
		SharedPreferences.Editor editor = mydata.edit();
		
		 
		Bundle b = getIntent().getExtras();
			 id=b.getString("id");
		  String title = b.getString("title");
		//  Toast.makeText(getApplicationContext(), title, Toast.LENGTH_LONG).show();
          String date = b.getString("date");
          String eventdate = b.getString("eventdate");
          String dep = b.getString("department");
          String time = b.getString("time");
          String body = b.getString("body");
          String att = b.getString("att");
      //    String sem = b.getString("sem");
          tv = (TextView)findViewById(R.id.tvNotices);
          editor.putInt(id,1);
          editor.commit();
         //int n=mydata.getInt(id, 0);
         //Log.d("id",n+"");
          tv.setText("title: "+title+"\n"+"updated date: "+date+"\n"+"EventDate: "+eventdate+"\n"+"department: "+dep+"\n"+"time: "+time+"\n"+"Body: "+body+"\n");
        // new GetProductDetails().execute();
          
	}
	/*class GetProductDetails extends AsyncTask<String, String, String> {
		 
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ShowNotices1.this);
            pDialog.setMessage("Loading Notice details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
 
        
        protected String doInBackground(String... params) {
 
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    // Check for success tag
                    int success;
                    try {
                        // Building Parameters
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("n_id",id));
 
                        // getting product details by making HTTP request
                        // Note that product details url will use GET request
                        JSONObject json = jsonParser.makeHttpRequest(url_notice_details, "GET", params);
                        //String json1=sh.makeServiceCall(url_notice_details,2, params);
                        // check your log for json response
                        Log.d("Single Notice Details", json.toString());
 
                        // json success tag
                        //JSONObject json=new JSONObject(json1);
                        success = json.getInt("success");
                        if (success == 1) {
                            // successfully received product details
                          
 
                            // product with this pid found
                            // Edit Text
                          
                        }else{
                            // product with pid not found
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
 
            return null;
        }
 
       
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once got all details
            pDialog.dismiss();
        }
    }
	*/
	 
}
