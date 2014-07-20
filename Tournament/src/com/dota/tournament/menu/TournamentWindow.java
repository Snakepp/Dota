package com.dota.tournament.menu;

import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

public class TournamentWindow extends Window{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TournamentWindow(){
		super("New Tournament");
		center();
		setContent(new Label("Tournament Window"));
	}

}
