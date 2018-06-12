/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.comment.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ningpai.comment.bean.Comment;
import com.ningpai.util.PageBean;

/**
 * 评论服务类接口
 * 
 * @author NINGPAI-zhangqiang
 * @since 2013年12月24日 下午6:52:43
 * @version 1.0
 */
public interface CommentServiceMapper {

    /**
     * 查询所有评论 分页
     * 
     * @see PageBean {@link com.ningpai.util.PageBean}
     * @see Comment {@link com.ningpai.comment.bean.Comment}
     * @params thirdId 第三方id
     * @return @see com.ningpai.util.PageBean {@link com.ningpai.util.PageBean}
     */
    PageBean selectAllComment(PageBean pageBean, Comment comment,Long thirdId);

    /**
     * 删除评论
     * 
     * @see commentIds 评论编号
     * @see java.lang.Integer {@link java.lang.Integer}
     * @return 0 失败 1成功
     */
    int deleteComment(String[] commentIds);

    /**
     * 根据评论编号 查询评论
     * 
     * @see java.lang.Integer {@link java.lang.Integer}
     * @param commentId
     * @return Comment {@link com.ningpai.comment.bean.Comment}
     */
    Comment selectByCommentId(Long commentId);

    /**
     * 查询所用评论 分页
     * 
     * @see PageBean {@link com.ningpai.util.PageBean
     * @see thirdId 第三方id
     * @see Comment {@link com.ningpai.comment.bean.Comment}
     * @return @see com.ningpai.util.PageBean {@link com.ningpai.util.PageBean}
     */
    PageBean selectAllConsult(PageBean pageBean, Comment comment,Long thirdId);

    /**
     * 根据商品编号查询所有评论
     * 
     * @see PageBean {@link com.ningpai.util.PageBean}
     * @param goodsId
     *            商品编号
     * @param type
     *            评论类型 传null将查询咨询 0查询好评 1是中评 2是差评 3全部
     * @param parmaString
     *            咨询内容
     * @return PageBean {@link com.ningpai.util.PageBean}
     */
    PageBean selectCommByGoodsId(PageBean pageBean, Long goodsId, Character type, String parmaString);

    /**
     * 根据商品编号查询所有评论
     * 新版移动端商品详情页使用
     * @author houyichang 2015/10/21
     *
     * @see PageBean {@link com.ningpai.util.PageBean}
     * @param goodsId
     *            商品编号
     * @param type
     *            评论类型 传null将查询咨询 0查询好评 1是中评 2是差评 3全部
     * @param parmaString
     *            咨询内容
     * @return PageBean {@link com.ningpai.util.PageBean}
     */
    PageBean selectCommByGoodsIdHyc(PageBean pageBean, Long goodsId, Character type, String parmaString);

    /**
     * 根据商品编号查询所有评论
     * 新版移动端商品详情页使用
     * @author houyichang 2015/10/21
     *
     * @see PageBean {@link com.ningpai.util.PageBean}
     * @param goodsId
     *            商品编号
     * @param type
     *            评论类型 传null将查询咨询 0查询好评 1是中评 2是差评 3全部
     * @param parmaString
     *            咨询内容
     * @return PageBean {@link com.ningpai.util.PageBean}
     */
    PageBean selectCommDetailByGoodsIdHyc(PageBean pageBean, Long goodsId, Character type, String parmaString);

    /**
     * 查询商品评论条数
     *
     * @param goodsId 商品id
     * @param commentType 评论类型
     * @return 查询到的行数
     * */
    int queryCommentCountByType(Long goodsId,Character commentType,String paramString);

    /**
     * 根据商品编号查询所有评论
     * 
     * @see PageBean {@link com.ningpai.util.PageBean}
     * @param goodsId
     *            商品编号
     * @param type
     *            评论类型 传null将查询咨询 0查询好评 1是中评 2是差评 3全部
     * @param parmaString
     *            咨询内容
     * @param item
     *            咨询项目
     * @return PageBean {@link com.ningpai.util.PageBean}
     */
    PageBean selectCommByGoodsId(PageBean pageBean, Long goodsId, Character type, String parmaString, String item);

    /**
     * 修改评论
     * 
     * @param comment
     *            评论对象{@link com.ningpai.comment.bean.Comment}
     * @return 0失败 1成功
     */
    int updateComment(Comment comment);

    /**
     * 添加商品评论
     * 
     * @param comment
     *            评论信息 {@link Comment}
     * @return 评论编号
     */
    int addGoodsComment(Comment comment);

    /**
     * 查询会员咨询
     * 
     * @param customerId
     *            会员编号 {@link Long}
     * @param flag
     *            回复标记
     * @param PageBean
     *            分页辅助类 {@link com.ningpai.util.PageBean}
     * @return PageBean 分页辅助类 {@link com.ningpai.util.PageBean}
     */
    PageBean queryCustConsult(Long customerId, String flag, PageBean pageBean);

    /**
     * 查询会员评论
     * 
     * @param customerId
     *            会员编号 {@link Long}
     * @param pageBean
     *            分页辅助类 {@link com.ningpai.util.PageBean}
     * @return PageBean 分页辅助类 {@link com.ningpai.util.PageBean}
     */
    PageBean queryCustComment(Long customerId, PageBean pageBean);


    /**
     * 查询会员评论  前台我的评论使用
     *
     * @param customerId
     *            会员编号 {@link Long}
     * @param pageBean
     *            分页辅助类 {@link com.ningpai.util.PageBean}
     * @return PageBean 分页辅助类 {@link com.ningpai.util.PageBean}
     */
    PageBean queryCommentByCust(Long orderId,Long customerId, PageBean pageBean);

    /**
     * 导出所有评论
     * @param response
     */
    void exportComment(HttpServletResponse response);

    /**
     * 导出评论模板
     * @param response
     */
    void exportCommentTemp(HttpServletResponse response);

    /**
     * 导入评论
     * @param request
     * @param response
     * @param multiRequest
     * @return
     */
    String importCommentByExcel(HttpServletRequest request, HttpServletResponse response, MultipartHttpServletRequest multiRequest);

    /**
     * 查询第三方店铺的评级
     * @param thirdId
     *             店铺Id
     * @return
     *     评论对象
     */
    Comment selectSellerComment(Long thirdId);

    /**
     * 根据订单商品编号和会员编号查询商品评论晒单信息
     * @param orderGoodsId
     * @param customerId
     * @return
     */
    Comment queryCommentByOrderGoodsId(Long orderGoodsId, Long customerId);



}
