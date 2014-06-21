package com.dota.tournament;



import com.dota.db.DBConnection;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
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

		
		submitlayout.setSpacing(true);
		submitlayout.addComponent(login);
		submitlayout.addComponent(register);
		submitlayout.setComponentAlignment(register, Alignment.BOTTOM_RIGHT);
		
		form.addStyleName("outlined");
		form.setSizeFull();
		form.setSpacing(true);

		form.addComponent(user);
		form.addComponent(password);
	    
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
				boolean exists=conn.isUserExistent(user.getValue(), password.getValue());
				if(exists){
					User logedUser = conn.getUser(user.getValue(), password.getValue());
//					loggedInUserLayout.addComponent(new Label(logedUser.getName()));
//					loggedInUserLayout.addComponent(new Label(logedUser.getEmail()));
//					UserPresentation loggedUser = new UserPresentation(logedUser);
//					loggedInUserLayout = loggedUser;
					UI.getCurrent().getSession().setAttribute("usernameId", logedUser.getId());
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
}
