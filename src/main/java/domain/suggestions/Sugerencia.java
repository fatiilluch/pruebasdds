package domain.suggestions;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import db.EntidadPersistente;
import domain.Atuendo;
import domain.Usuario;
import domain.events.Evento;
import domain.notificacion.AlertaMeteorologica;
import services.ElClima;

@Entity
@Table(name = "Sugerencia")
public class Sugerencia extends EntidadPersistente{
	
	@OneToMany
	@JoinColumn(name = "idSugerencia", referencedColumnName = "id")
	private List<Atuendo> atuendos;
	
	@ManyToOne
	@JoinColumn(name = "idEvento", referencedColumnName = "id")
	private Evento evento;
	
	@Column(name = "temperaturaSupuesta")
	private Double temperaturaSupuesta; // Para que se reevalue en caso de haber alerta
	
	
	
	public Sugerencia(List<Atuendo> atuendos , Evento evento, Double temperaturaSupuesta){
		this.atuendos = atuendos;
		this.evento = evento;
		this.temperaturaSupuesta = temperaturaSupuesta;
	}
	
	
	
	public Evento getEvento() {
		return this.evento;
	}
	
	public List<Atuendo> getAtuendos() {
		return this.atuendos;
	}

	public Atuendo getAtuendo(int pos) {
		return this.atuendos.get(pos);
	}

	public Usuario getUsuario(){
		return getEvento().getUsuario();
	}

	public boolean sugerenciaRechazada() {
		return this.atuendos.stream().allMatch(atuendo -> atuendo.estaRechazado());
	}
	
	protected List<Atuendo> getAtuendosRechazados(){
		return atuendos.stream().filter(atuendo -> atuendo.estaRechazado()).collect(Collectors.toList());
	}
	
	// setea los atuendos rechazados en una lista de atuendos rechazados de cada guardaropa que los genero
	public void setAtuendosRechazados() {
		this.getAtuendosRechazados().forEach(atuendo -> atuendo.getGuardarropaAsociado().elimAtuendoRechazado(atuendo));
	}

	public void evaluarAletas(){
		//Si hay alerta notifica al usuario y genero un nuevo atuendo
		if(ElClima.getInstance().alertaMeteorogolica(temperaturaSupuesta)){
			GestorDeSugerencias.getInstance().EnvioDeNotificaciones(this.getUsuario(), new AlertaMeteorologica(this.evento.getNombre()));
			this.getEvento().crearSugerencia();
		}
	}

	public boolean eventoTerminaHoy(){
		return this.evento.terminaHoy();
	}

}
