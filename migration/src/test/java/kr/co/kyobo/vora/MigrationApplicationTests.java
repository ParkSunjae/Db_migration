package kr.co.kyobo.vora;

import kr.co.kyobo.vora.config.JpaMssqlDataBaseConfig;
import kr.co.kyobo.vora.config.JpaMysqlDataBaseConfig;
import kr.co.kyobo.vora.service.ip.IpService;
import kr.co.kyobo.vora.service.memberAndAccount.MakeMemberAndAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "classpath:application.properties")
@Import({JpaMssqlDataBaseConfig.class, JpaMysqlDataBaseConfig.class})
public class MigrationApplicationTests {

	@Autowired
	private MakeMemberAndAccountService makeMemberAndAccountService;

	@Autowired
	private IpService ipService;
//	@Test
//	public void contextLoads() {
//
//	}

	@Test
	@Transactional
	public void testGetData() throws IOException {
		this.makeMemberAndAccountService.makeMemberAndAccount();
	}

//	@Test
//	public void ipToCityTest() throws IOException {
//		this.ipService.setIpToCity();
//	}

}
