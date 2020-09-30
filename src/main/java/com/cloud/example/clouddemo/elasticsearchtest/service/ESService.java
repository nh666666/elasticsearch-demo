package com.cloud.example.clouddemo.elasticsearchtest.service;

import com.alibaba.fastjson.JSON;
import com.cloud.example.clouddemo.common.BusinessException;
import com.cloud.example.clouddemo.common.ResponseObject;
import com.cloud.example.clouddemo.conf.ElasticSearchClientConfig;
import com.cloud.example.clouddemo.elasticsearchtest.domain.EsProduct;
import com.cloud.example.clouddemo.elasticsearchtest.model.Good;
import com.cloud.example.clouddemo.util.JsoupUtils;
import com.github.pagehelper.Page;
import com.google.common.collect.Lists;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author: niehan
 * @Description:
 * @Date:Create：in 2020/9/29 14:30
 */
@Service
public class ESService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ESService.class);

    JsoupUtils jsoupUtils = new JsoupUtils();

    @Autowired
    ESRepository eSRepository;

    @Autowired
    ElasticSearchClientConfig client;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    public ResponseObject<Boolean> importAll(String keywords) {
        List<Good> goodList;
        try {
            goodList = jsoupUtils.getTargetGoods(keywords);
            BulkRequest bulkRequest = new BulkRequest();
            bulkRequest.timeout("10s");
            for(int i=0;i<goodList.size();i++) {
                bulkRequest.add(new IndexRequest("jd_goods").source(JSON.toJSONString(goodList.get(i)), XContentType.JSON));
            }
            BulkResponse bulk = client.restHighLevelClient().bulk(bulkRequest, RequestOptions.DEFAULT);
            return new ResponseObject<>(!bulk.hasFailures());
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(1,"导入失败");
        }
    }

    public ResponseObject<Boolean> deleteIndex(String index) {
        try {
            GetIndexRequest request = new GetIndexRequest(index);
            boolean exists = client.restHighLevelClient().indices().exists(request, RequestOptions.DEFAULT);
            if(exists) {
                DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);
                AcknowledgedResponse delete = client.restHighLevelClient().indices().delete(deleteIndexRequest,RequestOptions.DEFAULT);
                boolean acknowledged = delete.isAcknowledged();
                return new ResponseObject<>(0,"删除索引成功",acknowledged);
            } else {
                throw new BusinessException(1,"此索引不存在");
            }
        } catch (IOException e) {
            throw new BusinessException(1,"删除索引失败");
        }
    }

    public ResponseObject<List<Map<String, Object>>> SearchGoods(String keywords,String index, int pageNum, int pageSize) throws IOException {
        if(pageNum < 0) {
            pageNum = 0;
        }
        SearchRequest searchRequest = new SearchRequest(index);
        //构建搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //使关键词显示高亮
//        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        highlightBuilder.field("name");
//        highlightBuilder.requireFieldMatch(false);
//        highlightBuilder.preTags("<span style='color:red'>");
//        highlightBuilder.postTags("</span>");
//        searchSourceBuilder.highlighter(highlightBuilder);

        searchSourceBuilder.from(pageNum);
        searchSourceBuilder.size(pageSize);
//        TermQueryBuilder queryBuilder = QueryBuilders.termQuery("name",keywords);

//        TermQueryBuilder queryBuilder2 = QueryBuilders.termQuery("name",keywords2);
//        BoolQueryBuilder should = QueryBuilders.boolQuery().should(queryBuilder).should(queryBuilder2);
//        BoolQueryBuilder should = QueryBuilders.boolQuery().must(queryBuilder).must(queryBuilder2);
//        searchSourceBuilder.query(should);

        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(keywords, "brandName");
        searchSourceBuilder.query(multiMatchQueryBuilder);

//        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.restHighLevelClient().search(searchRequest,RequestOptions.DEFAULT);
        List<Map<String,Object>> goodLists = new ArrayList<>();
        for(SearchHit hit: searchResponse.getHits().getHits()) {
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField name = highlightFields.get("name");
            Map<String,Object> sourceApp = hit.getSourceAsMap();
            if(!Objects.isNull(name)) {
                Text[] fragments = name.fragments();
                String title = "";
                for(Text fragment:fragments) {
                    title+=fragment;
                }
                sourceApp.put("name",title);
            }
            goodLists.add(sourceApp);
        }
        System.out.println("总条数====================================" + goodLists.size());
        return new ResponseObject<>(0,"操作成功",goodLists);
    }

    public List<EsProduct> searchProductQuery(String name, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(name, "brandName");
        Iterable<EsProduct> search = eSRepository.search(multiMatchQueryBuilder);
        Iterator<EsProduct> iterator = search.iterator();
        return Lists.newArrayList(iterator);
    }

    public ResponseObject<Page<EsProduct>> searchProduct(String name,String subTitle, String keywords, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        return new ResponseObject<>(eSRepository.findByNameOrSubTitleOrKeywords(name,subTitle,keywords,pageable));

    }

}
