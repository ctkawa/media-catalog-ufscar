/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.controller;

import br.ufscar.dc.controller.exceptions.NonexistentEntityException;
import br.ufscar.dc.entidade.Jogo;
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
public class JogoJpaController implements Serializable {

    public JogoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Jogo jogo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(jogo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Jogo jogo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            jogo = em.merge(jogo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = jogo.getId();
                if (findJogo(id) == null) {
                    throw new NonexistentEntityException("The jogo with id " + id + " no longer exists.");
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
            Jogo jogo;
            try {
                jogo = em.getReference(Jogo.class, id);
                jogo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The jogo with id " + id + " no longer exists.", enfe);
            }
            em.remove(jogo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Jogo> findJogoEntities() {
        return findJogoEntities(true, -1, -1);
    }

    public List<Jogo> findJogoEntities(int maxResults, int firstResult) {
        return findJogoEntities(false, maxResults, firstResult);
    }

    private List<Jogo> findJogoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Jogo as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Jogo findJogo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Jogo.class, id);
        } finally {
            em.close();
        }
    }

    public int getJogoCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Jogo as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
