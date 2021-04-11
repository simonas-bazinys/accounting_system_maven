package com.accouting.hibernate;


import com.accouting.model.Category;
import com.accouting.model.Payment;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class PaymentHibernate {
    private EntityManagerFactory entityManagerFactory = null;

    public PaymentHibernate(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    private EntityManager getEntityManager() { return entityManagerFactory.createEntityManager(); }

    public void create(Payment payment)
    {
        EntityManager entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entityManager.merge(payment));
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
            Payment payment = null;

            try {
                payment = em.getReference(Payment.class, id);
                //user.getUserID();
            } catch (Exception e) {
                //Pranesti, kad pagal Id nk nerado
                e.printStackTrace();
            }
            em.remove(payment);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }


    public List<Payment> getPaymentList() {
        return getPaymentList(true, -1, -1);
    }

    public List<Payment> getPaymentList(boolean all, int maxRes, int firstRes) {

        EntityManager em = getEntityManager();
        try {

            CriteriaQuery<Object> criteriaQuery = em.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(Payment.class));
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

    public List<Payment> getPaymentsOfCategory(Category category)
    {
        ArrayList<Payment> payments = new ArrayList<Payment>();
        for (Payment payment : getPaymentList())
        {
            if (payment.getCategory().getCategoryID() == category.getCategoryID()) payments.add(payment);
        }
        return payments;
    }

    public void edit(Payment payment) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            payment = em.merge(payment);
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
