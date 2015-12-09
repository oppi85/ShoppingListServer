package de.rocho.shopinglistserver.persistance;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Entity
public class Recepe implements Serializable {
    
    @ManyToOne
    private User user;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator="stacjatv_id_seq", strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
     
    @OneToMany(mappedBy = "recepe")
    @JoinColumn(nullable = true)
    private List<RecepeEntry> recepeEntry;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
   
    public List<RecepeEntry> getRecepeEntry() {
        return recepeEntry;
    }

    public void setRecepeEntry(List<RecepeEntry> recepeEntry) {
        this.recepeEntry = recepeEntry;
    }

    public void setName(String name) {
        this.name = name;
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
        if (!(object instanceof Recepe)) {
            return false;
        }
        Recepe other = (Recepe) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.rocho.shopinglistserver.persistance.Recepe[ id=" + id + " ]";
    }

    public JSONObject toJson() throws JSONException {
        JSONObject JSONObjectRecepe = new JSONObject();
        JSONArray JSONArrayListEntry = new JSONArray();

        JSONObjectRecepe
            .put("id", id)
            .put("name", name);

        for (RecepeEntry re : this.recepeEntry) {
            JSONArrayListEntry.put(re.toJson());
        }
        JSONObjectRecepe.put("RecepeEntry", JSONArrayListEntry);

        return JSONObjectRecepe;
    }
}
