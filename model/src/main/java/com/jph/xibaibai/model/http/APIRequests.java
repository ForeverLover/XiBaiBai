package com.jph.xibaibai.model.http;

import android.util.Log;

import com.jph.xibaibai.model.entity.Address;
import com.jph.xibaibai.model.entity.Car;
import com.jph.xibaibai.model.entity.ConfirmOrder;
import com.jph.xibaibai.model.entity.Order;
import com.jph.xibaibai.model.entity.UserInfo;
import com.lidroid.xutils.http.RequestParams;
import com.ta.utdid2.android.utils.StringUtils;

import java.io.File;

/**
 * Created by jph on 2015/8/12.
 */
public class APIRequests extends BaseAPIRequest implements IAPIRequests {

    private XRequestCallBack XRequestCallBack;

    public APIRequests(XRequestCallBack XRequestCallBack) {
        this.XRequestCallBack = XRequestCallBack;
    }

    @Override
    public void login(String phone, String pswd) {
        RequestParams requestParams = createRequestParams();
        requestParams.addBodyParameter("iphone", phone);
        requestParams.addBodyParameter("pwd", pswd);
        request(XRequestCallBack, Tasks.LOGIN, "/login", requestParams);
    }

    @Override
    public void register(String phone, String pswd) {

        RequestParams requestParams = createRequestParams();
        requestParams.addBodyParameter("iphone", phone);
        requestParams.addBodyParameter("pwd", pswd);
        request(XRequestCallBack, Tasks.REGISTER, "/register", requestParams);
    }

    /**
     * 修改用户信息
     *
     * @param id       用户ID    int
     * @param userInfo 用户信息
     */
    @Override
    public void changeUserInfo(int id, UserInfo userInfo,int flagModify) {
        RequestParams requestParams = createRequestParams();
        switch (flagModify){
            case 1:
                requestParams.addBodyParameter("uname", String.valueOf(userInfo.getUname()));
                break;
            case 2:
                requestParams.addBodyParameter("sex", String.valueOf(userInfo.getSex()));
                break;
            case 3:
                requestParams.addBodyParameter("age", String.valueOf(userInfo.getAge()));
                break;
        }
        requestParams.addBodyParameter("uid", String.valueOf(id));
        Log.i("Tag", "uname=>" + userInfo.getUname() + "/uid=>" + String.valueOf(id) + "/sex=>" + userInfo.getSex() + "/age=>" + userInfo.getAge());
        request(XRequestCallBack, Tasks.CHANGEUSERINFO, "/user_msg_up", requestParams);
    }

    /**
     * 获取用户信息
     *
     * @param uid 用户id
     * @return UserInfo.class
     */
    @Override
    public void getUserInfo(int uid) {
        RequestParams requestParams = createRequestParams();
        requestParams.addBodyParameter("uid", String.valueOf(uid));
        request(XRequestCallBack, Tasks.GETUSERINFO, "/user_msg_select", requestParams);
    }

    @Override
    public void getVersionInfo() {
        RequestParams requestParams = createRequestParams();
        requestParams.addBodyParameter("version_type", "1");
        request(XRequestCallBack, Tasks.VERSIONINFO, "/version_up", requestParams);
    }

    /**
     * 获取洗车服务的时间
     */
    @Override
    public void getServiceTime() {
        RequestParams requestParams = createRequestParams();
        request(XRequestCallBack, Tasks.GETSERVICE_TIME, "/servertime", requestParams);
    }

    /**
     * 联网访问服务区域
     */
    @Override
    public void getServiceArea() {
        RequestParams requestParams = createRequestParams();
        request(XRequestCallBack, Tasks.GETSERVICE_AREA, "/server_latlng", requestParams);
    }

    /**
     * 查询用户车辆
     *
     * @param uid
     * @return AllCar.class
     */
    @Override
    public void getCar(int uid) {
        RequestParams requestParams = createRequestParams();
        requestParams.addBodyParameter("uid", String.valueOf(uid));
        request(XRequestCallBack, Tasks.GETCAR, "/car_select", requestParams);
    }

