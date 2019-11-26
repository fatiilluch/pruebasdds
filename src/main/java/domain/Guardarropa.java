package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import db.EntidadPersistente;
import domain.events.Protocolo;
import domain.repositorio.RepositorioMolde;


@Entity
@Table(name = "Guardarropa")
public class Guardarropa extends EntidadPersistente
{
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "idGuardarropa", referencedColumnName = "id")
	private List<Prenda> prendas;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "idGuardarropaRechazado", referencedColumnName = "id")
	private List<Atuendo> atuendosRechazados;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "idGuardarropaAceptado", referencedColumnName = "id")
	private List<Atuendo> atuendosAceptados;
	
	@Transient
	private RepositorioMolde repoMolde;
		
	
	public Guardarropa() {
		this.inicializarPrenda();
		this.atuendosAceptados = new ArrayList<Atuendo>();
		this.atuendosRechazados = new ArrayList<Atuendo>();
		this.repoMolde = RepositorioMolde.getInstance();
	}
	

	private void inicializarPrenda() {
		this.prendas = new ArrayList<Prenda>();
	}

	public List<Prenda> getPrendas() {
		return prendas;
	}

	public void setPrendas(List<Prenda> prendas) {
		this.prendas = prendas;
	}

	public void agregarPrenda(Prenda nuevaPrenda) {
		this.prendas.add(nuevaPrenda);
	}

	public int cantidadDePrendas() {
		return this.prendas.size();
	}

	public List<Prenda> limitarPrendas(int limiteDePrendas){
		List<Prenda> otrasPrendas = new ArrayList<>();
	    if (prendas.size() < limiteDePrendas)
		{
		    otrasPrendas = prendas;
		}
	    else
        {
            otrasPrendas = this.prendas.stream().limit(limiteDePrendas).collect(Collectors.toList());
        }
		return otrasPrendas;
	}

	//filtra la lista de prendas por una categoria determinada
	public List<Prenda> filtrarPorCategoria(Categoria tipoCategoria) {
		return this.prendas.stream().filter(prenda -> prenda.sosDeCategoria(tipoCategoria)).collect(Collectors.toList());
	}

	public List<Prenda> filtrarPorProtocolo(Protocolo unProtocolo,int limiteDePrendas) {
		List<Prenda> prendasLimitadas = this.limitarPrendas(limiteDePrendas);
		return prendasLimitadas.stream().
			   filter(prenda -> prenda.estasAsociadoA(unProtocolo)).
			   collect(Collectors.toList());
	}
	//Obtengo un indice aleatorio de la lista que depende de la categoria
	public int indiceAleatorio(Categoria tipoCategoria) {
		Random aleatorio = new Random(System.currentTimeMillis());
		return aleatorio.nextInt(Math.abs(this.filtrarPorCategoria(tipoCategoria).size()));
	}
	
	//indice aleatorio seg�n el inivel de capa con una lista limitada
	public int indiceAleatorioSegunElNivel(List<Prenda> prendasLimitadas, int nivelDeCapa) {
		Random aleatorio = new Random(System.currentTimeMillis());
		return aleatorio.nextInt(Math.abs(this.filtrarPorNivelDeCapa(prendasLimitadas, nivelDeCapa).size()));
	}
	
	//Devuelve un lista de prendas segun un nivel de capa
	private List<Prenda> filtrarPorNivelDeCapa(List<Prenda> prendasLimitadas, int nivelDeCapa){
		return prendasLimitadas.stream().
				filter(prenda -> prenda.sosDeNivel(nivelDeCapa)).
				collect(Collectors.toList());
	}

	public Atuendo elegirAtuendoSegunProtocolo(Protocolo unProtocolo, int nivelDeCalor,Sensibilidad sensibilidadUsuario) {
		List<Prenda> prendasAtuendo = new ArrayList<Prenda>();
		// cambiar arbitrario segun la sensibilidad del usuario//
		if(sensibilidadUsuario.esFriolento()){
			nivelDeCalor+=1;
		}
		if (sensibilidadUsuario.esCaluroso()){
			nivelDeCalor-=1;
		}
		Molde molde = RepositorioMolde.getInstance().getMoldeCandidato(nivelDeCalor, sensibilidadUsuario.zonaMasSensible());
		// Excepcion por generar 
		if(this.tieneTipoDePrendaSegunMolde(molde)) {
			
				prendasAtuendo = this.rellenarMolde(molde);
		
		}
		
		return new Atuendo(prendasAtuendo, unProtocolo);
	}
	
	private List<Prenda> rellenarMolde(Molde molde) {
		List<Prenda> prendasDisponibles = new ArrayList<Prenda>();
		molde.getTiposDePrendas().forEach(tipo -> {
			prendasDisponibles.add(this.prendaSegunTipo(tipo));
		});
		
		return prendasDisponibles;
	}
	
	private Prenda prendaSegunTipo(TipoDePrenda tipo) {
		int indice = this.indicePrendaAleatoriaSegun(tipo);
		return this.filtrarPorTipoDePrenda(tipo).get(indice);
	}
	
	private int indicePrendaAleatoriaSegun(TipoDePrenda tipo) {
		Random aleatorio = new Random(System.currentTimeMillis());
		return aleatorio.nextInt(Math.abs(this.filtrarPorTipoDePrenda(tipo).size()));
	}


	private List<Prenda> filtrarPorTipoDePrenda(TipoDePrenda tipo) {
		return this.getPrendas().stream().filter(prenda -> prenda.sosDeTipoDe(tipo)).collect(Collectors.toList());
	}

	private boolean tieneTipoDePrendaSegunMolde(Molde molde) {
		List<TipoDePrenda> tipos = molde.getTiposDePrendas();
		
		return tipos.containsAll(this.tiposDePrendaDelGuardarropa());	
	}

	private List<TipoDePrenda> tiposDePrendaDelGuardarropa() {
		List<TipoDePrenda> tiposDelGuardarropa = this.getPrendas().stream().distinct()
				.map(prenda -> prenda.getTipoDePrenda()).collect(Collectors.toList());
		return tiposDelGuardarropa;
	}

	public List<Atuendo> getAtuendosRechazados(){
		return this.atuendosRechazados;
	}

	public void agregarAListaRechazado(Atuendo atuendo)
	{
		this.atuendosRechazados.add(atuendo);
	}
	
	public void elimAtuendoRechazado(Atuendo unAtuendo){
		this.atuendosRechazados.remove(unAtuendo);
	}
	
	public List<Atuendo> getAtuendosAceptados()
	{
		return this.atuendosAceptados;
	}
	
	public void agregarAListaAceptado(Atuendo atuendo) 
	{
		this.atuendosAceptados.add(atuendo);
	}

	public void elimAtuendorAceptado(Atuendo atuendo) 
	{
		this.atuendosAceptados.remove(atuendo);
	}
}