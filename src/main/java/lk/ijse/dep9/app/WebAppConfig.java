package lk.ijse.dep9.app;

import lombok.experimental.Delegate;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan
@EnableWebSecurity
public class WebAppConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests().requestMatchers(HttpMethod.POST,"/api/v1/users")
                .permitAll()
                .requestMatchers("/api/v1/auth/**").permitAll().anyRequest().authenticated()//all the other should authenticated
                .and()
                .csrf().disable()//cross side request frogary. as we do not use ssr we disable this(disable in restful as do not use state)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)//do not make sessions
                .and()
                .httpBasic()
                .and().build();
    }

    //as we use hashing to save the password this should be used
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return DigestUtils.sha256Hex(rawPassword+"");
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.matches(DigestUtils.sha256Hex(rawPassword+""));
            }
        };
    }
}
