package com.ifox.hgx.jpa.spring.service;

import com.ifox.hgx.jpa.spring.dao.PersonDao;
import com.ifox.hgx.jpa.spring.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: ${Description}
 * @Author hanguixian
 * @Date Created in 21:25 2018/7/2
 */

@Service
public class PersonService {

    @Autowired
    private PersonDao personDao ;

    public void save(Person person, Person person2){
        personDao.save(person);
        personDao.save(person2);
    }

}
