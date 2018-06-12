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
 * 广告仓库
 * Created by aqlu on 16/3/2.
 */
@Repository
public class AdvertRepository extends AppSiteRepository {

    @Autowired
    public AdvertRepository(ESClientManager esClientManager) {
        super(esClientManager);
    }

    public List<Advert> findAll() {
        return findAll(AppSiteConstants.ES_INDEX_NAME, AppSiteConstants.ES_TYPE_OF_ADVERTS, Advert.class, Collections.singletonMap("ordering", SortOrder.ASC));
    }

    public void deleteAll(){
        deleteAll(AppSiteConstants.ES_INDEX_NAME, AppSiteConstants.ES_TYPE_OF_ADVERTS);
    }

    public void save(List<Advert> adverts) throws IOException {
        save(AppSiteConstants.ES_INDEX_NAME, AppSiteConstants.ES_TYPE_OF_ADVERTS, adverts);
    }

    public List<Advert> findByThemeCode(String themeCode){
        return findByThemeCode(AppSiteConstants.ES_INDEX_NAME, AppSiteConstants.ES_TYPE_OF_ADVERTS, Advert.class, Collections.singletonMap("ordering", SortOrder.ASC), themeCode);
    }
}
