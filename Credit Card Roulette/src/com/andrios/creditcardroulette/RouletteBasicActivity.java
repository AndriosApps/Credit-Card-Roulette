package com.andrios.creditcardroulette;

import java.util.ArrayList;
import java.util.Random;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;



import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

@SuppressWarnings("unchecked")
public class RouletteBasicActivity extends Activity{

	ArrayList<String> players;
	Button fireBTN;
	TextView nameLBL;
	LinearLayout imageview;
	int currentPlayer = 0;
	ViewFlipper flipper;//Used to Show animation between Back / Front of card.
	boolean chosen = false;
	AdView adView;
	AdRequest request;
	GoogleAnalyticsTracker tracker;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roulettebasicactivity);
        
        
        getExtras();
        setConnections();
        
        setOnClickListeners();
        setTracker();
        
        adView = (AdView)this.findViewById(R.id.russianVersionAdView);
	      
	    request = new AdRequest();
		request.setTesting(false);
		adView.loadAd(request);
    }

	@SuppressWarnings("unchecked")
	private void getExtras() {
		Intent intent = this.getIntent();
		players = (ArrayList<String>) intent.getSerializableExtra("players");
		
		
		
	}

	private void setConnections() {
		fireBTN = (Button) findViewById(R.id.rouletteBasicActivityFireBTN);
		nameLBL = (TextView) findViewById(R.id.rouletteBasicActivityNameLBL);
		imageview = (LinearLayout) findViewById(R.id.rouletteBasicActivityImageView);
		flipper = (ViewFlipper) findViewById(R.id.viewFlipper1); 
		flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
	    flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));

		nameLBL.setText(players.get(currentPlayer));
		
	}

	private void setOnClickListeners() {
		
		fireBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				if(!chosen){
					Random generator = new Random(System.currentTimeMillis());
					int value = generator.nextInt(100);
					if(value < 15){
						chosen = true;
						try{
						MediaPlayer mp = MediaPlayer.create(RouletteBasicActivity.this, R.raw.gunfire);  
						  mp.start();
						}catch(Exception e){
							e.printStackTrace();
						}
						Toast.makeText(RouletteBasicActivity.this,
								players.get(currentPlayer)+" has this one. Thanks for Playing!",
								Toast.LENGTH_LONG).show();
						imageview.setBackgroundResource(R.drawable.shooting_target2);
					}else{
						try{
						MediaPlayer mp = MediaPlayer.create(RouletteBasicActivity.this, R.raw.dry_fire);  
						  mp.start();
						}catch(Exception e){
							e.printStackTrace();
						}
						getNextPlayer();
					}
				}else{
					Toast.makeText(RouletteBasicActivity.this,
							players.get(currentPlayer)+" has this one. Thanks for Playing!",
							Toast.LENGTH_LONG).show();
				}
			
				
			}
			
		});
		
		
	}
	
	private void getNextPlayer(){
		currentPlayer++;
		if(currentPlayer >= players.size()){
			currentPlayer = 0;
		}
		flipper.showNext();
		nameLBL.setText(players.get(currentPlayer));
	}
	
	public void onResume(){
		super.onResume();
		tracker.trackPageView("Russian Game");
	}
	
	public void onPause(){
		super.onPause();
		tracker.dispatch();
	}
	
	private void setTracker() {
		tracker = GoogleAnalyticsTracker.getInstance();

	    // Start the tracker in manual dispatch mode...
	    tracker.start("UA-23366060-4", this);
	    
		
	}
	
}
