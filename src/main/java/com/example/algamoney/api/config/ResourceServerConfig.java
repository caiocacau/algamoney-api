package com.example.algamoney.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;

// Não precisava ser colocado essa anotação @Configuration pois a anotação @EnableWebSecurity já tem essa anotação nela, 
// mas, colocou pois a bater o olho na classe já sabe que é uma classe de configuração
@Configuration
@EnableWebSecurity
@EnableResourceServer
//@EnableGlobalMethodSecurity(prePostEnabled = true) // Com essa anotação, se consegue habilitar a segurança nos métodos 
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		// Versão do Spring precisou colocar esse {noop} antes da senha para o encoder
//		auth.inMemoryAuthentication()
//			.withUser("admin").password("{noop}admin").roles("ROLES");
		
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//		auth.userDetailsService(userDetailsService).passwordEncoder(delegatingPasswordEncoder());
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		// .antMatchers("/categorias").permitAll() -> Liberando as categorias sem necessidade de autenticação
		// .anyRequest().authenticated() -> Todo o resto é autenticado
		// .httpBasic() -> Autenticação Básica
		// .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) -> Desabilitando a criação de Sessão no servidor
		// .csrf().disable() -> Desabilitando o csrf
		http.authorizeRequests()
				.antMatchers("/categorias/**").permitAll() // Se colocar no método alguma anotação tipo @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')"), perde sentido
				.anyRequest().authenticated().and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.csrf().disable();
	}
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.stateless(true);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// For spring security 5 OAuth 2 Server
//	@Bean
//    public PasswordEncoder delegatingPasswordEncoder() {
//        Map<String, PasswordEncoder> encoders = new HashMap<>();
//        encoders.put("bcrypt", new BCryptPasswordEncoder());
//        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("bcrypt", encoders);
//        passwordEncoder.setDefaultPasswordEncoderForMatches(new BCryptPasswordEncoder());
//        return passwordEncoder;
//    }
	
	
	// Handler para conseguir fazer a segurança dos métodos com o Oauth2
	@Bean
	public MethodSecurityExpressionHandler createExpressionHandler() {
		return new OAuth2MethodSecurityExpressionHandler(); 
	}
	 
}
