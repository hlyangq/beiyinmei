package com.ningpai.system.controller;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.ningpai.system.bean.OnLineServiceItem;
import com.ningpai.system.bean.ShopKuaiShang;
import com.ningpai.system.service.IOnLineServiceItemBiz;
import com.ningpai.system.service.ShopKuaiShangService;
import com.ningpai.util.JsonUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import com.ningpai.logger.util.OperaLogUtil;
import com.ningpai.system.bean.OnLineService;
import com.ningpai.system.service.IOnLineServiceBiz;
import com.ningpai.util.MyLogger;
import com.ningpai.util.PageBean;

/**
 * 在线客服Controller
 *
 * @author NINGPAI_xiaomm
 * @version V1.0
 * @Description:
 * @since 2014-03-27 17:40:56
 */
@Controller("onLineServiceController")
public class OnLineServiceController extends BaseController {

    /**
     * 在线客服业务类
     */
    @Resource(name = "onLineServiceBizImpl")
    private IOnLineServiceBiz onLineServiceBizImpl;
    /**
     * spring注解
     */
    @Resource(name = "ShopKuaiShangService")
    private ShopKuaiShangService shopKuaiShangService;

    /**
     * 添加在线客服JSP页面
     */
    private static final String ADD_ONLINESERVICE_JSP = "jsp/system/online/onlineservice_add";

    /**
     * 展示qq客服列表
     */
    private static final String ONLINESERVICE_LIST_JSP = "member/service_online";

    /**
     * 进入qq客服聊天页
     */
    private static final String ONLINESERVICE_QQ = "member/onlineService_qq";

    /**
     * 对象删除标识
     */
    private static final String DELETESTATUS = "deleteStatus";

    /**
     * QQ客服在线标志 0：在线
     */
    private static final String ONLINEINDEX = "onlineIndex";

    /**
     * 常量MSG
     */
    private static final String MSG = "msg";
    /**
     * 常量LOGINUSERID
     */
    private static final String LOGINUSERID = "loginUserId";
    /**
     * 常量ONLINESERVICE
     */
    private static final String ONLINESERVICE = "onLineService";
    /**
     * 常量QQLIST
     */
    private static final String QQLIST = "qqList";
    /**
     * 常量QQ
     */
    private static final String QQ = "qq";
    /**
     * 常量KSTLIST
     */
    private static final String KSTLIST = "kstList";

    /**
     * 用户名称
     */
    public static final String NAME = "name";

    /**
     * "operaPath"
     */
    public static final String OPERAPATH = "operaPath";

    /**
     * 记录日志对象
     */
    private static final MyLogger LOGGER = new MyLogger(OnLineServiceController.class);

    /** 在线客服项业务类 */
    @Resource(name = "onLineServiceItemBizImpl")
    private IOnLineServiceItemBiz onLineServiceItemBizImpl;