    /**
     * 删除用户车辆
     *
     * @param uid         用户id
     * @param id          车辆id
     * @param c_plate_num 车牌号
     */
    @Override
    public void deleteCar(int uid, int id, String c_plate_num) {
        RequestParams requestParams = createRequestParams();
        requestParams.addBodyParameter("uid", String.valueOf(uid));
        requestParams.addBodyParameter("id", String.valueOf(id));
        requestParams.addBodyParameter("c_plate_num", String.valueOf(c_plate_num));
        request(XRequestCallBack, Tasks.DELETECAR, "/car_delete", requestParams);
    }

    /**
     * 修改车辆信息
     *
     * @param car
     */
    @Override
    public void changeCarInfo(Car car) {

    }

    /**
     * 添加用户车辆
     * uid	用户ID	int
     * address	地址	String
     * address_lg	地址经度	String
     * address_lt	地址纬度	String
     * Address_info	详细地址	String
     */
    @Override
    public void addCar(Car car) {
        RequestParams requestParams = createRequestParams();
        requestParams.addBodyParameter("uid", String.valueOf(car.getUid()));
        requestParams.addBodyParameter("c_img", String.valueOf(car.getC_img()));
        requestParams.addBodyParameter("c_plate_num", String.valueOf(car.getC_plate_num()));
        requestParams.addBodyParameter("c_type", String.valueOf(car.getC_type()));
        requestParams.addBodyParameter("c_brand", String.valueOf(car.getC_brand()));
        requestParams.addBodyParameter("c_color", String.valueOf(car.getC_color()));
        requestParams.addBodyParameter("add_time", String.valueOf(car.getAdd_time()));
        requestParams.addBodyParameter("c_remark", String.valueOf(car.getC_remark()));
        request(XRequestCallBack, Tasks.ADDCAR, "/car_insert", requestParams);
    }

    @Override
    public void changeCar(Car car) {
        RequestParams requestParams = createRequestParams();
        requestParams.addBodyParameter("id", String.valueOf(car.getId()));
        requestParams.addBodyParameter("uid", String.valueOf(car.getUid()));
        requestParams.addBodyParameter("c_img", String.valueOf(car.getC_img()));
        requestParams.addBodyParameter("c_plate_num", String.valueOf(car.getC_plate_num()));
        requestParams.addBodyParameter("c_type", String.valueOf(car.getC_type()));
        requestParams.addBodyParameter("c_brand", String.valueOf(car.getC_brand()));
        requestParams.addBodyParameter("c_color", String.valueOf(car.getC_color()));
        requestParams.addBodyParameter("add_time", String.valueOf(car.getAdd_time()));
        requestParams.addBodyParameter("c_remark", String.valueOf(car.getC_remark()));
        request(XRequestCallBack, Tasks.CHANGE_CAR, "/car_up", requestParams);
    }

    /**
     * 设置用户默认地址
     *
     * @param uid
     * @param id  地址id
     */
    @Override
    public void setDefaultAddress(int uid, int id) {

    }

    /**
     * 设置用户默认车辆
     *
     * @param uid
     * @param id  车辆id
     */
    @Override
    public void setDefaultCar(int uid, int id) {
        RequestParams requestParams = createRequestParams();
        requestParams.addBodyParameter("uid", String.valueOf(uid));
        requestParams.addBodyParameter("id", String.valueOf(id));
        request(XRequestCallBack, Tasks.SETDEFAULTCAR, "/setup_default_car", requestParams);
    }

