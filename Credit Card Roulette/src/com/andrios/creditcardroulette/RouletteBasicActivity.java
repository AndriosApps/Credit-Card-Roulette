package com.andrios.creditcardroulette;

import java.util.ArrayList;
import java.util.Random;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;



import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
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

	ArrayList<Person> players;
	Button fireBTN;
	TextView nameLBL, billTXT;
	LinearLayout imageview;
	int currentPlayer = 0;
	ViewFlipper flipper;//Used to Show animation between Back / Front of card.
	boolean chosen = false;
	AdView adView;
	AdRequest request;
	GoogleAnalyticsTracker tracker;
	MediaPlayer mp;
	double bill;
	AndriosData mData;
	
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


	@SuppressWarnings("unchecked")
	private void getExtras() {
		Intent intent = this.getIntent();
		players = (ArrayList<Person>) intent.getSerializableExtra("players");
		bill = intent.getDoubleExtra("bill", 0.00);
		mData = (AndriosData) intent.getSerializableExtra("data");
		
	}

	private void setConnections() {
		fireBTN = (Button) findViewById(R.id.rouletteBasicActivityFireBTN);
		nameLBL = (TextView) findViewById(R.id.rouletteBasicActivityNameLBL);
		billTXT = (TextView) findViewById(R.id.rouletteBasicActivityBillTXT);
		billTXT.setText("$"+Double.toString(bill));
		
		imageview = (LinearLayout) findViewById(R.id.rouletteBasicActivityImageView);
		flipper = (ViewFlipper) findViewById(R.id.viewFlipper1); 
		flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
	    flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));

		nameLBL.setText(players.get(currentPlayer).toString());
		
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
						mp = MediaPlayer.create(RouletteBasicActivity.this, R.raw.gunfire);  
						  mp.start();
						  mp.setOnCompletionListener(new OnCompletionListener() {

		                        public void onCompletion(MediaPlayer mp) {
		                            // TODO Auto-generated method stub
		                            mp.release();
		                        }

		                    });
						}catch(Exception e){
							e.printStackTrace();
						}
						setMoneyChange(currentPlayer);
						nameLBL.setText(players.get(currentPlayer).toString());
						fireBTN.setEnabled(false);
						Toast.makeText(RouletteBasicActivity.this,
								players.get(currentPlayer).getName()+" has this one. Thanks for Playing!",
								Toast.LENGTH_LONG).show();
						imageview.setBackgroundResource(R.drawable.shooting_target2);
					}else{
						try{
						mp = MediaPlayer.create(RouletteBasicActivity.this, R.raw.dry_fire);  
						  mp.start();
						  mp.setOnCompletionListener(new OnCompletionListener() {

		                        public void onCompletion(MediaPlayer mp) {
		                            // TODO Auto-generated method stub
		                            mp.release();
		                        }

		                    });

						}catch(Exception e){
							e.printStackTrace();
						}
						getNextPlayer();
					}
				}else{

					setMoneyChange(currentPlayer);
					nameLBL.setText(players.get(currentPlayer).toString());
				
					Toast.makeText(RouletteBasicActivity.this,
							players.get(currentPlayer).getName()+" has this one. Thanks for Playing!",
							Toast.LENGTH_LONG).show();
				}
			
				
			}

			
			
		});
		
		
	}
	private void setMoneyChange(int index) {
		for(int i = 0; i< players.size(); i++){
			if(i == index){
				players.get(i).setLoss(bill);
			}else{
				players.get(i).setSaved(bill / players.size());
			}
			mData.setPerson(players.get(i));
		}
		mData.write(RouletteBasicActivity.this);
	}
	
	private void getNextPlayer(){
		currentPlayer++;
		if(currentPlayer >= players.size()){
			currentPlayer = 0;
		}
		flipper.showNext();
		nameLBL.setText(players.get(currentPlayer).toString());
	}
	

	
}
