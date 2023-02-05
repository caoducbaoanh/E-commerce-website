package cdw.hk2.shop.laptop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configurable
@EnableWebSecurity
@Order(1)
public class AdminConfigrurationSecurity extends WebSecurityConfigurerAdapter {
	@Autowired
	private SecurityConfiguration securityConfig;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(securityConfig.authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	      http.antMatcher("/admin/**")
          .authorizeRequests()
          .anyRequest()
          .hasRole("ADMIN")
          .and()
          .formLogin()
          .loginPage("/admin/system/login")
          .loginProcessingUrl("/admin/login")
          .failureUrl("/admin/system/login?error=loginError")
          .defaultSuccessUrl("/index")
          
          .and()
          .logout()
          .logoutUrl("/admin/system/logout")
          .logoutSuccessUrl("/")
          .deleteCookies("JSESSIONID")
          
          .and()
          .exceptionHandling()
          .accessDeniedPage("/error")
          
          .and()
          .csrf().disable();
	}
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/admin/system/login","/admin/css/**","/admin/js/**","/admin/fonts/**");
	}
}
