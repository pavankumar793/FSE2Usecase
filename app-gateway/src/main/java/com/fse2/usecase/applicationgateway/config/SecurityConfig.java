package com.fse2.usecase.applicationgateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeRequests(requests -> requests.antMatchers("/blogsite/**", "/actuator/**").permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(exceptionHandling -> {
                    exceptionHandling.authenticationEntryPoint(new Http403ForbiddenEntryPoint());
                    exceptionHandling.accessDeniedHandler(new AccessDeniedHandlerImpl());
                })
                .sessionManagement(management -> management
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    }
}