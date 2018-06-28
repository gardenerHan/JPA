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

public class TestMapping_TowWayOneToMany {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction transaction;

    @Before
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpa_m1");
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
        transaction.begin();
    }

    @After
    public void destroy() {
        transaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    //若是双向 1-n 的关联关系, 执行保存时
    //若先保存 n 的一端, 再保存 1 的一端, 默认情况下, 会多出 n 条 UPDATE 语句.
    //若先保存 1 的一端, 则会多出 n 条 UPDATE 语句
    //在进行双向 1-n 关联关系时, 建议使用 n 的一方来维护关联关系, 而 1 的一方不维护关联系, 这样会有效的减少 SQL 语句.
    //注意: 若在 1 的一端的 @OneToMany 中使用 mappedBy 属性, 则 @OneToMany 端就不能再使用 @JoinColumn 属性了.
    @Test
    public void testTowWayOneToManyPersist() {
        Customer customer = new Customer();
        customer.setLastName("CCC");
        customer.setCreateTime(new Date());
        customer.setBirth(new Date());
        customer.setEmail("CCC@qq.com");
        customer.setAge(34);

        Order order = new Order();
        order.setOrderName("CC-01-1");

        Order order1 = new Order();
        order1.setOrderName("CC-02-2");

        customer.getOrders().add(order);
        customer.getOrders().add(order1);

        order.setCustomer(customer);
        order1.setCustomer(customer);

        //3insert 2update
//        entityManager.persist(order);
//        entityManager.persist(order);
//
//        entityManager.persist(customer);

        //如果使用了mappedBy = "customer" 则只用3条insert语句
        entityManager.persist(customer);

        entityManager.persist(order);
        entityManager.persist(order);


    }

    //可以使用 @OneToMany 的 fetch 属性来修改默认的加载策略,可以使用 @OneToMany 的 fetch 属性来修改默认的加载策略
    @Test
    public void testTowWayOneToManyFind(){
        Customer customer = entityManager.find(Customer.class, 1);
        System.out.println(customer.getLastName());

        System.out.println(customer.getOrders().size());
    }

    //默认情况下, 若删除 1 的一端, 则会先把关联的 n 的一端的外键置空, 然后进行删除.
    //可以通过 @OneToMany 的 cascade 属性来修改默认的删除策略. CascadeType.REMOVE 级联删除
    @Test
    public void tesTowWayOneToManyRemove(){
        Customer customer = entityManager.find(Customer.class, 3);
        entityManager.remove(customer);
    }

    @Test
    public void testTowWayUpdate(){
        Customer customer = entityManager.find(Customer.class, 1);
        customer.getOrders().iterator().next().setOrderName("O-XXX-1");

    }

}
