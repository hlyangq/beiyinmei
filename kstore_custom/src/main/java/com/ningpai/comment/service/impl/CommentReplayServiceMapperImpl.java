/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.comment.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ningpai.comment.bean.Comment;
import com.ningpai.comment.bean.CommentReplay;
import com.ningpai.comment.dao.CommentMapper;
import com.ningpai.comment.dao.CommentReplayMapper;
import com.ningpai.comment.service.CommentReplayServiceMapper;
import com.ningpai.customer.bean.Customer;
import com.ningpai.other.util.IPAddress;

/**
 * 添加回复接口实现类
 * 
 * @author NINGPAI-zhangqiang
 * @since 2013年12月27日 下午5:25:46
 * @version 1.0
 */
@Service("commentReplayServiceMapper")
public class CommentReplayServiceMapperImpl implements CommentReplayServiceMapper {
    // spring注解
    private CommentReplayMapper commentReplayMapper;
    private CommentMapper commentMapper;

    /*
     * 
     * 
     * @see com.ningpai.comment.service.CommentReplayServiceMapper#insertSelective(CommentReplay record)
     */
    @Override
    @Transactional
    public int insertSelective(CommentReplay record) {
        Comment comm = commentMapper.selectByCommentId(record.getCommentId());
        if (!"1".equals(comm.getIsDisplay())) {
            comm.setIsDisplay("1");
            commentMapper.updateComment(comm);
        }

        // 根据咨询id查询第三方id
        Long thirdId = commentReplayMapper.queryThirdIdByCommentId(record.getCommentId());

        if (isDisplay(thirdId)) {
            record.setIsDisplay("1");
        } else {
            record.setIsDisplay("0");
        }

        return commentReplayMapper.insertSelective(record);
    }


    /**
     * 咨询是否展示
     *
     * @param thirdId 第三方id
     * @return 如果是第三方 返回true  否则返回false
     */
    private boolean isDisplay(Long thirdId) {
        if (null == thirdId || thirdId.equals(0L)) {
            return false;
        } else {
            return true;
        }
    }
    /*
     * 
     * 
     * @see com.ningpai.comment.service.CommentReplayServiceMapper#selectByCommentId(Long commentId)
     */
    @Override
    public List<CommentReplay> selectByCommentId(Long commentId) {
        return commentReplayMapper.selectByCommentId(commentId);
    }

    /*
     * 
     * 
     * @see com.ningpai.comment.service.CommentReplayServiceMapper#updateCommentRep (com.ningpai.comment.bean.CommentReplay)
     */
    @Override
    public int updateCommentRep(CommentReplay replay) {
        return commentReplayMapper.updateCommentRep(replay);
    }

    /*
     * 
     * 
     * @see com.ningpai.comment.service.CommentReplayServiceMapper#addCommentRepaly( javax.servlet.http.HttpServletRequest, java.lang.Long, java.lang.String)
     */
    @Override
    public int addCommentRepaly(HttpServletRequest request, Long commentId, String commentContent) {
        CommentReplay cr = new CommentReplay();
        // 设置IP
        cr.setReplayIp(IPAddress.getIpAddr(request));
        cr.setCommentId(commentId);
        cr.setCommentContent(commentContent);
        // 回复后立即显示
        cr.setIsDisplay("1");
        if(((Customer) request.getSession().getAttribute("cust")).getCustomerNickname() != null){
            cr.setCustomerNickname(((Customer) request.getSession().getAttribute("cust")).getCustomerNickname());
        }else{
            cr.setCustomerNickname(((Customer) request.getSession().getAttribute("cust")).getCustomerUsername());
        }
        cr.setCustomerId(Integer.parseInt(request.getSession().getAttribute("customerId").toString()));
        // 插入评论回复
        return commentReplayMapper.insertSelective(cr);
    }

    public CommentReplayMapper getCommentReplayMapper() {
        return commentReplayMapper;
    }

    @Resource(name = "commentReplayMapper")
    public void setCommentReplayMapper(CommentReplayMapper commentReplayMapper) {
        this.commentReplayMapper = commentReplayMapper;
    }

    public CommentMapper getCommentMapper() {
        return commentMapper;
    }

    @Resource(name = "commentMapper")
    public void setCommentMapper(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

}
