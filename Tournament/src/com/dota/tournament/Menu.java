package com.dota.tournament;

import com.dota.db.DBConnection;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;


public class Menu extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Button tournament,userProfile,stats,admin;
	private VerticalLayout bodylayout;
	private DBConnection con;
	public Menu(VerticalLayout bodyLayout, DBConnection con){
		this.bodylayout = bodyLayout;
		this.con = con;
		tournament = new Button("Tournament");
		stats = new Button("Dota Stats");
		admin = new Button("Administrate");
		userProfile = new Button("User Profile");
		
		tournament.setSizeFull();
		stats.setSizeFull();
		admin.setSizeFull();
		userProfile.setSizeFull();
		
		tournament.addClickListener(tournamentClick());
		
		addComponent(tournament);
		addComponent(stats);
		addComponent(admin);
		addComponent(userProfile);
	}
	
	public Button.ClickListener tournamentClick(){
		return new Button.ClickListener() {

			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				Tournament tournament = new Tournament(con);
				for(VersusLayout versus : tournament.teams){
					bodylayout.addComponent(versus);
				}
			}
		};
	}
}
