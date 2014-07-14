package com.dota.tournament;

import java.util.Arrays;
import java.util.List;

import com.dota.db.DBConnection;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public class UserProfile extends FormLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DBConnection connection;
	private User user;
	private TextField name;
	private TextField phone;
	private DateField birthday;
	private TextField email;
	private OptionGroup sex;
	private TextArea editor;
	@SuppressWarnings("deprecation")
	public UserProfile(User user, DBConnection connection){

		
		this.user=user;
		this.connection=connection;
		
		
		addComponent(new Label("              Edit your profile                  "));
		
		Label richText = new Label(
                "<h1>YOUR PROFILE</h1>"
                        + "<p>You can <b>add</b> information for  <b>share</b> with your friends</p>"
                        + "<p>(The information you post is visible for everyone).</p>"
                        + "<p>_________________________________________________</p>");
        richText.setContentMode(Label.CONTENT_XHTML);
        addComponent(richText);
		
        addComponent(new Label("                       "));
        
        name = new TextField("Enter your name");
        name.setIcon(new ThemeResource("../runo/icons/16/user.png"));
        name.setValue(user.getName());
        name.setRequired(true);
        addComponent(name);
        
        addComponent(new Label("                       "));
        
        birthday = new DateField("Date of birth  ");
		birthday.setIcon(new ThemeResource("../runo/icons/16/calendar.png"));
        addComponent(birthday);
        
        addComponent(new Label("                       "));
        
        phone = new TextField("Cell Phone");
        phone.setIcon(new ThemeResource("../runo/icons/16/globe.png"));
        addComponent(phone);      
        
        addComponent(new Label("                       "));
        
        email = new TextField("E-mail");
        email.setIcon(new ThemeResource("../runo/icons/16/email-reply.png"));
        email.setValue(user.getEmail());
        email.setRequired(true);
        addComponent(email);
        
        addComponent(new Label("                       "));
        
        List<String> sexo = Arrays.asList(new String[] {"Man", "Woman"});
        sex = new OptionGroup("Select your sex", sexo);
        
        sex.setNullSelectionAllowed(false); // user can not 'unselect'
        sex.select("Man"); // select this by default
        sex.setImmediate(true); // send the change to the server at once
        addComponent(sex); 
        
        addComponent(new Label("                       "));
        
        String message = "Phrase or message.";        
        setSpacing(true);
        setWidth("100%");

        editor = new  TextArea(null, message);
        editor.setIcon(new ThemeResource("../runo/icons/16/email.png"));
        editor.setRows(10);
        editor.setColumns(25);
        //editor.addListener(this);
        editor.setImmediate(true);
        addComponent(editor);

        addComponent(new Label("                       "));  
          
        Button button = new Button("Save Changes");
        button.setIcon(new ThemeResource("../runo/icons/16/ok.png"));
  		addComponent(button);
        
  		addComponent(new Label("                       "));
          
        addComponent(new Label("_________________________________________________"));
        
        
	}
	
	public Button.ClickListener SaveClick(){
		return new Button.ClickListener() {
			
			private static final long serialVersionUID = 2L;

			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				user.setName(name.getValue());
				user.setEmail(email.getValue());
			    java.sql.Date sqlDate = new java.sql.Date(birthday.getValue().getTime());			    
				user.setBirthday(sqlDate);
				user.setPhone(phone.getValue());
				user.setSex((String)sex.getValue());
				user.setMessage(editor.getValue());
				
				
				connection.modificateUser(user);
				
				Notification.show("The user has been saved sucssessfully");
				
			}
		};
	}
	

}
