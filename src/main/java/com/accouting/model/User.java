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
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String address;
    private String companyCode;
    private String username;
    private String password;
    @ManyToMany(cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Category> categoriesResponsible;
    private String userType;

    public User(String name, String surname, String email, String phone, String address, String companyCode, String username, String password, List<Category> categoriesResponsible, String userType) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.companyCode = companyCode;
        this.username = username;
        this.password = password;
        this.categoriesResponsible = categoriesResponsible;
        this.userType = userType;
    }


    /*
    public User(int userID, String name, String surname, String email, String phone, String address, String companyCode, String username, String password, List<Category> categoriesResponsible, String userType) {
        this.userID = userID;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.companyCode = companyCode;
        this.username = username;
        this.password = password;
        this.categoriesResponsible = categoriesResponsible;
        this.userType = userType;
    }

    public User(String name, String surname, String email, String phone, String address, String companyCode, String username, String password, List<Category> categoriesResponsible, String userType) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.companyCode = companyCode;
        this.username = username;
        this.password = password;
        this.categoriesResponsible = categoriesResponsible;
        this.userType = userType;
    }

    public User() {
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Category> getCategoriesResponsible() {
        return categoriesResponsible;
    }

    public void setCategoriesResponsible(List<Category> categoriesResponsible) {
        this.categoriesResponsible = categoriesResponsible;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
    */

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", name='" + name + '\'';
    }
}