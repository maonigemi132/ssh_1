package com.bdqn;

import com.bdqn.dao.UserMapping;
import com.bdqn.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public  class MybaitplusdemoApplicationTests {

    @Resource
    private UserMapping userMapping;

    @Test
    public  void test() {
            List<User> users=userMapping.selectList(null);
        for (User user : users) {
            System.out.println(user);
        }


    }
}
