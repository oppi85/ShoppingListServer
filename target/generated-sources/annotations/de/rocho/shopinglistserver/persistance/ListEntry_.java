package de.rocho.shopinglistserver.persistance;

import de.rocho.shopinglistserver.persistance.Article;
import de.rocho.shopinglistserver.persistance.AppUser;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-08T18:36:58")
@StaticMetamodel(ListEntry.class)
public class ListEntry_ { 

    public static volatile SingularAttribute<ListEntry, Integer> quantity;
    public static volatile SingularAttribute<ListEntry, Long> shoppingListID;
    public static volatile SingularAttribute<ListEntry, Boolean> bought;
    public static volatile SingularAttribute<ListEntry, String> buyDate;
    public static volatile SingularAttribute<ListEntry, Long> id;
    public static volatile SingularAttribute<ListEntry, AppUser> user;
    public static volatile SingularAttribute<ListEntry, String> addDate;
    public static volatile SingularAttribute<ListEntry, Article> article;

}