package com.dota.tournament;

import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.BaseTheme;

public class UserPresentation extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Here we will want to set all the components for the logged user on the page
	 * in this case is the presentation layout so maybe we will extends that layout type.
	 * 
	 * */
	private User loggedUser;
	private Button logout;
	public UserPresentation(User userLogged){
		loggedUser = userLogged;
		logout = new Button("logout");
		logout.setStyleName(BaseTheme.BUTTON_LINK);
		logout.addClickListener(logoutListener());
		addComponent(new Label(loggedUser.getName()));
		addComponent(new Label(loggedUser.getEmail()));
		addComponent(logout);
	}
	
	public Button.ClickListener logoutListener(){
		return new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
//				UI.getCurrent().getSession().removeUI(getUI());
				UI.getCurrent().getSession().close();
				UI.getCurrent().getPage().reload();
			}
		};
	}
	
}
