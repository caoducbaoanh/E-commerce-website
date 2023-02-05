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
@Order(2)
public class UserConfigrurationSecurity extends WebSecurityConfigurerAdapter {
	@Autowired
	private SecurityConfiguration securityConfig;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(securityConfig.authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/tai-khoan/**/","/themgiohang","/don-hang/**","/giohang").hasRole("USER")
				.and().formLogin().loginPage("/dang-nhap").loginProcessingUrl("/user/login")
				.failureUrl("/dang-nhap?error=loginError").defaultSuccessUrl("/")

				.and().logout().logoutUrl("/tai-khoan/dang-xuat").logoutSuccessUrl("/")
				.deleteCookies("JSESSIONID")

				.and().exceptionHandling().accessDeniedPage("/error")

				.and().csrf().disable();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
	
	}
}
