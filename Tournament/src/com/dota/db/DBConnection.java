package com.dota.db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


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
	 
	 public DBConnection(String host, String user, String pass, String database, String port){
		 this.user = user;
		 this.pass = pass;
		 this.host = host;
		 this.db = database;
		 this.port = port;
		 try {
			 Class.forName("com.mysql.jdbc.Driver");
			 connect = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+db,user, pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
	     
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
