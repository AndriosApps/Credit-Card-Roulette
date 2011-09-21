package com.andrios.creditcardroulette;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

import android.content.Context;
import android.widget.Toast;


public class AndriosData extends Observable implements Serializable, Cloneable {



	/**
	 * 
	 */
	private static final long serialVersionUID = -4214863039549187814L;
	ArrayList <Person> people;
	
	public AndriosData(Context context){
		people = new ArrayList<Person>();
		
	}
	

	/**
	 * Getter Methods
	 */
	public Person getPerson(int index){
		return people.get(index);
	}
	
	
	
	/**
	 * Setter Methods
	 */
	
	public void setPerson(Person p){
		int index = checkPerson(p);
		if(index != -1){
			people.remove(index);
		}
		people.add(p);

		System.out.println("DATA SET CHANGED SET PERSON");
		setChanged();
		notifyObservers();

	}
	
	public void clearHistory(){
		people.clear();

		System.out.println("DATA SET CHANGED CLEAR HISTORY");
		setChanged();
		notifyObservers();
	}
	
	
	public int checkPerson(Person p){
		for(int i = 0; i < people.size(); i++){
			
			if(people.get(i).getName().equals(p.getName())){
				System.out.println("Found a Person");
				return i;
			}
		}
		return -1;
	}
	


	
	public void write(Context ctx){
		System.out.println("ANDRIOS DATA BEING WRITTEN");
		AndriosData writableData = null;
		try {
			writableData = (AndriosData) this.clone();
		} catch (CloneNotSupportedException e1) {
			e1.printStackTrace();
		}
		
		
		try {
		
			FileOutputStream fos;
			fos = ctx.openFileOutput("data", Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(writableData);

			oos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(ctx, "Error: Writing to file",
					Toast.LENGTH_SHORT).show();
		}
	}













	
}

