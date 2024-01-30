package com.glauber.gfood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.glauber.gfood.GfoodApiApplication;
import com.glauber.gfood.domain.model.Cozinha;
import com.glauber.gfood.domain.repository.CozinhaRepository;

public class AlteracaoCozinhaMain {
	
	public static void main(String[] args) {

		ApplicationContext applicationContext = (ApplicationContext) new SpringApplicationBuilder(GfoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);

		CozinhaRepository cozinhas = applicationContext.getBean(CozinhaRepository.class);
		
		//ALTERA UMA COZINHA
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setId(1L);
		cozinha1.setNome("Espanhola");
		cozinha1 = cozinhas.save(cozinha1);
		System.out.printf("%d - %s\n", cozinha1.getId(), cozinha1.getNome());
		
		//CRIA UMA NOVA COZINHA
		cozinha1.setId(111L);
		cozinha1.setNome("Brasileira");
		cozinha1 = cozinhas.save(cozinha1);
		System.out.printf("%d - %s\n", cozinha1.getId(), cozinha1.getNome());
		
		//CRIA UMA NOVA COZINHA
		cozinha1.setId(null);
		cozinha1.setNome("Russa");
		cozinha1 = cozinhas.save(cozinha1);
		System.out.printf("%d - %s\n", cozinha1.getId(), cozinha1.getNome());
	}
}
