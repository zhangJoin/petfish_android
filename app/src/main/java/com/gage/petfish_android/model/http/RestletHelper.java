package com.gage.petfish_android.model.http;

import com.gage.petfish_android.app.Contact;
import com.gage.petfish_android.model.http.api.HttpHelper;

import org.restlet.data.ChallengeScheme;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

/**
 * ---日期----------维护人-----------变更内容----------
 * 2017/6/19       zuoyouming         com.gage.applyschool.model.http
 */

public class RestletHelper implements HttpHelper {

    private Form paramMap;
    private ClientResource client;

    @Inject
    public RestletHelper() {
    }


    public RestletHelper contractUrl(String contact) {
        String url = Contact.HOST + contact;
        client = new ClientResource(url);
        client.setChallengeResponse(ChallengeScheme.HTTP_BASIC, "1", "123");
        return this;
    }

    /**
     * 设置查询参数
     *
     * @param key
     * @param value
     * @return
     */
    public RestletHelper setParams(String key, String value) {
        if (paramMap == null) {
            paramMap = new Form();
        }
        paramMap.add(key, value);
        return this;
    }


    /**
     * post请求
     *
     * @return
     */
    public Flowable<Object> post() {

        return Flowable.create(new FlowableOnSubscribe<Object>() {

            @Override
            public void subscribe(FlowableEmitter<Object> e) throws Exception {
                String result = "";
                try {
                    Representation representation = client.post(paramMap);
                    result = representation.getText();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                e.onNext(result);
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER);

    }
    /**
     * get请求
     *
     * @return
     */
    public Flowable get() {

        return Flowable.create(new FlowableOnSubscribe<Object>() {

            @Override
            public void subscribe(FlowableEmitter<Object> e) throws Exception {
                String result = "";
                Representation representation = client.get();
                try {
                    result = representation.getText();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                e.onNext(result);
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER);

    }
}
