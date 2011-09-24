package com.andrios.creditcardroulette;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MaskedAdapter extends ArrayAdapter<Person>{

    private ArrayList<Person> items;
	
   
	public MaskedAdapter(Context context, int textViewResourceId,
			ArrayList<Person> items) {
		super(context, textViewResourceId, items);
		this.items = items;
		
		// TODO Auto-generated constructor stub
	}
	
	
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.profile_list_item, null);
            }
            Person p;
            p = items.get(position);
      
            if (p != null) {
                    TextView nameLBL = (TextView) v.findViewById(R.id.profile_list_item_nameLBL);
                   
                    if (nameLBL != null) {
                          nameLBL.setText(Integer.toString(position + 1));                            
                    }
                    
            }
            return v;
    }
    
	
}
