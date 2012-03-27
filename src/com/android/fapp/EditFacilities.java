// This activity edits the hotels, restaurants and cafes
// I have made use of some of the android developers' tutorials:
// http://developer.android.com/resources/browser.html?tag=tutorial

package com.android.fapp;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;

public class EditFacilities extends Activity {

    private EditText mAddrText;
    private EditText mNameText;
    private EditText mDescText;
    private EditText mWebText;
    private EditText mPhoneText;
    private Spinner spinner;
    private Spinner spinner2;
    private String minutes;
    private String typeHotel;
    private RatingBar mRateText;

    
    private Long mRowId;
    private HotelsDbAdapter mDbHelper;
    
    ArrayAdapter<String> myAdap;
    ArrayAdapter<String> myAdap2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //open a writable database
        mDbHelper = new HotelsDbAdapter(this);
        mDbHelper.open();

        setContentView(R.layout.edit_facilities);
        setTitle("Facilities near the Forum - Edit");
        
        mNameText = (EditText) findViewById(R.id.name);
        mDescText = (EditText) findViewById(R.id.desc);
        mAddrText = (EditText) findViewById(R.id.edit_addr);
        mWebText = (EditText) findViewById(R.id.edit_web);
        mPhoneText = (EditText) findViewById(R.id.edit_phone);
        mRateText = (RatingBar) findViewById(R.id.edit_rating);  
        
        //Initialize two spinners for the type of facility and for the walking distance from the forum
        
        spinner2 = (Spinner) findViewById(R.id.spinner2);
	    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
	            this, R.array.type_array, android.R.layout.simple_spinner_item);
	    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner2.setAdapter(adapter2);
        
        spinner = (Spinner) findViewById(R.id.spinner1);
	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	            this, R.array.spinner_array, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner.setAdapter(adapter);	    
	    
	    Button confirmButton = (Button) findViewById(R.id.confirm);
        
        mRowId = (savedInstanceState == null) ? null :
            (Long) savedInstanceState.getSerializable(HotelsDbAdapter.KEY_ROWID);
		if (mRowId == null) {
			Bundle extras = getIntent().getExtras();
			mRowId = extras != null ? extras.getLong(HotelsDbAdapter.KEY_ROWID)
									: null;
		}
		if(mRowId != null){
			Cursor hotel = mDbHelper.fetchHotel(mRowId);;
		    String str1 =  hotel.getString(hotel.getColumnIndexOrThrow(HotelsDbAdapter.KEY_TYPE));
		    String str2 =  hotel.getString(hotel.getColumnIndexOrThrow(HotelsDbAdapter.KEY_MINS));
		    
		    // SPINNER 1 SETTING THE INITIAL VALUE
		    // Generates a warning because the type "String" is not checked before run
		    myAdap = (ArrayAdapter<String>) spinner.getAdapter(); 
	
		    int spinnerPosition = myAdap.getPosition(str2);
	
		    spinner.setSelection(spinnerPosition);
		    
		    // SPINNER 2 SETTING THE INITIAL VALUE
		    // Generates a warning because the type "String" is not checked before run
		    Log.w("spinner 2 test", "str1 = "+str1);
	
		    myAdap2 = (ArrayAdapter<String>) spinner2.getAdapter(); //cast to an ArrayAdapter
	
		    int spinnerPosition2 = myAdap2.getPosition(str1);
			
		    spinner2.setSelection(spinnerPosition2);
		}
		
		// Call this method to get data from the database
		populateFields();

		// Create a facility entry when user is entering information
        confirmButton.setOnClickListener(new View.OnClickListener() {

        	public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
                
            }

        });
           
        // Get what the spinner is currently showing
        spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				minutes = spinner.getSelectedItem().toString();
		    	// TODO Auto-generated method stub
				
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        
        // Get the current selection of the spinner
        spinner2.setOnItemSelectedListener(new OnItemSelectedListener(){

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				typeHotel = spinner2.getSelectedItem().toString();
				// TODO Auto-generated method stub
				
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        
    }
    
    // A methond for filling the data in the fields
    // from the database
    private void populateFields() {
    	
        if (mRowId != null) {
            Cursor hotel = mDbHelper.fetchHotel(mRowId);
            startManagingCursor(hotel);
            
            mNameText.setText(hotel.getString(
                    hotel.getColumnIndexOrThrow(HotelsDbAdapter.KEY_NAME)));
            mDescText.setText(hotel.getString(
                    hotel.getColumnIndexOrThrow(HotelsDbAdapter.KEY_DESC)));
            mAddrText.setText(hotel.getString(
                    hotel.getColumnIndexOrThrow(HotelsDbAdapter.KEY_ADDR)));
            mWebText.setText(hotel.getString(
                    hotel.getColumnIndexOrThrow(HotelsDbAdapter.KEY_WEB)));
            mPhoneText.setText(hotel.getString(
                    hotel.getColumnIndexOrThrow(HotelsDbAdapter.KEY_PHONE)));
            mRateText.setRating(hotel.getFloat(
            		hotel.getColumnIndexOrThrow(HotelsDbAdapter.KEY_RATE)));
          
            
           
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState();
        outState.putSerializable(HotelsDbAdapter.KEY_ROWID, mRowId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateFields();
    }

    // save the state when for example the user presses the back button
    private void saveState() {
    	
    	String web = mWebText.getText().toString();
    	String addr = mPhoneText.getText().toString();
    	String phone = mAddrText.getText().toString();
        String name = mNameText.getText().toString();
        String desc = mDescText.getText().toString();
        float rate = mRateText.getRating();
        String type = typeHotel;
        String mins = minutes;
        
        if (mRowId == null) {
            long id = mDbHelper.createHotel(type, name, desc, mins, addr, rate, phone, web);
            if (id > 0) {
                mRowId = id;
            }
        } else {
            mDbHelper.updateHotel(mRowId, type, name, desc, mins, addr, rate, phone, web);
        }
    }
    //close the database when the activity is destroyed
    protected void onDestroy() {
		super.onDestroy();
		if (mDbHelper != null) {
			mDbHelper.close();
		}
	}
}
