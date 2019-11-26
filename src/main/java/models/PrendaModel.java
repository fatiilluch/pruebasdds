package models;

import domain.Prenda;
import domain.Guardarropa;
import db.EntityManagerHelper;
import java.util.List;
import domain.Color;

public class PrendaModel extends Model{
	private static PrendaModel instance;
	
	public static PrendaModel getInstance() {
		if(instance == null) {
			instance = new PrendaModel();
		}
		
		return instance;
	}

	@Override
	public List<Prenda> buscarTodos() {
		List<Prenda> prendas =  EntityManagerHelper.getEntityManager().createQuery("from Prenda").getResultList();
		return prendas;
	}

	@Override
	public Prenda buscar(int id) {
		return EntityManagerHelper.getEntityManager().find(Prenda.class, id);
	}

	private List<Prenda> buscarPrendaPorGuardarropa(int idGuardarropa){
		String idGuardarropas = String.valueOf(idGuardarropa);
		return EntityManagerHelper.getEntityManager().createQuery("from Prenda where idGuardarropa= "+idGuardarropas).getResultList();
	}

	@Override
	public List<Prenda> busquedaCondicional(int id) {
		return this.buscarPrendaPorGuardarropa(id);
	}


}
