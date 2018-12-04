package kr.co.kyobo.vora;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MigrationApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MigrationApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		if (args.length < 1) {
//			throw new IllegalArgumentException("no args given");
//		}
//		if(args[0].equals("call")){
//			//this.memberService.checkUserStatus();
//		}
	}
}
