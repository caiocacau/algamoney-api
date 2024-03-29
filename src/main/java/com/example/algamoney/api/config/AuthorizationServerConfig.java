package com.example.algamoney.api.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.example.algamoney.api.config.token.CustomTokenEnhancer;

//@Profile("ouath-security")
@Profile("prod")
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig  extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private ResourceServerConfig resourceServerConfig;
	
	@Autowired
	private UserDetailsService userDetailService;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
			.withClient("angular")
//			.secret("{noop}@ngul@r0")
			.secret(resourceServerConfig.passwordEncoder().encode("@ngul@r0"))
			.scopes("read", "write")
			.authorizedGrantTypes("password","refresh_token")
//			.accessTokenValiditySeconds(60 * 20) // 20 minutos
			.accessTokenValiditySeconds(10) // 10 segundos
			.refreshTokenValiditySeconds(3600 * 24) // 1 dia
//			.refreshTokenValiditySeconds(30) // 30 segundos
		.and()
			.withClient("mobile")
//			.secret("{noop}m0b1l30")
			.secret(resourceServerConfig.passwordEncoder().encode("m0b1l30"))
			.scopes("read")
			.authorizedGrantTypes("password","refresh_token")
			.accessTokenValiditySeconds(60 * 30) // 30 minutos
//			.accessTokenValiditySeconds(20) // 20 segundos
			.refreshTokenValiditySeconds(3600 * 24); // 1 dia
		
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
		// Criando uma cadeia de token melhorados
		// Colocando novas informações no token(nesse caso, pelo tokenEnhancer() vai ser inserido o nome do usuário no token)
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));
		
		endpoints
			.authenticationManager(authenticationManager)
			.userDetailsService(userDetailService) // TODO - INSERIDO AQUI DO GITHUB ALGAWORKS
			.tokenEnhancer(tokenEnhancerChain)
			.tokenStore(tokenStore())
//			.accessTokenConverter(accessTokenConverter()) // Substituido pela linha abaixo ao ser colocado o tokenEnhancerChain acima
			.reuseRefreshTokens(false);
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

	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new CustomTokenEnhancer();
	}
}
