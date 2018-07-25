package com.hengyushop.entity;

import java.io.Serializable;

import android.R.integer;

/**
 * 用户注册的bwan数据
 * @author Administrator
 *
 */
public class UserRegisterllData implements Serializable{
	
	public String id;
	public String user_code;
	public String user_name;
	public String agency_name;
	public String login_sign;
	public int agency_id;
	public String amount;
	public String pension;
	public String packet;
	public String point;
	public String group_id;
	public String group_name;
	public int reserves;
	public double hongbao;
	public String exp;
	public int reserveb;
	public String avatar;
	public String mobile;
	public String real_name;
	public String province;
	public String city;
	public String area;
	public String getExp() {
		return exp;
	}
	public void setExp(String exp) {
		this.exp = exp;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPension() {
		return pension;
	}
	public void setPension(String pension) {
		this.pension = pension;
	}
	public String getPacket() {
		return packet;
	}
	public void setPacket(String packet) {
		this.packet = packet;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser_code() {
		return user_code;
	}
	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	

}
