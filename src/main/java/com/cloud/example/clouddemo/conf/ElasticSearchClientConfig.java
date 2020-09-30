package com.cloud.example.clouddemo.conf;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Configuration;

import org.elasticsearch.client.RestHighLevelClient;

/**
 * @Author: niehan
 * @Description:
 * @Date:Create：in 2020/9/29 10:42
 */
@Configuration
public class ElasticSearchClientConfig {

    public RestHighLevelClient restHighLevelClient(){
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1",9200,"http")
                )
        );
        return client;
    }
}
