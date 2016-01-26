package launch;

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
import java.io.Console;
import static java.lang.System.exit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    public static  URI dbUri;
    public static String username;
    public static String password;
    public static String ip;
    
    public static String sqlServerPort;

    public static void main(String args[]) throws IOException, URISyntaxException {
        
        URI BASE_URI = URI.create("http://192.168.1.106:8080/");
        Set<Class<?>> s = new HashSet<>();
        s.add(ShoppingListRessource.class);
        ResourceConfig resConfig = new ResourceConfig(s);
        final int port = System.getenv("PORT") != null ? Integer.valueOf(System.getenv("PORT")) : 8080;
        
        if(port != 8080){
            BASE_URI = UriBuilder.fromUri("http://0.0.0.0/").port(port).build();
            dbUri = System.getenv("DATABASE_URL") !=null ? new URI(System.getenv("DATABASE_URL")) : null;
        }else{
            dbLogin();
            if(!createDB()){
                System.out.println("Server muss neugestartet werden");
                exit(0);
            }
        }
        
        HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resConfig);
        httpServer.getServerConfiguration().addHttpHandler(new StaticHttpHandler(), "/test"); //TODOD: rename test
        
        httpServer.start();
        System.out.println("Server started on: " + BASE_URI);
        while(true){
            System.in.read();
        }
       
    }
    
    private static void dbLogin(){
    Scanner userInput = new Scanner(System.in);
    Console console = System.console();
        System.out.println("Connect to Database.");
        System.out.println("Enter Login data.");
        System.out.println("");
            
        if (console == null) {
            System.out.print("DB Nutzer eingeben: ");
                username = userInput.nextLine();
            System.out.print("Nutzerpasswort eingeben: ");
                password = userInput.nextLine();
            System.out.print("MySQL Server IP: ");
                ip = userInput.nextLine();
            System.out.print("MySQL Server Port: ");
                sqlServerPort = userInput.nextLine();
        }else{

            console.printf("DB Nutzer eingeben: ");
                username = console.readLine();
            char passwordArray[] = console.readPassword("Nutzerpasswort eingeben: ");
                password = new String(passwordArray);
            console.printf("MySQL Server Server IP: ");
                ip = console.readLine();
            console.printf("MySQL Server Port: ");
                sqlServerPort = console.readLine();
        }
        
    }
    
    private static  Boolean createDB(){
        try {
            String jdbcDriver = "com.mysql.jdbc.Driver";
            String dbName = "ShoppingListDB";
            
            Class.forName(jdbcDriver);
            Connection conn = DriverManager.getConnection("jdbc:mysql://"+ip+":"+sqlServerPort+"/", username, password);
            Statement statement = conn.createStatement();
            statement.executeUpdate("CREATE DATABASE " + dbName);
            return true;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1007) {
                System.out.println(e.getMessage());
                return true;
            } else {
                System.out.println("Datenbankfehler: " + e);
            }
        }
        return false;
    }
}
