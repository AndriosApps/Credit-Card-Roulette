package com.andrios.creditcardroulette;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends TabActivity {
    
	
	/** Called when the activity is first created. */
   
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        setConnections();
    }

	

	private void setConnections() {
		TabHost mTabHost = getTabHost();
        Intent intent;
        Resources res = getResources(); 
        
        //Setup for Games Tab (Tab 0)
        intent = new Intent().setClass(this, PlayerSelectActivity.class);
        
        mTabHost.addTab(mTabHost.newTabSpec("Game").setIndicator("",res.getDrawable(R.drawable.revolver))
        		.setContent(intent));
        
      //Setup for Table Roulette Tab (Tab 1)
        intent = new Intent().setClass(this, ComingSoonActivity.class);
        
        mTabHost.addTab(mTabHost.newTabSpec("Game").setIndicator("",res.getDrawable(R.drawable.icon2))
        		.setContent(intent));
        
        //Setup for Table Roulette Tab (Tab 1)
        intent = new Intent().setClass(this, ToolListActivity.class);
        
        mTabHost.addTab(mTabHost.newTabSpec("Tools").setIndicator("",res.getDrawable(R.drawable.wrenches))
        		.setContent(intent));
        
        //Setup for Workout Tab (Tab 2)
        intent = new Intent().setClass(this, AboutActivity.class);
        
        mTabHost.addTab(mTabHost.newTabSpec("About").setIndicator("",res.getDrawable(R.drawable.abouticon))
        		.setContent(intent));
        
       
       

        //Set Tab host to Home Tab
        mTabHost.setCurrentTab(0);
	}
}