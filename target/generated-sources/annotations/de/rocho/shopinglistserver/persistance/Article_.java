package de.rocho.shopinglistserver.persistance;

import de.rocho.shopinglistserver.persistance.ListEntry;
import de.rocho.shopinglistserver.persistance.RecepeEntry;
import de.rocho.shopinglistserver.persistance.Store;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-07T14:48:58")
@StaticMetamodel(Article.class)
public class Article_ { 

    public static volatile SingularAttribute<Article, String> unit;
    public static volatile ListAttribute<Article, RecepeEntry> recepeEntrys;
    public static volatile ListAttribute<Article, ListEntry> listEntrys;
    public static volatile SingularAttribute<Article, String> name;
    public static volatile SingularAttribute<Article, Long> id;
    public static volatile SingularAttribute<Article, Store> store;

}