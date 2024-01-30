package com.glauber.gfood.api.model.mixin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.glauber.gfood.domain.model.Cozinha;
import com.glauber.gfood.domain.model.Endereco;
import com.glauber.gfood.domain.model.FormaPagamento;
import com.glauber.gfood.domain.model.Produto;

public class RestauranteMixin {

	@JsonIgnoreProperties(value = "nome", allowGetters = true) //ao serializar um restaurante a propriedade nome da cozinha é ignorada
	private Cozinha cozinha;

	@JsonIgnore
	private Endereco endereco;

	@JsonIgnore
	private LocalDateTime dataCadastro;

	@JsonIgnore
	private LocalDateTime dataAtualizacao;

	@JsonIgnore
	private List<FormaPagamento> formasPagamento = new ArrayList<>();

	@JsonIgnore
	private List<Produto> produtos = new ArrayList<>();
}
