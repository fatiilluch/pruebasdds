package controllers;

import domain.Guardarropa;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import repositories.RepositorioGuardarropa;
import repositories.factories.FactoryRepositorioGuardarropa;

public class GuardarropaController {
    private RepositorioGuardarropa repo;

    public GuardarropaController()
    {
        this.repo = FactoryRepositorioGuardarropa.get(); //va a chequear que repo usar
    }

    public ModelAndView mostrarGuardarropaUsuario(Request request,Response Response){
        int id = new Integer(request.params("id"));
        List<Guardarropa> guardarropasDeUsuario = this.repo.buscarGuardarropaPorUsuario(id);
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("guardarropas", guardarropasDeUsuario);
        ModelAndView vista  = new ModelAndView(parametros, "guardarropa.hbs");
        return  vista;
    }

    public ModelAndView mostrarTodos(Request request, Response response)
    {
        List<Guardarropa> guardarropas = this.repo.buscarTodos();
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("guardarropas", guardarropas);
        ModelAndView vista  = new ModelAndView(parametros, "guardarropa.hbs");
        return  vista;
    }

    public ModelAndView mostrar(Request request, Response response) {
        int id = new Integer(request.params("id"));
        Guardarropa guardarropasBuscado = this.repo.buscar(id);

        Map<String,Object> parametros = new HashMap<>();
        parametros.put("guardarropas", guardarropasBuscado);
        ModelAndView vista = new ModelAndView(parametros, "guardarropa.hbs");
        return vista;
    }
}
