package de.rocho.shopinglistserver.persistance;

import de.rocho.shopinglistserver.persistance.ListEntry;
import de.rocho.shopinglistserver.persistance.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-08T18:36:58")
@StaticMetamodel(ShoppingList.class)
public class ShoppingList_ { 

    public static volatile ListAttribute<ShoppingList, User> userList;
    public static volatile SingularAttribute<ShoppingList, String> name;
    public static volatile SingularAttribute<ShoppingList, Long> id;
    public static volatile ListAttribute<ShoppingList, ListEntry> listEntry;
    public static volatile SingularAttribute<ShoppingList, Integer> status;

}