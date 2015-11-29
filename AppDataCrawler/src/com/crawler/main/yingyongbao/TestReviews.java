package com.crawler.main.yingyongbao;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestReviews {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Document doc = Jsoup.connect("http://www.eoemarket.com/soft/35776.html?page=1").ignoreContentType(true).ignoreHttpErrors(true).timeout(9000).get();
			Elements elements = doc.getElementsByAttributeValue("class", "comment_item clearfix");
			if (elements.size() == 0) {
				System.out.println(true);
			}
			for (Element element : elements) {
				String reviews = element.select("span.comment_meg").text();
				String reviewer = element.select("span.comment_infoc").select("em").eq(1).text();
				System.out.println(reviews);
				System.out.println(reviewer);
			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
