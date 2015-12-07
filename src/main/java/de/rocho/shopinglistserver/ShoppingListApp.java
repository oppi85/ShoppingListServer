package de.rocho.shopinglistserver;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import de.rocho.shopinglistserver.resources.TempRessource;
import org.glassfish.grizzly.http.server.HttpServer;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;


public class ShoppingListApp {

    private static URI BASE_URI; //= URI.create("http://192.168.1.106:8080/rest/");

    /*public static void main(String args[]) throws IOException {
        
        
        
        String port = System.getenv("PORT");
        
        if (port == null || port.isEmpty()) {
            port = "8080";
        }
        //BASE_URI = URI.create("https://shoppingliste.herokuapp.com:"+port+"/rest/");
        BASE_URI =UriBuilder.fromUri("http://0.0.0.0/").port(Integer.valueOf(port)).build(); // URI.create("http://0.0.0.0:"+port+"/rest/");
        
        Set<Class<?>> s = new HashSet<>();
        
        s.add(TempRessource.class);
        
        ResourceConfig resConfig = new ResourceConfig(s);

        HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resConfig);
        httpServer.getServerConfiguration().addHttpHandler(new StaticHttpHandler(), args);

        httpServer.start();
/*
        System.out.println("Server started on: http://localhost:8080/rest/");
        System.out.println("Type <ENTER> to exit");
        while(true)
        System.in.read();
        //httpServer.stop();
    }*/
private static URI getBaseURI(String hostname, int port) {
        return UriBuilder.fromUri("http://0.0.0.0/").port(port).build();
    }

    protected static HttpServer startServer(URI uri) throws IOException {
        System.out.println("Starting grizzly...");
        ResourceConfig rc = new PackagesResourceConfig("de.rocho.shopinglistserver.resources");
        return GrizzlyServerFactory.createHttpServer(uri, rc);
    }

    public static void main(String[] args) throws IOException {
        URI uri = getBaseURI(System.getenv("HOSTNAME"), Integer.valueOf(System.getenv("PORT")));
        HttpServer httpServer = startServer(uri);
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nTry out %shelloworld\nHit enter to stop it...",
                uri, uri));
        while(true) {
            System.in.read();
        }
    }
}
