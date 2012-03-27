// Initail activity
// displays 4 buttons (Directions, Facilities, Weather and Map)

package com.android.fapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class FappActivity extends Activity {
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setTitle("Informatics Forum App - Home");
        
        Log.w("onCREATE: ","Loading button facility");
        Button facility = (Button)findViewById(R.id.but_facility);
        Log.w("onCREATE: ","Loading button info");
        Button map = (Button)findViewById(R.id.but_map);
        Log.w("onCREATE: ","Loading button weatehr");
        Button weather = (Button)findViewById(R.id.but_weather);
        Log.w("onCREATE: ","Loading button directions");
        Button directions = (Button)findViewById(R.id.but_directions);        
        
        directions.setOnClickListener( new Button.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(v.getContext(), DirectionsForum.class);
				startActivity(myIntent);
			}
        	
        });
        
        facility.setOnClickListener( new Button.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(v.getContext(), Facility.class);
				startActivity(myIntent);
			}
        	
        });
        
        map.setOnClickListener( new Button.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(v.getContext(), ForumMap.class);
				startActivity(myIntent);
			}
        	
        });
        
        weather.setOnClickListener( new Button.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(v.getContext(), EdiWeather.class);
				startActivity(myIntent);
			}
        	
        });

    }
}