    /**
     * 设置（新增，修改，找回）支付密码
     *
     * @param uid         用户ID	int	否
     * @param pay_pwd     旧支付密码	int	否
     * @param new_pay_pwd 新支付密码	int	否
     * @param staut       状态值	int	否	传0时候为第一次新增支付密码，新增支付密码不用传现支付密码，不是新增的时候不要传staut参数，
     */
    @Override
    public void setPayPassword(int uid, String pay_pwd, String new_pay_pwd, int staut) {

    }

    /**
     * 关于我们信息
     *
     * @param official_weixi     官方微信
     * @param official_weixi_url 官方微信地址
     * @param official_weibo     官方微博
     * @param official_weibo_url 官方微博地址
     * @param official_cor_sve   官方客服电话
     */
    @Override
    public void getAbout(String official_weixi, String official_weixi_url, String official_weibo, String official_weibo_url, String official_cor_sve) {

    }

    /**
     * 查询账户余额
     *
     * @param uid 用户id
     * @return AccountBalance.class
     */
    @Override
    public void getAccountBalance(int uid) {

        RequestParams requestParams = createRequestParams();
        requestParams.addBodyParameter("uid", String.valueOf(uid));
        request(XRequestCallBack, Tasks.GETACCOUNTBALANCE, "/pay_select", requestParams);
    }

    /**
     * 修改用户头像
     *
     * @param uid
     * @param u_img
     */
    @Override
    public void changeUserHead(int uid, File u_img) {
        RequestParams requestParams = createRequestParams();


        requestParams.addBodyParameter("uid", String.valueOf(uid));
        if (u_img != null) {
            requestParams.addBodyParameter("file", u_img);
        }

        request(XRequestCallBack, Tasks.CHANGEUSERHEAD, "/user_msg_u_img", requestParams);
    }

    /**
     * 获取用户资金记录
     *
     * @param uid
     * @return PayRecord.class
     */
    @Override
    public void getPayRecords(int uid) {

        RequestParams requestParams = createRequestParams();
        requestParams.addBodyParameter("uid", String.valueOf(uid));
        request(XRequestCallBack, Tasks.GETPAYRECORDS, "/user_pay_manage", requestParams);
    }

    /**
     * 查询抵用卷
     *
     * @param uid
     * @return Coupon.class
     */
    @Override
    public void getCoupons(int uid) {
        RequestParams requestParams = createRequestParams();
        requestParams.addBodyParameter("uid", String.valueOf(uid));
        request(XRequestCallBack, Tasks.GETCOUPONS, "/user_coupons_select", requestParams);
    }

    /**
     * 获取优惠券使用信息
     *
     * @param uid
     * @return CouponRecord.class
     */
    @Override
    public void getCouponRecord(int uid) {

    }

    /**
     * 提交反馈
     *
     * @param uid
     * @param content
     */
    @Override
    public void suggestion(int uid, String content) {
        RequestParams requestParams = createRequestParams();
        requestParams.addBodyParameter("uid", String.valueOf(uid));
        requestParams.addBodyParameter("content", String.valueOf(content));
        request(XRequestCallBack, Tasks.SUGGESTION, "/advice_insert", requestParams);
    }

    /**
     * 产品查询
     *
     * @return Product.class
     */
    @Override
    public void getProducts() {
        request(XRequestCallBack, Tasks.GETPRODUCTS, "/pro_select");
    }

    /**
     * 查询员工位置
     *
     * @retun EmployeeLocation.class
     */
    @Override
    public void locationEmployee() {

    }

    /**
     * 查询订单
     *
     * @param uid   用户ID	Int
     * @param state 查询状态值	String 传1查询进行中传2查询已完成，为空查询全部
     * @return Order.class
     */
    @Override
    public void getOrders(int uid, String state, int page) {
        RequestParams requestParams = createRequestParams();
        requestParams.addBodyParameter("uid", String.valueOf(uid));
        requestParams.addBodyParameter("state", String.valueOf(state));
        requestParams.addBodyParameter("page", String.valueOf(page));
        request(XRequestCallBack, Tasks.GETORDERS, "/order_select", requestParams);
    }

