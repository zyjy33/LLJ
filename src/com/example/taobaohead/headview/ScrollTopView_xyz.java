package com.example.taobaohead.headview;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.taobaohead.BeanVo;
import com.example.taobaohead.inteface.OnAdapterClickListener;
import com.hengyushop.demo.home.JuTuanGou2Activity;
import com.lelinju.www.R;

/*
 * �����Ա� ��ͷ��
 * 
 */
public class ScrollTopView_xyz extends LinearLayout {

	private Scroller mScroller;  //����ʵ��

	private List<BeanVo> articleList;  //������ݼ���
	private final int DURING_TIME = 3000;  //�����ӳ�
	private OnAdapterClickListener<BeanVo> click;  

	public ScrollTopView_xyz(Context context) {
		super(context);
		init();
	}

	public ScrollTopView_xyz(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mScroller = new Scroller(getContext());
	}

	/**
	 * ��������
	 * @param articleList
	 */
	public void setData(List<BeanVo> articleList) {
		this.articleList = articleList;
		if (articleList != null) {
			removeAllViews();
			Log.i("tag", articleList.size() + "");
			int size = articleList.size() > 1 ? 2 : articleList.size();
			for (int i = 0; i < size; i++) {
				addContentView(i);
			}
			if (articleList.size() > 2) {
				getLayoutParams().height = Utils.dip2px(20);  //���ڹ������ݵĸ߶�
				// ����
				cancelAuto();
				mHandler.sendEmptyMessageDelayed(0, DURING_TIME);
				smoothScrollBy(0, Utils.dip2px(50));
			}
		}
	}

	/**
	 * �����б����¼�
	 * 
	 * @param click
	 */
	public void setClickListener(OnAdapterClickListener<BeanVo> click) {
		this.click = click;
	}

	/**
	 * ��������
	 */
	private void resetView() {
		BeanVo article = articleList.get(0);
		articleList.remove(0);
		articleList.add(article);

		for (int i = 0; i < 2; i++) {
			addContentView(i);
		}
	}

	/**
	 * ȡ������
	 */
	public void cancelAuto() {
		mHandler.removeMessages(0);
	}

	private void addContentView(int position) {
		ViewHolder mHolder;
		if (position >= getChildCount()) {
			mHolder = new ViewHolder();
			View v = View.inflate(getContext(), R.layout.myhead, null);
			mHolder.nameTv = (TextView) v.findViewById(R.id.tv);
			v.setTag(mHolder);
			addView(v, LayoutParams.MATCH_PARENT, Utils.dip2px(50));
		} else {
			mHolder = (ViewHolder) getChildAt(position).getTag();
		}
		
		final BeanVo article = articleList.get(position);
		
//		System.out.println("JuTuanGou2Activity.type--------1---------"+JuTuanGou2Activity.type);
//		if (JuTuanGou2Activity.type == true) {
//			System.out.println("article.groupon_title-----------------"+article.groupon_title);
			//"��ϲ�������ǡ� �����й��ƶ�����100Ԫ��ֵ�� "
		String haoma_ll = article.mobile;
		String haoma = haoma_ll.substring(0, 3) + "****" + haoma_ll.substring(7, 11);
			mHolder.nameTv.setText("��ϲ"+"��"+haoma+"���û�,����"+article.lottery_title);
//		}else {
//			System.out.println("article.title-----------------"+article.title);
//			mHolder.nameTv.setText(article.title);
//		}
//		System.out.println("JuTuanGou2Activity.type--------2---------"+JuTuanGou2Activity.type);
		
//		String zhou = "��ϲ�����н�";
//		System.out.println("zhou-----------------"+zhou);
//		mHolder.nameTv.setText(zhou);
		
		mHolder.nameTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (click != null) {
					click.onAdapterClick(null, article);
				}
				// if(null != article){
				// Intent intent = new Intent(getContext(),
				// CareHairDetailActivity.class);
				// intent.putExtra("id", article.getId());
				// intent.putExtra("name", article.getName());
				// getContext().startActivity(intent);
				// }
			}
		});
	}

	private class ViewHolder {
		TextView nameTv;
	}

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			mHandler.removeMessages(0);
			mHandler.sendEmptyMessageDelayed(0, DURING_TIME);
			smoothScrollBy(0, Utils.dip2px(50));
			resetView();
		};
	};

	// ���ô˷������ù��������ƫ��
	public void smoothScrollBy(int dx, int dy) {
		// ����mScroller�Ĺ���ƫ����
		mScroller.startScroll(mScroller.getFinalX(), 0, dx, dy, DURING_TIME);
		invalidate();// ����������invalidate()���ܱ�֤computeScroll()�ᱻ���ã�����һ����ˢ�½��棬����������Ч��
	}

	@Override
	public void computeScroll() {

		// ���ж�mScroller�����Ƿ����
		if (mScroller.computeScrollOffset()) {

			// �������View��scrollTo()���ʵ�ʵĹ���
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			// ������ø÷���������һ���ܿ�������Ч��
			postInvalidate();

		}
		super.computeScroll();
	}
}
