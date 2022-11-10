package br.com.magnasistemas.carros.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.magnasistemas.carros.entity.Usuario;
import br.com.magnasistemas.carros.repository.UsuarioRepository;
import br.com.magnasistemas.carros.vo.UsuarioVO;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private CarroService service;

	public Page<UsuarioVO> mostrarTodosUsuarios(Pageable page) {
		return repository.findAll(page).map(this::converterUsuarioEntityParaVO);
	}

	public Optional<UsuarioVO> buscarPorIdUsuario(Long id) {
		Optional<Usuario> u = Optional.of(repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Não foi possivel encontrar a pessoa com esse ID")));
		return verificarExistenciaUsuario(u);
	}

	public Optional<UsuarioVO> buscarPorCpf(String cpf) {
		Optional<Usuario> u = Optional.of(repository.findByCpf(cpf).orElseThrow(
				() -> new RuntimeException("Não foi possivel encontrar uma" + " pessoa com esse CPF: " + cpf)));

		return verificarExistenciaUsuario(u);
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

	private UsuarioVO converterUsuarioEntityParaVO(Usuario usuario) {
		UsuarioVO vo = new UsuarioVO();
		vo.setNome(usuario.getNome());
		vo.setCpf(usuario.getCpf());
		vo.setSenha(usuario.getSenha());
		vo.setCarros(usuario.getCarros().stream().map(t -> service.converterCarroEntityParaVO(t))
				.collect(Collectors.toList()));
		return vo;
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
		if (u.isEmpty()) {
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
