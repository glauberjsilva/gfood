package com.glauber.gfood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.glauber.gfood.domain.model.FormaPagamento;
import com.glauber.gfood.domain.repository.FormaPagamentoRepository;

@RestController
@RequestMapping("/formasPagamento")
public class FormaPagamentoController {
	
	@Autowired
	private FormaPagamentoRepository repository;
	
	@GetMapping()
	public List<FormaPagamento> listar() {
		return repository.findAll();
	}

}
