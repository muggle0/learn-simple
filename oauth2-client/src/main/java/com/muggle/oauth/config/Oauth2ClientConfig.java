package com.muggle.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Description
 * Date 2023/12/6
 * Created by muggle
 */
@EnableWebSecurity
public class Oauth2ClientConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http	//表示所有请求都需要验证
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                )
                //  运行请求后会自动跳转到第三方登录界面
                .oauth2Login(Customizer.withDefaults());
        return http.build();
    }

}
