/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.controller;

import br.ufscar.dc.controller.exceptions.NonexistentEntityException;
import br.ufscar.dc.entidade.Faixa;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;

/**
 *
 * @author rafael
 */
public class FaixaJpaController implements Serializable {

    public FaixaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Faixa faixa) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(faixa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Faixa faixa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            faixa = em.merge(faixa);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = faixa.getId();
                if (findFaixa(id.longValue()) == null) {
                    throw new NonexistentEntityException("The faixa with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Faixa faixa;
            try {
                faixa = em.getReference(Faixa.class, id);
                faixa.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The faixa with id " + id + " no longer exists.", enfe);
            }
            em.remove(faixa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Faixa> findFaixaEntities() {
        return findFaixaEntities(true, -1, -1);
    }

    public List<Faixa> findFaixaEntities(int maxResults, int firstResult) {
        return findFaixaEntities(false, maxResults, firstResult);
    }

    private List<Faixa> findFaixaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Faixa as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Faixa findFaixa(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Faixa.class, id);
        } finally {
            em.close();
        }
    }

    public int getFaixaCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Faixa as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
