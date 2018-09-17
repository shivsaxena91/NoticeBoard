package com.example.noticeboard;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.RequestParams;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Add extends Activity implements OnClickListener{

	 @Override
		public void onBackPressed() {
			// TODO Auto-generated method stub
			super.onBackPressed();
			Intent i =new Intent(this,MyNewSelector.class);
			startActivity(i);
		}
	
	String url = "http://www.shrijay.com/Rushit/add.php";
	ProgressDialog d = null;
	EditText ettitle,etdate,etbody;
	String title,date,body;
	Button add,upload;
	JsonParser jsonParser=new JsonParser();
	ServiceHandler sh=new ServiceHandler();
	String encodedString;
    RequestParams params = new RequestParams();
    String imgPath, fileName;
    Bitmap bitmap;
    private static int RESULT_LOAD_IMG = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add);
		
		add = (Button)findViewById(R.id.bAdd);
		
		ettitle = (EditText)findViewById(R.id.etTitle);
		etdate = (EditText)findViewById(R.id.etDate);
		etbody = (EditText)findViewById(R.id.etBody);
		
		d = new ProgressDialog(this);
		
		add.setOnClickListener(this);
		
		
	}
	public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }
 
    // When Image is selected from Gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data
 
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
 
                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();
 
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgPath = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) findViewById(R.id.imgView);
                // Set the Image in ImageView
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgPath));
                // Get the Image's file name
                String fileNameSegments[] = imgPath.split("/");
                fileName = fileNameSegments[fileNameSegments.length - 1];
                // Put file name in Async Http Post Param which will used in Php web app
                
 
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
 
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.bAdd:
			 if (imgPath != null && !imgPath.isEmpty()) {
		            d.setMessage("Converting Image to Binary Data");
		            d.show();
		            // Convert image to String using Base64
		            new Read().execute();
		        // When Image is not selected from Gallery
		        } else {
		            Toast.makeText(
		                    getApplicationContext(),
		                    "You must select image from gallery before you try to upload",
		                    Toast.LENGTH_LONG).show();
		        }
			
			break;
		}
	}

	public class Read extends AsyncTask<String, String, String>
	{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			 BitmapFactory.Options options = null;
             options = new BitmapFactory.Options();
             options.inSampleSize = 3;
             bitmap = BitmapFactory.decodeFile(imgPath,
                     options);
             ByteArrayOutputStream stream = new ByteArrayOutputStream();
             // Must compress the Image to reduce image size to make upload easy
             bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
             byte[] byte_arr = stream.toByteArray();
             // Encode Image to String
             encodedString = Base64.encodeToString(byte_arr, 0);
             
			title = ettitle.getText().toString();
			date = etdate.getText().toString();
			body = etbody.getText().toString();
			
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            params1.add(new BasicNameValuePair("title", title));
            params1.add(new BasicNameValuePair("image", encodedString));
            params1.add(new BasicNameValuePair("filename", fileName));
            params1.add(new BasicNameValuePair("event", date));
            params1.add(new BasicNameValuePair("body", body));
			params1.add(new BasicNameValuePair("department", getIntent().getExtras().getString("department")));
           // params1.add(new BasicNameValuePair("attachment", " "));
            params1.add(new BasicNameValuePair("sem", getIntent().getExtras().getString("sem")));
			
           //String json =sh.makeServiceCall(url,1, params1);
           //JSONObject json1 = null;
		JSONObject json =jsonParser.makeHttpRequest(url, "POST", params1);  
           Intent i1=getIntent();
           Bundle b=i1.getExtras();
			String sem=b.getString("sem");
			String department=b.getString("department");
			
            Log.d("Create Response", json.toString());
            
            // check for success tag
            try {
            	 int success = json.getInt("success");
            	    //String message = jsonData.getString("message");
 
                if (success==1) {
                    // successfully created product
                   
                	Intent i = new Intent(getApplicationContext(), MyNewSelector.class);
        			i.putExtras(b);
        			
        			startActivity(i);
         
                    // closing this screen
                    finish();
                } else {
                	Toast.makeText(Add.this, "Error", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
			
			// closing this screen
		
 
            
            
			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
	            d.setMessage("Creating Notice..");
	            d.setIndeterminate(false);
	            d.setCancelable(true);
	            d.show();
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			d.dismiss();
		}
		
	}
	
}
