package kr.co.kyobo.vora.service.memberAndAccount;

import kr.co.kyobo.vora.common.CommonUtils;
import kr.co.kyobo.vora.model.database.mssql.*;
import kr.co.kyobo.vora.model.database.mysql.*;
import kr.co.kyobo.vora.repository.mssql.*;
import kr.co.kyobo.vora.repository.mysql.*;
import kr.co.kyobo.vora.service.ip.IpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class MakeMemberAndAccountServiceImpl implements MakeMemberAndAccountService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MtbMember2MssqlRepository mtbMember2MssqlRepository;

    @Autowired
    private ChangeHistoryMssqlRepository changeHistoryMssqlRepository;

    @Autowired
    private LoginHistoryMssqlRepository loginHistoryMssqlRepository;

    @Autowired
    private MemberActivityRepository memberActivityRepository;

    @Autowired
    private MemberLoginHistoryRepository memberLoginHistoryRepository;

    @Autowired
    private IpTocityResporitoy ipTocityResporitoy;

    @Autowired
    private IpService ipService;

    @Autowired
    private MtbInquiryMssqlRepository mtbInquiryMssqlRepository;

    @Autowired
    private MemberInquiryRepository memberInquiryRepository;

    @Autowired
    private MemberTermsApplyRespository memberTermsApplyRespository;

    @Autowired
    private MemberAdminRecommendRepository memberAdminRecommendRepository;

    @Autowired
    private FilesRepository filesRepository;

    @Autowired
    private SnsFeedMssqlRepository snsFeedMssqlRepository;
    @Autowired
    private SnsFeedTagMssqlRepository snsFeedTagMssqlRepository;
    @Autowired
    private TagsRepository tagsRepository;
    @Autowired
    private SnsTagMssqlRepository snsTagMssqlRepository;
    @Autowired
    private FeedsRepository feedsRepository;

    private List<Tags> tagsList;

    @Override
    public void makeMemberAndAccount() throws IOException {

        //mssql 회원정보를 모두 가져온다
        Iterable<MtbMember2> findMtb = this.mtbMember2MssqlRepository.findAll();

        List<Account> accountList = new ArrayList<>();

        for (MtbMember2 mtbMember2 : findMtb) {

            //FILES

            Files savefiles = null;

            if (mtbMember2.getStrimg() != null && !mtbMember2.getStrimg().equals("")) {
                savefiles = (Files) CommonUtils.getObjectFromString(mtbMember2.getStrimg(), "");
                this.filesRepository.save(savefiles);
            }

            Member member = new Member();
            member.setNickName(mtbMember2.getStrName());
            member.setCreateAt(mtbMember2.getDtmInsertDate());
            member.setEmailCertYn("Y");
            member.setContent(mtbMember2.getContents());
            if (mtbMember2.getBusinessGroup().equals("")) {
                member.setGroupIdx(1);
            } else {
                member.setGroupIdx(Integer.parseInt(mtbMember2.getBusinessGroup()));
            }
            if (!mtbMember2.getBusinessGroupName().equals("")) {
                member.setBusinessYn("Y");
                member.setBusinessCategoryIdx(1);
                member.setBusinessSubCategoryIdx(1);
                member.setBusinessContent(mtbMember2.getBusinessContent());
                member.setBusinessName(mtbMember2.getBusinessGroupName());
                member.setBusinessEmail(mtbMember2.getBusinessEmail());
                member.setBusinessSdate(CommonUtils.changeDateFormat(mtbMember2.getBusinessSdate()));
                member.setBusinessEdate(CommonUtils.changeDateFormat(mtbMember2.getBusinessEdate()));
                member.setBusinessChangeDate(CommonUtils.changeDateFormat(mtbMember2.getBusinessChangeDate()));
                member.setBusinessSite(mtbMember2.getBusinessSite());
                member.setBusinessTel(mtbMember2.getBusinessTel());
            } else {
                member.setBusinessYn("N");
            }
            member.setYear(mtbMember2.getBirthYear());
            if (savefiles != null) {
                member.setFileIdx(savefiles.getIdx());
            } else {
                member.setFileIdx(0);
            }

            member.setUserStatus("ACTIVE");
            this.memberRepository.save(member);

            //Account 정보 세팅

            Account saveEmail = this.makeSocailData(member, mtbMember2, "email");
            this.accountRepository.save(saveEmail);
            accountList.add(saveEmail);

            if (mtbMember2.getFacebookToken() != null && !mtbMember2.getFacebookToken().equals("")) {
                Account facebook = this.makeSocailData(member, mtbMember2, "facebook");
                this.accountRepository.save(facebook);
            }

            if (mtbMember2.getGoogleToken() != null && !mtbMember2.getGoogleToken().equals("")) {
                Account google = this.makeSocailData(member, mtbMember2, "google");
                this.accountRepository.save(google);
            }

            if (mtbMember2.getKakaoToken() != null && !mtbMember2.getKakaoToken().equals("")) {
                Account kakao = this.makeSocailData(member, mtbMember2, "kakao");
                this.accountRepository.save(kakao);
            }

            if (mtbMember2.getNaverToken() != null && !mtbMember2.getNaverToken().equals("")) {
                Account naver = this.makeSocailData(member, mtbMember2, "naver");
                this.accountRepository.save(naver);
            }

            this.memberLoginAndChangeHistory(member, mtbMember2);

            Iterable<MtbInquiry> getInquery = this.mtbInquiryMssqlRepository.findByStrUserId(mtbMember2.getStrId());

            for (MtbInquiry mtbInquiry : getInquery) {
                MemberInquiry inquery = new MemberInquiry();

                inquery.setContent(mtbInquiry.getStrContent());
                inquery.setType(mtbInquiry.getStrCategory());
                inquery.setStatus(mtbInquiry.getStrStatus());
                inquery.setUIdx(member.getIdx());
                inquery.setReEmail(mtbInquiry.getStrUserId());
                inquery.setCreateAt(CommonUtils.changeDateFormat(mtbInquiry.getStrInsertDate()));

                this.memberInquiryRepository.save(inquery);
            }

            MemberTermsApply memberTermsApply = new MemberTermsApply();
            memberTermsApply.setCheckYn("Y");
            memberTermsApply.setTermIdx(1);
            memberTermsApply.setUIdx(member.getIdx());
            this.memberTermsApplyRespository.save(memberTermsApply);

            MemberTermsApply memberTermsApply2 = new MemberTermsApply();
            memberTermsApply2.setCheckYn("Y");
            memberTermsApply2.setTermIdx(2);
            memberTermsApply2.setUIdx(member.getIdx());
            this.memberTermsApplyRespository.save(memberTermsApply2);

            MemberAdminRecommend recommend = new MemberAdminRecommend();
            recommend.setUIdx(member.getIdx());
            recommend.setContents(mtbMember2.getAdminChunContent());
            if (!mtbMember2.getAdminChun().equals("")) {
                recommend.setCount(Integer.parseInt(mtbMember2.getAdminChun()));
            }
            if (mtbMember2.getAdminChunSdate() != null && !mtbMember2.getAdminChunSdate().equals("")) {
                recommend.setStartDate(CommonUtils.changeDateFormat(mtbMember2.getAdminChunSdate()));
            }
            if (mtbMember2.getAdminChunEdate() != null && !mtbMember2.getAdminChunEdate().equals("")) {
                recommend.setEndDate(CommonUtils.changeDateFormat(mtbMember2.getAdminChunEdate()));
            }

            this.memberAdminRecommendRepository.save(recommend);


        }
        this.memberRepository.flush();
        this.accountRepository.flush();

        //FEEDS
        //태그 마이그래이션
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
        //피드 마이그레이션
        List<Feeds> feedsList = Feeds.migrationList(this.snsFeedMssqlRepository.findAll(), accountList);
        this.feedsRepository.saveAll(feedsList);
        feedsList.forEach(feed -> feed.migrationSecondery(accountList, snsTagAllList) );
        this.feedsRepository.saveAll(feedsList);
        this.feedsRepository.flush();
    }

    @Override
    public void memberLoginAndChangeHistory(Member member, MtbMember2 mtbMember2) throws IOException {
        Iterable<ChangeHistory> findMembers = this.changeHistoryMssqlRepository.findByStrid(mtbMember2.getStrId());

        for (ChangeHistory changeHistory : findMembers) {
            MemberActivity memberActivity = new MemberActivity();
            memberActivity.setMemberIdx(member.getIdx());
            memberActivity.setActivityComment(changeHistory.getChange1());
            memberActivity.setActivityType("Change");
            memberActivity.setCreateAt(CommonUtils.changeDateFormat(changeHistory.getRegdate()));
            this.memberActivityRepository.save(memberActivity);
        }


        Iterable<LoginHistory> findMemberLogins = this.loginHistoryMssqlRepository.findByStrid(mtbMember2.getStrId());

        for (LoginHistory loginHistory : findMemberLogins) {
            MemberLoginHistory memberLoginHistory = new MemberLoginHistory();
            memberLoginHistory.setIp(loginHistory.getStrip());
            memberLoginHistory.setUIdx(member.getIdx());
            IpToCity finded = this.ipTocityResporitoy.findByIp(loginHistory.getStrip());
            if (finded != null) {
                memberLoginHistory.setLocation(finded.getCityName());
            } else {
                String cn = ipService.callCityNameRtn(loginHistory.getStrip());
                memberLoginHistory.setLocation(cn);
            }

            memberLoginHistory.setLoginTime(CommonUtils.changeDateFormat(loginHistory.getRegdate()));
            memberLoginHistory.setLogType("i");
            memberLoginHistory.setLoginType(changeLoginType(loginHistory.getStrdevice()));
            this.memberLoginHistoryRepository.save(memberLoginHistory);
        }
    }

    private String changeLoginType(String str) {
        String rtn = "";
        switch (str) {
            case "PC(웹)":
                rtn = "WEB";
                break;
            case "MOBILE(APP)":
                rtn = "MOBILE(AOS)";
                break;
        }
        return rtn;
    }


    private Account makeSocailData(Member member, MtbMember2 mtbMember2, String type) {
        Account savefaceBook = new Account();

        savefaceBook.setUserIdx(member.getIdx());
        savefaceBook.setAccountType(type);
        savefaceBook.setDeviceType("WEB");
        savefaceBook.setPushYn("Y");
        savefaceBook.setCreateAt(member.getCreateAt());
        savefaceBook.setUpdateAt(member.getUpdateAt());

        if (mtbMember2.getPushKey() != null && !mtbMember2.getPushKey().equals("")) {
            savefaceBook.setPushToken(mtbMember2.getPushKey());
        }

        if (type.equals("email")) {
            savefaceBook.setEmail(mtbMember2.getStrId());
            savefaceBook.setUserIdx(member.getIdx());
        }

        if (type.equals("facebook")) {
            savefaceBook.setSnsToken(mtbMember2.getFacebookToken());
        }

        if (type.equals("google")) {
            savefaceBook.setSnsToken(mtbMember2.getGoogleToken());
        }

        if (type.equals("naver")) {
            savefaceBook.setSnsToken(mtbMember2.getNaverToken());
        }

        if (type.equals("kakao")) {
            savefaceBook.setSnsToken(mtbMember2.getKakaoToken());
        }

        return savefaceBook;
    }


}
