package com.ifox.hgx.jpa.spring.entities;

import javax.persistence.*;

/**
 * @Description: ${Description}
 * @Author hanguixian
 * @Date Created in 19:52 2018/7/2
 */


@Table(name = "JPA_PERSON")
@Entity
public class Person {
    private Integer id ;
    private String lastName ;
    private String email ;
    private Integer age ;

    public Person() {
    }

    public Person(String lastName, String email, Integer age) {
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    //    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
