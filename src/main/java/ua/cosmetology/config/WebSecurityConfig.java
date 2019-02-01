package ua.cosmetology.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ua.cosmetology.config.jwt.JwtAuthenticationEntryPoint;
import ua.cosmetology.config.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailService;
	
	@Autowired
	private JwtAuthenticationEntryPoint unauthorizeHandler;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.cors().and().csrf().disable();
		
		http.authorizeRequests()
		.antMatchers(HttpMethod.POST, "/auth/signup").permitAll()
		.antMatchers(HttpMethod.POST, "/auth/signin").permitAll()
		.antMatchers(HttpMethod.GET, "/masters/**").permitAll()
		.antMatchers(HttpMethod.GET, "/masters/image").permitAll()
		.antMatchers(HttpMethod.POST, "/masters/**").permitAll()
		.antMatchers(HttpMethod.POST, "/clients/**").permitAll()
		.antMatchers(HttpMethod.GET, "/clients/**").permitAll()
		.antMatchers(HttpMethod.GET, "/logs/**").permitAll()
		.antMatchers(HttpMethod.POST, "/notes/**").permitAll()
		.antMatchers(HttpMethod.GET, "/notes/**").permitAll()
		.antMatchers(HttpMethod.POST, "/services/**").permitAll()
		.antMatchers(HttpMethod.GET, "/services/**").permitAll()

		
		
		.and()
		.authorizeRequests()
			.antMatchers("/admin/**").hasRole("ADMIN")
			.antMatchers("/users/**").hasAnyRole("ADMIN", "USER")
			.anyRequest().authenticated();
		
		
		http.exceptionHandling().authenticationEntryPoint(unauthorizeHandler)
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(this.userDetailService).passwordEncoder(passwordEncoder());
	}


	public JwtAuthenticationFilter authenticationFilter() {
		
		return new JwtAuthenticationFilter();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		
		return super.authenticationManagerBean();
		
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	

}
