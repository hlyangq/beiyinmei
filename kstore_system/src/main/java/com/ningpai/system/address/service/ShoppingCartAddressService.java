package com.ningpai.system.address.service;

import com.ningpai.system.address.bean.ShoppingCartWareUtil;
import com.ningpai.system.service.DefaultAddressService;
import com.ningpai.system.service.DistrictService;
import com.ningpai.system.util.AddressUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by youzipi on 16/9/8.
 */
@Service
public class ShoppingCartAddressService {

    private static final String DISTINCTID = "distinctId";

    @Autowired
    private DefaultAddressService addressService;

    /**
     * 区县Service
     */
    @Autowired
    private DistrictService districtService;


    /**
     * 查询省名称和地区id
     *
     * @param request
     * @return
     */
    public ShoppingCartWareUtil loadAreaFromRequest(HttpServletRequest request) {
        Long dId = null;
        // 获取地区id
        if (request.getSession().getAttribute(DISTINCTID) != null && !"".equals(request.getSession().getAttribute(DISTINCTID))) {
            String obj = request.getSession().getAttribute(DISTINCTID).toString();
            dId = Long.parseLong(obj);
        }
        ShoppingCartWareUtil wareUtil = new ShoppingCartWareUtil();

        // 如果地区id为空，则设置为建邺区
        if (dId == null) {
            dId = addressService.getDefaultIdService();
            if (dId == null) {
                dId = 1103L;
                wareUtil.setProvinceName("江苏");
                wareUtil.setCityName("南京");
                wareUtil.setDistinctName("建邺区");
            } else {
                AddressUtil addressUtil = districtService.queryAddressNameByDistrictId(dId);
                wareUtil.setProvinceName(addressUtil.getProvinceName());
                wareUtil.setCityName(addressUtil.getCityName());
                wareUtil.setDistinctName(addressUtil.getDistrictName());
            }
            wareUtil.setDistrictId(dId);
        } else {
            wareUtil.setDistrictId(dId);
            // 省
            String provinceName = request.getSession().getAttribute("chProvince").toString();
            wareUtil.setProvinceName(provinceName);

            // 市
            String chCity = request.getSession().getAttribute("chCity").toString();
            wareUtil.setCityName(chCity);
            // 地址
            String chDistinct = request.getSession().getAttribute("chDistinct").toString();
            wareUtil.setDistinctName(chDistinct);
        }

        // 区id
        return wareUtil;
    }
}
