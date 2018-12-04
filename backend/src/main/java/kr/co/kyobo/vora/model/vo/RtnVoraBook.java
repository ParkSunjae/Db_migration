package kr.co.kyobo.vora.model.vo;

import kr.co.kyobo.vora.model.database.VoraBooks;
import lombok.Data;

@Data
public class RtnVoraBook extends VoraBooks {
    private int actionTagsIdx;
}
