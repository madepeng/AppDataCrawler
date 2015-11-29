package com.crawler.main.baidu;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.crawler.util.DateUtil;

public class getUrl{
	//数据库配置
		private static final String DRIVERNAME="com.mysql.jdbc.Driver";  
	    private static final String URL="jdbc:mysql://localhost:3306/appdata?useUnicode=true&characterEncoding=utf-8";  
	    private static final String USERNAME="root";  
	    private static final String PASSWORD="root"; 
	    
	    String appid = null;
	    
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
	
		public  void getUrl() {
			
			
			try {
				Document doc = Jsoup.connect("http://shouji.baidu.com/software/?from=landing").timeout(900000).get();
				Elements eles = doc.select("div#doc").select("ul.cate-body").select("li").select("a");
				//System.out.println("共有"+eles.size()+"分类");
				int m = eles.size();
				for (int j = 0; j < m; j++) {
					//System.out.println(element.attr("abs:href"));
					//记录程序中断的位置
					
					Element element = eles.get(j);
					String url = element.attr("abs:href");
					
						for (int  i= 1;; i++) {
							//记录程序中断位置
							
							Document doc1 = Jsoup.connect(url+"&page_num="+i).timeout(900000).get();
							Elements eles1 = doc1.select("div.app-box").select("a");
							//System.out.println("每一页共有"+eles1.size()+"个软件");
							if (eles1.size() ==0) {
								break;
							}
							int n = eles1.size();
							for (int k = 0; k < n; k++)						
							 {
								//记录程序中断位置
								System.out.println("app sort j="+j);
								System.out.println("app page i="+i);
								System.out.println("app k="+k);
								Element element1 = eles1.get(k);
								//System.out.println(element1.attr("abs:href"));
								
								//每一个app的url
								String appurl = element1.attr("abs:href");
								
								Response response = Jsoup.connect(appurl).timeout(3000000).ignoreHttpErrors(true).ignoreContentType(true).execute();
								 int statusCode = response.statusCode();
								 if (statusCode == 504 || statusCode == 502) {
									    try {
											Thread.sleep(120000);
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										Response response1 = Jsoup.connect(appurl).timeout(3000000).ignoreHttpErrors(true).ignoreHttpErrors(true).execute();
								    	int statusCode1 = response1.statusCode();
								    	if(statusCode1 == 504 || statusCode1 == 502)
								    		continue;
								    
								}   
								 
								Document doc2 = Jsoup.connect(appurl).timeout(900000).get();
								
								//每个app页面的groupid
								String groupid = doc2.select("div.app-intro").select("input").attr("value");
								
								String appName = doc2.select("h1.app-name").text();
								System.out.println(appName);
								
								String rating = doc2.select("span.star-percent").attr("style");
								String[] ratings = rating.split(":");
								if (ratings.length == 2) {
									rating = ratings[1];
								}
								rating = rating.substring(0, rating.length()-1);
																
								String size = doc2.select("span.size").first().text().split(":")[1];
								
								String version = null;
								String[] versionorign = doc2.select("span.version").text().split(":");
								if (versionorign.length == 2) {
									version = versionorign[1];
								}
								//String version = doc2.select("span.version").text().split(":")[1];
								
								String downloadNum = doc2.select("span.download-num").text().split(":")[1];
								if (downloadNum.contains("万")) {
									//downloadNum = downloadNum.replace("万", "0000");
									Double r = Double.parseDouble(downloadNum.substring(0, downloadNum.length()-1))*10000;
									downloadNum = (new BigDecimal(r)).toString();
								}else if (downloadNum.contains("亿")) {
									Double r = Double.parseDouble(downloadNum.substring(0, downloadNum.length()-1))*100000000;
									downloadNum = (new BigDecimal(r)).toString();
								}
								
								
								String downloadUrl = doc2.select("a.apk").attr("href");
								
								 //抓取时间
								 Date date = new Date();
								 DateUtil dateUtil = new DateUtil();
								 //String crawleDate = DateUtil.formatStandardDateTime(date);
								 String crawleDate = DateUtil.formatStandardDate(date);
								 
							
								 
								 //get database connection
								 Connection conn = getConnection();
								 
								 String sql = "select appname,version,crawledate from baiduapp where appname='"+appName+"' and version='"+version+"' and crawledate='"+crawleDate+"'";
								 try {
									Statement stmt = conn.createStatement();
									ResultSet rs = stmt.executeQuery(sql);
									if (!(rs.next())) {
										//插入数据库
										
										 java.sql.PreparedStatement insertSales = null;
										try {
											insertSales = conn.prepareStatement("insert into baiduapp (appname,rating,size,version,downloadnum,downloadurl,crawledate) values (?,?,?,?,?,?,?)");
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
									 
									 Response response1 = Jsoup.connect("http://shouji.baidu.com/comment?action_type=getCommentList&groupid="+groupid+"&pn="+p).timeout(3000000).ignoreHttpErrors(true).ignoreContentType(true).execute();
									 int statusCode1 = response1.statusCode();
									 if (statusCode1 == 504 || statusCode1 == 502) {
										    try {
												Thread.sleep(120000);
											} catch (InterruptedException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											Response response11 = Jsoup.connect("http://shouji.baidu.com/comment?action_type=getCommentList&groupid="+groupid+"&pn="+p).timeout(3000000).ignoreHttpErrors(true).ignoreHttpErrors(true).execute();
									    	int statusCode11 = response11.statusCode();
									    	if(statusCode11 == 504 || statusCode11 == 502)
									    		continue;
									    
									}   
									 
										Document reviews = Jsoup.connect("http://shouji.baidu.com/comment?action_type=getCommentList&groupid="+groupid+"&pn="+p).timeout(90000).get();
										//System.out.println(reviews);
										if (reviews.body().text().equals("")) {
											break;
										}
										Elements revieweles = reviews.select("li");
										//System.out.println(eles);
										
										
										for (Element elementreview : revieweles) {
											String reviewer = elementreview.select("div.comment-info").select("em").text();
											//System.out.println(reviewer);
											String content = elementreview.select("div.comment-info").select("p").text();
											//System.out.println(content);
											String commentTime = elementreview.select("div.comment-time").text();
											//System.out.println(commentTime);
											

											 String sql1 = "select reviewer,content,commenttime from baidureviews where reviewer='"+reviewer+"' and content='"+content+"' and commenttime='"+commentTime+"'";
											 String sql2 ="select id from baiduapp where appname='"+appName+"'";
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
														 insertSales = conn.prepareStatement("insert into baidureviews (reviewer,content,commenttime,appid) values (?,?,?,?)");
														 insertSales.setString(1, reviewer);
														 insertSales.setString(2, content);
														 insertSales.setString(3, commentTime);
														 insertSales.setString(4, appid);
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
							}
					} 
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	
			
			
		}
	

