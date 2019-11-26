package repositories.factories;

import models.SugerenciaModel;
import repositories.RepositorioSugerencia;
import repositories.daos.DAOMySQL;

public class FactoryRespositorioSugerencia
{
    private static RepositorioSugerencia repo;

    private static RepositorioSugerencia get()
    {
        if(repo == null)
        {
            repo = RepositorioSugerencia.getInstance(new DAOMySQL(SugerenciaModel.getInstance()));
        }
        return repo;
    }
}
