package com.ifox.hgx.jpa.entity;

import javax.persistence.*;

@Table(name = "JPA_ORDER")
@Entity
public class Order {
    private Integer id ;
    private String orderName ;
//    private Customer customer ;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "ORDER_NAME")
    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

////  @ManyToOne(fetch = FetchType.LAZY) 懒加载
//    @JoinColumn(name = "CUSTOMER_ID")
//    @ManyToOne(fetch = FetchType.LAZY)
//    public Customer getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(Customer customer) {
//        this.customer = customer;
//    }
}
