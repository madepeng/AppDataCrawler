package com.crawler.main.youyi;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.crawler.util.DateUtil;

public class Testapp {
	
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
    
    
	
	public void getAppInfo(String AppUrl,String rating) {
		
		try {
			Document doc = Jsoup.connect(AppUrl).ignoreContentType(true).ignoreHttpErrors(true).timeout(9000).get();
			
			String appName = doc.select("span.name_appintr").text();
			
			
			//String info = doc.getElementsByAttributeValue("class", "info_appintr clearfix").select("span.clearfix").select("em").text();
			String size = doc.select("span.clearfix").eq(2).select("em").first().text();
			size = size.split("：")[1];
			System.out.println(size);
			
			String version = doc.select("span.clearfix").eq(2).select("em").eq(1).text();
			version = version.split("：")[1];
			System.out.println(version);
			
			String downloadNum = doc.select("span.clearfix").eq(2).select("em").eq(2).text();
			downloadNum = downloadNum.split("：")[1];
			System.out.println(downloadNum);
			
			String updateTime = doc.select("span.clearfix").eq(3).select("em").eq(2).text();
			updateTime = updateTime.split("：")[1];
			System.out.println(updateTime);
			
			String downloadUrl = doc.select("span.download_intr").select("a").attr("href");
			System.out.println(downloadUrl);
			
			 //抓取时间
			 Date date = new Date();
			 DateUtil dateUtil = new DateUtil();
			 //String crawleDate = DateUtil.formatStandardDateTime(date);
			 String crawleDate = DateUtil.formatStandardDate(date);
			 
			 //get database connection
			 Connection conn = getConnection();
			 
			 String sql = "select appname,version,crawledate from youyiapp where appname='"+appName+"' and version='"+version+"' and crawledate='"+crawleDate+"'";
			 try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				if (!(rs.next())) {
					//插入数据库
					
					 java.sql.PreparedStatement insertSales = null;
					try {
						insertSales = conn.prepareStatement("insert into youyiapp (appname,rating,size,version,downloadnum,downloadurl,crawledate) values (?,?,?,?,?,?,?)");
						 insertSales.setString(1, appName);
						 insertSales.setString(2, rating);
						 insertSales.setString(3, size);
						 insertSales.setString(4, version);
						 insertSales.setString(5, downloadNum);
						 insertSales.setString(6, downloadUrl);
						 insertSales.setString(7, crawleDate);
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
			 for (int p = 1;; p++) {
				 
				
					Document reviewContent = Jsoup.connect(AppUrl+"?page="+p).timeout(90000).get();
					//System.out.println(reviews);
					Elements elements = reviewContent.getElementsByAttributeValue("class", "comment_item clearfix");
					if (elements.size() == 0) {
						break;
					}
					//System.out.println(eles);
					for (Element element : elements) {
						String reviews = element.select("span.comment_meg").text();
						String commentTime = element.select("span.comment_infoc").select("em").eq(1).text();
						

						 String sql1 = "select commenttime from youyireviews where  commenttime='"+commentTime+"'";
						 String sql2 ="select id from youyiapp where appname='"+appName+"'";
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
									 insertSales = conn.prepareStatement("insert into youyireviews (content,commenttime,appid) values (?,?,?)");
									 insertSales.setString(1, reviews);
									 insertSales.setString(2, commentTime);
									 insertSales.setString(3, appid);
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
				}
			 try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		


} catch (IOException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
}




			 
			
	
		
	}

	

