package cn.learncoding.jackson.serializer.expand;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

public class JsonTest {

    @Test
    public void testJson() throws JsonProcessingException {
        User user = new User();
        user.setEmail("123@qq.com");
        user.setUsername("0123456789");
        user.setId(1L);
        user.setIdCard("610124200001013315");
        user.setMoney(15.1649847f);
        user.setName("张三");
        user.setPhone("13501165874");
        user.setImageUrl("http://123.com");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);
        System.out.println(json);
    }

}
