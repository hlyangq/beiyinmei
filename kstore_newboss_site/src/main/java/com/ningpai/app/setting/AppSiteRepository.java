package com.ningpai.app.setting;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ningpai.app.setting.index.Advert;
import com.ningpai.searchplatform.client.ESClientManager;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsRequest;
import org.elasticsearch.action.admin.indices.mapping.delete.DeleteMappingRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.base.Strings;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * APP站点仓库
 * Created by aqlu on 16/3/2.
 */
public class AppSiteRepository {

    private ObjectMapper objectMapper;

    private ESClientManager esClientManager;

    public AppSiteRepository(ESClientManager esClientManager) {
        this.esClientManager = esClientManager;
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }

    public <T> List<T> findAll(String index, String type, Class<T> clazz, Map<String, SortOrder> sortOrderMap) {
        if (!isIndexExist(index)) { // 索引不存在
            initIndex(index);
        }

        SearchRequestBuilder searchRequestBuilder = esClientManager.getClient().prepareSearch(index).setTypes(type)
                .setQuery(QueryBuilders.matchAllQuery());

        return commonQuery(sortOrderMap, searchRequestBuilder, clazz);
    }

    /**
     * 根据主题编码查询document
     */
    public <T> List<T> findByThemeCode(String index, String type, Class<T> clazz, Map<String, SortOrder> sortOrderMap, String themeCode) {
        if (!isIndexExist(index)) { // 索引不存在
            initIndex(index);
        }

        SearchRequestBuilder searchRequestBuilder = esClientManager.getClient().prepareSearch(index).setTypes(type)
                .setQuery(QueryBuilders.matchQuery("themeCode", themeCode));

        return commonQuery(sortOrderMap, searchRequestBuilder, clazz);
    }

    public void deleteAll(String index, String type) {
        if (!isIndexExist(index) || !isTypeExist(index, type)) // 索引或者类型不存在
            return;

        esClientManager.getClient().admin().indices().deleteMapping(new DeleteMappingRequest(index).types(type))
                .actionGet();
    }

    /**
     * 根据id删除 document
     */
    public void deleteById(String index, String type, String id) {
        if (!isIndexExist(index) || !isTypeExist(index, type)) // 索引或者类型不存在
            return;
        esClientManager.getClient().prepareDelete(index, type, id);
    }

    public void deleteByThemeCode(String index, String type, String themeCode) {
        if (!isIndexExist(index) || !isTypeExist(index, type)) // 索引或者类型不存在
            return;
        esClientManager.getClient().prepareDeleteByQuery(index).setTypes(type).
                setQuery(QueryBuilders.matchQuery("themeCode", themeCode)).execute().actionGet();
    }

    public <T extends Document> void save(String index, String type, List<T> docs) throws IOException {
        if (!isIndexExist(index)) { // 索引或类型不存在
            initIndex(index);
        }
        BulkRequestBuilder requestBuilder = esClientManager.getClient().prepareBulk();
        for (T doc : docs) {
            requestBuilder.add(esClientManager.getClient().prepareIndex(index, type)
                    .setSource(objectMapper.writeValueAsString(doc)));
        }
        requestBuilder.execute().actionGet();
    }

    /**
     * 判断索引是否存在
     *
     * @param index
     * @return
     */
    public boolean isIndexExist(String index) {
        return esClientManager.getClient().admin().indices().exists(new IndicesExistsRequest(index)).actionGet().isExists();
    }

    /**
     * 判断类型是否存在
     *
     * @param index
     * @param type
     * @return
     */
    public boolean isTypeExist(String index, String type) {
        return esClientManager.getClient().admin().indices().
                typesExists(new TypesExistsRequest(new String[]{index}, type)).actionGet().isExists();
    }

    /**
     * 创建索引
     *
     * @param index
     */
    public void initIndex(String index) {
        esClientManager.getClient().admin().indices().prepareCreate(index).execute().actionGet();
    }

    /**
     * 抽取公共查询mac
     *
     * @param sortOrderMap         sortOrderMap
     * @param searchRequestBuilder searchRequestBuilder
     * @param clazz                clazz
     * @param <T>                  T
     * @return List<T>
     */
    private <T> List<T> commonQuery(Map<String, SortOrder> sortOrderMap, SearchRequestBuilder searchRequestBuilder, Class<T> clazz) {
        if (sortOrderMap != null) {
            for (Map.Entry<String, SortOrder> sort : sortOrderMap.entrySet()) {
                searchRequestBuilder.addSort(sort.getKey(), sort.getValue());
            }
        }
        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
        SearchHits searchHits = searchResponse.getHits();

        List<T> results = new ArrayList<>();
        for (SearchHit hit : searchHits) {
            if (hit != null) {
                String source = hit.sourceAsString();
                if (!Strings.isNullOrEmpty(source)) {
                    try {
                        T result = objectMapper.readValue(source, clazz);
                        results.add(result);
                    } catch (IOException e) {
                        throw new RuntimeException(
                                "failed to map source [ " + source + "] to class " + Advert.class.getSimpleName(), e);
                    }
                }
            }
        }

        return results;
    }
}
