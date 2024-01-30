package com.glauber.gfood;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.math.BigDecimal;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.glauber.gfood.domain.model.Cidade;
import com.glauber.gfood.domain.model.Cozinha;
import com.glauber.gfood.domain.model.Endereco;
import com.glauber.gfood.domain.model.Estado;
import com.glauber.gfood.domain.model.Restaurante;
import com.glauber.gfood.domain.repository.RestauranteRepository;
import com.glauber.gfood.domain.service.CadastroCidadeService;
import com.glauber.gfood.domain.service.CadastroCozinhaService;
import com.glauber.gfood.domain.service.CadastroEstadoService;
import com.glauber.gfood.domain.service.CadastroRestauranteService;
import com.glauber.gfood.util.DatabaseCleaner;
import com.glauber.gfood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteApiIT {

	private static final String VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE = "Violação de regra de negócio";
	private static final String DADOS_INVALIDOS_PROBLEM_TITLE = "Dados inválidos";
	private static final int RESTAURANTE_ID_INEXISTENTE = 10000;
	
	@LocalServerPort
	private int port;
	
    @Autowired
    private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	private int qtdeRestauranteCadastrado;
	private String jsonRestauranteCorreto;
	private String jsonRestauranteSemFrete;
	private String jsonRestauranteSemCozinha;
	private String jsonRestauranteComCozinhaInexistente;
	private Restaurante restauranteCozinhaMineira;
	
	/**
	 * Metodo de callback executado antes de iniciar os testes
	 * 
	 */
	@BeforeEach
	private void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/restaurantes";
		
		jsonRestauranteCorreto = ResourceUtils.getContentFromResource("/json/correto/restaurante-new-york-barbecue.json");
		jsonRestauranteSemFrete = ResourceUtils.getContentFromResource("/json/correto/restaurante-new-york-barbecue-sem-frete.json");
		jsonRestauranteSemCozinha = ResourceUtils.getContentFromResource("/json/correto/restaurante-new-york-barbecue-sem-cozinha.json");
		jsonRestauranteComCozinhaInexistente = ResourceUtils.getContentFromResource("/json/correto/restaurante-new-york-barbecue-com-cozinha-inexistente.json");

		this.databaseCleaner.clearTables();
		this.prepararDados();
	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarRestaurantes() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarQuantidadeCorretaDeRestaurante_QuandoConsultarRestaurantes() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", Matchers.hasSize(qtdeRestauranteCadastrado));
	}
	 
	@Test
	public void deveRetornarStatus201_QuandoCadastrarRestaurante() {
		given()
			.body(jsonRestauranteCorreto)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarStatus400_QuandoCadastrarRestauranteSemTaxaFrete() {
		given()
			.body(jsonRestauranteSemFrete)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
	}
	
	@Test
	public void deveRetornarStatus400_QuandoCadastrarRestauranteSemCozinha() {
		given()
			.body(jsonRestauranteSemCozinha)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
	}
	
	@Test
	public void deveRetornarStatus400_QuandoCadastrarRestauranteComCozinhaInexistente() {
		given()
        	.body(jsonRestauranteComCozinhaInexistente)
        	.contentType(ContentType.JSON)
        	.accept(ContentType.JSON)
        .when()
        	.post()
        .then()
        	.statusCode(HttpStatus.BAD_REQUEST.value())
        	.body("title", equalTo(VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE));
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarRestauranteInexistente() {
		given()
			.pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{restauranteId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarRestauranteExistente() {
		given()
			.pathParam("restauranteId", restauranteCozinhaMineira.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{restauranteId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo(restauranteCozinhaMineira.getNome()));
	}
	
	private void prepararDados() {
		Estado estadoMG = prepararDadosEstado("Minas Gerais");
		Cidade cidadeUberlandia = prepararDadosCidade("Uberlandia", estadoMG);
		Endereco endereco1 = prepararDadosEndereco("Rua Restaurante 1", "1111", "Bairro 1", "11111-111", cidadeUberlandia);
		Cozinha cozinhaMineira = prepararDadosCozinha("Mineira");

		restauranteCozinhaMineira = prepararDadosRestaurante("Cozinha Mineira 1", BigDecimal.ZERO);
		restauranteCozinhaMineira.setCozinha(cozinhaMineira);
		restauranteCozinhaMineira.setEndereco(endereco1);
		cadastroRestaurante.salvar(restauranteCozinhaMineira);
		
		
		Estado estadoGO = prepararDadosEstado("Catalão");
		Cidade cidadeCatalao = prepararDadosCidade("Goiânia", estadoGO);
		Endereco endereco2 = prepararDadosEndereco("Rua Restaurante 2", "2", "Bairro 2", "22222-2", cidadeCatalao);
		Cozinha cozinhaGoiana = prepararDadosCozinha("Goiana");
		
		Restaurante RestauranteBomApetite = prepararDadosRestaurante("Bom Apetite", new BigDecimal(10));
		RestauranteBomApetite.setCozinha(cozinhaGoiana);
		RestauranteBomApetite.setEndereco(endereco2);
		cadastroRestaurante.salvar(RestauranteBomApetite);
		
		qtdeRestauranteCadastrado = (int) restauranteRepository.count();
	}

	private Restaurante prepararDadosRestaurante(String nomeRestaurante, BigDecimal taxaFrete) {
		Restaurante restaurante = new Restaurante();
		restaurante.setNome("Cozinha Mineira");
		restaurante.setTaxaFrete(BigDecimal.valueOf(1000));
		
		return restaurante;
	}

	private Cozinha prepararDadosCozinha(String nome) {
		Cozinha cozinha = new Cozinha();
		cozinha.setNome(nome);
		return cadastroCozinha.salvar(cozinha);
	}

	private Endereco prepararDadosEndereco(String Logradouro, String numero, String bairro, String cep, Cidade cidadeUberlandia) {
		Endereco endereco = new Endereco();
		endereco.setLogradouro(Logradouro);
		endereco.setNumero(numero);
		endereco.setBairro(bairro);
		endereco.setCidade(cidadeUberlandia);
		endereco.setCep(cep);
		
		return endereco;
	}

	private Cidade prepararDadosCidade(String nome, Estado estadoMg) {
		Cidade cidade = new Cidade();
		cidade.setNome(nome);
		cidade.setEstado(estadoMg);
		
		return cadastroCidade.salvar(cidade);
	}

	private Estado prepararDadosEstado(String nome) {
		Estado estado = new Estado();
		estado.setNome(nome);
		
		return cadastroEstado.salvar(estado);
	}

	
}
