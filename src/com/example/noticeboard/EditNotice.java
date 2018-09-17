  package com.example.noticeboard;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditNotice extends ActionBarActivity {

	    EditText txtTitle;
	    EditText txtEvent;
	    EditText txtDesc;
	    EditText txtSem;
	    Button btnSave;
	    Button btnDelete;
	    SharedPreferences mydata;
	    String nid;
	 
	    // Progress Dialog
	    private ProgressDialog pDialog;
	    String url;
	 
	    // JSON parser class
	    ServiceHandler sh=new ServiceHandler();
	    JsonParser jsonParser = new JsonParser();
	   String url_notice_details = "http://www.shrijay.com/Rushit/singlenotice.php";
	    
	    // url to update product
	    String url_update_notice = "http://www.sanuda.com/Rushit/update.php";
	 
	    // url to delete product
	    String url_delete_notice = "http://www.sanuda.com/Rushit/delete.php";
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_notice);
		 // save button
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
        btnSave = (Button) findViewById(R.id.bUpdate);
        btnDelete = (Button) findViewById(R.id.bDelete);
 
        // getting product details from intent
        Intent i = getIntent();
 
        // getting product id (pid) from intent
        nid = i.getStringExtra("n_id");
        new GetProductDetails().execute();
        
        // save button click event
        btnSave.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View arg0) {
                // starting background task to update product
                new SaveProductDetails().execute();
            }
        });
 
        // Delete button click event
        btnDelete.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View arg0) {
                // deleting product in background thread
                new DeleteProduct().execute();
            }
        });
 
        
        
        
        
	}

	class GetProductDetails extends AsyncTask<String, String, String> {
		 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditNotice.this);
            pDialog.setMessage("Loading Notice details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
 
        /**
         * Getting product details in background thread
         * */
        protected String doInBackground(String... params) {
 
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    // Check for success tag
                    int success;
                    try {
                        // Building Parameters
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("nid",nid));
 
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
                            JSONArray noticeObj = json.getJSONArray("notice"); // JSON Array
 
                            // get first product object from JSON Array
                            JSONObject notice = noticeObj.getJSONObject(0);
 
                            // product with this pid found
                            // Edit Text
                            txtTitle = (EditText) findViewById(R.id.etTitle);
                            txtEvent = (EditText) findViewById(R.id.etDate);
                            txtDesc = (EditText) findViewById(R.id.etBody);
                            txtSem = (EditText) findViewById(R.id.etSem);
 
                            // display product data in EditText
                            txtTitle.setText(notice.getString("n_title"));
                            txtEvent.setText(notice.getString("n_eventdate"));
                            txtDesc.setText(notice.getString("n_body"));
                            txtSem.setText(notice.getString("n_sem"));

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
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once got all details
            pDialog.dismiss();
        }
    }
 
    /**
     * Background Async Task to  Save product Details
     * */
    class SaveProductDetails extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditNotice.this);
            pDialog.setMessage("Saving Notice...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
 
        /**
         * Saving product
         * */
        protected String doInBackground(String... args) {
 
            // getting updated data from EditTexts
            String title = txtTitle.getText().toString();
            String event = txtEvent.getText().toString();
            String description = txtDesc.getText().toString();
            String sem = txtSem.getText().toString();
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("n_id", nid));
            params.add(new BasicNameValuePair("n_title", title));
            params.add(new BasicNameValuePair("n_eventdate", event));
      //      params.add(new BasicNameValuePair(TAG_PRICE, price));
            params.add(new BasicNameValuePair("n_body", description));
            params.add(new BasicNameValuePair("n_sem", sem));
            
            // sending modified data through http request
            // Notice that update product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_update_notice,
                    "POST", params);
 
            // check json success tag
            try {
                int success = json.getInt("success");
 
                if (success == 1) {
                    // successfully updated
                    Intent i = getIntent();
                    // send result code 100 to notify about product update
                    setResult(100, i);
                    finish();
                } else {
                    // failed to update product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            
            mydata = getSharedPreferences("ids", 0);
            SharedPreferences.Editor editor = mydata.edit();
            Intent i = getIntent();
            editor.remove(i.getStringExtra("n_id"));
            editor.commit();
            
            
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product uupdated
            pDialog.dismiss();
        }
    }
 
    /*****************************************************************
     * Background Async Task to Delete Product
     * */
    class DeleteProduct extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditNotice.this);
            pDialog.setMessage("Deleting Notice...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
 
        /**
         * Deleting product
         * */
        protected String doInBackground(String... args) {
 
            // Check for success tag
            int success;
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("n_id",nid));
 
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(
                        url_delete_notice, "GET", params);
 
                // check your log for json response
                Log.d("Delete Product", json.toString());
                Log.d("nid",nid);
                // json success tag
                success = json.getInt("success");
                if (success == 1) {
                    // product successfully deleted
                    // notify previous activity by sending code 100
                    Intent i = getIntent();
                    // send result code 100 to notify about product deletion
                    setResult(100, i);
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
 
        }
 
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_notice, menu);
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
}
