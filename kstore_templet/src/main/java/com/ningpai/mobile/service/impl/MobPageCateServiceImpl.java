package com.ningpai.mobile.service.impl;

import com.ningpai.mobile.bean.MobPageCate;
import com.ningpai.mobile.dao.MobHomePageMapper;
import com.ningpai.mobile.dao.MobPageCateMapper;
import com.ningpai.mobile.service.MobPageCateServcie;
import com.ningpai.mobile.vo.MobHomePageVo;
import com.ningpai.util.PageBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by dyt on 2016/10/19.
 */
@Service("MobPageCateServcie")
public class MobPageCateServiceImpl implements MobPageCateServcie {

    @Resource(name = "MobPageCateMapper")
    private MobPageCateMapper mobPageCateMapper;

    @Resource(name = "MobHomePageMapper")
    private MobHomePageMapper mobHomePageMapper;

    @Override
    public PageBean selectByPageBean(PageBean pb, Map<String, Object> param) {
        pb.setRows(this.mobPageCateMapper.selectCount(param));
        if(pb.getRows() > 0 ){
            param.put("startRowNum", pb.getStartRowNum());
            param.put("endRowNum",pb.getEndRowNum());

            MobHomePageVo vo = new MobHomePageVo();
            vo.setTemp4("0");
            List<Object> cates = this.mobPageCateMapper.selectByPageBean(param);
            for (Object obj:cates){
                MobPageCate cate = ((MobPageCate)obj);
                vo.setPageCateId(cate.getPageCateId());
                vo.setTemp3("0");//页面数仅统计页面类
                cate.setHomePageNum(mobHomePageMapper.selectCount(vo));
            }
            pb.setList(cates);
        }
        return pb;
    }

    @Override
    public MobPageCate getPageCate(Long pageCateId) {
        return this.mobPageCateMapper.selectById(pageCateId);
    }

    @Override
    public int delete(Long[] ids) {
        return this.mobPageCateMapper.deleteByList(Arrays.asList(ids));
    }

    @Override
    public int savePageCate(MobPageCate record) {
        Date date = new Date();
        record.setDelflag("0");
        record.setCreateDate(date);
        record.setUpdateDate(date);
        return this.mobPageCateMapper.insert(record);
    }

    @Override
    public int updatePageCate(MobPageCate record) {
        Date date = new Date();
        record.setUpdateDate(date);
        return this.mobPageCateMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<MobPageCate> selectAll(Map<String, Object> param) {
        return this.mobPageCateMapper.selectAll(param);
    }
}
