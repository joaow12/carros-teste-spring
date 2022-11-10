package br.com.magnasistemas.carros.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.magnasistemas.carros.enums.Marca;

@Entity
@Table(name = "TB_CARRO")
public class Carro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String nome;

	@NotBlank
	private String placa;

	@Enumerated(value = EnumType.STRING)
	@NotNull
	private Marca marca;

	@ManyToOne
	private Usuario usuario;

	public Carro(@NotBlank String nome, @NotBlank String placa, @NotBlank Marca marca) {
		this.nome = nome;
		this.placa = placa;
		this.marca = marca;
	}

	public Carro() {
	}

	public Carro(Long id, @NotBlank String nome, @NotBlank String placa, @NotNull Marca marca) {
		this.id = id;
		this.nome = nome;
		this.placa = placa;
		this.marca = marca;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
