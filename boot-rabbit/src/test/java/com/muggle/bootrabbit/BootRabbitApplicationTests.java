package com.muggle.bootrabbit;

import org.apache.commons.text.StrSubstitutor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BootRabbitApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void subString(){
        HashMap<String, String> map = new HashMap<>();
        map.put("a","b");

        StrSubstitutor strSubstitutor = new StrSubstitutor(map);
        String replace = strSubstitutor.replace("${a} is hahahha");
        System.out.println(replace);

    }

}
