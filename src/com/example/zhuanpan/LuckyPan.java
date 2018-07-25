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

	private Thread t;// ���ڻ��Ƶ��߳�
	private boolean isRunning; // �̵߳Ŀ��ƿ���

	// �̿�Ľ���
//	public static String[] mStrs = new String[] { "8�۱�", "88�۱�", "888�۱�", "58�۱�", "18�۱�", "588�۱�", "188�۱�", "28�۱�", "388�۱�", "лл����" };
	public static String[] mStrs = new String[] { "δ�н�", "1�Ƚ�", "3�Ƚ�", "4�Ƚ�", "��Ʒ��4", "��Ʒ��5", "��Ʒ��6", "��Ʒ��7", "��Ʒ��8", "��Ʒ��9" };

	// �̿��ͼƬ
	private int[] mImgs = new int[] { R.drawable.ysj_jubi, R.drawable.ysj_jubi, R.drawable.ysj_jubi, R.drawable.ysj_jubi,
			R.drawable.ysj_jubi, R.drawable.ysj_jubi , R.drawable.ysj_jubi, R.drawable.ysj_jubi, R.drawable.ysj_jubi, R.drawable.ysj_jubi};
    //R.drawable.danfan, R.drawable.ipad,
	// ��ͼƬ��Ӧ��Bitmap����
	
	private Bitmap[] mImgsBitmap;

	// ���ñ���
	private Bitmap mBgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg2);

	// �����̿�Ļ���
	private Paint mArcPaint;

	// �����ı��Ļ���
	private Paint mTextPaint;

	// �̿����ɫ
	private int[] mColors = new int[] { 0xFFFFC300, 0xFFF17E01, 0xFFFFC300, 0xFFF17E01, 0xFFFFC300, 0xFFF17E01 , 0xFFFFC300, 0xFFF17E01, 0xFFFFC300, 0xFFF17E01};

	private int mItemCount = 10;

	// �����̿�ķ�Χ
	private RectF mRange = new RectF();

	// �����̿��ֱ��
	private int mDiameter;

	// ת�̵�����λ��
	private int mCenter;

	// �������ǵ�padding��paddingLeftΪ׼
	private int mPadding;

	// �̿�������ٶ�
	private double speed = 0;

	// ��ʼ�Ƕ�
	private volatile float mStartAngle = 0; // volatileΪ�˱�֤�̼߳�����Ŀɼ���

	// �ж��Ƿ�����ֹͣ��ť
	private boolean isShouldEnd;
	public static boolean type;
	// �����ı���С
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

		// �ɻ�ý���
		setFocusable(true);
		setFocusableInTouchMode(true);
		// ���ó���
		setKeepScreenOn(true);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int width = Math.min(getMeasuredWidth(), getMeasuredHeight());
		mPadding = getPaddingLeft();
		// �뾶
		mDiameter = width - mPadding * 2;
		// ���ĵ�
		mCenter = width / 2;

		setMeasuredDimension(width, width);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// ��ʼ�����ƻ����̿�Ļ���
		mArcPaint = new Paint();
		mArcPaint.setAntiAlias(true); // ���ÿ����
		mArcPaint.setDither(true); // ���ö���
		//
		mTextPaint = new Paint();
		mTextPaint.setColor(0xffffffff);
		mTextPaint.setTextSize(mTextSize);

		//
		mRange = new RectF(mPadding, mPadding, mPadding + mDiameter, mPadding + mDiameter);

		// ��ʼ��ͼƬ
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
		// ���ϵĽ��л���
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
				// ���Ʊ���
				drawBg();
				// �����̿�
				float tmpAngle = mStartAngle;
				float sweepAngle = 360 / mItemCount;
				for (int i = 0; i < mItemCount; i++) {
					mArcPaint.setColor(mColors[i]);
					// �����̿�
					mCanvas.drawArc(mRange, tmpAngle, sweepAngle, true, mArcPaint);
					// �����ı�
					drawText(tmpAngle, sweepAngle, mStrs[i]);
					// ����Icon
					drawIcon(tmpAngle, mImgsBitmap[i]);
					tmpAngle += sweepAngle;
				}
				mStartAngle += speed;
				// ��������ֹͣ��ť
				if (isShouldEnd) {
					speed -= 1;
				}
				if (speed <= 0) {
					speed = 0;
					isShouldEnd = false;
					type = true;
//					System.out.println("------------------���----------------------");
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
	 * ���������ת
	 */
	public void luckyStart(int index) {
		// ����ÿһ��ĽǶ�
		float angle = 360 / mItemCount;
		// ����ÿһ���н���Χ ����ǰindex��

		float from = 270 - (index + 1) * angle;
		float end = from + angle;

		// ����ͣ��������Ҫ��ת�ľ���
		float targetFrom = 5 * 360 + from;
		float targetEnd = 5 * 360 + end;

		/**
		 * <pre>
		 * v1 -> 0
		 * ��ÿ��-1
		 * ���ݵȲ�������͹�ʽ
		 * (v1 + 0)* (v1 +1) / 2 = targetFrom  ������+ĩ�*���� / 2
		 * ����Ϊ��v1*v1 + v1 - 2targetFrom = 0
		 * ��   v1 = (-1 + Math.sqrt (1 + 8 * targetFrom) ) / 2
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
	 * ת���Ƿ�����ת
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
	 * ����Icon
	 * 
	 * @param tmpAngle
	 * @param bitmap
	 */
	private void drawIcon(float tmpAngle, Bitmap bitmap) {
		// ����ͼƬ���Ϊֱ����1/8
		int imgWidth = mDiameter / 10;
		// 1����Ǧг���180
		// ��ʼ�Ƕȼ���ÿ���̿�һ��ĽǶ�
		float angle = (float) ((tmpAngle + 360 / mItemCount / 2) * Math.PI / 180);

		int x = (int) (mCenter + mDiameter / 2 / 2 * Math.cos(angle));
		int y = (int) (mCenter + mDiameter / 2 / 2 * Math.sin(angle));

		// ȷ��ͼƬ��λ��
		Rect rect = new Rect(x - imgWidth / 2, y - imgWidth / 2, x + imgWidth / 2, y + imgWidth / 2);
		mCanvas.drawBitmap(bitmap, null, rect, null);
	}

	/**
	 * ����ÿ���̿���ı�
	 * 
	 * @param tmpAngle
	 * @param sweepAngle
	 * @param string
	 */
	private void drawText(float tmpAngle, float sweepAngle, String string) {
		Path path = new Path();
		path.addArc(mRange, tmpAngle, sweepAngle);
		// ˮƽƫ���������־���
		// ˮƽƫ���� = ����/2 - ���ֳ��� /2
		float textWidth = mTextPaint.measureText(string);
		int hOffset = (int) (mDiameter * Math.PI / mItemCount / 2 - textWidth / 2);
		int vOffset = mDiameter / 1 / 8;
		mCanvas.drawTextOnPath(string, path, hOffset, vOffset, mTextPaint);
	}

	// ���Ʊ���
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
