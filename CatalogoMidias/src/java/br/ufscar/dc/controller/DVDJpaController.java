/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.controller;

import br.ufscar.dc.controller.exceptions.NonexistentEntityException;
import br.ufscar.dc.entidade.DVD;
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
public class DVDJpaController implements Serializable {

    public DVDJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DVD DVD) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(DVD);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DVD DVD) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DVD = em.merge(DVD);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = DVD.getId();
                if (findDVD(id) == null) {
                    throw new NonexistentEntityException("The dVD with id " + id + " no longer exists.");
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
            DVD DVD;
            try {
                DVD = em.getReference(DVD.class, id);
                DVD.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The DVD with id " + id + " no longer exists.", enfe);
            }
            em.remove(DVD);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DVD> findDVDEntities() {
        return findDVDEntities(true, -1, -1);
    }

    public List<DVD> findDVDEntities(int maxResults, int firstResult) {
        return findDVDEntities(false, maxResults, firstResult);
    }

    private List<DVD> findDVDEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from DVD as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public DVD findDVD(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DVD.class, id);
        } finally {
            em.close();
        }
    }

    public int getDVDCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from DVD as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
