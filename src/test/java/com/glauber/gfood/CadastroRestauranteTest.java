package com.glauber.gfood;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.glauber.gfood.util.ResourceUtils;

@SpringBootTest()
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteTest {

	private static final String DADOS_INVALIDOS_PROBLEM_TITLE = "Dados inválidos";
	
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void shouldMethodArgumentNotValidException_WhenCompoTaxaFreteForNegativo() throws Exception {
    	mockMvc.perform(post("/restaurantes")
	    			.contentType(MediaType.APPLICATION_JSON)
	    			.content(ResourceUtils.getContentFromResource("/json/incorreto/restaurante-taxa-frete-negativo.json"))
	    			.accept(MediaType.APPLICATION_JSON))
    			.andDo(print())
    			.andExpect(status().isBadRequest())
    			.andExpect((content().contentType(MediaType.APPLICATION_JSON)))
    			.andExpect(jsonPath("$.title", is(DADOS_INVALIDOS_PROBLEM_TITLE)));
    }
    
    
    /***
     * TESTE PARA MOCKAR UM UPLOAD DE ARQUIVOS
     */
	/*
	 * @Autowired private WebApplicationContext webApplicationContext;
	 * 
	 * @Test public void shoulReturnStatus200_WhenUpdatePhotoUser() throws Exception
	 * { MockMultipartFile file = new MockMultipartFile( "arquivo", "foto.png",
	 * MediaType.MULTIPART_FORM_DATA_VALUE, "Conteúdo da foto!".getBytes() );
	 * 
	 * MockMvc mockMvc =
	 * MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	 * mockMvc.perform(multipart("/url do serviço").file(file))
	 * .andExpect(status().isOk()); }
	 */
    
}