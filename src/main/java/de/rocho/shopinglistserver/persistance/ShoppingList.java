package de.rocho.shopinglistserver.persistance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Entity
public class ShoppingList implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int status;

    @ManyToMany(mappedBy = "shoppingLists")
    @JoinColumn(unique = true)
    private List<User> userList= new ArrayList<>();

    @OneToMany
    private List<ListEntry> listEntry;

    public List<ListEntry> getListEntry() {
        return listEntry;
    }

    public void setListEntry(List<ListEntry> listEntry) {
        this.listEntry = listEntry;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public String getName() {
        return name;
    }

    public int getStatus() {
        return status;
    }

    public List<User> getUserList() {
        return userList;
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
        if (!(object instanceof ShoppingList)) {
            return false;
        }
        ShoppingList other = (ShoppingList) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "de.rocho.shopinglistserver.persistance.ShoppingList[ id=" + id + " ]";
    }

   public JSONObject toJson() {
        JSONObject JSONObjectShoppingList = new JSONObject();
        JSONArray JSONArrayListEntry = new JSONArray();
        JSONArray JSONArrayUser = new JSONArray();

        JSONObject JSONObjectListEntry = new JSONObject();
        JSONObject JSONObjectUserList = new JSONObject();
        try {
            JSONObjectShoppingList
                .put("id",  id)
                .put("name",  name)
                .put("status",  status);

            for (ListEntry le : this.listEntry) {
                JSONArrayListEntry.put(le.toJson());
            }
            for (User user : this.userList) {
                JSONArrayUser.put(user.toJson());
            }


            JSONObjectShoppingList.put("listEntry",JSONArrayListEntry);

            JSONObjectShoppingList.put("user",JSONArrayUser);
        } catch (JSONException ex) {
            Logger.getLogger(ShoppingList.class.getName()).log(Level.SEVERE, null, ex);
        }

        return JSONObjectShoppingList;
    }
}
