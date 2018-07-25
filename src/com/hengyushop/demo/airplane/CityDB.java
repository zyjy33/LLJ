package com.hengyushop.demo.airplane;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CityDB {
	private Context context;
	private SQLiteDatabase db;

	public CityDB(Context context) {
		db = context
				.openOrCreateDatabase("city.db", Context.MODE_PRIVATE, null);
	}

	public String getCodes(String param) {
		String sql = "select sam from flyspace where cin='" + param + "'";
		Cursor cursor = db.rawQuery(sql, null);
		String temp = null;
		while (cursor.moveToNext()) {
			temp = cursor.getString(0);
		}
		cursor.close();
		db.close();

		return temp;
	}

	public String getJicBySam(String param) {
		String sql = "select jic from flyspace where sam='" + param + "'";
		Cursor cursor = db.rawQuery(sql, null);
		String r = "";
		while (cursor.moveToNext()) {
			r = cursor.getString(0);
		}
		cursor.close();
		db.close();
		return r;
	}

	/**
	 * ����������ӳ���������
	 * 
	 * @param param
	 * @return
	 */
	public String getCinBySam(String param) {
		String sql = "select cin from flyspace where sam='" + param + "'";
		Cursor cursor = db.rawQuery(sql, null);
		String r = "";
		while (cursor.moveToNext()) {
			r = cursor.getString(0);
		}
		cursor.close();
		db.close();
		return r;
	}

	/**
	 * ���ʡ��
	 * 
	 * @param sql
	 * @return
	 */
	public ArrayList<String> getProvince(String sql) {
		ArrayList<String> items = new ArrayList<String>();
		Cursor cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			items.add(cursor.getString(0));

		}
		cursor.close();
		db.close();
		return items;

	}

	public ArrayList<String> getProvinceW(String sql,boolean flag) {
		ArrayList<String> items = new ArrayList<String>();
		Cursor cursor = db.rawQuery(sql, null);
		if (flag) {
			items.add("����");
		}
		while (cursor.moveToNext()) {
			items.add(cursor.getString(0));

		}
		cursor.close();
		db.close();
		return items;

	}

	public String getName(String sql) {
		Cursor cursor = db.rawQuery(sql, null);
		String re = "";
		while (cursor.moveToNext()) {
			re = cursor.getString(0);

		}
		cursor.close();
		db.close();
		return re;
	}
}
