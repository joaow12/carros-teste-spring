package br.com.magnasistemas.carros.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.magnasistemas.carros.entity.Usuario;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository repository;
	
	@BeforeAll
	void start() {
		repository.save(new Usuario("Teste 1", "00000000011", "1234"));
		repository.save(new Usuario("Teste 2", "00000000012", "0000"));
		repository.save(new Usuario("Teste 3", "00000000013", "4321"));
	}

	@Test
	void deveRetornarUmUsuarioPeloID() {
		Optional<Usuario> u = repository.findById(1L);
		assertNotNull(u);
		assertEquals(u.get().getNome(), "Teste 1");
	}
	
	@Test
	void deveRetornarUmUsuarioPeloCPF() {
		Optional<Usuario> u = repository.findByCpf("00000000012");
		assertNotNull(u);
		assertEquals(u.get().getNome(), "Teste 2");
	}
	
	@Test
	void deveRetornarTodosOsUsuarios() {
		List<Usuario> u = repository.findAll();
		assertNotNull(u);
		assertEquals(u.size(), 3);
		assertEquals(u.get(0).getNome(), "Teste 1");
		assertEquals(u.get(1).getNome(), "Teste 2");
		assertEquals(u.get(2).getNome(), "Teste 3");
		
	}

}
