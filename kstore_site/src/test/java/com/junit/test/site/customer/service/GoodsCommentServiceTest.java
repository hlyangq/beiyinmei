package com.junit.test.site.customer.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.ningpai.site.goods.dao.GoodsProductMapper;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.comment.bean.Comment;
import com.ningpai.comment.service.CommentServiceMapper;
import com.ningpai.customer.service.CustomerPointServiceMapper;
import com.ningpai.site.customer.mapper.GoodsCommentMapper;
import com.ningpai.site.customer.service.GoodsCommentService;
import com.ningpai.site.customer.service.impl.GoodsCommentServiceImpl;
import com.ningpai.site.customer.vo.GoodsBean;

/**
 * 商品评论接口测试
 * @author djk
 *
 */
public class GoodsCommentServiceTest extends UnitilsJUnit3
{
	/**
     * 需要测试的Service
     */
    @TestedObject
    private GoodsCommentService goodsCommentService = new GoodsCommentServiceImpl();
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<GoodsCommentMapper> goodsCommentMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<CommentServiceMapper> commentServiceMapperMock;
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<CustomerPointServiceMapper> customerPointServiceMapperMock;


    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<GoodsProductMapper> goodsProductMapperMock;
    
    /**
     * 商品Bean
     */
    private GoodsBean goodsBean = new GoodsBean();
    
    /**
     * 模拟httpServletRequest
     */
    private MockHttpServletRequest request = new  MockHttpServletRequest ();
    
    /**
     * 评论实体
     */
    private Comment comment = new Comment();
    
    @Override
    protected void setUp() throws Exception 
    {
    	comment.setCommentId(1L);
    	comment.setCommentScore(new BigDecimal(1));
    }
    
    /**
     * 查询订单商品测试
     */
    @Test
    public void testSelectOrderGoods()
    {
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("goodsId", 1L);
        paramMap.put("customerId", 1L);
        paramMap.put("orderId", 1L);
        goodsCommentMapperMock.returns(goodsBean).selectOrderGoods(paramMap);
    	assertNotNull(goodsCommentService.selectOrderGoods(1L, 1L, 1L));
    }
    
    /**
     * 添加商品评论测试
     */
    @Test
    public void testAddGoodsComment()
    {

    	 assertEquals(0, goodsCommentService.addGoodsComment(request, comment, 1L));
    }
    
    /**
     * 添加商品评论测试
     */
    @Test
    public void testAddGoodsComment2()
    {
    	Long result = 1L;
    	assertEquals(result, goodsCommentService.addGoodsComment(request, comment));
    }
    
    /**
     *  查询商品评论测试
     */
    @Test
    public void testSelectGoodsComment()
    {
    	goodsBean.setCommentId(1L);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("goodsId", 1L);
        paramMap.put("customerId", 1L);
        paramMap.put("orderId", 1L);
        goodsCommentMapperMock.returns(goodsBean).selectOrderGoods(paramMap);
        commentServiceMapperMock.returns(new Comment()).selectByCommentId(1L);
        assertNotNull(goodsCommentService.selectGoodsComment(1L, 1L, 1L));
    }
    
    /**
     * 查询订单商品 -- 评论测试
     */
    @Test
    public void testSelectOrderGoodsToComment()
    {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("goodsId", 1L);
        paramMap.put("customerId", 1L);
        paramMap.put("orderId", 1L);
        goodsCommentMapperMock.returns(goodsBean).selectOrderGoodsToComment(paramMap);
    	assertNotNull(goodsCommentService.selectOrderGoodsToComment(1L, 1L, 1L));
    }
    
    /**
     * 检验订单商品是否已评价测试
     */
    @Test
    public void testCheckCommGoodFlag()
    {
    	Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("goodsId", 1L);
        paramMap.put("orderId", 1L);
        goodsCommentMapperMock.returns(1L).checkCommGoodFlag(paramMap);
    	Long result = 1L;
    	assertEquals(result, goodsCommentService.checkCommGoodFlag(1L, 1L));
    }
    
    /**
     * 第三方店铺评级测试
     */
    @Test
    public void testSelectSellerComment()
    {
    	commentServiceMapperMock.returns(comment).selectSellerComment(1L);
    	assertNotNull(goodsCommentService.selectSellerComment(1L));
    }
    
    /**
     * 判断是订单商品是否是会员的测试
     */
    @Test
    public void testCheckCommGoodIsUser()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("orderGoodsId", 1L);
        map.put("customerId", 1L);
        map.put("flag", "1");
        goodsCommentMapperMock.returns(1L).checkCommGoodIsUser(map);
    	assertEquals(true, goodsCommentService.checkCommGoodIsUser(1L, 1L, "1"));
    }
}
