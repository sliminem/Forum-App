// This class asks the user where he/she is located.

package com.android.fapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DirectionsForum extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directions);
        setTitle("Informatics Forum App - Directions");

        Button airport = (Button)findViewById(R.id.airport_button);
        Button waverly = (Button)findViewById(R.id.waverly_button);
        
        airport.setOnClickListener( new Button.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(v.getContext(), Airport.class);
				startActivity(myIntent);
				
			}
        	
        });
        
        waverly.setOnClickListener( new Button.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(v.getContext(), Waverly.class);
				startActivity(myIntent);
				
			}
        	
        });

    }
}