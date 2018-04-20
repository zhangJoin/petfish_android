package com.gage.petfish_android.component;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.gage.petfish_android.R;
import com.gage.petfish_android.util.ToastUtil;

import java.io.File;

public class UpdateService extends Service {
    private BroadcastReceiver receiver;
    private String versionUrl;
    public Context mContext;
    private ProgressDialog progressDialog;
    private DownloadManager dm;
    private long downloadId;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        versionUrl = intent.getStringExtra("versionUrl");

        /**
         * 下载完成的广播接收者
         */
        if (receiver == null) {
            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(downloadId);
                    Cursor cursor = dm.query(query);
                    if (cursor != null && cursor.moveToFirst()) {
                        int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                        int status = cursor.getInt(columnIndex);
                        switch (status) {
                            case DownloadManager.STATUS_FAILED:
                                ToastUtil.shortShow("STATUS_FAILED");
                                break;
                            case DownloadManager.STATUS_PAUSED:
                                ToastUtil.shortShow("STATUS_PAUSED");
                                break;
                            case DownloadManager.STATUS_PENDING:
                                ToastUtil.shortShow("STATUS_PENDING");
                                break;
                            case DownloadManager.STATUS_SUCCESSFUL:
                                ToastUtil.shortShow("STATUS_SUCCESSFUL");
                                installApk();

                                break;

                            default:
                                break;
                        }
                    }

                    dismissDialog();

                    unregisterReceiver(receiver); //取消注册
                    receiver = null;

                    stopSelf();  //service停止
                }
            };
            registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
            startDownload();
        }

        return Service.START_STICKY;
    }

    /**
     * 安装apk
     */
    private void installApk() {
        Intent intent;//安装apk
        intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/download/applyschool.apk")),
                "application/vnd.android.package-archive");
        startActivity(intent);
    }

    /**
     * 安装包下载
     */
    private void startDownload() {

        popProgressDialog();

        dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(versionUrl));

        request.setTitle("智慧掌上校园");
        request.setDescription("新版本下载中");
        request.setMimeType("application/vnd.android.package-archive");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "applyschool.apk");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        downloadId = dm.enqueue(request); //执行下载,返回唯一id

    }

    /**
     * 弹出进度对话框
     */
    private void popProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("提示");
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setMessage("新版本下载中...");

        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.setIndeterminate(true);
        progressDialog.setButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dm.remove(downloadId);
                dismissDialog();
            }
        });
        progressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);//系统级对话框
        progressDialog.show();
    }

    /**
     * 隐藏进度对话框
     */
    private void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

}
