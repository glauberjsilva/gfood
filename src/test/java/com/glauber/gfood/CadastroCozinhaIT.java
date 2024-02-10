package com.glauber.gfood;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.glauber.gfood.domain.exception.CozinhaNaoEncontradaException;
import com.glauber.gfood.domain.exception.EntidadeEmUsoException;
import com.glauber.gfood.domain.model.Cozinha;
import com.glauber.gfood.domain.model.Restaurante;
import com.glauber.gfood.domain.service.CadastroCozinhaService;
import com.glauber.gfood.domain.service.CadastroRestauranteService;

/**
 * TESTE DE INTEGRAÇÃO
 */
@SpringBootTest
public class CadastroCozinhaIT {

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	

	@Test
	public void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos() {
		// Cenário
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");

		// Ação
		novaCozinha = cadastroCozinhaService.salvar(novaCozinha);

		// Validação
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();

	}

	@Test()
	public void deveFalhar_QuandoCadastrarCozinhaSemNome() {
		// Cenário
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);

		// Ação

		ConstraintViolationException erroEsperado = Assertions.assertThrows(ConstraintViolationException.class, () -> {
			cadastroCozinhaService.salvar(novaCozinha);
		});

		assertThat(erroEsperado).isNotNull();

		// Ação
		// Segunda opção
		/*
		 * try { cadastroCozinhaService.salvar(novaCozinha);
		 * Assertions.fail("Deveria dar erro ao cadastrar uma cozinha sem nome"); }
		 * catch (Exception e) {
		 * assertThat(e.getClass()).isEqualTo(ConstraintViolationException.class); }
		 */
	}

	@Test
	public void deveFalhar_QuandoExcluirCozinhaEmUso() {
		Cozinha cozinha = new Cozinha();
		cozinha.setNome("Coreana");
		cozinha = cadastroCozinhaService.salvar(cozinha);
		
		Restaurante restaurante = new Restaurante();
		restaurante.setNome("Restaurante coreano");
		restaurante.setTaxaFrete(BigDecimal.TEN);
		restaurante.setCozinha(cozinha);
		restaurante.setEndereco(null);
		
		restaurante = cadastroRestauranteService.salvar(restaurante);
		
		Long cozinhaEmUsoId = cozinha.getId();
		
		var erroEsperado = assertThrows(EntidadeEmUsoException.class, () -> {
			cadastroCozinhaService.excluir(cozinhaEmUsoId);
		});

		assertThat(erroEsperado);
	}

	@Test
	@DisplayName("Dever lançar CozinhaNaoEncontradaException ao tentar excluir uma cozinha inexistente")
	public void deveFalhar_QuandoExcluirCozinhaInexistente() {
		var erroEsperado = assertThrows(CozinhaNaoEncontradaException.class, () -> {
			cadastroCozinhaService.excluir(100L);
		});

		assertThat(erroEsperado);
	}

}
