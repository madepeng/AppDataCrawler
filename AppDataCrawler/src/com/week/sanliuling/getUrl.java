package com.week.sanliuling;
import java.awt.List;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
		/*System.out.println(appmap.keySet());
		String[] temp=appmap.keySet().toString().split(",");	
		String a=temp[0].replace("[", "");
		temp[0]=a;
		String b=temp[99].replace("]", "");
		temp[99]=b;*/
		try{	
				for(int i=0;i<appList.size();i++){
				Document doc=Jsoup.connect("http://zhushou.360.cn/search/index/?kw="+appList.get(i)).timeout(900000).get();
				Element elem=doc.select("div.SeaCon").select("ul").select("li").first();
				String[] elem1=elem.select("div.seaDown").select("div.sdlft").text().split(" ");
				String rating=elem1[0].replace("分", "");
				String downloadnum=elem1[1].replace("次下载", "");
				 //抓取时间
				 Date date = new Date();
				 DateUtil dateUtil = new DateUtil();
				 //String crawleDate = DateUtil.formatStandardDateTime(date);
				 String crawleDate = DateUtil.formatStandardDate(date);
				 String sql = "select appname,crawledate from sanliuling where appname='"+appList.get(i)+"' and crawledate='"+crawleDate+"'";
//				 System.out.println(sql);
				 try {
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(sql);
					if (!(rs.next())) {
						//插入数据库
						
						 java.sql.PreparedStatement insertSales = null;
						try {
							insertSales = conn.prepareStatement("insert into sanliuling (appname,rating,downloadnum,crawledate) values (?,?,?,?)");
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
				 System.out.println("第"+(i+1)+"个app更新完成！");
}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
