package com.example.noticeboard;

import java.io.IOException;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

public class InternalChecker extends Service{

	Random rand;
	String url,id,name;
	Document doc;
	Boolean isNameSingle = false;
	Boolean isNameDouble = false;
	int itt = 0,i,j;
	SharedPreferences mydata;
	String subject1;
	String subject2;
	String subject3;
	String subject4;
	String subject5;
	String subject6;
	String sess1_sub1;
	String sess1_sub2;
	String sess1_sub3;
	String sess1_sub4;
	String sess1_sub5;
	String sess1_sub6;
	String sess2_sub1;
	String sess2_sub2;
	String sess2_sub3;
	String sess2_sub4;
	String sess2_sub5;
	String sess2_sub6;
	String sess3_sub1;
	String sess3_sub2;
	String sess3_sub3;
	String sess3_sub4;
	String sess3_sub5;
	String sess3_sub6;


	public Boolean checkConnection(Context c) {
		ConnectivityManager m = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo n = m.getActiveNetworkInfo();
		return n != null && n.isConnectedOrConnecting();
	}
	public void notify(Context context,String title,String text,Document doc)
	{
		Intent i = new Intent(context,ShowNewResults.class);
		Elements elem = doc.select("div#content");
		i.putExtra("elem", elem.toString());
		 PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
		            i, PendingIntent.FLAG_UPDATE_CURRENT);

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
		    mNotificationManager.notify(rand.nextInt(2147483647), mBuilder.build());
	}
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
	66
		myNewFunction();
		
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	public void myNewFunction()
	{
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
	

				rand = new Random();
				SharedPreferences getData = PreferenceManager
						.getDefaultSharedPreferences(getApplicationContext());
				String s = getData.getString("sem", "");
				String y = getData.getString("year", "");
				String id = getData.getString("id", "");
				String pass = getData.getString("pass", "");
				
				pass = pass.replace("/", "%2F");
				
				url = "http://www.darpandodiya.com/egov/result.php?id="+id+"&pass="+pass+"&session="+s+"&year="+y+"&type=intRelational";
				
				if (checkConnection(getApplicationContext())) {
					
						if(!id.contentEquals("") && !pass.contentEquals("") && !s.contentEquals("") && !y.contentEquals(""))
						{
							mydata = getApplicationContext().getSharedPreferences(id, 0);
							
							try {
							doc = Jsoup.connect(url).timeout(0).get();
							if(mydata.getString("fetched", "no").contentEquals("no"))
							{
								Marks1 m = new Marks1();
								m.storeMarks(getApplicationContext(),doc,id);
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						for (Element table : doc.select("table")) {
							for (Element bodyrow : table.select("tbody")) {
								for (Element row : bodyrow.select("tr")) {
									for (Element col : row.select("td")) {
										
										if (itt == 1)
											subject1 = col.text();
										else if (itt == 23)
											subject2 = col.text();
										else if (itt == 45)
											subject3 = col.text();
										else if (itt == 67)
											subject4 = col.text();
										else if (itt == 89)
											subject5 = col.text();
										else if (itt == 111)
											subject6 = col.text();
										
										else if(itt == 3)
											sess1_sub1 = col.text();
										else if(itt == 25)
											sess1_sub2 = col.text();
										else if(itt == 47)
											sess1_sub3 = col.text();
										else if(itt == 69)
											sess1_sub4 = col.text();
										else if(itt == 91)
											sess1_sub5 = col.text();
										else if(itt == 113)
											sess1_sub6 = col.text();
										else if(itt == 9)
											sess2_sub1 = col.text();
										else if(itt == 31)
											sess2_sub2 = col.text();
										else if(itt == 53)
											sess2_sub3 = col.text();
										else if(itt == 75)
											sess2_sub4 = col.text();
										else if(itt == 97)
											sess2_sub5 = col.text();
										else if(itt == 119)
											sess2_sub6 = col.text();
										else if(itt == 15)
											sess3_sub1 = col.text();
										else if(itt == 37)
											sess3_sub2 = col.text();
										else if(itt == 59)
											sess3_sub3 = col.text();
										else if(itt == 81)
											sess3_sub4 = col.text();
										else if(itt == 103)
											sess3_sub5 = col.text();
										else if(itt == 125)
											sess3_sub6 = col.text();
										itt++;
									}
								}
							}
						}
				
						Marks1 m = new Marks1();
						//1st sessional
						if(!mydata.getString("sess1_sub1", "").contentEquals(sess1_sub1))
						{
							InternalChecker.this.notify(getApplicationContext(),subject1+" marks updated","Your Score is :"+sess1_sub1,doc);
							m.updateMarks(getApplicationContext(),id,"sess1_sub1",sess1_sub1);
						}
						if(!mydata.getString("sess1_sub2", "").contentEquals(sess1_sub2))
						{
							InternalChecker.this.notify(getApplicationContext(),subject2+" marks updated","Your Score is :"+sess1_sub2,doc);
							m.updateMarks(getApplicationContext(),id,"sess1_sub2",sess1_sub2);
						}
						if(!mydata.getString("sess1_sub3", "").contentEquals(sess1_sub3))
						{
							InternalChecker.this.notify(getApplicationContext(),subject3+" marks updated","Your Score is :"+sess1_sub3,doc);
							m.updateMarks(getApplicationContext(),id,"sess1_sub3",sess1_sub3);
						}
						if(!mydata.getString("sess1_sub4", "").contentEquals(sess1_sub4))
						{
							InternalChecker.this.notify(getApplicationContext(),subject4+" marks updated","Your Score is :"+sess1_sub4,doc);
							m.updateMarks(getApplicationContext(),id,"sess1_sub4",sess1_sub4);
						}
						if(!mydata.getString("sess1_sub5", "").contentEquals(sess1_sub5))
						{
							InternalChecker.this.notify(getApplicationContext(),subject5+" marks updated","Your Score is :"+sess1_sub5,doc);
							m.updateMarks(getApplicationContext(),id,"sess1_sub5",sess1_sub5);
						}
						if(!mydata.getString("sess1_sub6", "").contentEquals(sess1_sub6))
						{
							InternalChecker.this.notify(getApplicationContext(),subject6+" marks updated","Your Score is :"+sess1_sub6,doc);
							m.updateMarks(getApplicationContext(),id,"sess1_sub6",sess1_sub6);
						}
						
						//2nd sessional
						if(!mydata.getString("sess2_sub1", "").contentEquals(sess2_sub1))
						{
							InternalChecker.this.notify(getApplicationContext(),subject1+" marks updated","Your Score is :"+sess2_sub1,doc);
							m.updateMarks(getApplicationContext(),id,"sess2_sub1",sess2_sub1);
						}
						if(!mydata.getString("sess2_sub2", "").contentEquals(sess2_sub2))
						{
							InternalChecker.this.notify(getApplicationContext(),subject2+" marks updated","Your Score is :"+sess2_sub2,doc);
							m.updateMarks(getApplicationContext(),id,"sess2_sub2",sess2_sub2);
						}
						if(!mydata.getString("sess2_sub3", "").contentEquals(sess2_sub3))
						{
							InternalChecker.this.notify(getApplicationContext(),subject3+" marks updated","Your Score is :"+sess2_sub3,doc);
							m.updateMarks(getApplicationContext(),id,"sess2_sub3",sess2_sub3);
						}
						if(!mydata.getString("sess2_sub4", "").contentEquals(sess2_sub4))
						{
							InternalChecker.this.notify(getApplicationContext(),subject4+" marks updated","Your Score is :"+sess2_sub4,doc);
							m.updateMarks(getApplicationContext(),id,"sess2_sub4",sess2_sub4);
						}
						if(!mydata.getString("sess2_sub5", "").contentEquals(sess2_sub5))
						{
							InternalChecker.this.notify(getApplicationContext(),subject5+" marks updated","Your Score is :"+sess2_sub5,doc);
							m.updateMarks(getApplicationContext(),id,"sess2_sub5",sess2_sub5);
						}
						if(!mydata.getString("sess2_sub6", "").contentEquals(sess2_sub6))
						{
							InternalChecker.this.notify(getApplicationContext(),subject6+" marks updated","Your Score is :"+sess2_sub6,doc);
							m.updateMarks(getApplicationContext(),id,"sess2_sub6",sess2_sub6);
						}
						
						//3rd sessional
						if(!mydata.getString("sess3_sub1", "").contentEquals(sess3_sub1))
						{
							InternalChecker.this.notify(getApplicationContext(),subject1+" marks updated","Your Score is :"+sess3_sub1,doc);
							m.updateMarks(getApplicationContext(),id,"sess3_sub1",sess3_sub1);
						}
						if(!mydata.getString("sess3_sub2", "").contentEquals(sess3_sub2))
						{
							InternalChecker.this.notify(getApplicationContext(),subject2+" marks updated","Your Score is :"+sess3_sub2,doc);
							m.updateMarks(getApplicationContext(),id,"sess3_sub2",sess3_sub2);
						}
						if(!mydata.getString("sess3_sub3", "").contentEquals(sess3_sub3))
						{
							InternalChecker.this.notify(getApplicationContext(),subject3+" marks updated","Your Score is :"+sess3_sub3,doc);
							m.updateMarks(getApplicationContext(),id,"sess3_sub3",sess3_sub3);
						}
						if(!mydata.getString("sess3_sub4", "").contentEquals(sess3_sub4))
						{
							InternalChecker.this.notify(getApplicationContext(),subject4+" marks updated","Your Score is :"+sess3_sub4,doc);
							m.updateMarks(getApplicationContext(),id,"sess3_sub4",sess3_sub4);
						}
						if(!mydata.getString("sess3_sub5", "").contentEquals(sess3_sub5))
						{
							InternalChecker.this.notify(getApplicationContext(),subject5+" marks updated","Your Score is :"+sess3_sub5,doc);
							m.updateMarks(getApplicationContext(),id,"sess3_sub5",sess3_sub5);
						}
						if(!mydata.getString("sess3_sub6", "").contentEquals(sess3_sub6))
						{
							InternalChecker.this.notify(getApplicationContext(),subject6+" marks updated","Your Score is :"+sess3_sub6,doc);
							m.updateMarks(getApplicationContext(),id,"sess3_sub6",sess3_sub6);
						}		
						}
					}
				
			}
		});
		t.start();
	}
	
	
}