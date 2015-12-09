package de.rocho.shopinglistserver.persistance;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Entity
public class Store implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator="stacjatv_id_seq", strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Article article;
    private int quantity;

    @OneToOne(mappedBy = "store")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
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
        if (!(object instanceof Store)) {
            return false;
        }
        Store other = (Store) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.rocho.shopinglistserver.persistance.Store[ id=" + id + " ]";
    }

     public JSONObject toJson() throws JSONException {
        JSONObject JSONObjectStore = new JSONObject();
        JSONObjectStore
            .put("id", id)
            .put("article", article.getName())
            .put("unit", article.getUnit())
            .put("quantity", quantity);

        return JSONObjectStore;
    }
}
