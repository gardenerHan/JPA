package com.ifox.hgx.jpa.test;

import com.ifox.hgx.jpa.entity.Customer;
import com.ifox.hgx.jpa.entity.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

public class TestMapping_OneToMany {

    private EntityManagerFactory entityManagerFactory ;
    private EntityManager entityManager ;
    private EntityTransaction transaction ;

    @Before
    public void init(){
        entityManagerFactory = Persistence.createEntityManagerFactory("jpa_m1") ;
        entityManager = entityManagerFactory.createEntityManager() ;
        transaction = entityManager.getTransaction() ;
        transaction.begin();
    }

    @After
    public void destroy(){
        transaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    //单向 1-n 关联关系执行保存时, 一定会多出 UPDATE 语句.
    //因为 n 的一端在插入时不会同时插入外键列.
    //
    @Test
    public void testOneToManyPersist(){
        Customer customer = new Customer() ;
        customer.setLastName("KKK");
        customer.setCreateTime(new Date());
        customer.setBirth(new Date());
        customer.setEmail("KKK@qq.com");
        customer.setAge(34);

        Order order = new Order() ;
        order.setOrderName("KK-01-1");

        Order order1 = new Order() ;
        order1.setOrderName("KK-02-2");

        customer.getOrders().add(order);
        customer.getOrders().add(order1) ;

        entityManager.persist(order);
        entityManager.persist(order);

        entityManager.persist(customer);


    }

    //默认对关联的多的一方使用懒加载的加载策略.
    //可以使用 @OneToMany 的 fetch 属性来修改默认的加载策略
    @Test
    public void testOneToManyFind(){
        Customer customer = entityManager.find(Customer.class,1) ;
        System.out.println(customer.getLastName());
        System.out.println(customer.getOrders());
    }

    //默认情况下, 若删除 1 的一端, 则会先把关联的 n 的一端的外键置空, 然后进行删除.
    //可以通过 @OneToMany 的 cascade 属性来修改默认的删除策略.
    @Test
    public void testOneToManyRemove(){
        Customer customer  = entityManager.find(Customer.class,1) ;
        entityManager.remove(customer);
    }

    @Test
    public void testOneToManyUpdate(){
        Customer customer  = entityManager.find(Customer.class,1) ;
        customer.getOrders().iterator().next().setOrderName("0-xxx-x");
    }






}
