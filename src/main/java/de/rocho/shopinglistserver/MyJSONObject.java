/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.rocho.shopinglistserver;

import de.rocho.shopinglistserver.persistance.Article;
import de.rocho.shopinglistserver.persistance.ListEntry;
import de.rocho.shopinglistserver.persistance.Recepe;
import de.rocho.shopinglistserver.persistance.RecepeEntry;
import de.rocho.shopinglistserver.persistance.ShoppingList;
import de.rocho.shopinglistserver.persistance.AppUser;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Sven
 */
public class MyJSONObject {
    
    String type;
    /**
     * which AppUser sends the request?
     * to check whether the user is able to change items
     */
    String privateKey;
    long userID;
    
    Article article;
    AppUser user;
    ShoppingList shoppingList;
    ListEntry listEntry;
    Recepe recepe;
    RecepeEntry recepeEntry;

    JSONObject object;

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public JSONObject getObject() {
        return object;
    }

    public void setObject(JSONObject object) {
        this.object = object;
    }
    
    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public ShoppingList getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }

    public ListEntry getListEntry() {
        return listEntry;
    }

    public void setListEntry(ListEntry listEntry) {
        this.listEntry = listEntry;
    }

    public Recepe getRecepe() {
        return recepe;
    }

    public void setRecepe(Recepe recepe) {
        this.recepe = recepe;
    }

    public RecepeEntry getRecepeEntry() {
        return recepeEntry;
    }

    public void setRecepeEntry(RecepeEntry recepeEntry) {
        this.recepeEntry = recepeEntry;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public JSONObject toJson() throws JSONException {
        JSONObject myJsonObject = new JSONObject();
        
        if (user != null)
            myJsonObject.put("user",user.toJson());
        if (shoppingList !=null)                
            myJsonObject.put("shoppingList", shoppingList.toJson());
        if (listEntry !=null)                
            myJsonObject.put("listEntry", listEntry.toJson());
        if (article != null)
            myJsonObject.put("article", article.toJson());
        if (recepe != null)
            myJsonObject.put("recepe", recepe.toJson());
        if (recepeEntry != null)
            myJsonObject.put("recepeEntry", recepeEntry.toJson());
            
        myJsonObject
                .put("type", type)
                .put("privateKey", privateKey)
                .put("userID", userID);

        return myJsonObject;
    }
}
