package com.dota.tournament;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class VersusLayout extends HorizontalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Label VS = new Label("VS");

	public VersusLayout(User user1, User user2){
		setSpacing(true);
		addComponent(user1.getAvatarJpg());
		addComponent(VS);
		addComponent(user2.getAvatarJpg());	
		setComponentAlignment(VS, Alignment.MIDDLE_CENTER);
	}
}
