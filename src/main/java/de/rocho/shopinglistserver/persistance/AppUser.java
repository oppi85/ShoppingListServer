package de.rocho.shopinglistserver.persistance;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Sven
 */
@Entity
public class AppUser implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(generator="stacjatv_id_seq", strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(nullable = true)
    private Store store;
    
    @OneToMany(mappedBy = "user")
    @JoinColumn(nullable = true)
    private List<ListEntry> listEntrys;

    @OneToMany(mappedBy = "user")
    @JoinColumn(nullable = true)
    private List<Recepe> recepeList;
    
    @ManyToMany
    @JoinColumn(nullable = true)
    private List<ShoppingList> shoppingLists;
    
    /*
     * privateKey for all methods
     */
    @Column(unique = true)
    private String privateKey;
    @Column(unique = true)
    /*
     * publicKey to add user to lists 
     */
    private String publicKey;
    @Column(unique = true)
    private String name;

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
    
    public List<Recepe> getRecepeList() {
        return recepeList;
    }

    public void setRecepeList(List<Recepe> recepeList) {
        this.recepeList = recepeList;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public void setListEntrys(List<ListEntry> listEntrys) {
        this.listEntrys = listEntrys;
    }

    public void setShoppingLists(List<ShoppingList> shoppingLists) {
        this.shoppingLists = shoppingLists;
    }

    public List<ListEntry> getListEntrys() {
        return listEntrys;
    }

    public List<ShoppingList> getShoppingLists() {
        return shoppingLists;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AppUser)) {
            return false;
        }
        AppUser other = (AppUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return new StringBuffer("[{\"ID :\" \"").append(this.id)
                .append(", \" \"Name :\" \"").append(this.name)
                .append(", \" \"PublicKey :\" \"").append(this.publicKey).append("\"}]").toString();
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jsonObjectUser = new JSONObject();

        jsonObjectUser.put("id",id);
        jsonObjectUser.put("name", name);
        jsonObjectUser.put("publicKey",publicKey);

        return jsonObjectUser;
    }
}
