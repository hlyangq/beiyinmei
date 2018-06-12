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
 * 轮播图仓库
 * Created by aqlu on 16/3/2.
 */
@Repository
public class SliderRepository extends AppSiteRepository {

    @Autowired
    public SliderRepository(ESClientManager esClientManager) {
        super(esClientManager);
    }

    public List<Slider> findAll() {
        return findAll(AppSiteConstants.ES_INDEX_NAME, AppSiteConstants.ES_TYPE_OF_SLIDERS, Slider.class, Collections.singletonMap("ordering", SortOrder.ASC));
    }

    public void deleteAll() {
        deleteAll(AppSiteConstants.ES_INDEX_NAME, AppSiteConstants.ES_TYPE_OF_SLIDERS);
    }

    public void save(List<Slider> sliders) throws IOException {
        save(AppSiteConstants.ES_INDEX_NAME, AppSiteConstants.ES_TYPE_OF_SLIDERS, sliders);
    }
}