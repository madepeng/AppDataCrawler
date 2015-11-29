package com.crawler.test;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Yingyongbaoreview {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 Document reviewDoc;
		try {
			reviewDoc = Jsoup.connect("http://android.myapp.com/myapp/app/comment.htm?apkName=com.sohu.newsclient").timeout(90000).ignoreContentType(true).ignoreHttpErrors(true).get();
			 String str = reviewDoc.body().text();
			 JSONObject jsonObj = JSONObject.fromObject(str);
			 System.out.println(jsonObj);
			String str1 =  jsonObj.getString("obj");
			System.out.println(str1);
			JSONObject jsonObj1 = JSONObject.fromObject(str1);
			String str2 = jsonObj1.getString("total");
			System.out.println(str2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
