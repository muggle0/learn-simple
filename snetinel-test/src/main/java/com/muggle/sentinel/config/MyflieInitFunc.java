package com.muggle.sentinel.config;

import com.alibaba.csp.sentinel.datasource.FileRefreshableDataSource;
import com.alibaba.csp.sentinel.datasource.FileWritableDataSource;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.WritableDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.transport.util.WritableDataSourceRegistry;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyflieInitFunc implements InitFunc {
    @Override
    public void init() throws Exception {
        URL resource = MyflieInitFunc.class.getClassLoader().getResource("");
        File file = new File(resource.getPath()+"/config/flow.json");
        File fileParent = file.getParentFile();
        if(!fileParent.exists()){
            fileParent.mkdirs();
        }
        if (!file.exists()){
            file.createNewFile();
        }
        ReadableDataSource<String, List<FlowRule>> flowReadDataSource = new FileRefreshableDataSource<>(
            resource.getPath()+"/config/flow.json",
            source -> JSON.parseObject(
                source,
                new TypeReference<List<FlowRule>>() {
                }
            ));
        FlowRuleManager.register2Property(flowReadDataSource.getProperty());
        WritableDataSource<List<FlowRule>> flowWriteDataSource = new FileWritableDataSource<>(
            resource.getPath()+"/config/flow.json",
            t->JSON.toJSONString(t)
        );
        WritableDataSourceRegistry.registerFlowDataSource(flowWriteDataSource);
    }


/*
*
*
https://cloud.tencent.com/developer/article/1340592

https://www.itmuch.com/spring-cloud-alibaba/sentinel-rules-persistence-pull-mode/
* */

}