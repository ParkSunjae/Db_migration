package kr.co.kyobo.vora;

import kr.co.kyobo.vora.config.DatabaseConfig;
import kr.co.kyobo.vora.model.database.Account;
import kr.co.kyobo.vora.model.database.Feeds;
import kr.co.kyobo.vora.model.database.Member;
import kr.co.kyobo.vora.model.database.Tags;
import kr.co.kyobo.vora.repository.AccountRepository;
import kr.co.kyobo.vora.repository.FeedsRepository;
import kr.co.kyobo.vora.repository.MemberRepository;
import kr.co.kyobo.vora.repository.TagsRepository;
import kr.co.kyobo.vora.util.TagsUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

//@RunWith(SpringRunner.class)
//@SpringBootTest(properties = "classpath:application.properties")
//@Import(DatabaseConfig.class)
@Slf4j
public class VoraTest {

//    @Autowired
//    private MemberRepository memberRepository;
//    @Autowired
//    private AccountRepository accountRepository;
//    @Autowired
//    private FeedsRepository feedsRepository;
//    @Autowired
//    private TagsRepository tagsRepository;
//
//    @Test
//    public void SHATest(){
//        String str = "test";
//        log.info("test_result : "+SHA256.getHash(str));
//    }
//
//    @Test
//    public void SeedTest(){
//        String str = "test";
//        log.info("test_encode : "+(str = SEED.getEncode(str)));
//        log.info("test_decode : "+SEED.getDecode(str));
//    }
//
//    @Test
//    public void insertIndexText(){
//        Member test_member = new Member();
//        test_member.setNickName("테스트1234");
//        test_member.setYear("1000");
//        //memberRepository.saveMember(test_member);
//        //log.info("member insert idx : "+test_member.getIdx());
//        Account account = new Account();
//        account.setUserIdx(test_member.getIdx());
//        account.setUserPwd(SHA256.getHash("테스트1234"));
//        account.setEmail("test1234@test.co.kr");
//        account.setAccountType("email");
//        account.setDeviceType("WEB");
//        //accountRepository.saveAccount(account);
//        //log.info("account insert idx : "+account.getIdx());
//    }
//
//    @Test
//    public void feedTest(){
//        Feeds feeds = new Feeds();
//        feeds.setIdx(2);
//        feeds.setContents("테스트2");
//
//    }
//
//    @Test
//    public void tagTest(){
////        Tags tags = new Tags();
////        tags.setTag("테스트");
////        tags.setIdx(101);
////        test1
////        agsRepository.save(tags);
////        log.info("saved_info : "+tags.getIdx());
//
////        test2
////        tagsRepository.findByIdx(tags);
////        log.info("search_info : "+tags.toString());
////
////        test3
////        tagsRepository.toggleUseYN(tags);
//
//        List<Tags> tags = TagsUtil.parseTags("#tag #test#뭐야 #베놈#test2#test3#test2#test4");
//        if(tags.size()>0){
//            List<Tags> savedTags = tagsRepository.findByTagMulti(tags);
//            List<Tags> newTags = new ArrayList<>();
//            boolean itsNew;
//            for (Tags tag: tags) {
//                itsNew = true;
//                for (Tags tag_buff: savedTags) {
//                    if(tag.getTag().equals(tag_buff.getTag())) itsNew=false;
//                }
//                if(itsNew)
//                    newTags.add(tag);
//            }
//            if(newTags.size()>0)
////                tagsRepository.saveMulti(newTags);
//
//            tags=savedTags;
//            tags.addAll(newTags);
//        }
//    }
}
