package br.com.magnasistemas.carros.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.magnasistemas.carros.enums.Marca;

public class CarroVO {

	@NotBlank
	private String nome;
	@NotBlank
	private String placa;
	@NotNull
	private Marca marca;
	@NotBlank
	private String usuario;

	public CarroVO(String nome, String placa, Marca marca, String usuario) {
		this.nome = nome;
		this.placa = placa;
		this.marca = marca;
		this.usuario = usuario;
	}

	public CarroVO() {

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}
