package com.cloud.example.clouddemo.elasticsearchtest.controller;

import com.cloud.example.clouddemo.common.BusinessException;
import com.cloud.example.clouddemo.common.ResponseObject;
import com.cloud.example.clouddemo.elasticsearchtest.domain.EsProduct;
import com.cloud.example.clouddemo.elasticsearchtest.service.ESService;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author: niehan
 * @Description:
 * @Date:Create：in 2020/5/13 16:30
 */
@RestController
@RequestMapping("/esTest")
@Api(value = "Elasticsearch 控制器", tags = "API-搜索引擎控制器")
public class ESController {

    @Autowired
    ESService eSService;

    @ApiOperation(value = "导入所有数据库中商品到ES")
    @GetMapping(value = "/importAll")
    @ResponseBody
    public ResponseObject<Boolean> importAllList(String keywords) {
        return eSService.importAll(keywords);
    }

    @ApiOperation(value = "删除索引")
    @GetMapping(value = "/deleteIndex")
    @ResponseBody
    public ResponseObject<Boolean> deleteIndex(String index) {
        return eSService.deleteIndex(index);
    }

    @ApiOperation(value = "搜索es的数据")
    @GetMapping(value = "/search/simple")
    @ResponseBody
    public ResponseObject<List<Map<String, Object>>> SearchGoods(String keywords,String index,int pageNum,int pageSize) {
        try {
            return eSService.SearchGoods(keywords,index,pageNum,pageSize);
        } catch (IOException e) {
            throw  new BusinessException(1,"搜索数据失败");
        }
    }

    @ApiOperation(value = "query形式直接搜索product信息")
    @GetMapping(value = "/searchProductQuery")
    @ResponseBody
    public List<EsProduct> searchProductQuery(String name, int pageNum, int pageSize) {
        return eSService.searchProductQuery(name, pageNum, pageSize);
    }

    @ApiOperation(value = "条件搜索product信息")
    @GetMapping(value = "/searchProduct")
    @ResponseBody
    public ResponseObject<Page<EsProduct>> searchProduct(String name, String subTitle, String keywords, int pageNum, int pageSize) {
        return eSService.searchProduct(name,subTitle,keywords, pageNum, pageSize);
    }

    @PostMapping(value = "/testPost")
    @ApiOperation(value = "测试")
    public String addDrone(@RequestBody String test) {
        System.out.println(test);
        return test;
    }
}
