package com.axing;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

@SpringBootTest
class ReggieTakeOutApplicationTests {

    @Test
    void contextLoads() {
        BigDecimal amount = new BigDecimal(1.34);
        Integer number = 4;
        BigDecimal s = amount.multiply(new BigDecimal(number)).setScale(2,BigDecimal.ROUND_HALF_UP);//乘以,四舍五入
        BigDecimal s2 = amount.multiply(new BigDecimal(number));
        long id = IdWorker.getId();
        System.out.println(id);
        System.out.println(s);
        System.out.println(s2);
    }

}
