package br.com.magnasistemas.carros.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.magnasistemas.carros.entity.Carro;
import br.com.magnasistemas.carros.entity.Usuario;

public interface CarroRepository extends JpaRepository<Carro, Long>{
	
	Optional<Carro> findByPlaca(String placa);
}
