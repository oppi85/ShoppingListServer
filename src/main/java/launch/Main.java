package launch;

import de.rocho.shopinglistserver.DbHelper;
import org.glassfish.grizzly.http.server.HttpServer;
import de.rocho.shopinglistserver.resources.ShoppingListRessource;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;


public class Main {

    public static void main(String args[]) throws IOException, URISyntaxException {
        DbHelper dbHelper = new DbHelper();
        URI BASE_URI = URI.create("http://localhost:8080/rest/");
        Set<Class<?>> s = new HashSet<>();
        s.add(ShoppingListRessource.class);
        ResourceConfig resConfig = new ResourceConfig(s);
        final int port = System.getenv("PORT") != null ? Integer.valueOf(System.getenv("PORT")) : 8080;
        
        if(port != 8080)
            BASE_URI = UriBuilder.fromUri("http://0.0.0.0/").port(port).build();
                       
        URI dbUri = System.getenv("DATABASE_URL") !=null ? new URI(System.getenv("DATABASE_URL")) : null;
        dbHelper.setDbUri(dbUri);
        
        HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resConfig);
        httpServer.getServerConfiguration().addHttpHandler(new StaticHttpHandler(), "/test");
        
        httpServer.start();
        System.out.println("Server started on: " + BASE_URI);
        while(true){
            System.in.read();
        }
        /*
        

        
        System.out.println("Type <ENTER> to exit");
        
        
        httpServer.stop();
                
                */
    }
}
