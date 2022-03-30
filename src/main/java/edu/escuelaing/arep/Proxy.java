package edu.escuelaing.arep;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

public class Proxy {

    private static List<String> urls = new ArrayList<String>();
    private static Boolean semaforo;
    public static void main( String[] args )
    {
        semaforo=true;
        urls.add("http://ec2-18-234-86-93.compute-1.amazonaws.com:4567/");
        urls.add("http://pruebados.com");
        port(getPort());

        get("/cos","application/json",(req,res)->{
            res.type("application/json");
            return getRequest("cos",req.queryParams("value"));
        });
        get("/tan","application/json",(req,res)->{
            res.type("application/json");
            return getRequest("tan",req.queryParams("value"));
        });
    }


    public static String getRequest(String oper,String value) throws IOException
    {
        URL url;
        String ans="respuesta";
        System.out.println(ans);
        String server;
        try{
            if(semaforo)
            {
                server=urls.get(0);
            }else
            {
                server=urls.get(1);
            }
            semaforo= !semaforo;
            url=new URL(server+ oper + "?value="+ value);
            System.out.println(url);
            HttpURLConnection urlconection=(HttpURLConnection) url.openConnection();
            System.out.println(urlconection);
            urlconection.setRequestMethod("GET");

            BufferedReader input= new BufferedReader(new InputStreamReader(urlconection.getInputStream()));
            String inputline;
            StringBuffer content=new StringBuffer();
            while ((inputline = input.readLine()) !=null)
            {
                content.append(inputline);
            }
            System.out.println(inputline);
            input.close();
            urlconection.disconnect();
            ans= content.toString();
            System.out.println(ans);
            return ans;
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
            System.out.println("puta");
        }
        return ans;
    }

    static int getPort(){
        if(System.getenv("PORT") != null){
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }

}
