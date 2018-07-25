package com.example.zhuanpan;

import com.hengyushop.demo.home.ZhuanYiZhuanActivity;
import com.lelinju.www.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class LuckyPan extends SurfaceView implements Callback, Runnable {

	private SurfaceHolder mHolder;
	private Canvas mCanvas;

	private Thread t;// 用于绘制的线程
	private boolean isRunning; // 线程的控制开关

	// 盘块的奖项
//	public static String[] mStrs = new String[] { "8聚币", "88聚币", "888聚币", "58聚币", "18聚币", "588聚币", "188聚币", "28聚币", "388聚币", "谢谢参与" };
	public static String[] mStrs = new String[] { "未中奖", "1等奖", "3等奖", "4等奖", "奖品项4", "奖品项5", "奖品项6", "奖品项7", "奖品项8", "奖品项9" };

	// 盘块的图片
	private int[] mImgs = new int[] { R.drawable.ysj_jubi, R.drawable.ysj_jubi, R.drawable.ysj_jubi, R.drawable.ysj_jubi,
			R.drawable.ysj_jubi, R.drawable.ysj_jubi , R.drawable.ysj_jubi, R.drawable.ysj_jubi, R.drawable.ysj_jubi, R.drawable.ysj_jubi};
    //R.drawable.danfan, R.drawable.ipad,
	// 与图片对应的Bitmap数组
	
	private Bitmap[] mImgsBitmap;

	// 设置背景
	private Bitmap mBgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg2);

	// 绘制盘块的画笔
	private Paint mArcPaint;

	// 绘制文本的画笔
	private Paint mTextPaint;

	// 盘块的颜色
	private int[] mColors = new int[] { 0xFFFFC300, 0xFFF17E01, 0xFFFFC300, 0xFFF17E01, 0xFFFFC300, 0xFFF17E01 , 0xFFFFC300, 0xFFF17E01, 0xFFFFC300, 0xFFF17E01};

	private int mItemCount = 10;

	// 整个盘块的范围
	private RectF mRange = new RectF();

	// 整个盘块的直径
	private int mDiameter;

	// 转盘的中心位置
	private int mCenter;

	// 这里我们的padding以paddingLeft为准
	private int mPadding;

	// 盘块滚动的速度
	private double speed = 0;

	// 起始角度
	private volatile float mStartAngle = 0; // volatile为了保证线程间变量的可见性

	// 判断是否点击了停止按钮
	private boolean isShouldEnd;
	public static boolean type;
	// 设置文本大小
	private float mTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15,
			getResources().getDisplayMetrics());

	public LuckyPan(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public LuckyPan(Context context, AttributeSet attrs) {
		super(context, attrs);

		mHolder = getHolder();
		mHolder.addCallback(this);

		// 可获得焦点
		setFocusable(true);
		setFocusableInTouchMode(true);
		// 设置长亮
		setKeepScreenOn(true);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int width = Math.min(getMeasuredWidth(), getMeasuredHeight());
		mPadding = getPaddingLeft();
		// 半径
		mDiameter = width - mPadding * 2;
		// 中心点
		mCenter = width / 2;

		setMeasuredDimension(width, width);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// 初始化绘制绘制盘块的画笔
		mArcPaint = new Paint();
		mArcPaint.setAntiAlias(true); // 设置抗锯齿
		mArcPaint.setDither(true); // 设置抖动
		//
		mTextPaint = new Paint();
		mTextPaint.setColor(0xffffffff);
		mTextPaint.setTextSize(mTextSize);

		//
		mRange = new RectF(mPadding, mPadding, mPadding + mDiameter, mPadding + mDiameter);

		// 初始化图片
		mImgsBitmap = new Bitmap[mItemCount];
		for (int i = 0; i < mItemCount; i++) {
			mImgsBitmap[i] = BitmapFactory.decodeResource(getResources(), mImgs[i]);
		}

		isRunning = true;
		t = new Thread(this);
		t.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		isRunning = false;

	}

	@Override
	public void run() {
		// 不断的进行绘制
		while (isRunning) {
			long start = System.currentTimeMillis();
			draw();
			long end = System.currentTimeMillis();
			if (end - start < 50) {
				try {
					Thread.sleep(50 - (end - start));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void draw() {
		try {
			mCanvas = mHolder.lockCanvas();
			if (mCanvas != null) {
				// 绘制背景
				drawBg();
				// 绘制盘块
				float tmpAngle = mStartAngle;
				float sweepAngle = 360 / mItemCount;
				for (int i = 0; i < mItemCount; i++) {
					mArcPaint.setColor(mColors[i]);
					// 绘制盘块
					mCanvas.drawArc(mRange, tmpAngle, sweepAngle, true, mArcPaint);
					// 绘制文本
					drawText(tmpAngle, sweepAngle, mStrs[i]);
					// 绘制Icon
					drawIcon(tmpAngle, mImgsBitmap[i]);
					tmpAngle += sweepAngle;
				}
				mStartAngle += speed;
				// 如果点击了停止按钮
				if (isShouldEnd) {
					speed -= 1;
				}
				if (speed <= 0) {
					speed = 0;
					isShouldEnd = false;
					type = true;
//					System.out.println("------------------完成----------------------");
					ZhuanYiZhuanActivity.handlerll.sendEmptyMessage(2);
				}
			}
		} catch (Exception e) {
		} finally {
			if (mCanvas != null) {
				mHolder.unlockCanvasAndPost(mCanvas);
			}
		}
	}

	/**
	 * 点击启动旋转
	 */
	public void luckyStart(int index) {
		// 计算每一项的角度
		float angle = 360 / mItemCount;
		// 计算每一项中奖范围 （当前index）

		float from = 270 - (index + 1) * angle;
		float end = from + angle;

		// 设置停下来的需要旋转的距离
		float targetFrom = 5 * 360 + from;
		float targetEnd = 5 * 360 + end;

		/**
		 * <pre>
		 * v1 -> 0
		 * 且每次-1
		 * 根据等差数列求和公式
		 * (v1 + 0)* (v1 +1) / 2 = targetFrom  （首项+末项）*项数 / 2
		 * 化简为：v1*v1 + v1 - 2targetFrom = 0
		 * 则   v1 = (-1 + Math.sqrt (1 + 8 * targetFrom) ) / 2
		 * </pre>
		 */
		float v1 = (float) ((-1 + Math.sqrt(1 + 8 * targetFrom)) / 2);
		
		float v2 = (float) ((-1 + Math.sqrt(1 + 8 * targetEnd)) / 2);
		
		speed = v1 + Math.random()*(v2-v1); 
		isShouldEnd = false;
	}

	public void luckyEnd() {
		mStartAngle = 0;
		isShouldEnd = true;
	}

	/**
	 * 转盘是否在旋转
	 * 
	 * @return
	 */
	public boolean isStart() {
		return speed != 0;
	}

	public boolean isShouldEnd() {
		return isShouldEnd;
	}

	/**
	 * 绘制Icon
	 * 
	 * @param tmpAngle
	 * @param bitmap
	 */
	private void drawIcon(float tmpAngle, Bitmap bitmap) {
		// 设置图片宽度为直径的1/8
		int imgWidth = mDiameter / 10;
		// 1°就是π除以180
		// 起始角度加上每个盘块一半的角度
		float angle = (float) ((tmpAngle + 360 / mItemCount / 2) * Math.PI / 180);

		int x = (int) (mCenter + mDiameter / 2 / 2 * Math.cos(angle));
		int y = (int) (mCenter + mDiameter / 2 / 2 * Math.sin(angle));

		// 确定图片的位置
		Rect rect = new Rect(x - imgWidth / 2, y - imgWidth / 2, x + imgWidth / 2, y + imgWidth / 2);
		mCanvas.drawBitmap(bitmap, null, rect, null);
	}

	/**
	 * 绘制每个盘块的文本
	 * 
	 * @param tmpAngle
	 * @param sweepAngle
	 * @param string
	 */
	private void drawText(float tmpAngle, float sweepAngle, String string) {
		Path path = new Path();
		path.addArc(mRange, tmpAngle, sweepAngle);
		// 水平偏移量让文字居中
		// 水平偏移量 = 弧长/2 - 文字长度 /2
		float textWidth = mTextPaint.measureText(string);
		int hOffset = (int) (mDiameter * Math.PI / mItemCount / 2 - textWidth / 2);
		int vOffset = mDiameter / 1 / 8;
		mCanvas.drawTextOnPath(string, path, hOffset, vOffset, mTextPaint);
	}

	// 绘制背景
	private void drawBg() {
		mCanvas.drawColor(0xFFFFFFFF);
		mCanvas.drawBitmap(mBgBitmap, 
				null, 
				new Rect(mPadding / 2,
				mPadding / 2, getMeasuredWidth() - mPadding / 2,
				getMeasuredWidth() - mPadding / 2), 
				null);
	}
}
