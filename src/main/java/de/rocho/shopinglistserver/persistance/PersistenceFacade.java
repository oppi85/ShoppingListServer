package de.rocho.shopinglistserver.persistance;

import de.rocho.shopinglistserver.MyJSONObject;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import launch.Main;

public class PersistenceFacade {

    URI dbUri = Main.dbUri;
    private static final Logger log = Logger.getLogger( PersistenceFacade.class.getName() );
    EntityManagerFactory FACTORY = createEntityManagerFactory();
   
    public EntityManagerFactory createEntityManagerFactory(){
        
        if(dbUri != null){
            HashMap<String, String> persistenceMap = new HashMap<>();
            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

            persistenceMap.put("javax.persistence.jdbc.url", dbUrl);
            persistenceMap.put("javax.persistence.jdbc.user", username);
            persistenceMap.put("javax.persistence.jdbc.password", password);
            persistenceMap.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
            
            return Persistence.createEntityManagerFactory("ShoppingListPU", persistenceMap);
        }else{
            return Persistence.createEntityManagerFactory("ShoppingListPU");
        }
            
    }
    
     public Boolean checkAccess(MyJSONObject myJsonObject){
        EntityManager em = FACTORY.createEntityManager();
        Boolean access = false;
        String type = myJsonObject.getType();
        AppUser databaseUser;
        Query query = em.createQuery("SELECT u FROM AppUser u WHERE u.id='"+myJsonObject.getUserID()+"'");
        try{
            databaseUser = (AppUser) query.getSingleResult();
        
        ShoppingList sl;
        
        switch(type){
            
            case "shoppingList":
                query = em.createQuery("SELECT sl FROM ShoppingList sl WHERE sl.id='"+myJsonObject.getShoppingList().getId()+"'");
               
                try{
                    sl = (ShoppingList) query.getSingleResult();
                }catch(Exception e){
                    access = true;
                    break;
                }
                for(AppUser u :  sl.getUserList()){
                    if(u.getPrivateKey().equals(myJsonObject.getPrivateKey()))
                        access = true;
                    }
                break;
            case "listEntry":
                query = em.createQuery("SELECT sl FROM ShoppingList sl WHERE sl.id='"+myJsonObject.getListEntry().getShoppingListID()+"'");
                sl = (ShoppingList) query.getSingleResult();
                for(AppUser u :  sl.getUserList()){
                    
                    if(u.getPrivateKey().equals(myJsonObject.getPrivateKey())) 
                        access = true;
                    }
                break;
            
            default:
                if(databaseUser.getPrivateKey().equals(myJsonObject.getPrivateKey()))
                    access = true;
                break;
        }
        }catch(Exception e){
            access = true;
        }
        return access;
    }
     
//------ START ARTICLE --------
    public List<Article> findAllArticle() {
        EntityManager em = FACTORY.createEntityManager();
        Query query = em.createQuery("SELECT a from Article a");
        List<Article> articleList = query.getResultList();

        return articleList;
    }

    public Article findArticle(Long id) {
        EntityManager em = FACTORY.createEntityManager();
        Query query = em.createQuery("SELECT a from Article a WHERE a.id='" + id + "'");
        Article article = (Article) query.getSingleResult();
        return article;
    }

