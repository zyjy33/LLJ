package com.ctrip.openapi.java.utils;

import java.util.regex.Pattern;

/**
 * У����������������ʽУ�����䡢�ֻ��ŵ�
 * @author Mr.duan
 */
public class Validator {
    /**
     * ������ʽ:��֤�û���(���������ĺ������ַ�)����û���ʹ���ֻ���������� �����ֻ�����֤��������֤
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";
 
    /**
     * ������ʽ:��֤����(�����������ַ�)
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";
 
    /**
     * ������ʽ:��֤�ֻ���
     */
//    public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4])|(18[0,1,2,3,5-9])|(17[0-9])|(147))\\d{8}$";  
    
 
    /**
     * ������ʽ:��֤����
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
 
    /**
     * ������ʽ:��֤����(1-9������)  {1,9} �Զ�������
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5]{1,9}$";
 
    /**
     * ������ʽ:��֤���֤
     */
    public static final String REGEX_ID_CARD = "(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])";
 
    /**
     * ������ʽ:��֤URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
 
    /**
     * ������ʽ:��֤IP��ַ 
     */
    public static final String REGEX_IP_ADDR = "(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})";
 
    /**
     * У���û���
     * 
     * @param username
     * @return У��ͨ������true�����򷵻�false
     */
    public static boolean isUserName(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }
 
    /**
     * У������
     * 
     * @param password
     * @return У��ͨ������true�����򷵻�false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }
 
    /**
     * У���ֻ���
     * 
     * @param mobile
     * @return У��ͨ������true�����򷵻�false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }
 
    /**
     * У������
     * 
     * @param email
     * @return У��ͨ������true�����򷵻�false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }
 
    /**
     * У�麺��
     * 
     * @param chinese
     * @return У��ͨ������true�����򷵻�false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }
 
    /**
     * У�����֤
     * 
     * @param idCard
     * @return У��ͨ������true�����򷵻�false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }
 
    /**
     * У��URL
     * 
     * @param url
     * @return У��ͨ������true�����򷵻�false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }
 
    /**
     * У��IP��ַ
     * 
     * @param ipAddress
     * @return
     */
    public static boolean isIPAddress(String ipAddress) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddress);
    }
   
}
