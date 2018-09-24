package br.radixeng.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Grafo implements Serializable{
	
	private static final long serialVersionUID = -4802358653516181808L;

	@Id
    @GeneratedValue
	@Column(name="grafo_id")
	private BigDecimal grafoId;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn
	private List<Rota> rotas;
	
	public BigDecimal getGrafoId() {
		return grafoId;
	}
	public void setGrafoId(BigDecimal grafoId) {
		this.grafoId = grafoId;
	}
	public List<Rota> getRotas() {
		return rotas;
	}
	public void setRotas(List<Rota> rotas) {
		this.rotas = rotas;
	}
	
}
