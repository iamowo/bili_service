package com.bili;

import com.bili.entity.PO.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BiliApplicationTests {

	@Test
	void contextLoads() {
		User v = new User();
		v.setName("2333333");
		System.out.println(v);
	}

}
