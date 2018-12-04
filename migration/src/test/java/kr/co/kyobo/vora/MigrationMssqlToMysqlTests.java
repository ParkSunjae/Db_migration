package kr.co.kyobo.vora;

import kr.co.kyobo.vora.config.JpaMssqlDataBaseConfig;
import kr.co.kyobo.vora.config.JpaMysqlDataBaseConfig;
import kr.co.kyobo.vora.model.database.mssql.*;
import kr.co.kyobo.vora.model.database.mysql.*;
import kr.co.kyobo.vora.repository.mssql.*;
import kr.co.kyobo.vora.repository.mysql.*;
import kr.co.kyobo.vora.service.memberAndAccount.MakeMemberAndAccountService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(properties = "classpath:application.properties")
@Import({JpaMssqlDataBaseConfig.class, JpaMysqlDataBaseConfig.class})
public class MigrationMssqlToMysqlTests {
    @Autowired
    @Qualifier("mssqlEntityManager")
    private EntityManager mssqlEntityManager;

	@Autowired
	private SnsTagMssqlRepository snsTagMssqlRepository;
    @Autowired
    private TagsRepository tagsRepository;
    List<Tags> tagsList;

    @Autowired
    private BoardV1MssqlRepository boardV1MssqlRepository;
    @Autowired
    private NoticeRepository noticeRepository;
    List<Notice> noticeList;

    @Autowired
    private SnsFeedMssqlRepository snsFeedMssqlRepository;
    @Autowired
    private SnsFeedFileMssqlRepository snsFeedFileMssqlRepository;
    @Autowired
    private SnsFeedTagMssqlRepository snsFeedTagMssqlRepository;
    @Autowired
    private SnsFeedAuserMssqlRepository snsFeedAuserMssqlRepository;
    @Autowired
    private FilesRepository filesRepository;
    @Autowired
    private FeedFilesRepository feedFilesRepository;
    List<Files> filesList;
    List<FeedFiles> feedFilesList;

    @Autowired
    private AccountRepository accountRepository;


	@Test
    @Transactional
	public void testMigrationTagsData(){
        Page<SnsTag> snsTagList;
        PageRequest pageRequest;
        int page = 0;
        List<Tags> snsTagAllList = new ArrayList<>();
        do{
            pageRequest = PageRequest.of(page++,10000,new Sort(Sort.Direction.ASC,"idx"));
            snsTagList = snsTagMssqlRepository.findAll(pageRequest);
            tagsList = new ArrayList<>();
            snsTagList.forEach(snsTag -> {
                Tags tags = new Tags();
                tags.setTag(snsTag.getTag());
                tags.setHits(snsTag.getHits());
                tags.setIdx(snsTag.getIdx().intValue());
                tagsList.add(tags);
            } );
            snsTagAllList.addAll(tagsList);
            tagsRepository.saveAll(tagsList);
            tagsRepository.flush();
        }while(snsTagList.hasNext());

        Page<SnsFeedTag> snsFeedTagList;
        page = 0;
        do{
            pageRequest = PageRequest.of(page++,10000,new Sort(Sort.Direction.ASC,"tag_name"));
            snsFeedTagList = snsFeedTagMssqlRepository.findAllUncontainSnsTag(pageRequest);
            tagsList = new ArrayList<>();
            snsFeedTagList.forEach(snsTag -> {
                Tags tags = new Tags();
                tags.setTag(snsTag.getTagName());
                tags.setHits(snsTag.getHits());
                tagsList.add(tags);
            } );
            snsTagAllList.addAll(tagsList);
            tagsRepository.saveAll(tagsList);
            tagsRepository.flush();
        }while(snsFeedTagList.hasNext());
	}

	@Test
    @Transactional
    public void testMigrationNoticeData(){
        List<BoardV1> boardV1List;
        boardV1List = boardV1MssqlRepository.findAll();
        noticeList = new ArrayList<>();
        boardV1List.forEach(boardV1 -> {
            Notice notice = new Notice();
            notice.setIdx(boardV1.getBIdx());
            notice.setTitle(boardV1.getBTitle());
            notice.setContent(boardV1.getBText());
            notice.setCreateAt(boardV1.getBWriteday());
            notice.setUseYn("Y");
            noticeList.add(notice);
        } );
        noticeRepository.saveAll(noticeList);
        noticeRepository.flush();
    }

    @Test
    @Transactional
    public void testMigrationFeedData(){
//        Feeds.migrationList(snsFeedMssqlRepository.findAll(), accountRepository.findAll(), tagsRepository.findAll());

//        List<SnsFeedFile> snsFeedFileList = snsFeedFileMssqlRepository.findAll();
//        List<SnsFeedTag> snsFeedTagList = snsFeedTagMssqlRepository.findAll();
//        List<SnsFeedAuser> snsFeedAuserList = snsFeedAuserMssqlRepository.findAll();

//        filesList = new ArrayList<>();
//        feedFilesList = new ArrayList<>();
//        snsFeedFileList.forEach(feedFile -> {
//            Files files = Files.getObjectFromString(feedFile.getFilename());
//            files.setIdx(feedFile.getIdx().intValue());
//            filesList.add(files);
//            FeedFiles feedFiles = new FeedFiles();
//            feedFiles.setFileIdx(feedFile.getIdx().intValue());
//            feedFiles.setFeedIdx(feedFile.getFidx().intValue());
//            feedFilesList.add(feedFiles);
//        } );
//        snsFeedList.forEach(snsFeed -> {
//            if( snsFeed!=null && !snsFeed.getStrFile().equals("")){
//                Files files = Files.getObjectFromString(snsFeed.getStrFile());
//                filesList.add(files);
////                FeedFiles feedFiles = new FeedFiles();
////                feedFiles.setFileIdx();
////                feedFiles.setFeedIdx(feedFile.getFidx().intValue());
////                feedFilesList.add(feedFiles);
//            }
//        });
//        filesRepository.saveAll(filesList);
//        filesRepository.flush();

//        feedFilesRepository.saveAll(feedFilesList);
//        feedFilesRepository.flush();
    }
}
