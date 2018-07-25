package com.example.downloadandnotificationbar;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import com.lelinju.www.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.widget.RemoteViews;

public class UpdateApkThread extends Thread {
    private String downloadUrl;
    private File saveFile;
    private String fileName;
    private Context context;
    private NotificationManager notificationManager;// ״̬��֪ͨ������
    private Notification notification;// ״̬��֪ͨ
    private RemoteViews notificationViews;// ״̬��֪ͨ��ʾ��view
    private Timer timer;// ��ʱ�������ڸ������ؽ���
    private TimerTask task;// ��ʱ��ִ�е�����

    private final int notificationID = 1;// ֪ͨ��id
    private final int updateProgress = 1;// ����״̬�������ؽ���
    private final int downloadSuccess = 2;// ���سɹ�
    private final int downloadError = 3;// ����ʧ��
    private DownLoadUtil downLoadUtil;

    public UpdateApkThread(String downloadUrl, String fileLocation, String fileName, Context context) {
        this.downloadUrl = downloadUrl;
        this.saveFile = new File(fileLocation);
        this.context = context;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        super.run();
        try {
            initNofication();
            handlerTask();
            downLoadUtil = new DownLoadUtil();
            int downSize = downLoadUtil.downloadUpdateFile(downloadUrl, saveFile, fileName, callback);
            if (downSize == downLoadUtil.getRealSize() && downSize != 0) {
                handler.sendEmptyMessage(downloadSuccess);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @author wangqian@iliveasia.com
     * @Description: ��ʼ��֪ͨ��
     */
    private void initNofication() {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notification = new Notification();
        notification.icon = R.drawable.llj_app;// ����֪ͨ��Ϣ��ͼ��
        notification.tickerText = "��������...";// ����֪ͨ��Ϣ�ı���
        notificationViews = new RemoteViews(context.getPackageName(), R.layout.down_notification);
        notificationViews.setImageViewResource(R.id.download_icon, R.drawable.llj_app);
    }

    /**
     * 
     * @author wangqian@iliveasia.com
     * @Description: ��ʱ֪ͨhandlerȥ��ʾ���ذٷֱ�
     */
    private void handlerTask() {
        timer = new Timer();
        task = new TimerTask() {

            @Override
            public void run() {
                handler.sendEmptyMessage(updateProgress);
            }
        };
        timer.schedule(task, 500, 500);
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == updateProgress) {// �������ؽ���
                int fileSize = downLoadUtil.getRealSize();
                int totalReadSize = downLoadUtil.getTotalSize();
                if (totalReadSize > 0) {
                    float size = (float) totalReadSize * 100 / (float) fileSize;
                    DecimalFormat format = new DecimalFormat("0.00");
                    String progress = format.format(size);
                    notificationViews.setTextViewText(R.id.progressTv, "������" + progress + "%");
                    notificationViews.setProgressBar(R.id.progressBar, 100, (int) size, false);
                    notification.contentView = notificationViews;
                    notificationManager.notify(notificationID, notification);
                }
            } else if (msg.what == downloadSuccess) {// �������
                notificationViews.setTextViewText(R.id.progressTv, "�������");
                notificationViews.setProgressBar(R.id.progressBar, 100, 100, false);
                notification.contentView = notificationViews;
                notification.tickerText = "�������";
                notificationManager.notify(notificationID, notification);
                if (timer != null && task != null) {
                    timer.cancel();
                    task.cancel();
                    timer = null;
                    task = null;
                }
                // ��װapk
                Uri uri = Uri.fromFile(new File(saveFile + "/LeLinJu.apk"));
                Intent installIntent = new Intent(Intent.ACTION_VIEW);
                installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
                // PendingIntent ֪ͨ����ת
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, installIntent, 0);
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                notification.contentIntent = pendingIntent;
                notification.contentView.setTextViewText(R.id.progressTv, "������ɣ������װ");
                notificationManager.notify(notificationID, notification);

            } else if (msg.what == downloadError) {// ����ʧ��
                if (timer != null && task != null) {
                    timer.cancel();
                    task.cancel();
                    timer = null;
                    task = null;
                }
                notificationManager.cancel(notificationID);

            }
        }

    };
    /**
     * ���ػص�
     */
    DownloadFileCallback callback = new DownloadFileCallback() {

        @Override
        public void downloadError(String msg) {
            handler.sendEmptyMessage(downloadError);
        }
    };

}
