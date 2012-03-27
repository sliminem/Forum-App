// When the user is at the airport and chooses to travel by bus

package com.android.fapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AirportBus extends Activity{
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.airport_bus);
        
        TextView waverly = (TextView)findViewById(R.id.waverly_link);
        
        waverly.setOnClickListener( new Button.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(v.getContext(), Waverly.class);
				startActivity(myIntent);
		        setTitle("Informatics Forum App - Bus");

			}
        	
        });
        
        
    }

}
