package br.radixeng.vo;

import java.util.ArrayList;
import java.util.List;

import br.radixeng.model.Rota;

public class EnvioCalculoRota {
	List<String> path;
	List<Rota> rotas;
	
	public List<String> getPath() {
		return path;
	}
	public void setPath(List<String> path) {
		this.path = path;
	}
	public List<Rota> getRotas() {
		return rotas;
	}
	public void setRotas(List<Rota> rotas) {
		this.rotas = rotas;
	}
	
	public void setPathString(String path) {
		this.path = new ArrayList<String>();
		for(int i = 0; i < path.length(); i++) {
			this.path.add(new Character(path.charAt(i)).toString());
		}
		
	}
}
