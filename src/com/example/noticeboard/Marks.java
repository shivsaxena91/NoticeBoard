package com.example.noticeboard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Marks extends Activity implements OnItemSelectedListener, OnClickListener{

	
	Bundle bc;
	Spinner spinsem1,spinyear1,spintype1;
	Button go1;
	EditText etid,etbdate;
	String[] sem = {"1","2","3","4","5","6","7","8"};
	String[] year = {"2010","2011","2012","2013","2014"};
	String[] type = {"Internal","External"};
	String strid,strbdate,strsem1,strtype1,stryear1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.marks);
	
		spintype1 = (Spinner)findViewById(R.id.spinType1);
		spinsem1 = (Spinner)findViewById(R.id.spinSem1);
		spinyear1 = (Spinner)findViewById(R.id.spinYear1);
		go1 = (Button)findViewById(R.id.bGo1);
		etid = (EditText)findViewById(R.id.etId);
		etbdate = (EditText)findViewById(R.id.etBdate);
		
		ArrayAdapter<String> adapterSem  = new ArrayAdapter<String>(Marks.this,android.R.layout.simple_spinner_item,sem);
		ArrayAdapter<String> adapterYear  = new ArrayAdapter<String>(Marks.this,android.R.layout.simple_spinner_item,year);
		ArrayAdapter<String> adapterType  = new ArrayAdapter<String>(Marks.this,android.R.layout.simple_spinner_item,type);
		
		spintype1.setAdapter(adapterType);
		spinyear1.setAdapter(adapterYear);
		spinsem1.setAdapter(adapterSem);

		spintype1.setSelection(0);
		spintype1.setOnItemSelectedListener(this);
		spinyear1.setOnItemSelectedListener(this);
		spinsem1.setOnItemSelectedListener(this);
		

		go1.setOnClickListener(this);
		
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	    strsem1 = spinsem1.getSelectedItem().toString();
		stryear1 = spinyear1.getSelectedItem().toString();
		strtype1 = spintype1.getSelectedItem().toString();
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if(!checkConnection())
		{
			showToast("Please Check your Internet Connection");
		}
		else if(etid.getText().toString().contentEquals("") || etbdate.getText().toString().contentEquals(""))
		{
			showToast("Please enter ur sufficient details!");
		}
		
		else
		{
		strid = etid.getText().toString();
		strbdate = etbdate.getText().toString();
		
		bc = new Bundle();
		bc.putString("id", strid);
		bc.putString("button", "go1");
		bc.putString("bdate", strbdate);
		bc.putString("semester1", strsem1);
		bc.putString("type1", strtype1);
		bc.putString("year1", stryear1);
		bc.putBoolean("naam", false);
		InputMethodManager imm1 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm1.hideSoftInputFromWindow(etid.getWindowToken(), 0);
		Intent ii = new Intent(Marks.this,ShowResults.class);
		ii.putExtras(bc);
		
		startActivity(ii);
		}
		
	}


	public void showToast(String string)
	{
		Toast.makeText(Marks.this, string, Toast.LENGTH_LONG).show();
	}
	

	public Boolean checkConnection()
	{
		ConnectivityManager m = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo n = m.getActiveNetworkInfo();
		return n!=null && n.isConnectedOrConnecting();
	}
}
