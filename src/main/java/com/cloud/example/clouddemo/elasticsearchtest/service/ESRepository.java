package com.cloud.example.clouddemo.elasticsearchtest.service;

import com.cloud.example.clouddemo.elasticsearchtest.domain.EsProduct;
import com.github.pagehelper.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * @Author: niehan
 * @Description:
 * @Date:Create：in 2020/9/30 14:59
 */
public interface ESRepository extends ElasticsearchRepository<EsProduct, Long> {

    /**
     *
     * @param name
     * @param subTitle
     * @param keywords
     * @param pageable
     * @return
     */
    Page<EsProduct> findByNameOrSubTitleOrKeywords(String name,String subTitle, String keywords, Pageable pageable);

}
