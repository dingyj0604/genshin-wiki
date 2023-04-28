//package com.example.genshinwiki.config;
//
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
//import org.springframework.stereotype.Component;
//
///**
// * 用户登录权限验证
// *
// * @author zxd
// * @date 2022/6/5 19:59
// */
//@Component
//@AllArgsConstructor
//public class UserWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
//    @Value("${system.user.password.secret}")
//    private String secret;
//    private final UserDetailsService userDetailsService;
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        UserDetails user = userDetailsService.loadUserByUsername("root");
//
//        PasswordEncoder encoder = new Pbkdf2PasswordEncoder(secret);
//        auth.userDetailsService(userDetailsService)
//                .passwordEncoder(encoder);
//    }
//}
