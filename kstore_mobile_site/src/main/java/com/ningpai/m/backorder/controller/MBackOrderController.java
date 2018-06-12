package com.ningpai.m.backorder.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ningpai.m.customer.vo.GoodsBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ningpai.goods.bean.GoodsProduct;
import com.ningpai.goods.service.GoodsProductService;
import com.ningpai.goods.vo.GoodsProductDetailViewVo;
import com.ningpai.logger.util.OperaLogUtil;
import com.ningpai.m.backorder.service.ReturnGoodsService;
import com.ningpai.m.common.service.SeoService;
import com.ningpai.m.customer.service.CustomerService;
import com.ningpai.m.customer.vo.CustomerAllInfo;
import com.ningpai.m.customer.vo.CustomerConstants;
import com.ningpai.m.customer.vo.OrderInfoBean;
import com.ningpai.m.order.service.OrderMService;
import com.ningpai.m.util.LoginUtil;
import com.ningpai.order.bean.BackOrder;
import com.ningpai.order.bean.BackOrderGeneral;
import com.ningpai.order.bean.BackOrderLog;
import com.ningpai.order.bean.Order;
import com.ningpai.order.bean.OrderGoods;
import com.ningpai.order.service.BackOrderLogService;
import com.ningpai.order.service.BackOrderService;
import com.ningpai.order.service.OrderService;
import com.ningpai.system.bean.SystemsSet;
import com.ningpai.system.service.IsBackOrderService;
import com.ningpai.util.MyLogger;

/**
 * 退货/退单 控制器
 * @author qiyuanyuan
 *
 */
@Controller
public class MBackOrderController{
    /** 记录日志对象 */
    private static final MyLogger LOGGER = new MyLogger(MBackOrderController.class);
    
    private static final String URLREDIRECT = "redirect:/loginm.html?url=/myaccount.html";
    
    private static final String LOGGERINFO1 = "】-->用户名：";
    
    @Resource(name="OrderMService")
    private OrderMService orderMService;
    
    @Resource(name="customerServiceM")
    private CustomerService customerService;
    
    @Resource(name="IsBackOrderService")
    private IsBackOrderService isbackOrderService;
    
    @Resource(name = "SeoService")
    private SeoService seoService;
    
    @Resource(name = "GoodsProductService")
    private GoodsProductService goodsProductService;
    
    @Resource(name="BackOrderService")
    private BackOrderService backOrderService;
    
    // 退单操作日志
    @Resource(name="BackOrderLogService")
    private BackOrderLogService backOrderLogService;
    
    @Resource(name = "OrderService")
    private OrderService orderService;
    
    @Resource(name="MReturnGoodsService")
    private ReturnGoodsService goodsService;

    /**
     * 查询退单限时
     */
    @Resource(name = "IsBackOrderService")
    private IsBackOrderService isBackOrderService;
    
    /**
     * 申请退款控制器
     * 
     * @param orderId
     * @return
     */
    @RequestMapping("customer/mapplybackmoney")
    public ModelAndView applybackmoney(HttpServletRequest request, Long orderId) {
        ModelAndView mav = null;
        //查询订单详情
        Order order = orderMService.getPayOrder(orderId);
        if (LoginUtil.checkLoginStatus(request)) {
            Long customerId = (Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID);
            // 订单详细信息
            OrderInfoBean backorder = customerService.queryOrderByCustIdAndOrderId(orderId, customerId);
            // 4174,1-
            String str = "";
            if(backorder.getGoods() !=null && !backorder.getGoods().isEmpty()){
                
                for (int i = 0; i < backorder.getGoods().size(); i++) {
                    Long goodsids = backorder.getGoods().get(i).getGoodsId();
                    Long goodsNum = backorder.getGoods().get(i).getGoodsNum();
                    str += goodsids + "," + goodsNum + "-";
                }
            }
            // 获取退款说明
            SystemsSet systemsSet = isbackOrderService.getIsBackOrder();
            mav = new ModelAndView("customer/apply_back_money").addObject("order", order).addObject("backorder", backorder).addObject("backPriceRemark", systemsSet.getBackPriceRemark())
                    .addObject("cusId", customerId).addObject("backGoodsIdAndSum", str);
        }else {
            mav = new ModelAndView(URLREDIRECT);
        }
        return seoService.getCurrSeo(mav);
    }
    

