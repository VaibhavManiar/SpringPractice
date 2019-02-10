package com.practice.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class DemoWebSecurity extends WebSecurityConfigurerAdapter {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

    @Autowired
    private MyPasswordEncoder passwordEncoder;

    @Autowired
    private SecurityUtils securityUtils;

    public DemoWebSecurity() {
        super();
    }

    public DemoWebSecurity(boolean isDefaultConfigEnabled) {
        super(isDefaultConfigEnabled);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser(new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
                grantedAuthorities.add(new SimpleGrantedAuthority(ROLE.ADMIN.name()));
                grantedAuthorities.add(new SimpleGrantedAuthority(ROLE.USER.name()));
                return grantedAuthorities;
            }

            @Override
            public String getPassword() {
                return passwordEncoder.encode(PASSWORD);
            }

            @Override
            public String getUsername() {
                return USERNAME;
            }

            @Override
            public boolean isAccountNonExpired() {
                return Boolean.TRUE;
            }

            @Override
            public boolean isAccountNonLocked() {
                return Boolean.TRUE;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return Boolean.TRUE;
            }

            @Override
            public boolean isEnabled() {
                return Boolean.TRUE;
            }
        });
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/anonymous*").anonymous()
                .antMatchers("/login*").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //.loginPage("/login.html")
                //.loginProcessingUrl("/perform_login")
                //.defaultSuccessUrl("/actuator/health", true)
                .defaultSuccessUrl(String.format("/hello/%s", getUsername()), true)
                //.failureUrl("/login.html?error=true")
                //.failureHandler(authenticationFailureHandler())
                .and()
                .logout()
                //.logoutUrl("/perform_logout")
                .deleteCookies("JSESSIONID");
        //.logoutSuccessHandler(logoutSuccessHandler());
    }

    public enum ROLE {
        ADMIN, USER;
    }

    private String getUsername() {
        Optional<String> userNameOptional = securityUtils.getCurrentLoggedInUserName();
        return userNameOptional.isPresent() ? userNameOptional.get() : USERNAME;
    }
}