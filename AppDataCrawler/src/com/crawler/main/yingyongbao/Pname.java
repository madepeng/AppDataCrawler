package com.crawler.main.yingyongbao;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Pname {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Document doc = Jsoup.connect("http://zhushou.360.cn/detail/index/soft_id/1954941").timeout(9000).get();
			String pname = doc.select("div.app-item").attr("data-pname");
			System.out.println(pname);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
