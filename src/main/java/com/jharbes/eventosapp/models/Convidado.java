package com.jharbes.eventosapp.models;





import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;



@Entity
public class Convidado {
	
	@Id
	@NotNull(message = "Campo não pode estar vazio")
	@Size(min=2, max=100, message="Tem de ter pelo menos 2 letras")
	@NotEmpty
	private String rg;
	
	@NotEmpty
	@NotNull(message = "Campo não pode estar vazio")
	@Size(min=2, max=100, message="Tem de ter pelo menos 2 letras")
	private String nomeConvidado;

	@ManyToOne
	@NotEmpty
	private Evento evento;

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getNomeConvidado() {
		return nomeConvidado;
	}

	public void setNomeConvidado(String nomeConvidado) {
		this.nomeConvidado = nomeConvidado;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

}
