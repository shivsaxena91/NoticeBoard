package com.example.noticeboard;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class ShowNewResults extends Activity{

	String url,ele;
	WebView wv;
	String mime = "text/html";
	String encoding = "utf-8";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.shownewresults);
		
		wv = (WebView)findViewById(R.id.showexternalresult);
		Bundle b = getIntent().getExtras();
		ele = b.getString("elem");
		wv.getSettings().setJavaScriptEnabled(true);
		wv.getSettings().setLoadWithOverviewMode(true);
		wv.getSettings().setBuiltInZoomControls(true);
		wv.getSettings().setUseWideViewPort(true);
		wv.loadData(ele, mime, encoding);
		
	
	}

}
