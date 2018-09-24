package br.radixeng.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Rota {
	
	@Id
	@GeneratedValue
	private BigDecimal id;
	private char source;
	private char target;
	private Integer distance;
	
	@ManyToOne()
	@JoinColumn(name="grafo_id")
	private Grafo grafo;
	
	public Rota() {
		
	}
	
	public Rota(char source, char target, Integer distance) {
		this.source = source;
		this.target = target;
		this.distance = distance;
	}
	
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}

	public char getSource() {
		return source;
	}

	public void setSource(char source) {
		this.source = source;
	}

	public char getTarget() {
		return target;
	}

	public void setTarget(char target) {
		this.target = target;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public Grafo getGrafo() {
		return grafo;
	}

	public void setGrafo(Grafo grafo) {
		this.grafo = grafo;
	}
	
}
