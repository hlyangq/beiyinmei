package com.ningpai.m.backorder.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ningpai.goods.vo.GoodsProductVo;
import com.ningpai.m.backorder.bean.BackOrderGeneral;
import com.ningpai.m.backorder.dao.ReturnGoodsMapper;
import com.ningpai.m.backorder.service.ReturnGoodsService;
import com.ningpai.m.customer.vo.CustomerConstants;
import com.ningpai.order.bean.BackOrder;
import com.ningpai.order.bean.BackOrderLog;
import com.ningpai.order.bean.Order;
import com.ningpai.order.bean.OrderGoods;
import com.ningpai.order.dao.BackOrderLogMapper;
import com.ningpai.order.dao.BackOrderMapper;
import com.ningpai.order.service.OrderService;
import com.ningpai.util.PageBean;

/***
 * 退货记录
 * 
 * @author qiyuanyuan
 *
 */
@Service("MReturnGoodsService")
public class ReturnGoodsServiceImpl implements ReturnGoodsService {

    private static final Logger LOGGER = Logger.getLogger(ReturnGoodsServiceImpl.class);

    // 注入dao层
    @Resource(name = "MReturnGoodsMapper")
    private ReturnGoodsMapper returngoods;
    // 注入订单dao层
    @Resource(name = "OrderService")
    private OrderService orderservice;
    //
    @Resource(name = "BackOrderMapper")
    private BackOrderMapper backOrderMapper;
    // 退单操作日志
    @Resource(name = "BackOrderLogMapper")
    private BackOrderLogMapper backOrderLogMapper;

