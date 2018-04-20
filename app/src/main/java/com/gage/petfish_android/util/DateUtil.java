package com.gage.petfish_android.util;

import java.util.List;

/**
 * Created by zhanglonglong on 2017/11/21.
 */

public class DateUtil {
    public static String splitString(String... characters) {
        StringBuffer oriString = new StringBuffer();
        for (int i = 0; i < characters.length; i++) {
            if(i==0){
                oriString.append(characters[0].toString());
            }else{
                oriString.append(",").append(characters[i].toString());
            }
        }
        return oriString.toString();
    }


    public static String splitString(List<String> list) {
        StringBuffer oriString = new StringBuffer();

        for (int i = 0; i < list.size(); i++) {
            if(i==0){
                oriString = oriString.append(list.get(0).toString());
            }else{
                oriString = oriString.append(",").append(list.get(i).toString());
            }
        }
        return oriString.toString();
    }

    public static String appendString(String... characters) {
        StringBuffer oriString = new StringBuffer();
        for (int i = 0; i < characters.length; i++) {
            if(i==0){
                oriString.append(characters[0].toString());
            }else{
                oriString.append("  ,  ").append(characters[i].toString());
            }
        }
        return oriString.toString();
    }
}
