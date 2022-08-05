package com.roms.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsService userDetailsService;


	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	 
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();

        http.
         // ... here goes your custom security configuration
         authorizeRequests().
         antMatchers(AUTH_WHITELIST).permitAll().  // whitelist Swagger UI resources
         // ... here goes your custom security configuration
         antMatchers("/**").authenticated();  // require authentication for any endpoint that's not whitelisted
}
		
//		http.csrf().ignoringAntMatchers("/eureka/**");
//		http.csrf().disable()
//		.authorizeRequests()
//		.antMatchers("/api/**")
//		.permitAll().antMatchers(HttpMethod.OPTIONS,"/**")
//		.permitAll().anyRequest().authenticated()
//		.and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).
//		and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
//		and().addFilterBefore(customJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	/*@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("swagger-ui.html")
	      .addResourceLocations("classpath:/META-INF/resources/");

	    registry.addResourceHandler("/webjars/**")
	      .addResourceLocations("classpath:/META-INF/resources/webjars/");
		}*/
		
	private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/auth/authenticate",
            "/auth/validate",
            "/auth/decode",
            "/process/processdetail",
            "/process/CompleteProcessing",
            "/process/processlist",
            "/compute/packagingdeliverycost",
            "/h2-console",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };

	



		}

	
	