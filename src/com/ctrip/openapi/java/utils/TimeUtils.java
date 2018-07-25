package com.ctrip.openapi.java.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Time工具类
 * @author SHI
 * 2016年3月16日 19:07:06
 */
public class TimeUtils {

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_DATE    = new SimpleDateFormat("yyyy-MM-dd");

    private TimeUtils() {
        throw new AssertionError();
    }
    
    /**
     * 把timeInMillis转化成"yyyy-MM-dd HH:mm:ss"格式的时间字符串返回
     * @param timeInMillis  毫秒级时间
     * @return
     */
    public static String getTimeString(long timeInMillis) {
        return getTimeString(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    /**
     * 把timeInMillis转化成dateFormat格式的时间字符串返回
     * @param timeInMillis  毫秒级时间
     * @param dateFormat    
     * @return
     */
    public static String getTimeString(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }
    
    /**
     * 把字符串按照"yyyy-MM-dd HH:mm:ss"格式 转化成时间Date对象返回
     * @param time  时间字符串
     * @return
     */
    public static Date getTimeDate(String time){
		Date date = null;
		try {
			date = DEFAULT_DATE_FORMAT.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
    }
    
    /****
     * 把字符串按照指定格式 转化成时间Date对象返回
     * @param time  时间字符串
     * @param dateFormat  时间格式对象
     * @return
     */
    public static Date getTimeDate(String time, SimpleDateFormat dateFormat){
		Date date = null;
		try {
			date = dateFormat.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
    }
    
    /**
     * get current time in milliseconds
     * 
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    /**
     * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
     * 
     * @return
     */
    public static String getCurrentTimeInString() {
        return getTimeString(getCurrentTimeInLong());
    }

    /**
     * get current time in milliseconds
     * 
     * @return
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTimeString(getCurrentTimeInLong(), dateFormat);
    }
    
}





