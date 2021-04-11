package com.accouting.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryID;
    private String categoryName;

    @ManyToMany(mappedBy = "categoriesResponsible", cascade = { CascadeType.MERGE, CascadeType.REMOVE })
    @OrderBy
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> responsibleUsers;
    @OneToMany(mappedBy = "parentCategory",cascade = {CascadeType.MERGE})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Category> subCategories;
    @ManyToOne
    private Category parentCategory;
    private float overallFinances;
    @OneToMany(mappedBy = "category",cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Receivable> income;
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Payment> expense;

    public Category(String categoryName, List<User> responsibleUsers, List<Category> subCategories, Category parentCategory, float overallFinances, List<Receivable> income, List<Payment> expense) {
        this.categoryName = categoryName;
        this.responsibleUsers = responsibleUsers;
        this.subCategories = subCategories;
        this.parentCategory = parentCategory;
        this.overallFinances = overallFinances;
        this.income = income;
        this.expense = expense;
    }

    /*
    public Category(int categoryID, String categoryName, List<User> responsibleUsers, List<Category> subCategories, Category parentCategory, float overallFinances, List<Receivable> income, List<Payment> expense) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.responsibleUsers = responsibleUsers;
        this.subCategories = subCategories;
        this.parentCategory = parentCategory;
        this.overallFinances = overallFinances;
        this.income = income;
        this.expense = expense;
    }

    public Category(String categoryName, List<User> responsibleUsers, List<Category> subCategories, Category parentCategory, float overallFinances, List<Receivable> income, List<Payment> expense) {
        this.categoryName = categoryName;
        this.responsibleUsers = responsibleUsers;
        this.subCategories = subCategories;
        this.parentCategory = parentCategory;
        this.overallFinances = overallFinances;
        this.income = income;
        this.expense = expense;
    }


    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<User> getResponsibleUsers() {
        return responsibleUsers;
    }

    public void setResponsibleUsers(List<User> responsibleUsers) {
        this.responsibleUsers = responsibleUsers;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public float getOverallFinances() {
        return overallFinances;
    }

    public void setOverallFinances(float overallFinances) {
        this.overallFinances = overallFinances;
    }

    public List<Receivable> getIncome() {
        return income;
    }

    public void setIncome(List<Receivable> income) {
        this.income = income;
    }

    public List<Payment> getExpense() {
        return expense;
    }

    public void setExpense(List<Payment> expense) {
        this.expense = expense;
    }*/

    @Override
    public String toString() {
        return "Category{" +
                "categoryID=" + categoryID +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
