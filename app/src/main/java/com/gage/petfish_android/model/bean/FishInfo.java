package com.gage.petfish_android.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * ---日期----------维护人-----------
 * 2017/9/13       zuoyouming
 */
public class FishInfo implements Serializable {
    public int totalCount;
    public List<FishBean> list;

    public static class FishBean implements Serializable{
        /**
         * seqId : 0c4f7aa8fe1f4b48ad45fe8bcff0fe1d
         * title : 如何养殖石美人
         * petfishSpecieId : 22fab307cd104de68a92aaea581188a0
         * moduleFlag : 02
         * showOrder : 00
         * insertUser : admin
         * insertDate : 1505725913000
         * updateUser : admin
         * updateDate : null
         * delFlag : false
         * updatedate : 2017-11-10 10:58:25.0
         */

        public String seqId;
        public String title;
        public String petfishSpecieId;
        public String moduleFlag;
        public String showOrder;
        public String content;
        public String textContent;
        public String updatedate;
        public String sarverFileName;
    }
}
