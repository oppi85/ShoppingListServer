package de.rocho.shopinglistserver.persistance;

import de.rocho.shopinglistserver.persistance.ListEntry;
import de.rocho.shopinglistserver.persistance.Recepe;
import de.rocho.shopinglistserver.persistance.ShoppingList;
import de.rocho.shopinglistserver.persistance.Store;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-07T14:48:58")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, String> privateKey;
    public static volatile ListAttribute<User, ShoppingList> shoppingLists;
    public static volatile ListAttribute<User, ListEntry> listEntrys;
    public static volatile ListAttribute<User, Recepe> recepeList;
    public static volatile SingularAttribute<User, String> name;
    public static volatile SingularAttribute<User, Long> id;
    public static volatile SingularAttribute<User, Store> store;
    public static volatile SingularAttribute<User, String> publicKey;

}