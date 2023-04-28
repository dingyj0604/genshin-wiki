package com.example.genshinwiki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author ZXD
 */
//@EnableWebSecurity
@SpringBootApplication
public class GenshinWikiApplication {
//    public GenshinWikiApplication(UserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }

    public static void main(String[] args) {
        SpringApplication.run(GenshinWikiApplication.class, args);
    }
//    private final UserDetailsService userDetailsService;
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//        PasswordEncoder encoder = new Pbkdf2PasswordEncoder(AppConst.SECRET);
//        auth.userDetailsService(userDetailsService)
//                .passwordEncoder(encoder);
//    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
//        http
//                .authorizeHttpRequests()
    //管理员和普通用户可访问user路径下的接口
//                .antMatchers("/user/**").hasAnyRole("USER","ADMIN")
    //管理员可访问admin路径下的接口
//                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
//                .anyRequest().permitAll()

//                .and().anonymous()
    //使用默认登录页面
//                .and().formLogin()
//                .and().httpBasic();
//    }
}
