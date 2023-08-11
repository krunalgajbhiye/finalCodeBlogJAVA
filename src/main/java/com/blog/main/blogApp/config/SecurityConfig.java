package com.blog.main.blogApp.config;


import com.blog.main.blogApp.helper.JwtAuthenticationEntryPoint;
import com.blog.main.blogApp.helper.JwtAuthenticationFilter;
import com.blog.main.blogApp.services.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailService userDetailService;
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JwtAuthenticationFilter jwtRequestFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                // dont authenticate this particular request
                .authorizeRequests().antMatchers("/authenticate/**").permitAll()
                        .anyRequest().authenticated().and().
                // all other requests need to be authenticated
                // make sure we use stateless session; session won't be used to
                // store user's state.
                          exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Add a filter to validate the tokens with every request
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(this.userDetailService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public FilterRegistrationBean corsFilter(){

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();



        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowCredentials(true);

        corsConfiguration.addAllowedOriginPattern("*");



        corsConfiguration.addAllowedHeader("Authorization");

        corsConfiguration.addAllowedHeader("Content-Type");

        corsConfiguration.addAllowedHeader("Accept");



        corsConfiguration.addAllowedMethod("POST");

        corsConfiguration.addAllowedMethod("GET");

        corsConfiguration.addAllowedMethod("DELETE");

        corsConfiguration.addAllowedMethod("PUT");

        corsConfiguration.addAllowedMethod("OPTIONS");

        corsConfiguration.setMaxAge(3600L);





        source.registerCorsConfiguration("/**", corsConfiguration);



        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));



        bean.setOrder(-110);

        return bean;



    }

    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }
     // Details omitted for brevity

        @Override @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }
}
