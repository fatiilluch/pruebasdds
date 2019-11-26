package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import db.EntidadPersistente;

@Entity
@Table(name = "Molde")
public class Molde extends EntidadPersistente{
	
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "idMolde", referencedColumnName = "id")
	private List<TipoDePrenda> tipoDePrendas;
	
	
	
	public Molde() {
		this.tipoDePrendas = new ArrayList<TipoDePrenda>();
	}
	
	public List<TipoDePrenda> getTiposDePrendas(){
		return this.tipoDePrendas;
	}
	
	
	public boolean esZonaMasAbrigada(Categoria  zona) {
		return this.zonaMasAbrigada().equals(zona);
	}
	public int obtenerCapaMaxima() {
		return (int) this.getTiposDePrendas().stream().
				mapToDouble(tipo -> tipo.getNivelDeCapa()).max().getAsDouble();
	}
	
	
	public Boolean esMenorIgualA(int nivelDeAbrigo) {
		return this.getNivelDeAbrigo() <= nivelDeAbrigo;
	}
	
	public TipoDePrenda getTipoDePrendaSegun(Categoria categoria) {
		return this.getTiposDePrendas().stream().
				filter(tipo -> tipo.sosDeCategoria(categoria))
				.collect(Collectors.toList()).get(0);
	}
	
	public TipoDePrenda getCapaSuperior(int nivelCapaSuperior) {
		return this.getTiposDePrendas().stream()
				.filter(tipo -> tipo.getNivelDeCapa() == nivelCapaSuperior)
				.collect(Collectors.toList()).get(0);
	}
	
	public void eliminarTipoDePrenda(TipoDePrenda tipoBuscado) {
		this.tipoDePrendas.removeIf(tipo -> tipo.equals(tipoBuscado));
	}
	public int getNivelDeAbrigo() {
		return this.getTiposDePrendas().stream().
				mapToInt(tipo->tipo.getNivelDeAbrigo()).sum();
	}
	
	public Boolean elNivelDeAbrigoEsIgualA(int nivelDeAbrigo) {
		return this.getNivelDeAbrigo() == nivelDeAbrigo;
	}
	
	public void agregarTipoDePrenda(TipoDePrenda tipo) {
		this.tipoDePrendas.add(tipo);
		
	}
	public boolean tieneTipoDePrenda(TipoDePrenda tipoObj) {
		return this.tipoDePrendas.stream().anyMatch(tipo -> tipo.esIgualNivelDeCapaA(tipoObj.getNivelDeCapa()));
	}

	public int getNivelDeAbrigoSegun(Categoria categoria) {
		return this.getTiposDePrendas().stream().filter(tipo -> tipo.sosDeCategoria(categoria)).mapToInt(tipo->tipo.getNivelDeAbrigo()).sum();
	}
	public Categoria zonaMasAbrigada() {
		if(this.getNivelDeAbrigoSegun(Categoria.Superior) < this.getNivelDeAbrigoSegun(Categoria.Inferior)) {
			return Categoria.Inferior;
		}else {
			return Categoria.Superior;
		}
	}
	
	
	public boolean abriga(int nivelDeAbrigo , Categoria zonaSensible) {
		return this.elNivelDeAbrigoEsIgualA(nivelDeAbrigo) && this.esZonaMasAbrigada(zonaSensible);
	}
	

}
