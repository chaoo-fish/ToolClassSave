package com.chaoo.springSecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 配置类
 */
@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin() // 自定义登录页面
                .loginPage("/login.html") // 登录页面的名称和路径
                .loginProcessingUrl("/login") // 处理登录
                .successForwardUrl("/success") // 登录成功后处理的请求
                .usernameParameter("account")// 自定义用户名
                .failureForwardUrl("/failure") // 登陆失败后处理的请求
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/", "/login").permitAll() // 这些请求不需要验证

//                .antMatchers("/find").hasAuthority("admin1") // 需要admin权限
//                .antMatchers("/find").hasAnyAuthority("admin","role") // 如果用户带有指定任何一个权限就可以
//                .antMatchers("/find").hasRole("role") // 需要用户带有 role 角色才可以
                .antMatchers("/find").hasAnyRole("role1", "admin1") // 需要用户带有 role 角色才可以

                .anyRequest().authenticated() // 其它请求都需要认证
                .and();
//                .csrf().disable(); // 关闭 csrf 防护

        http.exceptionHandling()
                .accessDeniedPage("/unauth.html"); // 配置无权限访问页面

        http.logout().logoutUrl("/logout").logoutSuccessUrl("/").permitAll();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}




/*
@Bean
    UserDetailsService userDetailsService() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String chaoo = passwordEncoder.encode("chaoo123");
        // 在内存中创建用户
        InMemoryUserDetailsManager users = new InMemoryUserDetailsManager();
        // 创建了一个用户
        users.createUser(User.withUsername("chaoo").password(chaoo).roles("admin").build());
        return users;
    }*/

