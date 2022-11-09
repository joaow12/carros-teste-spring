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
import br.com.magnasistemas.carros.vo.CarroVO;

@RestController
@RequestMapping("/carro")
public class CarroController {

	@Autowired
	private UsuarioService service;

	@GetMapping
	public ResponseEntity<Page<CarroVO>> mostrarTodos(@PageableDefault(size = 5) Pageable page) {
		return ResponseEntity.ok(service.mostrarTodosCarros(page));
	}

	@GetMapping("/{id}")
	public ResponseEntity<CarroVO> buscaPorID(@PathVariable Long id) {
		return service.buscarPorIdCarro(id)
				.map(r -> ResponseEntity.ok(r))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("placa/{placa}")
	public ResponseEntity<CarroVO> buscaPorPlaca(@PathVariable String placa) {
		return service.buscarPorPlaca(placa)
				.map(r -> ResponseEntity.ok(r))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<CarroVO> cadastrar(@RequestBody @Valid CarroVO carro){
		return service.cadastrarCarro(carro)
				.map(r -> ResponseEntity.status(HttpStatus.CREATED).body(carro))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CarroVO> atualizar(@PathVariable Long id, @RequestBody @Valid CarroVO carro){
		return service.atualizarCadastroCarro(id, carro)
				.map(r -> ResponseEntity.status(HttpStatus.OK).body(r))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deletar(@PathVariable Long id) {
		return service.deletarCarro(id);
	}

}
