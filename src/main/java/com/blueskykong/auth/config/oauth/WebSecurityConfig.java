package com.blueskykong.auth.config.oauth;

import com.blueskykong.auth.security.CodeAuthenticationProvider;
import com.blueskykong.auth.security.CustomAuthenticationProvider;
import com.blueskykong.auth.security.filter.CustomLogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

/**
 * Created by xuan on 2018/1/3.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{


    @Autowired
    CustomAuthenticationProvider customAuthenticationProvider;
    @Autowired
    CodeAuthenticationProvider codeAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .requestMatchers().antMatchers("/**")
                .and().authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().permitAll()
                .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                .addLogoutHandler(customLogoutHandler());
    }


    @Bean
    public CustomLogoutHandler customLogoutHandler() {
        return new CustomLogoutHandler();
    }



    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(customAuthenticationProvider);
        auth.authenticationProvider(codeAuthenticationProvider);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){

        return new SCryptPasswordEncoder();

    }


}
