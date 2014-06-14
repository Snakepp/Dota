package com.dota.tournament;

import java.io.File;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
@Theme("my-chameleon")
public class TournamentUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = TournamentUI.class)
	public static class Servlet extends VaadinServlet {
	}

	final GridLayout mainLayout = new GridLayout();
	final VerticalLayout presentationLayout = new VerticalLayout();
	final HorizontalLayout headLayout = new HorizontalLayout();
	final VerticalLayout menuLayout = new VerticalLayout();
	final VerticalLayout bodyLayout = new VerticalLayout();
	final VerticalLayout notificationLayout = new VerticalLayout();
	final VerticalLayout creditsLayout = new VerticalLayout();
	final Window subWindow = new Window();
	
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
		
		headLayout.addComponent(verify);
		
		// Find the application directory
		String basepath = VaadinService.getCurrent()
		                  .getBaseDirectory().getAbsolutePath();
		// Image as a file resource
		FileResource resource = new FileResource(new File(basepath +
		                        "/WEB-INF/images/logo/head.jpg"));
		// Show the image in the application
		Image image = new Image("Image from file", resource);
		
//		headLayout.addComponent(image);
		headLayout.addComponent(verify);
		presentationLayout.addComponent(verify6);
		
		Button register = new Button("Register");
		register.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				//make something here
				subWindow.setContent(new Register());
				addWindow(subWindow);
			}
		});
		
		
		presentationLayout.addComponent(register);
		menuLayout.addComponent(verify2);
		bodyLayout.addComponent(verify3);
		notificationLayout.addComponent(verify4);
		creditsLayout.addComponent(verify5);
		
		
		
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
		
		mainLayout.addComponent(headLayout, 0, 0);
		mainLayout.addComponent(presentationLayout, 2, 0);
        mainLayout.addComponent(menuLayout, 0, 1);
        mainLayout.addComponent(bodyLayout, 1, 1);
        mainLayout.addComponent(notificationLayout, 2, 1);
        mainLayout.addComponent(creditsLayout, 0, 2, 2, 2);

        mainLayout.setRowExpandRatio(0, 1.0f);
        mainLayout.setRowExpandRatio(1, 5.0f);
        mainLayout.setRowExpandRatio(2, 1.0f);

        mainLayout.setColumnExpandRatio(0, 1.0f);
        mainLayout.setColumnExpandRatio(1, 4.0f);
        mainLayout.setColumnExpandRatio(2, 1.0f);
    }
	
	@Deprecated
	private void demo(){
		final HorizontalLayout headLayout = new HorizontalLayout();
		final VerticalLayout mainLayout = new VerticalLayout();
		final VerticalLayout bodyLayout = new VerticalLayout();
		
			
		mainLayout.setMargin(true);
		mainLayout.addStyleName("outlined");		
		headLayout.setMargin(true);
		headLayout.addStyleName("outlined");
		
		
		Button login = new Button("Login");
		login.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				//make something here
				bodyLayout.addComponent(new Label("pepe"));
			}
		});
		
		
		
		
		mainLayout.addComponent(headLayout);
		mainLayout.addComponent(bodyLayout);
		setContent(mainLayout);
	}
}