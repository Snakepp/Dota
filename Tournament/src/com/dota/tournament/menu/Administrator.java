package com.dota.tournament.menu;

import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

public class Administrator extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Button createTournament;
	public Administrator(){
		createTournament = new Button("Create Torunament");
		
		addComponent(createTournament);
	}
	

}
