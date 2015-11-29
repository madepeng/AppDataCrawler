package com.crawler.main.youyi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;

import com.crawler.main.yingyongbao.getAppProperty;

public class get {
			
	public void getFirseAppUrl() {
		Integer[] nums1 = {4,5,8,36,37,72,73,74,77,79,80,81,9,10,15};
		Integer[] nums2 = {19,20,21,22,31,32,33,35};
		
		List<Integer> categoryIds1 = new ArrayList<Integer>(Arrays.asList(nums1));
		List<Integer> categoryIds2 = new ArrayList<Integer>(Arrays.asList(nums2));
		
		Test test = new Test();
		
		for (int K = 0; K < categoryIds1.size(); K++) {
			for (int i = 1;; i+=1) {
				
				
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				try {
					String pageurl = "http://www.eoemarket.com/soft/"+categoryIds1.get(K)+"_hot_unofficial_hasad_1_"+i+".html";
					
					Response response = Jsoup.connect(pageurl).timeout(3000000).ignoreHttpErrors(true).ignoreContentType(true).execute();
					 int statusCode = response.statusCode();
					 if (statusCode == 404) {
						 break;
					 }
					 
					//记录i，j的值，是为了记录中断的位置，直接从中断的位置开始执行就行
					System.out.println("youyiapp"+categoryIds1.size());
					System.out.println("k="+K);
					System.out.println("i="+i);
					
					
					
					test.getAppUrl(pageurl);
					
					
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
	
	public void getSecondAppUrl() {
		Integer[] nums1 = {4,5,8,36,37,72,73,74,77,79,80,81,9,10,15};
		Integer[] nums2 = {19,20,21,22,31,32,33,35};
		
		List<Integer> categoryIds1 = new ArrayList<Integer>(Arrays.asList(nums1));
		List<Integer> categoryIds2 = new ArrayList<Integer>(Arrays.asList(nums2));
		
		Test test = new Test();
		
		for (int K = 0; K < categoryIds2.size(); K++) {
			for (int i = 1;; i+=1) {
				
				
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				try {
					String pageurl = "http://www.eoemarket.com/game/"+categoryIds2.get(K)+"_hot_unofficial_hasad_2_"+i+".html";
					
					Response response = Jsoup.connect(pageurl).timeout(3000000).ignoreHttpErrors(true).ignoreContentType(true).execute();
					 int statusCode = response.statusCode();
					 if (statusCode == 404) {
						 break;
					 }
					 
					//记录i，j的值，是为了记录中断的位置，直接从中断的位置开始执行就行
					System.out.println("youyiapp"+categoryIds1.size());
					System.out.println("k="+K);
					System.out.println("i="+i);
					
					
					
					test.getAppUrl(pageurl);
					
					
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
