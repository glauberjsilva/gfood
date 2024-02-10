package com.glauber.gfood;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.glauber.gfood.domain.model.Cozinha;
import com.glauber.gfood.domain.repository.CozinhaRepository;
import com.glauber.gfood.util.DatabaseCleaner;
import com.glauber.gfood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

/**
 * TESTE DE API
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaApiIT {

	@LocalServerPort
	private int port;
	
	private static final Long COZINHA_INEXISTENTE = 100000L;
	
	//Utiliza o flyway para criar a estrutura de dados e 
	//massa de dados para teste do arquivo afterMigrate.sql
	//@Autowired private Flyway flyway;
	
	//Substitui o uso do flyway para criar a massa de dados para testes 
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	//private int nroCozinhasCadastradas;
	
	private Cozinha cozinhaAmericana;
	private int quantidadeCozinhasCadastradas;
	private String jsonCorretoCozinhaChinesa;
	
	/**
	 * Metodo de callback executado antes de iniciar os testes
	 * 
	 */
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
		
		//Executa o script afterMigrate.sql para popular a base antes de cada test
		//flyway.migrate();
		
		databaseCleaner.clearTables();
		prepararDados();
	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value())
		.log().all();;
	}
	
	@Test
	public void deveRetornarQuantidadeCorretaDeCozinhas_QuandoConsultarCozinhas() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()//verifica se retornou a quandidade correta de cozinhas cadastradas
			.body("", hasSize(quantidadeCozinhasCadastradas)); 
			//.body("nome", hasItems("Americana", "Tailandesa"));
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCastrarCozinha() {
		jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResource("/json/correto/cozinha-chinesa.json");
		
		given()
			.body(jsonCorretoCozinhaChinesa)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value())
		.log().all();;
	}
	
	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
		given()
			.pathParam("cozinhaId", cozinhaAmericana.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo(cozinhaAmericana.getNome()))
		.log().all();;
	}
	
	@Test
	public void deveRetornarRespostaEStatus404_QuandoConsultarCozinhaInxistente() {
		given()
			.pathParam("cozinhaId", COZINHA_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value())
		.log().all();;
	}
	
	private void prepararDados() {
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Tailandesa");
		cozinhaRepository.save(cozinha1);
		
		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Americana");
		cozinhaRepository.save(cozinha2);
		
		this.cozinhaAmericana = cozinha2;
		
		this.quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();
		
	}
}
