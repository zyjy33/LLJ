package com.lelinju.www;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.ui.CalendarPickerView;
import com.android.hengyu.ui.CalendarPickerView.SelectionMode;
import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.WareDao;
import com.hengyushop.db.SharedUtils;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.at.Common;
import com.hengyushop.entity.UserRegisterData;
import com.hengyushop.entity.UvDo;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.lelinju.www.R;

public class GoodCheckActivity extends BaseActivity {
	private LinearLayout content;
	// private Button btn1, btn2;
	private int SLIP = 0;
	private ArrayList<Object> arrayList = new ArrayList<Object>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.goods_check);
		init();
		// addTextView("��������");
		// addRadioButton(new String[] { "����1", "����2", "����3" });
		// addRatingBar();
		loadData();
	}

	/*
	 * DataR,DataC,DataT�ֱ��ǵ�ѡ�飬��ѡ�飬�ı���
	 */
	/**
	 * ��������
	 */
	private void loadData() {
		// http://www.wxpalm.com.cn//mi/getData.ashx?act=OneAdvertisingCommentsInfo&yth=admin&AdvertisingSerialNumber=GG2014514105828713
		Map<String, String> params = new HashMap<String, String>();
		String seri = getIntent().getStringExtra("seri");
		// seri = "GG2014515154313902";// ģ������
		System.out.println("seri:" + seri);
		params.put("advertisingSerialNumber", seri);
		params.put("act", "OneAdvertisingCommentsInfo");
		params.put("yth","admin");
		AsyncHttp.post_1(RealmName.REALM_NAME
				+ "/mi/getData.ashx",
				params, new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						System.out.println(arg1);
						parseDataToUI(arg1);
					}
				} );
	}

	/**
	 * ������������ӳ�䵽UI������
	 */
	private final String OBJECTR = "1";
	private final String OBJECTC = "2";
	private final String OBJECTT = "3";

	private void parseDataToUI(String result) {
		try {
			JSONObject jsonObject = new JSONObject(result);
			JSONArray objectR = jsonObject.getJSONArray("DataR");
			// ��ѡ����
			int lenR = objectR.length();
			for (int i = 0; i < lenR; i++) {
				JSONObject oR = objectR.getJSONObject(i);
				String titleR = oR.getString("CommentTitle");
				addTextView(titleR);
				JSONArray aR = oR.getJSONArray("CommentContents");
				int lenRA = aR.length();
				// 0��ʾbool�����������ͣ�1��ʾ��ͨ�������ͣ�2��ʾͼ�����͡�
				String type = oR.getString("CommentType");
				if (type.equals("0")) {
					// ��������
					ArrayList<UvDo> list = new ArrayList<UvDo>();
					for (int j = 0; j < lenRA; j++) {
						JSONObject oj = aR.getJSONObject(j);
						UvDo vDo = new UvDo();
						vDo.setId(oj.getString("AdvProOrderCommentR"));
						vDo.setContent(oj.getString("CommentContent"));
						vDo.setType(OBJECTR);
						list.add(vDo);
					}
					addSlipButton(list);
				} else if (type.equals("1")) {
					// ��������
					ArrayList<UvDo> list = new ArrayList<UvDo>();
					for (int j = 0; j < lenRA; j++) {
						JSONObject oj = aR.getJSONObject(j);
						UvDo vDo = new UvDo();
						vDo.setId(oj.getString("AdvProOrderCommentR"));
						vDo.setContent(oj.getString("CommentContent"));
						vDo.setType(OBJECTR);
						list.add(vDo);
					}
					addRadioButton(list);
				} else if (type.equals("2")) {
					// ͼ������

					ArrayList<UvDo> list = new ArrayList<UvDo>();
					for (int j = 0; j < lenRA; j++) {
						JSONObject oj = aR.getJSONObject(j);
						UvDo vDo = new UvDo();
						vDo.setId(oj.getString("AdvProOrderCommentR"));
						vDo.setContent(oj.getString("CommentContent"));
						vDo.setType(OBJECTR);
						list.add(vDo);
					}
					addRatingBar(list);
				}
			}
			// ��ѡ�¼�
			JSONArray objectC = jsonObject.getJSONArray("DataC");
			int lenC = objectC.length();
			for (int i = 0; i < lenC; i++) {
				// �����ѡ��ť
				JSONObject oC = objectC.getJSONObject(i);
				String title = oC.getString("CommentTitle");
				// ��������
				addTextView(title);
				JSONArray joc = oC.getJSONArray("CommentContents");
				int jocLen = joc.length();
				/*
				 * String values[] = new String[jocLen]; for (int j = 0; j <
				 * jocLen; j++) { JSONObject oj = joc.getJSONObject(j);
				 * values[j] = oj.getString("CommentContent"); }
				 */
				ArrayList<UvDo> list = new ArrayList<UvDo>();
				for (int j = 0; j < jocLen; j++) {
					JSONObject oj = joc.getJSONObject(j);
					UvDo vDo = new UvDo();
					vDo.setId(oj.getString("AdvProOrderCommentC"));
					vDo.setContent(oj.getString("CommentContent"));
					vDo.setType(OBJECTC);
					list.add(vDo);
				}
				addCheckBox(list);
			}
			// �����ṹ
			JSONArray objectT = jsonObject.getJSONArray("DataT");
			int lenT = objectT.length();
			/*
			 * for (int i = 0; i < lenT; i++) { JSONObject jbT =
			 * objectT.getJSONObject(i); String title =
			 * jbT.getString("CommentTitle"); addTextView(title); String type =
			 * jbT.getString("CommentTitleType");
			 * addEditView(Integer.parseInt(type)); }
			 */

			//
			// ArrayList<UvDo> list = new ArrayList<UvDo>();
			for (int j = 0; j < lenT; j++) {
				JSONObject oj = objectT.getJSONObject(j);
				String title = oj.getString("CommentTitle");
				addTextView(title);
				UvDo vDo = new UvDo();
				vDo.setId(oj.getString("AdvProOrderCommentT"));
				vDo.setContent("");
				vDo.setType(OBJECTT);
				String type = oj.getString("CommentTitleType");
				addEditView(vDo, Integer.parseInt(type));
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addSubmit();
	}

	/**
	 * ��ʼ���������
	 */
	private void init() {
		content = (LinearLayout) findViewById(R.id.content);
	}

	/**
	 * �����ύ��ť
	 */
	private void addSubmit() {
		LinearLayout submitLayout = new LinearLayout(getApplicationContext());
		submitLayout.setGravity(Gravity.CENTER);
		Button submitButton = new Button(getApplicationContext());
		submitButton.setText("�ύ");
		// submitButton.setEms(6);
		submitButton.setGravity(Gravity.CENTER);
		submitButton.setTextSize(18);
		submitButton.setTextColor(R.color.white);
		submitButton.setBackgroundResource(R.drawable.btn_add_shop_cart_select);
		submitLayout.addView(submitButton);
		content.addView(submitLayout);
		submitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				submitData();
			}
		});
	}

	/**
	 * �ύ��Ϣ�������� /mi/getData.ashx?act=SubmitOneAdvertisingComment&yth=0&
	 * AdvertisingSerialNumber=�����ˮ��
	 * &AdvProOrderCommentType=1,2,3&AdvProOrderCommentID
	 * =1,1,1&CommentContent=test,test,test
	 * ����AdvProOrderCommentType������ʾ�����������������Ϣ
	 * ��1��ʾ��ѡ�飬2��ʾ��ѡ�飬3��ʾ�ı��飩��AdvProOrderCommentID��ʾ��ͬ�������ID��
	 * ��������Ϣ�м��ö��Ÿ�����CommentContentΪ�ύ�ĵ������ݣ���������м��ö��Ÿ�����
	 */
	private void submitData() {
		ArrayList<String> param1 = new ArrayList<String>();
		ArrayList<String> param2 = new ArrayList<String>();
		ArrayList<String> param3 = new ArrayList<String>();
		for (int i = 0; i < arrayList.size(); i++) {
			Object o = arrayList.get(i);
			UvDo vDo = null;
			if (o instanceof RadioGroup) {
				// �ǵ�ѡ��
				vDo = (UvDo) ((RadioGroup) o).getTag();

				System.out.println(vDo.getContent() + "==Radio");
			} else if (o instanceof CheckBox) {
				vDo = (UvDo) ((CheckBox) o).getTag();
				System.out.println(vDo.getContent() + "==CheckBox");

			} else if (o instanceof RatingBar) {
				vDo = (UvDo) ((RatingBar) o).getTag();
				System.out.println(vDo.getContent() + "==RatingBar");
			} else if (o instanceof TextView) {
				vDo = (UvDo) ((TextView) o).getTag();
				System.out.println(vDo.getContent() + "==TextView");

			} else if (o instanceof EditText) {

				vDo = (UvDo) ((EditText) o).getTag();
				System.out.println(((EditText) o).getText().toString()
						+ "==EditText");
			}
			param1.add(vDo.getId());
			param2.add(vDo.getType());
			param3.add(vDo.getContent());
		}
		WareDao wareDao = new WareDao(getApplicationContext());
		UserRegisterData registerData = wareDao.findIsLoginHengyuCode();
		String yth = registerData.getHengyuCode();
		String p1 = param1.toString().replace("[", "").replace("]", "");
		String p2 = param2.toString().replace("[", "").replace("]", "");
		String p3 = param3.toString().replace("[", "").replace("]", "");
		/*
		 * /mi/getData.ashx?act=SubmitOneAdvertisingComment&yth=������
		 * &AdvertisingSerialNumber=�����ˮ��&ClientType=1&
		 * CurrentAddress=��ǰ��λ�õ�ַ&AdvProOrderCommentType=1,2,3&
		 * AdvProOrderCommentID=1,1,1&CommentContent=test,test,test
		 */
//		yth = "admin";
		if (yth == null || yth.length() == 0) {
			Toast.makeText(getApplicationContext(), "�û�δ��¼", 200).show();
		} else {
			Map<String, String> params = new HashMap<String, String>();
			params.put("AdvProOrderCommentType", p2);
			params.put("AdvProOrderCommentID", p1);
			params.put("CommentContent", p3);
			params.put("yth", yth);
			params.put("AdvertisingSerialNumber",
					getIntent().getStringExtra("seri"));
			params.put("ClientType", "1");
			SharedUtils utils = new SharedUtils(getApplicationContext(),
					Common.locationName);
			params.put(
					"CurrentAddress",
					utils.getStringValue("dProvince")
							+ utils.getStringValue("dCity")
							+ utils.getStringValue("dDistrict"));
			params.put("act", "SubmitOneAdvertisingComment");
			AsyncHttp.post_1(RealmName.REALM_NAME
					+ "/mi/getData.ashx",
					params, new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {
							// TODO Auto-generated method stub
							super.onSuccess(arg0, arg1);
							System.out.println(arg1);
							try {
								JSONObject object = new JSONObject(arg1);
								String smg = object.getString("msg");
								Message msg = new Message();
								msg.what = 0;
								msg.obj = smg;
								handler.sendMessage(msg);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} );
		}

	}

	private Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(getApplicationContext(), (String) msg.obj, 200)
						.show();
				AppManager.getAppManager().finishActivity();
				break;

			default:
				break;
			}
		};
	};

	private void setSubmit(Object object) {
		arrayList.add(object);
	}

	private void removeSubmit(Object object) {
		arrayList.remove(object);
	}

	/**
	 * ����TextView���
	 * 
	 * @param textValue
	 */
	private void addTextView(String textValue) {
		LinearLayout textViewLayout = new LinearLayout(getApplicationContext());
		TextView textView = new TextView(getApplicationContext());
		textView.setText(textValue);
		textView.setTextSize(22);
		textView.setTextColor(R.color.black);
		textViewLayout.setPadding(10, 5, 10, 2);
		textViewLayout.addView(textView);
		content.addView(textViewLayout);
	}

	/**
	 * ���ɵ�ѡ��ť��
	 */
	private void addRadioButton(final ArrayList<UvDo> list) {
		LinearLayout radioButtonLayout = new LinearLayout(
				getApplicationContext());
		radioButtonLayout.setOrientation(LinearLayout.HORIZONTAL);
		radioButtonLayout.setPadding(20, 2, 20, 2);
		LinearLayout oneLayout = new LinearLayout(getApplicationContext());
		LinearLayout twoLayout = new LinearLayout(getApplicationContext());
		// twoLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
		// LayoutParams.MATCH_PARENT));
		oneLayout.setOrientation(LinearLayout.VERTICAL);
		twoLayout.setOrientation(LinearLayout.VERTICAL);
		int len = list.size();// ������Ҫ����len��Radiobutton��len��textview���
		// �ȴ���len����ѡ��ť

		RadioGroup group = new RadioGroup(getApplicationContext());
		setSubmit(group);
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (arrayList.contains(group)) {
					arrayList.remove(group);
				}
				group.setTag(list.get(checkedId - 1));
				arrayList.add(group);
			}
		});
		for (int i = 0; i < len; i++) {
			// ��������radiobutton���,��װ���ڲ�����
			RadioButton button = new RadioButton(getApplicationContext());

			group.addView(button);
			button.setId(i + 1);
			// ͬ�´�������textview������ڲ�����
			TextView textView = new TextView(getApplicationContext());
			textView.setText(list.get(i).getContent());
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, 0, 1);
			textView.setLayoutParams(params);
			textView.setGravity(Gravity.CENTER_VERTICAL);
			twoLayout.addView(textView);
		}
		LinearLayout.LayoutParams paramsTwo = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, len);
		twoLayout.setLayoutParams(paramsTwo);
		oneLayout.addView(group);
		radioButtonLayout.addView(oneLayout);
		radioButtonLayout.addView(twoLayout);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(10, 1, 10, 1);
		radioButtonLayout.setLayoutParams(params);
		radioButtonLayout.setBackgroundResource(R.drawable.round);
		content.addView(radioButtonLayout);
	}

	/**
	 * ������ѡ������
	 */
	private void addCheckBox(ArrayList<UvDo> list) {
		LinearLayout checkBoxLayout = new LinearLayout(getApplicationContext());
		checkBoxLayout.setOrientation(LinearLayout.HORIZONTAL);
		checkBoxLayout.setPadding(20, 2, 20, 2);

		LinearLayout oneLayout = new LinearLayout(getApplicationContext());
		LinearLayout twoLayout = new LinearLayout(getApplicationContext());
		// twoLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
		// LayoutParams.MATCH_PARENT));
		oneLayout.setOrientation(LinearLayout.VERTICAL);
		twoLayout.setOrientation(LinearLayout.VERTICAL);
		int len = list.size();// ������Ҫ����len��Radiobutton��len��textview���
		// �ȴ���len����ѡ��ť
		for (int i = 0; i < len; i++) {
			// ��������radiobutton���,��װ���ڲ�����
			final CheckBox button = new CheckBox(getApplicationContext());
			// ͬ�´�������textview������ڲ�����
			button.setTag(list.get(i));
			oneLayout.addView(button);

			TextView textView = new TextView(getApplicationContext());
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, 0, 1);
			textView.setLayoutParams(params);
			textView.setGravity(Gravity.CENTER_VERTICAL);
			textView.setText(list.get(i).getContent());
			twoLayout.addView(textView);
			button.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					// TODO Auto-generated method stub
					if (arg1) {
						arrayList.add(button);
					} else {
						arrayList.remove(button);
					}

				}
			});
		}
		LinearLayout.LayoutParams paramsTwo = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, len);
		twoLayout.setLayoutParams(paramsTwo);
		checkBoxLayout.addView(oneLayout);
		checkBoxLayout.addView(twoLayout);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(10, 1, 10, 1);
		checkBoxLayout.setLayoutParams(params);
		checkBoxLayout.setBackgroundResource(R.drawable.round);
		content.addView(checkBoxLayout);
	}

	/**
	 * �����Ǽ�ͶƱ��ǩ
	 */
	private void addRatingBar(final ArrayList<UvDo> list) {
		LinearLayout ratingBarLayout = new LinearLayout(getApplicationContext());
		ratingBarLayout.setOrientation(LinearLayout.HORIZONTAL);
		ratingBarLayout.setPadding(20, 2, 20, 2);

		// twoLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
		// LayoutParams.MATCH_PARENT));
		int len = list.size();// ������Ҫ����len��Radiobutton��len��textview���
		// �ȴ���len����ѡ��ť
		// ��������radiobutton���,��װ���ڲ�����
		final RatingBar button = new RatingBar(getApplicationContext());
		// ͬ�´�������textview������ڲ�����

		button.setNumStars(len);
		button.setStepSize(1);
		ratingBarLayout.addView(button);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(10, 1, 10, 1);
		ratingBarLayout.setLayoutParams(params);
		ratingBarLayout.setGravity(Gravity.CENTER_VERTICAL);
		ratingBarLayout.setBackgroundResource(R.drawable.round);
		final TextView textView = new TextView(getApplicationContext());
		ratingBarLayout.addView(textView);
		textView.setTextSize(16);
		button.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				// TODO Auto-generated method stub
				textView.setText(list.get((int) rating - 1).getContent());
				System.out.println("button-->"
						+ list.get((int) rating - 1).getContent());
				button.setTag(list.get((int) rating - 1));

				if (arrayList.contains(button)) {
					arrayList.remove(button);
				}
				arrayList.add(button);

			}
		});
		content.addView(ratingBarLayout);
	}

	/**
	 * ����EditView
	 */
	private CalendarPickerView calendar;
	private CalendarPickerView dialogView;
	private AlertDialog theDialog;

	/*
	 * 0��ʾ�ش������Ϊ��ͨ�ı����ɣ�1��ʾ�ش�����ݱ�������������͵Ĺ淶��2��ʾ�ش�����ݱ�������ֻ��������͹淶
	 */
	private void addEditView(final UvDo vDo, int type) {
		// type 0��ͨ 1������ 2 ������
		LinearLayout editViewLayout = new LinearLayout(getApplicationContext());
		editViewLayout.setPadding(20, 2, 20, 2);
		if (type == 1) {
			final TextView editTextData = new TextView(getApplicationContext());
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			editTextData.setText("��������");
			editTextData.setLayoutParams(params);

			editTextData.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					dialogView = (CalendarPickerView) getLayoutInflater()
							.inflate(R.layout.dialog, null, false);
					final Calendar nextYear = Calendar.getInstance();
					nextYear.add(Calendar.YEAR, 1);

					final Calendar lastYear = Calendar.getInstance();
					lastYear.add(Calendar.YEAR, -1);

					calendar = (CalendarPickerView) dialogView
							.findViewById(R.id.calendar_view);
					calendar.init(lastYear.getTime(), nextYear.getTime()) //
							.inMode(SelectionMode.SINGLE) //
							.withSelectedDate(new Date());

					dialogView.init(lastYear.getTime(), nextYear.getTime()) //
							.withSelectedDate(new Date());
					theDialog = new AlertDialog.Builder(GoodCheckActivity.this)

							.setView(dialogView)
							.setNeutralButton("ѡ��",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialogInterface,
												int i) {
											SimpleDateFormat formatter = new SimpleDateFormat(
													"yyyy-MM-dd");
											editTextData.setText(formatter
													.format(calendar
															.getSelectedDate()));
											vDo.setContent(formatter
													.format(calendar
															.getSelectedDate()));
											editTextData.setTag(vDo);
											if (arrayList
													.contains(editTextData)) {
												arrayList.remove(editTextData);
											}
											arrayList.add(editTextData);

											dialogInterface.dismiss();
										}
									}).create();
					theDialog
							.setOnShowListener(new DialogInterface.OnShowListener() {
								@Override
								public void onShow(
										DialogInterface dialogInterface) {
									dialogView.fixDialogDimens();
								}
							});
					theDialog.show();
				}
			});
			// editText.setInputType(InputType.TYPE_CLASS_DATETIME);
			editViewLayout.addView(editTextData);
			editTextData.setBackgroundResource(R.drawable.round);
		} else if (type == 2) {
			final EditText editText = new EditText(getApplicationContext());
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			editText.setLayoutParams(params);
			editText.setLines(3);
			editText.setGravity(Gravity.TOP | Gravity.LEFT);
			editText.setInputType(InputType.TYPE_CLASS_PHONE);
			editViewLayout.addView(editText);
			editText.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
					// TODO Auto-generated method stub

				}

				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
					// TODO Auto-generated method stub

				}

				@Override
				public void afterTextChanged(Editable arg0) {
					// TODO Auto-generated method stub
					vDo.setContent(arg0.toString());
					editText.setTag(vDo);
					if (arrayList.contains(editText)) {
						arrayList.remove(editText);
					}
					arrayList.add(editText);
				}
			});
			// editText.setTag(vDo);
			// arrayList.add(editText);
			editText.setBackgroundResource(R.drawable.round);
		} else if (type == 0) {
			final EditText editText = new EditText(getApplicationContext());
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			editText.setLayoutParams(params);
			editText.setLines(3);
			editText.setInputType(InputType.TYPE_CLASS_TEXT);
			editText.setGravity(Gravity.TOP | Gravity.LEFT);
			editText.setInputType(InputType.TYPE_CLASS_TEXT);
			editText.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					// TODO Auto-generated method stub

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub

				}

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					vDo.setContent(s.toString());
					editText.setTag(vDo);
					if (arrayList.contains(editText)) {
						arrayList.remove(editText);
					}
					arrayList.add(editText);
				}
			});
			// editText.setTag(vDo);
			// arrayList.add(editText);
			editViewLayout.addView(editText);
			editText.setBackgroundResource(R.drawable.round);
		}

		content.addView(editViewLayout);
	}

	/**
	 * ����SlipButton
	 */
	private void addSlipButton(ArrayList<UvDo> list) {
		LinearLayout slipButtonLayout = new LinearLayout(
				getApplicationContext());

		slipButtonLayout.setPadding(20, 2, 20, 2);

		LinearLayout btns = new LinearLayout(getApplicationContext());
		btns.setPadding(5, 2, 5, 2);
		btns.setOrientation(LinearLayout.HORIZONTAL);
		// LinearLayout.LayoutParams lp = new
		// LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,50);
		final TextView btn1 = new TextView(getApplicationContext());
		final TextView btn2 = new TextView(getApplicationContext());
		btn1.setBackgroundResource(R.drawable.rating_btn);
		btn2.setBackgroundResource(R.drawable.rating_btn);
		// btns.setLayoutParams(lp);
		btns.setGravity(Gravity.CENTER_VERTICAL);
		btns.setBackgroundResource(R.drawable.rating_bg);
		btns.addView(btn1);
		btns.addView(btn2);
		btn1.setGravity(Gravity.CENTER);
		btn2.setGravity(Gravity.CENTER);
		btn1.setText(list.get(0).getContent());
		btn2.setText(list.get(1).getContent());
		// Ĭ�������btn1��ʾ
		btn1.setTag(list.get(0));
		btn2.setTag(list.get(1));
		btn1.setVisibility(View.VISIBLE);
		btn2.setVisibility(View.INVISIBLE);
		SLIP = 0;
		arrayList.add(btn1);
		btns.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				System.out.println("���" + btn1.VISIBLE);
				if (SLIP == 0) {
					btn1.setVisibility(View.INVISIBLE);
					btn2.setVisibility(View.VISIBLE);
					if (arrayList.contains(btn1)) {
						arrayList.remove(btn1);

					}
					if (arrayList.contains(btn2)) {
						arrayList.remove(btn2);
					}
					arrayList.add(btn2);
					System.out.println(((UvDo) btn2.getTag()).getContent()
							+ "!" + arrayList.contains(btn2));
					SLIP = -1;
				} else {
					btn1.setVisibility(View.VISIBLE);
					btn2.setVisibility(View.INVISIBLE);
					if (arrayList.contains(btn1)) {
						arrayList.remove(btn1);

					}
					if (arrayList.contains(btn2)) {
						arrayList.remove(btn2);
					}
					arrayList.add(btn1);
					System.out.println(((UvDo) btn1.getTag()).getContent()
							+ "!" + arrayList.contains(btn1));
					SLIP = 0;
				}
			}
		});
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(10, 1, 10, 1);
		slipButtonLayout.setLayoutParams(params);
		// slipButtonLayout.setBackgroundResource(R.drawable.round);
		slipButtonLayout.addView(btns);
		content.addView(slipButtonLayout);
	}

}
