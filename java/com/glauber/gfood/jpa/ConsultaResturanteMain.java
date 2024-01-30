package com.glauber.gfood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.glauber.gfood.GfoodApiApplication;
import com.glauber.gfood.domain.model.Restaurante;
import com.glauber.gfood.domain.repository.RestauranteRepository;

public class ConsultaResturanteMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = (ApplicationContext) new SpringApplicationBuilder(GfoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);

		RestauranteRepository resturantes = applicationContext.getBean(RestauranteRepository.class);

		List<Restaurante> todosRestaurantes = resturantes.findAll();

		for (Restaurante restaurante : todosRestaurantes) {
			System.out.printf("%s - %f - %s\n", restaurante.getNome(),
					restaurante.getTaxaFrete(), restaurante.getCozinha().getNome());
		}
	}
}
