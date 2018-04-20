package com.gage.petfish_android.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhanglonglong on 2017/11/24.
 */

public class OtherDetailInfo implements Serializable {

    private String sqlId;
    private String name;
    private double price;
    private Object delFlag;
    private String introduction;
    private String sarverFileName;
    private String cName;
    private String fName;
    private List<Object>list;

    public String getSqlId() {
        return sqlId;
    }

    public void setSqlId(String sqlId) {
        this.sqlId = sqlId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Object getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Object delFlag) {
        this.delFlag = delFlag;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getSarverFileName() {
        return sarverFileName;
    }

    public void setSarverFileName(String sarverFileName) {
        this.sarverFileName = sarverFileName;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }
}
