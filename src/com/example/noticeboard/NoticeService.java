package com.example.noticeboard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

public class NoticeService extends Service {

	private ProgressDialog pDialog;
	String department, sem;
	JSONObject json1;

	// Creating JSON Parser object
	JsonParser jParser = new JsonParser();

	ArrayList<HashMap<String, String>> noticeList;
	JSONArray notice = null;
	SharedPreferences myData;
	String url = "http://www.shrijay.com/Rushit/selectnotice.php";

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		myFunction();
		return START_STICKY;
	}



	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void notify(Context context,String title,String text,Bundle b)
	{
		Intent i= new Intent(context, ShowNotice.class);
		i.putExtras(b);
		int iUniqueId = (int) (System.currentTimeMillis() & 0xfffffff);
		 PendingIntent contentIntent = PendingIntent.getActivity(context,  iUniqueId,
		           i,  0);
		 

		    NotificationCompat.Builder mBuilder =
		            new NotificationCompat.Builder(context)
		            .setSmallIcon(R.drawable.icon)
		            .setContentTitle(title)
		            .setContentText(text);
		    mBuilder.setContentIntent(contentIntent);
		    mBuilder.setDefaults(Notification.DEFAULT_SOUND);
		    mBuilder.setAutoCancel(true);
		    NotificationManager mNotificationManager =
		        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		    Random ran=new Random();
		    mNotificationManager.notify(ran.nextInt(1000000), mBuilder.build());
	}	

	public void myFunction()
	{
	
		
		
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub

				SharedPreferences mydata = PreferenceManager
						.getDefaultSharedPreferences(getApplicationContext());
				
				String sem = mydata.getString("sem", "1");
				String department = mydata.getString("department", "CE");
				if(!sem.contentEquals("") && !department.contentEquals(""))
				{
				myData = getApplicationContext().getSharedPreferences("ids", 0);

				
				ServiceHandler sh = new ServiceHandler();
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("department", department));
				params.add(new BasicNameValuePair("sem", sem));
				String json = sh.makeServiceCall(url, 1, params);

				try {
					json1 = new JSONObject(json);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			

				try {
					// Checking for SUCCESS TAG
					int success = json1.getInt("success");

					if (success == 1) {
						// notices found
						// Getting Array of Notices
						notice = json1.getJSONArray("notices");

						
						// looping through All Notices
						for (int i = 0; i < notice.length(); i++) {
							JSONObject c = notice.getJSONObject(i);

							// Storing each json item in variable
							String id = c.getString("n_id");
							
							String title = c.getString("n_title");
							String date = c.getString("n_date");
							String eventdate = c.getString("n_eventdate");
							String dep = c.getString("n_department");
							String time = c.getString("n_time");
							String body = c.getString("n_body");
							String sem1 = c.getString("n_sem");
							String att = c.getString("n_attachment");

							Bundle b = new Bundle();

							b.putString("id", id);
							b.putString("title", title);
							b.putString("date", date);
							b.putString("eventdate", eventdate);
							b.putString("department", dep);
							b.putString("time", time);
							b.putString("body", body);
							b.putString("att", att);

							if (!(myData.contains(id))) {
								if(someFunction(date))
									NoticeService.this.notify(getApplicationContext(), "You have new notice from sem " + sem1,
											title, b);
	
								
								
							}
						
							
						}
					}
				} // }
				catch (JSONException e) {
					e.printStackTrace();
				}
				}
				
			}
		});
		t.start();
	}
	public boolean someFunction(String da)
	{
		
		
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String startdate = df.format(c.getTime());
		c.add(Calendar.DAY_OF_MONTH, -7);
		String endate = df.format(c.getTime());
		
		
		Date sdate = null,edate = null,date = null;
		try {
			sdate = df.parse(startdate);
			edate = df.parse(endate);
			date = df.parse(da);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(sdate.after(date) && edate.before(date))
		{
			return true;
		}
		else
			return false;
		
		
	}
	
}