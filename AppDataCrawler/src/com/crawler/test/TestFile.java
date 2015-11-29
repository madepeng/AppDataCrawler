package com.crawler.test;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			/*File file = new File("D:\\fsdfds.txt");
			File file1 = new File("D:\\fdggf\\fsdfdfg\\madepeng");
			try {
				file.createNewFile();
				file1.mkdirs();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		try {
		/*	Document doc =  Jsoup.connect("http://www.wandoujia.com/apps/com.moling.buyu10002.wdj").timeout(90000).get();
			System.out.println(doc.select("dl.infos-list").select("dd").get(3).text());*/
			
			Document doc =  Jsoup.connect("http://www.wandoujia.com/apps/com.moling.buyu10002.wdj").timeout(90000).get();
			Elements ele =  doc.select("div.comments");
			System.out.println(ele);
			for (Element element : ele) {
				System.out.println(element);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
