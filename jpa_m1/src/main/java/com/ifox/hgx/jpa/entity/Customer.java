package com.ifox.hgx.jpa.entity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "JPA_CUSTOMTERS")
@Entity
public class Customer {


    private Integer id ;
    private String lastName ;

    private String email ;
    private Integer age ;

    private Date createTime ;
    private Date birth ;

    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Temporal(TemporalType.DATE)
    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    // pkColumnName = "PK_NAME",pkColumnValue = "CUSTOMER_ID"确定行
    // valueColumnName = "PK_VALUE"确定列
    //allocationSize 每次增加多少
//    @TableGenerator(name = "ID_GENERATOR",table = "jap_id_generators",
//            pkColumnName = "PK_NAME",pkColumnValue = "CUSTOMER_ID",
//            valueColumnName = "PK_VALUE",initialValue = 1,allocationSize = 100
//
//    )
//    @GeneratedValue(strategy = GenerationType.TABLE,generator = "ID_GENERATOR")

//    GeneratedValue 生成方式:策略为 GenerationType.AUTO 自动选择
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(length = 50)
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

    //不是需要映射的字段的 需要加上注解Transient
    @Transient
    public String getInfo(){
        return "lastName:"+lastName+" Email:" +email ;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", createTime=" + createTime +
                ", birth=" + birth +
                '}';
    }
}
