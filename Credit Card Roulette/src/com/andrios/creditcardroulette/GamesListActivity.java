package com.andrios.creditcardroulette;

import android.app.Activity;
import android.os.Bundle;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class GamesListActivity extends Activity {

	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameslistactivity);
        
      
        setConnections();
       
        setOnClickListeners();
        /*
        setTracker();
        adView = (AdView)this.findViewById(R.id.toolListAdView);
	      
	    request = new AdRequest();
		request.setTesting(false);
		adView.loadAd(request);
		 */
		
    }

	private void setConnections() {
		// TODO Auto-generated method stub
		
	}

	private void setOnClickListeners() {
		// TODO Auto-generated method stub
		
	}
	
}
