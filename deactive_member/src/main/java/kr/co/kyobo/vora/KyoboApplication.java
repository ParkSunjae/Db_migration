package kr.co.kyobo.vora;

import kr.co.kyobo.vora.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class KyoboApplication implements CommandLineRunner {

    @Autowired
    private MemberService memberService;

    public static void main(String[] args) {
        log.info("Starting to run...");
        SpringApplication.run(KyoboApplication.class, args);
        log.info("Completed the run...");

    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length < 1) {
            throw new IllegalArgumentException("no args given");
        }
        if(args[0].equals("call")){
            this.memberService.checkUserStatus();
        }
    }
}
