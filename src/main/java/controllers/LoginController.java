package controllers;



import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.utils.RequestUtil;
import spark.utils.ViewUtil;

public class LoginController {
	
	public static ModelAndView show(Request req, Response res){
		return new ModelAndView(null, "login.hbs");
	}
	
	public static ModelAndView login(Request req, Response res) {
		
		Map<String, Object> model = new HashMap<>();
		
		System.out.print(UsuarioController.autentificacion(req.queryParams("username"), req.queryParams("password")));
		
		
        if (!UsuarioController.autentificacion(req.queryParams("username"), req.queryParams("password"))) {
            model.put("autentificacionFallida", true);
            return ViewUtil.responderError(req, model, "login.hbs");
        }
        
        model.put("autentificacionCorrecta", true);
        
        req.session().attribute("actualUsuario", RequestUtil.getQueryUsername(req));
        
        
		res.redirect("/main");
		
		return null;
	}

}
