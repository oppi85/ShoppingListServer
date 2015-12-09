package de.rocho.shopinglistserver;

import de.rocho.shopinglistserver.persistance.Article;
import de.rocho.shopinglistserver.persistance.ListEntry;
import de.rocho.shopinglistserver.persistance.PersistenceFacade;
import de.rocho.shopinglistserver.persistance.Recepe;
import de.rocho.shopinglistserver.persistance.RecepeEntry;
import de.rocho.shopinglistserver.persistance.ShoppingList;
import de.rocho.shopinglistserver.persistance.Store;
import de.rocho.shopinglistserver.persistance.AppUser;

public class CreateDB {
    
    public CreateDB(){
        PersistenceFacade pf = new PersistenceFacade();
        
        Article a = new Article();
        Article a1 = new Article();
        Article a2 = new Article();
        AppUser u = new AppUser();
        AppUser u1 = new AppUser();
        AppUser u2 = new AppUser();
        ShoppingList sl = new ShoppingList();
        ShoppingList sl1 = new ShoppingList();
        ListEntry le = new ListEntry();
        ListEntry le1 = new ListEntry();
        ListEntry le2 = new ListEntry();
        ListEntry le3 = new ListEntry();
        Store s;
        Recepe r;
        RecepeEntry re;
        
        a.setName("Toast");
        a.setUnit("Pck");
        pf.createArticle(a);
        
        a1.setName("Milch");
        a1.setUnit("Pck");
        pf.createArticle(a1);
        
        a2.setName("Kaffe");
        a2.setUnit("Pck");
        pf.createArticle(a2);
        
        u.setName("Sven");
        u.setPublicKey("3456");
        pf.createUser(u, "abc123");
        
        u1.setName("Anna");
        u1.setPublicKey("4567");
        pf.createUser(u1, "123abc");
        
        u2.setName("Berd");
        u2.setPublicKey("5678");
        pf.createUser(u2, "123");
        
        sl.setName("Geschenke");
        sl.setStatus(0);
        sl.getUserList().add(u);
        pf.createShoppingList(sl);
        
        sl1.setName("Geschenke");
        sl1.setStatus(0);
        sl1.getUserList().add(u1);        
        pf.createShoppingList(sl1);
              
        le.setAddDate("03-12-2015 18:39:07");
        le.setArticle(a);
        le.setBuyDate("0");
        le.setQuantity(2);
        le.setUser(u);
        le.setShoppingListID(sl.getId());
        le.setBought(false);
        pf.createEntry(le);
        
        le1.setAddDate("03-12-2015 18:39:07");
        le1.setArticle(a1);
        le1.setBuyDate("0");
        le1.setQuantity(1);
        le1.setUser(u);
        le1.setShoppingListID(sl.getId());
        le1.setBought(false);
        pf.createEntry(le1);
        
        le2.setAddDate("03-12-2015 18:39:07");
        le2.setArticle(a2);
        le2.setBuyDate("0");
        le2.setQuantity(1);
        le2.setUser(u2);
        le2.setShoppingListID(sl1.getId());
        le2.setBought(false);
        pf.createEntry(le2);
        
        le3.setAddDate("03-12-2015 18:39:07");
        le3.setArticle(a1);
        le3.setBuyDate("0");
        le3.setQuantity(1);
        le3.setUser(u1);
        le3.setShoppingListID(sl1.getId());
        le3.setBought(false);
        pf.createEntry(le3);
    }
    
}
