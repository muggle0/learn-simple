package com.muggle.sms.webservice;


import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * Description
 * Date 2021/11/11
 * Created by muggle
 */
//@Configuration
public class CxfConfig {

    @Bean
    public ServletRegistrationBean disServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new CXFServlet(), "/webService/*");
        return servletRegistrationBean;
    }
    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }
    @Bean
    public Endpoint endpoint(@Autowired SpringBus springBus) {
        EndpointImpl endpoint = new EndpointImpl(springBus, new TestServiceImpl());
        endpoint.publish("/TestService");
        return endpoint;
    }

}
