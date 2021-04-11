package com.accouting.hibernate;


import com.accouting.model.Category;
import com.accouting.model.Payment;
import com.accouting.model.Receivable;
import com.accouting.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class CategoryHibernate {
    private EntityManagerFactory entityManagerFactory = null;

    public CategoryHibernate(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    private EntityManager getEntityManager() { return entityManagerFactory.createEntityManager(); }

    public void create(Category category)
    {
        EntityManager entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entityManager.merge(category));
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

    public void remove(int id){
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            Category category = null;
            try{
                category = entityManager.getReference(Category.class, id);
                for(User user: category.getResponsibleUsers()){
                    user.getCategoriesResponsible().remove(category);
                }
                category.getResponsibleUsers().clear();
                category.getCategoryID();

            }catch(Exception e){
                e.printStackTrace();
            }
            entityManager.remove(category);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public List<Category> getCategoriesOfParent(Category parentCategory)
    {
        ArrayList<Category> categories = new ArrayList<Category>();

        for (Category category : getCategoryList())
        {
            if (category.getParentCategory() != null)
            {
                if (category.getParentCategory().getCategoryID() == parentCategory.getCategoryID())
                {
                    categories.add(category);
                }
            }
        }

        return categories;
    }

    public List<Category> getRootCategories()
    {
        ArrayList<Category> categories = new ArrayList<Category>();
        for (Category category : getCategoryList())
        {
            if (category.getParentCategory() == null) categories.add(category);
        }
        return categories;
    }

    public Category getCategoryById(int id)
    {
        for (Category category : getCategoryList())
        {
            if (category.getCategoryID() == id) return category;
        }
        return null;
    }

    public List<User> getResponsibleUsersOfCategory(Category category)
    {
        return getCategoryById(category.getCategoryID()).getResponsibleUsers();
    }

    public void update(Category category){
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            category = entityManager.merge(category);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void AddExpense(Category category, Payment payment)
    {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            try {
                category.getExpense().add(payment);
                category = em.merge(category);
                em.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void AddIncome(Category category, Receivable receivable)
    {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            try {
                category.getIncome().add(receivable);
                category = em.merge(category);
                em.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void AddSubCategory(Category category, Category subCategory)
    {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            try {
                category.getSubCategories().add(subCategory);
                em.merge(category);
                em.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void AddResponsibleUser(int  categoryId, int userId) throws Exception {
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

    public void removeResponsibleUser(int categoryId, int userId) throws Exception {
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
    public List<Category> getCategoryList(){
        return getCategoryList(true, -1, -1);
    }

    public List<Category> getCategoryList(boolean all, int maxRes, int firstRes){

        EntityManager entityManager = getEntityManager();
        try{

            CriteriaQuery criteriaQuery = entityManager.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(Category.class));
            Query query = entityManager.createQuery(criteriaQuery);

            if (!all) {
                query.setMaxResults(maxRes);
                query.setFirstResult(firstRes);
            }
            return query.getResultList();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        return null;
    }
}
