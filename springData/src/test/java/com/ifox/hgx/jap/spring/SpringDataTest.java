package com.ifox.hgx.jap.spring;

import com.ifox.hgx.springdata.entities.Person;
import com.ifox.hgx.springdata.service.PersonService;
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
public class SpringDataTest {

    private ApplicationContext applicationContext = null;
    private PersonService personService = null;

    {
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        personService = applicationContext.getBean(PersonService.class);
    }

    @Test
    public void testDataSource() throws Exception {
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        Connection connection = dataSource.getConnection();
        System.out.println(connection);

    }

    @Test
    public void testPersonFindByLastName() {
        Person person = personService.getByLastName("sjsja") ;

        System.out.println(person);

    }
}
