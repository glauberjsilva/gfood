package com.glauber.gfood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.glauber.gfood.GfoodApiApplication;
import com.glauber.gfood.domain.model.FormaPagamento;
import com.glauber.gfood.domain.repository.FormaPagamentoRepository;

public class ConsultaFormaPagamentoMain {

	public static void main(String[] args) {
		ApplicationContext aplApplicationContext = new SpringApplicationBuilder(GfoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		FormaPagamentoRepository formaPagamentoRepository = aplApplicationContext.getBean(FormaPagamentoRepository.class);
		
		List<FormaPagamento> formaPagamentos = formaPagamentoRepository.findAll();
	
		for (FormaPagamento formaPagamento : formaPagamentos) {
			System.out.println(formaPagamento.getDescricao());
		}
	}
}
