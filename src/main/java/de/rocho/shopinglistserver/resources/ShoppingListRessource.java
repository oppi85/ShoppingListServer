/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.rocho.shopinglistserver.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.rocho.shopinglistserver.CreateDB;
import de.rocho.shopinglistserver.MyJSONObject;
import de.rocho.shopinglistserver.persistance.Article;
import de.rocho.shopinglistserver.persistance.ListEntry;
import de.rocho.shopinglistserver.persistance.PersistenceFacade;
import de.rocho.shopinglistserver.persistance.Recepe;
import de.rocho.shopinglistserver.persistance.RecepeEntry;
import de.rocho.shopinglistserver.persistance.ShoppingList;
import de.rocho.shopinglistserver.persistance.Store;
import de.rocho.shopinglistserver.persistance.User;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.glassfish.grizzly.http.server.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Sven
 */
@Path("/shoppinglist")
public class ShoppingListRessource {
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String checkRunning(){
       
        return "Server läuft";
    }

    /**
     *
     * @return 
     */
    @GET
    @Path("/create")
    public String createDummyDate() {
        CreateDB createDB;
        createDB = new CreateDB();
        
        return "Data created";
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonIgnoreProperties
    public String createObject(MyJSONObject myJsonObject) {
        PersistenceFacade facade = new PersistenceFacade();
        
        Boolean bool = facade.checkAccess(myJsonObject);
        MyJSONObject response = new MyJSONObject();
        JSONObject ob = new JSONObject();
        
        if(bool){
            switch (myJsonObject.getType()) {
                case "article":
                    /**
                     * {"type":"article", "userID":"LONG", "userID":"LONG", "privateKey":"STRING",
                     * "article":{"name": "STRING", "unit": "STRING"}
                     * }
                     */

                    facade.createArticle(myJsonObject.getArticle());
                    break;
                case "shoppingList":
                    /**
                     * {"type":"shoppingList", "userID":"LONG", "privateKey":"String",
                     *  "shoppingList":{"name": "STRING", "userList":[{"id":"LONG"}]}
                     * }
                     */
                    response.setType("shoppingList");
                    response.setPrivateKey("serverKey");                    
                    response.setShoppingList(facade.createShoppingList(myJsonObject.getShoppingList()));
                    break;
                case "listEntry":
                    /**
                     * {"type":"listEntry", "userID":"LONG", "privateKey":"STRING",
                     *  "listEntry":{"article":{"id":"LONG"}, "user":{"id":"LONG"}, "addDate":"DATE", "buyData":"DATE", "shoppingList":{"id":"LONG"}, "quantity":"INT"}
                     * }
                     */
                    response.setType("listEntry");
                    response.setPrivateKey("serverKey");
                    response.setListEntry(facade.createEntry(myJsonObject.getListEntry()));
                    break;
                case "recepe":
                    /**
                     * {"type":"recepe", "userID":"LONG", "privateKey":"STRING",
                     *  "recepe":{"name": "STRING", "user":{"id":"LONG"}}
                     * }
                     */
                    facade.createRecepe(myJsonObject.getRecepe());
                    break;
                case "recepeEntry":
                    /**
                     * {"type":"recepeEntry", "userID":"LONG", "userID":"LONG", "privateKey":"STRING",
                     *  "recepeEntry":{"article":{"id":"LONG"}, "recepe":{"id":"LONG"}, "quantity":"INT"}
                     * }
                     * myJsonObject.userID = Recepe ownwing User id
                     */
                    facade.createRecepeEntry(myJsonObject.getRecepeEntry());
                    break;
                case "store":
                    /**
                     * {"type":"store", "userID":"LONG", "userID":"LONG", "privateKey":"STRING",
                     *  "store":{"article":{"id":"LONG"}, "quantity":"INT"}
                     * }
                     * myJsonObject.userID = Recepe ownwing User id
                     */
                    facade.createStoreEntry(myJsonObject.getStore());
                    break;
                case "user":
                    /**
                     * {"type":"user", "userID":"LONG", "privateKey":"STRING",
                     *  "user":{"name": "STRING", "privateKey": "STRING", "publicKey":"STRING"}
                     * }
                     */
                    response.setType("user");
                    response.setPrivateKey("serverKey");
                    response.setUser(facade.createUser(myJsonObject.getUser(), myJsonObject.getPrivateKey()));
                    
                    break;
            }
        }
        try {
            ob.put("Response", response.toJson());
        } catch (JSONException ex) {
            Logger.getLogger(ShoppingListRessource.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(ob.toString());
        return ob.toString();
    }

    @GET
    @Path("/single/{ObjID}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getObject(@PathParam("ObjID")Long ObjID, @PathParam("id")Long id){
        PersistenceFacade facade = new PersistenceFacade();
        JSONObject JSONObject = new JSONObject();

        try {
            switch (ObjID.intValue()) {
                case 1:

                    //{"name": "String", "unit": "String"}
                    JSONObject.put("Article", facade.findArticle(id).toJson());

                    break;
                case 2:
                    //{"name":"String", "user":"String"}
                    JSONObject.put("ShoppingList", facade.findShoppingList(id).toJson());
                    break;
                case 3:
                    //
                    JSONObject.put("ListEntry", facade.findListEntry(id).toJson());
                    break;
                case 4:
                    //
                    JSONObject.put("Recepe", facade.findRecepe(id).toJson());
                    break;
                case 5:
                    //
                    JSONObject.put("RecepeEntry", facade.findRecepeEntry(id).toJson());
                    break;
                case 6:
                    //{}
                    JSONObject.put("Store", facade.findStoreEntry(id).toJson());
                    break;
                case 7:
                    //{"name":"String", "keyNumber":"String"}
                    //JSONObject.put("User", facade.findUser(id).toJson());
                    break;
            }
        } catch (JSONException ex) {
            Logger.getLogger(ShoppingListRessource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return JSONObject.toString();
    }

    @GET
    @Path("/user/{publicKey}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUser(@PathParam("publicKey") String publicKey) {
        PersistenceFacade facade = new PersistenceFacade();
        
        MyJSONObject response = new MyJSONObject();
        JSONObject JSONObject = new JSONObject();
        
        response.setType("user");
        response.setPrivateKey("serverKey");
        response.setUser(facade.findUser(publicKey));
       
        try {
            JSONObject.put("Response", response.toJson());
        } catch (JSONException ex) {
            Logger.getLogger(ShoppingListRessource.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(JSONObject.toString());
        return JSONObject.toString();
    }
    
    
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllObjectets(@PathParam("id") Long id) {
        PersistenceFacade facade = new PersistenceFacade();
        JSONObject JSONObject = new JSONObject();
        JSONArray JSONArrayObject = new JSONArray();

        try {
            switch (id.intValue()) {
                case 1:
                    List<Article> articleList = facade.findAllArticle();
                    for (Article a : articleList) {
                        JSONArrayObject.put(a.toJson());
                    }
                    JSONObject.put("Article", JSONArrayObject);
                    break;
                case 2:
                    List<ShoppingList> shoppingLists = facade.findAllShoppingLists();
                    for (ShoppingList sl : shoppingLists) {
                        JSONArrayObject.put(sl.toJson());
                    }
                    
                    JSONObject.put("ShoppingList", JSONArrayObject);
                    break;
                case 3:
                    List<ListEntry> listEntries = facade.findAllListEntries();
                    for (ListEntry le : listEntries) {
                        JSONArrayObject.put(le.toJson());
                    }
                    
                    JSONObject.put("ListEntry", JSONArrayObject);
                    break;
                case 4:
                    List<Recepe> recepes = facade.findAllRecepes();
                    for (Recepe r : recepes) {
                        JSONArrayObject.put(r.toJson());
                    }
                    
                    JSONObject.put("Recepe", JSONArrayObject);
                    break;
                case 5:
                    List<RecepeEntry> recepeEntris = facade.findAllRecepeEntries();
                    for (RecepeEntry r : recepeEntris) {
                        JSONArrayObject.put(r.toJson());
                    }
                    
                    JSONObject.put("RecepeEntry", JSONArrayObject);
                    break;
                case 6:
                    List<Store> storeEntries = facade.findAllStoreEntries();
                    for (Store s : storeEntries) {
                        JSONArrayObject.put(s.toJson());
                    }
                    
                    JSONObject.put("Store", JSONArrayObject);
                    break;
                case 7:
                    List<User> users = facade.findAllUser();
                    for (User u : users) {
                        JSONArrayObject.put(u.toJson());
                    }
                    
                    JSONObject.put("User", JSONArrayObject);
                    break;
            }
        } catch (JSONException ex) {
            Logger.getLogger(ShoppingListRessource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return JSONObject.toString();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void editObject(MyJSONObject myJsonObject) {
        PersistenceFacade facade = new PersistenceFacade();
        Boolean bool = facade.checkAccess(myJsonObject);
        if(bool){
            switch (myJsonObject.getType()) {
                case "article":
                    facade.editArticle(myJsonObject);
                    break;
                case "shoppingList":
                    facade.editShoppingList(myJsonObject);
                    break;
                case "listEntry":
                    facade.editListEntry(myJsonObject);
                    break;
                case "recepe":
                    facade.editRecepe(myJsonObject);
                    break;
                case "recepeEntry":
                    facade.editRecepeEntry(myJsonObject);
                    break;
                case "store":
                    facade.editStoreEntry(myJsonObject);
                    break;
                case "user":
                    facade.editUser(myJsonObject);
                    break;
            }
        }
    }

    @PUT
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonIgnoreProperties
    public void deleteObject(MyJSONObject myJsonObject) {
        PersistenceFacade facade = new PersistenceFacade();
        
        Boolean bool = facade.checkAccess(myJsonObject);
        if(bool){
            switch (myJsonObject.getType()) {
                case "article":
                    facade.deleteArticle(myJsonObject.getArticle());
                    break;
                case "shoppingList":
                    facade.deleteShoppingList(myJsonObject.getShoppingList());
                    break;
                case "listEntry":
                    facade.deleteListEntry(myJsonObject.getListEntry());
                    break;
                case "recepe":
                    facade.deleteRecepe(myJsonObject.getRecepe());
                    break;
                case "recepeEntry":
                    facade.deleteRecepeEntry(myJsonObject.getRecepeEntry());
                    break;
                case "store":
                    facade.deleteStoreEntry(myJsonObject.getStore());
                    break;
                case "user":
                    facade.deleteUser(myJsonObject.getUser());
                    break;
            }
        }
    }

    /**
     *
     * @param myJsonObject
     * @return
     */
    @POST
    @Path("/user")
    @Consumes(MediaType.APPLICATION_JSON)
//Add a explicite User to an given List
    public String addUserToList(MyJSONObject myJsonObject) {
        PersistenceFacade facade = new PersistenceFacade();
        Boolean bool = facade.checkAccess(myJsonObject);
        /**
         * {"type":"shoppingList", "userID":"LONG", "privateKey":"STRING",
         *  "shoppingList":{"id": "LONG", "userList":[{"publicKey":"STRING"}]}
         * }
         */
        if(bool)
            facade.addUserToList(myJsonObject);
    
        return Response.filter("User added");
    }

    /**
     *
     * @param myJsonObject
     * @return
     */
    @DELETE
    @Path("/user")
    @Consumes(MediaType.APPLICATION_JSON)
    /**
     * {"type":"shoppingList", "userID":"LONG", "privateKey":"STRING",
     *  "shoppingList":{"id": "LONG", "userList":[{"id":"LONG"}]}
     * }
     */
    public String deleteUserFromList(MyJSONObject myJsonObject) {
        PersistenceFacade facade = new PersistenceFacade();
        Boolean bool = facade.checkAccess(myJsonObject);
        if(bool)
            facade.deleteUserFromList(myJsonObject);

        return Response.filter("User deleted");
    }

    @GET
    @Path("/user/sl/{publicKey}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getShoppingListFromUser(@PathParam("publicKey") String publicKey) {
        JSONObject JSONObjectArticlelist = new JSONObject();
        JSONArray JSONArrayShoppingList = new JSONArray();

        try {
            PersistenceFacade facade = new PersistenceFacade();
            List<ShoppingList> shoppingList = facade.findUser(publicKey).getShoppingLists();
            for (ShoppingList sl : shoppingList) {
                JSONArrayShoppingList.put(sl.toJson());
            }
            JSONObjectArticlelist.put("Shoppinglist", JSONArrayShoppingList);
        } catch (JSONException ex) {
            Logger.getLogger(ShoppingListRessource.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(JSONObjectArticlelist.toString());
        return JSONObjectArticlelist.toString();
    }
}
