package br.com.magnasistemas.carros.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.magnasistemas.carros.entity.Carro;
import br.com.magnasistemas.carros.entity.Usuario;
import br.com.magnasistemas.carros.repository.CarroRepository;
import br.com.magnasistemas.carros.repository.UsuarioRepository;
import br.com.magnasistemas.carros.vo.CarroVO;

@Service
public class CarroService {

	@Autowired
	private CarroRepository carroRepository;

	@Autowired
	private UsuarioRepository repository;

	public CarroVO mostrarCarroVO(Carro c) {
		return converterCarroEntityParaVO(c);
	}

	protected CarroVO converterCarroEntityParaVO(Carro carro) {
		CarroVO vo = new CarroVO();
		vo.setNome(carro.getNome());
		vo.setPlaca(carro.getPlaca());
		vo.setMarca(carro.getMarca());
		vo.setUsuario(carro.getUsuario().getNome());
		return vo;
	}

	public Page<CarroVO> mostrarTodosCarros(Pageable page) {
		return carroRepository.findAll(page).map(this::converterCarroEntityParaVO);
	}

	public Optional<CarroVO> buscarPorIdCarro(Long id) {
		Optional<Carro> c = Optional.of(carroRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Não foi possivel encontrar o carro com esse ID")));
		return verificarExistenciaCarro(c);
	}

	public ResponseEntity<HttpStatus> deletarCarro(Long id) {
		carroRepository.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).body(null);

	}

	public Optional<CarroVO> atualizarCadastroCarro(Long id, CarroVO carro) {
		Optional<Carro> c = carroRepository.findById(id);
		if (c.isEmpty()) {
			return Optional.empty();
		}

		else if (carroRepository.findByPlaca(carro.getPlaca()).isPresent()) {
			return Optional.empty();
		}

		else {
			c.get().setNome(carro.getNome());
			c.get().setPlaca(carro.getPlaca());
			c.get().setMarca(carro.getMarca());
			carroRepository.save(c.get());

			return Optional.of(converterCarroEntityParaVO(c.get()));
		}
	}

	public Optional<Carro> cadastrarCarro(CarroVO c) {
		if (carroRepository.findByPlaca(c.getPlaca()).isPresent()) {
			return Optional.empty();
		} else {
			Carro carro = new Carro();
			carro.setNome(c.getNome());
			carro.setMarca(c.getMarca());
			carro.setPlaca(c.getPlaca());
			Optional<Usuario> usuario = repository.findByCpf(c.getUsuario());
			carro.setUsuario(usuario.get());

			return Optional.of(carroRepository.save(carro));
		}
	}

	public Optional<CarroVO> buscarPorPlaca(String placa) {
		Optional<Carro> c = Optional.of(carroRepository.findByPlaca(placa).orElseThrow(
				() -> new RuntimeException("Não foi possivel encontrar " + "um carro com essa placa: " + placa)));

		return verificarExistenciaCarro(c);
	}

	private Optional<CarroVO> verificarExistenciaCarro(Optional<Carro> c) {
		if (c.isPresent()) {
			return Optional.of(converterCarroEntityParaVO(c.get()));
		} else {
			return Optional.empty();
		}
	}
}
