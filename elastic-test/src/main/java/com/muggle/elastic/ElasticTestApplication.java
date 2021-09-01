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
        final RestHighLevelClient restHighLevelClient = new RestHighLevelClient();
        restHighLevelClient.updateByQuery();
        restHighLevelClient.update()
        new UpdateByQueryRequest();
        final Script script = new Script();
        script.toString(
            restHighLevelClient.updateByQuery()
    }

}
