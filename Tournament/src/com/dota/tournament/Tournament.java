package com.dota.tournament;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dota.db.DBConnection;
import com.vaadin.ui.VerticalLayout;

public class Tournament  extends VerticalLayout{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DBConnection con;
	public List<VersusLayout> teams = new ArrayList<VersusLayout>();
	
	public Tournament(DBConnection connection){
		this.con = connection;
		List<User> users = con.getUsers();
		Collections.shuffle(users);
		int counter=0;
		
		if(users.size()%2==0){
			//par
			while(counter != users.size()){
				teams.add(new VersusLayout(users.get(counter), users.get(counter+1)));
				counter+=2;
			}
		}else{
			//impar
			while(counter != users.size()){
				teams.add(new VersusLayout(users.get(counter), users.get(counter+1)));
				counter+=2;
				int impar = counter+1;
				if(impar == users.size()){
					teams.add(new VersusLayout(users.get(counter), null));
					counter = impar;
				}
			}
		}
	}
}
