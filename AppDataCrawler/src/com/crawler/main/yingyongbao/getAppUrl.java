package com.crawler.main.yingyongbao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class getAppUrl {
		public void getAppUrl() {
			
			Integer[] nums1 = {-10,10,101,102,104,106,112,110,115,119,111,107,118,108,100,114,117,109,105,113,116,122};
			Integer[] nums2 = {147,121,144,148,149,153,146,151};
			List<Integer> categoryIds1 = new ArrayList<Integer>(Arrays.asList(nums1));
			List<Integer> categoryIds2 = new ArrayList<Integer>(Arrays.asList(nums2));
			for (int K = 0; K < categoryIds1.size(); K++) {
				for (int i = 0;; i+=20) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						Document doc = Jsoup.connect("http://android.myapp.com/myapp/cate/appList.htm?orgame=1&categoryId="+categoryIds1.get(K)+"&pageSize=20&pageContext="+i).ignoreContentType(true).ignoreHttpErrors(true).timeout(9000).get();
						//记录i，j的值，是为了记录中断的位置，直接从中断的位置开始执行就行
						System.out.println("yingyongbaoapp"+categoryIds1.size());
						System.out.println("k="+K);
						System.out.println("i="+i);
						
						String str = doc.body().text();
						JSONObject jsonObj = JSONObject.fromObject(str);
						//System.out.println(jsonObj);
						if (Integer.parseInt(jsonObj.getString("count"))==0) {
							System.out.println("game over");
							break;
						}
						String str1 = jsonObj.getString("obj");
						//System.out.println(str1);
						JSONArray jsonArray = JSONArray.fromObject(str1);
						//System.out.println(jsonArray);
						for (int j = 0; j < jsonArray.size(); j++) {
							String str2 = jsonArray.getString(j);
							JSONObject jsonObj1 = JSONObject.fromObject(str2);
							//System.out.println(jsonObj1);
							String pkgName = jsonObj1.getString("pkgName");
							System.out.println(pkgName);
							String url = "http://android.myapp.com/myapp/detail.htm?apkName=";
							url+=pkgName;
							String reviewsUrl = "http://android.myapp.com/myapp/app/comment.htm?apkName="+pkgName;
							//System.out.println(url);
							getAppProperty getproperty = new getAppProperty();
							getproperty.getProperty(url,reviewsUrl);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}catch (JSONException e) {
						// TODO: handle exception
						e.printStackTrace();
						System.out.println("json exception");
					}catch (NullPointerException e) {
						// TODO: handle exception
						e.printStackTrace();
						System.out.println("nullpointerexception");
					}
				}
			}
			
		
		
		
		
		
		
		}
	
}
