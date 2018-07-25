package com.hengyushop.db;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedUtils {
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;

	public SharedUtils(Context context, String NAME) {
		// TODO Auto-generated constructor stub
		preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		editor = preferences.edit();
	}

	public void clear() {
		editor.clear();
	}

	public boolean isLogin(String tag) {
		System.out.println(preferences.contains(tag));
		return preferences.contains(tag);
	}

	/**
	 * ����String���͵�sharedpreferences
	 * 
	 * @param key
	 * @return
	 */
	public void setStringValue(String key, String value) {
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * ���String���͵�sharedpreferences
	 * 
	 * @param key
	 * @return
	 */
	public String getStringValue(String key) {
		return preferences.getString(key, "");
	}

	/**
	 * ����boolean���͵�sharedpreferences
	 * 
	 * @param key
	 * @return
	 */
	public void setBooleanValue(String key, boolean value) {
		editor.putBoolean(key, value);
		editor.commit();
	}

	/**
	 * get all stringsS
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<String> getAllString() {
		Map<String, String> map = (Map<String, String>) preferences.getAll();
		Iterator<String> iterator = map.keySet().iterator();
		ArrayList<String> strs = new ArrayList<String>();
		while (iterator.hasNext()) {
			strs.add(iterator.next());
		}
		return strs;

	}

	/**
	 * ���boolean���͵�sharedpreferences
	 * 
	 * @param key
	 * @return
	 */
	public Boolean getBooleanValue(String key) {
		// Ĭ��Ϊfalse
		return preferences.getBoolean(key, false);
	}
}
