package org.yearup.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column (name = "order_id")
    private int orderId;

    @Column (name = "user_id")
    private int userId;

    @Column (name = "date")
    private LocalDateTime orderDate;

    @Column (name = "address")
    private String address;

    @Column (name = "city")
    private String city;

    @Column (name = "state")
    private String state;

    @Column (name = "zip")
    private String zip;

    @Column (name = "shipping_amount")
    private double total;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double shippingAmount) {
        this.total = total;
    }
}


