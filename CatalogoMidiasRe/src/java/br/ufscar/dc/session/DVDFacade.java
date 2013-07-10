/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.session;

import br.ufscar.dc.entidade.DVD;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author cleber
 */
@Stateless
public class DVDFacade extends AbstractFacade<DVD> {
    @PersistenceContext(unitName = "CatalogoMidiasRePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DVDFacade() {
        super(DVD.class);
    }
    
}
