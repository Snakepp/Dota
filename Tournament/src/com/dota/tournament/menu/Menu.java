package com.dota.tournament.menu;

import com.dota.db.DBConnection;
import com.dota.tournament.Tournament;
import com.dota.tournament.User;
import com.dota.tournament.UserProfile;
import com.dota.tournament.VersusLayout;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;


public class Menu extends HorizontalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Button tournament,userProfile,stats,admin,activate,members;
	private VerticalLayout bodylayout;
	private  boolean userAdmin = false;
	private DBConnection con;
	private Navigator navigator;
	private User userloged;
	
	public Menu(VerticalLayout bodyLayout, DBConnection con, Navigator navigator/*, boolean isUserAdmin*/,User userLogged){
		userloged = userLogged;
//		userAdmin = isUserAdmin;
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
		tournament.setHeight("100%");
//		stats.setSizeFull();
		stats.setWidth("100%");
//		admin.setSizeFull();
		admin.setWidth("100%");
//		userProfile.setSizeFull();
		userProfile.setWidth("100%");
		members.setWidth("100%");
		
		
//		admin.setVisible(!isUserAdmin);
		
		tournament.addClickListener(tournamentClick());
		userProfile.addClickListener(userProfileClick());
		members.addClickListener(memebersClick());
		
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
	
	
	public Button.ClickListener memebersClick(){
		return new Button.ClickListener() {
			
			private static final long serialVersionUID = 2L;
			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				if(bodylayout.getComponentCount()!=0){
					bodylayout.removeAllComponents();
					generateMembers();
				}else{
					generateMembers();
				}
				navigator.navigateTo("/members");
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
		UserProfile userProfile = new UserProfile(userloged, con);
		bodylayout.addComponent(userProfile);
	}
	public void generateMembers(){
		Members mem = new Members(con);
		bodylayout.addComponent(mem);
	}
	
	public void generateTournament(){
		
		Tournament tournament = new Tournament(con);
		for(VersusLayout versus : tournament.teams){
			bodylayout.setCaption("Tournament");
			bodylayout.addComponent(versus);
		}
	}
}
