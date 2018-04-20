package com.gage.petfish_android.model.http.other;


import android.app.AlertDialog;
import android.content.Context;

import com.gage.petfish_android.app.Contact;

import rx.Subscription;

/**
 * ---日期----------维护人-----------变更内容----------
 * 2017/6/21       zuoyouming         com.gage.applyschool.model.http.other
 */

public class AccountManager {

    private static AlertDialog.Builder builder;
    public static String url = Contact.HOST + Contact.REPEATLOGIN;
    private static Subscription subscribe;

    public static void checkAccountState(final Context mContext) {
        //                    if ("1".equals(userPhoneExistFlag)) {
//        subscribe = Observable.create(new Observable.OnSubscribe<String>() {
//
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                String teacherNumber = SPUtil.getString("teacherNumber", "000");
//                String deviceId = SystemUtil.getDeviceId();
//
//                if (!TextUtils.isEmpty(teacherNumber)) {
//                    Form paramMap = new Form();
//                    paramMap.add("teacherNumber", teacherNumber);
//                    paramMap.add("phoneId", deviceId);
//                    ClientResource client = new ClientResource(url);
//                    client.setChallengeResponse(ChallengeScheme.HTTP_BASIC, "1", "123");
//                    try {
//                        Representation representation = client.post(paramMap);
//                        String result = representation.getText();
//
//                        subscriber.onNext(result);
//                        subscriber.onCompleted();
//
//                    } catch (Exception e) {
//                        subscriber.onNext(e.getMessage());
//                        subscriber.onCompleted();
//
//                    }
//                }
//            }
//        })
//                .compose(RxUtil.<String>rxSchedulerHelper()).subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//                System.out.println(s);
//                try {
//                    JSONObject object = new JSONObject(s);
//                    String userPhoneExistFlag = object.getString("UserPhoneExistFlag");
////                    if ("1".equals(userPhoneExistFlag)) {
//                    if (true) {
//                        popReloginDialog(mContext);
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        })
//        ;

    }

    /**
     * 弹出重新登录对话框
     *
     * @param mContext
     */
    private static void popReloginDialog(final Context mContext) {

        builder = new AlertDialog.Builder(mContext);
        builder.setTitle("提示");
        builder.setMessage("您的账号在其他设备上登录，如非本人操作，请重新登录并及时修改密码");

        builder.setCancelable(false);

//        builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                mContext.startActivity(new Intent(mContext, LoginActivity.class));
//            }
//        });
//        builder.setNegativeButton("重新登录", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent(mContext, LoginActivity.class);
//                intent.putExtra("isAutologin", true);
//                mContext.startActivity(intent);
//            }
//        });

        builder.show();
        subscribe.unsubscribe();//解除订阅
    }
}
