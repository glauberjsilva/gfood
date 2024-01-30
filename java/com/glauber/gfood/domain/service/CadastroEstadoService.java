package com.glauber.gfood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.glauber.gfood.domain.exception.EntidadeEmUsoException;
import com.glauber.gfood.domain.exception.EstadoNaoEncontradoException;
import com.glauber.gfood.domain.model.Estado;
import com.glauber.gfood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	private static final String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removido, pois está em uso";

	@Autowired
	private EstadoRepository repository;

	public Estado salvar(Estado estado) {
		return repository.save(estado);
	}

	public void excluir(Long estadoId) {
		try {
			repository.deleteById(estadoId);
		
		} catch (EmptyResultDataAccessException ex) {
			throw new EstadoNaoEncontradoException(estadoId);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, estadoId));
		}
	}
	
	public Estado buscarOuFalhar(Long estadoId) {
		return repository.findById(estadoId).orElseThrow(
				() -> new EstadoNaoEncontradoException(estadoId));
	}

}
