package com.example.algamoney.api.config.token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.example.algamoney.api.security.UsuarioSistema;

public class CustomTokenEnhancer implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		UsuarioSistema usuarioSistema = (UsuarioSistema) authentication.getPrincipal();
		
		Map<String, Object> addInfo = new HashMap<>();
		addInfo.put("nome", usuarioSistema.getUsuario().getNome());
		
		/****** 
		 * Teste para o projeto do BezCoder React 
		 ******/
		addInfo.put("id", usuarioSistema.getUsuario().getCodigo());
		addInfo.put("username", usuarioSistema.getUsuario().getNome());
		addInfo.put("email", usuarioSistema.getUsuario().getEmail());
		List<String> roles = new ArrayList<>();
		usuarioSistema.getUsuario().getPermissoes().forEach(permissao -> {
		    roles.add(permissao.getDescricao());
		});
		addInfo.put("roles", roles);
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(addInfo);
		
		return accessToken;
	}

}
