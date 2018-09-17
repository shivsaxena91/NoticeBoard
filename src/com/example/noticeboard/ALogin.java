package com.example.noticeboard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class ALogin extends ActionBarActivity {
	Button login;
	TextView error;
	EditText aname,apass;
	String aname1;	
	String apass1;
	private static String url = "http://www.shrijay.com/Rushit/adminlogindata.php";
	private ProgressDialog pDialog;
	JSONArray users = null;
	int flag=0;
	
	 
	 
	 
    // JSON parser class
    ServiceHandler service=new ServiceHandler();
    
    
		protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alogin);
        login=(Button)findViewById(R.id.btnLogin);
        aname=(EditText)findViewById(R.id.aname);
        apass=(EditText)findViewById(R.id.apass);
        error=(TextView)findViewById(R.id.login_error);
  //      String jsonStr = service.makeServiceCall(url, ServiceHandler.GET);
//error.setText(jsonStr);
        
        
        login.setOnClickListener(new View.OnClickListener()
        {
        	 public void onClick(View arg0) {
        		 if(checkConnection(getApplicationContext()))
                 new GetUserDetails().execute();
        		 else
        			 Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection", Toast.LENGTH_LONG).show();
        	 }
        });
       
		
		}
        class GetUserDetails extends AsyncTask<String, String, String> {
        	 
            /**
             * Before starting background thread Show Progress Dialog
             * */
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(ALogin.this);
                pDialog.setMessage("Authenticating");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }
     
            /**
             * Getting product details in background thread
             * @return 
             * */
            protected String doInBackground(String... params)
            {
          
            	String jsonStr = service.makeServiceCall(url, ServiceHandler.GET);
            	
            	aname1= aname.getText().toString();
                apass1= apass.getText().toString();
            	 try {
            		 JSONObject jsonObj = new JSONObject(jsonStr);
                     // Checking for SUCCESS TAG
                     int success = jsonObj.getInt("success");
      
                     if (success == 1)
                     {
                         // products found
                         // Getting Array of Products
                         users = jsonObj.getJSONArray("users");
      
                         // looping through All Products
                         for (int i = 0; i < users.length(); i++)
                         {
                             JSONObject c = users.getJSONObject(i);
      
                             
                             String j_aname = c.getString("a_name");
                             String j_apass = c.getString("a_pass");
                             if(j_aname.contentEquals(aname1) && j_apass.contentEquals(apass1))
                             {
                            	flag=1;
                            	break;
                             }
                             
                         }
                        
                        
                         return null;
                     } 
                     
                     
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
				return null;
            	
            
            	
            		
                // updating UI from Background Thread
               }
     
            /**
             * After completing background task Dismiss the progress dialog
             * **/
            protected void onPostExecute(String file_url) {
                // dismiss the dialog once got all details
                pDialog.dismiss();
                if(flag==1)
                {
               	 Intent i = new Intent(getApplicationContext(),
                           MyNewSelector.class);
               	 //Main1Activity
               	 finish();
               	 startActivity(i);
                }
                else
                {
               	 error.setText("Check Your username or password!!!");
                }
            }
        }
     
        /**
         * Background Async Task to  Save product Details
         * */
        
        /*****************************************************************
         * Background Async Task to Delete Product
         * */
        
        
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.alogin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public Boolean checkConnection(Context c) {
		ConnectivityManager m = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo n = m.getActiveNetworkInfo();
		return n != null && n.isConnectedOrConnecting();
	}
    
}
