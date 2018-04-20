package com.gage.petfish_android.model.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/13.
 */

public class ContactUsBean implements Serializable{
    public String name;
    public String tel;
    public String email;
    public String address;
    public String enterpriseIntroduction;
    public String imageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEnterpriseIntroduction() {
        return enterpriseIntroduction;
    }

    public void setEnterpriseIntroduction(String enterpriseIntroduction) {
        this.enterpriseIntroduction = enterpriseIntroduction;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
