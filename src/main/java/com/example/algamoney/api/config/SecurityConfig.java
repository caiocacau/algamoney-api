package com.example.algamoney.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

// Não precisava ser colocado essa anotação @Configuration pois a anotação @EnableWebSecurity já tem essa anotação nela, 
// mas, colocou pois a bater o olho na classe já sabe que é uma classe de configuração
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("admin").password("admin").roles("ROLES");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// .antMatchers("/categorias").permitAll() -> Liberando as categorias sem necessidade de autenticação
		// .anyRequest().authenticated() -> Todo o resto é autenticado
		// .httpBasic() -> Autenticação Básica
		// .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) -> Desabilitando a criação de Sessão no servidor
		// .csrf().disable() -> Desabilitando o csrf
		http.authorizeRequests()
				.antMatchers("/categorias").permitAll()
				.anyRequest().authenticated().and()
					.httpBasic().and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.csrf().disable();
	}
}
