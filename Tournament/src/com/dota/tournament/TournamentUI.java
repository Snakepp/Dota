package com.dota.tournament;

import java.io.File;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;

import com.dota.db.DBConnection;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("my-chameleon")
public class TournamentUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = TournamentUI.class)
	public static class Servlet extends VaadinServlet {
	}

	final GridLayout mainLayout = new GridLayout();
	final HorizontalLayout presentationLayout = new HorizontalLayout();
	final HorizontalLayout headLayout = new HorizontalLayout();
	final VerticalLayout menuLayout = new VerticalLayout();
	final VerticalLayout bodyLayout = new VerticalLayout();
	final VerticalLayout notificationLayout = new VerticalLayout();
	final VerticalLayout creditsLayout = new VerticalLayout();
	private DBConnection connection;
	
	@Override
	protected void init(VaadinRequest request) {
		mainLayout.addStyleName("outlined");
		mainLayout.setSizeFull();
		Label verify = new Label("Verify Layout1");
		Label verify2 = new Label("Verify Layout");
		Label verify3 = new Label("Verify Layout");
		Label verify4 = new Label("Verify Layout");
		Label verify5 = new Label("Verify Layout");
		Label verify6 = new Label("Verify Layout");
		try{
		connection = new DBConnection("localhost", "root", "", "ghost", "3306");
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
		Image image = new Image("Title",resource);
		Image img = new Image();
		img.setSource(resource);
		headLayout.addComponent(img);
		
//		headLayout.addComponent(verify);
//		presentationLayout.addComponent(verify6);
		menuLayout.addComponent(verify2);
		bodyLayout.addComponent(verify3);
		notificationLayout.addComponent(verify4);
		creditsLayout.addComponent(verify5);
		
		if(getSession().getAttribute("usernameId")==null)
			addWindow(new Login(connection));
		else{
			long loggedUserId = (Long) getSession().getAttribute("usernameId");
			User userLogged = connection.getUser(loggedUserId);
			presentationLayout.addComponent(new UserPresentation(userLogged));
//			SendMail send = new SendMail();
//			send.send("cj10jose1@gmail.com");
		}
		generateBorderLayout();
		setContent(mainLayout);
	}

	private void generateBorderLayout() {
		mainLayout.setRows(3);
		mainLayout.setColumns(3);
		
//        mainLayout.addComponent(headLayout, 0, 0, 2, 0);
		headLayout.setMargin(true);
		presentationLayout.setMargin(true);
		menuLayout.setMargin(true);
		bodyLayout.setMargin(true);
		notificationLayout.setMargin(true);
		creditsLayout.setMargin(true);
		headLayout.setHeight("20%");
		headLayout.setWidth("80%");
		presentationLayout.setWidth("100%");
		mainLayout.addComponent(headLayout, 0, 0);
		mainLayout.addComponent(presentationLayout, 2, 0);
        mainLayout.addComponent(menuLayout, 0, 1);
        mainLayout.addComponent(bodyLayout, 1, 1);
        mainLayout.addComponent(notificationLayout, 2, 1);
        mainLayout.addComponent(creditsLayout, 0, 2, 2, 2);

        mainLayout.setRowExpandRatio(0, 1.4f);
        mainLayout.setRowExpandRatio(1, 5.0f);
        mainLayout.setRowExpandRatio(2, 1.0f);

        mainLayout.setColumnExpandRatio(0, 1.0f);
        mainLayout.setColumnExpandRatio(1, 4.0f);
        mainLayout.setColumnExpandRatio(2, 1.0f);
    }
}