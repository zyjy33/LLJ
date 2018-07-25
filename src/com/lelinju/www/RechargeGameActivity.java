package com.lelinju.www;

import java.util.ArrayList;
import java.util.List;

import com.android.hengyu.ui.MyPopupWindowMenu;
import com.hengyushop.demo.at.BaseActivity;
import com.lelinju.www.R;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class RechargeGameActivity extends BaseActivity implements OnClickListener {

	Spinner sp_game_name, sp_game_money;
	Button btn_game;
	private LinearLayout ll_information;
	List<String> game_name;
	List<String> game_money;
	String name;
	String money;
	private LayoutInflater inflater;
	private View view;
	private PopupWindow pop;
	private TextView tv_name, tv_money;
	private MyPopupWindowMenu popupWindowMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recharge_game_card);
		popupWindowMenu = new MyPopupWindowMenu(this);
		btn_game = (Button) findViewById(R.id.btn_game);
		GameData();
		getGameData();

		// btn_game.setOnClickListener(this);
	}

	private void GameData() {
		sp_game_name = (Spinner) findViewById(R.id.sp_game_name);
		sp_game_money = (Spinner) findViewById(R.id.sp_game_money);
		sp_game_name.setPrompt("��ѡ����Ϸ����");
		sp_game_money.setPrompt("��ѡ���ֵ���");

		ll_information = (LinearLayout) findViewById(R.id.ll_information);
		ll_information.setVisibility(View.GONE);

		String[] str3 = new String[] { "�λ�����", "ħ������", "����֮��", "�漣����", "��;",
				"��Ѫ����", "��������", "������2", "���ƺ���", "�����˲�", "��������", "ħ��", "����2",
				"�λ�����", "����3", "��Ѫ����", "�ʵ�", "������", "��������", "��������", "�����⴫",
				"���������", "������Ե2", "�����", "QQ����", "������", "ð�յ�", "ˮ�Q��", "�ʺ絺",
				"��ͷ����", "���ܿ�����", "����ȺӢ��", "���춯��", "��������", "�λù���", "�λù���",
				"����2", "����", "׿Խ֮��", "����2", "��������", "����Q��", "�������", "��Ӣ�����",
				"�ų�֮Ұ��", "�����ɶ�", "���", "�������⴫", "SD�Ҵ�", "��Խ����", "QQ���ɻ���",
				"QQ����", "����", "�����⴫", "�����2", "����", "��������˫", "�ڴ�����", "���³�����ʿ",
				"�ǳ���˵", "�����", "��Ѫ����", "����", "LUNA(¶��)", "�ʵ�" };
		game_name = new ArrayList<String>();
		for (int i = 0; i < str3.length; i++) {
			game_name.add(str3[i]);
		}
		ArrayAdapter aa3 = new ArrayAdapter(this,
				android.R.layout.simple_spinner_item, game_name);
		aa3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_game_name.setAdapter(aa3);

		String[] str4 = new String[] { "5Ԫ", "10Ԫ", "20Ԫ", "30Ԫ", "50Ԫ",
				"100Ԫ", "150Ԫ", "200Ԫ" };
		game_money = new ArrayList<String>();
		for (int i = 0; i < str4.length; i++) {
			game_money.add(str4[i]);
		}
		ArrayAdapter aa4 = new ArrayAdapter(this,
				android.R.layout.simple_spinner_item, game_money);
		aa4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_game_money.setAdapter(aa4);
	}

	private void getGameData() {

		sp_game_name.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				name = game_name.get(arg2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		sp_game_money.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				money = game_money.get(arg2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_game:

			break;

		default:
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("menu");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {

		if (0 == popupWindowMenu.currentState && popupWindowMenu.isShowing()) {
			popupWindowMenu.dismiss(); // �Ի�����ʧ
			popupWindowMenu.currentState = 1; // ���״̬������ʧ
		} else {
			popupWindowMenu
					.showAtLocation(findViewById(R.id.recharge_game_card),
							Gravity.BOTTOM, 0, 0);
			popupWindowMenu.currentState = 0; // ���״̬����ʾ��
		}
		return false; // true--��ʾϵͳ�Դ��˵���false--����ʾ��
	}

}
