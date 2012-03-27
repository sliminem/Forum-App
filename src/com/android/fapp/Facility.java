//this class is for selecting the type and the sorting order 
//of the facilities that the user wants to view

package com.android.fapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class Facility extends Activity {
	
    private HotelsDbAdapter mDbHelper;
    //this array handles the selection of facilities
    private String[] selection = {"'1'","'1'","'1'","'1'"};
    private String select;
    private String orderby;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facilities);
        setTitle("Facilities near the Forum");
        mDbHelper = new HotelsDbAdapter(this);
        mDbHelper.open();
        
        //Define checkboxes for the types(Hotel, Restaurant....)
        final CheckBox check_hotels = (CheckBox) findViewById(R.id.chk_hotels);
        final CheckBox check_restaurants = (CheckBox) findViewById(R.id.chk_restaurants);
        final CheckBox check_shops = (CheckBox) findViewById(R.id.chk_shops);
        final CheckBox check_cafes = (CheckBox) findViewById(R.id.chk_cafes);
              
        Button list_facilities = (Button)findViewById(R.id.but_show_facilities);
        
        list_facilities.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent_list = new Intent(Facility.this, ShowFacilities.class); 
	            Bundle b = new Bundle(); 
	            
	            //create the string for the SQL WHERE clause
	            select = "type="+selection[0]+"OR type="+selection[1]+"OR type="+selection[2]+"OR type="+selection[3];
	            
	            //pass data to the new intent
	            b.putString("selection", select);
	            b.putString("order by", orderby);
	            intent_list.putExtras(b);
	            startActivity(intent_list);
				
			}
        	
        });
        //Setting the sort_by spinner                
        final Spinner spinner_sort = (Spinner) findViewById(R.id.spinner_sort);
        
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.sort_by, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_sort.setAdapter(adapter);
        
        spinner_sort.setOnItemSelectedListener(new OnItemSelectedListener(){

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				orderby = spinner_sort.getSelectedItem().toString();
		    	// TODO Auto-generated method stub
				
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        //SETTING ALL THE CHECKBOXES
        check_hotels.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(check_hotels.isChecked()){
					selection[0] = "'Hotels'";
				} else {
					selection[0] = "'1'";
				}
			}
        	
        });
        check_restaurants.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(check_restaurants.isChecked()){
					selection[1] = "'Restaurants'";
				} else {
					selection[1] = "'1'";
				}
			}
        	
        });
        check_shops.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(check_shops.isChecked()){
					selection[2] = "'Shops'";
				} else {
					selection[2] = "'1'";
				}
			}
        	
        });
        check_cafes.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(check_cafes.isChecked()){
					selection[3] = "'Cafes'";
				} else {
					selection[3] = "'1'";
				}
			}
        	
        });
        
    }
    //close the database on destroy
    protected void onDestroy() {
		super.onDestroy();
		if (mDbHelper != null) {
			mDbHelper.close();
		}
	}
    

}
