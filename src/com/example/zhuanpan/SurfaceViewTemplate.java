package com.example.zhuanpan;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class SurfaceViewTemplate extends SurfaceView implements Callback, Runnable {

	private SurfaceHolder mHolder;
	private Canvas mCanvas;

	private Thread t;// ���ڻ��Ƶ��߳�
	private boolean isRunning; // �̵߳Ŀ��ƿ���

	public SurfaceViewTemplate(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public SurfaceViewTemplate(Context context, AttributeSet attrs) {
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
	public void surfaceCreated(SurfaceHolder holder) {
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
		try {
			while (isRunning) {
				mCanvas = mHolder.lockCanvas();
				if (mCanvas != null) {
					// draw sth
				}
			}
		} catch (Exception e) {
		} finally {
			if (mCanvas != null) {
				mHolder.unlockCanvasAndPost(mCanvas);
			}
		}
	}

}
