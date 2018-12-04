package kr.co.kyobo.vora;

import kr.co.kyobo.vora.util.TagsUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class NormalVoraTest {
//    @Test
//    public void tagParserTest(){
//        String str = "#tag #tag#tag";
//        log.info(TagsUtil.parseTags(str).toString());
//    }
//
//    @Test
//    public void pagingTest(){
//        int page =2;
//        int total = 21;
//        int limit = 10;
//        int offset = 0;
//        if(page > 1){
//            if(total < page * limit){
//                offset= ((int)Math.ceil(total/limit))*limit;
//            }else{
//                offset= ((page - 1) * limit);
//            }
//        }else{
//            offset= ((page - 1));
//        }
//        log.info("result : "+offset);
//    }
}
