package com.dota.tournament;

import com.dota.db.DBConnection;
import com.dota.db.SendMail;
import com.dota.utils.Encripter;
import com.dota.utils.PropertyManager;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public class ForgotPass extends Window/*VerticalLayout*/{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private FormLayout form;
	private HorizontalLayout submitLayout;
	private Button send,cancel;
	private TextField emailAddress;
	private DBConnection con;
	private String URL;
	private PropertyManager props;
	
	public ForgotPass(DBConnection con, PropertyManager props){
		super("You are an IDIOT!");
		
		this.con = con;
		this.props = props;
		this.URL = props.getURL();
		
		center();
		setModal(true);
		setDraggable(false);
		setResizable(false);
		setClosable(false);
		
		form = new FormLayout();
		submitLayout = new HorizontalLayout();
		
		emailAddress = new TextField("Email Address");
		emailAddress.setInputPrompt("Ex: YourMail@test.com");
		send = new Button("Send");
		send.addClickListener(sendClick());
		cancel = new Button("Cancel");
		cancel.addClickListener(cancelClick());
		
		
		submitLayout.addComponent(send);
		submitLayout.addComponent(cancel);
		submitLayout.setSpacing(true);
		
		form.setMargin(true);
		form.addComponent(emailAddress);
		form.addComponent(submitLayout);
		
		setContent(form);
	}
	
	public String getRandomPass(){
		String randomPass = null;
		
		randomPass = "ChangeMePls";
		
		return randomPass;
	}
	
	public Button.ClickListener sendClick(){
		return new Button.ClickListener() {
			
			private static final long serialVersionUID = 2L;

			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				User user = con.getUserByEmail(emailAddress.getValue());
				if(user!=null){
					SendMail send = new SendMail();
					Encripter encrypt = new Encripter();
					String randomPass = getRandomPass();
					send.sendForgotPass(user, URL, randomPass);
					con.modificateUserPass(user.getId(), encrypt.encript(randomPass));
					returnToLogin();
				}else{
					Notification.show("We don't have anybody with that fucking email!", Notification.Type.ERROR_MESSAGE);
					emailAddress.setValue("");
				}
				
			}
		};
	}
	
	public void returnToLogin(){
		UI.getCurrent().addWindow(new Login(con, props));
		close();
	}
	
	public Button.ClickListener cancelClick(){
		return new Button.ClickListener() {
			
			private static final long serialVersionUID = 2L;

			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				returnToLogin();
			}
		};
	}
	
	
	

}
