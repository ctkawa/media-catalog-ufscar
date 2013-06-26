/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.controller;

import br.ufscar.dc.controller.exceptions.NonexistentEntityException;
import br.ufscar.dc.entidade.Papel;
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
public class PapelJpaController implements Serializable {

    public PapelJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Papel papel) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(papel);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Papel papel) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            papel = em.merge(papel);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = papel.getId();
                if (findPapel(id.longValue()) == null) {
                    throw new NonexistentEntityException("The papel with id " + id + " no longer exists.");
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
            Papel papel;
            try {
                papel = em.getReference(Papel.class, id);
                papel.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The papel with id " + id + " no longer exists.", enfe);
            }
            em.remove(papel);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Papel> findPapelEntities() {
        return findPapelEntities(true, -1, -1);
    }

    public List<Papel> findPapelEntities(int maxResults, int firstResult) {
        return findPapelEntities(false, maxResults, firstResult);
    }

    private List<Papel> findPapelEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Papel as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Papel findPapel(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Papel.class, id);
        } finally {
            em.close();
        }
    }

    public int getPapelCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Papel as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
