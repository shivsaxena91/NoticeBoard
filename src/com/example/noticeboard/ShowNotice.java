package com.example.noticeboard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ShowNotice extends Activity implements OnClickListener{

	TextView tv,tvtitle;
	
	Button attach;
	 ProgressBar pb;
	    Dialog dialog;
	    int downloadedSize = 0;
	    int totalSize = 0;
	    TextView cur_val;
	    String dwnload_file_path;
	    String att;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shownotice);
		
		Bundle b = getIntent().getExtras();

		SharedPreferences mydata = getSharedPreferences("ids", 0);
		SharedPreferences.Editor editor = mydata.edit();
		
        attach=(Button)findViewById(R.id.Batt);
        
        
		  String title = b.getString("title");
		  String id = b.getString("id");
		  String date = b.getString("date");
          String eventdate = b.getString("eventdate");
          String dep = b.getString("department");
          String time = b.getString("time");
          String body = b.getString("body");
          att = b.getString("att");
          
          dwnload_file_path = "http://www.shrijay.com/Rushit/uploadedimages/"+att;  
          
          editor.putInt(id,1);
          editor.commit();
         
           
 
          tv = (TextView)findViewById(R.id.tvNoticesss);
          tvtitle = (TextView)findViewById(R.id.tvNoticeTitle);
          tv.setTextColor(Color.BLACK);
          tvtitle.setTextColor(Color.BLACK);
          tvtitle.setPaintFlags(tvtitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
         
          tvtitle.setText(title+"\n");
          time = time.substring(0, time.indexOf("."));
          dep = dep.toUpperCase();
          tv.setText("Time : "+time+"\n\n"+body+"\n\nEventdate : "+eventdate+"\n\n"+dep+" Department"	);
          if(!att.contentEquals("")){
        	  attach.setText(att);
        //	  Toast.makeText(getApplicationContext(),"inside if"+ att.toString(), Toast.LENGTH_LONG).show();
              attach.setOnClickListener(this);
          }
          else
          {
        //	  Toast.makeText(getApplicationContext(),"inside else"+ att.toString(), Toast.LENGTH_LONG).show();
              attach.setVisibility(View.INVISIBLE);  
          }
          
	}
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String pathfile = Environment.getExternalStorageDirectory().toString()+"/"+att;
        File f = new File(pathfile);
        if(f.exists()==false)
        {
        	showProgress(dwnload_file_path);
        new Thread(new Runnable() {
            public void run() {
            	
            	String pathfile1 = Environment.getExternalStorageDirectory().toString()+"/"+att;
                	downloadFile();
                	dialog.dismiss();
                	Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse("file:///"+pathfile1), "image/*");
                    startActivity(intent);
            }
          }).start();
        }
        else
        {
        	Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse("file:///"+pathfile), "image/*");
            startActivity(intent);
        }
        

        
	}
	void downloadFile(){
        
        try {
            URL url = new URL(dwnload_file_path);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
 
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
 
            //connect
            urlConnection.connect();
 
            //set the path where we want to save the file           
            File SDCardRoot = Environment.getExternalStorageDirectory(); 
            //create a new file, to save the downloaded file 
            File file = new File(SDCardRoot,att);
            
            FileOutputStream fileOutput = new FileOutputStream(file);
 
            //Stream used for reading the data from the internet
            InputStream inputStream = urlConnection.getInputStream();
 
            //this is the total size of the file which we are downloading
            totalSize = urlConnection.getContentLength();
 
            runOnUiThread(new Runnable() {
                public void run() {
                    pb.setMax(totalSize);
                }               
            });
             
            //create a buffer...
            byte[] buffer = new byte[1024];
            int bufferLength = 0;
 
            while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                // update the progressbar //
                runOnUiThread(new Runnable() {
                    public void run() {
                        pb.setProgress(downloadedSize);
                        float per = ((float)downloadedSize/totalSize) * 100;
                        cur_val.setText("Downloaded " + downloadedSize + "KB / " + totalSize + "KB (" + (int)per + "%)" );
                    }
                });
            }
            //close the output stream when complete //
            fileOutput.close();
            runOnUiThread(new Runnable() {
                public void run() {
                    // pb.dismiss(); // if you want close it..
                }
            });         
         
        } catch (final MalformedURLException e) {
            showError("Error : MalformedURLException " + e);        
            e.printStackTrace();
        } catch (final IOException e) {
            showError("Error : IOException " + e);          
            e.printStackTrace();
        }
        catch (final Exception e) {
            showError("Error : Please check your internet connection " + e);
        }       
    }
     
    void showError(final String err){
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(ShowNotice.this, err, Toast.LENGTH_LONG).show();
            }
        });
    }
     
    void showProgress(String file_path){
        dialog = new Dialog(ShowNotice.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.myprogressdialog);
        dialog.setTitle("Download Progress");
 
        TextView text = (TextView) dialog.findViewById(R.id.tv1);
        text.setText("Downloading file from ... " + file_path);
        cur_val = (TextView) dialog.findViewById(R.id.cur_pg_tv);
        cur_val.setText("Starting download...");
        dialog.show();
         
        pb = (ProgressBar)dialog.findViewById(R.id.progress_bar);
        pb.setProgress(0);
        pb.setProgressDrawable(getResources().getDrawable(R.drawable.green_progress));  
    }



}
