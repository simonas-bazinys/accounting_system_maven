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
public class Payment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;
    private float paymentSum;
    private Date paymentDate;

    @ManyToOne
    Category category;

    public Payment(float paymentSum, Date paymentDate, Category category) {
        this.paymentSum = paymentSum;
        this.paymentDate = paymentDate;
        this.category = category;
    }

    /*
        public Payment(int paymentId, float paymentSum, Date paymentDate, Category category) {
            this.paymentId = paymentId;
            this.paymentSum = paymentSum;
            this.paymentDate = paymentDate;
            this.category = category;
        }

        public Payment(float paymentSum, Date paymentDate, Category category) {
            this.paymentSum = paymentSum;
            this.paymentDate = paymentDate;
            this.category = category;
        }*/
/*
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public float getPaymentSum() {
        return paymentSum;
    }

    public void setPaymentSum(float paymentSum) {
        this.paymentSum = paymentSum;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
*/
    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", paymentSum=" + paymentSum +
                ", paymentDate=" + paymentDate +
                ", category=" + category +
                '}';
    }
}
