/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.customer.vo;

import com.ningpai.common.util.ConstantUtil;

/**
 * 会员模块常量类
 *
 * @author NINGPAI-zhangqiang
 * @version 0.0.1
 * @since 2013年12月25日 下午8:02:59
 */
public final class CustomerConstants {

    /**
     * 会员中心页面
     */
    public static final String CUSTOMERCENTER = "customer/customercenter";
    /**
     * 我的订单页面
     */
    public static final String MYORDER = "customer/order_list";
    public static final String BACKORDER = "customer/back_order_list";
    /**
     * 个人信息页面
     */
    public static final String OWNERINFO = "customer/info";
    /**
     * 订单状态显示页面
     */
    public static final String ORDERDETAIL = "customer/order_detail";
    /**
     * Cookie 会员编号键
     */
    public static final String NPSTORE_CUSTOMERID = "npstore_customerId";
    /**
     * 收货地址页面
     */
    public static final String CUSTOMERADDRESS = "customer/address";
    /**
     * customer string
     */
    public static final String CUSTOMER = "customer";
    /**
     * 订单成功页面
     */
    public static final String SUCCESSORDER = "customer/successorder";
    /**
     * order String
     */
    public static final String ORDER = "order";
    /**
     * 跳转重置密码页面
     */
    public static final String RESETPASS = "customer/resetPass";
    /**
     * 提示页面
     */
    public static final String TIP = "customer/tip";

    /**
     * 跳转邮件验证页面
     */
    public static final String CHECKIDENTITY = "customer/checkEmail";
    /**
     *
     */
    public static final String FINDPASS = "customer/findPass";
    /**
     * 邮件发送成功
     */
    public static final String EMAILSUCCESS = "customer/emailSuccess";
    /**
     * 密码修改成功
     */
    public static final String MODIFYSUCCESS = "customer/modifysuccess";
    /**
     * 跳转登录页面
     */
    public static final String GOLOGIN = "gologin.htm";
    /**
     * queryCustomerAddress.htm
     */
    public static final String CUSTOMERADDRESSHTM = "queryCustomerAddress.htm";
    /**
     * String "customerId"
     */
    public static final String CUSTOMERID = "customerId";


    /**
     * 站内信 接收人id
     */
    public static final String RECEIVE_CUSTOMERID = "receiveCustomerId";
    /**
     * 积分页面
     */
    public static final String CUSTOMERPOINT = "customer/customerPoint";
    /**
     * startRowNum
     */
    public static final String STARTNUM = "startRowNum";
    /**
     * "endRowNum"
     */
    public static final String ENDNUM = "endRowNum";
    /**
     * "pb"
     */
    public static final String PB = "pb";
    /**
     * 会员首页 customer/index ,
     * 改为新页面customer/main 2015/10/10
     */
    //public static final String CUSTOMERINDEX = "customer/index";
    public static final String CUSTOMERINDEX = "member/main";
    /**
     * 会员积分页面 customer/integral
     */
    public static final String MYINTEGRAL = "customer/integral";
    /**
     * 浏览记录页面 customer/browserecord.ftl
     */
    public static final String BROWSERECORD = "customer/browserecord";
    /**
     * 我的收藏页面
     */
    public static final String MYCOLLECTIONS = "customer/collections";
    /**
     * 我的收藏页面
     */
    public static final String MYFOLLOW = "customer/follow";

    public static final String REFUNDLIST = "customer/cancelorder";
    /**
     * 投诉页面
     */
    public static final String ORDERCOMPLAIN = "customer/ordercomplain";
    /**
     * 填写投诉内容
     */
    public static final String TOCOMPLAIN = "customer/tocomplain";
    /**
     * 会员安全中心
     */
    public static final String SECURITYCENTER = "customer/accountsecurity";
    /**
     * 验证身份
     */
    public static final String VALIDATEIDENTITY = "customer/validateidentity";
    /**
     * 修改密码 邮箱 手机
     */
    public static final String MODIFYPEM = "customer/reirectpem";
    /**
     * 登录跳转
     */
    public static final String LOGINREDIRECT = "/login/redirect";
    /**
     * URL String
     */
    public static final String URL = "url";
    /**
     * /customer
     */
    public static final String CUSTOMERS = "/" + CUSTOMER;
    /**
     * ISO-8859-1
     */
    public static final String ISO = "ISO-8859-1";
    /**
     * utf-8
     */
    public static final String UTF = ConstantUtil.UTF;
    /**
     * "/securitycenter.html"
     */
    public static final String CENTERHTML = "/securitycenter.html";

