package com.luv2code.ecommerce.config;
import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filerChain( HttpSecurity http) throws Exception{
        //protect endpoint /api /orders
        http.authorizeRequests(configurer ->
                configurer
                        .antMatchers("/api/orders/**")
                        .authenticated())
                .oauth2ResourceServer()
                .jwt();
              //add cors filters
        http.cors();
        //add content negotiation strategy
        http.setSharedObject(ContentNegotiationStrategy.class,
                new HeaderContentNegotiationStrategy());
        //force a non-empty response body for1's to make the response more friendly
        Okta.configureResourceServer401ResponseBody(http);
        //disable csrf since we are not using cookies for session tracking
        http.csrf().disable();
    return http.build();
    }

}
