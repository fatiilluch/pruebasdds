package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class EventosController {
	
	
	
	public EventosController() {
	}

	public static ModelAndView show(Request req, Response res){
		return new ModelAndView(null, "events.hbs");
	}

}
