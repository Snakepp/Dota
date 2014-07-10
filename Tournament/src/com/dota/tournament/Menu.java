package com.dota.tournament;

import com.dota.db.DBConnection;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;


public class Menu extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Button tournament,userProfile,stats,admin,activate,members;
	private VerticalLayout bodylayout;
	private DBConnection con;
	private Navigator navigator;
	
	public Menu(VerticalLayout bodyLayout, DBConnection con, Navigator navigator){
		this.bodylayout = bodyLayout;
		this.con = con;
		this.navigator = navigator;
		
		tournament = new Button("Tournament");
		stats = new Button("Dota Stats");
		admin = new Button("Administrate");
		userProfile = new Button("User Profile");
		activate = new Button();
		members = new Button("Members");
		
		activate.setVisible(false);
		
		
		
//		tournament.setSizeFull();
		tournament.setWidth("100%");
//		stats.setSizeFull();
		stats.setWidth("100%");
//		admin.setSizeFull();
		admin.setWidth("100%");
//		userProfile.setSizeFull();
		userProfile.setWidth("100%");
		members.setWidth("100%");
		
		tournament.addClickListener(tournamentClick());
		userProfile.addClickListener(userProfileClick());
		
		addComponent(tournament);
		addComponent(stats);
		addComponent(admin);
		addComponent(userProfile);
		addComponent(members);
	}
	
	public Button.ClickListener tournamentClick(){
		return new Button.ClickListener() {
			
			private static final long serialVersionUID = 2L;

			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				if(bodylayout.getComponentCount()!=0){
					bodylayout.removeAllComponents();
					generateTournament();
				}else{
					generateTournament();
				}
				navigator.navigateTo("/tournament");
			}
		};
	}
	public Button.ClickListener userProfileClick(){
		return new Button.ClickListener() {
			
			private static final long serialVersionUID = 2L;

			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				if(bodylayout.getComponentCount()!=0){
					bodylayout.removeAllComponents();
					generateUserProfile();
				}else{
					generateUserProfile();
				}
				navigator.navigateTo("/userProfile");
			}
		};
	}
	
	
	public Button.ClickListener activateClick(){
		return new Button.ClickListener() {
			
			private static final long serialVersionUID = 2L;

			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				if(bodylayout.getComponentCount()!=0){
					bodylayout.removeAllComponents();
//					generateTournament();
				}else{
//					generateTournament();
				}
				navigator.navigateTo("/activate");
			}
		};
	}
	
	public void generateUserProfile(){
		UserProfile userProfile = new UserProfile();
		bodylayout.addComponent(userProfile);
	}
	
	public void generateTournament(){
		
		Tournament tournament = new Tournament(con);
		for(VersusLayout versus : tournament.teams){
			bodylayout.setCaption("Tournament");
			bodylayout.addComponent(versus);
		}
	}
}
