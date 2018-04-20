package com.gage.petfish_android.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhanglonglong on 2017/11/17.
 */

public class VarieInfo implements Serializable{

    public List<MaterialListBean> MaterialList;
    public List<TypeListBean> TypeList;

    public static class MaterialListBean implements Serializable{
        /**
         * seqId : 040ae3f9b3864c9784846203ab59ef75
         * name : 不锈钢
         */

        public String seqId;
        public String name;
    }

    public static class TypeListBean implements  Serializable{
        /**
         * seqId : 08cdec134a0d45efa06aea3882fbb26c
         * name : 食斗
         */

        public String seqId;
        public String name;
    }
}
