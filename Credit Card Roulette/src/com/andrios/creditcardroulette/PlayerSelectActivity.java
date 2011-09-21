package com.andrios.creditcardroulette;


import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Observable;
import java.util.Observer;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.AdapterView.OnItemClickListener;

public class PlayerSelectActivity extends Activity implements Observer {

	Button addBTN, playBTN, clearBTN;
	ViewFlipper flipper;//Used to Show animation between Back / Front of card.
	ArrayList<Person> contacts;
	ArrayList<Person> available;
	ListView contactsLV, availableLV;
	ArrayAdapter<Person> contactsAdapter;
	ArrayAdapter<Person> availableAdapter;
	AndriosData mData;
	GoogleAnalyticsTracker tracker;
	AdView adView;
	AdRequest request;
	
	/** Called when the activity is first created. */
	 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playerselectactivity);
        
        
        setTracker();
        adView = (AdView)this.findViewById(R.id.playerSelectAdView);
	      
	    request = new AdRequest();
		request.setTesting(false);
		adView.loadAd(request);
    }
    
    public void onStart(){
    	getExtras();
        setConnections();
    	System.out.println("Player Select");
    	super.onStart();
    	
        queryContacts();
        
        setAdapters();
        setOnClickListeners();
       
    }
    
 
    

    private void getExtras() {
    	/*
    	Intent intent = this.getIntent();
		mData = (AndriosData) intent.getSerializableExtra("data");
		mData.addObserver(this);	
		mData.clearHistory();
		*/
    	readData();
    	
	}

	private void queryContacts(){
		
    	ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                   null, null, null, null);
        if (cur.getCount() > 0) {
	   	    while (cur.moveToNext()) {
	   	        String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
	   	        String name = cur.getString(
	                           cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
	   	        
	   	        if(name != null){
	   	        	Person p = new Person(name, id);
	   	        	
	   	        	int index = mData.checkPerson(p);
	   	        	if(index != -1){
	   	        		contacts.add(mData.getPerson(index));
	   	        	}else{
	   	        		contacts.add(p);
	   	        	}
	   	        	
	   	        }
	   	        
            }
    	}
    }


	private void setConnections() {
		// TODO Auto-generated method stub
		addBTN = (Button) findViewById(R.id.playerSelectActivityAddBTN);
		playBTN = (Button) findViewById(R.id.playerSelectActivityPlayBTN);
		clearBTN = (Button) findViewById(R.id.selectedPlayersActivityClearBTN);
		flipper = (ViewFlipper) findViewById(R.id.viewFlipper1); 
		flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
	    flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));  
		
	    contactsLV = (ListView) findViewById(R.id.playerSelectActivityContactsListView);
	    availableLV = (ListView) findViewById(R.id.playerSelectActivityAvailableListView);
	    
	    contacts = new ArrayList<Person>();
	    available = new ArrayList<Person>();
	}
	

	private void setAdapters(){
		contactsAdapter = new ArrayAdapter<Person>(this, android.R.layout.simple_list_item_1, contacts);
		contactsLV.setAdapter(contactsAdapter);
		
			contactsAdapter.sort(new Comparator<Person>() {

				public int compare(Person object1, Person object2) {
					
					return object1.getName().compareToIgnoreCase(
							object2.getName());
				}

			});
		
		
		contactsAdapter.setNotifyOnChange(true);
		
		availableAdapter = new ArrayAdapter<Person>(this, android.R.layout.simple_list_item_1, available);
		availableLV.setAdapter(availableAdapter);
	
		availableAdapter.setNotifyOnChange(true);
		
		
	}
	
	private void setOnClickListeners() {
		
		addBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				if(addBTN.getText().equals("Select Players")){
					addBTN.setText("View Selected");
				}else{
					addBTN.setText("Select Players");
				}
				flipper.showNext();
				
			}
			
		});
		
		playBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				if(available.size() < 2){
					Toast.makeText(PlayerSelectActivity.this,
							"Must choose at least two players",
							Toast.LENGTH_SHORT).show();
				}else{
					Intent intent = new Intent(v.getContext(),
							TipCalcActivity.class);
					intent.putExtra("players", available);
					intent.putExtra("game", true);
					intent.putExtra("data", mData);
					startActivity(intent);
				}
				
				
			}
			
		});
		
		clearBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				//TODO Add Error Checking (ensure user wants to do this here)
				contacts.clear();
				available.clear();
				mData.clearHistory();
				mData.write(PlayerSelectActivity.this);
				onStart();
				
			}
			
		});
		
		contactsLV.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int row,
					long arg3) {
				 

				Person p = (Person) contactsAdapter.getItem(row);
			
				contactsAdapter.remove(p);
				availableAdapter.add(p);
				availableAdapter.sort(new Comparator<Person>() {

					public int compare(Person object1, Person object2) {
						return object1.getName().compareToIgnoreCase(
								object2.getName());
					}

				});
			
				
			}

		});
		
		availableLV.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int row,
					long arg3) {
				 

				Person p = (Person) availableAdapter.getItem(row);
			
				availableAdapter.remove(p);
				contactsAdapter.add(p);
				contactsAdapter.sort(new Comparator<Person>() {

					public int compare(Person object1, Person object2) {
						return object1.getName().compareToIgnoreCase(
								object2.getName());
					}

				});
			
				
			}

		});
	}
	
	public void onResume(){
		super.onResume();
		tracker.trackPageView("Player Select");
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

	public void update(Observable observable, Object data) {
		System.out.println("PLAYER SELECT SAW AN UPDATE");
		
	}
	
	private void readData() {
		try {
			FileInputStream fis = openFileInput("data");
			ObjectInputStream ois = new ObjectInputStream(fis);

			mData = (AndriosData) ois.readObject();
			ois.close();
			fis.close();
		} catch (Exception e) {
			System.out.println("MAKING mDATA");
			mData = new AndriosData(this);
			
			
		}
		mData.addObserver(this);
		
		
	}
}
