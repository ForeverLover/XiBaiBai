package com.jph.xibaibai.model.http;

import com.jph.xibaibai.model.entity.Address;
import com.jph.xibaibai.model.entity.Car;
import com.jph.xibaibai.model.entity.ConfirmOrder;
import com.jph.xibaibai.model.entity.Order;
import com.jph.xibaibai.model.entity.UserInfo;

import java.io.File;

/**
 * Created by jph on 2015/8/12.
 */
public interface IAPIRequests {

    /**
     * 登录
     *
     * @param phone 手机号
     * @param pswd  密码
     */
    void login(String phone, String pswd);

    /**
     * 注册
     *
     * @param phone 手机号
     * @param pswd  密码
     */
    void register(String phone, String pswd);

    /**
     * 修改用户信息
     *
     * @param id       用户ID    int
     * @param userInfo 用户信息
     * @param flagModify 哪一个用户信息的标志
     */
    void changeUserInfo(int id, UserInfo userInfo,int flagModify);

    /**
     * 获取用户信息
     *
     * @param uid 用户id
     * @return UserInfo.class
     */
    void getUserInfo(int uid);

    /**
     * 获取版本信息，是否有更新
     */
    void getVersionInfo();

    /**
     * 获取洗车服务的时间
     */
    void getServiceTime();
    /**
     * 获取服务区域的经纬度
     */
    void getServiceArea();

    /**
     * 查询用户车辆
     *
     * @param uid
     * @return AllCar.class
     */
    void getCar(int uid);

    /**
     * 删除用户车辆
     *
     * @param uid         用户id
     * @param id          车辆id
     * @param c_plate_num 车牌号
     */
    void deleteCar(int uid, int id, String c_plate_num);

    /**
     * 修改车辆信息
     *
     * @param car
     */
    void changeCarInfo(Car car);

    /**
     * 添加用户车辆
     */
    void addCar(Car car);

    /**
     * 修改车辆
     *
     * @param car
     */
    void changeCar(Car car);

    /**
     * 设置用户默认地址
     *
     * @param uid
     * @param id  地址id
     */
    void setDefaultAddress(int uid, int id);

    /**
     * 设置用户默认车辆
     *
     * @param uid
     * @param id  车辆id
     */
    void setDefaultCar(int uid, int id);

    /**
     * 设置（新增，修改，找回）支付密码
     *
     * @param uid         用户ID	int	否
     * @param pay_pwd     旧支付密码	int	否
     * @param new_pay_pwd 新支付密码	int	否
     * @param staut       状态值	int	否	传0时候为第一次新增支付密码，新增支付密码不用传现支付密码，不是新增的时候不要传staut参数，
     */
    void setPayPassword(int uid, String pay_pwd, String new_pay_pwd, int staut);

    /**
     * 关于我们信息
     *
     * @param official_weixi     官方微信
     * @param official_weixi_url 官方微信地址
     * @param official_weibo     官方微博
     * @param official_weibo_url 官方微博地址
     * @param official_cor_sve   官方客服电话
     */

    void getAbout(String official_weixi, String official_weixi_url, String official_weibo, String official_weibo_url, String official_cor_sve);

    /**
     * 查询账户余额
     *
     * @param uid 用户id
     * @return AccountBalance.class
     */
    void getAccountBalance(int uid);

    /**
     * 修改用户头像
     *
     * @param uid
     * @param u_img
     */
    void changeUserHead(int uid, File u_img);


    /**
     * 获取用户资金记录
     *
     * @param uid
     * @return PayRecord.class
     */
    void getPayRecords(int uid);

    /**
     * 查询抵用卷
     *
     * @param uid
     * @return Coupon.class
     */
    void getCoupons(int uid);


    /**
     * 获取优惠券使用信息
     *
     * @param uid
     * @return CouponRecord.class
     */
    void getCouponRecord(int uid);

    /**
     * 提交反馈
     *
     * @param uid
     * @param content
     */
    void suggestion(int uid, String content);

    /**
     * 产品查询
     *
     * @return Product.class
     */
    void getProducts();

    /**
     * 查询员工位置
     *
     * @retun EmployeeLocation.class
     */
    void locationEmployee();


    /**
     * 查询订单
     *
     * @param uid   用户ID	Int
     * @param state 查询状态值	String 传1查询进行中传2查询已完成，为空查询全部
     * @return Order.class
     */
    void getOrders(int uid, String state, int page);

    /**
     * 新订单
     *
     * @param order
     */
    void newOrder(Order order);

    /**
     * 查询用户地址
     *
     * @param uid
     */
    void getAddress(int uid);

    /**
     * 设置地址
     *
     * @param address
     */
    void setAddress(Address address);

    /**
     * 发送验证码
     *
     * @param iphone
     * @param code
     */
    void sendCode(String iphone, String code);

    /**
     * 得到我的评论
     *
     * @param uid
     */
    void getComment(int uid, int page);

    /**
     * 添加订单
     *
     * @param order_id
     * @param comment
     * @param star
     */
    void addComment(int uid, int emp_id, int order_id, String comment, float star);

    /**
     * 当前天时间戳
     *
     * @param day
     */
    void getTimeScope(long day);

    /**
     * 得到外观清洗和整车清洗的数据
     */
    void getWashInfo(int uid);

    /**
     * 得到美容的项目
     */
    void getBeautyService();
    /**
     * 获取diy服务
     */
    void getDIYProduct();

    /**
     * 确认订单
     * @param confirmOrder
     */
    void confirmOrderInfo(ConfirmOrder confirmOrder);

    /**
     * 查询订单详情
     *
     * @param order_id
     */
    void getOrderDetail(int order_id);

    /**
     * 得到城市限号规则
     *
     * @param city_name
     */
    void getCityLimitRule(String city_name);

    /**
     * 取消订单
     *
     * @param uid
     * @param order_id
     */
    void cancelOrder(int uid, int order_id);

    /**
     * 投诉
     *
     * @param uid
     * @param order_id
     * @param emp_id
     * @param content
     */
    void complain(int uid, int order_id, int emp_id, String content);

    /**
     * 系统消息
     *
     * @param uid
     */
    void listMessage(int uid);

    /**
     * 得到充值信息
     *
     * @param uid
     * @param money
     */
    void getTopUpInfo(int uid, double money);

    /**
     * 得到所有品牌
     */
    void getAllBrand();

    /**
     * 发送重置密码验证码
     *
     * @param iphone
     * @param code
     */
    void sendResetPswdCode(String iphone, String code);

    /**
     * 重置密码
     *
     * @param iphone 手机号
     * @param pwd    密码
     */
    void resetPswd(String iphone, String pwd);

    /**
     * 得到积分
     *
     * @param uid
     */
    void getMyIntegral(int uid);

    /**
     * 得到最新版本信息
     *
     * @param version_type
     */
    void getVersionInfo(int version_type);

    /**
     * 提现
     *
     * @param uid
     * @param money
     */
    void withdraw(int uid, double money);

    /**
     * 提交无效的订单信息
     *
     * @param uid
     * @param city     城市
     * @param location 地址
     * @param lt       纬度
     * @param lg       经度
     */
    void commitInvalidOrderInfo(int uid, String city, String location, String lt, String lg);

    /**
     * 申请开通此城市
     */
    void applyOpenCity(int uid,String latitude,String longitude,String location);
}
