package de.rocho.shopinglistserver.persistance;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Entity
public class RecepeEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator="stacjatv_id_seq", strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(unique = true)
    private Article article;
    
    @ManyToOne
    private Recepe recepe;
    
    private long recepeID;
    
    private int quantity;

    public long getRecepeID() {
        return recepeID;
    }

    public void setRecepeID(long recepeID) {
        this.recepeID = recepeID;
    }

    public Recepe getRecepe() {
        return recepe;
    }

    public void setRecepe(Recepe recepe) {
        this.recepe = recepe;
    }

    public Article getArticle() {
        return article;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
        if (!(object instanceof RecepeEntry)) {
            return false;
        }
        RecepeEntry other = (RecepeEntry) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.rocho.shopinglistserver.persistance.RecepeEntry[ id=" + id + " ]";
    }

    public JSONObject toJson() throws JSONException {
        JSONObject JSONObjectRecepeEntry = new JSONObject();
        JSONObjectRecepeEntry
            .put("id", id)
            .put("recepeID", recepeID)
            .put("article", article.getName())
            .put("quantity", quantity);

        return JSONObjectRecepeEntry;
    }
}
