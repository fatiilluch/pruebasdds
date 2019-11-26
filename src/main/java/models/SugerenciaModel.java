package models;

import domain.suggestions.Sugerencia;
import db.EntityManagerHelper;
import java.util.List;

public class SugerenciaModel extends Model{
    private static SugerenciaModel instance;

    public static SugerenciaModel getInstance() {
        if(instance == null) {
            instance = new SugerenciaModel();
        }

        return instance;
    }

    @Override
    public List<Sugerencia> buscarTodos() {
        return EntityManagerHelper.getEntityManager().createQuery("from Sugerencia").getResultList();
    }

    @Override
    public Sugerencia buscar(int id) {
        return EntityManagerHelper.getEntityManager().find(Sugerencia.class, id);}
}

