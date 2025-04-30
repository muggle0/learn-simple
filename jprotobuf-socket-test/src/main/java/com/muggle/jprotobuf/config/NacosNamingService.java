package com.muggle.jprotobuf.config;

import com.baidu.jprotobuf.pbrpc.client.ha.NamingService;
import com.baidu.jprotobuf.pbrpc.registry.RegisterInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Description
 * Date 2024/8/7
 * Created by muggle
 */
@Component
public class NacosNamingService implements NamingService {
    @Override
    public Map<String, List<RegisterInfo>> list(Set<String> set) throws Exception {
        return null;
    }
}
