package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class SugerenciasController {

	public ModelAndView show(Request req, Response res){
		return new ModelAndView(null, "sugerencias.hbs");
	}
}
