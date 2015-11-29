package com.crawler.test;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class JsoupNull {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			try {
				Document doc = Jsoup.connect("http://shouji.baidu.com/comment?action_type=getCommentList&groupid=3081893&pn=312").get();
				//System.out.println(doc);
				String groupid = doc.select("div.app-intro").select("input").attr("value");
				String test = doc.body().text();
				String s = "fdsf";
				System.out.println(test);
				if (test.equals("")) {
					System.out.println("rigdfsdafdsaf ht");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}

}
