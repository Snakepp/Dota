package com.dota.tournament;

import java.io.File;
import java.sql.SQLException;

import com.dota.db.DBConnection;
import com.dota.tournament.menu.Menu;
import com.dota.utils.PropertyManager;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("my-chameleon")
public class TournamentUI extends UI {
	
	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Navigator navigator;
	boolean loggedUser=false;
	boolean activatedUser = false;
	GridLayout mainLayout = new GridLayout();
	HorizontalLayout presentationLayout = new HorizontalLayout();
	HorizontalLayout headLayout = new HorizontalLayout();
	HorizontalLayout menuLayout = new HorizontalLayout();
	VerticalLayout bodyLayout = new VerticalLayout();
	VerticalLayout notificationLayout = new VerticalLayout();
	VerticalLayout creditsLayout = new VerticalLayout();
	DBConnection connection;
	
	VerticalLayout main;
	HorizontalLayout head;
	VerticalLayout body;
	HorizontalLayout mainBody;
	VerticalLayout mainCorps;
	VerticalLayout mainCorps1;
	VerticalLayout mainCorps2, mainCorps3;
//	VerticalLayout 
//	VerticalLayout mainBody;
	PropertyManager properties;
	private User userLoged;
	
	public class MainView extends VerticalLayout implements View{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public MainView(){
			properties = new PropertyManager();
			head = new HorizontalLayout();
			body = new VerticalLayout();
			main = new VerticalLayout();
			mainBody = new HorizontalLayout();
			mainCorps = new VerticalLayout();
			mainCorps1 = new VerticalLayout();
			mainCorps2 = new VerticalLayout();
			mainCorps3 = new VerticalLayout();
			//printMainUI();
//			head.setWidth("100%");
//			head.setHeight("20%");
//			body.setWidth("100%");
////			main.setSizeFull();
//			
//			setSizeFull();
//			mainLayout.addStyleName("outlined");
//			mainLayout.setSizeFull();
			try{
				connection = new DBConnection(properties);
				
			}catch(SQLException e){
				//if there is an exception then that means that we could not connect to database
				Notification.show("Database issue!","Could not connect to database",
		                  Notification.Type.ERROR_MESSAGE);
			}
//			// Find the application directory
//
//			//TODO: verify this is obtained for both Test and Production environment
//			FileResource resource = new FileResource(new File(properties.getLogoPath()+"/Head3.jpg"));
//			Image img = new Image();
//			img.setSource(resource);
//			head.addComponent(img);
//			Menu menu = new Menu(mainBody,connection,navigator);
//			menu.setWidth("90%");
//			menu.setHeight("20%");
//			menu.setSpacing(false);
//			body.addComponent(menu);
//			main.addComponent(head);
//			body.addComponent(mainBody);
//			body.setExpandRatio(menu, 1.0f);
//			body.setExpandRatio(mainBody, 4.0f);
//			main.addComponent(body);
//			
//			
//			addComponent(main);
		}
		
		public void printMainUI(){
//			removeAllComponents();
			head.setWidth("100%");
			head.setHeight("20%");
//			body.setWidth("100%");
			mainBody.setWidth("100%");
			mainCorps.setWidth("100%");
			mainCorps2.setWidth("100%");
			mainCorps3.setWidth("100%");
			mainCorps1.setWidth("100%");
//			main.setSizeFull();
			
			setSizeFull();
			mainLayout.addStyleName("outlined");
			mainLayout.setSizeFull();
//			try{
//				connection = new DBConnection(properties);
//				
//			}catch(SQLException e){
//				//if there is an exception then that means that we could not connect to database
//				Notification.show("Database issue!","Could not connect to database",
//		                  Notification.Type.ERROR_MESSAGE);
//			}
			// Find the application directory

			//TODO: verify this is obtained for both Test and Production environment
			FileResource resource = new FileResource(new File(properties.getLogoPath()+"/Head3.jpg"));
			Image img = new Image();
			img.setSource(resource);
			head.addComponent(img);
			Menu menu = new Menu(mainCorps1,connection,navigator,userLoged,properties);
			
			
			mainCorps.addComponent(menu);
			mainCorps.addComponent(mainCorps1);
//			body.addComponent(mainBody);
			mainCorps.setExpandRatio(menu, 1.0f);
			mainCorps.setSpacing(true);
			
			menu.setSizeFull();
			menu.setSpacing(false);
			
			
			body.addComponent(mainBody);
			
			
			mainBody.addComponent(mainCorps2);
			mainBody.addComponent(mainCorps);
			mainBody.addComponent(mainCorps3);
			
			
			mainBody.setExpandRatio(mainCorps2, 0.5f);
			mainBody.setExpandRatio(mainCorps3, 0.5f);
			mainBody.setExpandRatio(mainCorps, 4.0f);
			
			
//			mainBody.setExpandRatio(mainBody, 4.0f);
			
			
			main.addComponent(head);
			main.addComponent(body);
			
			addComponent(main);
			
			UserPresentation loggedUserPresentation = new UserPresentation(userLoged);
			head.addComponent(loggedUserPresentation);
			head.setComponentAlignment(loggedUserPresentation, Alignment.MIDDLE_RIGHT);
			
		}
		
		
		@Override
		public void enter(ViewChangeEvent event) {
			
//			if (event.getParameters() == null  || event.getParameters().isEmpty()) {
//				body.addComponent(
//	                    new Label("Nothing to see here, " +
//	                              "just pass along."));
//	        }
			
			if(UI.getCurrent().getSession().getAttribute("usernameId")==null)
				UI.getCurrent().addWindow(new Login(connection,properties));
			else{
				if(!loggedUser){
					long loggedUserId = (Long) UI.getCurrent().getSession().getAttribute("usernameId");
					User userLogged = connection.getUser(loggedUserId);
					userLoged=userLogged;
//					UserPresentation loggedUserPresentation = new UserPresentation(userLogged);
//					head.addComponent(loggedUserPresentation);
//					head.setComponentAlignment(loggedUserPresentation, Alignment.MIDDLE_RIGHT);
					loggedUser = true;
					activatedUser = userLogged.activated();
					printMainUI();
				}
				if(!activatedUser){
					Notification.show("Your account has NOT been activated, please follow the email instructions to activate it!");
				}
			}
			if(event.getParameters().indexOf("activate")==0){
				//try to get the id from the URL to activate the account
				String post = event.getParameters();
				if(post.contains("?") && post.contains("=")){
					post= post.substring(post.indexOf("activate")+8+1);
					post= post.substring(post.indexOf("=")+1);
					System.out.println(post);
					int id = Integer.parseInt(post);
					connection.activateUser(id);
					Notification.show("Your account has been activated succesfully!");
					navigator.navigateTo("");
				}
			}
			
		}
		
	}
	
	
	
	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("Doteros SJ");
		navigator = new Navigator(this, this);
		navigator.addView("", new MainView());
	}
}