package com.dota.tournament;

public class User {

	private String name;
	private String email;
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
}
