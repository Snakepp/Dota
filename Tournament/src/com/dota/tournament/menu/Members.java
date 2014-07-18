package com.dota.tournament.menu;

import java.util.ArrayList;
import java.util.List;

import com.dota.db.DBConnection;
import com.dota.tournament.User;
import com.vaadin.data.Item;
import com.vaadin.ui.Button;
import com.vaadin.ui.Image;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class Members extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Table table;
	private DBConnection con;
	private User userLogged;
	private int count;
	
	public Members(DBConnection con, User userLogged){
		table = new Table();
		this.con = con;
		this.userLogged = userLogged;
		init();
	}
	
	public void init(){
		table.setImmediate(true);
		
		table.addContainerProperty("Name", String.class, null);
		table.addContainerProperty("Avatar", Image.class, null);
		table.addContainerProperty("Role", String.class, null);
		if(userLogged.isAdmin()){
			table.addContainerProperty("Make admin", com.vaadin.ui.Button.class, "");
		}
		
		for(User user : con.getUsers()){
			addItem(user,count);
			count++;
		}
		
		table.setFooterVisible(true);
		table.setColumnFooter("Name", "Total Members");
		table.setColumnFooter("Avatar", count+++"");
		addComponent(table);
		
	}
	
	public void addItem(User user, int count){
		Image image = user.getAvatarJpg();
		image.setWidth("50%");
		image.setHeight("50%");
		Button button = new Button();
		button.addClickListener(setAdminMember(user, count));
		if(userLogged.isAdmin()){
			if(user.isAdmin()){
				button.setCaption("remove Admin");
				table.addItem(new Object[]{user.getName(),image,(user.isAdmin())?"Admin":"Member",button},new Integer(count));
			}else{
				button.setCaption("make admin");
				table.addItem(new Object[]{user.getName(),image,(user.isAdmin())?"Admin":"Member",button},new Integer(count));
			}
		}else{
			table.addItem(new Object[]{user.getName(),image,(user.isAdmin())?"Admin":"Member"},new Integer(count));
		}
	}
	
	public Button.ClickListener setAdminMember(final User user, final int itemId){
		return new Button.ClickListener() {
			
			private static final long serialVersionUID = 2L;

			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				if(user.isAdmin()){
					user.setAdmin(false);
				}else{
					user.setAdmin(true);
				}
				con.setUserAdmin(user);
				table.removeItem(itemId);
				addItem(user,itemId);
			}
		};
	}

}
