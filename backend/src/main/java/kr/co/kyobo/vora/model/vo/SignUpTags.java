package kr.co.kyobo.vora.model.vo;

import kr.co.kyobo.vora.model.database.Tags;
import lombok.Data;

@Data
public class SignUpTags extends Tags {
    private Boolean selected = false;

}
