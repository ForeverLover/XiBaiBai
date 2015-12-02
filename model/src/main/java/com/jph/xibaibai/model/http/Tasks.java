package com.jph.xibaibai.model.http;

/**
 * 接口请求任务ID
 * CREATED BY JPH ON 2015/8/12.
 */
public interface Tasks {

    /**
     * 登录
     */
    int LOGIN = 0XA98AC7;
    /**
     * 注册
     */
    int REGISTER = 0XA98AC8;
    /**
     * 修改用户信息
     */
    int CHANGEUSERINFO = 0XA98AC9;
    /**
     * 获取用户信息
     */
    int GETUSERINFO = 0XA98ACA;
    /**
     * 获取车辆
     */
    int GETCAR = 0XA98ACB;

    /**
     * 删除用户车辆
     */
    int DELETECAR = 0XA98ACC;
    /**
     * 修改车辆信息
     */
    int CHANGECARINFO = 0XA98ACD;
    /**
     * 添加用户车辆
     */
    int ADDCAR = 0XA98ACE;
    /**
     * 设置用户默认地址
     */
    int SETDEFAULTADDRESS = 0XA98ACF;
    /**
     * 设置用户默认车辆
     */
    int SETDEFAULTCAR = 0XA98AB0;
    /**
     * 设置（新增，修改，找回）支付密码
     */
    int SETPAYPASSWORD = 0XA98AB1;
    /**
     * 关于我们信息
     */
    int GETABOUT = 0XA98AB2;
    /**
     * 查询账户余额
     */
    int GETACCOUNTBALANCE = 0XA98AB3;
    /**
     * 修改用户头像
     */
    int CHANGEUSERHEAD = 0XA98AB4;
    /**
     * 获取用户资金记录
     */
    int GETPAYRECORDS = 0XA98AB5;
    /**
     * 查询抵用卷
     */
    int GETCOUPONS = 0XA98AB6;
    /**
     * 获取优惠券使用信息
     */
    int GETCOUPONRECORD = 0XA98AB7;
    /**
     * 提交反馈
     */
    int SUGGESTION = 0XA98AB8;
    /**
     * 产品查询
     */
    int GETPRODUCTS = 0XA98AB9;
    /**
     * 查询员工位置
     */
    int LOCATIONEMPLOYEE = 0XA98ABA;

    /**
     * 查询订单
     */
    int GETORDERS = 0XA98ABB;
    /**
     * 新订单
     */
    int NEWORDER = 0XA98ABC;

    /**
     * 查询用户地址
     */
    int GETADDRESS = 0XA98BD;

    /**
     * 设置地址
     */
    int SETADDRESS = 0XA98BE;

    /**
     * 发送验证码
     */
    int SEND_CODE = 0XA98BF;

    /**
     * 全部评价
     */
    int GET_COMMENT = 0XA98C0;

    /**
     * 添加评论
     */
    int ADD_COMMENT = 0XA98C1;

    /**
     * 获取预约时间段
     */
    int GET_TIME_SCOPE = 0XA98C2;

    /**
     * 得到diy产品
     */
    int GET_DIYPRODUCT = 0XA98C3;

    /**
     * 订单详情
     */
    int ORDER_DETAIL = 0XA98C4;

    int GET_CITY_LIMIT_RULE = 0XA98C5;

    int CANCEL_ORDER = 0XA98C6;

    int COMPLAIN = 0XA98C7;

    int CHANGE_CAR = 0XA98C8;

    int LIST_MESSAGE = 0XA98C9;

    int GET_TOP_UP_INFO = 0xa98d0;

    int GET_ALL_BRAND = 0XA98D1;

    int RESET_PSWD = 0XA98D2;

    int SEND_RESET_CODE = 0XA98D3;

    int INTEGRAL = 0XA98D4;

    int VERSION_INFO = 0xa98d5;

    int WITHDRAW = 0xa98d6;

    int COMMIT_INVALID_ORDER = 0xa98d7;

    int GETSERVICE_TIME = 0XA98D8;

    int GETSERVICE_AREA = 0XA98D9;

    int GETBEAUTY_SERVICE = 0XA98F1;

    int GEWASHCAR_DATA = 0XA98F2;

    int CONFIMORDER = 0XA98F3;

    int APPLYOPENCITY = 0XA98F4;
    /**获取版本信息*/
    int VERSIONINFO = 0XA98F5;
}
