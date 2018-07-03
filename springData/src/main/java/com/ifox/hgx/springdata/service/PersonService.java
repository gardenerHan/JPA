package com.ifox.hgx.springdata.service;

import com.ifox.hgx.springdata.dao.PersonRepository;
import com.ifox.hgx.springdata.entities.Person;
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
    private PersonRepository personRepository;

    public Person getByLastName(String lastName) {
        return personRepository.getByLastName(lastName);
    }

}
