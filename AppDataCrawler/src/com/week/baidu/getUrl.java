package com.week.baidu;
import java.awt.List;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.crawler.util.DateUtil;

public class getUrl {
	/**
	 * 抓取360手机市场app的属性信息
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
	     java.util.List<String> appList=new ArrayList<String>();
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
				appList.add(apprs.getString(2));
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		for(int i=0;i<appList.size();i++){
		try {
			
		    Document doc = Jsoup.connect("http://shouji.baidu.com/s?wd="+appList.get(i)+"&data_type=app&f=header_app%40input&from=landing").timeout(9000).get();
		    System.out.println(appList.get(i));		    
			Element elem=doc.select("div.search-res").select("div.s-special-type").select("div.info").select("div.middle").last();
//			System.out.println(elem);
			
			if(elem!=null){
				String[] elem1=elem.select("span.star-big").select("span").attr("style").split(":");
				 rating =elem1[1].replace(";", "").replace("%","");
				 elem2=elem.select("span.down-num").text();
				 if(elem2.contains("亿")){
					Double r=Double.parseDouble(elem2.substring(0, elem2.length()-3))*100000000;
					downloadnum=(new BigDecimal(r).toString());
					System.out.println(downloadnum);
				}
				if(elem2.contains("万")){
					Double r=Double.parseDouble(elem2.substring(0, elem2.length()-3))*10000;
					downloadnum=(new BigDecimal(r).toString());
					System.out.println(downloadnum);
				}
				
			}
			else{
				elem=doc.select("div.search-res").select("ul.app-list").select("li").first();	
				String[] elem1=elem.select("div.info").select("div.middle").last().select("span.star").select("span").attr("style").split(":");
			    rating =elem1[1].replace(";", "").replace("%", "");
				elem2=elem.select("div.info").select("div.middle").last().select("em").text();
//				System.out.println(elem2);			
				if(elem2.contains("亿")){
					Double r=Double.parseDouble(elem2.substring(0, elem2.length()-4))*100000000;
					downloadnum=(new BigDecimal(r).toString());
					System.out.println(downloadnum);
				}
				if(elem2.contains("万")){
					Double r=Double.parseDouble(elem2.substring(0, elem2.length()-4))*10000;
					downloadnum=(new BigDecimal(r).toString());
					System.out.println(downloadnum);
				}
			}
//			System.out.println(rating);
			 //抓取时间
			 Date date = new Date();
//			 DateUtil dateUtil = new DateUtil();
			 //String crawleDate = DateUtil.formatStandardDateTime(date);
			 String crawleDate = DateUtil.formatStandardDate(date);
			 String sql = "select appname,crawledate from baidu where appname='"+appList.get(i)+"' and crawledate='"+crawleDate+"'";
			 try {
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(sql);
					if (!(rs.next())) {
						//插入数据库
						
						 java.sql.PreparedStatement insertSales = null;
						try {
							insertSales = conn.prepareStatement("insert into baidu (appname,rating,downloadnum,crawledate) values (?,?,?,?)");
							 insertSales.setString(1, appList.get(i));
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
				

