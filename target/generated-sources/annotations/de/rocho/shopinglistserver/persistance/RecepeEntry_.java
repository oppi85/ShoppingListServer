package de.rocho.shopinglistserver.persistance;

import de.rocho.shopinglistserver.persistance.Article;
import de.rocho.shopinglistserver.persistance.Recepe;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-08T18:36:58")
@StaticMetamodel(RecepeEntry.class)
public class RecepeEntry_ { 

    public static volatile SingularAttribute<RecepeEntry, Integer> quantity;
    public static volatile SingularAttribute<RecepeEntry, Long> id;
    public static volatile SingularAttribute<RecepeEntry, Recepe> recepe;
    public static volatile SingularAttribute<RecepeEntry, Article> article;

}