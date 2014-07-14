package com.dota.tournament;

import java.io.File;
import java.sql.Date;

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
	private String avatar;
	private String phone;
	private String sex;
	private Date birthday;
	private String message;
	
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
	public String getPhone(){
		return phone;
	}
	public String getSex(){
		return sex;
	}
	public Date getBirthday(){
		return birthday;
	}
	public String getMessage(){
		return message;
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
	public void setPhone(String phone){
		this.phone = phone;
	}
	public void setSex(String sex){
		this.sex = sex;
	}
	public void setBirthday(Date birthday){
		this.birthday = birthday;
	}
	public void setMessage(String message){
		this.message = message;
	}
	
	
	public String getAvatar(){
		return avatar;
	}
	public Image getAvatarGif(){
		return avatarGif;
	}
	public Image getAvatarJpg(){
		return avatarJpg;
	}
	
	public void setAvatarName(String avatarName){
		avatar= avatarName;
		avatarGif = new Image();
		avatarJpg = new Image();
		String filePath = props.getHeroesPath()+avatarName;
		FileResource resource1 = new FileResource(new File(filePath+".jpg"));
		FileResource resource2 = new FileResource(new File(filePath+".gif"));
		
		avatarJpg.setSource(resource1);
		avatarGif.setSource(resource2);
	}
	
	
}
