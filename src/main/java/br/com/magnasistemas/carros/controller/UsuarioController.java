package br.com.magnasistemas.carros.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.magnasistemas.carros.service.UsuarioService;
import br.com.magnasistemas.carros.vo.UsuarioVO;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService service;
	
	@GetMapping
	public ResponseEntity<Page<UsuarioVO>> mostrarTodos(@PageableDefault(size = 5) Pageable page) {
		return ResponseEntity.ok(service.mostrarTodosUsuarios(page));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioVO> buscaPorID(@PathVariable Long id) {
		return service.buscarPorIdUsuario(id)
				.map(r -> ResponseEntity.ok(r))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/cpf/{cpf}")
	public ResponseEntity<UsuarioVO> buscaPorCpf(@PathVariable String cpf) {
		return service.buscarPorCpf(cpf)
				.map(r -> ResponseEntity.ok(r))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<UsuarioVO> cadastrar(@RequestBody @Valid UsuarioVO usuario) {
		return service.cadastrarUsuario(usuario)
				.map(r -> ResponseEntity.status(HttpStatus.CREATED).body(usuario))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UsuarioVO> atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioVO usuario){
		return service.atualizarCadastroUsuario(id, usuario)
				.map(r -> ResponseEntity.status(HttpStatus.OK).body(r))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deletar(@PathVariable Long id) {
		return service.deletarUsuario(id);
	}
	
	
	
	
}
