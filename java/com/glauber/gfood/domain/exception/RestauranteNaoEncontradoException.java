package com.glauber.gfood.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public RestauranteNaoEncontradoException(String mensgem) {
		super(mensgem);
	}
	
	public RestauranteNaoEncontradoException(Long estadoId) {
		this(String.format("Não existe um cadastro de restaurante com o códido %d", estadoId));
	}
}
