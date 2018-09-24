package br.radixeng.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.radixeng.model.Grafo;
import br.radixeng.model.Rota;
import br.radixeng.repository.GrafoRepository;
import br.radixeng.vo.EnvioCalculoRota;
import br.radixeng.vo.RetornoRotasDisponiveis;

@Service
public class GrafoService {
	
	@Autowired
	GrafoRepository repository;	
	
	public List<RetornoRotasDisponiveis> buscarRotas(Grafo grafo, char source, char target, Integer maxStops){
		List<RetornoRotasDisponiveis> listRotasDestino = new ArrayList<RetornoRotasDisponiveis>();
			
		List<Rota> rotas = new ArrayList<Rota>(grafo.getRotas());
		Rota rotaInicial = null;
		
		boolean fimRota = false;
		while(!fimRota) {
			
			String rota = null;
			rotaInicial = null;

			//recupera a rota inicial
			for (Rota rotaVo : rotas) {
				
				if(rotaVo.getSource() == source) {
					rotaInicial = rotaVo;
					break;
				}
				
			}
			
			if(rotaInicial == null) {
				fimRota = true;
			
			}else{
			
				//remove a rota inicial
				rotas.remove(rotaInicial);
				rota = new Character(rotaInicial.getSource()).toString();
				Character proximaRota = new Character(rotaInicial.getTarget());
				rota += proximaRota;
				Integer stops = 1;
				
				while(proximaRota != null) {
					
					if (maxStops != null && rota.length() > maxStops) {
						break;
					}
					
					proximaRota = buscarProximaRota(rotas, rota);
					
					if(proximaRota != null) {
						rota += proximaRota;
						stops++;
					}
					
					if(proximaRota.equals(target)) {
						listRotasDestino.add(new RetornoRotasDisponiveis(rota, stops));
						break;
					}
					
				}
			}
		}

		return listRotasDestino;
 	}
	
	private Character buscarProximaRota(List<Rota> rotas, String rota) {
		
		for (Rota rotaVo : rotas) {
			
			if(rotaVo.getSource() == rota.charAt(rota.length()-1) && !rota.contains(new Character(rotaVo.getTarget()).toString())) {
				return rotaVo.getTarget();
			}
			
		}
		
		return null;	
	}
	
	
	public Grafo salvarGrafo(Grafo grafo) {
		return repository.save(grafo);
	}
	
	public Grafo recuperarGrafo(BigDecimal grafoId) {
		return repository.findById(grafoId).get();
	}
	
	public Integer calcularDistancia(EnvioCalculoRota calculoRota) {
		
		Integer distancia = 0;
		int rotaPos = 0;
		int qtdStops = calculoRota.getPath().size() - 1;
		Rota ultimaRota = null;
		
		for(int i = 0; i < qtdStops; i++) {
			
			for(Rota rota : calculoRota.getRotas()) {
				
				if(rota.getSource() == calculoRota.getPath().get(rotaPos).charAt(0) && rota.getTarget() == calculoRota.getPath().get(rotaPos + 1).charAt(0)) {
					distancia+= rota.getDistance();
					ultimaRota = rota;
					break;
				}
			}

			rotaPos++;
		}
						
		if(distancia == 0 || ultimaRota == null || ultimaRota.getTarget() != calculoRota.getPath().get(calculoRota.getPath().size()-1).charAt(0)) {
			distancia = -1;	
		}
		
		return distancia;
		
	}
	
	public RetornoRotasDisponiveis buscarMenorDistancia(Grafo grafo, Character town1, Character town2) {
		List<RetornoRotasDisponiveis> listRotas = buscarRotas(grafo, town1, town2, null);
		RetornoRotasDisponiveis menorDistancia = null;
		
		for(RetornoRotasDisponiveis rota : listRotas) {
			EnvioCalculoRota calculoRota = new EnvioCalculoRota();
			calculoRota.setPathString(rota.getRoute());
			calculoRota.setRotas(grafo.getRotas());
			
			rota.setDistance(calcularDistancia(calculoRota));
			
			if(menorDistancia == null || rota.getDistance() < menorDistancia.getDistance()) {
				menorDistancia = rota;
			}
		}
		
		if(town1 == town2) {
			menorDistancia = new RetornoRotasDisponiveis();
			menorDistancia.setRoute(new Character(town1).toString() + town2);
			menorDistancia.setDistance(0);
		}
		
		if(menorDistancia == null) {
			menorDistancia = new RetornoRotasDisponiveis();
			menorDistancia.setRoute(new Character(town1).toString() + town2);
			menorDistancia.setDistance(-1);
		}
		
		return menorDistancia;
	}
	
}
