package com.crawler.test;

import java.io.IOException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;

import com.gargoylesoftware.htmlunit.javascript.host.dom.Document;

public class TestSend {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
		/*	Response response = Jsoup.connect("http://apps.wandoujia.com/api/v1/apps?ads_count=0&tag=%E8%A7%86%E9%A2%91&max=12&start=24&opt_fields=apps.likesCount,apps.reason,apps.ad,apps.title,apps.packageName,apps.apks.size,apps.icons.px68,apps.apks.superior,apps.installedCountStr,apps.snippet,apps.editorComment,apps.apks.versionCode").timeout(90000).ignoreHttpErrors(true).execute();
			org.jsoup.nodes.Document doc = response.parse();
			//System.out.println(doc);
			String str = doc.select("body").text();
			System.out.println(str);*/
			 /*JSONObject jsonObject = JSONObject.fromObject(str);
			 System.out.println(jsonObject);*/
			//System.out.println(doc);
			//Response response = Jsoup.connect(currenturl).timeout(3000000).ignoreHttpErrors(true).execute();
		org.jsoup.nodes.Document doc = Jsoup.connect("http://apps.wandoujia.com/api/v1/apps?ads_count=0&tag=%E4%BC%91%E9%97%B2%E6%97%B6%E9%97%B4&max=24&start=12&opt_fields=apps.likesCount,apps.reason,apps.ad,apps.title,apps.packageName,apps.apks.size,apps.icons.px68,apps.apks.superior,apps.installedCountStr,apps.snippet,apps.editorComment,apps.apks.versionCode").ignoreContentType(true).get();
		//System.out.println(doc.body().text());
		String str = doc.body().text();
		JSONArray json = JSONArray.fromObject(str); 
		//System.out.println(json);
		String str1 = json.getString(0);
		JSONObject jsonObj = JSONObject.fromObject(str1); 
		//System.out.println(jsonObj);
		//System.out.println(jsonObj.get("apps"));
		String str2 = jsonObj.get("apps").toString();
		System.out.println(str2);
		if (str2.equals("[]")) {
			System.out.println("[] is true");
			System.out.println("test break");
		}
		
		
		JSONArray jsonarry = JSONArray.fromObject(str2);
		for (int i = 0; i < jsonarry.size(); i++) {
			//System.out.println(jsonarry.getString(i));
			String str3 = jsonarry.getString(i);
			JSONObject jsonobj = JSONObject.fromObject(str3);
			String str4 = jsonobj.get("packageName").toString();
			System.out.println(str4);
		}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
