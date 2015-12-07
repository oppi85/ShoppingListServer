package de.rocho.shopinglistserver.persistance;

import de.rocho.shopinglistserver.persistance.Article;
import de.rocho.shopinglistserver.persistance.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-07T14:48:58")
@StaticMetamodel(Store.class)
public class Store_ { 

    public static volatile SingularAttribute<Store, Integer> quantity;
    public static volatile SingularAttribute<Store, Long> id;
    public static volatile SingularAttribute<Store, User> user;
    public static volatile SingularAttribute<Store, Article> article;

}