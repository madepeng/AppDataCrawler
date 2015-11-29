package com.crawler.test;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BaiduReviews {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			try {
				Document doc = Jsoup.connect("http://shouji.baidu.com/soft/item?docid=7799274&from=landing&f=software%40tophot%401").timeout(90000).get();
				String groupid = doc.select("div.app-intro").select("input").attr("value");
				System.out.println(groupid);
				
				for (int i = 0;; i++) {
					Document reviews = Jsoup.connect("http://shouji.baidu.com/comment?action_type=getCommentList&groupid="+groupid+"&pn="+i).timeout(90000).get();
					//System.out.println(reviews);
					if (reviews.body().text().equals("")) {
						break;
					}
					Elements eles = reviews.select("li");
					//System.out.println(eles);
					for (Element element : eles) {
						String reviewer = element.select("div.comment-info").select("em").text();
						System.out.println(reviewer);
						String content = element.select("div.comment-info").select("p").text();
						System.out.println(content);
						String commentTime = element.select("div.comment-time").text();
						System.out.println(commentTime);
					}
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}

}
