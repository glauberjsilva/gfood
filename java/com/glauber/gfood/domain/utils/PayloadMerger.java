package com.glauber.gfood.domain.utils;

import java.lang.reflect.Field;
import java.util.Map;

import org.springframework.util.ReflectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glauber.gfood.domain.model.Restaurante;

public class PayloadMerger {

	public <T> T merge(Map<String, Object> dadosOrigem, T objetoDesino, Class<T> T) {
		ObjectMapper objectMapper = new ObjectMapper();
		
		// converte os valores do map para o valor do objeto
		Object objetoOrigem = objectMapper.convertValue(dadosOrigem, T);
		dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
	
			// busca o atributo dentro da classe com o mesmo nome!
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			
			// como o atributo é privado é preciso setar para acessivel
			field.setAccessible(true);
			
			// Seta o valor do atributo com o valor da propriedade.
			Object novoValor = ReflectionUtils.getField(field, objetoOrigem);
			ReflectionUtils.setField(field, objetoDesino, novoValor);
		});
		
		return objetoDesino;
	}
}
