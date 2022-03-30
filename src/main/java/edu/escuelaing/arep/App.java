package edu.escuelaing.arep;

import org.json.JSONObject;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

public class App {

    private static  Calculator calculadora;

    public static void main(String ... args){
        calculadora = Calculator.initCalculator();
        staticFiles.location("/public");
        port(getPort());
        get("/", (req, res) -> {
            res.redirect("index.html");
            return null;
        });
        get("/cos","application/json",(req,res) -> {
            res.type("application/json");
            return operations("cos",req,res);
        });
        get("/tan","application/json", (req,res) -> {
            res.type("application/json");
            return operations("tan",req,res);
        });
    }

    static int getPort(){
        if(System.getenv("PORT") != null){
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }

    public static JSONObject operations(String operation, Request req, Response res) {
        JSONObject json;
        Double value = Double.parseDouble(req.queryParams("value"));
        Double result = 0.0;
        if (operation.equals("tan")) {
            result = calculadora.tan(value);
            json = getJSON(value, result, operation);
        } else {
            result = calculadora.cos(value);
            json = getJSON(value, result, operation);
        }
        return json;
    }

    private static JSONObject getJSON(Double value, Double result, String operation) {
        JSONObject json = new JSONObject();
        json.put("Operation", operation);
        json.put("Input", value);
        json.put("Output", result);
        return json;
    }
}
