package de.rocho.shopinglistserver;

import de.rocho.shopinglistserver.resources.TempRessource;
import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;


public class ShoppingListApp {

    private static URI BASE_URI; //= URI.create("http://192.168.1.106:8080/rest/");

    public static void main(String args[]) throws IOException {
        
        String port = System.getenv("PORT");
        
        if (port == null || port.isEmpty()) {
            port = "8080";
        }
        //BASE_URI = URI.create("https://shoppingliste.herokuapp.com:"+port+"/rest/");
        BASE_URI = URI.create("http://0.0.0.0:"+port+"/rest/");
        
        Set<Class<?>> s = new HashSet<>();
        
        s.add(TempRessource.class);
        
        ResourceConfig resConfig = new ResourceConfig(s);

        HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resConfig);
        httpServer.getServerConfiguration().addHttpHandler(new StaticHttpHandler(), args);

        httpServer.start();

        System.out.println("Server started on: http://localhost:8080/rest/");
        System.out.println("Type <ENTER> to exit");

        System.in.read();
        httpServer.stop();
    }

}
