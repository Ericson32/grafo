package br.radixeng.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.radixeng.model.Grafo;
import br.radixeng.service.GrafoService;
import br.radixeng.vo.EnvioCalculoRota;
import br.radixeng.vo.RetornoRotasDisponiveis;

@RestController
public class GrafoController {
	
	@Autowired
	GrafoService grafoService;
	
	@PostMapping(produces="application/json")
	@RequestMapping("/graph")
	public @ResponseBody ResponseEntity<Grafo> salvarGrafo(@RequestBody Grafo grafo) {
		return new ResponseEntity<Grafo>(grafoService.salvarGrafo(grafo), HttpStatus.CREATED);
	}
	
	@GetMapping(produces="application/json")
	@RequestMapping(method = RequestMethod.GET, value = "/graph/{graphId}")
	public @ResponseBody ResponseEntity<Grafo> recuperarGrafo(@PathVariable("graphId") String grafoId) {
		
		if(grafoId == null ||grafoId.trim().equals("")) {
			return new ResponseEntity<Grafo>(new Grafo(),HttpStatus.NOT_FOUND);
		}
		
		BigDecimal id = new BigDecimal(grafoId);
		Grafo grafo = null;
				
		try {
			grafo = grafoService.recuperarGrafo(id);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		if(grafo == null) {
			return new ResponseEntity<Grafo>(grafo, HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<Grafo>(grafo, HttpStatus.OK);
		}
	}
	
	@PostMapping(produces="application/json")
	@RequestMapping(method = RequestMethod.POST, value = "/routes/from/{town1}/to/{town2}", params="maxStops")
	public @ResponseBody ResponseEntity<List<RetornoRotasDisponiveis>> encontrarRotas(@RequestBody Grafo grafo, @PathVariable("town1") Character town1, @PathVariable("town2") Character town2, @RequestParam("maxStops") int maxStops) {
		
		List<RetornoRotasDisponiveis> retorno = grafoService.buscarRotas(grafo, town1, town2, maxStops);
		return new ResponseEntity<List<RetornoRotasDisponiveis>>(retorno, HttpStatus.OK);
	}	
	
	@PostMapping(produces="application/json")
	@RequestMapping(method = RequestMethod.POST, value = "/routes/{grafoId}/from/{town1}/to/{town2}", params="maxStops")
	public @ResponseBody ResponseEntity<List<RetornoRotasDisponiveis>> encontrarRotas(@PathVariable("grafoId") String grafoId , @PathVariable("town1") Character town1, @PathVariable("town2") Character town2, @RequestParam("maxStops") int maxStops) {
		
		if(grafoId == null ||grafoId.trim().equals("")) {
			return new ResponseEntity<List<RetornoRotasDisponiveis>>(new ArrayList<RetornoRotasDisponiveis>(),HttpStatus.NOT_FOUND);
		}
		
		BigDecimal id = new BigDecimal(grafoId);
		Grafo grafo = null;
				
		try {
			grafo = grafoService.recuperarGrafo(id);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}		
		
		if(grafo == null) {
			return new ResponseEntity<List<RetornoRotasDisponiveis>>(new ArrayList<RetornoRotasDisponiveis>(),HttpStatus.NOT_FOUND);
		}
		else {
			List<RetornoRotasDisponiveis> retorno = grafoService.buscarRotas(grafo, town1, town2, maxStops);
			return new ResponseEntity<List<RetornoRotasDisponiveis>>(retorno, HttpStatus.OK);
		}
	}
	
	@PostMapping(produces="application/json")
	@RequestMapping(method = RequestMethod.POST, value = "/distance")
	public @ResponseBody ResponseEntity<Integer> calculaDistancia(@RequestBody EnvioCalculoRota calculoRota) {
				
		return new ResponseEntity<Integer>(grafoService.calcularDistancia(calculoRota), HttpStatus.OK); 
	}
	
	@PostMapping(produces="application/json")
	@RequestMapping(method = RequestMethod.POST, value = "/distance/{grafoId}")
	public @ResponseBody ResponseEntity<Integer> calculaDistancia(@RequestBody EnvioCalculoRota calculoRota, @PathVariable("grafoId") String grafoId) {
		
		if(grafoId == null ||grafoId.trim().equals("")) {
			return new ResponseEntity<Integer>(-1,HttpStatus.NOT_FOUND);
		}
		
		BigDecimal id = new BigDecimal(grafoId);
		Grafo grafo = null;
				
		try {
			grafo = grafoService.recuperarGrafo(id);
			calculoRota.setRotas(grafo != null ? grafo.getRotas() : null);
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}		
		
		if(grafo == null) {
			return new ResponseEntity<Integer>(-1,HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<Integer>(grafoService.calcularDistancia(calculoRota), HttpStatus.OK); 
		}
				
	}	
	
	@PostMapping(produces="application/json")
	@RequestMapping(method = RequestMethod.POST, value = "/distance/from/{town1}/to/{town2}")
	public @ResponseBody ResponseEntity<RetornoRotasDisponiveis> buscarMenorDistancia(@RequestBody Grafo grafo, @PathVariable("town1") Character town1, @PathVariable("town2") Character town2) {
		
		RetornoRotasDisponiveis retorno = grafoService.buscarMenorDistancia(grafo, town1, town2);
		return new ResponseEntity<RetornoRotasDisponiveis>(retorno, HttpStatus.OK);
	}
	
	@PostMapping(produces="application/json")
	@RequestMapping(method = RequestMethod.POST, value = "/distance/{grafoId}/from/{town1}/to/{town2}")
	public @ResponseBody ResponseEntity<RetornoRotasDisponiveis> buscarMenorDistancia(@PathVariable("grafoId") String grafoId, @PathVariable("town1") Character town1, @PathVariable("town2") Character town2) {
		
		if(grafoId == null ||grafoId.trim().equals("")) {
			return new ResponseEntity<RetornoRotasDisponiveis>(new RetornoRotasDisponiveis(),HttpStatus.NOT_FOUND);
		}
		
		BigDecimal id = new BigDecimal(grafoId);
		Grafo grafo = null;
				
		try {
			grafo = grafoService.recuperarGrafo(id);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}		
		
		if(grafo == null) {
			return new ResponseEntity<RetornoRotasDisponiveis>(new RetornoRotasDisponiveis(),HttpStatus.NOT_FOUND);
		}
		else {
			RetornoRotasDisponiveis retorno = grafoService.buscarMenorDistancia(grafo, town1, town2);
			return new ResponseEntity<RetornoRotasDisponiveis>(retorno, HttpStatus.OK);
		}
		
	}
	
}
