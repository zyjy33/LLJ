package com.ctrip.openapi.java.utils;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

@SuppressLint({ "HandlerLeak", "UseValueOf" })
public abstract class MyCountdownTimer {

	private static final int MSG_RUN = 1;
	private long mCountdownInterval;// ��ʱ������Ժ����
	private long mTotalTime;// ��ʱʱ��
	private long mRemainTime;// ʣ��ʱ��

	public MyCountdownTimer(long millisInFuture, long countDownInterval) {
		mTotalTime = millisInFuture;
		mCountdownInterval = countDownInterval;
		mRemainTime = millisInFuture;
	}

	public abstract void onFinish();

	public abstract void onTick(long millisUntilFinished, int percent);

	// ȡ����ʱ
	public final void cancel() {
		mHandler.removeMessages(MSG_RUN);
	}

	// ���¿�ʼ��ʱ
	public final void resume() {
		mHandler.sendMessageAtFrontOfQueue(mHandler.obtainMessage(MSG_RUN));
	}

	// ��ͣ��ʱ
	public final void pause() {
		mHandler.removeMessages(MSG_RUN);
	}

	// ��ʼ��ʱ
	public synchronized final MyCountdownTimer start() {
		if (mRemainTime <= 0) {// ��ʱ�����󷵻�
			onFinish();
			return this;
		}
		// ���ü�ʱ���
		mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_RUN),
				mCountdownInterval);
		return this;
	}

	// ͨ��handler����android UI����ʾ��ʱʱ��
	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {

			synchronized (MyCountdownTimer.this) {
				if (msg.what == MSG_RUN) {
					mRemainTime = mRemainTime - mCountdownInterval;

					if (mRemainTime <= 0) {
						onFinish();
					} else if (mRemainTime < mCountdownInterval) {
						sendMessageDelayed(obtainMessage(MSG_RUN), mRemainTime);
					} else {

						onTick(mRemainTime,
								new Long(100 * (mTotalTime - mRemainTime) / mTotalTime).intValue());

						sendMessageDelayed(obtainMessage(MSG_RUN),
								mCountdownInterval);
					}
				}
			}
		}
	};

}
