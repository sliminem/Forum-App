package com.android.fapp;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

// A simple class for displaying the route from
// Waverly Station to the Forum.
// Simply shows a zoomable image in WebView
public class Walk extends Activity {
	
	WebView wv;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.walk);
        setTitle("About 0.7 miles or 14 minutes walk");
        
        wv = (WebView) findViewById(R.id.webview);
        
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl("file:///android_asset/route.png");  
        
    }
}
