/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.entidade;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author cleber
 */

@Entity(name="JOGO")
public class Jogo extends Midia implements Serializable {
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Jogo)) {
            return false;
        }
        Jogo other = (Jogo) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.ufscar.dc.entidade.Jogo[ id=" + getId() + " ]";
    }
    
}
