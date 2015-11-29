package com.crawler.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * <pre>
 * 日期工具
 * </pre>
 * @author 马德棚-18813095841-北京交通大学
 *
 */
public class DateUtil{

	private static String STANDARD_FULL_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

	private static String SHORT_FULL_DATETIME_FORMAT = "yyyyMMddHHmmssSSS";

	private static String STANDARD_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private static String SHORT_DATETIME_FORMAT = "yyyyMMddHHmmss";

	private static String STANDARD_DATE_FORMAT = "yyyy-MM-dd";

	private static String SHORT_DATE_FORMAT = "yyyyMMdd";

	private static String SHORT_MMDD_FORMAT = "MMdd";

	private static String SHORT_TIME_FORMAT = "HHmmss";

	/**
	 * 取得昨天时间(yyyy-MM-dd)
	 */
	public static String getYesterday() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		SimpleDateFormat sf = new SimpleDateFormat(STANDARD_DATE_FORMAT);
		return sf.format(cal.getTime());
	}
	/**
	 * 取得当天时间(yyyy-MM-dd)
	 */
	public static String getToday() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1+1);
		SimpleDateFormat sf = new SimpleDateFormat(STANDARD_DATE_FORMAT);
		return sf.format(cal.getTime());
	}
	/**
	 * 取得昨天时间(yyyy-MM-dd)
	 */
	public static String getYesterdaytime() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		SimpleDateFormat sf = new SimpleDateFormat(STANDARD_DATETIME_FORMAT);
		return sf.format(cal.getTime());
	}
	
	/**
	 * 取得去年的时间(yyyy-MM-dd)
	 */
	public static String getLastYeartime() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -2);
		SimpleDateFormat sf = new SimpleDateFormat(STANDARD_DATETIME_FORMAT);
		return sf.format(cal.getTime());
	}

	/**
	 * 传入年月，得到传入月的最后一天的日期(yyyy-MM-dd HH:mm:ss)
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * 
	 */
	public static Date getEndDayofMonth(String year, String month, String day) {
		Calendar cDate = new GregorianCalendar();
		cDate.set(Integer.parseInt(year), Integer.parseInt(month) + 1, 01);
		cDate.getActualMaximum(Calendar.DAY_OF_MONTH);
		System.out.println(cDate.getActualMaximum(Calendar.DAY_OF_MONTH));
		String str = year + "-" + day + "-" + day + " " + "00:00:";
		return parseStandardDateTime(str);
	}

	/**
	 * 格式完全日期时间字符串（包含毫秒，标准格式：yyyy-MM-dd HH:mm:ss.S）
	 * 
	 * @param date
	 *            日期时间
	 * @return 完全日期时间字符串
	 */
	public static String formatStandardFullDateTime(Date date) {
		return new SimpleDateFormat(STANDARD_FULL_DATETIME_FORMAT).format(date);
	}

	/**
	 * 格式完全日期时间字符串（包含毫秒，短格式：yyyyMMddHHmmssS）
	 * 
	 * @param date
	 *            日期时间
	 * @return 完全日期时间字符串
	 */
	public static String formatShortFullDateTime(Date date) {
		return new SimpleDateFormat(SHORT_FULL_DATETIME_FORMAT).format(date);
	}

	/**
	 * 格式日期时间字符串（标准格式：yyyy-MM-dd HH:mm:ss）
	 * 
	 * @param date
	 *            日期时间
	 * @return 日期时间字符串
	 */
	public static String formatStandardDateTime(Date date) {
		return new SimpleDateFormat(STANDARD_DATETIME_FORMAT).format(date);
	}

	/**
	 * 格式日期时间字符串（短格式：yyyyMMddHHmmss）
	 * 
	 * @param date
	 *            日期时间
	 * @return 日期时间字符串
	 */
	public static String formatShortDateTime(Date date) {
		return new SimpleDateFormat(SHORT_DATETIME_FORMAT).format(date);
	}

	/**
	 * 格式日期字符串（标准格式：yyyy-MM-dd）
	 * 
	 * @param date
	 *            日期
	 * @return 日期字符串
	 */
	public static String formatStandardDate(Date date) {
		return new SimpleDateFormat(STANDARD_DATE_FORMAT).format(date);
	}

	/**
	 * 格式日期字符串（短格式：yyyyMMdd）
	 * 
	 * @param date
	 *            日期
	 * @return 日期字符串
	 */
	public static String formatShortDate(Date date) {
		return new SimpleDateFormat(SHORT_DATE_FORMAT).format(date);
	}

	/**
	 * 格式日期字符串（短格式：MMdd）
	 * 
	 * @param date
	 *            日期
	 * @return 日期字符串
	 */
	public static String formatShortMMDDDate(Date date) {
		return new SimpleDateFormat(SHORT_MMDD_FORMAT).format(date);
	}

	/**
	 * 格式时间字符串（短格式：HHmmss）
	 * 
	 * @param date
	 *            时间
	 * @return 时间字符串
	 */
	public static String formatShortTime(Date date) {
		return new SimpleDateFormat(SHORT_TIME_FORMAT).format(date);
	}

	/**
	 * 解析完全日期时间字符串（包含毫秒，标准格式：yyyy-MM-dd HH:mm:ss.S）
	 * 
	 * @param dateStr
	 *            完全日期时间字符串
	 * @return 日期时间
	 */
	public static Date parseStandardFullDateTime(String dateStr) {
		if (dateStr == null) {
			return null;
		}
		try {
			return new SimpleDateFormat(STANDARD_FULL_DATETIME_FORMAT)
					.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 解析完全日期时间字符串（包含毫秒，短格式：yyyyMMddHHmmssS）
	 * 
	 * @param dateStr
	 *            完全日期时间字符串
	 * @return 日期时间
	 */
	public static Date parseShortFullDateTime(String dateStr) {
		if (dateStr == null) {
			return null;
		}
		try {
			return new SimpleDateFormat(SHORT_FULL_DATETIME_FORMAT)
					.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 解析日期时间字符串（标准格式：yyyy-MM-dd HH:mm:ss）
	 * 
	 * @param dateStr
	 *            日期时间字符串
	 * @return 日期时间
	 */
	public static Date parseStandardDateTime(String dateStr) {
		if (dateStr == null) {
			return null;
		}
		try {
			return new SimpleDateFormat(STANDARD_DATETIME_FORMAT)
					.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 解析日期时间字符串（短格式：yyyyMMddHHmmss）
	 * 
	 * @param dateStr
	 *            日期时间字符串
	 * @return 日期时间
	 */
	public static Date parseShortDateTime(String dateStr) {
		if (dateStr == null) {
			return null;
		}
		try {
			return new SimpleDateFormat(SHORT_DATETIME_FORMAT).parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 解析日期字符串（标准格式：yyyy-MM-dd）
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @return 日期
	 */
	public static Date parseStandardDate(String dateStr) {
		if (dateStr == null) {
			return null;
		}
		try {
			return new SimpleDateFormat(STANDARD_DATE_FORMAT).parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取传入日期的下一天
	 * 
	 * @param date
	 *            日期字符串
	 * @return 日期
	 */
	public static Date getStandardDateNext(Date date) {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, 1);
			return cal.getTime();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取传入日期的下一天
	 * 
	 * @param date
	 *            日期字符串
	 * @return 日期
	 */
	public static String getStandardDateBeforeMonth(Date date) {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MONTH, -1);
			SimpleDateFormat sf = new SimpleDateFormat(STANDARD_DATE_FORMAT);
			return sf.format(cal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 解析日期字符串（短格式：yyyyMMdd）
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @return 日期
	 */
	public static Date parseShortDate(String dateStr) {
		if (dateStr == null) {
			return null;
		}
		try {
			return new SimpleDateFormat(SHORT_DATE_FORMAT).parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 解析日期字符串（短格式：MMdd）
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @return 日期
	 */
	public static Date parseShortMMDDDate(String dateStr) {
		if (dateStr == null) {
			return null;
		}
		try {
			return new SimpleDateFormat(SHORT_MMDD_FORMAT).parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 解析时间字符串（短格式：HHmmss）
	 * 
	 * @param dateStr
	 *            时间字符串
	 * @return 时间
	 */
	public static Date parseShortTime(String dateStr) {
		if (dateStr == null) {
			return null;
		}
		try {
			return new SimpleDateFormat(SHORT_TIME_FORMAT).parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取当前完全日期时间字符串（包含毫秒，标准格式：yyyy-MM-dd HH:mm:ss.S）
	 * 
	 * @return 完全日期时间字符串
	 */
	public static String getStandardFullDateTimeStr() {
		return formatStandardFullDateTime(new Date());
	}

	/**
	 * 获取当前完全日期时间字符串（包含毫秒，短格式：yyyyMMddHHmmssS）
	 * 
	 * @return 完全日期时间字符串
	 */
	public static String getShortFullDateTimeStr() {
		return formatShortFullDateTime(new Date());
	}

	/**
	 * 获取当前日期时间字符串（标准格式：yyyy-MM-dd HH:mm:ss）
	 * 
	 * @return 日期时间字符串
	 */
	public static String getStandardNowDateTimeStr() {
		return formatStandardDateTime(new Date());
	}

	/**
	 * 获取当前日期时间字符串（短格式：yyyyMMddHHmmss）
	 * 
	 * @return 日期时间字符串
	 */
	public static String getShortNowDateTimeStr() {
		return formatShortDateTime(new Date());
	}

	/**
	 * 获取当前日期字符串（标准格式：yyyy-MM-dd）
	 * 
	 * @return 日期字符串
	 */
	public static String getStandardNowDateStr() {
		return formatStandardDate(new Date());
	}

	/**
	 * 获取当前日期字符串（短格式：yyyyMMdd）
	 * 
	 * @return 日期字符串
	 */
	public static String getShortNowDateStr() {
		return formatShortDate(new Date());
	}

	/**
	 * 将秒数换算成x天x时x分x秒x毫秒
	 * 
	 * @method_name format
	 * @author yangxinwang
	 * @date 2009-12-21 下午12:00:10
	 * @description
	 * @param ms
	 * @return
	 */
	public static String calculateTimeDifference(long ms) {
		String time = "";
		if (ms > 0) {
			long day = ms / (24 * 3600);
			long hour = ms % (24 * 3600) / 3600;
			long minute = ms % 3600 / 60;
			long second = ms % 60;
			if (day > 0) {
				time = day + "天" + hour + "小时" + minute + "分钟" + second + "秒";
			} else {
				if (hour > 0) {
					time = hour + "小时" + minute + "分钟" + second + "秒";
				} else {
					if (minute > 0) {
						time = minute + "分钟" + second + "秒";
					} else {
						if (second > 0) {
							time = second + "秒";
						} else {
							time = "0秒";
						}
					}
				}
			}
		} else {
			time = "0秒";
		}
		return time;
	}
}
