package com.example.algamoney.api.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.entities.Usuario;
import com.example.algamoney.api.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Optional<Usuario> buscarUsuarioPeloCodigo(Long codigo) {
		Optional<Usuario> usuario = usuarioRepository.findById(codigo);
		if (!usuario.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		
		return usuario;
	}
	
	@Transactional
	public Usuario inserir(Usuario usuario) {
		Usuario usuarioInserir = new Usuario();
		
		BeanUtils.copyProperties(usuario, usuarioInserir);
		
		// Senha
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		usuarioInserir.setSenha(encoder.encode(usuarioInserir.getSenha()));
		
		Usuario usuarioSalvo = usuarioRepository.save(usuarioInserir);
		
		return usuarioSalvo;
	}
	
	@Transactional
	public Usuario atualizar(Long codigo, Usuario usuario) {
		
		Optional<Usuario> usuarioBDOptional = buscarUsuarioPeloCodigo(codigo);
		Usuario usuarioBD = usuarioBDOptional.get();
		
		BeanUtils.copyProperties(usuario, usuarioBD, new String[] {"codigo", "senha"});

		// Senha
		if (usuario.getSenha() != null) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			usuarioBD.setSenha(encoder.encode(usuario.getSenha()));
		}
		
		return usuarioRepository.save(usuarioBD);
	}

//	public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
//		Pessoa pessoa = buscarPessoaPeloCodigo(codigo);
//		pessoa.setAtivo(ativo);
//		pessoaRepository.save(pessoa);
//	}
//	
//	private void atualizandoListasComPessoa(Pessoa pessoa) {
//		if (pessoa.getLancamentos() != null && !pessoa.getLancamentos().isEmpty()) {
//			pessoa.getLancamentos().forEach((lancamento)-> {
//				lancamento.setPessoa(pessoa);
////				lancamento.setCodigoPessoa(pessoa.getCodigo());
//			});
//		}
//			
//		if (pessoa.getAlcunhas() != null && !pessoa.getAlcunhas().isEmpty()) {
//			pessoa.getAlcunhas().forEach((alcunha)-> {
//				alcunha.setPessoa(pessoa);
//			});
//		}
//	}
//	
//	private List<Long> idsLancamentosARemover(Pessoa pessoa, Pessoa pessoaBD) {
//		// Excluindo registros que não tiverem nas listas
//		List<Long> idLancamentosARemover = new ArrayList<>();
//		
//		pessoaBD.getLancamentos().forEach( lancamento -> {
//			if (!pessoa.getLancamentos().contains(lancamento)) {
//				idLancamentosARemover.add(lancamento.getCodigo());
//			};
//		});
//		
////		for (Lancamento lancamento : pessoaBD.getLancamentos()) {
////			if (lancamento.getCodigo() != null) {
////				if (!pessoa.getLancamentos().contains(lancamento)) {
////					idLancamentosARemover.add(lancamento.getCodigo());
////				};
////			}
////		}
//		return idLancamentosARemover;
//	}
//	
//	private List<Long> idsAlcunhasARemover(Pessoa pessoa, Pessoa pessoaBD) {
//		// Excluindo registros que não tiverem nas listas
//		List<Long> idAlcunhasARemover = new ArrayList<>();
//		
//		pessoaBD.getAlcunhas().forEach( alcunha -> {
//			if (!pessoa.getAlcunhas().contains(alcunha)) {
//				idAlcunhasARemover.add(alcunha.getCodigo());
//			};
//		});
//		
////		for (Alcunha alcunha : pessoaBD.getAlcunhas()) {
////			if (alcunha.getCodigo() != null) {
////				if (!pessoa.getAlcunhas().contains(alcunha)) {
////					idAlcunhasARemover.add(alcunha.getCodigo());
////				};
////			}
////		}
//		return idAlcunhasARemover;
//	}
	
}
