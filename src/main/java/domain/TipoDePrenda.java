package domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


import db.EntidadPersistente;

@Entity
@Table(name = "tipoDePrenda")
public class TipoDePrenda extends EntidadPersistente{
	public TipoDePrenda(){}

	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "categoria")
	@Enumerated(value = EnumType.STRING)
	private Categoria categoria;
	
	@Column(name = "nivelDeCapa")
	private int nivelDeCapa;
	
	@Column(name = "nivelDeAbrigo")
	private int nivelDeAbrigo;
	
	@Column(name = "telasAdmitidas")
	@ElementCollection(targetClass=Tela.class)
	private List<Tela> telasAdmitidas;
	
	@ManyToMany
	private List<TipoDePrenda> combinaciones;

	public TipoDePrenda(String nombre, Categoria categoria, int nivelDeCapa , int nivelDeAbrigo, List<Tela> telasAdmitidas , List<TipoDePrenda> tiposCombinables)
	{
		this.nombre = nombre;
		this.categoria = categoria;
		this.nivelDeCapa = nivelDeCapa;
		this.nivelDeAbrigo = nivelDeAbrigo;
		
		this.telasAdmitidas = telasAdmitidas;
		this.combinaciones = tiposCombinables;
	}

	public List<Tela> getTelasAdmitidas() {
		return telasAdmitidas;
	}

	public void setTelasAdmitidas(Tela nuevaTela) {
		this.telasAdmitidas.add(nuevaTela);
	}

	public List<TipoDePrenda> getCombinaciones() {
		return combinaciones;
	}

	public void setCombinaciones(TipoDePrenda nuevaCombinacion) {
		this.combinaciones.add(nuevaCombinacion);
	}

	public TipoDePrenda(String nombre, Categoria categoria, int nivelDeCapa)
	{
		this.nombre = nombre;
		this.categoria = categoria;
		this.nivelDeCapa = nivelDeCapa;
	}

	public TipoDePrenda(String nombre, Categoria categoria)
	{
		this.nombre = nombre;
		this.categoria = categoria;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public int getNivelDeCapa() {
		return nivelDeCapa;
	}

	public void setNivelDeCapa(int nivelDeCapa) {
		this.nivelDeCapa = nivelDeCapa;
	}

	public int getNivelDeAbrigo() {
		return nivelDeAbrigo;
	}

	public void setNivelDeAbrigo(int nivelDeAbrigo) {
		this.nivelDeAbrigo = nivelDeAbrigo;
	}

	public Boolean esIgualNivelDeCapaA(int nivelDeCapa) {
		return this.nivelDeCapa == nivelDeCapa;
	}

	public boolean sosDeCategoria(Categoria categoria) {

		return this.getCategoria().equals(categoria);
	}
	
	public boolean nivelDeAbrigoIgualA(int nivel) {
		return this.nivelDeAbrigo == nivel;
	}
	
	public boolean permiteTela(Tela tela){
		return telasAdmitidas.contains(tela);
	}
	
	public boolean combinaCon(TipoDePrenda tipo) {
		return this.combinaciones.contains(tipo);
	}
}