package com.ifox.hgx.jpa.spring.dao;

import com.ifox.hgx.jpa.spring.entities.Person;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @Description: ${Description}
 * @Author hanguixian
 * @Date Created in 21:18 2018/7/2
 */

@Repository
public class PersonDao {

    //标注成员变量
    @PersistenceContext
    private EntityManager entityManager ;

    @Transactional
    public void save(Person person){
        entityManager.persist(person);
    }
}
