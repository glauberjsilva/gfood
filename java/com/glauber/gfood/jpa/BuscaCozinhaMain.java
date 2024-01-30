package com.glauber.gfood.jpa;

import java.util.Optional;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.glauber.gfood.GfoodApiApplication;
import com.glauber.gfood.domain.model.Cozinha;
import com.glauber.gfood.domain.repository.CozinhaRepository;

public class BuscaCozinhaMain {

	public static void main(String[] args) {

		ApplicationContext applicationContext = (ApplicationContext) new SpringApplicationBuilder(
				GfoodApiApplication.class).web(WebApplicationType.NONE).run(args);

		CozinhaRepository cozinhas = applicationContext.getBean(CozinhaRepository.class);

		Optional<Cozinha> cozinhaOpt = cozinhas.findById(1L);
		if (cozinhaOpt.isPresent()) {
			Cozinha cozinha1 = cozinhaOpt.get();
			System.out.printf("%d - %s", cozinha1.getId(), cozinha1.getNome());

		}
	}
}
