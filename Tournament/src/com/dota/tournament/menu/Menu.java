package com.dota.tournament.menu;

import com.dota.db.DBConnection;
import com.dota.tournament.User;
import com.dota.utils.PropertyManager;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;


public class Menu extends HorizontalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Button home,tournament,userProfile,stats,admin,activate,members;
	private VerticalLayout bodylayout;
	private  boolean userAdmin = false;
	private DBConnection con;
	private Navigator navigator;
	private User userloged;
	private PropertyManager pm;
	public Menu(VerticalLayout bodyLayout, DBConnection con, Navigator navigator/*, boolean isUserAdmin*/,User userLogged, PropertyManager pm){
		userloged = userLogged;
//		userAdmin = isUserAdmin;
		this.bodylayout = bodyLayout;
		this.con = con;
		this.navigator = navigator;
		this.pm=pm;
		
		home = new Button("Home");
		tournament = new Button("Tournament");
		stats = new Button("Dota Stats");
		admin = new Button("Administrate");
		userProfile = new Button("User Profile");
		activate = new Button();
		members = new Button("Members");
		
		activate.setVisible(false);
		
//		tournament.setSizeFull();
		tournament.setWidth("100%");
		home.setWidth("100%");
		
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
		admin.addClickListener(administratorClick());
		
		addComponent(tournament);
		addComponent(stats);
		if(userLogged.isAdmin()){
			addComponent(admin);
		}
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
	
	public Button.ClickListener administratorClick(){
		return new Button.ClickListener() {
			
			private static final long serialVersionUID = 2L;
			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				if(bodylayout.getComponentCount()!=0){
					bodylayout.removeAllComponents();
					generateAdminPage();
				}else{
					generateAdminPage();
				}
				navigator.navigateTo("/administration");
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
		UserProfile userProfile = new UserProfile(userloged, con, pm);
		bodylayout.addComponent(userProfile);
	}
	public void generateMembers(){
		Members mem = new Members(con,userloged);
		bodylayout.addComponent(mem);
	}
	public void generateAdminPage(){
		Administrator admin = new Administrator();
		bodylayout.addComponent(admin);
	}
	
	public void generateTournament(){
		
		Tournament tournament = new Tournament(con);
		for(VersusLayout versus : tournament.teams){
			bodylayout.addComponent(versus);
		}
	}
}
