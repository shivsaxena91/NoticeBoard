package com.example.noticeboard;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;


public class MyMarksChecker extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if(checkConnection(context))
		{
			Intent i = new Intent(context,InternalChecker.class);
			context.startService(i);
		}
		else
		{
			Intent i = new Intent(context,InternalChecker.class);
			context.stopService(i);
			
		}
		
		
	}


	public Boolean checkConnection(Context c) {
		ConnectivityManager m = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo n = m.getActiveNetworkInfo();
		return n != null && n.isConnectedOrConnecting();
	}
}
