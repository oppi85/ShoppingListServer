package launch;

import de.rocho.shopinglistserver.persistance.PersistenceFacade;
import org.glassfish.grizzly.http.server.HttpServer;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;


public class Main {

    public static void main(String args[]) throws IOException, URISyntaxException {
        HashMap<String, String> persistenceMap = new HashMap<>();
        URI BASE_URI;
        ResourceConfig resConfig = new ResourceConfig(de.rocho.shopinglistserver.resources.ShoppingListRessource.class);
        final int port = System.getenv("PORT") != null ? Integer.valueOf(System.getenv("PORT")) : 8080;
        if(port == 8080){
            BASE_URI= URI.create("http://192.168.1.106:8080/rest/");
        }else{
            BASE_URI = UriBuilder.fromUri("http://0.0.0.0/").port(port).build();
        }
        
        if (System.getenv("DATABASE_URL") != null){
            URI dbUri = new URI(System.getenv("DATABASE_URL"));
            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

            persistenceMap.put("javax.persistence.jdbc.url", dbUrl);
            persistenceMap.put("javax.persistence.jdbc.user", username);
            persistenceMap.put("javax.persistence.jdbc.password", password);
            persistenceMap.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
            PersistenceFacade pf = new PersistenceFacade();
            pf.createPU(persistenceMap);
        }
        
        
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