    /**
     * 新订单
     *
     * @param order
     */
    @Override
    public void newOrder(Order order) {
        RequestParams requestParams = createRequestParams();

        requestParams.addBodyParameter("uid", String.valueOf(order.getUid()));
        requestParams.addBodyParameter("location", order.getLocation());
        requestParams.addBodyParameter("location_lg", order.getLocation_lg());
        requestParams.addBodyParameter("location_lt", order.getLocation_lt());
        requestParams.addBodyParameter("remark", order.getRemark());
        requestParams.addBodyParameter("p_ids", order.getP_ids());
        requestParams.addBodyParameter("total_price", String.valueOf(order.getTotal_price()));
        requestParams.addBodyParameter("order_reg_id", String.valueOf(order.getOrder_reg_id()));
        requestParams.addBodyParameter("plan_time", order.getPlan_time());
        requestParams.addBodyParameter("c_ids", order.getC_ids());
        requestParams.addBodyParameter("p_order_time_cid", String.valueOf(order.getP_order_time_cid()));
        requestParams.addBodyParameter("day", String.valueOf(order.getDay()));
        if (order.getFileVoice() != null) {
            requestParams.addBodyParameter("file", order.getFileVoice());
        }

        request(XRequestCallBack, Tasks.NEWORDER, "/order_insert", requestParams);
    }

    @Override
    public void getAddress(int uid) {
        RequestParams requestParams = createRequestParams();
        requestParams.addBodyParameter("uid", String.valueOf(uid));
        request(XRequestCallBack, Tasks.GETADDRESS, "/address_select", requestParams);
    }

    @Override
    public void setAddress(Address address) {
        RequestParams requestParams = createRequestParams();
        requestParams.addBodyParameter("uid", String.valueOf(address.getUid()));
        requestParams.addBodyParameter("address", address.getAddress());
        requestParams.addBodyParameter("address_lg", address.getAddress_lg());
        requestParams.addBodyParameter("address_lt", address.getAddress_lt());
        requestParams.addBodyParameter("address_info", address.getAddress_info());
        requestParams.addBodyParameter("address_type", String.valueOf(address.getAddress_type()));
        request(XRequestCallBack, Tasks.SETADDRESS, "/address_insert", requestParams);
    }

    @Override
    public void sendCode(String iphone, String code) {
        RequestParams requestParams = createRequestParams();
        requestParams.addBodyParameter("iphone", String.valueOf(iphone));
        requestParams.addBodyParameter("code", String.valueOf(code));
        request(XRequestCallBack, Tasks.SEND_CODE, "/re_iphone", requestParams);
    }

    @Override
    public void getComment(int uid, int page) {
        RequestParams requestParams = createRequestParams();
        requestParams.addBodyParameter("uid", String.valueOf(uid));
        requestParams.addBodyParameter("page", String.valueOf(page));
        request(XRequestCallBack, Tasks.GET_COMMENT, "/comment_select", requestParams);
    }

    @Override
    public void addComment(int uid, int emp_id, int order_id, String comment, float star) {
        RequestParams requestParams = createRequestParams();
        requestParams.addBodyParameter("uid", String.valueOf(uid));
        requestParams.addBodyParameter("emp_id", String.valueOf(emp_id));
        requestParams.addBodyParameter("order_id", String.valueOf(order_id));
        requestParams.addBodyParameter("comment", String.valueOf(comment));
        requestParams.addBodyParameter("star", String.valueOf(star));
        request(XRequestCallBack, Tasks.ADD_COMMENT, "/comment_insert", requestParams);
    }

    @Override
    public void getTimeScope(long day) {
        Log.v("Tag","tim scope");
        RequestParams requestParams = createRequestParams();
        requestParams.addBodyParameter("day", String.valueOf(day));
        request(XRequestCallBack, Tasks.GET_TIME_SCOPE, "/time_config_select", requestParams);
    }

