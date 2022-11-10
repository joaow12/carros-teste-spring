package br.com.magnasistemas.carros.controller;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.magnasistemas.carros.entity.Carro;
import br.com.magnasistemas.carros.entity.Usuario;
import br.com.magnasistemas.carros.enums.Marca;
import br.com.magnasistemas.carros.repository.CarroRepository;
import br.com.magnasistemas.carros.repository.UsuarioRepository;
import br.com.magnasistemas.carros.service.UsuarioService;
import br.com.magnasistemas.carros.vo.CarroVO;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CarroControllerTest {

	@Autowired
	private CarroRepository repository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private UsuarioService service;

	@BeforeAll
	void start() {
		repository.deleteAll();
		usuarioRepository.save(new Usuario("Teste", "00000000011", "123456789"));
	}

	@Test
	@Order(1)
	void deveCadastrarUmCarro() {
		HttpEntity<CarroVO> requisicao = new HttpEntity<CarroVO>(
				new CarroVO("Golf", "987HJY", Marca.Volkswagen, "00000000011"));
		ResponseEntity<CarroVO> resposta = testRestTemplate.exchange("/carro/", HttpMethod.POST, requisicao,
				CarroVO.class);

		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		assertEquals(requisicao.getBody().getNome(), resposta.getBody().getNome());
		assertEquals(requisicao.getBody().getPlaca(), resposta.getBody().getPlaca());
		assertEquals(requisicao.getBody().getMarca(), resposta.getBody().getMarca());

	}

	@Test
	@Order(2)
	void naoDeveCadastrarDoisCarrosComAMesmaPlaca() {

		service.cadastrarCarro(new CarroVO("Civic", "000HHH", Marca.Honda, "00000000011"));
		HttpEntity<CarroVO> requisicao = new HttpEntity<CarroVO>(
				new CarroVO("Golf", "000HHH", Marca.Volkswagen, "00000000011"));
		ResponseEntity<CarroVO> resposta = testRestTemplate.exchange("/carro/", HttpMethod.POST, requisicao,
				CarroVO.class);

		assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());

	}

	@Test
	@Order(3)
	void deveAtualizarUmCarro() {
		Optional<Carro> c = service.cadastrarCarro(new CarroVO("Teste", "010HHH", Marca.Ford, "00000000011"));

		if (c.isPresent()) {

			CarroVO carro = new CarroVO("Teste 2", "110HHH", Marca.Ford, "00000000011");

			HttpEntity<CarroVO> requisicao = new HttpEntity<CarroVO>(carro);

			ResponseEntity<CarroVO> resposta = testRestTemplate.exchange("/carro/{id}", HttpMethod.PUT, requisicao,
					CarroVO.class, c.get().getId());

			assertEquals(HttpStatus.OK, resposta.getStatusCode());
			assertEquals(carro.getNome(), resposta.getBody().getNome());
			assertEquals(carro.getPlaca(), resposta.getBody().getPlaca());

		} else {
			System.out.println("Nenhum valor");
		}
	}

	@Test
	@Order(4)
	void DeveDeletarUmCarroDoBanco() {
		Optional<Carro> c = service.cadastrarCarro(new CarroVO("Teste 3", "019HHH", Marca.Citroen, "00000000011"));

		if (c.isPresent()) {

			ResponseEntity<HttpStatus> resposta = testRestTemplate.exchange("/carro/{id}", HttpMethod.DELETE, null,
					HttpStatus.class, c.get().getId());

			assertEquals(HttpStatus.OK, resposta.getStatusCode());

		} else {
			System.out.println("Nenhum valor");
		}
	}

	@Test
	@Order(5)
	void deveRetornarUmCarroPeloID() {
		Optional<Carro> c = service.cadastrarCarro(new CarroVO("Teste 4", "98HY1", Marca.Citroen, "00000000011"));

		if (c.isPresent()) {

			ResponseEntity<CarroVO> resposta = testRestTemplate.exchange("/carro/{id}", HttpMethod.GET, null,
					CarroVO.class, c.get().getId());

			assertEquals(HttpStatus.OK, resposta.getStatusCode());
			assertEquals(c.get().getNome(), resposta.getBody().getNome());
		} else {
			System.out.println("Nenhum valor");
		}
	}

	@Test
	@Order(6)
	void deveRetornarUmCarroPelaPlaca() {
		Optional<CarroVO> c = service.buscarPorPlaca("98HY1");

		if (c.isPresent()) {
			ResponseEntity<CarroVO> resposta = testRestTemplate.exchange("/carro/placa/{placa}", HttpMethod.GET, null,
					CarroVO.class, c.get().getPlaca());

			assertEquals(HttpStatus.OK, resposta.getStatusCode());
			assertEquals(c.get().getNome(), resposta.getBody().getNome());
		} else {
			System.out.println("Nenhum valor");
		}
	}

	@Test
	@Order(7)
	void deveDeletarUmCarroPeloID() {
		Long id = 3L;
		Optional<CarroVO> c = service.buscarPorIdCarro(id);

		if (c.isPresent()) {
			ResponseEntity<CarroVO> resposta = testRestTemplate.exchange("/carro/{id}", HttpMethod.DELETE, null,
					CarroVO.class, id);

			assertEquals(HttpStatus.OK, resposta.getStatusCode());
		} else {
			System.out.println("Nenhum valor");
		}
	}

}
