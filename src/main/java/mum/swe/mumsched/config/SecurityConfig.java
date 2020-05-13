package mum.swe.mumsched.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//@Autowired
//private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;
   
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new PasswordEncoder() {

@Override
public boolean matches(CharSequence rawPassword, String encodedPassword) {
// TODO Auto-generated method stub
return true;
}

@Override
public String encode(CharSequence rawPassword) {
// TODO Auto-generated method stub
return rawPassword.toString();
}
});
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {        
       http
        .authorizeRequests()
    .antMatchers("/login*", "/signup*", "/forgot-password*").permitAll()
            //.antMatchers("/users/**").hasAuthority("ROLE_ADMIN")
            .anyRequest().fullyAuthenticated()
            .and()
        .formLogin()
        .loginPage("/login")
        .failureUrl("/login?error")
.usernameParameter("username")
.passwordParameter("password")
        .defaultSuccessUrl("/")
            .permitAll()
            .and()
        .logout()        
        .logoutUrl("/logout")                    
            .permitAll()
            .and()
            .rememberMe();

//       http
//               .authorizeRequests()
//                   .antMatchers("/resources/**", "/registration").permitAll()
//                   .anyRequest().authenticated()
//                   .and()
//               .formLogin()
//                   .loginPage("/login")
//                   .permitAll()
//                   .and()
//               .logout()
//                   .permitAll();
//      
    }

@Override
public void configure(WebSecurity web) throws Exception {
   web
      .ignoring()
      .antMatchers("/public/**");
}

@Bean
public BCryptPasswordEncoder passwordEncoder() {
BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
return bCryptPasswordEncoder;
}

   
// @Autowired
// private DataSource dataSource;
//
// @Value("${spring.queries.users-query}")
// private String usersQuery;
//
// @Value("${spring.queries.roles-query}")
// private String rolesQuery;
//
// @Override
// protected void configure(AuthenticationManagerBuilder auth)
// throws Exception {
// auth.
// jdbcAuthentication()
// .usersByUsernameQuery(usersQuery)
// .authoritiesByUsernameQuery(rolesQuery)
// .dataSource(dataSource)
// .passwordEncoder(bCryptPasswordEncoder);
// }
}