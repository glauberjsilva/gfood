package com.glauber.gfood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.glauber.gfood.GfoodApiApplication;
import com.glauber.gfood.domain.model.Estado;
import com.glauber.gfood.domain.repository.EstadoRepository;

public class ConsultaEstadoMain {

	public static void main(String[] args) {
		ApplicationContext aplApplicationContext = new SpringApplicationBuilder(GfoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		EstadoRepository estadoRepository = aplApplicationContext.getBean(EstadoRepository.class);
		
		List<Estado> estados = estadoRepository.findAll();
	
		for (Estado estado : estados) {
			System.out.println(estado.getNome());
		}
	}
}
