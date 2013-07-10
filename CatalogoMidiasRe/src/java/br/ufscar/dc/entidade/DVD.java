/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.entidade;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 *
 * @author cleber
 */
@Entity(name="DVD")
public class DVD extends Midia implements Serializable {
    
    @Column(length = 80)
    private String diretor;
    
    @OneToMany
    private Set<Papel> Papeis;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DVD)) {
            return false;
        }
        DVD other = (DVD) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    public String getDiretor() {
        return diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public Set<Papel> getPapeis() {
        return Papeis;
    }

    public void setPapeis(Set<Papel> Papeis) {
        this.Papeis = Papeis;
    }

    @Override
    public String toString() {
        return "br.ufscar.dc.entidade.DVD[ id=" + getId() + " ]";
    }
    
}
