package repositories.factories;

import models.EventoModel;
import repositories.RepositorioEvento;
import repositories.daos.DAOMySQL;

public class FactoryRepositorioEvento
{
    private static RepositorioEvento repo;

    private static RepositorioEvento get()
    {
        if(repo == null)
        {
            repo = RepositorioEvento.getInstance(new DAOMySQL(EventoModel.getInstance()));
        }
        return repo;
    }
}
