package com.gage.petfish_android.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhanglonglong on 2017/11/15.
 */

public class DisplayInfo implements Serializable{

    /**
     * totalCount : 29
     */

    public int totalCount;
    public List<DisplayBean> list;

    public static class DisplayBean implements Serializable{
        /**
         * sqlId : 07c994e0947d44439aeb0402c3b1c78d
         * name : 燕子美人
         * habit : 淡水鱼类，底层鱼类，pH6.0~8.0，硬度5~12德国度（GH），水温26~30℃
         * fishSpeciesClassificationId : 95b7692336244934a588c6fb6bf753de
         * floorPrice : 1
         * ceilingPrice : 30
         * showOrder : 01
         * insertUser : admin
         * insertDate : 1505719251000
         * updateUser : admin
         * updateDate : 1510279234000
         * delFlag : false
         * relateId : null
         * moduleFlag : 06
         * fileContent : null
         * sarverFileName : /2017/11/10/b013b4e0dd724563827aac40298d6559.jpeg
         */

        public String sqlId;
        public String name;
        public String habit;
        public String method;
        public String fishSpeciesClassificationId;
        public Double floorPrice;
        public Double ceilingPrice;
        public String showOrder;
        public String introduction;
        public String sarverFileName;
    }
}
