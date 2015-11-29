package com.crawler.test;

import java.io.IOException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WanReviews {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			try {
				Document doc = Jsoup.connect("http://apps.wandoujia.com/api/v1/comments/primary?packageName=com.sohu.sohuvideo").ignoreContentType(true).timeout(90000).get();
				//System.out.println(doc);
				String str = doc.body().text();
				JSONObject jsonObj = JSONObject.fromObject(str);
				//System.out.println(jsonObj);
				String superiorComments =jsonObj.getString("superiorComments");
				System.out.println(superiorComments);
				JSONArray jsonArray = JSONArray.fromObject(superiorComments);
				for (int i = 0; i < jsonArray.size(); i++) {
					//System.out.println(jsonArray.getString(i));
					JSONObject json = (JSONObject)jsonArray.get(i);
					String content = json.getString("content");
					String updatedDateStr = json.getString("updatedDateStr");
					System.out.println(content);
					System.out.println(updatedDateStr);
					JSONObject authorInfo = (JSONObject)json.get("author");
					String auto = authorInfo.getString("name");
					System.out.println(auto);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}

}
