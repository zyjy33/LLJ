package com.lelinju.www;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.TextView;
/**
 * ���ڹιο��Ļ�������
 * @author cloor
 *
 */
@SuppressLint("HandlerLeak")
public class GuaGuaUtil extends TextView {
	private int W;
	private int H;
	private static final int MV = 1;
	private static final int SW = 50;
	private static final int MC = 0xFFD6D6D6;
	private int mWidth;
	private int mHeight;
	private int mMaskColor;
	private int mStrokeWidth;
	private float mX;
	private float mY;
	private boolean mRun;
	private boolean caculate;
	private Path mPath;
	private Paint mPaint;
	private Paint mBitmapPaint;
	private Canvas mCanvas;
	private Bitmap mBitmap;
	private int[] mPixels;
	private Thread mThread;
	private onWipeListener mWipeListener;
	private Handler parentHandler;
	public void clear(){
		if(mBitmap!=null){
			mBitmap.recycle();
			mBitmap=null;
		}
	}
	public GuaGuaUtil(Context context, int W, int H,Handler parentHandler) {
		super(context);
		this.W = W;
		this.H = H;
		this.parentHandler = parentHandler;
		init(context);
	}
	public GuaGuaUtil(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	private void init(Context context) {
		mMaskColor = MC;
		mStrokeWidth = SW;
		mPath = new Path();
		mBitmapPaint = new Paint();
		mPaint = new Paint();
		mPaint.setAntiAlias(true);// �����
		mPaint.setDither(true);// ��ɫ
		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND); // ǰԲ��
		mPaint.setStrokeCap(Paint.Cap.ROUND); // ��Բ��
		mPaint.setStrokeWidth(mStrokeWidth); // �ʿ�
		mBitmap = Bitmap.createBitmap(W, H, Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);
		mCanvas.drawColor(mMaskColor);
		mRun = true;
		mThread = new Thread(mRunnable);
		mThread.start();
		setGravity(Gravity.CENTER);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mCanvas.drawPath(mPath, mPaint);
		canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int w = MeasureSpec.getSize(widthMeasureSpec);
		int h = MeasureSpec.getSize(heightMeasureSpec);
		if (w > 0 && h > 0) {
			mWidth = W;
			mHeight = H;
		}
	}
	public void reset() {
		mPath.reset();
		mCanvas.drawPaint(mPaint);
		mCanvas.drawColor(mMaskColor);
		invalidate();
	}

	public void setOnWipeListener(onWipeListener listerer) {
		this.mWipeListener = listerer;
	}

	public void setStrokeWidth(int width) {
		this.mStrokeWidth = width;
		mPaint.setStrokeWidth(width);
	}

	public void setMaskColor(int color) {
		this.mMaskColor = color;
		reset();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean invalidate = false;
		boolean consume = false;
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			consume = true;
			touchDown(event);
			break;
		case MotionEvent.ACTION_MOVE:
			consume = true;
			invalidate = touchMove(event);
			break;
		case MotionEvent.ACTION_UP:
			consume = true;
			touchUp(event);
			break;
		}
		if (invalidate) {
			invalidate();
		}
		if (consume) {
			return true;
		}
		return super.onTouchEvent(event);
	}

	// ��ָ������Ļʱ����
	private void touchDown(MotionEvent event) {
		caculate = false;
		// ���û���·�ߣ�������֮ǰ���ƵĹ켣
		mPath.reset();
		float x = event.getX();
		float y = event.getY();
		mX = x;
		mY = y;
		// mPath���ƵĻ������
		mPath.moveTo(x, y);
	}
	// ��ָ����Ļ�ϻ���ʱ����
	private boolean touchMove(MotionEvent event) {
		caculate = false;
		final float x = event.getX();
		final float y = event.getY();
		final float previousX = mX;
		final float previousY = mY;
		// ���ñ��������ߵĲ�����Ϊ�����յ��һ��
		float cX = (x + previousX) / 2;
		float cY = (y + previousY) / 2;
		final float dx = Math.abs(x - previousX);
		final float dy = Math.abs(y - previousY);
		boolean move = false;
		if (dx >= MV || dy >= MV) {
			// ���α�������ʵ��ƽ�����ߣ�cX, cYΪ������ x,yΪ�յ�
			mPath.quadTo(cX, cY, x, y);
			// �ڶ���ִ��ʱ����һ�ν������õ�����ֵ����Ϊ�ڶ��ε��õĳ�ʼ����ֵ
			mX = x;
			mY = y;
			move = true;
		}
		return move;
	}

	private void touchUp(MotionEvent event) {
		caculate = true;
		mRun = true;
	}
	private Runnable mRunnable = new Runnable() {
		@Override
		public void run() {
			while (mRun) {
				SystemClock.sleep(100);
				// �յ��������������ʼ����
				if (caculate) {
					caculate = false;
					int w = mWidth;
					int h = mHeight;
					float wipeArea = 0;
					float totalArea = w * h;
					// �����ʱ100��������
					Bitmap bitmap = mBitmap;
					if (mPixels == null) {
						mPixels = new int[w * h];
					}
					bitmap.getPixels(mPixels, 0, w, 0, 0, w, h);
					for (int i = 0; i < w; i++) {
						for (int j = 0; j < h; j++) {
							int index = i + j * w;
							if (mPixels[index] == 0) {
									wipeArea++;
							}
						}
					}
					if (wipeArea > 0 && totalArea > 0) {
						int percent = (int) (wipeArea * 100 / totalArea);
						if(percent>=5){
							//mHandler��˳��
							Message msg = mHandler.obtainMessage();
							msg.what = 0;
							parentHandler.sendEmptyMessage(-1);
							mHandler.sendMessage(msg);
						}else {
							Message msg = mHandler.obtainMessage();
							msg.what = 1;
							msg.arg1 = percent;
							mHandler.sendMessage(msg);
						}
					}
				}
			}
		}
	};

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				mRun = false;
				GuaGuaUtil.this.setEnabled(false);
				mCanvas.drawColor(Color.TRANSPARENT,PorterDuff.Mode.CLEAR);
				invalidate();
				if (mWipeListener != null) {
					//-1��������˳齱����
						mWipeListener.onWipe(-1);
				}
				break;
			case 1:
				if (mWipeListener != null) {
						int percent = msg.arg1;
						mWipeListener.onWipe(percent);
				}
				break;
			default:
				break;
			}
		};
	};

	public interface onWipeListener {
		
		public void onWipe(int percent);
	
	}
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		mRun = false;
	}
}
