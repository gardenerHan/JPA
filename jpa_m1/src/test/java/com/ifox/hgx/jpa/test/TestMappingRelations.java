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
import java.math.BigDecimal;
import java.util.Date;

public class TestMappingRelations {

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
    public void distroy(){
        transaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    public void testManyToOnePersist(){
        Customer customer = new Customer();
        customer.setAge(19);
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());
        customer.setEmail("GG@163.com");
        customer.setLastName("GG");

        Order order1 = new Order() ;
        Order order2 = new Order() ;
        order1.setOrderName("GG-0-1");
        order2.setOrderName("GG-0-2");

        order1.setCustomer(customer);
        order2.setCustomer(customer);

        //3条insert sql语句
//        entityManager.persist(customer);
//        entityManager.persist(order1);
//        entityManager.persist(order2);

        //3条insert 2条update
        entityManager.persist(order1);
        entityManager.persist(order2);
        entityManager.persist(customer);
    }

    //默认不使用懒加载
    @Test
    public void testManyToOneFind(){
        Order order = entityManager.find(Order.class,1) ;
        System.out.println(order.getOrderName());
        System.out.println(order.getCustomer().getLastName());
    }

    @Test
    public void testManyToOneRemove(){
        Order order = entityManager.find(Order.class,1) ;
        System.out.println(order.getOrderName());
        System.out.println(order.getCustomer().getLastName());
    }

    @Test
    public void testManyToOneUpdate(){
        Order order = entityManager.find(Order.class,1) ;
        order.getCustomer().setLastName("FF");

    }

    //不能直接删除 1 的一端, 因为有外键约束.
    @Test
    public void testManyToOneDelete(){
//        Order order = entityManager.find(Order.class,1) ;
//        entityManager.remove(order);

        Customer customer = entityManager.find(Customer.class,1) ;
        entityManager.remove(customer);
    }


}
