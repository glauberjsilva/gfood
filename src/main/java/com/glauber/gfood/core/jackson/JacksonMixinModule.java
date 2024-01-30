package com.glauber.gfood.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.glauber.gfood.api.model.mixin.CidadeMixin;
import com.glauber.gfood.api.model.mixin.CozinhaMixin;
import com.glauber.gfood.api.model.mixin.RestauranteMixin;
import com.glauber.gfood.domain.model.Cidade;
import com.glauber.gfood.domain.model.Cozinha;
import com.glauber.gfood.domain.model.Restaurante;

@Component
public class JacksonMixinModule extends SimpleModule {

	private static final long serialVersionUID = 1L;
	
	public JacksonMixinModule() {
		setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
		setMixInAnnotation(Cidade.class, CidadeMixin.class);
		setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
	}

}
