package br.com.hioktec.keycloakspringboottestes.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class WebConfig {
		
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilterRegistry(){
		
		List<String> all = Arrays.asList("*");
		
		List<String> appsUrl = Arrays.asList("http://localhost:3000", "http://localhost:4200");
	
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowedOrigins(appsUrl);
		corsConfig.setAllowedHeaders(all);
		corsConfig.setAllowedMethods(all);
		corsConfig.setAllowCredentials(true);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);
		
		CorsFilter corsFilter = new CorsFilter((CorsConfigurationSource) source);
		
		FilterRegistrationBean<CorsFilter> filter = new FilterRegistrationBean<CorsFilter>(corsFilter);
		filter.setOrder(Ordered.HIGHEST_PRECEDENCE);
		
		return filter;
	}
}
