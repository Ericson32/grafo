package br.radixeng.vo;

public class RetornoRotasDisponiveis {
	
	private String route;
	private Integer stops;
	private Integer distance;
	
	public RetornoRotasDisponiveis(String route, Integer stops, Integer distance) {
		this.route = route;
		this.stops = stops;
		this.distance = distance;
	}
	
	public RetornoRotasDisponiveis(String route, Integer stops) {
		this.route = route;
		this.stops = stops;
	}
	
	public RetornoRotasDisponiveis() {
		
	}
	
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public Integer getStops() {
		return stops;
	}
	public void setStops(Integer stops) {
		this.stops = stops;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

}
