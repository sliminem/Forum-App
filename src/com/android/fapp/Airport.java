// This class guides the user from the airport to the Forum

package com.android.fapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Airport extends Activity {
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.airport);
        setTitle("Informatics Forum App - Airport");
        
        //Two buttons that represent the two modes of transport available from the Airport
        
        Button taxi = (Button)findViewById(R.id.button2);
        Button bus = (Button)findViewById(R.id.button1);
        
        taxi.setOnClickListener( new Button.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(v.getContext(), AirportTaxi.class);
				startActivity(myIntent);
				
			}
        	
        });
        
        bus.setOnClickListener( new Button.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(v.getContext(), AirportBus.class);
				startActivity(myIntent);
				
			}
        	
        });
        

    }
	
}
