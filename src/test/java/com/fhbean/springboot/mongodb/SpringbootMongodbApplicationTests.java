package com.fhbean.springboot.mongodb;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fhbean.springboot.mongodb.entity.User;
import com.fhbean.springboot.mongodb.reposistory.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootMongodbApplicationTests {

    @Autowired
    private UserRepository userRepository;
    
    @Before
    public void setUp() {
//        userRepository.deleteAll();
    }
    
	@Test
	public void contextLoads() {
	}

    @Test
    public void testSave() throws Exception {
        // 创建三个User，并验证User总数
        userRepository.save(new User(1L, "didi", 30));
        userRepository.save(new User(2L, "mama", 40));
        userRepository.save(new User(3L, "kaka", 50));
        Assert.assertEquals(3, userRepository.findAll().size());
    }
    
    @Test
    public void testFind() throws Exception {
        // 删除一个User，再验证User总数
        Optional<User> optional = userRepository.findById(1L);//.findOne(1L);
        Assert.assertTrue(optional.isPresent());
        if(optional.isPresent()) {
        	System.out.println("----------------------------------------------------------- begin");
        	Assert.assertEquals("didi", optional.get().getUsername());
        	System.out.println("----------------------------------------------------------- end");
        }
    	
    }
    
    @Test
    public void testDelete() throws Exception {
    	userRepository.save(new User(4L, "lisi", 60));
    	Optional<User> optional = userRepository.findById(4L);
    	Assert.assertTrue(optional.isPresent());
    	
    	User u = optional.get();
    	Assert.assertEquals("lisi", u.getUsername());
    	
    	int sizeBefore = userRepository.findAll().size();
    	userRepository.delete(u);
    	int sizeAfter = userRepository.findAll().size();
    	Assert.assertEquals(1, sizeBefore - sizeAfter);
    }
    
//    @Test
//    public void test() throws Exception {
//        // 删除一个User，再验证User总数
//        Optional<User> optional = userRepository.findById(1L);//.findOne(1L);
//        User u = null;
//        if(null != optional) {
//        	optional.get();
//        }
//        userRepository.delete(u);
//        Assert.assertEquals(2, userRepository.findAll().size());
//        // 删除一个User，再验证User总数
//        u = userRepository.findByUsername("mama");
//        userRepository.delete(u);
//        Assert.assertEquals(1, userRepository.findAll().size());
//    }
    
    
}
