package kr.co.kyobo.vora.model.database.mysql;

import kr.co.kyobo.vora.common.CommonUtils;
import kr.co.kyobo.vora.model.database.mssql.SnsFeedFile;
import kr.co.kyobo.vora.model.database.mssql.SnsFeedScrap;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table
public class FeedFiles extends BaseObject {
    private Integer feedIdx;
    @ManyToOne
    @JoinColumn(name = "fileIdx", referencedColumnName = "idx")
    private Files fileIdx;

    @Transient
    public static List<FeedFiles> migrationList(List<SnsFeedFile> snsFeedFileList, Integer feedIdx){
        List<FeedFiles> memberScrapFeedsList = new ArrayList<>();
        for (SnsFeedFile snsFeedScrap : snsFeedFileList) {
            FeedFiles feedFiles = new FeedFiles();
            feedFiles.setFeedIdx(feedIdx);
            feedFiles.setFileIdx(CommonUtils.getObjectFromString(snsFeedScrap.getFilename(),"feed"));
            memberScrapFeedsList.add(feedFiles);
        }
        return memberScrapFeedsList;
    }
}