    public Boolean createArticle(Article article) {
        EntityManager em = FACTORY.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(article);
            tx.commit();
            System.out.println("add new article "+article.getName());
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public void deleteArticle(Article article) {
        EntityManager em = FACTORY.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(article));
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void editArticle(MyJSONObject myJsonObject) {
        EntityManager em = FACTORY.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Article article = myJsonObject.getArticle();
        Query query = em.createQuery("SELECT a from Article a WHERE a.id='" + article.getId() + "'");
        
        Article tempArticle = (Article) query.getSingleResult();

        if (article.getName() != null) {
            tempArticle.setName(article.getName());
        }
        if (article.getUnit() != null) {
            tempArticle.setUnit(article.getUnit());
        }
        try {
            tx.begin();
            em.merge(tempArticle);
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

//------ END ARTICLE --------
//------ START USER ---------
    public AppUser createUser(AppUser newUser, String privateKey) {
        EntityManager em = FACTORY.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        AppUser tmpUser = new AppUser();
        newUser.setPrivateKey(privateKey);
        Query query;
        
        /*
         * prüft erst ob eine NUtzername schon vorhanden ist
         * wenn das der Fall ist, wird Serverseitig der neue Benutzer umbenannt (hinzufügen von + id)
         */
        try{
            query = em.createQuery("SELECT u FROM AppUser u WHERE u.privateKey='"+privateKey+"'");
            tmpUser = (AppUser) query.getSingleResult();
            log.info("Nutzer schon vorhande");
        }catch(Exception e1){
            try{
                query = em.createQuery("SELECT u FROM AppUser u WHERE u.name='"+newUser.getName()+"'");
                AppUser user = (AppUser) query.getSingleResult();
                String userName = newUser.getName()+ "+" + user.getId();
                newUser.setName(userName);
            }catch(Exception e){
                log.info("Nutzername wurde geändert");
            }

            try {
                tx.begin();
                em.persist(newUser);
                tx.commit();

                query = em.createQuery("SELECT u FROM AppUser u WHERE u.privateKey='"+newUser.getPrivateKey()+"'");
                tmpUser = (AppUser) query.getSingleResult();
            } catch (Exception e) {
                return tmpUser;
            }
        }
        return tmpUser;
    }

    public AppUser findUser(String publicKey) {
        EntityManager em = FACTORY.createEntityManager();
        
        Query query = em.createQuery("SELECT u from AppUser u WHERE u.publicKey='" + publicKey + "'");
        AppUser user = (AppUser) query.getSingleResult();
        
        return user;
    }

    public List<AppUser> findAllUser() {
        EntityManager entityManager = FACTORY.createEntityManager();
        Query query = entityManager.createQuery("SELECT u from AppUser u");
        List<AppUser> userList = query.getResultList();

        return userList;
    }

    //TODO: 
    public void editUser(MyJSONObject myJsonObject) {
        EntityManager em = FACTORY.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        AppUser user = myJsonObject.getUser();
        Query query = em.createQuery("SELECT u from AppUser u WHERE u.id='" + user.getId() + "'");
        AppUser tempUser = (AppUser) query.getSingleResult();
            
        if (user.getName() != null) {
            tempUser.setName(user.getName());
        }
        if (user.getPrivateKey() != null) {
            tempUser.setPrivateKey(user.getPrivateKey());
        }
        try {
            tx.begin();
            em.merge(tempUser);
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteUser(AppUser user) {
        EntityManager em = FACTORY.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(user));
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

//------ END AppUser --------    
//------ START SHOPPINGLIST --------
    public ShoppingList createShoppingList(ShoppingList shoppingList) {
        EntityManager em = FACTORY.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Query query = em.createQuery("SELECT u from AppUser u WHERE u.id='" + shoppingList.getUserList().get(0).getId() + "'");
        AppUser user = (AppUser) query.getSingleResult();
        
        ShoppingList sl = shoppingList;
            try {
                tx.begin();
                em.persist(shoppingList);
                user.getShoppingLists().add(sl);
                em.merge(user);
                tx.commit();
                System.out.println("ShoppingList angelegt");
            } catch (Exception e) {
                System.out.println(e);
            }
        return shoppingList;
    }

    public ShoppingList findShoppingList(Long id) {
        EntityManager em = FACTORY.createEntityManager();
        Query query = em.createQuery("SELECT sl FROM ShoppingList sl WHERE sl.id='" + id + "'");
        ShoppingList shoppingList = (ShoppingList) query.getSingleResult();

        return shoppingList;
    }

    //{"id": "LONG", "userList":[{"id": "LONG"}]}
    public void addUserToList(MyJSONObject myJsonObject) {
        EntityManager em = FACTORY.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        ShoppingList shoppingList = myJsonObject.getShoppingList();
        Query query = em.createQuery("SELECT u FROM AppUser u WHERE u.publicKey='" + shoppingList.getUserList().get(0).getPublicKey() + "'");
        ShoppingList sl = findShoppingList(shoppingList.getId());
        AppUser user = (AppUser) query.getSingleResult();
        try {
            tx.begin();
            sl.getUserList().add(user);
            em.merge(sl);
            user.getShoppingLists().add(sl);
            em.merge(user);
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteUserFromList(MyJSONObject myJsonObject) {
        EntityManager em = FACTORY.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        ShoppingList tempShoppingList = myJsonObject.getShoppingList();
        
        try {
            tx.begin();
            ShoppingList sl = findShoppingList(tempShoppingList.getId());
            AppUser user = findUser(tempShoppingList.getUserList().get(0).getPublicKey());
            sl.getUserList().remove(user);
            if (sl.getUserList().isEmpty()) {
                deleteShoppingList(sl);

            } else {
                em.merge(sl);
            }
            user.getShoppingLists().remove(sl);
            em.merge(user);
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<ShoppingList> findAllShoppingLists() {
        EntityManager entityManager = FACTORY.createEntityManager();
        Query query = entityManager.createQuery("SELECT sl from ShoppingList sl");
        List<ShoppingList> shoppingList = query.getResultList();

        return shoppingList;
    }

    public void deleteShoppingList(ShoppingList shoppingList) {
        EntityManager em = FACTORY.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        for (ListEntry le : shoppingList.getListEntry()) {
            deleteListEntry(le);
        }
        try {
            tx.begin();
            em.remove(em.merge(shoppingList));
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void editShoppingList(MyJSONObject myJsonObject) {
        EntityManager em = FACTORY.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        ShoppingList shoppingList = myJsonObject.getShoppingList();
        Query query = em.createQuery("SELECT sl FROM ShoppinGlist sl WHERE sl.id='" + shoppingList.getId() + "'");
        ShoppingList tempShoppingList = (ShoppingList) query.getSingleResult();

        if (shoppingList.getName() != null) {
            tempShoppingList.setName(shoppingList.getName());
        }
        
        try {
            tx.begin();
            em.merge(tempShoppingList);
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

//------ END SHOPPINGLIST --------  
//------ START LISTENTRY --------
    public ListEntry createEntry(ListEntry listEntry) {
        EntityManager em = FACTORY.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        ShoppingList shoppingList = findShoppingList(listEntry.getShoppingListID());
        Article article = findArticle(listEntry.getArticle().getId());

        ListEntry le = listEntry;
        le.setArticle(article);
        le.setShoppingListID(shoppingList.getId());
        
            try {
                tx.begin();
                em.persist(le);
                article.getListEntrys().add(le);
                em.merge(article);
                shoppingList.getListEntry().add(le);
                em.merge(shoppingList);
                tx.commit();
            } catch (Exception e) {
                System.out.println(e);
            }
        return le;
    }

    public ListEntry findListEntry(Long id) {
        EntityManager em = FACTORY.createEntityManager();
        Query query = em.createQuery("SELECT le FROM ListEntry le WHERE le.id='" + id + "'");
        ListEntry listEntry = (ListEntry) query.getSingleResult();

        return listEntry;
    }

    public List<ListEntry> findAllListEntries() {
        EntityManager em = FACTORY.createEntityManager();
        Query query = em.createQuery("SELECT le from ListEntry le");
        List<ListEntry> listEntry = query.getResultList();

        return listEntry;

    }

    public void deleteListEntry(ListEntry listEntry) {
        
        EntityManager em = FACTORY.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Query query = em.createQuery("SELECT sl FROM ShoppingList sl WHERE sl.id='"+listEntry.getShoppingListID()+"'");
        ShoppingList shoppingList = (ShoppingList) query.getSingleResult();
        shoppingList.getListEntry().remove(listEntry);
        try {
            tx.begin();
            em.merge(shoppingList);
            em.remove(em.merge(listEntry));
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }

    public void editListEntry(MyJSONObject myJsonObject) {
        EntityManager em = FACTORY.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        ListEntry listEntry = myJsonObject.getListEntry();
        Query query = em.createQuery("SELECT le from ListEntry le WHERE le.id='" + listEntry.getId() + "'");
        ListEntry tempListEntry = (ListEntry) query.getSingleResult();

        if (listEntry.getArticle() != null) {
            tempListEntry.setArticle(listEntry.getArticle());
        }
        if (listEntry.getBuyDate() != null) {
            tempListEntry.setBuyDate(listEntry.getBuyDate());
        }
        if (listEntry.getQuantity() != 0) {
            tempListEntry.setQuantity(listEntry.getQuantity());
        }
        try {
            tx.begin();
            em.merge(tempListEntry);
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
//------ END LISTENTRY -------- 

//------ START RECEPE ---------
    public void createRecepe(Recepe recepe) {        
        EntityManager em = FACTORY.createEntityManager();
        EntityTransaction tx = em.getTransaction();        
        try {
            tx.begin();
            em.persist(recepe);
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
        }        
    }

    public Recepe findRecepe(Long id) {
        EntityManager em = FACTORY.createEntityManager();
        Query query = em.createQuery("SELECT r from Recepe r WHERE r.id='" + id + "'");
        Recepe recepe = (Recepe) query.getSingleResult();

        return recepe;
    }

    public List<Recepe> findAllRecepes() {
        EntityManager entityManager = FACTORY.createEntityManager();
        Query query = entityManager.createQuery("SELECT r from Recepe r");
        List<Recepe> recepeList = query.getResultList();

        return recepeList;
    }

//TODO: 
    public void editRecepe(MyJSONObject myJsonObject) {
        EntityManager em = FACTORY.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Recepe recepe = myJsonObject.getRecepe();
        Query query = em.createQuery("SELECT r FROM Recepe r WHERE r.id='" + recepe.getId() + "'");
        Recepe tempRecepe = (Recepe) query.getSingleResult();
            
            if (recepe.getName() != null) 
                tempRecepe.setName(recepe.getName());
            
         try {
             
            tx.begin();
                em.merge(tempRecepe);
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteRecepe(Recepe recepe) {
        EntityManager em = FACTORY.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        for (RecepeEntry re : recepe.getRecepeEntry()) {
            deleteRecepeEntry(re);
        }
        try {
            tx.begin();
            em.remove(em.merge(recepe));
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
//------ END RECEPE -------- 

//------ START RECEPE ENTRY--------   
    public void createRecepeEntry(RecepeEntry recepeEntry) {
        EntityManager em = FACTORY.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        Recepe recepe = findRecepe(recepeEntry.getRecepe().getId());
        
            Article article = findArticle(recepeEntry.getArticle().getId());
            try {
                tx.begin();
                em.persist(recepeEntry);
                article.getRecepeEntrys().add(recepeEntry);
                em.merge(article);
                recepe.getRecepeEntry().add(recepeEntry);
                em.merge(recepe);
                tx.commit();
            } catch (Exception e) {
                System.out.println(e);
            }
        
    }

    public RecepeEntry findRecepeEntry(Long id) {
        EntityManager em = FACTORY.createEntityManager();
        Query query = em.createQuery("SELECT re FROM RecepeEntry re WHERE re.id='" + id + "'");
        RecepeEntry recepeEntry = (RecepeEntry) query.getSingleResult();

        return recepeEntry;
    }

    public List<RecepeEntry> findAllRecepeEntries() {
        EntityManager em = FACTORY.createEntityManager();
        Query query = em.createQuery("SELECT re FROM RecepeEntry re");
        List<RecepeEntry> recepeEntry = query.getResultList();

        return recepeEntry;

    }

    public void deleteRecepeEntry(RecepeEntry recepeEntry) {
        EntityManager em = FACTORY.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(recepeEntry));
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void editRecepeEntry(MyJSONObject myJsonObject) {
        EntityManager em = FACTORY.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        RecepeEntry recepeEntry = myJsonObject.getRecepeEntry();
        Query query = em.createQuery("SELECT le from RecepeEntry le WHERE le.id='" + recepeEntry.getId() + "'");
        RecepeEntry tempRecepeEntry = (RecepeEntry) query.getSingleResult();

        if (recepeEntry.getArticle() != null) {
            tempRecepeEntry.setArticle(recepeEntry.getArticle());
        }
        if (recepeEntry.getQuantity() != 0) {
            tempRecepeEntry.setQuantity(recepeEntry.getQuantity());
        }
        try {
            tx.begin();
            em.merge(tempRecepeEntry);
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

//------ START STORE --------
    public void createStoreEntry(Store store) {
        EntityManager em = FACTORY.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
            try {
                tx.begin();
                em.persist(store);
                tx.commit();
            } catch (Exception e) {
                System.out.println(e);
            }
        
    }

    public Store findStoreEntry(Long id) {
        EntityManager em = FACTORY.createEntityManager();
        Query query = em.createQuery("SELECT storeEntry FROM Store storeEntry WHERE storeEntry.id='" + id + "'");
        Store store = (Store) query.getSingleResult();

        return store;
    }

    public List<Store> findAllStoreEntries() {
        EntityManager em = FACTORY.createEntityManager();
        Query query = em.createQuery("SELECT storeEntry FROM Store storeEntry");
        List<Store> store = query.getResultList();

        return store;

    }

    public void deleteStoreEntry(Store store) {
        EntityManager em = FACTORY.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(store));
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void editStoreEntry(MyJSONObject myJsonObject) {
        EntityManager em = FACTORY.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Store store = myJsonObject.getStore();
        Query query = em.createQuery("SELECT storeEntry FROM Store storeEntry WHERE .id='" + store.getId() + "'");
        Store tempStoreEntry = (Store) query.getSingleResult();

        if (store.getArticle() != null) {
            tempStoreEntry.setArticle(store.getArticle());
        }
        if (store.getQuantity() != 0) {
            tempStoreEntry.setQuantity(store.getQuantity());
        }
        try {
            tx.begin();
            em.merge(tempStoreEntry);
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
//------ END STORE -------- 
}
