package com.ifox.hgx.jpa.test;

import com.ifox.hgx.jpa.entity.Customer;
import com.ifox.hgx.jpa.entity.Order;
import org.hibernate.jpa.QueryHints;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;
import java.util.List;

public class TestJPQL {

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


    //需要注意的是位置参数的是位置前加符号”?”，命名参数是名称前是加符号”:”。
    @Test
    public void testHelloJPQL() {
//        String jpql = "FROM Customer c WHERE c.age > ?1";
//        Query query = entityManager.createQuery(jpql);
//        query.setParameter(1, 1);

        String jpql = "FROM Customer c where c.age > :age";

        Query query = entityManager.createQuery(jpql);

        query.setParameter("age", 1);
        List<Customer> customers = query.getResultList();
        System.out.println(customers.size());

        /**
         * 注意:如果是双向关联的 可能会发生:StackOverflowError
         */
//        for (Customer customer:customers) {
//            System.out.println(customer);
//        }
    }

    //默认情况下, 若只查询部分属性, 则将返回 Object[] 类型的结果. 或者 Object[] 类型的 List.
    //也可以在实体类中创建对应的构造器, 然后再 JPQL 语句中利用对应的构造器返回实体类的对象.
    @Test
    public void testPartlyProperties() {
        String jpql = "SELECT new Customer(c.lastName, c.age) FROM Customer c WHERE c.id > ?1";
        List result = entityManager.createQuery(jpql).setParameter(1, 1).getResultList();

        System.out.println(result);
        //输出结果:[Customer{id=null, lastName='CCC', email='null', age=34, createTime=null, birth=null, orders=[]}]
    }

    //createNamedQuery 适用于在实体类前使用 @NamedQuery 标记的查询语句
    @Test
    public void testNameQuery() {
        Customer customer = (Customer) entityManager.createNamedQuery("testNameQuery").setParameter(1, 34).getSingleResult();
        System.out.println(customer);
    }

    //createNativeQuery 适用于本地 SQL
    @Test
    public void test() {
        String sql = "select age from JPA_CUSTOMTERS where id = ?";
        Query query = entityManager.createNativeQuery(sql).setParameter(1, 1);
        int age = (int) query.getSingleResult();
        System.out.println("age:" + age);
    }


    //使用 hibernate 的查询缓存.
    @Test
    public void testQueryCache(){
        String jpql = "FROM Customer c WHERE c.age > ?1";
        Query query = entityManager.createQuery(jpql).setHint(QueryHints.HINT_CACHEABLE, true);

        //占位符的索引是从 1 开始
        query.setParameter(1, 1);
        List<Customer> customers = query.getResultList();
        System.out.println(customers.size());

        query = entityManager.createQuery(jpql).setHint(QueryHints.HINT_CACHEABLE, true);

        //占位符的索引是从 1 开始
        query.setParameter(1, 1);
        customers = query.getResultList();
        System.out.println(customers.size());
    }

    //查询 order 数量大于 2 的那些 Customer
    @Test
    public void testGroupBy(){
        String jpql = "SELECT o.customer FROM Order o "
                + "GROUP BY o.customer "
                + "HAVING count(o.id) >= 2";
        List<Customer> customers = entityManager.createQuery(jpql).getResultList();

        System.out.println(customers);
    }

    @Test
    public void testOrderBy(){
        String jpql = "FROM Customer c WHERE c.age > ?1 ORDER BY c.age DESC";
        Query query = entityManager.createQuery(jpql).setHint(QueryHints.HINT_CACHEABLE, true);

        //占位符的索引是从 1 开始
        query.setParameter(1, 1);
        List<Customer> customers = query.getResultList();
        System.out.println(customers.size());
    }

    /**
     * JPQL 的关联查询同 HQL 的关联查询.
     *
     * LEFT OUTER JOIN FETCH c.orders
     *
     * FETCH 很关键 会自动封装类
     */
    @Test
    public void testLeftOuterJoinFetch(){
        String jpql = "select c FROM Customer  c LEFT OUTER JOIN FETCH c.orders  WHERE c.id = ?1";

        Customer customer =
                (Customer) entityManager.createQuery(jpql).setParameter(1, 1).getSingleResult();
        System.out.println(customer);
        System.out.println(customer.getLastName());
        System.out.println(customer.getOrders().size());

//		List<Object[]> result = entityManager.createQuery(jpql).setParameter(1, 12).getResultList();
//		System.out.println(result);
    }

    @Test
    public void testSubQuery(){
        //查询所有 Customer 的 lastName 为 YY 的 Order
        String jpql = "SELECT o FROM Order o "
                + "WHERE o.customer = (SELECT c FROM Customer c WHERE c.lastName = ?1)";

        Query query = entityManager.createQuery(jpql).setParameter(1, "KKK");
        List<Order> orders = query.getResultList();
        System.out.println(orders.size());
    }

    //使用 jpql 内建的函数
    @Test
    public void testJpqlFunction(){
        String jpql = "SELECT lower(c.email) FROM Customer c";

        List<String> emails = entityManager.createQuery(jpql).getResultList();
        System.out.println(emails);
    }

    //可以使用 JPQL 完成 UPDATE 和 DELETE 操作.
    @Test
    public void testExecuteUpdate(){
        String jpql = "UPDATE Customer c SET c.lastName = ?1 WHERE c.id = ?2";
        Query query = entityManager.createQuery(jpql).setParameter(1, "YYY").setParameter(2, 1);

        query.executeUpdate();
    }



}
