package com.dota.tournament.menu;

import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class Administrator extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Button createTournament;
	public Administrator(){
		createTournament = new Button("Create Torunament");
		createTournament.addClickListener(createTournament());
		addComponent(createTournament);
	}
	
	public Button.ClickListener createTournament(){
		return new Button.ClickListener() {
			
			private static final long serialVersionUID = 2L;

			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				TournamentWindow window = new TournamentWindow();
				UI.getCurrent().addWindow(window);
			}
		};
	}
}
