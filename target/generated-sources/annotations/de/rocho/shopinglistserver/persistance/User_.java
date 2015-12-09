package de.rocho.shopinglistserver.persistance;

import de.rocho.shopinglistserver.persistance.ListEntry;
import de.rocho.shopinglistserver.persistance.Recepe;
import de.rocho.shopinglistserver.persistance.ShoppingList;
import de.rocho.shopinglistserver.persistance.Store;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-08T18:36:58")
@StaticMetamodel(AppUser.class)
public class User_ { 

    public static volatile SingularAttribute<AppUser, String> privateKey;
    public static volatile ListAttribute<AppUser, ShoppingList> shoppingLists;
    public static volatile ListAttribute<AppUser, ListEntry> listEntrys;
    public static volatile ListAttribute<AppUser, Recepe> recepeList;
    public static volatile SingularAttribute<AppUser, String> name;
    public static volatile SingularAttribute<AppUser, Long> id;
    public static volatile SingularAttribute<AppUser, Store> store;
    public static volatile SingularAttribute<AppUser, String> publicKey;

}