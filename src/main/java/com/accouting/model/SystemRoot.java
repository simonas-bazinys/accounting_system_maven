package com.accouting.model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SystemRoot implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int systemId;
    private String companyName;

    @OneToMany
    private List<Category> rootCategories;

    @OneToMany
    private List<User> users;


    public SystemRoot(String companyName) {
        this.companyName = companyName;
        this.rootCategories =new ArrayList<Category>();
        this.users = new ArrayList<User>();
    }


    public int getSystemId() {
        return systemId;
    }

    public void setSystemId(int systemId) {
        this.systemId = systemId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<Category> getRootCategories() {
        return rootCategories;
    }

    public void setRootCategories(List<Category> rootCategories) {
        this.rootCategories = rootCategories;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "SystemRoot{" +
                "systemId=" + systemId +
                ", companyName='" + companyName + '\'' +
                ", rootCategories=" + rootCategories +
                ", users=" + users +
                '}';
    }
}
