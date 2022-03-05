package com.ems.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ems.filter.CustomAuthenticationFilter;
import com.ems.filter.CustomAuthorizationFilter;
import com.ems.service.EmployeeService;

@EnableWebSecurity
//@Configuration
public class EMSSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(employeeService).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/login").permitAll();
        http.authorizeRequests().antMatchers("/registerEmployee").permitAll();
        http.authorizeRequests().antMatchers("/v1/getAllEmployees").hasAnyAuthority("ADMIN","USER");
        http.authorizeRequests().antMatchers("/v1/**").hasAnyAuthority("USER");
        http.authorizeRequests().anyRequest().authenticated();
		http.addFilter(new CustomAuthenticationFilter(authenticationManager()));
		http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	
	
	
	
	
}
