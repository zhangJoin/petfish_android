package com.gage.petfish_android.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhanglonglong on 2017/11/22.
 */

public class QualityInfo implements Serializable{

    public List<NameListBean> NameList;
    public List<TypeListBean> TypeList;

    public static class NameListBean implements  Serializable{
        /**
         * seqId : 07c994e0947d44439aeb0402c3b1c78d
         * name : 燕子美人
         */

        public String seqId;
        public String name;
    }

    public static class TypeListBean implements Serializable{
        /**
         * seqId : 025a38e2c3294350a3b5c32b31dda406
         * name : 鲤鱼
         */

        public String seqId;
        public String name;
    }
}
