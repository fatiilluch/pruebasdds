package domain;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import db.EntidadPersistente;
import domain.events.Protocolo;


@Entity
@Table(name = "Atuendo")
public class Atuendo extends EntidadPersistente
{
    
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "idAtuendo", referencedColumnName = "id")
    private List<Prenda> prendas;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "idGuardarropa", referencedColumnName = "id")
    private Guardarropa guardarropaAsociado;
    
	@Column(name = "protocolo")
	@Enumerated(value = EnumType.STRING)
    private Protocolo protocoloAtuendo;
    
	@Column(name = "rechazado")
    private boolean rechazado;
   
    public Atuendo(List<Prenda> partesSuperiores, List<Prenda> partesInferiores, List<Prenda> calzados, List<Prenda> accesorios)
    {
        
        this.rechazado = false;
    }

    public Atuendo(List<Prenda> partesSuperiores, List<Prenda> partesInferiores, List<Prenda> calzados)
    {
        
        this.rechazado = false;
        
    }
    
    public Atuendo(List<Prenda> prendas, Protocolo unProtocolo)
    {
        this.prendas = prendas;
        this.protocoloAtuendo = unProtocolo;
        this.rechazado = false;
        
    }
  
    
    public Protocolo getProtocoloAtuendo() {
        return protocoloAtuendo;
    }
    public void setProtocolo(Protocolo unProtocolo) {
        this.protocoloAtuendo = unProtocolo;
    }
    
    public void aceptarAtuendo() {
    	this.rechazado = false;
    }
    
    public void rechazarAtuendo() {
    	this.rechazado = true;
    }
    
    public boolean estaRechazado() {
    	return this.rechazado;
    }
  
    public void setGuardarropa(Guardarropa guardarropa) {
        this.guardarropaAsociado = guardarropa;
    }
    public Guardarropa getGuardarropaAsociado() {
        return this.guardarropaAsociado;
    }

    public double puntos(List<Prenda> prendas)
    {
        return prendas.stream().mapToDouble(prenda -> prenda.getPuntosDeCalor()).sum(); 
    }
    
    
    public List<Prenda> getPrendasSegun(Categoria categoria){
    	return this.prendas.stream().filter(prenda -> prenda.sosDeCategoria(categoria)).
    			collect(Collectors.toList());
    }
    
    public int puntosTotalesSegun(Categoria categoria) {
    	return this.getPrendasSegun(categoria).stream().mapToInt(prenda -> prenda.getPuntosDeCalor()).sum();
    }

	public List<Prenda> getParteSuperior() {
		return this.getPrendasSegun(Categoria.Superior);
	}
    
}