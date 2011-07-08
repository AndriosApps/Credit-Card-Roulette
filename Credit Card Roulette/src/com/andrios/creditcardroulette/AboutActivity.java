package com.andrios.creditcardroulette;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AboutActivity extends Activity {
	
	   AdView adView;
	   AdRequest request;
	   Button rateBTN;

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
        setConnections();
        setOnClickListeners();
		
      //TODO
        setTracker();
    }
    
  

	private void setConnections(){
    	rateBTN = (Button) findViewById(R.id.aboutActivityRateBTN);
    }
	
	  private void setOnClickListeners() {
			rateBTN.setOnClickListener(new OnClickListener(){

				public void onClick(View v) {
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse("market://details?id=com.andrios.creditcardroulette"));
					startActivity(intent);

				}
				
			});
			
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
