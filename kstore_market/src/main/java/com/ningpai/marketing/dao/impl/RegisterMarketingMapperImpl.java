package com.ningpai.marketing.dao.impl;

import com.ningpai.manager.base.BasicSqlSupport;
import com.ningpai.marketing.bean.RegisterMarketing;
import com.ningpai.marketing.dao.RegisterMarketingMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 注册营销实现类 Created by Zhoux on 2015/4/1.
 */
@Repository("RegisterMarketingMapper")
public class RegisterMarketingMapperImpl extends BasicSqlSupport implements
        RegisterMarketingMapper {

    /**
     * 查询注册营销信息
     * 
     * @return
     */
    @Override
    public RegisterMarketing findRegisterMarketing() {
        RegisterMarketing registerMarketing = null;
        List<RegisterMarketing> blist = this
                .selectList("com.ningpai.web.dao.RegisterMarketingMapper.findRegisterMarketing");
        if (blist.isEmpty()) {
            registerMarketing = new RegisterMarketing();
            registerMarketing.setId(1L);
            this.insert(
                    "com.ningpai.web.dao.RegisterMarketingMapper.insertSelective",
                    registerMarketing);
        } else {
            registerMarketing = blist.get(0);
        }
        return registerMarketing;
    }

    /**
     * 更新注册营销信息
     * 
     * @param registerMarketing
     * @return
     */
    @Override
    public int updateRegisterCoupon(RegisterMarketing registerMarketing) {
        return this
                .update("com.ningpai.web.dao.RegisterMarketingMapper.updateRegisterCoupon",
                        registerMarketing);
    }

}
