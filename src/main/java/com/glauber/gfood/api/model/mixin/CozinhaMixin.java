package com.glauber.gfood.api.model.mixin;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.glauber.gfood.domain.model.Restaurante;

@JsonRootName("cozinha") // usado para xml
public abstract class CozinhaMixin {

	// @JsonProperty("titulo") //para dar nome a uma propriedade json ou xml
	// private String nome;

	// @JsonProperty("ListaResturantes") //para dar nome a uma propriedade json ou xml
	@JsonIgnore
	private List<Restaurante> restaurantes;
}
