package com.iqmsoft;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.component.restlet.RestletComponent;
import org.apache.camel.spring.boot.FatJarRouter;
import org.restlet.Component;
import org.restlet.ext.spring.SpringServerServlet;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MySpringBootRouter extends FatJarRouter {

    @Override
    public void configure() {
    	
    	restConfiguration().component("restlet");
    	
        rest("/hello").get().to("direct:hello");
        
        rest("/test").get().to("direct:test");
        
        from("direct:hello").transform().simple("Hello World!");
        
        from("direct:test").transform().simple("Test!");
        
    }

    
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
    	
    	SpringServerServlet serverServlet = new SpringServerServlet();
    	ServletRegistrationBean regBean = new ServletRegistrationBean( serverServlet, "/rest/*");
    	
    	
    	Map<String,String> params = new HashMap<String,String>();
    	
    	params.put("org.restlet.component", "restletComponent");
    	
    	regBean.setInitParameters(params);
    	
    	return regBean;
    }
    
    
    @Bean
    public Component restletComponent() {
    	return new Component();
    }
    
    @Bean
    public RestletComponent restletComponentService() {
    	return new RestletComponent(restletComponent());
    }

}
