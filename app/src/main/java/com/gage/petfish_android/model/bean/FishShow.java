package com.gage.petfish_android.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * ---日期----------维护人-----------
 * 2017/9/13       zuoyouming
 */

public class FishShow implements Serializable{
    private String seqId;//Id
    private String imageUrl;//首页图片url
    private String name;//标题
    private String Content;//生活习性
    private String date;//日期
    private String address;//地址
    private String phone;//电话
    private String floorPrice;//最低价格
    private String ceilingPrice;//最高价格
    private String introduction;//形态特征
    private String method;//养殖方法
    private String pName;//品种分类
    private List<Object> listTP;//展示详情页图片url
    private List<ProdunctInfo> fishList;//展示详情页图片url

    public String getContactPeople() {
        return contactPeople;
    }

    public void setContactPeople(String contactPeople) {
        this.contactPeople = contactPeople;
    }

    private String contactPeople;

    public List<Object> getListTP() {
        return listTP;
    }

    public void setListTP(List<Object> listTP) {
        this.listTP = listTP;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }
    public String getCeilingPrice() {
        return ceilingPrice;
    }

    public void setCeilingPrice(String ceilingPrice) {
        this.ceilingPrice = ceilingPrice;
    }

    public String getFloorPrice() {
        return floorPrice;
    }

    public void setFloorPrice(String floorPrice) {
        this.floorPrice = floorPrice;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<ProdunctInfo> getFishList() {
        return fishList;
    }

    public void setFishList(List<ProdunctInfo> fishList) {
        this.fishList = fishList;
    }
}
