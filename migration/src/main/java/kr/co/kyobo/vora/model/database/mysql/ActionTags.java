package kr.co.kyobo.vora.model.database.mysql;

import kr.co.kyobo.vora.model.database.mssql.SnsFeedAction;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="action_tags")
public class ActionTags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Integer idx;
    private Integer feedIdx;
    private String actionTagType;

    @Transient
    public static ActionTags migration(SnsFeedAction snsFeedAction){
        ActionTags actionTags = new ActionTags();
        String buff = snsFeedAction.getAtype().substring(0,1);
        buff = buff == "b" || buff == "m" ? buff : "c";
        actionTags.setActionTagType(buff);
        return actionTags;
    }

    @Transient
    public static List<ActionTags> migrationList(List<SnsFeedAction> snsFeedActionList, Integer feedIdx){
        List<ActionTags> actionTagsList = new ArrayList<>();
        for (SnsFeedAction snsFeedAction : snsFeedActionList) {
            ActionTags actionTags = ActionTags.migration(snsFeedAction);
            actionTags.setFeedIdx(feedIdx);
            actionTagsList.add(actionTags);
        }
        return actionTagsList;
    }
}
