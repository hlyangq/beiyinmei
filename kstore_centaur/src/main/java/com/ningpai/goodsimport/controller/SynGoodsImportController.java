package com.ningpai.goodsimport.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ningpai.category.bean.ReturnObject;
import com.ningpai.goodsimport.service.SynGoodsImportService;

/**
 * e店宝导入商品
 * 
 * @author ggn
 * 
 */
@Controller
public class SynGoodsImportController {

    @Resource(name = "SynGoodsImportService")
    private SynGoodsImportService synGoodsImportService;

    /**
     * ͬ同步商品
     * 
     * @param request
     * @return ReturnObject
     */
    @RequestMapping("syngoodsimport")
    @ResponseBody
    public ReturnObject SynchronousGoodsImport(HttpServletRequest request) {
        // 执行同步商品操作
        try {
            return synGoodsImportService.SynchronousGoodsImport();
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        ReturnObject returnObject = new ReturnObject();
        returnObject.setStatus(0);
        return returnObject;
    }

    /**
     * 同步单个商品
     * 
     * @param goodsNo
     * @return ReturnObject
     */
    @RequestMapping("synupdategoodsimport")
    @ResponseBody
    public ReturnObject SynchronousUpdateGoodsImport(String goodsNo) {
        // 执行同步更新商品的操作
        try {
            return synGoodsImportService.SynchronousUpdateGoodsImport(goodsNo);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        ReturnObject returnObject = new ReturnObject();
        returnObject.setStatus(0);
        return returnObject;
    }

}
