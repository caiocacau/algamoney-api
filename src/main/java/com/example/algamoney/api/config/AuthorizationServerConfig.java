package com.example.algamoney.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig  extends AuthorizationServerConfigurerAdapter{

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private ResourceServerConfig resourceServerConfig;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
			.withClient("angular")
//			.secret("{noop}@ngul@r0")
			.secret(resourceServerConfig.passwordEncoder().encode("@ngul@r0"))
			.scopes("read", "write")
			.authorizedGrantTypes("password","refresh_token")
			.accessTokenValiditySeconds(1800) // 30 minutos
//			.accessTokenValiditySeconds(20) // 20 segundos
			.refreshTokenValiditySeconds(3600 * 24)
		.and()
			.withClient("mobile")
//			.secret("{noop}m0b1l30")
			.secret(resourceServerConfig.passwordEncoder().encode("m0b1l30"))
			.scopes("read")
			.authorizedGrantTypes("password","refresh_token")
			.accessTokenValiditySeconds(1800) // 30 minutos
//			.accessTokenValiditySeconds(20) // 20 segundos
			.refreshTokenValiditySeconds(3600 * 24);
		
//		clients.inMemory()
//        .withClient("client")
//        .secret("encrypt secret")
//        .scopes("read", "write")
//        .authorizedGrantTypes("password", "refresh_token")
//        .accessTokenValiditySeconds(20)
//        .refreshTokenValiditySeconds(3600 * 24);
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
			.tokenStore(tokenStore())
			.accessTokenConverter(accessTokenConverter())
			.reuseRefreshTokens(false)
			.authenticationManager(authenticationManager);
	}
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		accessTokenConverter.setSigningKey("algaworks");
		return accessTokenConverter;
	}

	@Bean
	public TokenStore tokenStore() {
//		return new InMemoryTokenStore();
		return new JwtTokenStore(accessTokenConverter());
	}
	
}
