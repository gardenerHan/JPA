package com.ifox.hgx.jap.spring;

import com.ifox.hgx.jpa.spring.entities.Person;
import com.ifox.hgx.jpa.spring.service.PersonService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;


/**
 * @Description: ${Description}
 * @Author hanguixian
 * @Date Created in 19:03 2018/7/2
 */
public class JPATest {

    private ApplicationContext applicationContext = null ;
    private PersonService personService = null ;

    {
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml") ;
        personService = applicationContext.getBean(PersonService.class) ;
    }

    @Test
    public void testDataSource() throws Exception{
        DataSource dataSource = applicationContext.getBean(DataSource.class) ;
        Connection connection = dataSource.getConnection();
        System.out.println(connection);

    }

    @Test
    public void testPersonSave(){
        Person person = new Person("AAA","12132@qq.com",13) ;
        Person person1 = new Person("BBB","12312@qq.com",23) ;

        personService.save(person,person1);


    }
}
