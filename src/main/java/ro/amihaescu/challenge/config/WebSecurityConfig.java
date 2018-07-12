package ro.amihaescu.challenge.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/employee/**").authenticated()
                .antMatchers(HttpMethod.PUT, "/employee/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/employee/**").authenticated()
                .and()
                .httpBasic().and().csrf().disable();
    }
}
