package com.glauber.gfood.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public CozinhaNaoEncontradaException(String mensgem) {
		super(mensgem);
	}
	
	public CozinhaNaoEncontradaException(Long estadoId) {
		this(String.format("Não existe um cadastro de cozinha com o códido %d", estadoId));
	}
}