    /**
     * 申请退货控制器
     * 
     * @param orderId
     * @return
     */
    @RequestMapping("customer/applybackgoods")
    public ModelAndView applyBackGoods(HttpServletRequest request, Long orderId) {
        ModelAndView mav = null;
        Order order = orderMService.getPayOrder(orderId);
        mav = new ModelAndView("customer/applybackmoney");
        //没有参加优惠的话，计算订单金额
        BigDecimal price=new BigDecimal(0);
        //标识为未参加促销的订单
        String staCheck="0";
        //标识为未使用优惠券吗的订单
        String isUseCoupon="0";
        //
//        if(order.getCouponNo() != null && order.getCouponNo() != ""){
//            isUseCoupon="1";
//        }
        //
        if(!"0.00".equals(order.getOrderPrePrice().toString())||(order.getOrderIntegral()!=null && !"".equals(order.getOrderIntegral()) && order.getOrderIntegral()>0L)){
            //计算去除运费的金额
            price=order.getOrderPrice().subtract(order.getExpressPrice());
            //标识为已参加促销的订单
            staCheck="1";
        }
        if (LoginUtil.checkLoginStatus(request)) {
            Long customerId = (Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID);
            // 订单详细信息
            OrderInfoBean backorder = customerService.queryOrderByCustIdAndOrderId(orderId, customerId);
            for (int i = 0; i < backorder.getGoods().size(); i++) {
                // 查询货品详细信息
                GoodsProductDetailViewVo goodsProductDetailViewVo = goodsProductService.queryViewVoByProductId(backorder.getGoods().get(i).getGoodsId());
                // 规格信息
                backorder.getGoods().get(i).setSpecVo(goodsProductDetailViewVo.getSpecVo());
                
            }
            // 获取退货说明
            String backInfoRemark = isbackOrderService.queryBackInfoRemark();
            BigDecimal orderPrice  = order.getOrderPrice();
            BigDecimal expressPrice = order.getExpressPrice();
            BigDecimal newprice = BigDecimal.ZERO;
//            if(orderPrice != null && expressPrice != null){
//                 newprice= orderPrice.subtract(expressPrice);
//            }
            if(CollectionUtils.isNotEmpty(backorder.getGoods())){
                for(GoodsBean gb:backorder.getGoods()){
                    newprice = newprice.add(gb.getGoodsBackPrice());
                }
            }
            //拿bigdecimal类型的值与0比较是否小于0 返回-1代表小于 返回0代表等于 返回1代表大于
            if (BigDecimal.ZERO.compareTo(newprice) >= 0){
                //截取两位小数点
                newprice = new BigDecimal(0.01).setScale(2, BigDecimal.ROUND_HALF_UP);
            }
            mav = new ModelAndView("customer/apply_back_goods").addObject("order", order).addObject("backorder", backorder).addObject("backInfoRemark", backInfoRemark).addObject("price",price).addObject("staCheck",staCheck)
                    .addObject("cusId", customerId).addObject("newprice", newprice).addObject("isUseCoupon",isUseCoupon);
        }else {
            mav = new ModelAndView(URLREDIRECT);
        }
        // 获取退货说明
        return seoService.getCurrSeo(mav);
    }
    
    @RequestMapping("/backorderpricedetail")
    public ModelAndView backOrderPriceDetail(HttpServletRequest request, Long orderId){
        ModelAndView mav =  new ModelAndView();
        //判断用户是否登陆
        if (LoginUtil.checkLoginStatus(request)) {
            //查询订单详情
            Order backorder = orderMService.getPayOrder(orderId);
            // 根据订单编号查找退单信息
            BackOrder bOrder = backOrderService.queryBackOrderByOrderCodeAndIsback(backorder.getOrderCode(), "2");
            List imglist =null;
            if (bOrder.getUploadDocuments() != null&&!"".equals(bOrder.getUploadDocuments())) {
                imglist= new ArrayList();
                String[] imgs = bOrder.getUploadDocuments().split(",");
                for (int i = 0; i < imgs.length; i++) {
                    imglist.add(imgs[i]);
                }
            }
            // 获取退单日志信息
            List<BackOrderLog> backOrderLogs = backOrderLogService.queryByBackId(bOrder.getBackOrderId());  
            mav = new ModelAndView("customer/back_order_detail").addObject("backorder", backorder).addObject("bOrder", bOrder).addObject("imglist", imglist)
                    .addObject("backOrderLogs", backOrderLogs);
        }else{
            mav = new ModelAndView(URLREDIRECT);
        }
        return seoService.getCurrSeo(mav);
    }
    
