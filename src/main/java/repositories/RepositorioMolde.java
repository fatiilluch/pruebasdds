package repositories;

import domain.Molde;
import repositories.daos.DAO;
import java.util.List;

public class RepositorioMolde extends Repositorio {
    private static RepositorioMolde instance;

    public static RepositorioMolde getInstance(DAO dao) {
        if(instance == null){
            instance = new RepositorioMolde(dao);
        }
        return instance;
    }

    private RepositorioMolde(DAO dao){
        this.setDao(dao);
    }

    public List<Molde> buscarTodos(){
        return this.dao.buscarTodos();
    }

    public Molde buscar(int id){
        return this.dao.buscar(id);
    }
}
