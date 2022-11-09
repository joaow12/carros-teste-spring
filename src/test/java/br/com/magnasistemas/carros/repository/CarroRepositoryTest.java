package br.com.magnasistemas.carros.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.magnasistemas.carros.entity.Carro;
import br.com.magnasistemas.carros.enums.Marca;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CarroRepositoryTest {

	@Autowired
	private CarroRepository repository;
	
	@BeforeAll
	void start() {
		repository.save(new Carro("Civic", "000HHH", Marca.Honda));
		repository.save(new Carro("Up", "000FFF", Marca.Honda));
		repository.save(new Carro("C3", "111GHD", Marca.Renault));
	}
	
	@Test
	void buscarCarroPeloID() {
		Optional<Carro> c = repository.findById(1L);
		assertNotNull(c);
		assertEquals("Civic", c.get().getNome());
	}
	
	@Test
	void buscarCarroPelaPlaca() {
		Optional<Carro> c = repository.findByPlaca("000HHH");
		assertNotNull(c);
		assertEquals("Civic", c.get().getNome());
	}
	
	@Test
	void deveVoltarTodosCarros() {
		List<Carro> c = repository.findAll();
		assertEquals(3, c.size());
		assertEquals("Civic", c.get(0).getNome());
		assertEquals("Up", c.get(1).getNome());
		assertEquals("C3", c.get(2).getNome());
		
	}

}
