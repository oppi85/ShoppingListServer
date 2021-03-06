package de.rocho.shopinglistserver.persistance;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.json.JSONException;
import org.json.JSONObject;

@Entity
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator="stacjatv_id_seq", strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String unit;

    @OneToMany(mappedBy = "article",cascade = CascadeType.REMOVE)
    @JoinColumn(nullable = true)
    private List<ListEntry> listEntries;
    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    @JoinColumn(nullable = true)
    private List<RecepeEntry> recepeEntries;
    
    public List<RecepeEntry> getRecepeEntries() {
        return recepeEntries;
    }

    public void setRecepeEntries(List<RecepeEntry> recepeEntries) {
        this.recepeEntries = recepeEntries;
    }

    public Article() {
    }

    public List<ListEntry> getListEntries() {
        return listEntries;
    }

    public void setListEntries(List<ListEntry> listEntries) {
        this.listEntries = listEntries;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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
        if (!(object instanceof Article)) {
            return false;
        }
        Article other = (Article) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return new StringBuffer("[\"ID :\" \"").append(this.id)
                .append("\" \"Name :\" \"").append(this.name)
                .append("\" \"Unit :\" \"").append(this.unit).append("\"]").toString();
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jsonObjectArticle = new JSONObject();
        jsonObjectArticle
            .put("id",id)
            .put("name",name)
            .put("unit",unit);

        return jsonObjectArticle;
    }

}
