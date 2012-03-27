//this class is responsible for getting the user 
//from Waverly station to the forum
//The user gets to choose his/her desired way mode of transport

package com.android.fapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Waverly extends Activity {
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waverly);
        setTitle("Informatics Forum App - Waverly Station");

        //define three buttins representing the possible modes of transport
        Button waverly_taxi = (Button)findViewById(R.id.button2);
        Button walk = (Button)findViewById(R.id.button3);
        Button bus = (Button)findViewById(R.id.button1);

        
        waverly_taxi.setOnClickListener( new Button.OnClickListener() {

    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			Intent myIntent = new Intent(v.getContext(), AirportTaxi.class);
    			startActivity(myIntent);
    			
    		}
        	
        });
        
        bus.setOnClickListener( new Button.OnClickListener() {

    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			Intent myIntent = new Intent(v.getContext(), WaverlyBus.class);
    			startActivity(myIntent);
    			
    		}
        	
        });
        
        walk.setOnClickListener( new Button.OnClickListener() {

    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			Intent myIntent = new Intent(v.getContext(), Walk.class);
    			startActivity(myIntent);
    			
    		}
        	
        });
    }
   


}
