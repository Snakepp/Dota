package com.dota.db;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.dota.tournament.User;
import com.dota.utils.PropertyManager;


public class DBConnection {

	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private String user;
	private String pass;
	private String db;
	private String port;
	private String host;
	private PropertyManager prop;
	 
	 public DBConnection(PropertyManager props) throws SQLException{
		 this.prop = props;
		 
		 this.user = props.getDBUser();
		 this.pass = props.getDBPass();
		 this.host = props.getDBHost();
		 this.db = props.getDB();
		 this.port = props.getDBPort();
			 try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			connect = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+db,user, pass);
	     
	 }
	
	public String query(){
		PreparedStatement preparedStatement;
		ResultSet querRslt = null;
		String data = null;
		int key = 0;
		try {
			preparedStatement = connect.prepareStatement("select * from notes where id =?");
			preparedStatement.setString(1, "2");
			querRslt = preparedStatement.executeQuery();
					
			while(querRslt.next()) { // process results one row at a time
				 key = querRslt.getInt(1);
				 data = querRslt.getString(3);
			}
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
		data += " id:"+key;
		return data;
	}
	
	public User getUser(String userName, String pass){
		User user = new User(prop);
		PreparedStatement preparedStatement;
		ResultSet querRslt = null;
		try {
			preparedStatement = connect.prepareStatement("select * from users where name =? and pass = ?");
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, pass);
			querRslt = preparedStatement.executeQuery();
			while(querRslt.next()) {
				user.setId(querRslt.getLong(1));
				 user.setName(querRslt.getString(2));
				 user.setEmail(querRslt.getString(4));
				 user.setAvatarName(querRslt.getString(5));
				 user.setActive(querRslt.getInt(6) == 0 ? false : true);
				 user.setToken(querRslt.getString(7));
				 user.setAdmin(querRslt.getInt(8) == 0 ? false : true);
				 
			}
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	public User getUserByToken(String userName, String token){
		User user = new User(prop);
		PreparedStatement preparedStatement;
		ResultSet querRslt = null;
		try {
			preparedStatement = connect.prepareStatement("select * from users where name =? and token = ?");
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, token);
			querRslt = preparedStatement.executeQuery();
			while(querRslt.next()) {
				user.setId(querRslt.getLong(1));
				 user.setName(querRslt.getString(2));
				 user.setEmail(querRslt.getString(4));
				 user.setAvatarName(querRslt.getString(5));
				 user.setActive(querRslt.getInt(6) == 0 ? false : true);
				 user.setToken(querRslt.getString(7));
				 user.setAdmin(querRslt.getInt(8) == 0 ? false : true);
			}
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	public User getUser(long id){
		User user = new User(prop);
		PreparedStatement preparedStatement;
		ResultSet querRslt = null;
		try {
			preparedStatement = connect.prepareStatement("select * from users where id =?");
			preparedStatement.setLong(1, id);
			querRslt = preparedStatement.executeQuery();
			while(querRslt.next()) {
				user.setId(querRslt.getLong(1));
				 user.setName(querRslt.getString(2));
				 user.setEmail(querRslt.getString(4));
				 user.setAvatarName(querRslt.getString(5));
				 user.setActive(querRslt.getInt(6) == 0 ? false : true);
				 user.setToken(querRslt.getString(7));
				 user.setAdmin(querRslt.getInt(8) == 0 ? false : true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	public User getUser(String token){
		User user = new User(prop);
		PreparedStatement preparedStatement;
		ResultSet querRslt = null;
		try {
			preparedStatement = connect.prepareStatement("select * from users where token =?");
			preparedStatement.setString(1, token);
			querRslt = preparedStatement.executeQuery();
			int count=0;
			while(querRslt.next()) {
				user.setId(querRslt.getLong(1));
				 user.setName(querRslt.getString(2));
				 user.setEmail(querRslt.getString(4));
				 user.setAvatarName(querRslt.getString(5));
				 user.setActive(querRslt.getInt(6) == 0 ? false : true);
				 user.setToken(querRslt.getString(7));
				 user.setAdmin(querRslt.getInt(8) == 0 ? false : true);
				 count++;
			}
			if(count==0){
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public List<User> getUsers(){
		PreparedStatement preparedStatement;
		ResultSet querRslt = null;
		List<User> users = new ArrayList<User>();
		try {
			preparedStatement = connect.prepareStatement("select * from users");
			querRslt = preparedStatement.executeQuery();
			while(querRslt.next()) {
				User user = new User(prop);
				user.setId(querRslt.getLong(1));
				user.setName(querRslt.getString(2));
				user.setEmail(querRslt.getString(4));
				user.setAvatarName(querRslt.getString(5));
				user.setActive(querRslt.getInt(6) == 0 ? false : true);
				user.setToken(querRslt.getString(7));
				user.setAdmin(querRslt.getInt(8) == 0 ? false : true);
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	
	public boolean isUserExistent(String user, String pass){
		boolean exists = false;
		
		PreparedStatement preparedStatement;
		ResultSet querRslt = null;
		try {
			preparedStatement = connect.prepareStatement("select id from users where name =? and pass = ?");
			preparedStatement.setString(1, user);
			preparedStatement.setString(2, pass);
			querRslt = preparedStatement.executeQuery();
			while(querRslt.next()) { 
				 exists = true;
			}
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return exists;
	}
	public boolean isUserExistent(String user){
		boolean exists = false;
		
		PreparedStatement preparedStatement;
		ResultSet querRslt = null;
		try {
			preparedStatement = connect.prepareStatement("select id from users where name =?");
			preparedStatement.setString(1, user);
			querRslt = preparedStatement.executeQuery();
			while(querRslt.next()) { 
				 exists = true;
			}
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return exists;
	}
	public boolean isTokenExistent(String user, String token){
		boolean exists = false;
		
		PreparedStatement preparedStatement;
		ResultSet querRslt = null;
		try {
			preparedStatement = connect.prepareStatement("select id from users where token =? and name = ?");
			preparedStatement.setString(1, token);
			preparedStatement.setString(2, user);
			querRslt = preparedStatement.executeQuery();
			while(querRslt.next()) { 
				 exists = true;
			}
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return exists;
	}
	public void saveUser(String username, String pass, String email, String heroName){
		PreparedStatement preparedStatement;
		UUID uniqueToken = UUID.randomUUID();
		try {
			preparedStatement = connect.prepareStatement("insert into users(name,pass,email,avatar,token) values(?,?,?,?,?)");
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, pass);
			preparedStatement.setString(3, email);
			preparedStatement.setString(4, heroName);
			preparedStatement.setString(5, uniqueToken.toString());
			preparedStatement.executeUpdate();
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void modificateUser(User user){
		PreparedStatement preparedStatement;
		UUID uniqueToken = UUID.randomUUID();
		try {
			preparedStatement = connect.prepareStatement("UPDATE users SET name=?,email=?,avatar=?,"
					+ "phone=?,sex=?,birthday=?,message=? WHERE id=?");
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getAvatar());
			preparedStatement.setString(4, user.getPhone());
			preparedStatement.setString(5, user.getSex());
			preparedStatement.setDate(6, user.getBirthday());
			preparedStatement.setString(7, user.getMessage());
			preparedStatement.setLong(8, user.getId());
			preparedStatement.executeUpdate();
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void activateUser(int id){
		PreparedStatement preparedStatement;
		
		try {
			preparedStatement = connect.prepareStatement("update users set activated = 1 where id = ?");
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	  // you need to close all three to make sure
	  private void close() {
		  try {
			resultSet.close();
			statement.close();
			 connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		  
	  }
	
}
