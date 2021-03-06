package com.ifox.hgx.jpa;

import com.ifox.hgx.jpa.entity.Customer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

public class Test1 {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa_m1") ;
        EntityManager entityManager = entityManagerFactory.createEntityManager() ;
        EntityTransaction transaction = entityManager.getTransaction() ;
        transaction.begin();

        Customer customer = new Customer();
        customer.setAge(12);
        customer.setEmail("1212212@qq.com");
        customer.setLastName("masterKK");
        customer.setCreateTime(new Date());
        customer.setBirth(new Date());
        entityManager.persist(customer);

        transaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
