package com.crawler.test;

import java.io.IOException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TestYingyongbaourl {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
				try {
					Document doc = Jsoup.connect("http://android.myapp.com/myapp/cate/appList.htm?orgame=1&categoryId=0&pageSize=20&pageContext=80").ignoreContentType(true).ignoreHttpErrors(true).timeout(9000).get();
					//System.out.println(doc);
					String str = doc.body().text();
					System.out.println(str);
					/*JSONArray jsonArray = JSONArray.fromObject(str);
					System.out.println(jsonArray);*/
					JSONObject jsonObj = JSONObject.fromObject(str);
					System.out.println(jsonObj);
					String str1 = jsonObj.getString("obj");
					System.out.println(str1);
					JSONArray jsonArray = JSONArray.fromObject(str1);
					System.out.println(jsonArray);
					/*String str2 = jsonArray.getString(0);
					JSONObject jsonObj1 = JSONObject.fromObject(str2);
					System.out.println(jsonObj1);
					String pkgName = jsonObj1.getString("pkgName");
					System.out.println(pkgName);*/
					for (int j = 0; j < jsonArray.size(); j++) {
						String str2 = jsonArray.getString(j);
						JSONObject jsonObj1 = JSONObject.fromObject(str2);
						System.out.println(jsonObj1);
						String pkgName = jsonObj1.getString("pkgName");
						System.out.println(pkgName);
						String url = "http://android.myapp.com/myapp/detail.htm?apkName=";
						url+=pkgName;
						System.out.println(url);
					}
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}

}
