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

public class TipCalcActivity extends Activity{

	GoogleAnalyticsTracker tracker;
	AdView adView;
	AdRequest request;
	double bill = 0.00;
	double tipPercent = 0.15;
	double tip = 0.00;
	double total = 0.00;
	AndriosData mData;
	EditText billTXT;
	TextView tipPercentTXT, tipTXT, totalTXT;
	Button tipPercentUpBTN, tipPercentDownBTN, tipUpBTN, tipDownBTN, nextBTN;
	boolean game;
	ArrayList<Person> available;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.tipcalcactivity);
	        
	        getExtras();
	        setConnections();
	       
	        setOnClickListeners();
	        setTracker();
	        adView = (AdView)this.findViewById(R.id.tipCalcAdView);
		      
		    request = new AdRequest();
			request.setTesting(false);
			adView.loadAd(request);
			
			
	    }
	
		private void getExtras() {
			Intent intent = this.getIntent();
			game = intent.getBooleanExtra("game", false);
			available = (ArrayList<Person>) intent.getSerializableExtra("players");
			mData = (AndriosData) intent.getSerializableExtra("data");
	}

		private void setConnections() {
	
		billTXT =(EditText) findViewById(R.id.tipCalcBillTXT);
		tipPercentTXT =(TextView) findViewById(R.id.tipCalcTipPercentTXT);
		tipTXT =(TextView) findViewById(R.id.tipCalcTipTXT);
		totalTXT =(TextView) findViewById(R.id.tipCalcTotalTXT);
		
		tipPercentTXT.setText(Integer.toString((int) (tipPercent * 100)));
		
		tipPercentUpBTN = (Button) findViewById(R.id.tipCalcTipPercentUpBTN);
		tipPercentDownBTN = (Button) findViewById(R.id.tipCalcTipPercentDownBTN);
		tipUpBTN = (Button) findViewById(R.id.tipCalcTipAmountUpBTN);
		tipDownBTN = (Button) findViewById(R.id.tipCalcTipAmountDownBTN);
		nextBTN = (Button) findViewById(R.id.tipCalcNextBTN);
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
		        	Toast.makeText(TipCalcActivity.this, "Ensure Format is Correct", Toast.LENGTH_SHORT).show();
		        	
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
				setTexts();
			}
			
		});
		
		tipDownBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				tip -= 0.50;
				if(tip <= 0.0){
					tip = 0.0;
				}
				total = bill + tip;
				setTexts();
			}
			
		});
		
		nextBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(),
						RouletteBasicActivity.class);
				intent.putExtra("players", available);
				intent.putExtra("bill", total);
				intent.putExtra("data", mData);
				startActivity(intent);
				TipCalcActivity.this.finish();
				
			}
			
		});
		
	}
		
		private void setTexts(){
			billTXT.setText("$"+Double.toString(bill));
			tipTXT.setText("$"+Double.toString(roundTwoDecimals(tip)));
			tipPercentTXT.setText(Integer.toString((int) (tipPercent * 100.00))+"%");
			
			totalTXT.setText("$"+Double.toString(roundTwoDecimals(total)));
		}
		private double roundTwoDecimals(double d) {
        	DecimalFormat twoDForm = new DecimalFormat("#.##");
        	return Double.valueOf(twoDForm.format(d));
		}
		
		private void calcTip(){
			tip = bill * tipPercent;
			total = tip + bill;
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
