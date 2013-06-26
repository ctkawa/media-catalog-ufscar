/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.controller;

import br.ufscar.dc.controller.exceptions.NonexistentEntityException;
import br.ufscar.dc.entidade.CD;
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
public class CDJpaController implements Serializable {

    public CDJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CD CD) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(CD);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CD CD) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CD = em.merge(CD);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = CD.getId();
                if (findCD(id) == null) {
                    throw new NonexistentEntityException("The cD with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CD CD;
            try {
                CD = em.getReference(CD.class, id);
                CD.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The CD with id " + id + " no longer exists.", enfe);
            }
            em.remove(CD);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CD> findCDEntities() {
        return findCDEntities(true, -1, -1);
    }

    public List<CD> findCDEntities(int maxResults, int firstResult) {
        return findCDEntities(false, maxResults, firstResult);
    }

    private List<CD> findCDEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from CD as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public CD findCD(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CD.class, id);
        } finally {
            em.close();
        }
    }

    public int getCDCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from CD as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
