package com.crawler.test;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class baiduurl {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			try {
				Document doc = Jsoup.connect("http://shouji.baidu.com/software/?from=as").timeout(90000).get();
				Elements eles = doc.select("div#doc").select("ul.cate-body").select("li").select("a");
				for (Element element : eles) {
					System.out.println(eles.size());
					System.out.println(element.attr("abs:href"));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				Document doc1 = Jsoup.connect("http://shouji.baidu.com/software/list?cid=503&boardid=board_100_01").timeout(90000).get();
				Elements eles1 = doc1.select("div.app-box").select("a");
				for (Element element : eles1) {
					//System.out.println(eles1.size());
					//System.out.println(element.attr("abs:href"));
					//System.out.println("fdsfds");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
	}

}
