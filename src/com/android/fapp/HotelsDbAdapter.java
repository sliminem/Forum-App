package com.android.fapp;

// This class handles creation and update of the database
// as well as adding and removing items 
// it is purely logic and the user does not see what is happening

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class HotelsDbAdapter {
	//names of the field in the hotels table
	public static final String KEY_ROWID = "_id";
    public static final String KEY_TYPE = "type";
    public static final String KEY_NAME = "name";
    public static final String KEY_DESC = "description";
    public static final String KEY_ADDR = "address";    
    public static final String KEY_MINS = "minutes";
    public static final String KEY_RATE = "rating";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_WEB = "web";

    private static final String TAG = "HotelsDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    /**
     * Database creation sql statement
    */ 
    
    private static final String TABLE_CREATE =
        "create table if not exists hotels (_id integer primary key autoincrement, "
        + "type text not null, name text not null, description text not null, address text not null," +
        " minutes integer not null, rating integer not null, phone text default 'no phone number', web text default 'no address');";
    
    //LIST OF HOTELS, RESTAURANTS AND CAFES
    //to be loaded initially
    private static final String[][] INS_ITEMS = {
    	{"Restaurants","McDonalds","Fast Food Restaurant","3/29 Princes Mall, Edinburgh, Midlothian EH1 1BQ","15","3","0131 556 6597","www.mcdonalds.co.uk"},
    	{"Restaurants","Pink Olive Restaurant","Pre-theatre. Quality food.","55-57 West Nicolson Street, EH8 9DB","5","4","0131 662 4493","www.ilovepinkolive.co.uk"},
    	{"Restaurants","The Buffalo Grill","BYOB. Starters. Veggie.","12-14 Chapel Street, EH8 9AY","5","4","0131 667 7427","www.buffalogrill.co.uk"},
    	{"Restaurants","Red Box Noodle Bar Ltd","Chinese restaurant. Sweet chili.","51 West Nicolson Street, EH8 9DB","5","4","0131 662 0828","www.red-boxnoodlebar.co.uk "},
    	{"Restaurants","Home Bistro","Fish and chips, twist, pudding.","41 West Nicolson Street, EH8 9DB","5","4","0131 667 7010","www.homebistro.co.uk"},
    	{"Restaurants","Khushis Diner","BYOB. Butter chicken. Starters. Curry.","32 West Nicolson Street, EH8 9CH","5","3","0131 667 4871","www.khushisdiner.com"},
    	{"Restaurants","Kebab Mahal","Curry dishes, side dishes, veggie","7 Nicolson Square, EH8 9BH","5","4","0131 667 5214","www.kebab-mahal.co.uk"},
    	{"Restaurants","KFC","Take away food: chicken, fries and more...","36 Nicolson Street, Edinburgh, EH8 9DT","5","3","0131 662 9533","www.kfc.co.uk"},
    	{"Hotels","Lady Nicolson Court","A nice hotel","38 Potterow, EH8 9BT","5","4","0800 783 4213","www.accomodationforstudents.com"},
    	{"Hotels","Nicolson Apartments","No reservation costs. Great rates.","28 West Nicolson Street, EH8 9DD","5","2","0131 477 3680","www.apts-edinburgh.co.uk"},
    	{"Hotels","Serviced Apartments Edinburgh","Call for a quote or search through many Edinburgh serviced apartments.","24 Simpson Loan, EH3 9GE","5","5","020 8630 7200","www.silverdoor.co.uk"},
    	{"Hotels","Ten Hill Place Hotel","No reservation costs. Great rates.","10 Hill Place, EH8 9DS","5","4","0131 622 2080","www.tenhillplace.com"},
    	{"Hotels","Edinburgh Hotel du Vin","Nestled in the Old Town, close to the Castle. Guests can enjoy stylish bedrooms and suites.","11 Bristo Place, EH1 1EZ","5","4","0131 247 4900","www.hotelduvin.com"},
    	{"Hotels","Grassmarket Hotel Edinburgh","Located in the historic Grassmarket area, only two minutes walk from The Royal Mile.","94-96 Grassmarket, EH1 2JR","10","4","0131 220 2299","www.festival-inns.co.uk"},
    	{"Hotels","The Scotsman Hotel Edinburgh","The Scotsman Hotel Edinburgh opened in 2001 in the Victorian building which had housed The Scotsman newspaper for nearly a century.","20 North Bridge, EH1 1TR","15","5","0131 556 5565","www.thescotsmanhotel.co.uk"},
    	{"Hotels","Salisbury Hotel","A luxury boutique hotel in the cosmopolitan Southside and close to the Old Town, Royal Mile, Edinburgh Castle and Edinburgh University","43-45 Salisbury Road, EH16 5AA","15","4","0131 667 1264","www.the-salisbury.co.uk"},
    	{"Cafes","Nile Valley Cafe ","A nice coffee place, just accross the street.","6 Chapel St, Edinburgh, Midlothian EH8 9AY ","5","3","0131 667 8200",""},
    	{"Cafes","Starbucks Coffee","Cafes and Coffee Shops","32 Simpson Loan, West End, Edinburgh EH3 9GG","5","5","0131 656 0455","www.starbucks.co.uk"},
    	{"Cafes","Press Coffee","Cofee Shop","30 Buccleuch Street, Edinburgh, Midlothian EH8 9LP","5","4","0131 667 6205",""},
    	{"Cafes","Kilimanjaro Coffee","Cofee Shop, Hours: 9:00 am-6:00 pm","104 Nicolson Street, Edinburgh EH8 9EJ","5","3","0131 662 0135",""},
    	{"Cafes","Scoopz Ice Cream Cafe","Ice Cream Shop, Cafe, milkshake bar, ...","25-27 West Nicolson St, Edinburgh, City of Edinburgh EH8 9","5","4","0131 667 8789","www.scoopz-uk.com"},
    	{"Shops","TESCO Metro","Supermarket, grocery...","94 Nicolson Street, Edinburgh EH8 9EW","5","5","0845 677 9247","www.tesco.com"},
    	{"Shops","Superdrug Stores PLC","Pharmacy","70 Nicolson St, Edinburgh, Midlothian EH8 9DT","5","5","0131 667 5070","www.superdrug.com"},
    	{"Shops","LIDL","Supermarket","56-58 Nicolson Street, Newington, Edinburgh EH8 9DT","5","4","0870 444 1234","www.lidl.co.uk"},
    	{"Shops","Scayles Music","Musical Instrument Shop, Musical Instrument Rental, Musical Instrument Repair Shop","50 Saint Patrick Square, Edinburgh, Midlothian EH8 9EZ","5","4","0131 667 8241","www.scayles.co.uk"},
    	{"Shops","Tesco Express","Supermarket, Retail, Open: 6:00 am-11:00 pm","59-62 South Bridge, Edinburgh EH1 1LS","10","3","0800 505555","www.tesco.com"},
    	{"Shops","Argos","Supermarket, Electricals, Spoorts Goods, Furniture","11-15 North Bridge, Edinburgh, City of Edinburgh EH1 1SB","15","5","0845 165 7183","www.argos.co.uk"},
    	{"Shops","Oxfam","Charity Shop","120-122 Nicolson Street, Edinburgh, Midlothian EH8 9EJ","5","5","0131 662 4498","www.oxfam.org.uk"},
    	{"Shops","Boots","Pharmacy, Health & Medical, Retail Stores","6A St Patrick Street, Edinburgh, EH8 9HB","5","4","0131 667 1698","www.boots.org"}
    	};
    
    //a string used to insert information into the database
    //it continues with the above two-dimensional array
    private static final String TABLE_INSERT = "insert into hotels(type, name, description, address, minutes, rating, phone, web) values";
	    
    //some data, defining the database, its version and the table
    private static final String DATABASE_NAME = "forum";
    private static final String DATABASE_TABLE = "hotels";
    private static final int DATABASE_VERSION = 1;

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        	
        	Log.w("SQL INFO", TABLE_CREATE);
            db.execSQL(TABLE_CREATE);
                       
            for(int i=0; i<INS_ITEMS.length; i++){
            	Log.w("SQL INSERT","Item: "+(i+1)+" of "+INS_ITEMS.length);
            	String stmt;
            	stmt=TABLE_INSERT+"('"+INS_ITEMS[i][0]+"','"+INS_ITEMS[i][1]+"','"+INS_ITEMS[i][2]+"','"+
            	INS_ITEMS[i][3]+"','"+INS_ITEMS[i][4]+"','"+INS_ITEMS[i][5]+"','"+INS_ITEMS[i][6]+"','"+INS_ITEMS[i][7]+"');";
            	Log.w("SQL Executing: ", stmt);
            	db.execSQL(stmt);
            }
        
        }

        //when a new db version is released this method is called
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
            onCreate(db);
        }
    }
    // Constructor
    public HotelsDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    /**
     * Open the hotels database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     */
    public HotelsDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }


    /**
     * Create a new hotel using the title and body provided. If the hotel is
     * successfully created return the new rowId for that hotel, otherwise return
     * a -1 to indicate failure.
    */
    public long createHotel(String type, String name, String desc, String mins, String addr, float rate, String phone, String web ) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TYPE, type);
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_DESC, desc);
        initialValues.put(KEY_MINS, mins);
        initialValues.put(KEY_ADDR, addr);
        initialValues.put(KEY_RATE, rate);
        initialValues.put(KEY_PHONE, phone);
        initialValues.put(KEY_WEB, web);

        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    /**
     * Delete the hotel with the given rowId
     */
    public boolean deleteHotel(long rowId) {

        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    /**
     * Return a Cursor over the list of all hotels in the database
     */
    public Cursor fetchAllHotels() {

        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TYPE,
                KEY_NAME, KEY_DESC, KEY_ADDR, KEY_MINS, KEY_RATE, KEY_PHONE, KEY_WEB }, null, null, null, null, null, null);
    }
    
    //This method fetches facilities with a WHERE clause and ORDER BY clause
    public Cursor fetchAllHotelsWhere(String type, String order) {
		// TODO Auto-generated method stub
    	String asc_or_desc;
    	if(order.equalsIgnoreCase("minutes")){
    		 asc_or_desc = "ASC";
    	} else {
    		 asc_or_desc = "DESC";
    	}
    	Log.w("FAPP SQL:",type+" | order: "+order+" DESC");
		return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TYPE,
                KEY_NAME, KEY_DESC, KEY_ADDR, KEY_MINS, KEY_RATE, KEY_PHONE, KEY_WEB },type, null, null, null, order+" "+asc_or_desc);
	}

    /**
     * Return a Cursor positioned at the hotel that matches the given rowId
     */
    public Cursor fetchHotel(long rowId) throws SQLException {

        Cursor mCursor =

            mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TYPE,
                    KEY_NAME, KEY_DESC, KEY_ADDR, KEY_MINS, KEY_RATE, KEY_PHONE, KEY_WEB}, KEY_ROWID + "=" + rowId, null,
                    null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }
    // update an existing row in the DB
    public boolean updateHotel(long rowId, String type, String name, String desc, String mins, String addr, float rate, String phone, String web ) {
        ContentValues args = new ContentValues();
        args.put(KEY_TYPE, type);
        args.put(KEY_NAME, name);
        args.put(KEY_DESC, desc);
        args.put(KEY_MINS, mins);
        args.put(KEY_ADDR, addr);
        args.put(KEY_RATE, rate);
        args.put(KEY_PHONE, phone);
        args.put(KEY_WEB, web);

        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
}
