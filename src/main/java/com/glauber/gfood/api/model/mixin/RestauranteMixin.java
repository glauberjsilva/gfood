package com.glauber.gfood.api.model.mixin;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.glauber.gfood.domain.model.Cozinha;
import com.glauber.gfood.domain.model.Endereco;
import com.glauber.gfood.domain.model.FormaPagamento;
import com.glauber.gfood.domain.model.Produto;

public abstract class RestauranteMixin {

	//ao serializar um restaurante a propriedade nome da cozinha é ignorada
	@JsonIgnoreProperties(value = "nome", allowGetters = true) 
	private Cozinha cozinha;

	//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	//@JsonIgnoreProperties({"hibernateLazyInitializer"})
	@JsonIgnore
	private Endereco endereco;

	//@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	//@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
	@JsonIgnore
	private OffsetDateTime dataCadastro;

	//@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	//@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
	@JsonIgnore
	private OffsetDateTime dataAtualizacao;

	@JsonIgnore
	private List<FormaPagamento> formasPagamento;

	@JsonIgnore
	private List<Produto> produtos;
}
