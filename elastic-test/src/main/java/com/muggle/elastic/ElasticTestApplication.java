package com.muggle.elastic;

import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ElasticTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticTestApplication.class, args);
        /**
         * final String orgId = userRoleDo.getOrgId();
         *             final String userId = userRoleDo.getUserId();
         *             final String roleId = userRoleDo.getRoleId();
         *             final Long authorityCode = authorityGroup.getAuthorityGroupCode();
         *             final UpdateByQueryRequestBuilder requestBuilder = UpdateByQueryAction.INSTANCE.newRequestBuilder(esClient);
         *             Map<String, Object> params=new HashMap<>();
         *             params.put("authorityCode",authorityCode.toString());
         *             params.put("billTypeCode",authorityGroupMap.get(authorityCode).getBillTypeCode());
         *             final StringBuilder scriptCode = new StringBuilder();
         *             // 更新脚本
         *             scriptCode.append( "List dataAuths = ((List) ctx._source.dataAuth); for (dataAuthItem in dataAuths) " )
         *                 .append("{if (dataAuthItem.billTypeCode=params.billTypeCode){List authGrops = ((List) dataAuthItem.authorityGroup); ")
         *                 .append("authGrops.removeIf(item -> { item.authorityGroupCode == params.authorityCode; })}}");
         *             final Script script = new Script(ScriptType.INLINE, "painless", scriptCode.toString(), params);
         *             // 更新条件
         *             final BoolQueryBuilder must = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("roleId", roleId))
         *                 .must(QueryBuilders.termQuery("userId", userId)).must(QueryBuilders.termQuery("orgId", orgId));
         *             requestBuilder.source("auth").script(script).filter(must).abortOnVersionConflict(false).get();
         */
    }

}
