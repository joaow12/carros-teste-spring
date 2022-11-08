package br.com.magnasistemas.carros.service;

import java.util.Optional;
import java.util.stream.Collectors;

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
import br.com.magnasistemas.carros.vo.UsuarioVO;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private CarroRepository carroRepository;

	public Page<UsuarioVO> mostrarTodosUsuarios(Pageable page) {
		return repository.findAll(page).map(this::converterUsuarioEntityParaVO);
	}

	public Page<CarroVO> mostrarTodosCarros(Pageable page) {
		return carroRepository.findAll(page).map(this::converterCarroEntityParaVO);
	}

	public Optional<CarroVO> buscarPorIdCarro(Long id) {
		Optional<Carro> c = carroRepository.findById(id);
		return verificarExistenciaCarro(c);
	}

	public Optional<UsuarioVO> buscarPorIdUsuario(Long id) {
		Optional<Usuario> u = repository.findById(id);
		return verificarExistenciaUsuario(u);
	}

	public Optional<CarroVO> buscarPorPlaca(String placa) {
		Optional<Carro> c = carroRepository.findByPlaca(placa);
		return verificarExistenciaCarro(c);
	}

	public Optional<UsuarioVO> buscarPorCpf(String cpf) {
		Optional<Usuario> u = repository.findByCpf(cpf);
		return verificarExistenciaUsuario(u);
	}

	private Optional<CarroVO> verificarExistenciaCarro(Optional<Carro> c) {
		if (c.isPresent()) {
			return Optional.of(converterCarroEntityParaVO(c.get()));
		} else {
			return Optional.empty();
		}
	}

	private Optional<UsuarioVO> verificarExistenciaUsuario(Optional<Usuario> u) {
		if (u.isPresent()) {
			return Optional.of(converterUsuarioEntityParaVO(u.get()));
		} else {
			return Optional.empty();
		}
	}

	public UsuarioVO mostrarUsuarioVO(Usuario u) {
		return converterUsuarioEntityParaVO(u);
	}

	public CarroVO mostrarCarroVO(Carro c) {
		return converterCarroEntityParaVO(c);
	}

	private UsuarioVO converterUsuarioEntityParaVO(Usuario usuario) {
		UsuarioVO vo = new UsuarioVO();
		vo.setNome(usuario.getNome());
		vo.setCpf(usuario.getCpf());
		vo.setSenha(usuario.getSenha());
		vo.setCarros(usuario.getCarros().stream().map(this::converterCarroEntityParaVO).collect(Collectors.toList()));
		return vo;
	}

	private CarroVO converterCarroEntityParaVO(Carro carro) {
		CarroVO vo = new CarroVO();
		vo.setNome(carro.getNome());
		vo.setPlaca(carro.getPlaca());
		vo.setModelo(carro.getModelo());
		vo.setUsuario(carro.getUsuario().getNome());
		return vo;
	}

	public Optional<Carro> cadastrarCarro(CarroVO c) {
		if (carroRepository.findByPlaca(c.getPlaca()).isPresent()) {
			return Optional.empty();
		} else {
			Carro carro = new Carro();
			carro.setNome(c.getNome());
			carro.setModelo(c.getModelo());
			carro.setPlaca(c.getPlaca());
			Optional<Usuario> usuario = repository.findByCpf(c.getUsuario());
			carro.setUsuario(usuario.get());

			return Optional.of(carroRepository.save(carro));
		}
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
			c.get().setModelo(carro.getModelo());
			carroRepository.save(c.get());

			return Optional.of(converterCarroEntityParaVO(c.get()));
		}
	}

	public ResponseEntity<HttpStatus> deletarCarro(Long id) {
		carroRepository.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	public Optional<Usuario> cadastrarUsuario(UsuarioVO usuario) {
		if (repository.findByCpf(usuario.getCpf()).isPresent()) {
			return Optional.empty();
		} else {
			Usuario u = new Usuario();
			u.setCpf(usuario.getCpf());
			u.setNome(usuario.getNome());
			u.setSenha(usuario.getSenha());
			
			return Optional.of(repository.save(u));
			
		}
	}

	public ResponseEntity<HttpStatus> deletarUsuario(Long id) {
		repository.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	public Optional<UsuarioVO> atualizarCadastroUsuario(Long id, UsuarioVO usuario) {
		Optional<Usuario> u = repository.findById(id);
		if(u.isEmpty()) {
			return Optional.empty();
		}
		
		else if (repository.findByCpf(usuario.getCpf()).isPresent()) {
			return Optional.empty();
		}
		
		else {
			u.get().setNome(usuario.getNome());
			u.get().setCpf(usuario.getCpf());
			u.get().setSenha(usuario.getSenha());
			repository.save(u.get());
			
			return Optional.of(converterUsuarioEntityParaVO(u.get()));
		}
	}

}
