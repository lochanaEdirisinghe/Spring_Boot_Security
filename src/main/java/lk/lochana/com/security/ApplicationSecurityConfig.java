package lk.lochana.com.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static lk.lochana.com.security.AppUserPermissions.COURSE_WRITE;
import static lk.lochana.com.security.ApplicationUserRole.ADMIN;
import static lk.lochana.com.security.ApplicationUserRole.ADMINTRAINEE;
import static lk.lochana.com.security.ApplicationUserRole.STUDENT;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    PasswordEncoder encoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http    .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                .authorizeRequests()
                .antMatchers("/", "index").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
                .anyRequest().authenticated().and().httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails lochana = User.builder().username("Lochana").password(encoder.encode("lochana"))
                //.roles(STUDENT.name()).build();
            .authorities(STUDENT.getGrantedAuthorities()).build();
        UserDetails rashmika = User.builder().username("Rashmika").password(encoder.encode("rashmika"))
                //.roles(ADMIN.name()).build();
            .authorities(ADMIN.getGrantedAuthorities()).build();
        UserDetails sahan = User.builder().username("Sahan").password(encoder.encode("sahan"))
                //.roles(ADMINTRAINEE.name()).build();
            .authorities(ADMINTRAINEE.getGrantedAuthorities()).build();

        return new InMemoryUserDetailsManager(lochana, rashmika, sahan);
    }
}
