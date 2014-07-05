package com.dota.db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.dota.tournament.User;


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
	 
	 public DBConnection(String host, String user, String pass, String database, String port) throws SQLException{
		 this.user = user;
		 this.pass = pass;
		 this.host = host;
		 this.db = database;
		 this.port = port;
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
		User user = new User();
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
			}
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	public User getUserByToken(String userName, String token){
		User user = new User();
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
			}
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	public User getUser(long id){
		User user = new User();
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
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	public User getUser(String token){
		User user = new User();
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
				User user = new User();
				user.setId(querRslt.getLong(1));
				user.setName(querRslt.getString(2));
				user.setEmail(querRslt.getString(4));
				user.setAvatarName(querRslt.getString(5));
				user.setActive(querRslt.getInt(6) == 0 ? false : true);
				user.setToken(querRslt.getString(7));
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
	
	public void example() throws Exception{
		 try {
		 // statements allow to issue SQL queries to the database
	      statement = connect.createStatement();
	      // resultSet gets the result of the SQL query
	      resultSet = statement
	          .executeQuery("select * from FEEDBACK.COMMENTS");

	      // preparedStatements can use variables and are more efficient
	      preparedStatement = connect
	          .prepareStatement("insert into  FEEDBACK.COMMENTS values (default, ?, ?, ?, ? , ?, ?)");
	      // "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
	      // parameters start with 1
	      preparedStatement.setString(1, "Test");
	      preparedStatement.setString(2, "TestEmail");
	      preparedStatement.setString(3, "TestWebpage");
	      preparedStatement.setString(5, "TestSummary");
	      preparedStatement.setString(6, "TestComment");
	      preparedStatement.executeUpdate();

	      preparedStatement = connect
	          .prepareStatement("SELECT myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
	      resultSet = preparedStatement.executeQuery();

	      // remove again the insert comment
	      preparedStatement = connect
	      .prepareStatement("delete from FEEDBACK.COMMENTS where myuser= ? ; ");
	      preparedStatement.setString(1, "Test");
	      preparedStatement.executeUpdate();
	      
	      resultSet = statement
	      .executeQuery("select * from FEEDBACK.COMMENTS");
		 } catch (Exception e) {
		      throw e;
		 } finally {
		      close();
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
