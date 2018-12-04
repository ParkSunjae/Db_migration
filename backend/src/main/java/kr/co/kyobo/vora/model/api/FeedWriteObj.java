package kr.co.kyobo.vora.model.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.kyobo.vora.model.database.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class FeedWriteObj extends Feeds {

    private FeedLocations feedLocations;
    private List<ActionTagsBook> saveBooks;
    private List<ActionTagsMovie> saveMovies;
    private List<ActionTagsCultureExhibition> saveCultureAndExhibition;
    @JsonIgnore
    private List<MultipartFile> multipartFiles;
    private List<Tags> tags;
    private List<FeedMemberTag> followers;




}
