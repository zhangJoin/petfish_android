package com.gage.petfish_android.model.http.other;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.gage.petfish_android.app.Contact;
import com.gage.petfish_android.component.UpdateService;
import com.gage.petfish_android.model.bean.VersionBean4Local;
import com.gage.petfish_android.model.bean.VersionBean4Server;
import com.gage.petfish_android.util.SPUtil;
import com.gage.petfish_android.util.SystemUtil;

import org.restlet.data.ChallengeScheme;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * ---日期----------维护人-----------变更内容----------
 * 2017/6/20       zuoyouming         com.gage.applyschool.model.http.other
 */

public class VersionManager {

    private Subscription subscribe;
    private Context mContext;
    private VersionBean4Server versionBean4Server;

    public VersionManager(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 检测软件更新
     */
    public void checkVersion() {

        //版本检测
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {

                //获取服务器上应用版本
                String url = Contact.HOST + Contact.SERVICECODE;
                ClientResource client = new ClientResource(url);
                client.setChallengeResponse(ChallengeScheme.HTTP_BASIC, "1", "123");

                String result = "";
                Representation representation = client.get();
                try {
                    result = representation.getText();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                subscriber.onNext(result);
                subscriber.onCompleted();
            }
        });


//        subscribe = observable.compose(RxUtil.<String>rxSchedulerHelper()).subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//                versionBean4Server = GsonUtils.json2Bean(s, VersionBean4Server.class);
//
//                compareVersion();
//            }
//        }, new Action1<Throwable>() {
//            @Override
//            public void call(Throwable throwable) {
//
//            }
//        });
    }

    /**
     * 比对版本
     */
    private void compareVersion() {
        if (versionBean4Server == null)
            return;
        String versionCode = versionBean4Server.version;
        String versionName = versionBean4Server.version;

        SPUtil.putString("versionCode4Server", versionCode);
        SPUtil.putString("versionName4Server", versionName);


        if (versionCode == null)
            return;

        int serverVersion = Integer.parseInt(versionCode); //服务器版本
        VersionBean4Local versionBean4Local = SystemUtil.getVersionInfo();// 获取当前软件版本
        int loaclVersion = versionBean4Local.versionCode;
        // 版本判断
        if (serverVersion > loaclVersion) {
            //弹出更新提醒
            showUpdateDialog();
            unSubscribe();
        }
    }

    /**
     * 弹出更新提示对话框
     */
    private void showUpdateDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("检测到新版本!");
        builder.setMessage("检测到新版本，立即更新吗");
        builder.setNegativeButton("稍后更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SPUtil.putBoolean("isNeedCheckVersion", false);
            }
        });
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startDownloadService();
                SPUtil.putBoolean("isNeedCheckVersion", true);
            }
        });
        builder.show();
    }

    /**
     * 开始下载apk
     */
    private void startDownloadService() {
        Intent intent = new Intent(mContext, UpdateService.class);
        intent.putExtra("versionUrl", versionBean4Server.url);
        mContext.startService(intent);
    }

    /**
     * 取消订阅,防止Rxjava内存泄漏
     */
    protected void unSubscribe() {
        if (subscribe != null) {
            subscribe.unsubscribe();
        }
    }
}
