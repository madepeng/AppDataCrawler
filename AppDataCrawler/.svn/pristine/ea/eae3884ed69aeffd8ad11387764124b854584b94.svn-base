package com.crawler.test;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class BaiduZhushouattr {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			try {
				Document doc = Jsoup.connect("http://shouji.baidu.com/software/item?docid=7799274&from=as&f=list_software_503%400%401").timeout(90000).get();
				String appName = doc.select("h1.app-name").text();
				System.out.println(appName);
				
				String rating = doc.select("span.star-percent").attr("style").split(":")[1];
				System.out.println(rating);
				
				String size = doc.select("span.size").first().text().split(":")[1];
				System.out.println(size);
				
				String version = doc.select("span.version").text().split(":")[1];
				System.out.println(version);
				
				String downloadNum = doc.select("span.download-num").text().split(":")[1];
				System.out.println(downloadNum);
				
				String downloadUrl = doc.select("a.apk").attr("href");
				System.out.println(downloadUrl);
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}

}
