package com.example.algamoney.api.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("algamoney") // Passa o nome que desejar
public class AlgamoneyApiProperty {

	@Getter
	@Setter
	private String originPermitida = "http://localhost:8000";
	
	@Getter
	public final Seguranca seguranca = new Seguranca();
	
	// Dica: Criado por classe para agrupar por temas(Pode criar outras classes se desejar)
	// Na chamadas, algamoneyApiProperty.enableHttps
	public static class Seguranca {
		
		@Getter
		@Setter
		private boolean enableHttps; // Por default do boolean é false
		
	}
	
}
