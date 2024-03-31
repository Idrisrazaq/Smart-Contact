package com.jsp.SmartContact.securityconfig;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.util.UrlPathHelper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class CustomConfiguration{
	
	@Bean
	public UserDetailsService getUserDetailService() {
		return new CustomUserServiceDetailImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider authenticationManager(
			UserDetailsService userDetailsService,
			BCryptPasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);
		
//		ProviderManager providerManager = new ProviderManager(authenticationProvider);
//		providerManager.setEraseCredentialsAfterAuthentication(false);

		return authenticationProvider;
	}
	
	@Bean
	protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.authorizeHttpRequests((authorize) -> {authorize
			.requestMatchers( "/**").permitAll()
			.requestMatchers("/user/**").hasRole("USER_ROLE")
			.requestMatchers("/admin/**").hasRole("ADMIN_ROLE")
			.anyRequest().authenticated();
		}
		)
		.httpBasic(Customizer.withDefaults())
		.formLogin(form -> form
				.loginPage("/login")
				.defaultSuccessUrl("/user/index")
				.permitAll()
			);
		
		//Not needed for logout
//		.logout().logoutSuccessHandler(new LogoutSuccessHandler() {
//			
//			@Override
//			public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
//					throws IOException, ServletException {
//				
//				System.out.println("user "+authentication.getName()+" is logged out");
//				
//				UrlPathHelper pathHelper=new UrlPathHelper();
//				
//				String context=pathHelper.getContextPath(request);
//				
//				response.sendRedirect(context+"/login");
//				
//			}
//		});
//		.logout(logout ->
// 				logout.deleteCookies("remove")
// 					.invalidateHttpSession(true)
//// 					.logoutUrl("/custom-logout")
// 					.logoutSuccessUrl("/"));

	return http.build();
	}
	
//	@Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//        	.authorizeHttpRequests(authorize -> {
//        			authorize
//                    .requestMatchers("/","/signup").permitAll();
//                    authorize
//                    .requestMatchers("/user/index").hasAnyAuthority("USER_ROLE");
//                    })
//                .formLogin()
//                    .loginPage("/signin")
//                    .defaultSuccessUrl("/user/index", true)
////                .and()
//                .permitAll();
//        return http.build();
//    }
	//$10$ggcfGy366bDoTShHj1nbxuuaBveb4Gz5DVUHelhthpObvKaDnSz3a
	
}
