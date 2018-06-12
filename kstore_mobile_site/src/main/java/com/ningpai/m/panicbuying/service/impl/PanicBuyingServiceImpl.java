package com.ningpai.m.panicbuying.service.impl;

import com.ningpai.channel.bean.ChannelAdver;
import com.ningpai.customer.bean.CustomerAddress;
import com.ningpai.m.goods.bean.GoodsDetailBean;
import com.ningpai.m.goods.service.GoodsProductService;
import com.ningpai.m.goods.util.ValueUtil;
import com.ningpai.m.goods.vo.GoodsProductVo;
import com.ningpai.m.panicbuying.bean.MarketingRushUtil;
import com.ningpai.m.panicbuying.dao.PanicBuyingDao;
import com.ningpai.m.panicbuying.service.PanicBuyingService;
import com.ningpai.marketing.bean.Marketing;
import com.ningpai.marketing.service.MarketingService;
import com.ningpai.system.service.DefaultAddressService;
import com.ningpai.system.util.AddressUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * PanicBuyingServiceImpl
 * 抢购接口 实现类
 * @author djk
 * @date 2016/1/18
 */
@Service
public class PanicBuyingServiceImpl  implements PanicBuyingService{

    /**
     * 调试日志
     */
    private static final Logger DEBUG = Logger.getLogger(PanicBuyingServiceImpl.class);

    /**
     * 默认地址
     */
    private static final String CHADDRESS = "chAddress";

    /**
     *静态变量distinctId
     * */
    private static final String DISTINCTID = "distinctId";

    /**
     * Spring注入
     */
    @Resource(name = "DefaultAddressService")
    private DefaultAddressService addressService;

    /**
     * 注入抢购数据库接口
     */
    @Resource
    private PanicBuyingDao panicBuyingDao;

    /**
     * 促销serivce
     */
    @Resource(name = "MarketingService")
    private MarketingService marketingService;

    /**
     * 注入货品接口
     */
    @Resource(name="HsiteGoodsProductService")
    private GoodsProductService productService;

    /**
     * 查询 抢购信息
     *
     * @param request
     * @return 返回抢购信息列表
     */
    @Override
    public List<MarketingRushUtil> queryMarketingRushUtils(HttpServletRequest request) {

        // 查询出参与抢购的货品
        List<GoodsProductVo> goodsProductVos = panicBuyingDao.queryGoodsProductVos();

        // 设置货品的价格后返回
        return setPricesByDistinctId(goodsProductVos, getDistinctId(request));
    }

    /**
     * 查询抢购的广告图片
     *
     * @return 返回抢购的广告图片
     */
    @Override
    public List<ChannelAdver> queryChannelAdvers() {
        return panicBuyingDao.queryChannelAdvers();
    }

    /**
     * 根据区域id设置货品的价格
     *
     * @param goodsProductVos 货品
     * @param distinctId      区域id
     * @return
     */
    private List<MarketingRushUtil> setPricesByDistinctId(List<GoodsProductVo> goodsProductVos, Long distinctId) {

        // 如果货品为空 则直接返回
        if (CollectionUtils.isEmpty(goodsProductVos)) {
            return new ArrayList<>();
        }

        List<MarketingRushUtil> marketingRushUtils = new ArrayList<MarketingRushUtil>();

        for (GoodsProductVo vo : goodsProductVos) {

            Marketing marketing = marketingService.selectRushMarket(vo.getMarketingId());
            GoodsDetailBean detailBean = productService.queryDetailBeanByProductIdForGroupon(vo.getGoodsInfoId(), distinctId);
            if (marketing != null) {
                MarketingRushUtil mrUtils = new MarketingRushUtil();
                String rushs = compareTime(marketing.getMarketingBegin(), marketing.getMarketingEnd());

                mrUtils.setRushTime(rushs);
                // 设置分仓价格、库存
                vo.setGoodsInfoPreferPrice(detailBean.getProductVo().getGoodsInfoPreferPrice());
                vo.setGoodsInfoStock(detailBean.getProductVo().getGoodsInfoStock());
                mrUtils.setGoodsProductVo(vo);
                mrUtils.setMarketing(marketing);
                marketingRushUtils.add(mrUtils);
            }
        }

        return marketingRushUtils;
    }



    /**
     * 获得区域id
     *
     * @param request
     * @return 返回 默认的区域id
     */
    private Long getDistinctId(HttpServletRequest request) {
        Long distinctId;
        if (null != request.getSession().getAttribute(ValueUtil.ADDRESS) && null == request.getSession().getAttribute(CHADDRESS)) {
            distinctId = ((CustomerAddress) request.getSession().getAttribute(ValueUtil.ADDRESS)).getDistrict().getDistrictId();
        } else {
            if (null == request.getSession().getAttribute(CHADDRESS)) {
                Long dId = addressService.getDefaultIdService();
                if (null == dId) {
                    dId = 1103L;
                    // 默认设置为南京建邺区仓库
                    distinctId = Long.valueOf(dId);
                } else {
                    distinctId = Long.valueOf(dId);
                }
            } else {
                /* 取session的数据 */
                distinctId = Long.parseLong(request.getSession().getAttribute(DISTINCTID).toString());
            }
        }

        return distinctId;
    }

    /**
     * 比较时间大小
     *
     * @param begin 开始时间
     * @param end   结算时间
     */
    private String compareTime(Date begin, Date end) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(new Date().getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date sysdate = df.parse(dateFormat.format(c.getTime()));
            Date begindate = df.parse(dateFormat.format(begin.getTime()));
            Date enddate = df.parse(dateFormat.format(end.getTime()));
            if (sysdate.getTime() <= begindate.getTime()) {
                // 没有开始
                return "1";
            } else if (sysdate.getTime() >= enddate.getTime()) {
                return "3";
            } else if (sysdate.getTime() >= begindate.getTime() && sysdate.getTime() <= enddate.getTime()) {
                // 已开始未结束
                return "2";
            } else {
                return "0";
            }
        } catch (ParseException e) {
            DEBUG.error("比较时间大小出错：" + e);
        }
        return "0";
    }
}
