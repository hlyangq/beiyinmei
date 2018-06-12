package com.ningpai.app.setting.index;

import com.ningpai.app.AppSiteConstants;
import com.ningpai.util.MyLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 首页控制器
 * Created by aqlu on 15/12/6.
 */
@Controller
public class IndexController {
    private static final MyLogger LOGGER = new MyLogger(IndexController.class);

    private static final String TEMPLATE_OF_RUSH_GROUP_BUY = "RushGroupBuy";

    @Autowired
    private FloorRepository floorRepository;

    @Autowired
    private SliderRepository sliderRepository;

    @Autowired
    private AdvertRepository advertRepository;

    @Autowired
    private MenuRepository menuRepository;

    /**
     * 查看移动app可视化配置首页列表
     * @return ModelAndView
     */
    @RequestMapping("/queryAppHomePage")
    public ModelAndView queryAppHomePage() {
        ModelAndView mav = new ModelAndView();
        // 获取该商家未启用的首页列表
        mav.setViewName("/jsp/appsite/app_home_page_list");
        return mav;
    }

    /**
     *  查看主题首页列表
     * @return ModelAndView
     */
    @RequestMapping("/queryThemeHomePage")
    public ModelAndView queryThemeHomePage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/jsp/theme/theme_home_page_list");
        return modelAndView;
    }

    /**
     * 首页
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/setAppHomePage", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView index(@RequestParam(required = false) Long districtId) throws IOException {
        ModelAndView mav = new ModelAndView();

        List<Floor> floors = null;
        try {
            floors = floorRepository.findByThemeCode("home");
        } catch (Exception e) {
            LOGGER.info("获取楼层信息失败, msg:"+ e.getMessage());
            floors = Collections.emptyList();
        }
        List<Slider> sliders = null;
        try {
            sliders = sliderRepository.findAll();
        } catch (Exception e) {
            LOGGER.info("获取轮播信息失败, msg:"+ e.getMessage());
            sliders = Collections.emptyList();
        }
        List<Advert> adverts = null;
        try {
            adverts = advertRepository.findByThemeCode("home");
        } catch (Exception e) {
            LOGGER.info("获取广告信息失败, msg:"+ e.getMessage());
            adverts = Collections.emptyList();
        }

        IndexResponse indexResponse = new IndexResponse(sliders, null, adverts, floors);
        mav.setViewName("jsp/appsite/set_app_home_page");
        mav.addObject("data", indexResponse);

        return mav;
    }

    /**
     * 保存首页设置
     */
    @RequestMapping(value = "/saveAppIndex", method = RequestMethod.POST)
    @ResponseBody
    public String saveIndex(@RequestBody IndexSaveForm indexSaveForm) throws IOException {
        List<Slider> sliders = indexSaveForm.getSliders();
        List<Advert> adverts = indexSaveForm.getAdverts();
        List<Floor> floors = indexSaveForm.getFloors();

        if(null !=sliders && !sliders.isEmpty()) {
            sliderRepository.deleteAll();
            sliderRepository.save(sliders);
        }

        if(null !=adverts && !adverts.isEmpty()) {
            advertRepository.deleteByThemeCode(AppSiteConstants.ES_INDEX_NAME, AppSiteConstants.ES_TYPE_OF_ADVERTS, "home");
            advertRepository.save(adverts);
        }

        if(null != floors && !floors.isEmpty()) {

            List<Floor> list = new ArrayList<>();
            for(Floor floor: floors) {
                if (TEMPLATE_OF_RUSH_GROUP_BUY.equals(floor.getTemplate())) {
                    //抢购与团购楼层处理，删除楼层里面的数据
                    floor.setAdverts(null);
                }
                list.add(floor);
            }
            floorRepository.deleteByThemeCode(AppSiteConstants.ES_INDEX_NAME, AppSiteConstants.ES_TYPE_OF_FLOORS, "home");
            floorRepository.save(list);
        }

        return "success";
    }

    /**
     * 主题页
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/setThemeHomePage", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView themeIndex(@RequestParam String themeCode) throws IOException {
        ModelAndView mav = new ModelAndView();

        List<Floor> floors = null;
        try {
            floors = floorRepository.findByThemeCode(themeCode);
        } catch (Exception e) {
            LOGGER.info("获取楼层信息失败, msg:"+ e.getMessage());
            floors = Collections.emptyList();
        }
        List<Advert> adverts = null;
        try {
            adverts = advertRepository.findByThemeCode(themeCode);
        } catch (Exception e) {
            LOGGER.info("获取广告信息失败, msg:"+ e.getMessage());
            adverts = Collections.emptyList();
        }
        List<Menu> menus = null;
        try {
            menus = menuRepository.findByThemeCode(themeCode);
        } catch (Exception e) {
            LOGGER.info("获取menu失败, msg:"+ e.getMessage());
            menus = Collections.emptyList();
        }
        ThemeResponse indexResponse;
        if (CollectionUtils.isEmpty(adverts)) {
            indexResponse = new ThemeResponse(null, floors);
        }else {
            indexResponse = new ThemeResponse(adverts.get(0) ,floors);
        }

        mav.setViewName("jsp/theme/set_theme_home_page");
        mav.addObject("data", indexResponse);

        return mav;
    }

    /**
     * 保存主题页设置
     */
    @RequestMapping(value = "/saveThemeIndex", method = RequestMethod.POST)
    @ResponseBody
    public String saveIndex(@RequestBody ThemeSaveForm saveForm) throws IOException {
        Advert advert = saveForm.getAdvert();
        List<Floor> floors = saveForm.getFloors();

        if(null != advert) {
            advert.setThemeCode("theme-1");
            advertRepository.deleteByThemeCode(AppSiteConstants.ES_INDEX_NAME, AppSiteConstants.ES_TYPE_OF_ADVERTS, advert.getThemeCode());
            List<Advert> adverts = new ArrayList<>();
            adverts.add(advert);
            advertRepository.save(adverts);
        }

        if(floors != null && !floors.isEmpty()) {
            floorRepository.deleteByThemeCode(AppSiteConstants.ES_INDEX_NAME, AppSiteConstants.ES_TYPE_OF_FLOORS, floors.get(0).getThemeCode());
            floorRepository.save(floors);
        }

        return "success";
    }
}