    public static final String TYPE = "type";
    public static final String DATE = "date";
    /**
     * customer/consult
     */
    public static final String CONSULT = "customer/consult";
    /**
     * consults
     */
    public static final String CONSULTS = "consults";
    /**
     * customer/comment
     */
    public static final String COMMENT = "customer/comment";
    /**
     * 验证方式 ut
     */
    public static final String UT = "ut";
    public static final String REDIRECTLOGINTOINDEX = "redirect:/loginm.html";
    public static final String FILLADDRESS = "customer/filladdress";
    public static final String REGOSTER = "register/register";
    public static final String TOPWSSUCCESS = "customer/success";
    public static final String REDIRECTTOINDEX = "redirect:/customercenter.html";
    public static final String REDIRECTTOFINDPWD = "customer/findpwd1";
    public static final String REDIRECTTORESETPWD = "customer/findpwd2";
    public static final String REDIRECTTORESETPWDSUCC = "customer/findpwd3";




    /*手机端新页面--》我的账户中新页面*/
    /**
     * 我的账户页面
     */
    public static final String MYACCOUNT = "member/myaccount";
    /**
     * 修改昵称页面
     */
    public static final String CHANGENAME = "member/changename";
    /**
     * 修改姓名页面
     */
    public static final String CHANGENAME2 = "member/changename2";

    /**
     * 验证现在手机号页面
     */
    public static final String PHONEVALIDATE1 = "member/phonevalidate1";
    /**
     * 新手机号页面
     */
    public static final String PHONEVALIDATE3 = "member/phonevalidate3";
    /**
     * 地址列表页面
     */
    public static final String ADDRESSLIST = "order/my_account_address_list";
    /**
     * 地址列表页面
     */
    public static final String ORDERLIST = "order/address_list";
    /**
     * 地址列表页面
     */
    public static final String FORGETPASSWORD = "login/forgetpassword";
    /**
     * 账号安全页面
     */
    public static final String  ACCOUNTSAFE = "member/my_account_safe";
    /**
     * 修改密码页面
     */
    public static final String  CHANGEPASSWORD = "member/my_account_change_password";
    /**
     * 修改qq页面
     */
    public static final String  CHANGEQQ = "member/changeqq";

    /**
     * 会员中心-预存款交易名下页面
     */
    public static final String CUSTOMERTREADINFO = "customer/tradeinfo";

    /**
     * 设置支付密码
     */
    public static final String PAYPASSWORD = "member/paypassword";

    /**
     * 我的预付款页面
     */
    public static final String MYPREDEPOSITS = "member/mypredeposits";

    /**
     * 预付款充值
     */
    public static final String MYPREDEPOSITSRECHARGE = "member/mypredepositsrecharge";

    /**
     * 构造方法
     */
     CustomerConstants() {

    }

    /**
     * 修改支付密码页面
     */
    public static final String MODIFYPAYPASSWORD = "member/modifypaypassword";

    /**
     * 提现申请单
     */
    public static final String MYDEPOSITSCASH= "member/mypredepositscash";

    /**
     * 会员中心-提现记录页面
     */
    public static final String CASHRECORDS = "member/cashrecords";

    /**
     * 会员中心-提现记录详细页面
     */
    public static final String CASHRECORDSDETAIL = "member/cashrecords_detail";

    /**
     * 下一步设置支付密码页面
     */
    public static final String NEXTPAYPASSWORD = "member/nextpaypassword";

}