    @Override
    public void getWashInfo(int uid) {
        RequestParams requestParams = createRequestParams();
        requestParams.addBodyParameter("uid", String.valueOf(uid));
        request(XRequestCallBack, Tasks.GEWASHCAR_DATA, "/washinfo");
    }

    @Override
    public void getBeautyService() {
        request(XRequestCallBack, Tasks.GETBEAUTY_SERVICE, "/product_info");
    }

    @Override
    public void getDIYProduct() {
        request(XRequestCallBack, Tasks.GET_DIYPRODUCT, "/diy_select");
    }

    @Override
    public void confirmOrderInfo(ConfirmOrder confirmOrder) {
        Log.i("Tag", "执行了confirmOrderInfo");
        RequestParams requestParams = createRequestParams();
        requestParams.addBodyParameter("uid", confirmOrder.getUserId());
        requestParams.addBodyParameter("location", confirmOrder.getCarAddress());
        requestParams.addBodyParameter("location_lg", confirmOrder.getCarLocateLg());
        requestParams.addBodyParameter("location_lt", confirmOrder.getCarLocateLt());
        requestParams.addBodyParameter("p_ids", confirmOrder.getProductId());
        requestParams.addBodyParameter("total_price", confirmOrder.getAllTotalPrice());
        requestParams.addBodyParameter("c_ids", confirmOrder.getCarsId());
        if(confirmOrder.getCouponsId() != -1){
            requestParams.addBodyParameter("coupons_id", confirmOrder.getCouponsId()+"");
        }
        Log.i("Tag", "uid=" + confirmOrder.getUserId());
        Log.i("Tag", "location=" + confirmOrder.getCarAddress());
        Log.i("Tag", "location_lg=" + confirmOrder.getCarLocateLg());
        Log.i("Tag", "location_lt=" + confirmOrder.getCarLocateLt());
        Log.i("Tag", "p_ids=" + confirmOrder.getProductId());
        Log.i("Tag", "total_price=" + confirmOrder.getAllTotalPrice());
        Log.i("Tag", "c_ids=" + confirmOrder.getCarsId());
        Log.i("Tag", "coupons_id=" + confirmOrder.getCouponsId());
        if(!"".equals(confirmOrder.getReMark())){
            requestParams.addBodyParameter("remark", confirmOrder.getReMark());
        }
        if(!"".equals(confirmOrder.getAppointDay())){
            requestParams.addBodyParameter("day", confirmOrder.getAppointDay());
        }
        if(confirmOrder.getAudioFile() != null){
            requestParams.addBodyParameter("audio", confirmOrder.getAudioFile());
        }
        request(XRequestCallBack, Tasks.CONFIMORDER, "/order_insert",requestParams);
    }

    @Override
    public void getOrderDetail(int order_id) {
        RequestParams requestParams = createRequestParams();
        requestParams.addBodyParameter("order_id", String.valueOf(order_id));
        request(XRequestCallBack, Tasks.ORDER_DETAIL, "/order_msg_select", requestParams);
    }

    @Override
    public void getCityLimitRule(String city_name) {
        RequestParams requestParams = createRequestParams();
        requestParams.addBodyParameter("city_name", String.valueOf(city_name));
        request(XRequestCallBack, Tasks.GET_CITY_LIMIT_RULE, "/city_limit", requestParams);
    }

    @Override
    public void cancelOrder(int uid, int order_id) {
        RequestParams requestParams = createRequestParams();
        requestParams.addBodyParameter("uid", String.valueOf(uid));
        requestParams.addBodyParameter("order_id", String.valueOf(order_id));
        request(XRequestCallBack, Tasks.CANCEL_ORDER, "/cancel_order", requestParams);
    }

