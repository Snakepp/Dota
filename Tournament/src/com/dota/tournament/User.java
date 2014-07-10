package com.dota.tournament;

import java.io.File;

import com.dota.utils.PropertyManager;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Image;


public class User {
	
	public User(PropertyManager props){
		this.props = props;
	}

	private PropertyManager props;
	private String name;
	private String email;
	private Image avatarGif;
	private Image avatarJpg;
	private long id;
	private boolean active;
	private String token;
	private boolean isAdmin;
	
	public String getName(){
		return name;
	}
	public String getEmail(){
		return email;
	}
	public long getId(){
		return id;
	}
	public boolean activated(){
		return active;
	}
	public boolean isAdmin(){
		return isAdmin;
	}
	public String getToken(){
		return token;
	}
	
	public void setName(String name){
		this.name = name;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public void setId(long id){
		this.id = id;
	}
	public void setActive(boolean active){
		this.active = active;
	}
	public void setAdmin(boolean admin){
		this.isAdmin = admin;
	}
	public void setToken(String token){
		this.token = token;
	}
	
	
	public Image getAvatarGif(){
		return avatarGif;
	}
	public Image getAvatarJpg(){
		return avatarJpg;
	}
	
	public void setAvatarName(String avatarName){
		avatarGif = new Image();
		avatarJpg = new Image();
		String filePath = props.getHeroesPath()+avatarName;
		FileResource resource1 = new FileResource(new File(filePath+".jpg"));
		FileResource resource2 = new FileResource(new File(filePath+".gif"));
		
		avatarJpg.setSource(resource1);
		avatarGif.setSource(resource2);
	}
	
	
}
