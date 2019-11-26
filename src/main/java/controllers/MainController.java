package controllers;

import java.util.HashMap;
import java.util.Map;

import com.twilio.rest.api.v2010.account.usage.record.ThisMonth;

import domain.Usuario;
import repositories.RepositorioUsuario;
import repositories.factories.FactoryRepositorioUsuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class MainController {
	
	public static RepositorioUsuario repoUser;
	
	public MainController() {
		this.repoUser = FactoryRepositorioUsuario.get();
	}
	
	public static ModelAndView show(Request req, Response res){
		return new ModelAndView(null, "main.hbs");
	}
	
	public static ModelAndView seleccion_guardarropa(Request req, Response res){
		
		String username = req.params("actualUsuario");
		
		Usuario user = repoUser.buscarPorUsername(username);
		int id = user.getId();
		
		req.session().attribute("id", id);
		
		res.redirect("/guardarropa/:" + id);
        
		return null;
	}
	
	public static ModelAndView seleccion_eventos(Request req, Response res){
		
		String username = req.params("actualUsuario");
		
		Usuario user = repoUser.buscarPorUsername(username);
		int id = user.getId();
		
		req.session().attribute("id", id);
		
//		res.redirect("/eventos");
        
		return null;
	}
	
	public static ModelAndView seleccion_sugerencias(Request req, Response res){
		
		String username = req.params("actualUsuario");
		
		Usuario user = repoUser.buscarPorUsername(username);
		int id = user.getId();
		
		req.session().attribute("id", id);
		
		res.redirect("/sugerencias");
        
		return null;
	}

}
