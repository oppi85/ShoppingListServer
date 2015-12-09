package de.rocho.shopinglistserver.persistance;

import de.rocho.shopinglistserver.persistance.RecepeEntry;
import de.rocho.shopinglistserver.persistance.AppUser;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-08T18:36:58")
@StaticMetamodel(Recepe.class)
public class Recepe_ { 

    public static volatile SingularAttribute<Recepe, String> name;
    public static volatile ListAttribute<Recepe, RecepeEntry> recepeEntry;
    public static volatile SingularAttribute<Recepe, Long> id;
    public static volatile SingularAttribute<Recepe, AppUser> user;

}