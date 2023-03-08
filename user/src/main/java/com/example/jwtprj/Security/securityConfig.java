package com.example.jwtprj.Security;

import com.example.jwtprj.filter.customAuthFilter;
import com.example.jwtprj.filter.customAuthoriszationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class securityConfig extends WebSecurityConfigurerAdapter {
private final UserDetailsService userDetailsService;
private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final WebClient.Builder webClientBuilder;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        customAuthFilter customauthFilter = new customAuthFilter(authenticationManagerBean());
        customauthFilter.setFilterProcessesUrl("/api/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/api/login/**","/api/token/refresh/**","/api/checkEmail","/api/oauth/**","/api/user/save").permitAll();
        http.authorizeRequests().antMatchers(GET,"/api/user/**").hasAnyAuthority("USER");
        http.authorizeRequests().antMatchers(POST,"/api/user/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(GET,"/api/users").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(GET,"/api/role/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(POST,"/api/role/**").hasAnyAuthority("ADMIN");
       http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customauthFilter);
        http.addFilterBefore(new customAuthoriszationFilter(), UsernamePasswordAuthenticationFilter.class);


    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

}
