package com.example.noticeboard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.noticeboard.ALogin.GetUserDetails;

import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ULogin extends ActionBarActivity {

	Button login;
	TextView error;
	EditText aname,apass;
	String aname1;	
	String apass1;
	private static String url = "http://www.shrijay.com/Rushit/userlogindata.php";
	private ProgressDialog pDialog;
	JSONArray users = null;
	int flag=0;
	SharedPreferences mydata;
	
	 String j_dept,j_sem;
	 
	 
    // JSON parser class
    ServiceHandler service=new ServiceHandler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ulogin);
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
	                pDialog = new ProgressDialog(ULogin.this);
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
	      
	                             
	                             String j_aname = c.getString("u_name");
	                             String j_apass = c.getString("u_pass");
	                             j_dept=c.getString("u_department");
	                             j_sem=c.getString("n_sem");
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
	                	mydata = getSharedPreferences("login", 0);
	            		SharedPreferences.Editor editor = mydata.edit();
	            		editor.putString("department", j_dept);
	            		editor.putString("sem", j_sem);
	               	 Intent i = new Intent(getApplicationContext(),UMain.class);
	               	 i.putExtra("department", j_dept);
	               	i.putExtra("sem", j_sem);
	               	 Log.d("d:",j_dept);

	               	 finish();
	               	 
	               	 startActivity(i);
	                }
	                else
	                {
	               	 error.setText("Check Your username or password!!!");
	                }
	            }
	}


	        public Boolean checkConnection(Context c) {
	    		ConnectivityManager m = (ConnectivityManager) c
	    				.getSystemService(Context.CONNECTIVITY_SERVICE);
	    		NetworkInfo n = m.getActiveNetworkInfo();
	    		return n != null && n.isConnectedOrConnecting();
	    	}
	        
}
