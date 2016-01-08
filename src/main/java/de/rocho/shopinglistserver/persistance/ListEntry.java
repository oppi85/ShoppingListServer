package de.rocho.shopinglistserver.persistance;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.json.JSONException;
import org.json.JSONObject;

@Entity
public class ListEntry implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator="stacjatv_id_seq", strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Article article;
    @ManyToOne
    private AppUser user;
    
    private long shoppingListID;

    private String addDate;
    private String buyDate;
    private int quantity;
    private Boolean bought;

    public Boolean getBought() {
        return bought;
    }

    public void setBought(Boolean bought) {
        this.bought = bought;
    }

    public void setShoppingListID(long shoppingListID) {
        this.shoppingListID = shoppingListID;
    }

    public long getShoppingListID() {
        return shoppingListID;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public AppUser getUser() {
        return user;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Article getArticle() {
        return article;
    }

    public String getAddDate() {
        return addDate;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public int getQuantity() {
        return quantity;
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
        if (!(object instanceof ListEntry)) {
            return false;
        }
        ListEntry other = (ListEntry) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return new StringBuffer("[\"ID :\" \"").append(this.id)
                .append("\" \"Name :\" \"").append(this.article.getName())
                .append("\" \"Quantity :\" \"").append(this.quantity)
                .append("\" \"AddDate :\" \"").append(this.addDate)
                .append("\" \"BuyDate :\" \"").append(this.buyDate)
                .append("\"]").toString();
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jsonObjectListEntry = new JSONObject();
        jsonObjectListEntry
            .put("id", id)
            .put("name",article.getName())
            .put("quantity",quantity)
            .put("addDate",addDate)
            .put("buyDate", buyDate)
            .put("bought", bought)
            .put("shoppingListID", shoppingListID);

        return jsonObjectListEntry;
    }
}
