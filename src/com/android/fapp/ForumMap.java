// A class for viewing Google Maps.
// The map is centered at the forum and there is a flag that marks it.
// There is a MapActivity error produced in runtime which is cause by a bug in the emulator.

// ! uses an API KEY FOR DEBUGGING

package com.android.fapp;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

public class ForumMap extends MapActivity {
	
	// IF longitude and latitude
	float lat = 55.944619f;
    float lng = -3.187467f;
	
    // initailize a map controller and define the position of the Forum
	MapController mc;
	GeoPoint forumPoint = new GeoPoint((int)(lat*1E6),(int)(lng*1E6));
	GeoPoint p;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.if_map);
        setTitle("Informatics Forum App - Map");       
                
        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        mc = mapView.getController();
                
        //getting the marker and positioning it on the right place
        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.map_flag1);
        ForumItemizedOverlay itemizedoverlay = new ForumItemizedOverlay(this, drawable);
       
        //Invoking the onTap() method from ForumItemizedOverlay()
        //to display some info about the Forum
        OverlayItem overlayitem = new OverlayItem(forumPoint, "EH8 9AB", "Informatics Forum!");
        
        itemizedoverlay.addOverlay(overlayitem);
        mapOverlays.add(itemizedoverlay);
                
        mc.animateTo(forumPoint);
        
    }
    
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}