    @Override
    @Transactional
    public int saveBackOrderGeneral(String wlname, String wlno, String orderNo) {
        // 保存物流信息返回值
        int result = 0;
        try {
            BackOrderGeneral general = new BackOrderGeneral();
            // 根据订单号获取退单对象
            List<BackOrder> orders = returngoods.selectBackOrderId(orderNo);
            for (int i = 0; i < orders.size(); i++) {
                BackOrder backOrder = orders.get(i);
                // 物流单号
                general.setOgisticsNo(wlno); 
                // 物流名称
                general.setOgisticsName(wlname); 
                // 退单ID
                general.setBackOrderId(backOrder.getBackOrderId()); 
                // 时间
                general.setCreatTime(new Date()); 
                result = returngoods.saveGeneral(general);
                if (1 == result) {
                    // 修改订单状态为 退货中 ：10
                    Order order = orderservice.getPayOrderByCode(orderNo);
                    order.setBackPrice(order.getBackPrice());
                    // 订单状态
                    order.setOrderStatus("10");
                    // 修改订单对象
                    result = returngoods.updateOrder(order); 
                    if (1 == result) {

                        if (order.getBusinessId() == 0) {
                            // 同意退货平台订单
                            backOrder.setBackCheck("1");
                        } else {
                            // 待商家收货商家订单
                            backOrder.setBackCheck("3"); 
                        }
                        result = backOrderMapper.updateByPrimaryKeySelective(backOrder);
                    }
                    if (1 == result) {
                        // 插入退单操作日志
                        BackOrderLog backOrderLog = new BackOrderLog();
                        backOrderLog.setBackLogStatus("4");
                        backOrderLog.setBackOrderId(backOrder.getBackOrderId());
                        backOrderLog.setBackLogPerson("customer");
                        backOrderLog.setBackLogTime(new Date());
                        result = backOrderLogMapper.insert(backOrderLog);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("",e);
            result = 0;
        }
        return result;

    }

    /**
     * 新增一条退货记录 ，更改交易表状态退单金额，更新商品信息表
     * 
     * @param orderId
     *            id:0 退款 id:1 退货
     * @return
     */
    @Override
    @Transactional
    public Boolean saveReturnGoodsDetail(Long orderId, String returnyuanyin, Long id) {
        // 保存退单记录返回的结果
        int result = 0;
        // 操作状态
        boolean bool = true; 
        // 退单信息
        BackOrder backOrder = null; 
        // 交易对象
        Order order = null; 
        // Customer customer = null; //当前登录会员
        try {
            order = this.returngoods.selectOrderById(orderId);
            // customer =
            // this.returngoods.selectCustomerById(order.getCustomerId());
            // 根据会员ID查询单个会员对象
            // 退单对象
            backOrder = new BackOrder(); 
            // 退单单号
            backOrder.setBackOrderCode(this.createBackOrderNo());
            // 按照日期自动生成
            // 订单单号（从交易记录获取）
            backOrder.setOrderCode(order.getOrderCode()); 
            // 退单时间（当前系统时间）
            backOrder.setBackTime(new Date());
            // 退单原因（前台传过来）
            backOrder.setBackRemark(returnyuanyin); 
            // 退款总金额（交易记录获取）
            backOrder.setBackPrice(order.getOrderPrice()); 
            // 退单人真是姓名
            backOrder.setBackRealName(order.getShippingPerson()); 
            // 商家Id(交易记录获取)
            backOrder.setBusinessId(order.getBusinessId()); 
            // 0:退款1：退货
            backOrder.setOrderstatus(id); 
            if (id == 0) {
                // 状态需退款
                backOrder.setBackCheck("6"); 
            }
            // 非空验证退单单号
            if (null != backOrder.getBackOrderCode()) {
                LOGGER.info("退单成功，退单单号为：" + backOrder.getBackOrderCode());
            }
            result = returngoods.saveBackOrder(backOrder);// 保存退单记录
        } catch (Exception e) {
            LOGGER.error("",e);
            bool = false;
        }

        if (result == 1 && bool && updateOrder(backOrder, orderId, id)) {
            bool = true;
        } else {
            bool = false;
        }
        return bool;
    }

    /***
     * //更新交易表的状态
     * 
     * @param backOrder
     *            退单表
     * @param orderId
     *            交易表记录id
     * @return
     */
    public Boolean updateOrder(BackOrder backOrder, Long orderId, Long id) {
        // 交易对象
        Order order = null; 
        // 操作状态
        boolean bool = true; 
        // 更新数据返回的结果
        int result = 0;
        // 更改交易记录的状态
        try {
            order = new Order();
            // order = this.returngoods.selectOrderById(orderId);
            order.setOrderId(orderId);
            if (id == 0) {
                // 12退款审核中
                order.setOrderStatus("12"); 
            } else {
                // 7退货审核中
                order.setOrderStatus("7");
            }
            // 退单金额
            order.setBackPrice(backOrder.getBackPrice()); 
            result = returngoods.updateOrder(order);
        } catch (Exception e) {
            LOGGER.error("",e);
            bool = false;
        }
        if (result == 1 && bool && updateOrderGoods(order, backOrder)) {
            bool = true;
        } else {
            bool = false;
        }
        return bool;
    }

    /***
     * 更新记录商品对象
     * 
     * @param order
     *            交易对象
     * @param backOrder
     *            退单表对象
     * @return
     */
    public Boolean updateOrderGoods(Order order, BackOrder backOrder) {
        // 订单商品信息表
        List<OrderGoods> orderGoods = null; 
        // 操作状态
        boolean bool = true; 
        // 更新订单商品表返回的结果
        int updateOrderGoodsResult = 0;
        try {
            orderGoods = returngoods.selectOrderGoodsById(order.getOrderId());
            OrderGoods goods = orderGoods.get(0);
            // 退单单号
            goods.setBackOrderCode(backOrder.getBackOrderCode());
            // 更改订单商品表表
            updateOrderGoodsResult = returngoods.updateOrderGoods(goods);

        } catch (Exception e) {
            LOGGER.error("更新记录商品对象失败！", e);
        }

        if (updateOrderGoodsResult == 1) {
            bool = true;
        } else {
            bool = false;
        }
        return bool;

    }

    /***
     * 生成退单单号 系统时间+随机六位数字
     * 
     * @return
     */
    public String createBackOrderNo() {
        String d = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
        Random r = new Random();
        Double d1 = r.nextDouble();
        String s = d1 + "";
        s = d + s.substring(3, 3 + 6);
        return s;
    }
    
    /*
     * 查询当前会员的退单信息
     * @see com.ningpai.m.customer.service.CustomerOrderService#queryAllBackOrders(java.util.Map, com.ningpai.util.PageBean)
     */
    @Override
    public PageBean queryAllBackOrders(Map<String, Object> paramMap, PageBean pb) {
        // 退单的数据条数
        Long count = returngoods.searchTotalCountBack(paramMap);
        pb.setRows(Integer.parseInt(count == null ? "0" : count.toString()));
        pb.setPageSize(3);
        paramMap.put(CustomerConstants.STARTNUM, pb.getStartRowNum());
        paramMap.put(CustomerConstants.ENDNUM, pb.getEndRowNum());
        // 查询该会员下面的
        List<Object> backOrders = returngoods.queryAllMyBackOrders(paramMap);
        if (null != backOrders && !backOrders.isEmpty()) {
            for (int i = 0; i < backOrders.size(); i++) {
                BackOrder bo = (BackOrder) backOrders.get(i);
                if (!"".equals(bo.getBackGoodsIdAndSum())) {
                    // 获取退单对象 下面的退单的 商品Id
                    String[] strs = bo.getBackGoodsIdAndSum().split("-");
                    // 处理数据
                    if (strs.length > 0) {
                        // 遍历退单对象下面的商品Id
                        for (int j = 0; j < strs.length; j++) {
                            String strss = strs[j];
                            // 获取第J个商品的Id
                            Long goodsId = Long.valueOf(strss.substring(0, strss.indexOf(",")));
                            Long backNum = null;
                            //退单数量
                            if (strss.lastIndexOf(",") == strss.indexOf(",")){
                                //老数据
                                backNum = Long.valueOf(strss.substring(strss.indexOf(",") + 1, strss.length()));
                            }else {
                                backNum = Long.valueOf(strss.substring(strss.indexOf(",") + 1, strss.lastIndexOf(",")));
                            }
                            // 根据ID获取单个商品的详细信息
                            GoodsProductVo orderProductVo = backOrderMapper.selectGoodsById(goodsId);
                            // 循环把查询获取到的商品信息放入到退单对象的商品集合中
                            bo.getOrderGoodslistVo().add(orderProductVo);
                            bo.setBackNum(backNum);
                            
                        }
                    }
                }
            }
            pb.setList(backOrders);
        }
        return pb;
    }

    /**
     * 根据customerId查询退款退货订单数量
     *
     * @param customerId
     */
    @Override
    public int queryCancleOrderCount(Long customerId) {
        Map<String, Object> paramMap = new HashMap<String,Object>();
        paramMap.put("customerId",customerId);
        return returngoods.searchTotalCountBack(paramMap).intValue();
    }

}
