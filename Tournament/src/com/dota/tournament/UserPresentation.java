package com.dota.tournament;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

public class UserPresentation extends HorizontalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Here we will want to set all the components for the logged user on the page
	 * in this case is the presentation layout so maybe we will extends that layout type.
	 * 
	 * */
	private VerticalLayout userInfoLayout = new VerticalLayout();
	private User loggedUser;
	private Button logout;
	
	public UserPresentation(User userLogged){
//		setSizeFull();
		loggedUser = userLogged;
		logout = new Button("logout");
		logout.setStyleName(BaseTheme.BUTTON_LINK);
		logout.addClickListener(logoutListener());
		
		addComponent(loggedUser.getAvatarGif());
		userInfoLayout.addComponent(new Label(loggedUser.getName()));
		userInfoLayout.addComponent(new Label(loggedUser.getEmail()));
		userInfoLayout.addComponent(logout);
		addComponent(userInfoLayout);
		
	}
	
	public Button.ClickListener logoutListener(){
		return new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getSession().close();
				UI.getCurrent().getPage().reload();
			}
		};
	}
	
}
