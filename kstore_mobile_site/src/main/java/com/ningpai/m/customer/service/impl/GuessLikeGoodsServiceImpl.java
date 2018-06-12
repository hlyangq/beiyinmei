package com.ningpai.m.customer.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ningpai.goods.bean.GoodsProduct;
import com.ningpai.goods.bean.ProductWare;
import com.ningpai.goods.service.ProductWareService;
import com.ningpai.m.customer.bean.Browserecord;
import com.ningpai.m.customer.mapper.BrowserecordMapper;
import com.ningpai.m.customer.service.GuessLikeGoodsService;
import com.ningpai.m.customer.vo.CustomerConstants;
import com.ningpai.m.goods.dao.GoodsMapper;
import com.ningpai.m.goods.dao.GoodsProductMapper;
import com.ningpai.m.goods.service.GoodsProductService;
import com.ningpai.system.service.DefaultAddressService;
/**
 * 猜你喜欢业务层实现类
 * @author zhangwc
 *
 */
@Service("GuessLikeGoodsServiceM")
public class GuessLikeGoodsServiceImpl implements GuessLikeGoodsService {
    private static final String CUSTOMERID="customerId";
    /**
     * 浏览记录
     */
    @Resource(name="browserecordMapper")
    private BrowserecordMapper browserecordMapper;
    /**
     * 查询分仓库存
     * */
    @Resource(name = "ProductWareService")
    private ProductWareService productWareService;
    
    @Resource(name="HsiteGoodsProductService")
    private GoodsProductService  goodsProductService;
    
    @Resource(name="HsiteGoodsMapper")
    private GoodsMapper goodsMapper;
    
    @Resource(name="HsiteGoodsProductMapper")
    private GoodsProductMapper goodsProductMapper;
    /**
     * 查询默认地址id
     * */
    @Resource(name = "DefaultAddressService")
    private DefaultAddressService defaultAddressService;
    /*
     * 计算猜你喜欢商品(根据浏览记录的货品的分类)
     * (non-Javadoc)
     * @see com.ningpai.m.customer.service.GuessLikeGoodsService#guessLikeGoodsList(Long customerId)
     */
    @Override
    public List<GoodsProduct> guessLikeGoodsList(Long customerId) {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put(CUSTOMERID, customerId);
        paramMap.put("isMobile", 1);
        paramMap.put(CustomerConstants.STARTNUM, 0);
        paramMap.put(CustomerConstants.ENDNUM, 20);
        //查询浏览记录
        List<Object> browereList = browserecordMapper.selectBrowserecord(paramMap);
        List<Long> cateIds = new ArrayList<Long>();
        long cateId;
        GoodsProduct goodsProductT;
        if(browereList != null && !browereList.isEmpty()){
            for(int i = 0; i < browereList.size(); i++){
                Browserecord browserecord = (Browserecord) browereList.get(i);
                //根据货品id查询货品
                goodsProductT  = goodsProductService.queryByGoodsInfoDetail(new Long(browserecord.getGoodsId()));
                //查询货品的分类
                if (goodsProductT != null) {
                    cateId = goodsMapper.selectByGoodsId(goodsProductT.getGoodsId());
                    cateIds.add(cateId);
                }
            }
        }
        List<GoodsProduct> goodsProducts = new ArrayList<GoodsProduct>();
        if (!cateIds.isEmpty()) {
            //set能自动去重复
            HashSet<Long> catSet = new HashSet<Long>(cateIds);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("catIds", catSet);
            //查询10条不重复的货品id
            List<Long> goodInfoIds = goodsProductMapper.queryGoodsInfoIdsByCatIds(map);
            if(null != goodInfoIds && goodInfoIds.size() > 0){
                map.put("goodInfoIds", goodInfoIds);
                //查询货品
                goodsProducts = goodsProductMapper.queryGoodsInfoByGoodInfoIds(map);
                for (GoodsProduct goodsProduct : goodsProducts) {
                    if(goodsProduct!=null){
                        //判断货品是否是第三方货品
                        if(goodsProduct.getIsThird() !=null && !"".equals(goodsProduct.getIsThird()) && "1".equals(goodsProduct.getIsThird())){
                            goodsProduct.setGoodsInfoStock(goodsProduct.getGoodsInfoStock());
                        }else{
                            // 1.查询出后台设置的默认地区
                            Long distinctId = this.defaultAddressService.getDefaultIdService();
                            if(distinctId == null){
                                distinctId = 1103L;
                            }
                            // 2.根据默认地区以及货品id去查询该货品的分仓库存
                            ProductWare productWare = this.productWareService.queryProductWareByProductIdAndDistinctId(new Long(goodsProduct.getGoodsInfoId()),distinctId);
                            if (productWare != null) {
                                goodsProduct.setGoodsInfoStock(productWare.getWareStock());
                            }
                        }
                    }
                }
            }
        }
        return goodsProducts;
    }

}
