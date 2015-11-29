package com.crawler.main.youyi;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test {
	
	public void getAppUrl(String PageUrl) {
		
		Testapp testapp = new Testapp();
		
		try {
			String appurl = PageUrl;
			
			
			Document doc = Jsoup.connect(appurl).ignoreContentType(true).ignoreHttpErrors(true).timeout(9000).get();
			
			Elements elements = doc.getElementsByAttributeValue("class", "classf_list_item fl").select("span.classf_list_inf");
			
			for (Element element : elements) {
				String url = element.select("a.classf_list_name").attr("abs:href");
				System.out.println(url);
				
				String rating = element.select("span.classf_list_infc").select("em").select("span.stary_rem").attr("style");
				rating = rating.substring(6).split("p")[0];
				System.out.println(rating);
				
				testapp.getAppInfo(url, rating);
				
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}
