package com.ctrip.openapi.java.utils;

import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import android.graphics.Bitmap;

public final class EncodingHandler {

	private static final int BLACK = 0xff000000;

	public static Bitmap createQRCode(String str, int widthHeight)
			throws WriterException {

		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
		// ���ö�ά������ʽ
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

		BitMatrix marBitMatrix = new MultiFormatWriter().encode(str,
				BarcodeFormat.QR_CODE, widthHeight, widthHeight);
		int _width = marBitMatrix.getWidth();
		int _height = marBitMatrix.getHeight();
		// ���ݿ�Ⱥ͸߶ȼ������ش�С��
		int[] pixels = new int[_height * _width];

		// ���
		for (int y = 0; y < _height; y++) {
			for (int x = 0; x < _width; x++) {
				if (marBitMatrix.get(x, y)) {
					pixels[y * _width + x] = BLACK;
				}
			}
		}
		
		// ����λͼ
		Bitmap mBitmap = Bitmap.createBitmap(_width, _height,Bitmap.Config.ARGB_8888);
		mBitmap.setPixels(pixels, 0, _width, 0, 0, _width, _height);

		return mBitmap;
	}

}
