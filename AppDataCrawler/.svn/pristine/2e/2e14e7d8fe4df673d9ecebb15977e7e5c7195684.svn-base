package com.crawler.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestSql {

	//数据库配置
		private static final String DRIVERNAME="com.mysql.jdbc.Driver";  
	    private static final String URL="jdbc:mysql://localhost:3306/appdata?useUnicode=true&characterEncoding=utf-8";  
	    private static final String USERNAME="root";  
	    private static final String PASSWORD="root"; 
	    
	    public static Connection getConnection(){  
	        Connection conn=null;  
	          
	        try {  
	            Class.forName(DRIVERNAME);  
	            conn=DriverManager.getConnection(URL,USERNAME,PASSWORD);  
	              
	            //System.out.print(conn.toString());  
	        } catch (ClassNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (SQLException e) {  
	            e.printStackTrace();  
	        }         
	        return conn;  
	    }//Connection
	    
	    
	    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			
		Connection conn = getConnection();
		String s= "insert into gamereviews (gameid) values('689') WHERE not exists (select gameid from gamereviews)";
		String sql = "insert into gamereviews(gameid) select id from wandoujiagame";
		String sql1 = "select id from wandoujiagame";
		
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(s);
			//stmt.executeUpdate(sql);
			ResultSet rs = stmt.executeQuery(sql1);
			
			while (rs.next()) {
				System.out.println(rs.getString(1));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
