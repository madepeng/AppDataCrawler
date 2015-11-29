package com.crawler.main.sanliuling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class getapp {
	
	getInfo getInfo = new getInfo();
		
	public void get() {
		
		Integer[] nums = {11,12,14,15,16,18,17,102228,102230,102231,102232,102139,102233,101587,19,20,100451,51,52,53,54,102238};
		List<Integer> categoryIds = new ArrayList<Integer>(Arrays.asList(nums));
		
		for (int K = 0; K < categoryIds.size(); K++) {
			for (int i = 1; i < 51; i++) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//记录i，j的值，是为了记录中断的位置，直接从中断的位置开始执行就行
				System.out.println("360app"+categoryIds.size());
				System.out.println("k="+K);
				System.out.println("i="+i);
				
				String pageurl = "http://zhushou.360.cn/list/index/cid/"+categoryIds.get(K)+"/?page="+i;
				
				try {
					Document doc = Jsoup.connect(pageurl).timeout(9000).get();
					Elements elements = doc.select("ul.iconList").select("li");
					//System.out.println(elements.size());
					for (int j = 1; j < elements.size(); j++) {
						String appUrl = elements.get(j).select("a").first().attr("abs:href");
						System.out.println(appUrl);
						getInfo.get(appUrl);
					}
					/*for (Element element : elements) {
						String appUrl = element.select("a").first().attr("abs:href");
						System.out.println(appUrl);
						getInfo.get(appUrl);
					}*/
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
		
	}

}
