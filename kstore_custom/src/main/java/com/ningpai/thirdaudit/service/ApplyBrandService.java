package com.ningpai.thirdaudit.service;

import com.ningpai.thirdaudit.bean.ApplyBrand;
import com.ningpai.util.PageBean;

/**
 * 应用品牌服务层接口
 * */
public interface ApplyBrandService {
    /**
     * 查询未审核列表
     * 
     * @param pb
     * @return
     */
    PageBean queryApplyBrand(PageBean pb);

    /**
     * 修改审核状态
     * 
     * @param
     * @return
     */
    int updateApplyBrand(Long[] applyBrandIds, String reason,
            String applyStatuts);

    /**
     * 根据自定义品牌名称修改
     * @param applyBrandName 自定义品牌名称
     * @return 修改结果
     */
    int updateApplyBrandByName(String applyBrandName);
}
