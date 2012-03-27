// This class displays a list of facilities

package com.android.fapp;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class ShowFacilities extends ListActivity {
	
    private HotelsDbAdapter mDbHelper;
    private String selection = "";
    private String orderby = "";
    
    private static final int ACTIVITY_CREATE=0;
    private static final int ACTIVITY_EDIT=1;

    //define menu id's for inserting, creating and editing facilities
    private static final int INSERT_ID = Menu.FIRST;
    private static final int DELETE_ID = Menu.FIRST + 1;
    private static final int EDIT_ID = Menu.FIRST + 2;


	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_facilities);
        setTitle("Facilities List");
        registerForContextMenu(getListView());

        mDbHelper = new HotelsDbAdapter(this);
        mDbHelper.open();
        
        Bundle b = getIntent().getExtras();
        selection = b.getString("selection");
        // SQLite formatting is necessary
        orderby = b.getString("order by").toLowerCase();
        if(orderby.equalsIgnoreCase("distance")){
        	orderby = "minutes";
        }        
        
        Log.w("FAPP", selection+" | order by: "+orderby);
        
        fillData();
        
        //initialize a toast to inform the user of how to edit, delete and insert new items
        Context context = getApplicationContext();
        
        CharSequence text = "Press Menu if you want to add items\n\nLong press on item to delete or edit.";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        
    }
      
    // a method to extract all the data needed to display the list of facilities
    // namely: Name, rating and distance
    private void fillData() {
    	
        Cursor hotelsCursor = mDbHelper.fetchAllHotelsWhere(selection, orderby);
        startManagingCursor(hotelsCursor);

        // Create an array to specify the fields we want to display in the list
        String[] from = new String[]{HotelsDbAdapter.KEY_NAME, HotelsDbAdapter.KEY_RATE, HotelsDbAdapter.KEY_MINS};
      
        // and an array of the fields we want to bind those fields to
        int[] to = new int[]{R.id.hotelName, R.id.hotelRating, R.id.num_mins};
        
        // Now create a simple cursor adapter and set it to display
        SimpleCursorAdapter hotels = new SimpleCursorAdapter(this, R.layout.one_row, hotelsCursor, from, to);
        setListAdapter(hotels);
    }
    //when a user clicks on an item more info about the item is displayed by the new activity
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(this, ShowSelected.class);
        i.putExtra(HotelsDbAdapter.KEY_ROWID, id);
        startActivityForResult(i, ACTIVITY_EDIT);
    }
    //when Menu is pressed the user is allowed to insert new items
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	Log.w("MENU", "Options Menu 1");

        super.onCreateOptionsMenu(menu);
        menu.add(0, INSERT_ID, 0, R.string.menu_insert);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
            case INSERT_ID:
                createHotel();
                return true;
        }

        return super.onMenuItemSelected(featureId, item);
    }

    // A context menu for editing or deleting a selected item
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0, DELETE_ID, 0, R.string.menu_delete);
        menu.add(0, EDIT_ID, 0, R.string.menu_edit);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	Log.w("MENU", "ContextMenu 3");

        switch(item.getItemId()) {
            case DELETE_ID:
                AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
                mDbHelper.deleteHotel(info.id);
                fillData();
                return true;
            case EDIT_ID:
                AdapterContextMenuInfo info2 = (AdapterContextMenuInfo) item.getMenuInfo();
                updateHotel(info2.id);
                return true;
        }
        return super.onContextItemSelected(item);
    }
    
    private void updateHotel(long id) {
		// TODO Auto-generated method stub
    	Intent i = new Intent(this, EditFacilities.class);
        i.putExtra(HotelsDbAdapter.KEY_ROWID, id);
        startActivityForResult(i, ACTIVITY_EDIT);		
	}

	// when a hotel is selected a new intent is started
    private void createHotel() {
        Intent i = new Intent(this, EditFacilities.class);
        startActivityForResult(i, ACTIVITY_CREATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        fillData();
    }
    
    // close the database on destroy
    protected void onDestroy() {
		super.onDestroy();
		if (mDbHelper != null) {
			mDbHelper.close();
		}
	}

}
