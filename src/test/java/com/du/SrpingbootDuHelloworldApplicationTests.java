package com.du;

import com.du.pojo.Dog;
import com.du.pojo.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SrpingbootDuHelloworldApplicationTests {

    //自动装配 使用注解方式赋值
    @Autowired
    Dog dog;

    //自动装配 使用Yaml方式赋值
    @Autowired
    Person person;

    @Test
    void contextLoads() {
        System.out.println(dog);
        System.out.println("person = " + person);
    }

}
