package com.dota.tournament;

import java.io.File;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Image;


public class User {

	private String name;
	private String email;
	private Image avatarGif;
	private Image avatarJpg;
	private long id;
	
	public String getName(){
		return name;
	}
	public String getEmail(){
		return email;
	}
	public long getId(){
		return id;
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
	
	public Image getAvatarGif(){
		return avatarGif;
	}
	public Image getAvatarJpg(){
		return avatarJpg;
	}
	
	public void setAvatarName(String avatarName){
		avatarGif = new Image();
		avatarJpg = new Image();
		
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		String filePath = basepath +"/WEB-INF/images/Heroes/"+avatarName;
		FileResource resource1 = new FileResource(new File(filePath+".jpg"));
		FileResource resource2 = new FileResource(new File(filePath+".gif"));
		
		avatarJpg.setSource(resource1);
		avatarGif.setSource(resource2);
	}
	
	
}
