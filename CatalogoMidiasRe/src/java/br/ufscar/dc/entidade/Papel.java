/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.entidade;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author cleber
 */
@Entity
@Table(name="PAPEL")
public class Papel implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String papelNoFilme;

    public String getPapelNoFilme() {
        return papelNoFilme;
    }

    public void setPapelNoFilme(String papelNoFilme) {
        this.papelNoFilme = papelNoFilme;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    private String nome;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        if (!(object instanceof Papel)) {
            return false;
        }
        Papel other = (Papel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.ufscar.dc.entidade.Papel[ id=" + id + " ]";
    }
    
}
