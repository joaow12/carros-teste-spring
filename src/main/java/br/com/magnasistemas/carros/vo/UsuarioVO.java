package br.com.magnasistemas.carros.vo;

import java.util.List;

public class UsuarioVO {

	private String nome;
	private String cpf;
	private String senha;
	private List<CarroVO> carros;

	public UsuarioVO(String nome, String cpf, String senha, List<CarroVO> carros) {
		this.nome = nome;
		this.cpf = cpf;
		this.senha = senha;
		this.carros = carros;
	}
	
	public UsuarioVO() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public List<CarroVO> getCarros() {
		return carros;
	}

	public void setCarros(List<CarroVO> carros) {
		this.carros = carros;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
