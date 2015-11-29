package com.crawler.test;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TestEncode {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			try {
				Document doc = Jsoup.connect("http://www.wandoujia.com/apps/com.yinhan.shenmo.wdj").timeout(90000).get();
				System.out.println(doc.select("p.cmt-content").select("span").text());
				System.out.println("dfsfdf");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

}
