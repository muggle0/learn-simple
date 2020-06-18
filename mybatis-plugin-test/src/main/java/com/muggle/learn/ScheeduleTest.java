package com.muggle.learn;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.muggle.learn.domain.UserAuthority;
import com.muggle.learn.mapper.UserAuthorityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: muggle
 * @Date: 2020/4/29
 **/
@Component
public class ScheeduleTest {

    @Autowired
    UserAuthorityMapper userAuthorityMapper;
    @Scheduled(cron = "0/5 * * * * ?")
    public void test(){
        UserAuthority userAuthority = userAuthorityMapper.selectByPrimaryKey(1L);
        PageHelper.startPage(1,2);
        Page<UserAuthority> page =userAuthorityMapper.find();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
