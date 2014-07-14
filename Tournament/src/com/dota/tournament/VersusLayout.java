package com.dota.tournament;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class VersusLayout extends HorizontalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Label VS = new Label("VS");
	private VerticalLayout userLayout1 = new VerticalLayout();
	private VerticalLayout userLayout2 = new VerticalLayout();
	
	public VersusLayout(User user1, User user2){
		setSpacing(true);
		setMargin(true);
		setUser(userLayout1, user1);
		setUser(userLayout2, user2);
		addComponent(userLayout1);
		addComponent(VS);
		addComponent(userLayout2);
		setComponentAlignment(VS, Alignment.MIDDLE_CENTER);
	}
	
	public void setUser(VerticalLayout layout, User user){
		if(user!=null){
			layout.setSpacing(true);
			layout.addComponent(user.getAvatarJpg());
			layout.addComponent(new Label(user.getName()));
		}
	}
}
