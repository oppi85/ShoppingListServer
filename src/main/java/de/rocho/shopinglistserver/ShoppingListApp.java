package de.rocho.shopinglistserver;

import de.rocho.shopinglistserver.resources.TempRessource;
import org.glassfish.grizzly.http.server.HttpServer;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;


public class ShoppingListApp {

    public static void main(String args[]) throws IOException {
        ResourceConfig resConfig = new ResourceConfig(TempRessource.class);
        
        final int port = System.getenv("PORT") != null ? Integer.valueOf(System.getenv("PORT")) : 8080;
        //final URI BASE_URI; //= URI.create("http://192.168.1.106:8080/rest/");
        final URI BASE_URI = UriBuilder.fromUri("http://0.0.0.0/").port(port).build(); 
        
        final HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resConfig);
        httpServer.getServerConfiguration().addHttpHandler(new StaticHttpHandler(), args);

        /*httpServer.start();

        System.out.println("Server started on: http://localhost:8080/rest/");
        System.out.println("Type <ENTER> to exit");
        
        System.in.read();
        //httpServer.stop();*/
    }
}
