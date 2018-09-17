package com.example.noticeboard;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.HeterogeneousExpandableList;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Receiver1 extends BroadcastReceiver{
	 @Override
	 public void onReceive(Context context,Intent intent) {

		 if(checkConnection(context))
		 {
			 Intent i = new Intent(context,NoticeService.class);
			 context.startService(i);
         }
		 else
		 {
			 Intent i = new Intent(context,NoticeService.class);
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
