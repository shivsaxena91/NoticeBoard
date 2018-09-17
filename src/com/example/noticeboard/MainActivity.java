package com.example.noticeboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends Activity implements OnClickListener {

	Button staffLogin,studentLogin,marks;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		staffLogin = (Button) findViewById(R.id.bStaffLogin);
		studentLogin = (Button)findViewById(R.id.bStudentLogin);

		marks = (Button)findViewById(R.id.bMarks);
		
		staffLogin.setOnClickListener(this);
		studentLogin.setOnClickListener(this);

		marks.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch(v.getId())
		{
		case R.id.bStudentLogin:
			Intent i=new Intent(getApplicationContext(),ULogin.class);
			startActivity(i);
			break;
		case R.id.bStaffLogin:
			Intent i1=new Intent(getApplicationContext(),ALogin.class);
			startActivity(i1);
			break;
		
		case R.id.bMarks:
			Intent intent = new Intent(MainActivity.this,Marks.class);
			startActivity(intent);
			break;
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
	MenuInflater in=getMenuInflater();
	in.inflate(R.menu.mymenu, menu);
		return super.onCreateOptionsMenu(menu);
	
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.prefs:
			Intent i=new Intent(MainActivity.this, Prefs.class);
			startActivity(i);
			
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	
}
