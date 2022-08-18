package io.codewithwinnie.shopping.domain;

import java.util.Date;
import java.util.StringJoiner;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 17/08/2022
 */

public class Order {
    private int userId;
    private int id;
    private int noOfItems;
    private double totalAmount;
    private Date date;

    public int getUserId() {
        return userId;
    }

    public void setUserId(final int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getNoOfItems() {
        return noOfItems;
    }

    public void setNoOfItems(final int noOfItems) {
        this.noOfItems = noOfItems;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(final double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Order.class.getSimpleName() + "[", "]")
                .add("userId=" + userId)
                .add("id=" + id)
                .add("noOfItems=" + noOfItems)
                .add("totalAmount=" + totalAmount)
                .add("date=" + date)
                .toString();
    }
}