    /**
     * private static final String ALL_NUMBERS = "0123456789";
     */
    /**
     * 保存在线客服
     *
     * @param onLineService 货币设置对象
     * @param request       请求对象
     * @return 视图对象
     */
    @RequestMapping("/addOnLineService")
    public ModelAndView addOnLineService(@Valid final OnLineService onLineService,final String[] serviceItems, String onlineSwitchKST,BindingResult bindingResult,final HttpServletRequest request, final HttpServletResponse response)
            throws UnsupportedEncodingException {
        if (bindingResult.hasErrors()) {
            return new ModelAndView(new RedirectView("readOnLineService.htm?deleteStatus=0"));
        }
        boolean boo=true;
        ModelAndView mav = new ModelAndView();
        String msg = "";
        try {
            List<OnLineServiceItem> list = new ArrayList<>();
            //启用状态下需要进行数据的判断
            if("Y".equals(onLineService.getOnlineIndex())){
                Boolean flag = true;
                //启用状态下客服数量必须>0
                if(serviceItems.length > 0){
                    //判断数据格式是否正确
                    for(String item:serviceItems){
                        OnLineServiceItem onLineServiceItem = new OnLineServiceItem();
                        String name = item.substring(item.indexOf("-")+1);
                        String number = item.substring(0,item.indexOf("-"));
                        //qq号qq昵称的校验
                        if(number != "" && name != ""){
                            //判断qq格式、名称是否有特殊字符
                            if(Pattern.matches("^[1-9]\\d{4,9}$",number)){
                                if(Pattern.matches("^[\\u4e00-\\u9fa5a-zA-Z\\d]{1,6}$",name)){
                                    onLineServiceItem.setName(name);
                                    onLineServiceItem.setContactNumber(number);
                                    list.add(onLineServiceItem);
                                }else{
                                    flag =  false && flag;
                                }
                            }else{
                                flag =  false && flag;
                            }
                        }else{
                            flag =  false && flag;
                        }
                    }
                    if(flag){
                        onLineServiceItemBizImpl.deleteAll();
                        onLineServiceItemBizImpl.addItems(list);
                    }else{
                        mav.addObject(MSG, "保存在线客服失败！");
                    }
                }
            }else{
                if(serviceItems !=null && serviceItems.length > 0){
                    for(String item:serviceItems) {
                        OnLineServiceItem onLineServiceItem = new OnLineServiceItem();
                        onLineServiceItem.setName(item.substring(item.indexOf("-") + 1));
                        onLineServiceItem.setContactNumber(item.substring(0, item.indexOf("-")));
                        list.add(onLineServiceItem);
                    }
                    onLineServiceItemBizImpl.deleteAll();
                    onLineServiceItemBizImpl.addItems(list);
                }else{
                    onLineServiceItemBizImpl.deleteAll();
                }

            }
            // 如果在线服务id为0，则保存在线服务
            if (onLineService.getOnLineServiceId() == 0) {
                onLineService.setInsertDate(new Date());
                onLineService.setInsertId(((Long) request.getSession().getAttribute(LOGINUSERID)).intValue());
                onLineService.setDeleteStatus(0);
                // 添加在线客服
                int flag = onLineServiceBizImpl.saveOnLineService(onLineService);
                if (flag == 0) {
                    mav.addObject(MSG, "保存在线客服失败！");
                }
                // 更新
            } else {
                onLineService.setModifyDate(new Date());
                onLineService.setModifyId(((Long) request.getSession().getAttribute(LOGINUSERID)).intValue());
                // 修改在线客服
                String titleModel = "^[\\u4e00-\\u9fa5a-zA-Z\\d]{1,4}$";
                if("0".equals(onLineService.getOnlineIndex())){
                    if(!Pattern.matches(titleModel,onLineService.getTitle()) || onLineService.getEffectiveTerminal().length()<=0 ){
                        boo= false && boo;
                        mav.addObject(MSG, "客服标题不符或者生效终端未选择！");
                    }
                }
                if(boo){
                    if(onLineService.getEffectiveTerminal()==null){
                        onLineService.setEffectiveTerminal("");
                    }
                    if (onLineServiceBizImpl.updateOnLineService(onLineService) == 0) {
                        mav.addObject(MSG, "更新在线客服失败！");
                    } else {
                        ShopKuaiShang kuaiShangTong = new ShopKuaiShang();
                        kuaiShangTong.setIsuseing("1");
                        String shangId = request.getParameter("shangId").toString();
                        kuaiShangTong.setShangId(Long.valueOf(Integer.parseInt(shangId)));
                        if(onLineService.getOnlineIndex().equals("Y")){
                            shopKuaiShangService.updateKuaiShangByPrimaryKey(kuaiShangTong);
                        }
                        String customerName = (String) request.getSession().getAttribute(NAME);
                        OperaLogUtil.addOperaLog(request, customerName, "修改在线客服", request.getSession().getAttribute(OPERAPATH) + ",用户名:" + customerName);
                    }
                }
            }

        } catch (Exception e) {
            LOGGER.error("保存在线客服错误：=>", e);
            mav.addObject(MSG, "保存在线客服失败！");
        }
        // 返回在线客服列表
        mav.addObject(MSG, msg);
        mav.setView(new RedirectView("readOnLineService.htm?deleteStatus=0"));
        return mav;
    }

