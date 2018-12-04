package kr.co.kyobo.vora.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class HelpRequest {
    @JsonIgnore
    private int offset = 1;
    private int page = 1;
    private int total = 0;
    private int limit = 20;
    private String useYn = "Y";
    private List<HelpListObj> helpList;
}
