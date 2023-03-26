package by.itstep.servicedeskproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
//                authorizeHttpRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers(
                                "/register/**").permitAll()
                                .requestMatchers("/index").permitAll()
                                .requestMatchers("/home").permitAll()
                                .requestMatchers("/appeals").permitAll()
                                .requestMatchers("/history").permitAll()
                                .requestMatchers("/appeals/**").permitAll()
                                .requestMatchers("/main").permitAll()
                                .requestMatchers("/add/**").permitAll()
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/appeals/work/**").hasAnyRole("ADMIN", "MANAGER")
                                .requestMatchers("/appeals/close/**").hasAnyRole("ADMIN", "MANAGER")
                                .requestMatchers("/appeals/delete/**").permitAll()
                                .requestMatchers("/appeals/update/**").hasRole("USER")
                                .requestMatchers("/appeals/save_admin/**").hasRole("ADMIN")
                                .requestMatchers("/save/**").permitAll()
                                .requestMatchers("/user/delete/**").hasRole("ADMIN")
                                .requestMatchers("/user/update/**").hasRole("ADMIN")
                                .requestMatchers("/users/page/**").hasRole("ADMIN")
                                .requestMatchers("/user/save/**").hasRole("ADMIN")
                                .requestMatchers("/img/**").permitAll()
                                .requestMatchers("/users").hasRole("ADMIN")
                                .requestMatchers("/css/style.css","/js/bootstrap.js","/css/**").permitAll()
                                .requestMatchers("/appeals/history/").permitAll()
                                .requestMatchers("/appeals/history/").hasAnyRole("ADMIN", "MANAGER")

                ).formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/main")
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}