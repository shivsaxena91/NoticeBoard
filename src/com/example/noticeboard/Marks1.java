package com.example.noticeboard;

import java.io.IOException;
import java.io.InputStreamReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class Marks1 {

	
	String name;
	int itt = 0;
	SharedPreferences mydata;

	String url,id;
	Boolean isNameSingle = false,isNameDouble = false;
	public void storeMarks(Context context,Document doc,String url)
	{

		Toast.makeText(context, "inside here", Toast.LENGTH_LONG).show();
		SharedPreferences getData = PreferenceManager
				.getDefaultSharedPreferences(context);
		String s = getData.getString("sem", "6");
		String y = getData.getString("year", "2012");
		String namme = getData.getString("name", "");
		mydata = context.getSharedPreferences(url, 0);
		SharedPreferences.Editor editor = mydata.edit();
		
							  
					  for (Element table : doc.select("table")) {
							for (Element bodyrow : table.select("tbody")) {
								for (Element row : bodyrow.select("tr")) {
									for (Element col : row.select("td")) {
										
										if (itt == 1)
											editor.putString("subject1", col.text());
										else if (itt == 23)
											editor.putString("subject2", col.text());
										else if (itt == 45)
											editor.putString("subject3", col.text());
										else if (itt == 67)
											editor.putString("subject4", col.text());
										else if (itt == 89)
											editor.putString("subject5", col.text());
										else if (itt == 111)
											editor.putString("subject6", col.text());
										
										else if(itt == 3)
											editor.putString("sess1_sub1", col.text());
										else if(itt == 25)
											editor.putString("sess1_sub2", col.text());
										else if(itt == 47)
											editor.putString("sess1_sub3", col.text());
										else if(itt == 69)
											editor.putString("sess1_sub4", col.text());
										else if(itt == 91)
											editor.putString("sess1_sub5", col.text());
										else if(itt == 113)
											editor.putString("sess1_sub6", col.text());
										else if(itt == 9)
											editor.putString("sess2_sub1", col.text());
										else if(itt == 31)
											editor.putString("sess2_sub2", col.text());
										else if(itt == 53)
											editor.putString("sess2_sub3", col.text());
										else if(itt == 75)
											editor.putString("sess2_sub4", col.text());
										else if(itt == 97)
											editor.putString("sess2_sub5", col.text());
										else if(itt == 119)
											editor.putString("sess2_sub6", col.text());
										else if(itt == 15)
											editor.putString("sess3_sub1", col.text());
										else if(itt == 37)
											editor.putString("sess3_sub2", col.text());
										else if(itt == 59)
											editor.putString("sess3_sub3", col.text());
										else if(itt == 81)
											editor.putString("sess3_sub4", col.text());
										else if(itt == 103)
											editor.putString("sess3_sub5", col.text());
										else if(itt == 125)
											editor.putString("sess3_sub6", col.text());
										itt++;
									}
								}
							}
						}
					  Toast.makeText(context, "updated", Toast.LENGTH_LONG).show();
					  editor.putString("fetched", "yes");
					  editor.commit();
					  
		}
		
			
public void updateMarks(Context c,String id,String key,String marks)
{
	SharedPreferences up = c.getSharedPreferences(id, 0);
	SharedPreferences.Editor ed = up.edit();
	
	ed.putString(key, marks);
	ed.commit();
}
			
		}
	
	
	

