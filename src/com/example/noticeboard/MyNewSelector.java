package com.example.noticeboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MyNewSelector extends Activity implements OnItemSelectedListener, OnClickListener{

	@Override
	public void onBackPressed() {
		
	}
	String[] sem = {"1","2","3","4","5","6","7","8"};
	String[] dep = {"CE","EC","MECH","CHEM","IT","CIVIL","IC"};
	String[] am = {"ADD","MANAGE"};
	Spinner spindep,spinsem,spinam;
	Button go,logout;
	String semester,depp,addm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.mynewselector);
		spindep = (Spinner)findViewById(R.id.spindep);
		spinsem = (Spinner)findViewById(R.id.spinsems);
		spinam = (Spinner)findViewById(R.id.spinaddmanage);
		go = (Button)findViewById(R.id.bletssssgo);
		logout = (Button)findViewById(R.id.bLogoutnew);
		go.setOnClickListener(this);
		logout.setOnClickListener(this);
		
		ArrayAdapter<String> asem = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,sem);
		ArrayAdapter<String> adep = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,dep);
		ArrayAdapter<String> aam = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,am);

		spindep.setAdapter(adep);
		spinsem.setAdapter(asem);
		spinam.setAdapter(aam);
		
		spindep.setOnItemSelectedListener(this);
		spinsem.setOnItemSelectedListener(this);
		spinam.setOnItemSelectedListener(this);
		
		super.onCreate(savedInstanceState);
	}
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		semester = spinsem.getSelectedItem().toString();
		depp = spindep.getSelectedItem().toString();
		addm = spinam.getSelectedItem().toString();
	}
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch(v.getId())
		{
		case R.id.bletssssgo:
			if(addm.contentEquals("ADD"))
			{
				finish();
				Intent i = new Intent(this,Add.class);
				Bundle b = new Bundle();
				 b.putString("department", depp);
				 b.putString("sem", semester);
				 i.putExtras(b);
				 startActivity(i);
			}
			else
			{
				finish();
				Intent i = new Intent(this,ViewAll.class);
				Bundle b = new Bundle();
				 b.putString("department", depp);
				 b.putString("sem", semester);
				 i.putExtras(b);
				 startActivity(i);
				
			}

			break;
		case R.id.bLogoutnew:
			Intent i = new Intent(MyNewSelector.this,ALogin.class);
			finish();
			startActivity(i);
			break;
		}
		
		
		
		
			}

}
