package com.accouting.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Data
@NoArgsConstructor
@Entity
public class Receivable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int receivableId;
    private float receivableSum;
    private Date receivableDate;

    @ManyToOne
    Category category;

    public Receivable(float receivableSum, Date receivableDate, Category category) {
        this.receivableSum = receivableSum;
        this.receivableDate = receivableDate;
        this.category = category;
    }

    /*
    public Receivable(int receivableId, float receivableSum, Date receivableDate, Category category) {
        this.receivableId = receivableId;
        this.receivableSum = receivableSum;
        this.receivableDate = receivableDate;
        this.category = category;
    }

    public Receivable(float receivableSum, Date receivableDate, Category category) {
        this.receivableSum = receivableSum;
        this.receivableDate = receivableDate;
        this.category = category;
    }


    public int getReceivableId() {
        return receivableId;
    }

    public void setReceivableId(int receivableId) {
        this.receivableId = receivableId;
    }

    public float getReceivableSum() {
        return receivableSum;
    }

    public void setReceivableSum(float receivableSum) {
        this.receivableSum = receivableSum;
    }

    public Date getReceivableDate() {
        return receivableDate;
    }

    public void setReceivableDate(Date receivableDate) {
        this.receivableDate = receivableDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
     */
}