    /**
     * 查看在线客服
     *
     * @param deleteStatus 对象删除标识
     * @return 视图对象
     */
    @RequestMapping("/readOnLineService")
    public ModelAndView readOnLineService(@RequestParam(value = DELETESTATUS, required = false, defaultValue = "0") final Integer deleteStatus, final HttpServletRequest request) {

        ModelAndView mav = new ModelAndView();
        mav.addObject(DELETESTATUS, deleteStatus);
        // 判断查询在线客服id是否为0
        mav.setViewName(ADD_ONLINESERVICE_JSP);
        // 查询当前系统中默认的在线客服配置
        Map<String, Object> pa = new HashMap<String, Object>(2);
        pa.put(DELETESTATUS, deleteStatus);
        PageBean pg = new PageBean();
        pg.setPageSize(1);
        List<Object> list = ((PageBean) onLineServiceBizImpl.getOnLineServiceByField(pa, pg)).getList();
        mav.addObject(ONLINESERVICE, list != null && !list.isEmpty() ? list.get(0) : new OnLineService());
        mav.addObject(KSTLIST, shopKuaiShangService.selectInitone());
        mav.addObject(MSG, request.getAttribute(MSG));
        mav.addObject(MSG, request.getParameter(MSG));
        return mav;
    }

    /**
     * 查看在线客服
     *
     * @return 视图对象
     */
    @RequestMapping("/readQQ")
    @ResponseBody
    public Object readQQ() {
        // 查询当前系统中默认的在线客服配置
        Map<String, Object> pa = new HashMap<String, Object>(2);
        pa.put(DELETESTATUS, 0);
        PageBean pg = new PageBean();
        pg.setPageSize(1);
        List<Object> list = ((PageBean) onLineServiceBizImpl.getOnLineServiceByField(pa, pg)).getList();
        return list.get(0);
    }

    /**
     * 查看在线客服
     *
     * @return 视图对象
     */
    @RequestMapping("/readKst")
    @ResponseBody
    public ShopKuaiShang readKst() {
        // 查询当前系统中默认的在线客服配置
        ShopKuaiShang shopKuaiShang= shopKuaiShangService.selectInitone();
        return shopKuaiShang;
    }

    /**
     * 移动端查询qq客服
     * @return
     */
    @RequestMapping("/checkOnLineService")
    @ResponseBody
    public Map<String, Object> checkOnLineService() {
        // qq
        Map<String, Object> pa = new HashMap<String, Object>(2);
        Map<String, Object> result = new HashMap<String, Object>();
        pa.put(DELETESTATUS, "0");
        pa.put(ONLINEINDEX, "Y");
        PageBean pg = new PageBean();
        pg.setPageSize(1);
        List<Object> list = ((PageBean) onLineServiceBizImpl.getOnLineServiceByField(pa, pg)).getList();
        //kst
        ShopKuaiShang kst = shopKuaiShangService.selectInitone();
        if(!list.isEmpty() && ((OnLineService)list.get(0)).getItemList().size()==1 && ((OnLineService)list.get(0)).getEffectiveTerminal().contains("3")){
            result.put("url",((OnLineService)list.get(0)).getItemList().get(0).getContactNumber());
            result.put("type","QQ");
        }else if(!list.isEmpty() && ((OnLineService)list.get(0)).getItemList().size()>1 && ((OnLineService)list.get(0)).getEffectiveTerminal().contains("3")){
            result.put("url","readOnLineServiceQQList.htm");
            result.put("type","QQList");
        }else if(kst != null && kst.getIsuseing().equals("0") && kst.getKstEffectiveTerminal().contains("3")){
            result.put("url","goToKst.htm");
            result.put("type","KST");
        }else{
            result.put("url","");
            result.put("type","NO");
        }
        return result;
    }
    /**
     * 移动端查询qq客服
     * @return
     */
    @RequestMapping("/readOnLineServiceQQList")
    public ModelAndView readOnLineServiceQQList() {
        ModelAndView mav = new ModelAndView();
        // 判断查询在线客服id是否为0
        Map<String, Object> pa = new HashMap<String, Object>(2);
        pa.put(DELETESTATUS, "0");
        PageBean pg = new PageBean();
        pg.setPageSize(1);
        try{
            List<Object> list = ((PageBean) onLineServiceBizImpl.getOnLineServiceByField(pa, pg)).getList();
            if(((OnLineService)list.get(0)).getItemList().size()>1) {
                mav.addObject(QQLIST, ((OnLineService) list.get(0)).getItemList());
                mav.setViewName(ONLINESERVICE_LIST_JSP);
            }else{
                mav.addObject(MSG, "移动端查询qq客服列表失败！");
            }
        }catch(Exception e){
            LOGGER.error("移动端查询qq客服列表错误：=>", e);
            mav.addObject(MSG, "移动端查询qq客服列表失败！");
        }
        return mav;
    }

