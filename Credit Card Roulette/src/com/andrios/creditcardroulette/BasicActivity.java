package com.andrios.creditcardroulette;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class BasicActivity extends Activity {

	ArrayAdapter<Person> availableAdapter;

	ListView availableLV;
	double bill;
	AndriosData mData;
	ArrayList<Person> players;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basicactivity);
        
        getExtras();
        setConnections();
       
        setOnClickListeners();
        rulesDialog();
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
		players = (ArrayList<Person>) intent.getSerializableExtra("players");
		bill = intent.getDoubleExtra("bill", 0.00);
		mData = (AndriosData) intent.getSerializableExtra("data");
		
	}

	private void setConnections() {
		 availableLV = (ListView) findViewById(R.id.basicActivityListView);
		    
		availableAdapter = new MaskedAdapter(this, android.R.layout.simple_list_item_1, players);
		availableLV.setAdapter(availableAdapter);
	
		availableAdapter.setNotifyOnChange(true);
		
	}

	private void setOnClickListeners() {
		availableLV.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int row,
					long arg3) {
				 
				confirmDialog(row);
				
			
				
			}

		});
		
	}
	
	private void finishDialog(String winner){
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		
		alert.setTitle(winner + " is the winner!");
		alert.setMessage(winner + " Has the bill ($" + bill + ") this time! Thanks for playing");
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				BasicActivity.this.finish();
				
			}
		});
		
		alert.show();
	}
	
	private void rulesDialog(){
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		
		alert.setTitle("Credit Card Roulette Rules");
		alert.setMessage("Have your waitress select the 'Winner' from the following list.");
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.cancel();
				
			}
		});
		
		alert.show();
	}
	
	private void confirmDialog(final int row){
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		
		alert.setTitle("Set Winner");
		alert.setMessage("Confirm that " + players.get(row).getName() + " is the 'Winner'");
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				setMoneyChange(row);
				
			}
		});
		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.cancel();
					}
				});
		alert.show();
	}
	
	private void setMoneyChange(int index) {
		int winner = -1;
		for(int i = 0; i< players.size(); i++){
			if(i == index){
				players.get(i).setLoss(bill);
				winner = i;
			}else{
				players.get(i).setSaved(bill / players.size());
			}
			mData.setPerson(players.get(i));
		}
		mData.write(BasicActivity.this);
		finishDialog(players.get(winner).getName());
	}
	
}
