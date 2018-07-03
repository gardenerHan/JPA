package com.ifox.hgx.springdata.dao;

import com.ifox.hgx.springdata.entities.Person;
import org.springframework.data.repository.Repository;

/**
 * @Description: ${Description}
 * @Author hanguixian
 * @Date Created in 23:10 2018/7/2
 */
public interface PersonRepository extends Repository<Person,Integer> {

    Person getByLastName(String lastName) ;
}
