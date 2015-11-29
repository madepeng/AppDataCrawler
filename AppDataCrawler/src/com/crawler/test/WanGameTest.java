﻿package com.crawler.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.RestoreAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.crawler.util.DateUtil;
import com.mysql.jdbc.PreparedStatement;


public class WanGameTest {

	/**
	 * 抓取豌豆荚游戏app的属性信息
	 * author:马德棚-18813095841-2015.6.16
	 * @param args
	 */
	
	//数据库配置
	private static final String DRIVERNAME="com.mysql.jdbc.Driver";  
    private static final String URL="jdbc:mysql://localhost:3306/appdata?useUnicode=true&characterEncoding=utf-8";  
    private static final String USERNAME="root";  
    private static final String PASSWORD="root"; 
    
    
    
	//游戏链接
	String url = "http://www.wandoujia.com/tag/game";
	
	//所有分类的href
	List<String> sorthrefs=new ArrayList<String>();
	
	//url的中文后缀
	List<String> sorturls=new ArrayList<String>();
	
	//每一个app 的url
	List<String> eachAppUrls=new ArrayList<String>();
	
	List<String> eachPageApp=new ArrayList<String>();
	
	String appName = null;
	String installNum = null;
	String enjoyNum = null;
	String reviewNum = null;
	String size = null;
	String classification= null;
	String updateTime = null;
	String version = null;
	String description = null;
	String updateInfo = null;
	String downloadUrl = null;
	
     //获取软件分类中所有类别的url
	public void getAppSort() throws IOException{
		
		Document doc=Jsoup.connect(url).timeout(200000000).get();
		Element content=doc.getElementsByAttributeValue("class", "clearfix tag-box").first();
		//Elements links=content.getElementsByTag("a").select(".cate-link");
		Elements links= content.select("a.cate-link");
		
		for(Element link: links){
			String sorthref=link.attr("abs:href");
			sorthrefs.add(sorthref);
		}
		
		int x = sorthrefs.size();
		System.out.println("软件分类有:"+x+"种");
		
		for(int i=0;i<x;i++){
			
		   String sorturl=sorthrefs.get(i);
           String s1 = URLEncoder.encode(sorturl.split("tag/")[1], "utf-8");
		   sorturls.add(s1);
		}
	} 

	//获取所有app的地址
	public void getAllAppUrl(){
		
		try {
			getAppSort();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		for(int i=0;i<sorturls.size();i++){
			
			for (int j = 0;; j+=24) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String currentUrl = "http://apps.wandoujia.com/api/v1/apps?ads_count=0&tag="+sorturls.get(i)+"&max=24&start="+j+"&opt_fields=apps.likesCount,apps.reason,apps.ad,apps.title,apps.packageName,apps.apks.size,apps.icons.px68,apps.apks.superior,apps.installedCountStr,apps.snippet,apps.editorComment,apps.apks.versionCode";
				System.out.println(currentUrl);
				//记录i，j的值，是为了记录中断的位置，直接从中断的位置开始执行就行
				System.out.println("i="+i);
				System.out.println("j="+j);
				try {
					/*Response response = Jsoup.connect(currentUrl).timeout(3000000).ignoreHttpErrors(true).ignoreContentType(true).execute();
					int statusCode = response.statusCode();
					System.out.println(statusCode);*/
					 Response response = Jsoup.connect(currentUrl).timeout(3000000).ignoreHttpErrors(true).ignoreContentType(true).execute();
					 int statusCode = response.statusCode();
					 if (statusCode == 404) {
						break;
					}   
					 
					Document doc = Jsoup.connect(currentUrl).timeout(90000).ignoreContentType(true).ignoreHttpErrors(true).get();
					String str = doc.body().text();
					JSONArray json = JSONArray.fromObject(str); 
					//System.out.println(json);
					String str1 = json.getString(0);
					JSONObject jsonObj = JSONObject.fromObject(str1); 
					//System.out.println(jsonObj);
					//System.out.println(jsonObj.get("apps"));
					String str2 = jsonObj.get("apps").toString();
					if (str2.equals("[]")) {
						System.out.println("[] is true");
						System.out.println("test break");
						break;
					}
					System.out.println(str2);
					
					JSONArray jsonarry = JSONArray.fromObject(str2);
					for (int k = 0; k < jsonarry.size(); k++) {
						//System.out.println(jsonarry.getString(i));
						String str3 = jsonarry.getString(k);
						JSONObject jsonobj = JSONObject.fromObject(str3);
						String str4 = jsonobj.get("packageName").toString();
						System.out.println(str4);
						str4 = "http://www.wandoujia.com/apps/"+str4;
						System.out.println(str4);
						//eachAppUrls.add(str4);
						getAppAttribution(str4);
					}} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/* catch(JSONException e)
			   	 {
			   		 e.printStackTrace();
			   		 continue;
			   	 }*/
			}
			
			
		}
	}
	
