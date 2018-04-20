package com.gage.petfish_android.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/16.
 */

public class OtherInfo implements Serializable{

    /**
     * totalCount : 16
     * list : [{"sqlId":null,"name":"玻璃板鱼缸","status":null,"productMaterialId":null,"price":13,"productTypeId":null,"enterpriseInfoId":null,"insertUser":null,"insertDate":null,"updateUser":null,"updateDate":null,"delFlag":null,"introduction":null,"relateId":null,"sarverFileName":"/2017/11/10/32b0ab787c60416a8454899dfed8150b.jpg","cName":"亚克力玻璃"},{"sqlId":null,"name":"亿彩七彩神仙鱼饲料","status":null,"productMaterialId":null,"price":39.9,"productTypeId":null,"enterpriseInfoId":null,"insertUser":null,"insertDate":null,"updateUser":null,"updateDate":null,"delFlag":null,"introduction":null,"relateId":null,"sarverFileName":null,"cName":"饲料"},{"sqlId":null,"name":"鱼缸加热棒自动恒温防爆超短温控迷你加温器龟缸水族箱养鱼加温棒","status":null,"productMaterialId":null,"price":99.9,"productTypeId":null,"enterpriseInfoId":null,"insertUser":null,"insertDate":null,"updateUser":null,"updateDate":null,"delFlag":null,"introduction":null,"relateId":null,"sarverFileName":"/2017/11/10/2589bbea714c4ff3a0db224bbbcabb57.jpg","cName":"碳素结构钢"},{"sqlId":null,"name":"不绣钢抄网","status":null,"productMaterialId":null,"price":88,"productTypeId":null,"enterpriseInfoId":null,"insertUser":null,"insertDate":null,"updateUser":null,"updateDate":null,"delFlag":null,"introduction":null,"relateId":null,"sarverFileName":"/2017/11/10/bca03d2163e840cb8cbf910e7749342d.jpg","cName":"不锈钢"},{"sqlId":null,"name":"鱼食","status":null,"productMaterialId":null,"price":9.9,"productTypeId":null,"enterpriseInfoId":null,"insertUser":null,"insertDate":null,"updateUser":null,"updateDate":null,"delFlag":null,"introduction":null,"relateId":null,"sarverFileName":"/2017/11/10/b81620c7296d43faa927ed3f581dba6b.jpg","cName":"饲料"},{"sqlId":null,"name":"创意透明圆形玻璃鱼缸","status":null,"productMaterialId":null,"price":58,"productTypeId":null,"enterpriseInfoId":null,"insertUser":null,"insertDate":null,"updateUser":null,"updateDate":null,"delFlag":null,"introduction":null,"relateId":null,"sarverFileName":null,"cName":"浮法玻璃"},{"sqlId":null,"name":"德彩原装热带鱼饲料","status":null,"productMaterialId":null,"price":25.9,"productTypeId":null,"enterpriseInfoId":null,"insertUser":null,"insertDate":null,"updateUser":null,"updateDate":null,"delFlag":null,"introduction":null,"relateId":null,"sarverFileName":null,"cName":"饲料"},{"sqlId":null,"name":"依华莱斯自动喂鱼器","status":null,"productMaterialId":null,"price":98.9,"productTypeId":null,"enterpriseInfoId":null,"insertUser":null,"insertDate":null,"updateUser":null,"updateDate":null,"delFlag":null,"introduction":null,"relateId":null,"sarverFileName":null,"cName":"塑料"},{"sqlId":null,"name":"碳纤维过滤棉活性炭鱼缸过滤棉过滤材料高密度滤材养鱼用品净化棉","status":null,"productMaterialId":null,"price":19.9,"productTypeId":null,"enterpriseInfoId":null,"insertUser":null,"insertDate":null,"updateUser":null,"updateDate":null,"delFlag":null,"introduction":null,"relateId":null,"sarverFileName":null,"cName":"棉"},{"sqlId":null,"name":"森森缸外过滤器壁挂式鱼缸过滤桶水族箱外置静音草缸冲氧过滤设备","status":null,"productMaterialId":null,"price":88,"productTypeId":null,"enterpriseInfoId":null,"insertUser":null,"insertDate":null,"updateUser":null,"updateDate":null,"delFlag":null,"introduction":null,"relateId":null,"sarverFileName":null,"cName":"塑料"}]
     */

    public int totalCount;
    public List<OtherBean> list;

    public static class OtherBean implements Serializable{
        /**
         * sqlId : null
         * name : 玻璃板鱼缸
         * status : null
         * productMaterialId : null
         * price : 13
         * productTypeId : null
         * enterpriseInfoId : null
         * insertUser : null
         * insertDate : null
         * updateUser : null
         * updateDate : null
         * delFlag : null
         * introduction : null
         * relateId : null
         * sarverFileName : /2017/11/10/32b0ab787c60416a8454899dfed8150b.jpg
         * cName : 亚克力玻璃
         */

        public String sqlId;
        public String name;
        public Double price;
        public String sarverFileName;
        public String cName;
    }
}
