package controllers;

import domain.Guardarropa;
import domain.Prenda;
import repositories.RepositorioPrenda;
import repositories.factories.FactoryRepositorioPrenda;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrendaController {
        private RepositorioPrenda repo;

        public PrendaController()
        {
            this.repo = FactoryRepositorioPrenda.get(); //va a chequear que repo usar
        }

        public ModelAndView mostrarPrendasUnGuardarropa(Request request, Response response)
        {
            int id = new Integer (request.params("id"));
            List<Prenda> prendas = this.repo.buscarPrendaPorGuardarropa(id);//aca debería agarrar el id del guardarropa que quiere
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("prendas",prendas);
            ModelAndView vista  = new ModelAndView(parametros, "prendas.hbs");
            return  vista;
        }

        public ModelAndView mostrarTodos(Request request, Response response)
        {
            List<Prenda> prendas = this.repo.buscarTodos();
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("prendas", prendas);
            ModelAndView vista  = new ModelAndView(parametros, "prendas.hbs");
            return  vista;
        }



}
