package com.gage.petfish_android.app;


public class Contact {

    //   public static final String HOST = "http://192.168.1.222:8011";
    public static final String HOST = "http://192.168.1.157:8022";//http://182.254.231.144:8022
    /**
     * 首页获取数据
     */
    public static String GETINFORNATION = "/getInformation";
    /**
     * 周边产品数据
     */
    public static String GETPATFISHPRODUCT = "/getPetfishProduct";
    /**
     * 周边产品点击获取详细
     */
    public static String GETPRODUCTDETAIL = "/getProductDetail";

    /**
     * 联系我们
     */
    public static String GETCONTACTUS = "/getContactUs";
    /**
     * 查询周边数据填充
     */
    public static String SEARCHZBCP = "/searchZBCP";
//    /**
//     * 周边产品检索条件搜索
//     */
//    public static String GETSEARCHERPRODUCT = "/getSearchProduct";
    /**
     * 观赏鱼-展示页面下拉框
     */
    public static String SEARCHMC = "/searchMC";
    /**
     * 观赏鱼-展示页面下拉框条件查询
     */
    public static String GETSEARCHFISH = "/getSearchFish";
    /**
     * 鱼品种,名称搜索
     */
    public static String SEARCHPZY = "/searchPZY";
//    /**
//     * 鱼品种,名称搜索条件搜索
//     */
//    public static String GETSEARCHLIST = "/getSearchList";
    /**
     * 首页各列表点击获取详细
     */
    public static String GETDETAILED = "/getDetailed";
    /**
     * 服务器版本
     */
    public static String SERVICECODE = "/dlzj.rest/Rest/apkInfo";
    /**
     * 判断用户是否已登录
     */
    public static String REPEATLOGIN = "/dlzj.rest/Rest/repeatLogin";

}
