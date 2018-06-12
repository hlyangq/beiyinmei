package com.ningpai.m.findpwd.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ningpai.utils.SecurityUtil;

import org.springframework.stereotype.Service;

import com.ningpai.m.customer.bean.Customer;
import com.ningpai.m.customer.mapper.CustomerMapper;
import com.ningpai.m.customer.vo.CustomerAllInfo;
import com.ningpai.m.findpwd.service.FindPwdService;

/**
 * 忘记密码service实现
 * 
 * @author zhangwenchang
 */
@Service("FindPwdServiceM")
public class FindPwdServiceImpl implements FindPwdService {
    private static final String UTYPE = "uType";
    // spring注解 会员Mapper
    @Resource(name = "customerMapperM")
    private CustomerMapper customerMapper;

    /*
     * 修改密码 (non-Javadoc)
     * 
     * @see com.ningpai.m.findpwd.service.FindPwdService#forGetPwd()
     */
    @Override
    public Object forGetPwd(HttpServletRequest request,HttpServletResponse response, String code,
            String mobile, String newPassword) {
        // 验证码错误
        if (!code
                .equals((int) request.getSession().getAttribute("mcCode") + "")) {
            return 1;
        }
        Map<String, Object> paramMap = null;
        String nameEmp = mobile.trim();
        paramMap = new HashMap<String, Object>();
        if (nameEmp.indexOf("@") != -1) {
            paramMap.put(UTYPE, "email");
        } else if (Pattern.compile("^0?(13|15|17|18|14)[0-9]{9}$")
                .matcher(nameEmp).find()) {
            paramMap.put(UTYPE, "mobile");
        } else {
            paramMap.put(UTYPE, "username");
        }
        paramMap.put("username", mobile);
        Customer customerN = customerMapper
                .selectCustomerByCustNameAndType(paramMap);
        if (customerN == null) {
            // 手机号未注册
            return 2;
        } else {
            CustomerAllInfo allInfo = new CustomerAllInfo();
            allInfo.setInfoMobile((String) request.getSession().getAttribute(
                    "userMobile"));
            // 生成盐值
            String salt = SecurityUtil.getNewPsw();
            // 新密码
            String newpwd = SecurityUtil.getStoreLogpwd(
                    customerN.getUniqueCode(), newPassword, salt);
            // 设置信息
            allInfo.setCustomerPassword(newpwd);
            allInfo.setSaltVal(salt);
            allInfo.setCustomerId(customerN.getCustomerId());
            customerMapper.updatePwdInfo(allInfo);
            // 清除cookie中记录的用户名和密码,购物车
            Cookie cookieName = new Cookie("_kstore_newMobile_username", null);
            Cookie cookiePassword = new Cookie("_kstore_newMobile_password", null);
            // 设置cookie生存周期为-1，删除cookie
            cookieName.setMaxAge(-1);
            // 设置同一服务器内共享cookie
            cookieName.setPath("/");
            cookiePassword.setMaxAge(-1);
            cookiePassword.setPath("/");
            response.addCookie(cookieName);
            response.addCookie(cookiePassword);
            return "/loginm.html";
        }
    }

    /*
     * 验证用户名是否存在 (non-Javadoc)
     * 
     * @see
     * com.ningpai.m.findpwd.service.FindPwdService#checkCustomerExists(javax
     * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
     * java.lang.String)
     */
    @Override
    public int checkCustomerExists(HttpServletRequest request,
            HttpServletResponse response, String username)
            throws UnsupportedEncodingException {
        Map<String, Object> paramMap = null;
        String nameEmp = username.trim();
        paramMap = new HashMap<String, Object>();
        if (nameEmp.indexOf("@") != -1) {
            paramMap.put(UTYPE, "email");
        } else if (Pattern.compile("^0?(13|15|17|18|14)[0-9]{9}$")
                .matcher(nameEmp).find()) {
            paramMap.put(UTYPE, "mobile");
        } else {
            paramMap.put(UTYPE, "username");
        }
        paramMap.put("username", username);
        Customer customerN = customerMapper
                .selectCustomerByNamePwdAndType(paramMap);
        if (customerN != null) {
            // 手机号已存在
            return 1;
        } else {
            // 用户名不存在
            return 2;
        }
    }

}
