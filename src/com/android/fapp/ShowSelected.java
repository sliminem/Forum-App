package com.android.fapp;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

// show some info about the selected item
public class ShowSelected extends Activity {
	
    private HotelsDbAdapter mDbHelper;
    private Long mRowId;
    private TextView hotelName;
    private TextView hotelDesc;
    private TextView mins;
    private TextView hotelPhone;
    private TextView hotelWeb;
    private TextView hotelAddress;
    private RatingBar hotelRate;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Facilities near the Forum");
        setContentView(R.layout.show_specific);
        
        //open the database for extracting the relevant information
        mDbHelper = new HotelsDbAdapter(this);
        mDbHelper.open();
        
        hotelName = (TextView)findViewById(R.id.hotel_name);
        mins = (TextView)findViewById(R.id.hotel_mins);
        hotelDesc = (TextView)findViewById(R.id.hotel_desc);
        hotelAddress = (TextView)findViewById(R.id.hotel_address);
        hotelPhone = (TextView)findViewById(R.id.hotel_phone);
        hotelWeb = (TextView)findViewById(R.id.hotel_web);

        hotelRate = (RatingBar)findViewById(R.id.hotel_rating);
        hotelRate.setEnabled(false);

        
        mRowId = (savedInstanceState == null) ? null :
            (Long) savedInstanceState.getSerializable(HotelsDbAdapter.KEY_ROWID);
        if (mRowId == null) {
			Bundle extras = getIntent().getExtras();
			mRowId = extras != null ? extras.getLong(HotelsDbAdapter.KEY_ROWID)
									: null;
		}
        
        populateFields();


    }

    // populate the already defined textviews, and rating bar
	private void populateFields() {
		// TODO Auto-generated method stub
		if(mRowId != null){
			Cursor hotel = mDbHelper.fetchHotel(mRowId);
            startManagingCursor(hotel);
            
            // get all the relevant data from the database with the given ID
            hotelName.setText(hotel.getString(hotel.getColumnIndexOrThrow(HotelsDbAdapter.KEY_NAME)));
            mins.setText(hotel.getString(hotel.getColumnIndexOrThrow(HotelsDbAdapter.KEY_MINS)));
            hotelDesc.setText(hotel.getString(hotel.getColumnIndexOrThrow(HotelsDbAdapter.KEY_DESC)));
            hotelAddress.setText(hotel.getString(hotel.getColumnIndexOrThrow(HotelsDbAdapter.KEY_ADDR)));
            hotelRate.setRating(hotel.getFloat(hotel.getColumnIndexOrThrow(HotelsDbAdapter.KEY_RATE)));
            hotelPhone.setText(hotel.getString(hotel.getColumnIndexOrThrow(HotelsDbAdapter.KEY_PHONE)));
            hotelWeb.setText(hotel.getString(hotel.getColumnIndexOrThrow(HotelsDbAdapter.KEY_WEB)));
		}
		
	}
	//close the database on destroy
	protected void onDestroy() {
		super.onDestroy();
		if (mDbHelper != null) {
			mDbHelper.close();
		}
	}
}




















