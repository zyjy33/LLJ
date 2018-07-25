package com.hengyushop.db;

import java.util.ArrayList;

import com.hengyushop.demo.at.Common;
import com.lglottery.www.domain.CategoryDomain;
import com.lglottery.www.domain.DistrictsDomain;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ComboDB {
	private SQLiteDatabase db;

	public ComboDB(Context context) {
		// TODO Auto-generated constructor stub
		db = context.openOrCreateDatabase(Common.DB_NAME, Context.MODE_PRIVATE,
				null);
	}

	/**
	 * �õ����͵Ĵ���
	 * 
	 * @return
	 */
	public ArrayList<CategoryDomain> getCategoryDomains() {
		ArrayList<CategoryDomain> domains = new ArrayList<CategoryDomain>();
		String sql = "SELECT * FROM DEAL_CATEGORIES WHERE PARENTDEALCATEGORYID='0'";
		Cursor cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			CategoryDomain domain = new CategoryDomain();
			domain.setCategoryId(cursor.getString(0));
			domain.setCategoryName(cursor.getString(1));
			domains.add(domain);
		}
		db.close();
		return domains;
	}
	/**
	 * �õ����͵�С��
	 */
	public ArrayList<CategoryDomain> getCategoryDomainsChilds(String parentID) {
		ArrayList<CategoryDomain> domains = new ArrayList<CategoryDomain>();
		String sql = "select * from Deal_Categories where ParentDealCategoryID='"
				+ parentID + "'";
		Cursor cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			CategoryDomain domain = new CategoryDomain();
			domain.setCategoryId(cursor.getString(0));
			domain.setCategoryName(cursor.getString(1));
			domains.add(domain);
		}
		db.close();
		return domains;
	}

	/**
	 * ���ҹ��ڳ�������
	 */
	public ArrayList<DistrictsDomain> getDistrictsDomains(String cityId) {
		ArrayList<DistrictsDomain> districts = new ArrayList<DistrictsDomain>();
		String sql = "SELECT * FROM DEAL_DISTRICTS WHERE CITYID='" + cityId
				+ "'";
		Cursor cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			DistrictsDomain district = new DistrictsDomain();
			district.setId(cursor.getString(0));
			district.setCityId(cursor.getString(1));
			district.setName(cursor.getString(2));
			districts.add(district);
		}
		return districts;
	}
	/**
	 * ���ҹ��ڳ������������
	 */
	public ArrayList<DistrictsDomain> getDistrictsDomainsChilds(String id) {
		ArrayList<DistrictsDomain> districts = new ArrayList<DistrictsDomain>();
		String sql = "SELECT * FROM DEAL_NEIGHBORHOODS WHERE DISTRICTID='" + id
				+ "'";
		System.out.println(sql);
		Cursor cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			DistrictsDomain district = new DistrictsDomain();
			district.setId(cursor.getString(0));
			district.setCityId(cursor.getString(1));
			district.setName(cursor.getString(2));
			districts.add(district);
		}
		return districts;
	}
}