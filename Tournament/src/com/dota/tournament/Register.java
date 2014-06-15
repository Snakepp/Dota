package com.dota.tournament;


import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class Register extends VerticalLayout{

	private FormLayout form;
	private TextField user,email;
	private PasswordField password;
	private PasswordField verifyPassword;
	private Button register;
	private Notification registerNotif;
	
	public Register(){
		form = new FormLayout();
		user = new TextField("User", "");
		user.setRequired(true);
		user.setInputPrompt("your nickname");
		user.setInvalidAllowed(false);
		email = new TextField("Email");
		email.setInputPrompt("email@gmail.com");
		email.setInvalidAllowed(false);
		email.addValidator(new EmailValidator("email must be a valid email address"));
		email.setRequired(true);
		password = new PasswordField("Password");
		password.setRequired(true);
		password.setInvalidAllowed(false);
		password.setWidth("300px");
		verifyPassword = new PasswordField("Verify Password");
		setCaption("register new member");
		setSpacing(true);
		setMargin(new MarginInfo(true,true,true,false));
		setSizeUndefined();
		
		register = new Button("Register");
		register.addClickListener(RegisterListener());
		
		form.addStyleName("outlined");
		form.setSizeFull();
		form.setSpacing(true);

		form.addComponent(user);
		form.addComponent(email);
		form.addComponent(password);
		form.addComponent(verifyPassword);
	    form.addComponent(register);
	    form.setComponentAlignment(register, Alignment.MIDDLE_CENTER);
	    addComponent(form);
	}
	
	public Button.ClickListener RegisterListener(){
		return new Button.ClickListener() {

			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				Notification.show("Register Successful!","the register was completed successfully, to activate your account follow the mail instructions that we sent to your mail.",
		                  Notification.Type.TRAY_NOTIFICATION);
				Window subWindow = new Window();
				subWindow.setContent(new Login());
				subWindow.center();
			}
		};
	}
}
