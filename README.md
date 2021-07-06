## 功能描述

### 数据脱敏注解 @Masking

作用于 String 类型。

| ***\*属性\**** | ***\*默认值\****            | ***\*含义\****                                               |
| -------------- | --------------------------- | ------------------------------------------------------------ |
| type           | MaskingType.CUSTOMER        | 脱敏类型，默认自定义，可供选择常用的脱敏选项：PHONE、NAME、ID_CARD、BANK_CARD、EMAIL 。 |
| customer       | DataMaskingSerializer.class | 当 type 为 CUSTOMER 时，自定义类实现 DataMaskingSerializer 接口，并进行声明，优先级最高。 |
| beginIndex     | -1                          | 当 type 为 CUSTOMER 时，且未自定义 customer 实现时，从下标 beginIndex（包含） 开始进行脱敏处理。 |
| endIndex       | 0                           | 当 type 为 CUSTOMER 时，且未自定义 customer 实现时。当 endIndex 为正数时，脱敏处理截止到下标 endIndex（不包含）；当 endIndex 为 0 时，脱敏处理到结尾，即从下标 beginIndex 后全部进行脱敏处理；当 endIndex 为负数时，脱敏处理截止到，从结尾倒数的下标 \|endIndex\|（不包含）； |
| replaceChar    | *                           | 脱敏替换字符。                                               |

### 数据格式化注解 @Format

作用于 CharSequence 及其子类、Number 及其子类。

| ***\*属性\**** | ***\*默认值\**** | ***\*含义\****                                               |
| -------------- | ---------------- | ------------------------------------------------------------ |
| value          | ''               | 格式化模板，原数据使用第一个替换符进行格式化替换。可参见 java.util.Formatter。 |

## 使用示例

```java
// User
package cn.learncoding.jackson.serializer.expand;

import lombok.Data;

@Data
public class User {

    private Long id;

    @Masking(type = MaskingType.CUSTOMER, beginIndex = 4, endIndex = -4)
    private String username;

    @Masking(type = MaskingType.NAME)
    private String name;

    @Masking(type = MaskingType.PHONE)
    private String phone;

    @Masking(type = MaskingType.ID_CARD)
    private String idCard;

    @Masking(type = MaskingType.EMAIL)
    private String email;

    @Format("%s_100x100")
    private String imageUrl;

    @Format("%.2f")
    private Float money;
}

// JsonTest
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
```

### 结果

```json
{
    "id":1,
    "username":"0123**6789",
    "name":"张*",
    "phone":"135****5874",
    "idCard":"6101**********3315",
    "email":"1**@qq.com",
    "imageUrl":"http://123.com_100x100",
    "money":"15.16"
}
```

可以看到:

- username 使用自定义脱敏方式，前面保留4位，后面保留4位；
- name、phone、idCard、email 都使用默认的脱敏方式;
- url 使用自定义格式化，后面加上了 _100x100 字符串；
- money 也使用自定义格式化，将数字类型进行了格式化;