package com.accouting.hibernate;




import com.accouting.model.Category;
import com.accouting.model.Receivable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class ReceivableHibernate {
    private EntityManagerFactory entityManagerFactory = null;

    public ReceivableHibernate(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    private EntityManager getEntityManager() { return entityManagerFactory.createEntityManager(); }

    public void create(Receivable receivable)
    {
        EntityManager entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entityManager.merge(receivable));
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

    public void remove(int id) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Receivable receivable = null;

            try {
                receivable = em.getReference(Receivable.class, id);
                //user.getUserID();
            } catch (Exception e) {
                //Pranesti, kad pagal Id nk nerado
                e.printStackTrace();
            }
            em.remove(receivable);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }


    public List<Receivable> getReceivableList() {
        return getReceivableList(true, -1, -1);
    }

    public List<Receivable> getReceivableList(boolean all, int maxRes, int firstRes) {

        EntityManager em = getEntityManager();
        try {

            CriteriaQuery criteriaQuery = em.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(Receivable.class));
            Query query = em.createQuery(criteriaQuery);

            if (!all) {
                query.setMaxResults(maxRes);
                query.setFirstResult(firstRes);
            }
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

    public List<Receivable> getReceivablesOfCategory(Category category)
    {
        ArrayList<Receivable> receivables = new ArrayList<Receivable>();
        for (Receivable receivable : getReceivableList())
        {
            if (receivable.getCategory().getCategoryID() == category.getCategoryID()) receivables.add(receivable);
        }
        return receivables;
    }

    public void edit(Receivable receivable) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            receivable = em.merge(receivable);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
