package com.crawler.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class Attraction {
	String url;
	String title;
	String Name;
	String user_id;//评论人标号
	String user_url;//评论人信息网址
	String user_name;//评论人昵称
	String user_level;//评论人等级
	String location;//评论人所在地
	String country_count;//去过国家数
	String city_count;//去过城市数
	String starlevel;//评论星级
	String date;//评论时间
	String content;//评论内容
	String useful;//有用数
	String poiid;
	String order = "2";//页内排序方式
	static int a = 0;
	int number = 0;//统计空数据为10则退出
	
	public Attraction(String url){
		this.url = url;
	}
	
	/**
	 * 根据URL获得所有的html信息
	 * 
	 * @param url
	 * @return
	 */
	public static String getHtmlByUrl(String url) {
		String html = null;
		HttpClient httpClient = new DefaultHttpClient();// 创建httpClient对象
		HttpGet httpget = new HttpGet(url);// 以get方式请求该URL
		try {
			HttpResponse responce = httpClient.execute(httpget);// 得到responce对象
			int resStatu = responce.getStatusLine().getStatusCode();// 返回码
			if (resStatu == HttpStatus.SC_OK) {// 200正常 其他就不对
				// 获得相应实体
				HttpEntity entity = responce.getEntity();
				if (entity != null) {
					html = EntityUtils.toString(entity);// 获得html源代码
				}
			}
		} catch (Exception e) {
			System.out.println("访问【" + url + "】出现异常!");
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return html;
	}
	
	public void getAttraction(PrintWriter out1) throws InterruptedException 
	{
		try{
			a++;
			Document doc = Jsoup.connect(url).timeout(300000).get();
			Elements poi = doc.select("input#poiid");
			if(poi!=null && poi.size()>0)
				poiid = poi.get(0).attr("value");
			else
				poiid = "";
			Elements bigtitle = doc.select("div.poiDet-largeTit").select("h1.cn");
			if(bigtitle!=null && bigtitle.size()>0)
				title = bigtitle.get(0).text();
			else
				title = "";
			Name = "attraction "+title;
//			new File(City.cityDir+"/"+"attraction").mkdirs();
		//	File file = new File(City.cityDir+"/attraction/"+title+".txt");
//			PrintWriter out = new  PrintWriter(new BufferedWriter(new FileWriter(file)));
			Document doc1 = null;
			for(int pp=1;;pp++)
			{
				
				String currenturl = "http://place.qyer.com/poi.php?action=comment&page="+pp+"&order="+order+"&poiid="+poiid+"&starLevel=all";
			   
			    System.out.println(currenturl);
			    try{
				    Response response = Jsoup.connect(currenturl).timeout(3000000).ignoreHttpErrors(true).execute();
				    int statusCode = response.statusCode();
				    if(statusCode == 599)
				    {
				    	Thread.sleep(500);
				    	Response response1 = Jsoup.connect(currenturl).timeout(3000000).ignoreHttpErrors(true).execute();
				    	int statusCode1 = response1.statusCode();
				    	if(statusCode1 == 599)
				    		continue;
				    	else
				    		doc1 = response1.parse();
				    }
				    else
				    {
				    	doc1 = response.parse();
				    	String str = doc1.select("body").text();
				    	  JSONObject jsonObject = JSONObject.fromObject(str);
				    	  String data = jsonObject.get("data").toString();
				    	  JSONObject jsonObject1 = JSONObject.fromObject(data);
				    	  JSONArray jsonArray = jsonObject1.getJSONArray("lists");
				    	  if(jsonArray.size()==0)
				    		  break;
				    	 for (int i = 0; i < jsonArray.size(); i++) {
				    		 //初始化
				    		 user_name = "";
				    		 user_level = "";
				    		 location = "";
				    		 country_count = "";
				    		 city_count = "";
				    		 starlevel = "";
				    		 date = "";
				    		 content = "";
				    		 useful = "";
				    				 
				    		 String info = jsonArray.getString(i);
				    		 JSONObject jsonObject2 = JSONObject.fromObject(info);
				    		 starlevel = jsonObject2.get("starlevel").toString();
				    		 date = jsonObject2.get("date").toString();
				    		 content = jsonObject2.get("content").toString().replace("\r", "").replace("\n", "").replace("<br />", "").replace("<br>", "");
				    		 useful = jsonObject2.get("useful").toString();
				    		 String user = jsonObject2.get("userinfo").toString();
				    		 JSONObject jsonObject3 = JSONObject.fromObject(user);
				    		 String user_url = jsonObject3.get("link").toString();
				    		 user_name = jsonObject3.get("name").toString();
				    		 System.out.println(user_url);
				    		 String html = getHtmlByUrl(user_url);
				    		 if(html!=null)
				    		 {
					    		 Document doc2 = Jsoup.parse(html);
					    		 Elements names = doc2.select("h3.name").select("strong.fontYaHei");
					    		 if(names!=null && names.size()>0)
					    			 user_name = names.get(0).text();
					    		 else
					    			 user_name = "";
					    		 Elements texts = doc2.select("div.infos").select("div.text.fontSong");
					    		 if(texts!=null && texts.size()>0)
					    		 {
					    			 Elements levels = texts.get(0).select("a");
					    			 if(levels!=null && levels.size()>0)
					    				 user_level = levels.get(0).text();
					    			 else
					    				 user_level = "";
					    			 texts.get(0).select("a").remove();
					    			 location = texts.get(0).text();
					    			 if(location.contains("现居："))
					    				 location = location.split("等级：")[1];
					    			 
					    		 }
					    		 System.out.println(location);
					    		 Elements destinations = doc2.select("ul.uIndSpoor").select("span.spoor");
					    		 if(destinations!=null && destinations.size()>=2)
					    		 {
					    			 country_count = destinations.get(0).text();
					    			 city_count = destinations.get(1).text();
					    		 }
				    		 }
				    		
//				    		 out.printf("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\n", title,starlevel,content,date,useful,user_name,location,user_level,city_count,country_count);
	//			    		 out.flush();
			
				    		// System.out.printf("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\n", starlevel,content,date,useful,user_name,location,user_level,city_count,country_count);
			
				    	 }
				    }
			     
				} catch(JSONException e)
			   	 {
			   		 e.printStackTrace();
			   		 continue;
			   	 }
			}
		//	out.close();
			out1.println(a+" "+Name+" success!");
	   		out1.flush();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
	    
	}

}