    /**
     * 快商通
     *
     * @return
     */
    @RequestMapping("/shopkuaishang")
    public ModelAndView shopkuaishang() {

        return new ModelAndView("jsp/system/kuaishang/kuaishang_list", "list", shopKuaiShangService.selectInitone());
    }

    /**
     * 打开,关闭快商通.
     *
     * @param shopKuaiShang
     * @return
     */
    @RequestMapping("/updateKuaiShangByPrimaryKey")
    public ModelAndView updateKuaiShangByPrimaryKey(ShopKuaiShang shopKuaiShang) {
        shopKuaiShangService.updateKuaiShangByPrimaryKey(shopKuaiShang);
        return new ModelAndView(new RedirectView("shopkuaishang.htm"));
    }

    /**
     * 申请快商通
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/applyKuaiShang")
    public String applyKuaiShang(ShopKuaiShang shopKuaiShang) {
        String url = null;
        url = "http://shop.kuaishang.cn/api/thirdregist.php?";
        String returnString = "";
        PostMethod postMethod = new PostMethod(url);
        // 设置post请求头的编码格式
        postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        // 传递数据
        NameValuePair[] param = {new NameValuePair("shangId", shopKuaiShang.getShangId().toString()), new NameValuePair("username", shopKuaiShang.getShangLoginName()),
                new NameValuePair("password", shopKuaiShang.getPassword()), new NameValuePair("company", shopKuaiShang.getCompanyName()),
                new NameValuePair("weburl", shopKuaiShang.getCompanyUrl()), new NameValuePair("industryCategory", shopKuaiShang.getTrade()),
                new NameValuePair("linkman", shopKuaiShang.getLinkman()), new NameValuePair("phone", shopKuaiShang.getTelephone()),
                new NameValuePair("email", shopKuaiShang.getEmail()), new NameValuePair("mobile", shopKuaiShang.getMobilephone()), new NameValuePair("keytype", "qianmi"),
                new NameValuePair("keynumber", shopKuaiShang.getShangLoginName())};
        postMethod.setRequestBody(param);
        HttpClient client = new HttpClient();

        try {
            client.executeMethod(postMethod);
            returnString = postMethod.getResponseBodyAsString();
            // 处理带有bom头的文本
            if (returnString.startsWith("\uFEFF")) {
                returnString = returnString.substring(1);
            }
            // 处理接口返回值得到合格的json数据
            returnString = returnString.replaceAll("'", "\\\\'");
        } catch (IOException e) {
            LOGGER.error("申请快商通失败：", e);
        }

        Map<String, Object> result = JsonUtil.getMapFromJson(returnString);
        // 注册成功
        if ((Boolean) result.get("success")) {
            shopKuaiShang.setShangLongId(result.get("compId").toString());
            shopKuaiShang.setOperation(result.get("code").toString());
            shopKuaiShangService.updateKuaiShangByPrimaryKey(shopKuaiShang);
            return "1";
        } else {
            return result.get("msg").toString();
        }
    }

    /**
     * 更新快商通
     *
     * @return
     */
    @RequestMapping("/updateKuaiShang")
    public ModelAndView  updateKuaiShang(MultipartHttpServletRequest multipartRequest){
        boolean flag=true;
        ModelAndView mav = new ModelAndView();
        ShopKuaiShang kuaishangTong=new ShopKuaiShang();
        kuaishangTong.setOperation(multipartRequest.getParameter("operation").toString());
        kuaishangTong.setShangId(Long.valueOf(multipartRequest.getParameter("shangId").toString()));
        kuaishangTong.setAppOperation(multipartRequest.getParameter("appOperation").toString());
        kuaishangTong.setKstEffectiveTerminal(multipartRequest.getParameter("kstSite").toString());
        kuaishangTong.setIsuseing(multipartRequest.getParameter("isuseing").toString());
        String onlineSwitchQQ =multipartRequest.getParameter("onlineSwitchQQ").toString();
        int onLineServiceId =Integer.parseInt(multipartRequest.getParameter("onLineServiceId"));
        //快商通启用时
        if("0".equals(kuaishangTong.getIsuseing())){
            //<script>脚本语言转义以及防止脚本注入
            String operationStr=kuaishangTong.getOperation();
            if(kuaishangTong.getKstEffectiveTerminal().contains("1")){
                if(! operationStr.isEmpty()){
                    String operationModel = "^<script type=\"text\\/javascript\" src=\"http:\\/\\/z1-pcok6\\.kuaishangkf\\.cn\\/bs\\/ks\\.j\\?cI=\\d+&fI=\\d+\" charset=\"utf-8\"><\\/script>$";
                    Pattern.matches(operationModel,operationStr);
                    if(!Pattern.matches(operationModel,operationStr)) {
                        flag=flag && false;
                        mav.addObject(MSG, "PC生效代码不正确！");
                    }
                }else{
                    flag=flag && false;
                    mav.addObject(MSG, "PC生效代码不能为空！");
                }
            }
            String appOperationStr=kuaishangTong.getAppOperation();
            if(kuaishangTong.getKstEffectiveTerminal().contains("2") || kuaishangTong.getKstEffectiveTerminal().contains("3")) {
                if (!appOperationStr.isEmpty()) {
                    String appOperationModel = "^http:\\/\\/z1-pcok6\\.kuaishangkf\\.cn\\/bs\\/im\\.htm\\?cas=\\d+___\\d+&fi=\\d+&ism=1$";
                    if (!Pattern.matches(appOperationModel, appOperationStr)) {
                        flag = flag && false;
                        mav.addObject(MSG, "APP生效代码不正确！");
                    }
                } else {
                    flag = flag && false;
                    mav.addObject(MSG, "APP生效代码不能为空！");
                }
            }
        }
        OnLineService onlineService = new OnLineService();
        if(flag){
            try {
                if(kuaishangTong.getKstEffectiveTerminal()==null || kuaishangTong.getKstEffectiveTerminal().trim().equals("")){
                    kuaishangTong.setKstEffectiveTerminal(" ");
                }
                int count=shopKuaiShangService.updateKuaiShangByPrimaryKey(kuaishangTong);
                //如果前台判断启用KST则需要禁用QQ
                if(multipartRequest.getParameter("isuseing").toString().equals("0")){
                    onlineService.setOnlineIndex("N");
                    onlineService.setOnLineServiceId(onLineServiceId);
                }
                onLineServiceBizImpl.updateOnLineService(onlineService);
                if (count<=0) {
                    mav.addObject(MSG, "更新快商通客服失败！");
                }
            } catch (Exception e) {
                LOGGER.error("更新快商通失败：", e);
                mav.addObject(MSG, "更新快商通失败！");
            }
        }
        // 返回在线客服列表
        mav.setView(new RedirectView("readOnLineService.htm?deleteStatus=0"));
        return mav;
    }
}