package com.example.noticeboard;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class ShowResults extends Activity{

	WebView wv;
	ProgressDialog d;
	String mime = "text/html";
	String encoding = "utf-8";
	String id, bdate, name, sem, year, type, returnedID, returnedBdate, html,url,name1,id1,pass1;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showresults);
		
		wv = (WebView) findViewById(R.id.webView1);
		d=new ProgressDialog(ShowResults.this);
		d.setMessage("Your result is loading..");
		d.setProgress(0);
		d.setTitle("Have some patience!");
		d.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		
		
		id = getIntent().getStringExtra("id");
		bdate = getIntent().getStringExtra("bdate"); 
		sem = getIntent().getStringExtra("semester1");
		type = getIntent().getStringExtra("type1"); 
		year = getIntent().getStringExtra("year1");
				  
				  
				  
				  wv.getSettings().setJavaScriptEnabled(true); // enables java script
				  wv.getSettings().setLoadWithOverviewMode(true); // full screen mode
			
				  wv.getSettings().setBuiltInZoomControls(true);
				  wv.getSettings().setUseWideViewPort(true);
				 // wv.setWebViewClient(new ourViewClient());
				  wv.setWebViewClient(new SimpleSimple());
				  bdate = bdate.replace("/", "%2F");
				  
				  url =
				  "http://www.darpandodiya.com/egov/result.php?id="+id+"&pass="+bdate
				  +"&session="+sem+"&year="+year+"&type="; StringBuilder sd1 = new
				  StringBuilder(url);
				  
				  if (type.contentEquals("Internal")) { sd1.append("intRelational"); }
				  else { sd1.append("extRelational"); } url=sd1.toString();
				 
				 
				  d.show();
				
				  new Read().execute();
		
		
	}
	
	
	public class Read extends AsyncTask<Void, Void, Void> {

		Boolean load = true;
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			Document doc = null;

			try {
				doc = Jsoup.connect(url).timeout(0).get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Elements ele = doc.select("div#content");
			if(ele.toString().contentEquals(""))
				load=false;
			html = ele.toString();
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			if(load)
			wv.loadData(html, mime, encoding);
			else
			{
				if(getIntent().getBooleanExtra("naam",true))
				showToast("Sorry no results found for "+name1);
				else
					showToast("sorry no results found for student with id "+id+" and password "+bdate);
				d.dismiss();
				finish();
			}
				
			super.onPostExecute(result);
		}

	}
	
	public void showToast(String string)
	{
		Toast.makeText(ShowResults.this, string, Toast.LENGTH_LONG).show();
	}

	public class SimpleSimple extends WebViewClient
	{
		
		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			super.onPageFinished(view, url);
			if(wv.getProgress()==100)
				d.dismiss();
		}
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// TODO Auto-generated method stub
			view.loadUrl(url);
			return true;
		}
		
	}
	
	
}
