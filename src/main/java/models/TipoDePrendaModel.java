package models;

import domain.TipoDePrenda;
import db.EntityManagerHelper;
import java.util.List;

public class TipoDePrendaModel extends Model{
    private static TipoDePrendaModel instance;

    public static TipoDePrendaModel getInstance() {
        if(instance == null) {
            instance = new TipoDePrendaModel();
        }

        return instance;
    }

    @Override
    public List<TipoDePrenda> buscarTodos() {
        return EntityManagerHelper.getEntityManager().createQuery("from tipoDePrenda").getResultList();
    }

    @Override
    public TipoDePrenda buscar(int id) {
        return EntityManagerHelper.getEntityManager().find(TipoDePrenda.class, id);}
}