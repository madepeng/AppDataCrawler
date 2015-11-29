package com.crawler.main.sanliuling;

import java.io.IOException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class getReviews {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Document docReviews = Jsoup.connect("http://intf.baike.360.cn/index.php?name=360%E6%B8%85%E7%90%86%E5%A4%A7%E5%B8%88&c=message&a=getmessage&start=0&count=10&type=best").timeout(90000).ignoreContentType(true).ignoreHttpErrors(true).get();
			String str = docReviews.body().text();
			System.out.println(str);
			JSONObject jsonObj = JSONObject.fromObject(str); 
			String str1 = jsonObj.get("data").toString();
			System.out.println(str1);
			
			JSONObject jsonObj1 = JSONObject.fromObject(str1); 
			/*String total = jsonObj1.get("total").toString();
			System.out.println(total);*/
			String str2 = jsonObj1.get("messages").toString();
			if (str2.equals("[]")) {
				System.out.println("end");
			}
			System.out.println(str2);
			
			JSONArray jsonArray1 = JSONArray.fromObject(str2); 
			
			//数组
			int size = jsonArray1.size();
			System.out.println(size);
			String str3 = jsonArray1.getString(0);
			System.out.println(str3);
			
			JSONObject str4 = JSONObject.fromObject(str3);
			String create_time = str4.getString("create_time");
			System.out.println(create_time);
			String score = str4.getString("score");
			System.out.println(score);
			String content = str4.getString("content");
			System.out.println(content);
			String username = str4.getString("username");
			System.out.println(username);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
