package com.crawler.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.crawler.util.DateUtil;

public class TestDate {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			Date date = new Date();
			DateUtil dateUtil = new DateUtil();
			System.out.println(dateUtil.formatStandardDate(date));
			/*System.out.println(date);
			System.out.println(dateUtil.formatStandardDateTime(date));
			System.out.println(dateUtil.formatStandardDate(date));
			System.out.println(dateUtil.formatStandardFullDateTime(date));
			System.out.println(dateUtil.formatShortDateTime(date));
			System.out.println(dateUtil.formatShortDate(date));
			System.out.println(dateUtil.formatStandardDate(date));*/
			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			/*System.out.println(sdf.format(date));
			System.out.println(date.toLocaleString());
			System.out.println(date.getDate());
			System.out.println(date.toGMTString());*/
	}

}
