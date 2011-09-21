package com.andrios.creditcardroulette;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class GamesListActivity extends Activity {

	Button russianBTN;
	AndriosData mData;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameslistactivity);
        
        getExtras();
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
	 private void getExtras() {
	    	Intent intent = this.getIntent();
			mData = (AndriosData) intent.getSerializableExtra("data");
				
		}

	private void setConnections() {
		russianBTN = (Button) findViewById(R.id.gamesListActivityRussianBTN);
		
	}

	private void setOnClickListeners() {
		russianBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				Intent intent = new Intent(GamesListActivity.this,
						PlayerSelectActivity.class);
				intent.putExtra("data", mData);
				startActivity(intent);
				
			}
			
		});
		
	}
	
}
