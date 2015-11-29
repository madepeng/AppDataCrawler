package com.week.yingyongbao;
import java.awt.List;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.crawler.util.DateUtil;
import com.google.gson.JsonArray;

public class getUrl {
	/**
	 * 抓取优亿手机市场app的属性信息
	 * author:wangchao-2015.7.17
	 * 
	 *可配置爬虫：按名字，版本，抓取时间过滤，这样一个软件就会有多个ID,每天可以抓一次 。也可按名字，版本过滤，当有新版本的时候抓取数据。
	 * @param args
	 */
	//数据库配置
		private static final String DRIVERNAME="com.mysql.jdbc.Driver";  
	    private static final String URL="jdbc:mysql://localhost:3306/appdata?useUnicode=true&characterEncoding=utf-8";  
	    private static final String USERNAME="root";  
	    private static final String PASSWORD="root"; 
	    java.util.List<String> appmap=new ArrayList<String>();
	    String elem2;
	    String rating;
	    String downloadnum;
	    public static Connection getConnection(){
	    	Connection conn=null;
	    	
	    	try {
	    		Class.forName(DRIVERNAME);
				conn=DriverManager.getConnection(URL, USERNAME, PASSWORD);
	    	} catch (ClassNotFoundException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    	
			return conn;   
	    }

	public void getappUrl() {
		// TODO Auto-generated method stub
		Connection conn=getConnection();
		String appsql="select * from baidurating";
		try{
			Statement stmt = conn.createStatement();
			ResultSet apprs=stmt.executeQuery(appsql);	
			while(apprs.next()){
				appmap.add(apprs.getString(2));
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		for(int i=0;i<appmap.size();i++){
		try {
			String ss = URLEncoder.encode(appmap.get(i));
		    Document doc = Jsoup.connect("http://android.myapp.com/myapp/searchAjax.htm?kw="+ss).ignoreContentType(true).ignoreHttpErrors(true).timeout(90000).get();
//		    System.out.println(doc);
		    System.out.println(appmap.get(i));		    
		    String str=doc.body().text();
		    JSONObject json = JSONObject.fromObject(str); 
			String str1=json.get("obj").toString();
			 JSONObject json1 = JSONObject.fromObject(str1); 
				String str2=json1.get("appDetails").toString();
			JSONArray json2 = JSONArray.fromObject(str2); 
			String str3=json2.getString(0);
//			System.out.println(str3);
			 JSONObject json3 = JSONObject.fromObject(str3); 
			downloadnum=json3.get("appDownCount").toString();
			String rating=json3.get("averageRating").toString().substring(0, 3);
			System.out.println(downloadnum);									
			System.out.println(rating);
			 //抓取时间
			 Date date = new Date();
//			 DateUtil dateUtil = new DateUtil();
			 //String crawleDate = DateUtil.formatStandardDateTime(date);
			 String crawleDate = DateUtil.formatStandardDate(date);
			 String sql = "select appname,crawledate from yingyongbao where appname='"+appmap.get(i)+"' and crawledate='"+crawleDate+"'";
			 try {
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(sql);
					if (!(rs.next())) {
						//插入数据库
						
						 java.sql.PreparedStatement insertSales = null;
						try {
							insertSales = conn.prepareStatement("insert into yingyongbao (appname,rating,downloadnum,crawledate) values (?,?,?,?)");
							 insertSales.setString(1, appmap.get(i));
							 insertSales.setString(2, rating);
							 insertSales.setString(3, downloadnum);
							 insertSales.setString(4, crawleDate);
							 insertSales.executeUpdate();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}			
			}
					
	}catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("第"+(i+1)+"个app更新完成！");
		}
	}
}
				