    @RequestMapping("/backordergoodsdetail")
    public ModelAndView backOrderGoodsDetail(HttpServletRequest request, Long orderId){
        ModelAndView mav =  new ModelAndView();
        //判断用户是否登陆
        if (LoginUtil.checkLoginStatus(request)) {
            //订单信息
            Order backorder = orderMService.getPayOrder(orderId);
            // 根据订单编号和退货退款标记查找退单信息
            BackOrder bOrder = backOrderService.queryBackOrderByOrderCodeAndIsback(backorder.getOrderCode(),"1");

            //根据退单商品字段 筛选出退单商品
            String backStr = bOrder.getBackGoodsIdAndSum();
            List<Long> orderGoodIds = new ArrayList<Long>();
            if(StringUtils.isNotEmpty(backStr)){
                String [] goods = backStr.split("-");
                if(goods.length>0){
                    for(int i=0;i<goods.length;i++){
                        //如果是满足条件的 退货字段 即有两个 ，则将ordergoods表主键 放入集合中
                        if(goods[i].indexOf(",")!=goods[i].lastIndexOf(",")){
                            orderGoodIds.add(Long.valueOf(goods[i].substring(goods[i].lastIndexOf(",")+1,goods[i].length())));
                        }
                    }
                }
            }
            //遍历订单商品 去除没有退货的商品 留下退货的
            if(null!=backorder && CollectionUtils.isNotEmpty(backorder.getOrderGoodsList())){
                for(int i=0;i<backorder.getOrderGoodsList().size();i++){
                    OrderGoods og = backorder.getOrderGoodsList().get(i);
                    if(!orderGoodIds.contains(og.getOrderGoodsId())){
                        backorder.getOrderGoodsList().remove(i);
                        i--;
                    }
                }
            }


            // 根据订单编号查找退单物流信息
            BackOrderGeneral general = backOrderService.queryBackOrderGeneral(bOrder.getBackOrderId());
            List imglist = new ArrayList();
            if (bOrder.getUploadDocuments() != null) {
                String[] imgs = bOrder.getUploadDocuments().split(",");
                for (int i = 0; i < imgs.length; i++) {
                    imglist.add(imgs[i]);
                }
            }
            // 获取退单日志信息
            List<BackOrderLog> backOrderLogs = backOrderLogService.queryByBackId(bOrder.getBackOrderId());
            // 查询退货商品信息（根据orderId和退单编号查询）
            List<OrderGoods> goodslist = orderService.queryOrderGoodsByOrderIdAndBackCode(orderId, bOrder.getBackOrderCode());
            for (int j = 0; j < goodslist.size(); j++) {
                GoodsProduct goodsProduct = goodsProductService.queryProductByGoodsId(goodslist.get(j).getGoodsInfoId());
                goodslist.get(j).setGoodsImg(goodsProduct.getGoodsInfoImgId());
                goodslist.get(j).setGoodsName(goodsProduct.getGoodsInfoName());
                goodslist.get(j).setGoodsCode(goodsProduct.getGoodsInfoItemNo());
            }   
            mav = new ModelAndView("customer/back_order_detail").addObject("backorder", backorder).addObject("bOrder", bOrder).addObject("goodslist", goodslist)
                    .addObject("imglist", imglist).addObject("backOrderLogs", backOrderLogs).addObject("general", general);
        }else{
            mav = new ModelAndView(URLREDIRECT);
        }
        return seoService.getCurrSeo(mav);
    }
    
    /***
     * 
     * @param wlname
     *            物流名称
     * @param wlno
     *            物流单号
     * @param orderNo
     *            订单号
     * @return
     */
    @RequestMapping("/saveMBackOrderGeneral")
    @ResponseBody
    public int saveBackOrderGeneral(HttpServletRequest request, String wlname, String wlno, String orderNo) {
        if (LoginUtil.checkLoginStatus(request)) {
            // 当前登录会员id
            Long customerId = (Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID);
            // 当前登录成功的会员信息
            CustomerAllInfo customerAllInfo = customerService.selectByPrimaryKey(customerId);
            // 非空验证 订单号
            if (null != orderNo) {
                LOGGER.info("新增一条退货的物流信息");
                // 操作日志
                OperaLogUtil.addOperaLog(request, customerAllInfo.getCustomerUsername(), "新增退单物流信息", "新增退单物流信息-->需要执行退单操作的订单号【" + orderNo + "】,物流信息：名称【" + wlname + "】,物流单号【" + wlno
                        + LOGGERINFO1 + customerAllInfo.getCustomerUsername());
            }
            return goodsService.saveBackOrderGeneral(wlname, wlno, orderNo);
        }else{
            return 0;
        }
    }

    /**
     * 验证是否超出退单时间
     *
     * @param orderId
     *            :要操作的退单ID
     * @return
     */
    @RequestMapping("customer/timeBackOrderByIdM")
    @ResponseBody
    public int timeBackOrderById(HttpServletRequest request, Long orderId) {
        int time = 1;
        Long customerId = (Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID);
        //查询订单完成时间
        Order order = this.orderService.getPayOrder(orderId);
        //查询退单限制时间
        SystemsSet systemsSet = this.isBackOrderService.getIsBackOrder();
        Long day = systemsSet.getLimitOrderTime();
        //判断是否超出退单限制时间
        if(order.getGetGoodsTime()!=null&&new Date().getTime() - order.getGetGoodsTime().getTime() < (day * 60 * 60 * 24 * 1000)){
            time = 1;
        }else{
            time = 0;
        }
        return time;
    }

}