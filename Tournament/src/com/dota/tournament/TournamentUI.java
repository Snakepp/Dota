package com.dota.tournament;

import java.io.File;
import java.sql.SQLException;

import com.dota.db.DBConnection;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("my-chameleon")
public class TournamentUI extends UI {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Navigator navigator;
	boolean loggedUser=false;
	boolean activatedUser = false;
	GridLayout mainLayout = new GridLayout();
	HorizontalLayout presentationLayout = new HorizontalLayout();
	HorizontalLayout headLayout = new HorizontalLayout();
	VerticalLayout menuLayout = new VerticalLayout();
	VerticalLayout bodyLayout = new VerticalLayout();
	VerticalLayout notificationLayout = new VerticalLayout();
	VerticalLayout creditsLayout = new VerticalLayout();
	DBConnection connection;
	
	VerticalLayout main;
	HorizontalLayout head;
	HorizontalLayout body;
	VerticalLayout mainBody;
	
	
	public class MainView extends VerticalLayout implements View{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public MainView(){
			
			head = new HorizontalLayout();
			body = new HorizontalLayout();
			main = new VerticalLayout();
			mainBody = new VerticalLayout();
			
			head.setWidth("100%");
			head.setHeight("20%");
			body.setWidth("100%");
//			main.setSizeFull();
			
			setSizeFull();
			mainLayout.addStyleName("outlined");
			mainLayout.setSizeFull();
			try{
//				Production environment
			connection = new DBConnection("127.0.250.1", "adminQT3aEtS", "AMF84AreG5ZU", "ghost", "3306");
//				Local environment
//			connection = new DBConnection("localhost", "root", "", "ghost", "3306");
//				Local environment(Port forwarding)
//			connection = new DBConnection("127.0.0.1", "adminQT3aEtS", "AMF84AreG5ZU", "ghost", "3307");
				
			}catch(SQLException e){
				//if there is an exception then that means that we could not connect to database
				Notification.show("Database issue!","Could not connect to database",
		                  Notification.Type.ERROR_MESSAGE);
			}
			// Find the application directory
			String basepath = VaadinService.getCurrent()
			                  .getBaseDirectory().getAbsolutePath();
			// Image as a file resource
			FileResource resource = new FileResource(new File(basepath +
			                        "/WEB-INF/images/logo/Head3.jpg"));
			// Show the image in the application
			Image img = new Image();
			img.setSource(resource);
//			img.setWidth("80%");
			head.addComponent(img);
			Menu menu = new Menu(mainBody,connection,navigator);
			menu.setWidth("90%");
			menu.setSpacing(false);
			body.addComponent(menu);
//			headLayout.addComponent(img);
			main.addComponent(head);
			body.addComponent(mainBody);
			body.setExpandRatio(menu, 1.0f);
			body.setExpandRatio(mainBody, 4.0f);
			main.addComponent(body);
//			menuLayout.addComponent(new Menu(bodyLayout,connection,navigator));
//			notificationLayout.addComponent(verify4);
//			creditsLayout.addComponent(verify5);
			
			
			
//			generateBorderLayout();
//			addComponent(mainLayout);
			
			addComponent(main);
			
//			setContent(mainLayout);
		}
		@Override
		public void enter(ViewChangeEvent event) {
			
			if (event.getParameters() == null  || event.getParameters().isEmpty()) {
				body.addComponent(
	                    new Label("Nothing to see here, " +
	                              "just pass along."));
	        }
			
			if(UI.getCurrent().getSession().getAttribute("usernameId")==null)
				UI.getCurrent().addWindow(new Login(connection));
			else{
				if(!loggedUser){
					long loggedUserId = (Long) UI.getCurrent().getSession().getAttribute("usernameId");
					User userLogged = connection.getUser(loggedUserId);
//					presentationLayout.addComponent(new UserPresentation(userLogged));
					UserPresentation loggedUserPresentation = new UserPresentation(userLogged);
//					loggedUserPresentation.setWidth("20%");
					head.addComponent(loggedUserPresentation);
					head.setComponentAlignment(loggedUserPresentation, Alignment.MIDDLE_RIGHT);
					loggedUser = true;
					activatedUser = userLogged.activated();
				}
				if(!activatedUser){
					Notification.show("Your account has NOT been activated, please follow the email instructions to activate it!");
				}
//				SendMail send = new SendMail();
//				send.send();
			}
			if(event.getParameters().equals("tournament")){
				body.addComponent(
	                    new Label("TOURNAMENT, "));
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
		private void generateBorderLayout() {
			mainLayout.setRows(3);
			mainLayout.setColumns(3);
			
//	        mainLayout.addComponent(headLayout, 0, 0, 2, 0);
			headLayout.setMargin(true);
			presentationLayout.setMargin(true);
			menuLayout.setMargin(true);
			bodyLayout.setMargin(true);
			notificationLayout.setMargin(true);
			creditsLayout.setMargin(true);
//			headLayout.setHeight("100%");
//			headLayout.setWidth("80%");
//			presentationLayout.setWidth("100%");
			mainLayout.addComponent(headLayout, 0, 0);
			mainLayout.addComponent(presentationLayout, 2, 0);
	        mainLayout.addComponent(menuLayout, 0, 1);
	        mainLayout.addComponent(bodyLayout, 1, 1);
//	        mainLayout.addComponent(notificationLayout, 2, 1);
	        mainLayout.addComponent(creditsLayout, 0, 2, 2, 2);

	        mainLayout.setRowExpandRatio(0, 1.4f);
	        mainLayout.setRowExpandRatio(1, 5.0f);
	        mainLayout.setRowExpandRatio(2, 1.0f);

	        mainLayout.setColumnExpandRatio(0, 1.0f);
	        mainLayout.setColumnExpandRatio(1, 4.0f);
	        mainLayout.setColumnExpandRatio(2, 1.0f);
	    }
		
	}
	
	
	
	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("Navigation Example");
		navigator = new Navigator(this, this);
		navigator.addView("", new MainView());
	}
}