	public void getAppAttribution(String url){
	
			Document docs = null;
			try {
				docs = Jsoup.connect(url).timeout(90000).get();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			//获取名字
			appName=docs.select("span.title").first().text();
			
			//获取下载量
			installNum = docs.select("span.item").first().select("i").text();
			
			
			 //获取喜欢数
			// enjoyNum=docs.select(".item").eq(1).select("i").text();
			enjoyNum = docs.getElementsByAttributeValue("class", "item love").select("i").text();
			
			 //获取评论数
			 reviewNum =docs.select(".item").eq(2).select("i").text();
			 
			 //获取大小
			 size = docs.select("dl.infos-list").first().select("dd").first().text();
			 
   //所属类别
			 classification=docs.select("dd.tag-box").first().text();   
			 
			 //更新时间
			 updateTime=docs.select("time[datetime]").first().text();
			 
			 //版本号
			 version=docs.select("dd").eq(3).text();
			 
			 //描述
			 description=docs.select("div.con").eq(1).text();
			 
			 //更新内容
			 updateInfo=docs.select("div.con").eq(2).text();
			 
			 //抓取时间
			 Date date = new Date();
			 DateUtil dateUtil = new DateUtil();
			 //String crawleDate = DateUtil.formatStandardDateTime(date);
			 String crawleDate = DateUtil.formatStandardDate(date);
			 
			 //获取下载地址
			 downloadUrl = docs.getElementsByAttributeValue("class", "install-btn").attr("href");
			 
			 //下载app到电脑上
			 try {
				//download(downloadUrl, appName+".apk");
				//downLoadFromUrl(downloadUrl, appName+".apk", "D:/wandoujia/game");
				System.out.println(appName+"download succeed");
			 }catch (Exception e1) {
				e1.printStackTrace();
				System.out.println(appName+"download failed");
			 }
			 
			 //get database connection
			 Connection conn = getConnection();
			 
			 String sql = "select appname,version,crawledate from wandoujiagame where appname='"+appName+"' and version='"+version+"' and crawledate='"+crawleDate+"'";
			 try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				if (!(rs.next())) {
					//插入数据库
					
					 java.sql.PreparedStatement insertSales = null;
					try {
						insertSales = conn.prepareStatement("insert into wandoujiagame (appname,installnum,enjoynum,reviewnum,size,classification,updatetime,version,description,updateinfo,crawledate,downloadurl) values (?,?,?,?,?,?,?,?,?,?,?,?)");
						 insertSales.setString(1, appName);
						 insertSales.setString(2, installNum);
						 insertSales.setString(3, enjoyNum);
						 insertSales.setString(4, reviewNum);
						 insertSales.setString(5, size);
						 insertSales.setString(6, classification);
						 insertSales.setString(7, updateTime);
						 insertSales.setString(8, version);
						 insertSales.setString(9, description);
						 insertSales.setString(10, updateInfo);
						 insertSales.setString(11, crawleDate);
						 insertSales.setString(12, downloadUrl);
						 insertSales.executeUpdate();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						try {
							conn.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 
}     
	
	
	public static void  downLoadFromUrl(String urlStr,String fileName,String savePath) throws IOException{  
        URL url = new URL(urlStr);    
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();    
                //设置超时间为3秒  
        conn.setConnectTimeout(3*1000);  
        //防止屏蔽程序抓取而返回403错误  
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
  
        //得到输入流  
        InputStream inputStream = conn.getInputStream();    
        //获取自己数组  
        byte[] getData = readInputStream(inputStream);      
  
        //文件保存位置  
        File saveDir = new File(savePath);  
        if(!saveDir.exists()){  
            saveDir.mkdir();  
        }  
        File file = new File(saveDir+File.separator+fileName);      
        FileOutputStream fos = new FileOutputStream(file);       
        fos.write(getData);   
        if(fos!=null){  
            fos.close();    
        }  
        if(inputStream!=null){  
            inputStream.close();  
        }  
  
  
        System.out.println("info:"+url+" download success");   
  
    }  
	
	public static  byte[] readInputStream(InputStream inputStream) throws IOException {    
        byte[] buffer = new byte[1024];    
        int len = 0;    
        ByteArrayOutputStream bos = new ByteArrayOutputStream();    
        while((len = inputStream.read(buffer)) != -1) {    
            bos.write(buffer, 0, len);    
        }    
        bos.close();    
        return bos.toByteArray();    
    }    
	
	
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
	    

		
		
	}
	

