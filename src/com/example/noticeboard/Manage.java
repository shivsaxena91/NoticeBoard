package com.example.noticeboard;

import com.example.noticeboard.Add.Read;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Manage extends ActionBarActivity implements OnClickListener {

	Button badd,bmanage;
	String department,sem;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage);
		badd=(Button)findViewById(R.id.add);
		bmanage=(Button)findViewById(R.id.manage);
		badd.setOnClickListener(this);
		bmanage.setOnClickListener(this);
		Bundle b = getIntent().getExtras();
		 department = b.getString("department");
		 sem = b.getString("sem");
	}
	
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.add:
			Intent i = new Intent(getApplicationContext(), Add.class);
			 Bundle b = new Bundle();
			 b.putString("department", department);
			 b.putString("sem", sem);
			 i.putExtras(b);
			 startActivity(i);
			break;	
			
		
		case R.id.manage:
			Intent i1 = new Intent(getApplicationContext(), ViewAll.class);
			 Bundle b1 = new Bundle();
			 b1.putString("department", department);
			 b1.putString("sem", sem);
			 Log.d("depart",department);
			 i1.putExtras(b1);
			 startActivity(i1);
			break;
			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manage, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
