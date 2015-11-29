package com.crawler.main.sanliuling;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.crawler.util.DateUtil;

public class getInfo {
	
	 String appid = null;
		
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
	              
	           // System.out.print(conn.toString());  
	        } catch (ClassNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (SQLException e) {  
	            e.printStackTrace();  
	        }         
	        return conn;  
	    }//Connection
	    
	    
	public void get(String appUrl) {
		
		try {
			Document doc = Jsoup.connect(appUrl).timeout(9000).get();
			
			String pname = doc.select("div.app-item").attr("data-pname");
			
			String appName = doc.select("h3").text();
			
			String ratingOrignal = doc.select("p.star").select("span").attr("style");
			String rating = ratingOrignal.substring(6, ratingOrignal.length()-1);
			
			String downloadNumOrignal = doc.select("p.desc").text();
			Integer downloadNum = Integer.parseInt(downloadNumOrignal.substring(1, downloadNumOrignal.length()-4).trim())*10000;
			
			String downloadUrl = doc.select("div.hsd-btn-con").select("a").attr("href");
			
			Elements elements = doc.select("ul.app-ver").select("li");
			
			String version = elements.eq(0).text().split("：")[1];
			
			String size = elements.eq(1).text().split("：")[1];
			
			String updateTime = elements.eq(3).text().split("：")[1];
			
			 //抓取时间
			 Date date = new Date();
			 DateUtil dateUtil = new DateUtil();
			 //String crawleDate = DateUtil.formatStandardDateTime(date);
			 String crawleDate = DateUtil.formatStandardDate(date);
			 
			 //get database connection
			 Connection conn = getConnection();
			 
			 String sql = "select appname,version,crawledate from sanliulingapp where appname='"+appName+"' and version='"+version+"' and crawledate='"+crawleDate+"'";
			 
			 try {
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(sql);
					if (!(rs.next())) {
						//插入数据库
						
						 java.sql.PreparedStatement insertSales = null;
						try {
							insertSales = conn.prepareStatement("insert into sanliulingapp (appname,rating,size,version,downloadnum,downloadurl,crawledate,updatetime) values (?,?,?,?,?,?,?,?)");
							 insertSales.setString(1, appName);
							 insertSales.setString(2, rating);
							 insertSales.setString(3, size);
							 insertSales.setString(4, version);
							 insertSales.setInt(5, downloadNum);
							 insertSales.setString(6, downloadUrl);
							 insertSales.setString(7, crawleDate);
							 insertSales.setString(8, updateTime);
							 insertSales.executeUpdate();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			
			
			//评论
				try {
					int flag = 0;
					String appname = URLEncoder.encode(appName, "UTF-8");
					String[] type = {"best","good","bad"};
					for (int m = 0; m < type.length; m++) {
						for (int  i= 0;; i+=10) {
                    	
							
						if (m==0) {
							flag = 0;
						}else if (m==1) {
							flag=1;
						}else {
							flag=2;
						}
                    		
                    	try {					
                    	
									String reviewsUrl = "http://intf.baike.360.cn/index.php?name="+appname+"+Android_"+pname+"&c=message&a=getmessage&start="+i+"&count=10&type="+type[m];
									
									Document docReviews = Jsoup.connect(reviewsUrl).timeout(90000).ignoreContentType(true).ignoreHttpErrors(true).get();
									String str = docReviews.body().text();
									//System.out.println(str);
									JSONObject jsonObj = JSONObject.fromObject(str); 
									String str1 = jsonObj.get("data").toString();
									//System.out.println(str1);
									if (str1.equals("false")) {
										break;
									}
									
									JSONObject jsonObj1 = JSONObject.fromObject(str1); 
									String total = jsonObj1.get("total").toString();
									//System.out.println(total);
									String str2 = jsonObj1.get("messages").toString();
									if (str2.equals("[]")) {
										System.out.println("end");
										break;
									}
									//System.out.println(str2);
									
									JSONArray jsonArray1 = JSONArray.fromObject(str2); 
									
									//数组
									int size1 = jsonArray1.size();
									//System.out.println(size1);
									for (int j = 0; j < size1; j++) {
										
										String str3 = jsonArray1.getString(j);
										System.out.println(str3);
										
										JSONObject str4 = JSONObject.fromObject(str3);
										String create_time = str4.getString("create_time");
										//System.out.println(create_time);
										String score = str4.getString("score");
										//System.out.println(score);
										String content = str4.getString("content");
										//System.out.println(content);
										String username = str4.getString("username");
										//System.out.println(username);
										
										
				
										 String sql1 = "select reviewer,content,commenttime from sanliulingreviews where reviewer='"+username+"' and content='"+content+"' and commenttime='"+create_time+"'";
										 String sql2 ="select id from sanliulingapp where appname='"+appName+"'";
										 try {
											 Statement stmt = conn.createStatement();
											 ResultSet rs1 = stmt.executeQuery(sql2);
												
												while (rs1.next()) {
													appid = rs1.getString(1);
												}
											
											ResultSet rs = stmt.executeQuery(sql1);
											
										   
											if (!(rs.next())) {
												//插入数据库
												
												 java.sql.PreparedStatement insertSales = null;
												try {
													 insertSales = conn.prepareStatement("insert into sanliulingreviews (reviewer,content,commenttime,score,flag,appid) values (?,?,?,?,?,?)");
													 insertSales.setString(1, username);
													 insertSales.setString(2, content);
													 insertSales.setString(3, create_time);
													 insertSales.setString(4, score);
													 insertSales.setInt(5, flag);
													 insertSales.setString(6, appid);
													 insertSales.executeUpdate();
												} catch (SQLException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											}
										} catch (SQLException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
										 
										
									}
					
                    }catch (JSONException e) {
    					// TODO: handle exception
   					 e.printStackTrace();
   			   		 continue;
   				}	
                    }}
                    
                    
                    
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	

}
