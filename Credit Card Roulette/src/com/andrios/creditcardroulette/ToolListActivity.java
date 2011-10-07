package com.andrios.creditcardroulette;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ToolListActivity extends Activity {

	GoogleAnalyticsTracker tracker;
	AdView adView;
	AdRequest request;
	Button tipCalcBTN, splitCalcBTN;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toollistactivity);
        
        setTracker();
        setConnections();
       
        setOnClickListeners();
        setTracker();
        adView = (AdView)this.findViewById(R.id.toolListAdView);
	      
	    request = new AdRequest();
		request.setTesting(false);
		adView.loadAd(request);
		 
		
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
	
	private void setConnections() {
		tipCalcBTN = (Button)findViewById(R.id.toolListActivityTipCalcBTN);

		splitCalcBTN = (Button)findViewById(R.id.toolListActivitySplitCalcBTN);
		
	}

	private void setOnClickListeners() {
		tipCalcBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(),
						TipCalcActivity.class);
				
				startActivity(intent);
				
			}
			
		});
		
		splitCalcBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(),
						SplitCalcActivity.class);
				
				startActivity(intent);
				
			}
			
		});
		
	}


	
}
