package com.andrios.creditcardroulette;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Observable;
import java.util.Observer;


import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends TabActivity implements Observer {
    
	AndriosData mData;
	/** Called when the activity is first created. */
   
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        readData();
        setConnections();
        mData.addObserver(this);
    }

	

	private void setConnections() {
		TabHost mTabHost = getTabHost();
        Intent intent;
        Resources res = getResources(); 
        
        
      //Setup for Table Roulette Tab (Tab 1)
        intent = new Intent().setClass(this, GamesListActivity.class);
        intent.putExtra("data", mData);
        mTabHost.addTab(mTabHost.newTabSpec("Game").setIndicator("",res.getDrawable(R.drawable.icon2))
        		.setContent(intent));
        
        //Setup for Table Roulette Tab (Tab 1)
        intent = new Intent().setClass(this, ToolListActivity.class);
        intent.putExtra("data", mData);
        mTabHost.addTab(mTabHost.newTabSpec("Tools").setIndicator("",res.getDrawable(R.drawable.wrenches))
        		.setContent(intent));
        
        //Setup for Workout Tab (Tab 2)
        intent = new Intent().setClass(this, AboutActivity.class);
        intent.putExtra("data", mData);
        mTabHost.addTab(mTabHost.newTabSpec("About").setIndicator("",res.getDrawable(R.drawable.abouticon))
        		.setContent(intent));
        
       
       

        //Set Tab host to Home Tab
        mTabHost.setCurrentTab(0);
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
			mData = new AndriosData(MainActivity.this);
			
			
		}
		mData.addObserver(this);
		
		
	}
	
	
	
	public void onDestroy(){
		super.onDestroy();
		
	}



	public void update(Observable observable, Object data) {
		System.out.println("MAIN ACTIVITY SAW UDPATE");
		
		
	}
	
}