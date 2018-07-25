package com.zihao.db;

import java.util.ArrayList;
import java.util.List;

import com.ctrip.openapi.java.utils.PinYin;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 鏁版嵁搴撴搷浣滅被
 * 
 * @author zihao
 * 
 */
public class DatabaseAdapter {

	private static DatabaseManager manager;
	private static Context mContext;

	/**
	 * 鑾峰彇涓涓搷浣滅被瀵硅薄
	 * 
	 * @param context
	 * @return
	 */
	public static DatabaseAdapter getIntance(Context context) {
		DatabaseAdapter adapter = new DatabaseAdapter();
		mContext = context;
		manager = DatabaseManager.getInstance(new DatabaseHelper(mContext));
		return adapter;
	}

	/**
	 * 鎻掑叆淇℃伅
	 * 
	 * @param titleArray
	 */
	public void inserInfo(List<String> titleArray) {
		SQLiteDatabase database = manager.getWritableDatabase();

		try {
			for (String title : titleArray) {
				ContentValues values = new ContentValues();
				values.put("title", title);
				values.put("pinyin", PinYin.getPinYin(title));// 璁插唴瀹硅浆鎹负鎷奸煶
				database.insert(DatabaseHelper.TABLE_NAME, null, values);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			manager.closeDatabase();
		}
	}

	/**
	 * 鏌ヨ淇℃伅
	 * 
	 * @param pinyin
	 *            // 瀛楃涓茶浆鎹㈢殑鎷奸煶
	 * @return
	 */
	public List<String> queryInfo(String pinyin) {
		List<String> resultArray = new ArrayList<String>();
		SQLiteDatabase database = manager.getReadableDatabase();
		Cursor cursor = null;

		try {
			// 鍒涘缓妯＄硦鏌ヨ鐨勬潯浠
			String likeStr = "'";
			for (int i = 0; i < pinyin.length(); i++) {
				if (i < pinyin.length() - 1) {
					likeStr += "%" + pinyin.charAt(i);
				} else {
					likeStr += "%" + pinyin.charAt(i) + "%'";
				}
			}

			cursor = database.rawQuery("select * from "
					+ DatabaseHelper.TABLE_NAME + " where pinyin like "
					+ likeStr, null);

			while (cursor.moveToNext()) {
				resultArray
						.add(cursor.getString(cursor.getColumnIndex("title")));
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.toString();
		} finally {
			manager.closeDatabase();
		}

		return resultArray;
	}

	/**
	 * 鍒犻櫎琛ㄤ腑鐨勬墍鏈夋暟鎹
	 */
	public void deleteAll() {
		SQLiteDatabase database = manager.getWritableDatabase();

		try {
			database.delete(DatabaseHelper.TABLE_NAME, null, null);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			manager.closeDatabase();
		}
	}
}