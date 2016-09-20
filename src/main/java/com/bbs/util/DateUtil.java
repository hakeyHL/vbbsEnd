package com.bbs.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	private static String defaultDatePattern = "yyyy-MM-dd HH:mm:ss";
	public static final long Milliseconds_HOUR = (long) (1000 * 3600);
	public static final long Milliseconds_DAY = (long) (Milliseconds_HOUR * 24);

	/**
	 * 四位年份
	 * 
	 * @return
	 */
	public static int getYear() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.YEAR);
	}

	/**
	 * 根据日期得到四位年份
	 * 
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	/**
	 * 月份(1~12)
	 * 
	 * @return
	 */
	public static int getMonth() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.MONTH) + 1;
	}


	/**
	 * 日期转换为字符串
	 * 
	 * @param date
	 * @param pattern
	 */
	public static String formatDate(Date date, String pattern) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	public static String format(Date datetime) {
		return formatDate(datetime, defaultDatePattern);
	}

	public static final Date parseDate(String strDate, String datePattern) {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(datePattern);
		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
		}
		return date;
	}

    /**
     * 获得指定日期的起始时间
     * @param date
     * @return
     */
    public static Date getStartTimeOfDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获得指定日期的结束日期
     * @param date
     * @return
     */
    public static Date getEndTimeOfDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 得到日期后几天日期
     * @param date
     * @param num
     * @return
     */
    public static Date getDateNextDays(Date date, int num) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, num);
        return cal.getTime();
    }

}