    @Override
    public void complain(int uid, int order_id, int emp_id, String content) {
        RequestParams requestParams = createRequestParams();
        requestParams.addBodyParameter("uid", String.valueOf(uid));
        requestParams.addBodyParameter("order_id", String.valueOf(order_id));
        requestParams.addBodyParameter("emp_id", String.valueOf(emp_id));
        requestParams.addBodyParameter("content", String.valueOf(content));
        request(XRequestCallBack, Tasks.COMPLAIN, "/complaint", requestParams);
    }

    @Override
    public void listMessage(int uid) {
        RequestParams requestParams = createRequestParams();
        requestParams.addBodyParameter("uid", String.valueOf(uid));
        request(XRequestCallBack, Tasks.LIST_MESSAGE, "/admin_msg_select", requestParams);
    }

    @Override
    public void getTopUpInfo(int uid, double money) {
        RequestParams requestParams = createRequestParams();
        requestParams.addBodyParameter("uid", String.valueOf(uid));
        requestParams.addBodyParameter("money", String.valueOf(money));
        request(XRequestCallBack, Tasks.GET_TOP_UP_INFO, "/recharge", requestParams);
    }

    @Override
    public void getAllBrand() {
        request(XRequestCallBack, Tasks.GET_ALL_BRAND, "/all_carbrand_select");
    }

    @Override
    public void resetPswd(String iphone, String pwd) {
        RequestParams requestParams = createRequestParams();
        requestParams.addBodyParameter("iphone", iphone);
        requestParams.addBodyParameter("pwd", pwd);
        request(XRequestCallBack, Tasks.REGISTER, "/register2", requestParams);
    }

    @Override
    public void sendResetPswdCode(String iphone, String code) {
        RequestParams requestParams = createRequestParams();
        requestParams.addBodyParameter("iphone", String.valueOf(iphone));
        requestParams.addBodyParameter("code", String.valueOf(code));
        request(XRequestCallBack, Tasks.SEND_CODE, "/re_iphone2", requestParams);
    }

    @Override
    public void getMyIntegral(int uid) {
        RequestParams requestParams = createRequestParams();
        requestParams.addBodyParameter("uid", String.valueOf(uid));
        request(XRequestCallBack, Tasks.INTEGRAL, "/mypoints_select", requestParams);
    }

    @Override
    public void getVersionInfo(int version_type) {
        RequestParams requestParams = createRequestParams();
        requestParams.addBodyParameter("version_type", String.valueOf(version_type));
        request(XRequestCallBack, Tasks.VERSION_INFO, "/version_up", requestParams);
    }

    @Override
    public void withdraw(int uid, double money) {
        RequestParams requestParams = createRequestParams();
        requestParams.addBodyParameter("uid", String.valueOf(uid));
        requestParams.addBodyParameter("money", String.valueOf(money));
        request(XRequestCallBack, Tasks.WITHDRAW, "/applyfor", requestParams);
    }

    @Override
    public void commitInvalidOrderInfo(int uid, String city, String location, String lt, String lg) {
        RequestParams requestParams = createRequestParams();
        requestParams.addBodyParameter("uid", String.valueOf(uid));
        requestParams.addBodyParameter("city", String.valueOf(location));
        requestParams.addBodyParameter("location", String.valueOf(location));
        requestParams.addBodyParameter("location_lt", String.valueOf(lt));
        requestParams.addBodyParameter("location_lg", String.valueOf(lg));
        request(XRequestCallBack, Tasks.COMMIT_INVALID_ORDER, "/order_notopen", requestParams);
    }

    @Override
    public void applyOpenCity(int uid,String latitude,String longitude,String location) {
        RequestParams requestParams = createRequestParams();
        requestParams.addBodyParameter("uid", String.valueOf(uid));
        requestParams.addBodyParameter("latitude", String.valueOf(latitude));
        requestParams.addBodyParameter("longitude", String.valueOf(longitude));
        requestParams.addBodyParameter("address", location);
        request(XRequestCallBack, Tasks.APPLYOPENCITY, "/applyopne", requestParams);
    }
}
