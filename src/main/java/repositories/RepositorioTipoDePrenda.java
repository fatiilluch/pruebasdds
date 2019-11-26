package repositories;

import domain.TipoDePrenda;
import repositories.daos.DAO;
import java.util.List;

public class RepositorioTipoDePrenda extends Repositorio {
    private static RepositorioTipoDePrenda instance;

    public static RepositorioTipoDePrenda getInstance(DAO dao) {
        if(instance == null){
            instance = new RepositorioTipoDePrenda(dao);
        }
        return instance;
    }

    private RepositorioTipoDePrenda(DAO dao){
        this.setDao(dao);
    }

    public List<TipoDePrenda> buscarTodos(){
        return this.dao.buscarTodos();
    }

    public TipoDePrenda buscar(int id){
        return this.dao.buscar(id);
    }
}