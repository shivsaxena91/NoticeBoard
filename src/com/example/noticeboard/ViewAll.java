package com.example.noticeboard;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class ViewAll extends ListActivity implements OnItemSelectedListener, OnQueryTextListener {

	 @Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent i =new Intent(this,MyNewSelector.class);
		startActivity(i);
	}

	private ProgressDialog pDialog;
	 String department,sem;
	
	 JSONObject json;
	 String date,eventdate;
	    // Creating JSON Parser object
	    JsonParser jParser = new JsonParser();
	    String[] so = {"Date","Eventdate"},titles,ids;
		ArrayAdapter<String> mysorter = null;
		SearchView searchview;
	    ArrayList<HashMap<String, String>> newnoticelist;
	    public List<Date> datelist = new ArrayList<Date>();
	    public List<Date> eventdatelist = new ArrayList<Date>();
	    Boolean adapterset = false;
	    Spinner sorter;
	    ArrayList<HashMap<String, String>> noticeList;
	    JSONArray notice = null;
		  String url="http://www.shrijay.com/Rushit/allnotices.php";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_all);
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		
		sorter = (Spinner)findViewById(R.id.myspinnerview);
		sorter.setOnItemSelectedListener(this);
		mysorter = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, so);
		sorter.setAdapter(mysorter);
		
		
		Bundle b = getIntent().getExtras();
		 department = b.getString("department");
		 sem = b.getString("sem");
		
		  noticeList = new ArrayList<HashMap<String, String>>();
		  
	        // Loading products in Background Thread
	        new LoadAllProducts().execute();
	        ListView lv = getListView();
	        
	        
	        // on selecting single product
	        // launching Edit Product Screen
	        lv.setOnItemClickListener(new OnItemClickListener() {
	 
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view,
	                    int position, long id) {
	                // getting values from selected ListItem
	                TextView nid1 = ((TextView) view.findViewById(R.id.nid));
	                String nid=nid1.getText().toString();
	                // Starting new intent
	                Intent in = new Intent(getApplicationContext(),
	                       EditNotice.class);
	               // Bundle b=new Bundle();
	                //b.putString("department", department);
	                in.putExtra("department",department);
	                in.putExtra("sem",sem);
	               // b.putString("n_id", nid);
	                // sending pid to next activity
	                
	                //in.putExtras(b);
	                in.putExtra("n_id", nid);
	 
	                // starting new activity and expecting some response back
	                startActivityForResult(in, 100);
	            }
	        });
		  
		
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 100) {
            // if result code 100 is received
            // means user edited/deleted product
            // reload this screen again
            Intent intent = getIntent();
            finish();
            startActivity(intent);
	}
	}

	class LoadAllProducts extends AsyncTask<String, String, String> {
		 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ViewAll.this);
            pDialog.setMessage("Loading Notices. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("department",department));
            params.add(new BasicNameValuePair("sem",sem));
            // getting JSON string from URL
           
			json = jParser.makeHttpRequest(url, "GET", params);
			
 
            // Check your log cat for JSON reponse
            Log.d("All Notices: ", json.toString());
 
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt("success");
 
                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    notice = json.getJSONArray("notices");
 
                    // looping through All Products
                    for (int i = 0; i < notice.length(); i++) {
                        JSONObject c = notice.getJSONObject(i);
 
                        // Storing each json item in variable
                        String id = c.getString("n_id");
                        String title = c.getString("n_title");
                        String sem1=c.getString("n_sem");
                        String time = c.getString("n_time");
                        String date = c.getString("n_date");
                        String eventdate = c.getString("n_eventdate");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                        
                    	
                    	if(!date.contentEquals(""))
                    	{
                    		Date mydate = sdf.parse(date+" "+time);
                    		datelist.add(mydate);
                    	}
                    	if(!eventdate.contentEquals(""))
                    	{
                    		Date myeventdate = sdf.parse(eventdate+" "+time);
                    		eventdatelist.add(myeventdate);
                    	}
                        
                        
                        
                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();
 
                        // adding each child node to HashMap key => value
                        map.put("n_id", id);
                        map.put("n_title", title);
                        map.put("n_sem", sem1);
                        map.put("n_eventdate", c.getString("n_eventdate"));
 
                        
                        
                        
                        // adding HashList to ArrayList
                        noticeList.add(map);
                    }
                } else {
                    // no products found
                    // Launch Add New product Activity
                    Intent i = new Intent(getApplicationContext(),
                            Add.class);
                    Bundle b = new Bundle();
       			 b.putString("department", department);
       			b.putString("sem", sem);
       			 i.putExtras(b);
                    // Closing all previous activities
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                           ViewAll.this, noticeList,
                            R.layout.list_item, new String[] { "n_id","n_eventdate","n_title"},
                            new int[] { R.id.nid,R.id.ndate, R.id.title });
                    // updating listview
                    setListAdapter(adapter);
                    adapterset = true;
                }
            });
 
        }
 
	}
	
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		if(adapterset)
		{
		
		// TODO Auto-generated method stub
		String s = sorter.getSelectedItem().toString();
		if(s.contentEquals("Date"))
		{
			
		try {
			Collections.sort(datelist,Collections.reverseOrder());
			JSONArray notice = json.getJSONArray("notices");
			int counter = datelist.size();
			int len = notice.length();
			int i = 0,j;
			newnoticelist = new ArrayList<HashMap<String, String>>();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			while(i<counter)
			{
				j=0;
				while(true)
				{
					JSONObject obj = notice.getJSONObject(j);
					Date d = sdf1.parse(obj.getString("n_date")+" "+obj.getString("n_time"));
					if(d.toString().contentEquals(datelist.get(i).toString()))
					{
						HashMap<String, String> map2 = new HashMap<String, String>();
						
						map2.put("n_id", obj.getString("n_id"));
						map2.put("n_title", obj.getString("n_title"));
						map2.put("n_eventdate", obj.getString("n_date"));
						newnoticelist.add(map2);
						break;
					}
					j++;
				}
				i++;
			}
			ListAdapter adapter1 = new SimpleAdapter(
			        ViewAll.this, newnoticelist,
			         R.layout.list_item, new String[] { "n_id", "n_eventdate",
			                "n_title"},
			         new int[] { R.id.nid,R.id.ndate, R.id.title });
			setListAdapter(adapter1);
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ParseException e) {
			// TODO A-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	

		else if(s.contentEquals("Eventdate"))
		{
			
			try {
				Collections.sort(eventdatelist,Collections.reverseOrder());
				JSONArray notice = json.getJSONArray("notices");
				int counter = eventdatelist.size();
				int len = notice.length();
				int i = 0,j=0;
				newnoticelist = new ArrayList<HashMap<String, String>>();
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				while(i<counter)
				{
					j=0;
					while(true)
					{
						JSONObject obj = notice.getJSONObject(j);
						Date d = sdf1.parse(obj.getString("n_eventdate")+" "+obj.getString("n_time"));
						if(d.toString().contentEquals(eventdatelist.get(i).toString()))
						{
							HashMap<String, String> map2 = new HashMap<String, String>();
							map2.put("n_id", obj.getString("n_id"));
							map2.put("n_title", obj.getString("n_title"));
							map2.put("n_eventdate", obj.getString("n_eventdate"));
							newnoticelist.add(map2);
							break;
						}
						j++;
					}
					i++;
				}
				ListAdapter adapter1 = new SimpleAdapter(
				        ViewAll.this, newnoticelist,
				         R.layout.list_item, new String[] { "n_id","n_eventdate",
				                "n_title"},
				         new int[] { R.id.nid, R.id.ndate, R.id.title });
				setListAdapter(adapter1);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (ParseException e) {
				// TODO A-generated catch block
				e.printStackTrace();
			}
		}		
		}
	}


	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		
		inflater.inflate(R.menu.searchmenu, menu);
		
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		searchview = (SearchView) menu.findItem(R.id.search_action)
				.getActionView();
		searchview.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));
		searchview.setQueryHint("Search Notices..");
		searchview.setOnQueryTextListener(this);
		return super.onCreateOptionsMenu(menu);
	}


	@Override
	public boolean onQueryTextChange(String arg0) {
		// TODO Auto-generated method stub
		if(searchview.getQuery().toString().contentEquals(""))
		{
			newnoticelist = new ArrayList<HashMap<String, String>>();
			newnoticelist.clear();
			newnoticelist = noticeList;

			ListAdapter adapter1 = new SimpleAdapter(
                    ViewAll.this, newnoticelist,
                     R.layout.list_item, new String[] { "n_id","n_eventdate",
                            "n_title"},
                     new int[] { R.id.nid, R.id.ndate, R.id.title });
			setListAdapter(adapter1);
			return false;
		}
		else
		{
			try {
				JSONArray notice = json.getJSONArray("notices");
				int i;
				newnoticelist = new ArrayList<HashMap<String, String>>();
				for(i=0;i<notice.length();i++)
				{
					
					JSONObject c = notice.getJSONObject(i);
					
					if(c.getString("n_title").contains(searchview.getQuery().toString()))
					{
						HashMap<String, String> map2 = new HashMap<String, String>();
						map2.put("n_id", c.getString("n_id"));
						map2.put("n_title", c.getString("n_title"));
						map2.put("n_eventdate", c.getString("n_eventdate"));
						newnoticelist.add(map2);
					}
				}
				ListAdapter adapter1 = new SimpleAdapter(
				        ViewAll.this, newnoticelist,
				         R.layout.list_item, new String[] { "n_id","n_eventdate",
				                "n_title"},
				         new int[] { R.id.nid, R.id.ndate , R.id.title });
				setListAdapter(adapter1);
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		}
		return false;
	}


	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
