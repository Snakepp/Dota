package com.dota.tournament;


import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;

@SuppressWarnings("serial")
public class Login extends VerticalLayout{

	private FormLayout form;
	private TextField user;
	private PasswordField password;
	private Button login;
	private Button register;
	
	public Login(){
		form = new FormLayout();
		user = new TextField("User", "");
		user.setRequired(true);
		user.setInputPrompt("your nickname");
		user.setInvalidAllowed(false);
		password = new PasswordField("Password");
		password.setRequired(true);
		password.setInvalidAllowed(false);
		password.setWidth("300px");
		setCaption("Login");
		setSpacing(true);
		setMargin(new MarginInfo(true,true,true,false));
		setSizeUndefined();
		
		register = new Button("Register");
		register.setStyleName(BaseTheme.BUTTON_LINK);
		register.addClickListener(RegisterListener());
		login = new Button("Login");
		login.addClickListener(LoginListener());
		
		form.addStyleName("outlined");
		form.setSizeFull();
		form.setSpacing(true);

		form.addComponent(user);
		form.addComponent(password);
	    form.addComponent(login);
	    form.addComponent(register);
//	    form.setComponentAlignment(login, Alignment.MIDDLE_CENTER);
	    addComponent(form);
	}
	
	public Button.ClickListener RegisterListener(){
		return new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				
				Window subWindow = new Window();
				subWindow.setContent(new Register());
				subWindow.center();
				UI.getCurrent().addWindow(subWindow);
				((Window)subWindow.getParent()).close();
			}
		};
	}
	public Button.ClickListener LoginListener(){
		return new Button.ClickListener() {

			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				Notification.show("Login Successfuly!","Enjoy!",
		                  Notification.Type.TRAY_NOTIFICATION);
			}
		};
	}
}
