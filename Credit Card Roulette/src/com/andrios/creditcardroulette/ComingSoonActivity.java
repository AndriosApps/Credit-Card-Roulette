package com.andrios.creditcardroulette;

import android.app.Activity;
import android.os.Bundle;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;

public class ComingSoonActivity extends Activity{

	AdView adView;
	AdRequest request;
	GoogleAnalyticsTracker tracker;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comingsoonactivity);
        
		adView = (AdView)this.findViewById(R.id.comingSoonAdView);
	      
	    request = new AdRequest();
		request.setTesting(false);
		adView.loadAd(request);
		
		setTracker();
    }
	
	private void setTracker() {
		tracker = GoogleAnalyticsTracker.getInstance();
		tracker.start(this.getString(R.string.ga_api_key),
				getApplicationContext());
	}

	@Override
	public void onResume() {
		super.onResume();
		tracker.trackPageView("/" + this.getLocalClassName());
	}

	@Override
	public void onPause() {
		super.onPause();
		tracker.dispatch();
	}

	
}

