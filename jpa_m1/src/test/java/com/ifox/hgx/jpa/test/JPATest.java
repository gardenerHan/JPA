package com.ifox.hgx.jpa.test;

import com.ifox.hgx.jpa.entity.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

public class JPATest {

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

    //类似Hibernate 中Session的 get的方法
    @Test
    public void testFind(){
        Customer customer = entityManager.find(Customer.class,1) ;
        System.out.println("--------------------------------------");
        System.out.println(customer);
    }
    /*
    Hibernate:
    select
        customer0_.id as id1_0_0_,
        customer0_.age as age2_0_0_,
        customer0_.birth as birth3_0_0_,
        customer0_.createTime as createTi4_0_0_,
        customer0_.email as email5_0_0_,
        customer0_.LAST_NAME as LAST_NAM6_0_0_
    from
        JPA_CUSTOMTERS customer0_
    where
        customer0_.id=?
    --------------------------------------
    Customer{id=1, lastName='masterKK', email='1212212@qq.com', age=12, createTime=2018-06-21 22:01:55.964, birth=2018-06-21}

     */

    //类似Hibernate 中Session的 load的方法
    @Test
    public void testLoad(){
        Customer customer = entityManager.getReference(Customer.class,1);
        //返回了代理对象，可能出现懒加载异常
        System.out.println(customer.getClass().getName());
        System.out.println("--------------------------------------");
        System.out.println(customer);
    }
    /*

    com.ifox.hgx.jpa.entity.Customer$HibernateProxy$uCEfpt3v
    --------------------------------------
    Hibernate:
    select
        customer0_.id as id1_0_0_,
        customer0_.age as age2_0_0_,
        customer0_.birth as birth3_0_0_,
        customer0_.createTime as createTi4_0_0_,
        customer0_.email as email5_0_0_,
        customer0_.LAST_NAME as LAST_NAM6_0_0_
    from
        JPA_CUSTOMTERS customer0_
    where
        customer0_.id=?
    Customer{id=1, lastName='masterKK', email='1212212@qq.com', age=12, createTime=2018-06-21 22:01:55.964, birth=2018-06-21}

     */



    /*
        类似于 hibernate 的 save 方法. 使对象由临时状态变为持久化状态.
	    和 hibernate 的 save 方法的不同之处: 若对象有 id, 则不能执行 insert 操作, 而会抛出异常.
     */
    @Test
    public void testPersist(){
        Customer customer = new Customer() ;
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());
        customer.setLastName("AAA");
        customer.setEmail("AAA@163.com");
        customer.setAge(10);
        //customer.setId(100);

        entityManager.persist(customer);
        System.out.println(customer.getId());
    }

    //类似于 hibernate 中 Session 的 delete 方法. 把对象对应的记录从数据库中移除
    //但注意: 该方法只能移除 持久化 对象. 而 hibernate 的 delete 方法实际上还可以移除 游离对象.
    @Test
    public void testRemove(){
//        Customer customer = new Customer() ;
//        customer.setId(1);
//        此时数据库中有这个对象对应的数据，但是和EntityManager没有关联，是一个游离对象

        Customer customer = entityManager.find(Customer.class,2) ;
        entityManager.remove(customer);
    }

    /**
     * 总的来说: 类似于 hibernate Session 的 saveOrUpdate 方法.
     */
    //1. 若传入的是一个临时对象
    //会创建一个新的对象, 把临时对象的属性复制到新的对象中, 然后对新的对象执行持久化操作. 所以
    //新的对象中有 id, 但以前的临时对象中没有 id.
    @Test
    public void testMerge1(){
        Customer customer = new Customer();
        customer.setAge(18);
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());
        customer.setEmail("cc@163.com");
        customer.setLastName("CC");

        Customer customer2 = entityManager.merge(customer);

        System.out.println("customer#id:" + customer.getId());
        System.out.println("customer2#id:" + customer2.getId());
    }


    //若传入的是一个游离对象, 即传入的对象有 OID.
    //1. 若在 EntityManager 缓存中没有该对象
    //2. 若在数据库中也没有对应的记录
    //3. JPA 会创建一个新的对象, 然后把当前游离对象的属性复制到新创建的对象中
    //4. 对新创建的对象执行 insert 操作.
    @Test
    public void testMerge2(){
        Customer customer = new Customer();
        customer.setAge(18);
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());
        customer.setEmail("dd@163.com");
        customer.setLastName("DD");

        customer.setId(100);

        Customer customer2 = entityManager.merge(customer);

        System.out.println("customer#id:" + customer.getId());
        System.out.println("customer2#id:" + customer2.getId());
    }


    //若传入的是一个游离对象, 即传入的对象有 OID.
    //1. 若在 EntityManager 缓存中没有该对象
    //2. 若在数据库中也有对应的记录
    //3. JPA 会查询对应的记录, 然后返回该记录对一个的对象, 再然后会把游离对象的属性复制到查询到的对象中.
    //4. 对查询到的对象执行 update 操作.
    @Test
    public void testMerge3(){
        Customer customer = new Customer();
        customer.setAge(18);
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());
        customer.setEmail("ee@163.com");
        customer.setLastName("EE");

        customer.setId(4);

        Customer customer2 = entityManager.merge(customer);

        System.out.println(customer == customer2); //false
    }

    //若传入的是一个游离对象, 即传入的对象有 OID.
    //1. 若在 EntityManager 缓存中有对应的对象
    //2. JPA 会把游离对象的属性复制到查询到EntityManager 缓存中的对象中.
    //3. EntityManager 缓存中的对象执行 UPDATE.
    @Test
    public void testMerge4(){
        Customer customer = new Customer();
        customer.setAge(18);
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());
        customer.setEmail("dd@163.com");
        customer.setLastName("DD");

        customer.setId(4);
        Customer customer2 = entityManager.find(Customer.class, 4);

        entityManager.merge(customer);

        System.out.println(customer == customer2); //false
    }


    /**
     * 同 hibernate 中 Session 的 refresh 方法.
     */
    @Test
    public void testRefresh(){

        Customer customer = entityManager.find(Customer.class, 1);
        customer = entityManager.find(Customer.class, 1);

        entityManager.refresh(customer);
    }

    /**
     * 同 hibernate 中 Session 的 flush 方法.
     */
    @Test
    public void testFlush(){
        Customer customer = entityManager.find(Customer.class, 1);
        System.out.println(customer);

        customer.setLastName("AA");

        entityManager.flush();
    }
}
