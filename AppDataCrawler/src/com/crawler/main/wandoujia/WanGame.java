﻿package com.crawler.main.wandoujia;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
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

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.crawler.util.DateUtil;


public class WanGame {

	/**
	 * 抓取豌豆荚游戏app的属性信息
	 * author:马德棚-18813095841-2015.6.16
	 * 
	 * 可配置爬虫：按名字，版本，抓取时间过滤，这样一个软件就会有多个ID,每天可以抓一次 。也可按名字，版本过滤，当有新版本的时候抓取数据。
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
	
	String content = null;
	String updatedDateStr = null;
	String author = null;
	String gameid = null;
	
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
	    	
			//Document everySortUrl=Jsoup.connect("http://www.wandoujia.com/tag/"+sorturls.get(i)).timeout(60000).get();
			//Elements eachapp=everySortUrl.getElementsByAttributeValue("class","app-desc");
			/*Elements eachapp = everySortUrl.select("div.app-desc");
			for(Element tags:eachapp){
				//Elements tag=tags.getElementsByTag("a").select(".name");
				Elements tag = tags.select("a.name");
				String eachAppUrl = tag.attr("abs:href");
				eachAppUrls.add(eachAppUrl);
			}*/
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
				int x = sorthrefs.size();
				System.out.println("软件分类有:"+x+"种");
				System.out.println("wandoujiagame");
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
					String str1 = json.getString(0);
					JSONObject jsonObj = JSONObject.fromObject(str1); 
					String str2 = jsonObj.get("apps").toString();
					if (str2.equals("[]")) {
						System.out.println("[] is true");
						System.out.println("test break");
						break;
					}
					//System.out.println(str2);
					
					JSONArray jsonarry = JSONArray.fromObject(str2);
					for (int k = 0; k < jsonarry.size(); k++) {
						//System.out.println(jsonarry.getString(i));
						String str3 = jsonarry.getString(k);
						JSONObject jsonobj = JSONObject.fromObject(str3);
						String str4 = jsonobj.get("packageName").toString();
						String appUrl = null;
						//System.out.println(str4);
						
						//每个app 的url
						appUrl = "http://www.wandoujia.com/apps/"+str4;
						//System.out.println(appUrl);
						
						//每个app对应的请求的评论的url
						String reviewsUrl = "http://apps.wandoujia.com/api/v1/comments/primary?packageName="+str4;
						
						//eachAppUrls.add(str4);
						getAppAttribution(appUrl,reviewsUrl);
						//getAppReviews(reviewsUrl);
					}} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch(JSONException e)
			   	 {
			   		 e.printStackTrace();
			   		 continue;
			   	 }catch (NullPointerException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			
			
		}
	}
	
	public void getAppAttribution(String url,String reviewsUrl){
	
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
			if (installNum.contains("万")) {
				//installNum = installNum.replace("万", "0000");
				Double r = Double.parseDouble(installNum.substring(0, installNum.length()-1))*10000;
				installNum = (new BigDecimal(r)).toString();
			}else if (installNum.contains("亿")) {
				Double r = Double.parseDouble(installNum.substring(0, installNum.length()-1))*1000000000;
				installNum = (new BigDecimal(r)).toString();
			}
			
			
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
				System.out.println(appName+"download succeed");
			 }catch (Exception e1) {
				e1.printStackTrace();
				System.out.println(appName+"download failed");
			 }
			 
			 //get database connection
			 Connection conn = getConnection();
			 
			 String sql = "select appname,version,crawledate from wandoujiaapp where appname='"+appName+"' and version='"+version+"' and crawledate='"+crawleDate+"'";
			 try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				if (!(rs.next())) {
					//插入数据库
					
					 java.sql.PreparedStatement insertSales = null;
					try {
						insertSales = conn.prepareStatement("insert into wandoujiaapp (appname,installnum,enjoynum,reviewnum,size,classification,updatetime,version,description,updateinfo,crawledate,downloadurl) values (?,?,?,?,?,?,?,?,?,?,?,?)");
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
					}
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 
			 
			 try {
					Document doc = Jsoup.connect(reviewsUrl).ignoreContentType(true).timeout(90000).get();
					String str = doc.body().text();
					JSONObject jsonObj = JSONObject.fromObject(str);
					
					
					//获得Comments部门的评论
					String comments =jsonObj.getString("comments");
					//System.out.println(comments);
					JSONArray jsonArray = JSONArray.fromObject(comments);
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject json = (JSONObject)jsonArray.get(i);
						content = json.getString("content");
						updatedDateStr = json.getString("updatedDateStr");
						JSONObject authorInfo = (JSONObject)json.get("author");
						author = authorInfo.getString("name");
						
						 String sql1 = "select author,updateddatestr from wandoujiareviews where author='"+author+"' and updateddatestr='"+updatedDateStr+"'";
						 String sql2 ="select id from wandoujiaapp where appname='"+appName+"'";
						 try {
							 Statement stmt = conn.createStatement();
							 ResultSet rs1 = stmt.executeQuery(sql2);
								
								while (rs1.next()) {
									gameid = rs1.getString(1);
								}
							
							ResultSet rs = stmt.executeQuery(sql1);
							
						   
							if (!(rs.next())) {
								//插入数据库
								
								 java.sql.PreparedStatement insertSales = null;
								try {
									insertSales = conn.prepareStatement("insert into wandoujiareviews (author,updateddatestr,reviewcontent,appid) values (?,?,?,?)");
									 insertSales.setString(1, author);
									 insertSales.setString(2, updatedDateStr);
									 insertSales.setString(3, content);
									 insertSales.setString(4, gameid);
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
					
					//获得superiorComments部门的评论
					String superiorComments =jsonObj.getString("superiorComments");
					//System.out.println(superiorComments);
					JSONArray jsonArray1 = JSONArray.fromObject(superiorComments);
					for (int j = 0; j < jsonArray1.size(); j++) {
						JSONObject json1 = (JSONObject)jsonArray1.get(j);
						content = json1.getString("content");
						updatedDateStr = json1.getString("updatedDateStr");
						JSONObject authorInfo = (JSONObject)json1.get("author");
						author = authorInfo.getString("name");
						
						 String sql1 = "select author,updateddatestr from wandoujiareviews where author='"+author+"' and updateddatestr='"+updatedDateStr+"'";
						 try {
							 Statement stmt = conn.createStatement();
							 ResultSet rs = stmt.executeQuery(sql1);
						   
							if (!(rs.next())) {
								//插入数据库
								
								 java.sql.PreparedStatement insertSales = null;
								try {
									insertSales = conn.prepareStatement("insert into wandoujiareviews (author,updateddatestr,reviewcontent,appid) values (?,?,?,?)");
									 insertSales.setString(1, author);
									 insertSales.setString(2, updatedDateStr);
									 insertSales.setString(3, content);
									 insertSales.setString(4, gameid);
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
					
					
				} catch (IOException e) {
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
	
	
	   public static  Connection getConnection(){  
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
	
