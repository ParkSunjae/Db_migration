package kr.co.kyobo.vora.service.tags;

import kr.co.kyobo.vora.model.api.ResponseEntityUtil;
import kr.co.kyobo.vora.model.api.SearchTagList;
import kr.co.kyobo.vora.model.database.TagFollow;
import kr.co.kyobo.vora.model.database.Tags;
import kr.co.kyobo.vora.model.vo.ReturnTags;
import kr.co.kyobo.vora.model.vo.SignUpTags;
import kr.co.kyobo.vora.repository.TagFollowRepository;
import kr.co.kyobo.vora.repository.TagsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class TagsServiceImpl implements TagsService {

    @Autowired
    private TagsRepository tagsRepository;

    @Autowired
    private TagFollowRepository tagFollowRepository;

    @Override
    public ResponseEntity<Object> getSuggestTags(SearchTagList searchTagList) {
        this.setPage(searchTagList);
        ReturnTags returnTags = new ReturnTags();

        List<SignUpTags> tagsList = this.tagsRepository.signUpSuggestTags(searchTagList);
        int totalCount = this.tagsRepository.signUpSuggestTagsTotal();

        returnTags.setPage(searchTagList.getPage());
        returnTags.setLimit(searchTagList.getLimit());
        returnTags.setTags(tagsList);
        returnTags.setTotalCount(totalCount);

        return ResponseEntityUtil.makeResultSuccess(returnTags);
    }

    @Override
    public ResponseEntity<Object> saveUsersTags(List<TagFollow> tagFollows) {


        this.tagFollowRepository.saveMulti(tagFollows);

        return ResponseEntityUtil.makeResultSuccess("");
    }

    private void setPage(SearchTagList searchTagList) {
        if(searchTagList.getPage() > 1){
            searchTagList.setOffset((searchTagList.getPage() - 1) * searchTagList.getLimit());
        }else{
            searchTagList.setOffset((searchTagList.getPage() - 1));
        }
    }
}
