package com.accouting.hibernate;


import com.accouting.model.SystemRoot;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class SystemRootHibernate {
    private EntityManagerFactory entityManagerFactory = null;

    public SystemRootHibernate(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    private EntityManager getEntityManager() { return entityManagerFactory.createEntityManager(); }

    public void create(SystemRoot systemRoot)
    {
        EntityManager entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entityManager.merge(systemRoot));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (entityManager != null)
            {
                entityManager.close();
            }
        }
    }

    public void edit(SystemRoot systemRoot) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            systemRoot = em.merge(systemRoot);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = Integer.toString(systemRoot.getSystemId());
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SystemRoot> getSystemRoot() {

        EntityManager em = getEntityManager();
        try {

            CriteriaQuery criteriaQuery = em.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(SystemRoot.class));
            Query query = em.createQuery(criteriaQuery);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }
}
