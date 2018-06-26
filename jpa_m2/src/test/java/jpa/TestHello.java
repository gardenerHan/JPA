package jpa;

import jpa.entity.Customer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class TestHello {
    public static void main(String[] args) {

        //1. 创建 EntityManagerFactory
        String persistenceUnitName = "jpa_m2";
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);


        //2. 创建 EntityManager. 类似于 Hibernate 的 SessionFactory
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //3. 开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        //4. 进行持久化操作
        Customer customer = new Customer();
        customer.setAge(12);
        customer.setEmail("tom@153.com");
        customer.setLastName("Tom");

        entityManager.persist(customer);

        //5. 提交事务
        transaction.commit();

        //6. 关闭 EntityManager
        entityManager.close();

        //7. 关闭 EntityManagerFactory
        entityManagerFactory.close();
    }
}
