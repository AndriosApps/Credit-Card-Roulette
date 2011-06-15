package com.andrios.creditcardroulette;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;

import android.app.Activity;
import android.os.Bundle;

public class AboutActivity extends Activity {
	
	   AdView adView;
	   AdRequest request;

		GoogleAnalyticsTracker tracker;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutactivity);
        
    	adView = (AdView)this.findViewById(R.id.aboutAdView);
	      
	    request = new AdRequest();
		request.setTesting(false);
		adView.loadAd(request);
        
      //TODO
        setTracker();
    }
	private void setTracker() {
		tracker = GoogleAnalyticsTracker.getInstance();

	    // Start the tracker in manual dispatch mode...
	    tracker.start("UA-23366060-4", this);
	    
		
	}
	
	public void onResume(){
		super.onResume();
		tracker.trackPageView("About Activity");
	}
	
	public void onPause(){
		super.onPause();
		tracker.dispatch();
	}
	
	
	
}
