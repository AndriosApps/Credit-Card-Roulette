package com.andrios.creditcardroulette;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;

public class SplitCalcActivity extends Activity{

	GoogleAnalyticsTracker tracker;
	AdView adView;
	AdRequest request;
	double bill = 0.00;
	double tipPercent = 0.15;
	double tip = 0.00;
	double total = 0.00;
	double split = 0.00;
	int people = 1;
	
	EditText billTXT;
	TextView tipPercentTXT, tipTXT, totalTXT, peopleTXT, splitTXT;
	Button tipPercentUpBTN, tipPercentDownBTN, tipUpBTN, tipDownBTN, nextBTN;
	Button peopleUpBTN, peopleDownBTN;
	boolean game;
	ArrayList<String> available;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.splitcalcactivity);
	        
	        getExtras();
	        setConnections();
	       
	        setOnClickListeners();
	        setTracker();
	        adView = (AdView)this.findViewById(R.id.splitCalcAdView);
		      
		    request = new AdRequest();
			request.setTesting(false);
			adView.loadAd(request);
			
			
	    }
	
		private void getExtras() {
			Intent intent = this.getIntent();
			game = intent.getBooleanExtra("game", false);
			available = (ArrayList<String>) intent.getSerializableExtra("players");
			
	}

		private void setConnections() {
	
		billTXT =(EditText) findViewById(R.id.splitCalcBillTXT);
		tipPercentTXT =(TextView) findViewById(R.id.splitCalcTipPercentTXT);
		tipTXT =(TextView) findViewById(R.id.splitCalcTipTXT);
		totalTXT =(TextView) findViewById(R.id.splitCalcTotalTXT);

		splitTXT =(TextView) findViewById(R.id.splitCalcSplitTXT);
		peopleTXT =(TextView) findViewById(R.id.splitCalcPeopleTXT);
		peopleTXT.setText(Integer.toString(people));
		peopleUpBTN = (Button) findViewById(R.id.splitCalcPeopleUpBTN);
		peopleDownBTN = (Button) findViewById(R.id.splitCalcPeopleDownBTN);
		
		tipPercentTXT.setText(Integer.toString((int) (tipPercent * 100)));
		
		tipPercentUpBTN = (Button) findViewById(R.id.splitCalcTipPercentUpBTN);
		tipPercentDownBTN = (Button) findViewById(R.id.splitCalcTipPercentDownBTN);
		tipUpBTN = (Button) findViewById(R.id.splitCalcTipAmountUpBTN);
		tipDownBTN = (Button) findViewById(R.id.splitCalcTipAmountDownBTN);
		nextBTN = (Button) findViewById(R.id.splitCalcNextBTN);
		if(game){
			nextBTN.setVisibility(View.VISIBLE);
		}else{
			nextBTN.setVisibility(View.INVISIBLE);
		}
		
	}



		private void setOnClickListeners() {
			
			

		billTXT.setOnKeyListener(new OnKeyListener() {
		   

			public boolean onKey(View v, int keyCode, KeyEvent event) {
				 // If the event is a key-down event on the "enter" button
		        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
		            (keyCode == KeyEvent.KEYCODE_ENTER)) {
		          // Perform action on key press
		          
		        try{
		        	bill = Double.parseDouble(billTXT.getText().toString());
		        	calcTip();
			          setTexts();
			          billTXT.clearFocus();
			          billTXT.requestFocus();
		        }catch(Exception e){
		        	Toast.makeText(SplitCalcActivity.this, "Ensure Format is Correct", Toast.LENGTH_SHORT).show();
		        	
		        }
		          
		          return true;
		        }
				return false;
			}
		});	
		tipPercentUpBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				tipPercent += 0.01;
				
				calcTip();
			}
			
		});
		tipPercentDownBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				tipPercent -= 0.01;
				if(tipPercent <= 0.00){
					tipPercent = 0.00;
				}
				
				calcTip();
				
			}
			
		});
		
		tipUpBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				tip += 0.50;
				total = bill + tip;
				calcTip();
			}
			
		});
		
	
		
		tipDownBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				tip -= 0.50;
				if(tip <= 0.0){
					tip = 0.0;
				}
				total = bill + tip;
				calcTip();
			}
			
		});
		
		peopleDownBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				people -= 1;
				if(people < 1){
					people = 1;
				}
				calcTip();
			}
			
		});
		
		peopleUpBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				people += 1;
				calcTip();
			}
			
		});
		
		nextBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(),
						RouletteBasicActivity.class);
				intent.putExtra("players", available);
				intent.putExtra("bill", total);
				startActivity(intent);
				
			}
			
		});
		
	}
		
		private void setTexts(){
			billTXT.setText("$"+Double.toString(bill));
			tipTXT.setText("$"+Double.toString(roundTwoDecimals(tip)));
			tipPercentTXT.setText(Integer.toString((int) (tipPercent * 100.00))+"%");
			peopleTXT.setText(Integer.toString(people));
			totalTXT.setText("$"+Double.toString(roundTwoDecimals(total)));
			splitTXT.setText("$"+ Double.toString(roundTwoDecimals(split)));
		}
		private double roundTwoDecimals(double d) {
        	DecimalFormat twoDForm = new DecimalFormat("#.##");
        	return Double.valueOf(twoDForm.format(d));
		}
		
		private void calcTip(){
			tip = bill * tipPercent;
			total = tip + bill;
			split = total / people;
			setTexts();
			
		}

		

		public void onResume(){
			super.onResume();
			if(game){
				tracker.trackPageView("Tip Calculator Game Mode");
			}else{
				tracker.trackPageView("Tip Calculator");
			}
			
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
