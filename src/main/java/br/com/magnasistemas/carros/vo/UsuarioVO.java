package br.com.magnasistemas.carros.vo;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

public class UsuarioVO {

	@NotBlank
	private String nome;
	@NotBlank
	@Length(max = 11, min = 11)
	private String cpf;
	@NotBlank
	@Length(max = 8, min = 4)
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
