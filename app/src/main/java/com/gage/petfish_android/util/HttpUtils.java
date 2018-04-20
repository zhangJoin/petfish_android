package com.gage.petfish_android.util;

import com.gage.petfish_android.app.Contact;

import org.restlet.data.ChallengeScheme;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

import java.io.IOException;

/**
 * Created by zhanglonglong on 2017/11/10.
 */

public class HttpUtils {

    public static String post(String contact, Form mForm) {
        String url = Contact.HOST + contact;
        ClientResource client = new ClientResource(url);
        client.setChallengeResponse(ChallengeScheme.HTTP_BASIC, "1", "123");
        try {
            Representation representation = client.post(mForm);
            return representation.getText();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public static String get(String contact) {
        String url = Contact.HOST + contact;
        ClientResource client = new ClientResource(url);
        client.setChallengeResponse(ChallengeScheme.HTTP_BASIC, "1", "123");
        try {
            Representation representation = client.get();
            return  representation.getText();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
