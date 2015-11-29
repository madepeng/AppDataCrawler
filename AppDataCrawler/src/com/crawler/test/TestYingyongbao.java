package com.crawler.test;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TestYingyongbao {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			try {
				Document doc = Jsoup.connect("http://android.myapp.com/myapp/detail.htm?apkName=com.icoolme.android.weather").timeout(90000).get();
			   
				String appName = doc.select("div.det-name-int").text();
				System.out.println(appName);
				
				String rating = doc.select("div.com-blue-star-num").text();
				System.out.println(rating);
				
				String reviewNum = doc.select("a.det-comment-num").text();
				System.out.println(reviewNum);
				
				String downloadNum = doc.select("div.det-ins-num").text();
				System.out.println(downloadNum);
				
				String size = doc.select("div.det-size").text();
				System.out.println(size);
				
				String adStatus = doc.select("div#J_AdvBox").text();
				System.out.println(adStatus);
				
				String catagory = doc.select("a#J_DetCate").text();
				System.out.println(catagory);
				
				String appInfo = doc.select("div.det-app-data-info").text();
				System.out.println(appInfo);
				
				String version = doc.select("div.det-othinfo-data").first().text();
				System.out.println(version);
				
				String updateTime = doc.select("div#J_ApkPublishTime").text();
				System.out.println(updateTime);
				
				String downloadUrl=doc.select("a").attr("data-apkurl");
			     System.out.println(downloadUrl); 
			     
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}

}
