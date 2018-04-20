package com.gage.petfish_android.component.rxbus;

/**
 * ---日期----------维护人-----------
 * 2017/9/5       zuoyouming
 */
public class RxBusBaseMessage {
    private int code;
    private Object object;
    public RxBusBaseMessage(int code, Object object){
        this.code=code;
        this.object=object;
    }
    public RxBusBaseMessage(){}

    public int getCode() {
        return code;
    }

    public Object getObject() {
        return object;
    }
}
