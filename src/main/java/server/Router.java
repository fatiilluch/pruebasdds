package server;


import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.BooleanHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;

import controllers.*;

//import controllers.HomeController;
//import controllers.LoginController;
//import controllers.MainController;
//import controllers.UsuarioController;
//import controllers.EventosController;
//import controllers.GuardarropaController;


public class Router {
    private static HandlebarsTemplateEngine engine;

    private static void initEngine() {
        Router.engine = HandlebarsTemplateEngineBuilder
                .create()
                .withDefaultHelpers()
                .withHelper("isTrue", BooleanHelper.isTrue)
                .build();
    }

    public static void init() {
        Router.initEngine();
        Spark.staticFileLocation("/public");
        Router.configure();
    }

    private static void configure() //GET, POST, DELETE, PUT
    {
        UsuarioController usuarioController = new UsuarioController();
        GuardarropaController guardarropaController = new GuardarropaController();
        PrendaController prendaController = new PrendaController();

        //Spark.get("/saludo", (req,res) -> "Hola :D");
        //Spark.get("/saludo/:nombre", (req,res) -> "Hola " + req.params("nombre")); // el ":" es interno, es para decir lo que venga despues es una variable
        //Spark.get("/saludo/:nombre", usuarioController::saludo);
        //Spark.get("/saludo/*/:nombre", (req,res) -> "Hola :D " + req.splat()[0] + " " + req.params("nombre")); // con * puede llegar cualquier cosa, con [0] le digo que consiga todos los parametros y me quedo con el primero
        //Spark.get("/saludo", (req,res) -> "Hola :D " + req.queryParams("nombre") + " " + req.queryParams("apellido"));
        Spark.get("/login", LoginController::show,engine);
        Spark.post("/login", LoginController::login, engine);
        Spark.get("/home", HomeController::show, engine);
        Spark.get("/usuarios", usuarioController::mostrarTodos, Router.engine);
        Spark.get("/usuario/:id", usuarioController::mostrar, Router.engine);
        Spark.post("/usuario/:id", usuarioController::modificar);
        Spark.get("/guardarropa/:id", guardarropaController::mostrarGuardarropaUsuario, Router.engine);
        Spark.get("/guardarropa", guardarropaController::mostrarTodos, Router.engine);
        Spark.get("/guardarropass/:id", guardarropaController::mostrar, Router.engine);
        Spark.get("/prendas", prendaController::mostrarTodos, Router.engine);
        
        
        Spark.get("/main", MainController::show, engine);
        Spark.post("/main", MainController::seleccion_guardarropa, engine);
        //Spark.post("/main", MainController::seleccion_eventos, engine);
        //Spark.post("/main", MainController::seleccion_sugerencias, engine);
        Spark.get("/eventos", EventosController::show, engine);
    }
}
