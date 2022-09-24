package br.pbmulesoft.api.controller.form;

import br.pbmulesoft.api.modelo.Estado;

public class EstadoForm {

	private String nome; //estado
	private String regiao;
	private Integer populacao;
	private String capital;
	private Integer area;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getRegiao() {
		return regiao;
	}
	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}
	public Integer getPopulacao() {
		return populacao;
	}
	public void setPopulacao(Integer populacao) {
		this.populacao = populacao;
	}
	public String getCapital() {
		return capital;
	}
	public void setCapital(String capital) {
		this.capital = capital;
	}
	public Integer getArea() {
		return area;
	}
	public void setArea(Integer area) {
		this.area = area;
	}
	public Estado converter() {
		return new Estado(nome, regiao, populacao, capital, area);
	}

}
