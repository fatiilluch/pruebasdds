package models;

import domain.events.Evento;
import db.EntityManagerHelper;
import java.util.List;

public class EventoModel extends Model{
    private static EventoModel instance;

    public static EventoModel getInstance() {
        if(instance == null) {
            instance = new EventoModel();
        }

        return instance;
    }

    @Override
    public List<Evento> buscarTodos() {
        return EntityManagerHelper.getEntityManager().createQuery("from Evento").getResultList();
    }

    @Override
    public Evento buscar(int id) {
        return EntityManagerHelper.getEntityManager().find(Evento.class, id);}
}

