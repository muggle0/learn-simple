package com.muggle.sentinel.config;

import com.alibaba.csp.sentinel.datasource.AbstractDataSource;
import com.alibaba.csp.sentinel.datasource.Converter;

/**
 * Description
 * Date 2021/7/10
 * Created by muggle
 */
public class RedisDataSource extends AbstractDataSource {

    public RedisDataSource(Converter parser) {
        super(parser);
    }

    @Override
    public Object readSource() throws Exception {
        return null;
    }

    @Override
    public void close() throws Exception {

    }
}
