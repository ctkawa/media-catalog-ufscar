/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.entidade;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 *
 * @author cleber
 */
@Entity(name="CD")
public class CD extends Midia implements Serializable{
    
    @Column(length = 80)
    private String artista;
        
    @OneToMany
    private Collection<Faixa> Faixas;


    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }
    
    public Collection<Faixa> getFaixas() {
        return Faixas;
    }

    public void setFaixas(Collection<Faixa> Faixas) {
        this.Faixas = Faixas;
    }

    @Override
    public String toString() {
        return "catalogo.CD[ id=" + super.getId() + " ]";
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CD)) {
            return false;
        }
        CD other = (CD) object;
        if ((super.getId() == null && other.getId() != null) || (super.getId() != null && !super.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    
    
}
