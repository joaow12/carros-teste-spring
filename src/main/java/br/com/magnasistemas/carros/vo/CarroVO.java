package br.com.magnasistemas.carros.vo;

import br.com.magnasistemas.carros.enums.Modelo;

public class CarroVO {

	private String nome;
	private String placa;
	private Modelo modelo;
	private String usuario;

	public CarroVO(String nome, String placa, Modelo modelo, String usuario) {
		this.nome = nome;
		this.placa = placa;
		this.modelo = modelo;
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

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}
