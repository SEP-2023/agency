//package com.project.agencija.config;
//
//
//import com.project.agencija.security.RestAuthenticationEntryPoint;
//import com.project.agencija.security.TokenAuthenticationFilter;
//import com.project.agencija.service.UserService;
//import com.project.agencija.util.TokenUtils;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//
//@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    public WebSecurityConfig(TokenUtils tokenUtils) {
//        this.tokenUtils = tokenUtils;
//    }
//
//    @Bean
//    public UserService userDetailsService() {
//        return new UserService();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public RestAuthenticationEntryPoint restAuthenticationEntryPoint(){ return new RestAuthenticationEntryPoint();}
//
//    @Bean
//    public AuthenticationManager authenticationManager(
//        AuthenticationConfiguration authConfig) throws Exception {
//        return authConfig.getAuthenticationManager();
//    }
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//
//        authProvider.setUserDetailsService(userDetailsService());
//        authProvider.setPasswordEncoder(passwordEncoder());
//
//        return authProvider;
//    }
//
//    private final TokenUtils tokenUtils;
////    @Bean
////    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
////        http.cors().and().csrf().disable()
////                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
////                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint()).and()
////                .authorizeHttpRequests()
////                .requestMatchers("api/auth/**").permitAll()
////                .anyRequest().authenticated().and()
////                .logout(logout -> logout.logoutUrl("/").logoutSuccessUrl("/").invalidateHttpSession(true))
////                .addFilterBefore(new TokenAuthenticationFilter(tokenUtils, userDetailsService()),BasicAuthenticationFilter.class);
////
////        http.headers().frameOptions().disable();
////        http.headers()
////                .xssProtection()
////                .and()
////                .contentSecurityPolicy("script-src 'self'");
////        return http.build();
////    }
////
////    @Bean
////    public WebSecurityCustomizer webSecurityCustomizer() {
////        return web -> {
////            web.ignoring().requestMatchers(HttpMethod.GET, "/","/socket/**", "/webjars/**", "/*.html", "favicon.ico", "/*/*.html",
////                    "/*/*.css", "/*/*.js");
////            web.ignoring().requestMatchers(HttpMethod.POST,"/", "/api/auth/**");
////
////        };
////    }
//
//    @Bean
//    protected CorsConfigurationSource corsConfigurationSource() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
//        return source;
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//
//                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint()).and()
//                .authorizeRequests().antMatchers("**/auth/**").permitAll()
//                .antMatchers("/api/auth/**").permitAll()
//                .anyRequest().authenticated().and()
//                .cors().and()
//                .addFilterBefore(new TokenAuthenticationFilter(tokenUtils, userDetailsService()), BasicAuthenticationFilter.class);
//
//        http.csrf().disable();
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//
//        web.ignoring().antMatchers(HttpMethod.POST, "/api/auth/**");
//
//        web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "favicon.ico", "/**/*.html",
//                "/**/*.css", "/**/*.js");
//    }
//
//}
