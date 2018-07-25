package com.ctrip.openapi.java.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Time������
 * @author SHI
 * 2016��3��16�� 19:07:06
 */
public class TimeUtils {

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_DATE    = new SimpleDateFormat("yyyy-MM-dd");

    private TimeUtils() {
        throw new AssertionError();
    }
    
    /**
     * ��timeInMillisת����"yyyy-MM-dd HH:mm:ss"��ʽ��ʱ���ַ�������
     * @param timeInMillis  ���뼶ʱ��
     * @return
     */
    public static String getTimeString(long timeInMillis) {
        return getTimeString(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    /**
     * ��timeInMillisת����dateFormat��ʽ��ʱ���ַ�������
     * @param timeInMillis  ���뼶ʱ��
     * @param dateFormat    
     * @return
     */
    public static String getTimeString(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }
    
    /**
     * ���ַ�������"yyyy-MM-dd HH:mm:ss"��ʽ ת����ʱ��Date���󷵻�
     * @param time  ʱ���ַ���
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
     * ���ַ�������ָ����ʽ ת����ʱ��Date���󷵻�
     * @param time  ʱ���ַ���
     * @param dateFormat  ʱ���ʽ����
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





