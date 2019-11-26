package domain.events;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import db.EntidadPersistente;
import domain.Atuendo;
import domain.Usuario;
import domain.notificacion.EventoProximo;
import domain.suggestions.GestorDeSugerencias;
import domain.suggestions.Sugerencia;
import services.ElClima;

@Entity
@Table(name = "Evento")
public class Evento extends EntidadPersistente{
	
	@Column(name ="nombre")
	private String nombre;
	
	@Column(name ="fechaEvento")
	private Date fechaEvento;
	
	@Column(name ="protocolo")
	@Enumerated(value = EnumType.STRING)
	private Protocolo protocolo;
	
	@ManyToOne
	@JoinColumn(name = "idUsuario", referencedColumnName = "id")
    private Usuario usuario;
	
	@Column(name ="frecuencia")
	@Enumerated(value = EnumType.STRING)
	private Frecuencia frecuencia;
	
	@Transient
	private int proximidad = 1;
	
	@OneToMany
	@JoinColumn(name = "idEvento", referencedColumnName = "id")
	private List<Sugerencia> sugerencias;
	
	@OneToMany
	@JoinColumn(name = "idEventoPasado", referencedColumnName = "id")
	private List<Sugerencia> sugerenciasPasada;


    public Usuario getUsuario() {
        return usuario;
    }

	public int getProximidad() {
		return proximidad;
	}

	public void setProximidad(int proximidad) {
		this.proximidad = proximidad;
	}

	public Evento(String nombre, Date fecha, Protocolo protocolo,Frecuencia frecuencia,Usuario usuario) {
	
		this.nombre = nombre;
		this.fechaEvento = fecha;
		this.protocolo = protocolo;
		this.usuario = usuario;
		this.frecuencia = frecuencia;
		this.sugerencias = new ArrayList<Sugerencia>();
		this.sugerenciasPasada = new ArrayList<Sugerencia>();
	}
	
	public String getNombre() {
		return nombre;
	}

	public String getFecha() {
		SimpleDateFormat formatFecha = new SimpleDateFormat("yyyy-MM-dd");

		return formatFecha.format(this.fechaEvento);
	}

	public Protocolo getProtocoloVestimenta() {
		return this.protocolo;
	}

	// Metodos relacionados con el tiempo

	public int diasFaltantes() {
		Date fechaActual = new Date();
		return (int) ((this.fechaEvento.getTime()-fechaActual.getTime())/86400000);
	}

	public int diasEntre(Date d1, Date d2){
		return Math.abs((int)(( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24))));
	}

	public boolean esProximo() {
		return diasEntre(fechaEvento,new Date()) <= proximidad;
	}

	public boolean terminaHoy(){
    	return diasFaltantes() == 0;
	}
	
	public boolean finalizoEvento() {
        Date fechaActual = new Date();
        return this.fechaEvento.before(fechaActual);
    }

    public boolean eventoSigueFinalizado() {
        if(finalizoEvento()){ GestorDeEventos.getInstance().setEventoTerminado(this);}
        return finalizoEvento();
    }

	public void actualizarEvento(){
		if(finalizoEvento()){
			Calendar fecha = new GregorianCalendar();
			fecha.setTime(this.fechaEvento);

			switch(this.frecuencia){
				case UNICO: break;
				case DIARIO:
					fecha.add(Calendar.DAY_OF_YEAR,1);
					break;
				case SEMANAL:
					fecha.add(Calendar.WEEK_OF_YEAR,1);
					break;
				case MENSUAL:
					fecha.add(Calendar.MONTH,1);
					break;
				case ANUAL:
					fecha.add(Calendar.YEAR,1);
					break;
			}
			this.fechaEvento = fecha.getTime();
			if(sugerencias.size()!=0) {
				this.sugerenciasPasada.add(sugerencias.get(0));
				this.sugerencias.remove(0);
			}
		}
	}

	// Metodos de sugerencia

	public boolean esSugerible() {
    	if(!frecuencia.equals(Frecuencia.DIARIO)) {
			return this.esProximo() && sugerencias.size()==0;
		}
		else{
			return sugerencias.size()<3;
		}
    }

	public Sugerencia crearSugerencia() {
		GestorDeSugerencias.getInstance().EnvioDeNotificaciones(this.getUsuario(), new EventoProximo(this.getNombre()));
		int dia = this.diasFaltantes();
		dia = evaluarSugerenciasDiario(dia); // si es diario, tiene que calcular el de 3 dias en adelante
		Sugerencia sugerenciaRealizada = recibirSugerencia(dia);
		return sugerenciaRealizada;
	}

	public Sugerencia recibirSugerencia(int dia){
		double temperaturaDelMomento = ElClima.getInstance().getTemperatura(dia);

		List<Atuendo> sugerencia = this.getUsuario().getGuardarropas().stream().
				map(guardarropa -> this.getUsuario().pedirAtuendoEnUnDia(guardarropa,this.getProtocoloVestimenta(),dia)).
				collect(Collectors.toList());
		Sugerencia sugerenciaRealizada = new Sugerencia(sugerencia, this,temperaturaDelMomento);
		this.sugerencias.add(sugerenciaRealizada);
		return sugerenciaRealizada;
	}

	private int evaluarSugerenciasDiario(int dia) {
		if (this.frecuencia.equals(Frecuencia.DIARIO)) { // Esto está medio muy hardcodeado, pero no se me ocurria otra

			int repeticiones = 3 - sugerencias.size();
			dia = dia + 3; // Setea el dia en el maximo que podemos calcular, el 3 ese lo podriamos hacer global para que no quede feo

			while (repeticiones != 1) {
				// Calcula de atras para adelante las sugerencias, para que normalemente calcule la de 3 dias,
				// que es la que debe calcular siempre, ya que las otras estarían calculadas
				GestorDeSugerencias.getInstance().setSugerencia(recibirSugerencia(dia - repeticiones));
				repeticiones = repeticiones - 1;
			}

		}
		return dia;
	}


	public boolean cumpleSensibilidad(Atuendo atuendo) {
		
		return false;
	}

}
