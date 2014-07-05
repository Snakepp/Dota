package com.dota.tournament;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class ActivateAccount {

	public void initiate(){
		final VerticalLayout content = new VerticalLayout();
		content.setMargin(false);
		content.setSpacing(true);
		content.setSizeFull();
		
		final Navigator navigator = new Navigator(UI.getCurrent(), content);
	}
}
