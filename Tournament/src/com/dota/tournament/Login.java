package com.dota.tournament;



import javax.servlet.http.Cookie;

import com.dota.db.DBConnection;
import com.dota.utils.Encript;
import com.dota.utils.Encripter;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;

@SuppressWarnings("serial")
public class Login extends Window{

	private VerticalLayout mainlayout;
	private HorizontalLayout submitlayout;
	private FormLayout form;
	private TextField user;
	private PasswordField password;
	private CheckBox rememberMe;
	private Button login;
	private Button register;
	private DBConnection conn;
	
	public Login( DBConnection connection){
		super("Login");
		center();
		setModal(true);
		setDraggable(false);
		setResizable(false);
		conn=connection;
		mainlayout = new VerticalLayout();
		submitlayout = new HorizontalLayout();
		setClosable(false);
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
		mainlayout.setSpacing(true);
		mainlayout.setMargin(new MarginInfo(true,true,true,false));
		setSizeUndefined();
		
		register = new Button("Register");
		register.setStyleName(BaseTheme.BUTTON_LINK);
		register.addClickListener(RegisterListener());
		login = new Button("Login");
		login.addClickListener(LoginListener());
		
		rememberMe = new CheckBox("Remember Me?");
		fillValuesWithCookies();
		
		submitlayout.setSpacing(true);
		submitlayout.addComponent(login);
		submitlayout.addComponent(register);
		submitlayout.setComponentAlignment(register, Alignment.BOTTOM_RIGHT);
		
		form.addStyleName("outlined");
		form.setSizeFull();
		form.setSpacing(true);

		form.addComponent(user);
		form.addComponent(password);
		form.addComponent(rememberMe);
	    
	    form.addComponent(submitlayout);
//	    form.setComponentAlignment(login, Alignment.MIDDLE_CENTER);
	    mainlayout.addComponent(form);
	    setContent(mainlayout);
	   
	}
	
	public Button.ClickListener RegisterListener(){
		return new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().addWindow(new Register(conn));
				close();
			}
		};
	}
	public Button.ClickListener LoginListener(){
		return new Button.ClickListener() {

			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				boolean exists = false;
				boolean existsToken = false;
				Encripter encripter = new Encripter();
				exists=conn.isUserExistent(user.getValue(), encripter.encript(password.getValue()).substring(0,20));
				if(!exists){
					existsToken= conn.isTokenExistent(user.getValue(), password.getValue());
				}
				if(exists || existsToken){
					User logedUser = null;
					if(exists)
						logedUser = conn.getUser(user.getValue(), password.getValue());
					else
						logedUser = conn.getUserByToken(user.getValue(), password.getValue());
//					loggedInUserLayout.addComponent(new Label(logedUser.getName()));
//					loggedInUserLayout.addComponent(new Label(logedUser.getEmail()));
//					UserPresentation loggedUser = new UserPresentation(logedUser);
//					loggedInUserLayout = loggedUser;
					UI.getCurrent().getSession().setAttribute("usernameId", logedUser.getId());
					
					if(rememberMe.getValue()){
						saveDataIntoCookies(logedUser);
					}else{
						deleteDataFromCookies();
					}
					
					Notification.show("Login Successfuly!","Enjoy!",
							Notification.Type.TRAY_NOTIFICATION);
					UI.getCurrent().getPage().reload();
					
					close();
				}else{
					Notification.show("Incorrect login","Badd pass/User",
							Notification.Type.ERROR_MESSAGE);
				}
			}
			
		};
	}
	
	public void fillValuesWithCookies(){
		Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();
		
		for(int x=0; x<cookies.length;x++){
//			if(cookies[x].getName().equals("username")){
//				user.setValue(cookies[x].getValue());
//			}
//			if(cookies[x].getName().equals("password")){
//				password.setValue(cookies[x].getValue());
//			}
			if(cookies[x].getName().equals("loggedUser")){
				User logUser = conn.getUser(cookies[x].getValue());
				if(logUser!=null){
					user.setValue(logUser.getName());
					password.setValue(logUser.getToken());
					rememberMe.setValue(true);
				}
			}
		}
	}
	
	public void saveDataIntoCookies(User userLogged){
		Cookie user = new Cookie("loggedUser", userLogged.getToken());
//		Cookie userCookie = new Cookie("username", user.getValue());
//		Cookie passCookie = new Cookie("password", password.getValue());
		// Set the cookie path.
//		userCookie.setPath(VaadinService.getCurrentRequest().getContextPath());
		user.setPath(VaadinService.getCurrentRequest().getContextPath());

		// Save cookie
//		VaadinService.getCurrentResponse().addCookie(userCookie);
		VaadinService.getCurrentResponse().addCookie(user);
		
		// Set the cookie path.
//		passCookie.setPath(VaadinService.getCurrentRequest().getContextPath());
//
//		// Save cookie
//		VaadinService.getCurrentResponse().addCookie(passCookie);
	}
	public void deleteDataFromCookies(){
		Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();
		
		for(int x=0; x<cookies.length;x++){
			if(cookies[x].getName().equals("loggedUser")){
				cookies[x].setMaxAge(0);
				cookies[x].setValue(null);
				cookies[x].setPath(VaadinService.getCurrentRequest().getContextPath());
				VaadinService.getCurrentResponse().addCookie(cookies[x]);
			}
		}
	}
}
