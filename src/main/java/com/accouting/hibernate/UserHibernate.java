package com.accouting.hibernate;



import com.accouting.model.Category;
import com.accouting.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserHibernate {
    private EntityManagerFactory entityManagerFactory = null;

    public UserHibernate(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    private EntityManager getEntityManager() { return entityManagerFactory.createEntityManager(); }

    public void create(User user)
    {
            EntityManager entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entityManager.merge(user));
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

    public User getUserById(int id)
    {
        for (User user : getUsersList())
        {
            if (user.getUserID() == id) return user;
        }
        return null;
    }

    public List<Category> getCategoriesResponsible(User user)
    {
        return getUserById(user.getUserID()).getCategoriesResponsible();
    }

    public void addCategoryResponsible(int categoryId, int userId) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            try {
                Category category = em.find(Category.class, categoryId);
                User user = em.find(User.class, userId);
                user.getCategoriesResponsible().add(category);
                category.getResponsibleUsers().add(user);
                em.getTransaction().commit();
            } catch (EntityNotFoundException enfe) {
                throw new Exception("Error when removing responsible User from category", enfe);
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void removeCategoryResponsible(int categoryId, int userId) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            try {
                Category category = em.find(Category.class, categoryId);
                User user = em.find(User.class, userId);
                user.getCategoriesResponsible().remove(category);
                category.getResponsibleUsers().remove(user);
                em.getTransaction().commit();
            } catch (EntityNotFoundException enfe) {
                throw new Exception("Error when removing responsible User from category", enfe);
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void remove(int id) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User user = null;

            try {
                user = em.getReference(User.class, id);
                //user.getUserID();
            } catch (Exception e) {
                //Pranesti, kad pagal Id nk nerado
                e.printStackTrace();
            }
            em.remove(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<User> getUsersList() {
        return getUsersList(true, -1, -1);
    }

    public List getUsersList(boolean all, int maxRes, int firstRes) {

        EntityManager em = getEntityManager();
        try {

            CriteriaQuery<Object> criteriaQuery = em.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(User.class));
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

    public void edit(User user) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            user = em.merge(user);
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
