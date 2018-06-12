package com.ningpai.app.setting.index;


import com.ningpai.app.AppSiteConstants;
import com.ningpai.app.setting.AppSiteRepository;
import com.ningpai.searchplatform.client.ESClientManager;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * 菜单仓库
 * Created by aqlu on 16/3/2.
 */
@Repository
public class MenuRepository extends AppSiteRepository {

    @Autowired
    public MenuRepository(ESClientManager esClientManager) {
        super(esClientManager);
    }

    public List<Menu> findAll() {
        return findAll(AppSiteConstants.ES_INDEX_NAME, AppSiteConstants.ES_TYPE_OF_MENUS, Menu.class, Collections.singletonMap("ordering", SortOrder.ASC));
    }

    public void deleteAll() {
        deleteAll(AppSiteConstants.ES_INDEX_NAME, AppSiteConstants.ES_TYPE_OF_MENUS);
    }

    public void save(List<Menu> menus) throws IOException {
        save(AppSiteConstants.ES_INDEX_NAME, AppSiteConstants.ES_TYPE_OF_MENUS, menus);
    }

    public List<Menu> findByThemeCode(String themeCode) {
        return findByThemeCode(AppSiteConstants.ES_INDEX_NAME, AppSiteConstants.ES_TYPE_OF_MENUS, Menu.class, Collections.singletonMap("ordering", SortOrder.ASC), themeCode);
    }
}