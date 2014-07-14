package com.dota.tournament.menu;

import java.util.ArrayList;
import java.util.List;

import com.dota.db.DBConnection;
import com.dota.tournament.User;
import com.vaadin.data.Item;
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
	
	public Members(DBConnection con){
		table = new Table();
		this.con = con;
		init();
	}
	
	public void init(){
		table.setImmediate(true);
		
		table.addContainerProperty("Name", String.class, null);
		table.addContainerProperty("Avatar", Image.class, null);
		table.addContainerProperty("Role", String.class, null);
		List<Item> items = new ArrayList<Item>();
		int count=0;
		for(User user : con.getUsers()){
			Image image = user.getAvatarJpg();
			image.setWidth("50%");
			image.setHeight("50%");
			table.addItem(new Object[]{user.getName(),image,(user.isAdmin())?"Admin":"Member"},new Integer(count));
			count++;
		}
		for(Item a : items){
			System.out.println(a.toString());
		}
		
		table.setFooterVisible(true);
		table.setColumnFooter("Name", "Total Members");
		table.setColumnFooter("Avatar", count+++"");
		addComponent(table);
		
	}

}
