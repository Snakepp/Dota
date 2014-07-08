package com.dota.tournament;


import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.dota.db.DBConnection;
import com.dota.db.SendMail;
import com.dota.utils.Encripter;
import com.dota.utils.PropertyManager;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.UserError;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class Register extends Window{

	private VerticalLayout mainlayout;
	private HorizontalLayout submitlayout;
	private FormLayout form;
	private TextField user,email;
	private PasswordField password;
	private PasswordField verifyPassword;
	private Button register;
	private Button cancel;
	private DBConnection conn;
	private ComboBox comboHeroes;
	private PropertyManager props;
	
	public Register(DBConnection connection, PropertyManager properies){
		super("Register");
		props = properies;
		
		setClosable(false);
		setDraggable(false);
		setResizable(false);
		setModal(true);
		center();
		conn = connection;
		mainlayout = new VerticalLayout();
		submitlayout = new HorizontalLayout();
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
		comboHeroes = new ComboBox();
		comboHeroes.setInputPrompt("select a hero");
		comboHeroes.addItems(getHeroes());
		comboHeroes.setNullSelectionAllowed(false);
		comboHeroes.setRequired(true);
		comboHeroes.setPageLength(3);
		comboHeroes.setFilteringMode(FilteringMode.CONTAINS);
		setIcons(comboHeroes);
		setCaption("register new member");
		mainlayout.setSpacing(true);
		mainlayout.setMargin(new MarginInfo(true,true,true,false));
		setSizeUndefined();
		
		cancel = new Button("Cancel");
		cancel.addClickListener(CancelListener());
		register = new Button("Register");
		register.addClickListener(RegisterListener());
		
		submitlayout.addComponent(register);
		submitlayout.addComponent(cancel);
		submitlayout.setSpacing(true);
		
		form.addStyleName("outlined");
		form.setSizeFull();
		form.setSpacing(true);

		form.addComponent(user);
		form.addComponent(email);
		form.addComponent(password);
		form.addComponent(verifyPassword);
		form.addComponent(comboHeroes);
	    form.addComponent(submitlayout);
	    mainlayout.addComponent(form);
	    setContent(mainlayout);
	}
	
	public Button.ClickListener RegisterListener(){
		return new Button.ClickListener() {

			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				String error = validateRegister();
				if(error==null){
					Encripter encripter = new Encripter();
					conn.saveUser(user.getValue(), encripter.encript(password.getValue()), email.getValue(),(String)comboHeroes.getValue());
					
					Notification.show("Register Successful!","the register was completed successfully, to activate your account follow the mail instructions that we sent to your mail.",
		                  Notification.Type.TRAY_NOTIFICATION);
					User registerUser = conn.getUser(user.getValue(), encripter.encript(password.getValue()));
					SendMail mail = new SendMail();
					mail.sendActivate((int)registerUser.getId(), registerUser.getEmail(), props.getURL());
					close();
					UI.getCurrent().addWindow(new Login(conn, props));
				}else{
					Notification.show("Error!",error,
			                  Notification.Type.ERROR_MESSAGE);
				}
			}
		};
	}
	public Button.ClickListener CancelListener(){
		return new Button.ClickListener() {

			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				close();
				UI.getCurrent().addWindow(new Login(conn, props));
			}
		};
	}
	
	public TextChangeListener passwordValidator(){
		return new TextChangeListener() {
			
			@Override
			public void textChange(TextChangeEvent event) {
				// TODO Auto-generated method stub
				if(!password.getValue().equals(verifyPassword.getValue())){
					verifyPassword.setComponentError(new UserError("passwords don't match."));
				}else{
					verifyPassword.setComponentError(null);
				}
			}
		};
	}
	
	public String validateRegister(){
		String validate = validateUser();
		if(validate!=null)
			return validate;
		validate = validatePass();
		if(((String)comboHeroes.getValue()) == null){
			validate= "Select a hero please.";
		}
		return validate;
	}
	public String validateUser(){
		String error= null;
		if(conn.isUserExistent(user.getValue())){
			error = "this user has already taken, please choce another.";
		}
		if(user.getValue().equals("")){
			error = "no empty values allowed.";
		}
		return error;
	}
	public String validatePass(){
		String error = null;
		if(!password.getValue().equals(verifyPassword.getValue())){
			error = "passwords don't match.";
		}
		return error;
	}
	
	public Set<String> getHeroes(){
		Set<String> heroes = new HashSet<String>();
		String filePath = props.getHeroesPath();
		
		File heroesDir = new File(filePath);
		for(File image : heroesDir.listFiles()){
			if(image.getName().contains(".jpg") || image.getName().contains(".gif")){
				String heroName = image.getName();
				heroName=heroName.substring(0,heroName.indexOf("."));
				heroes.add(heroName);
			}
		}
		
		return heroes;
	}
	
	public void setIcons(ComboBox heroes){
		String filePath =props.getHeroesPath();
		String extencion=".jpg";
		Map<String,FileResource> heroesMap = new HashMap<String, FileResource>();
		for(String hero : getHeroes()){
			heroesMap.put(hero, new FileResource(new File(filePath+hero+extencion)));
		}
		for(String hero : heroesMap.keySet()){
			heroes.setItemIcon(hero, heroesMap.get(hero));
		}
	}
	
}
