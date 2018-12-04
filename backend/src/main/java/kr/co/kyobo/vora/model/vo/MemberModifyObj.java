package kr.co.kyobo.vora.model.vo;

import kr.co.kyobo.vora.model.database.Member;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MemberModifyObj extends Member {
    private MultipartFile uploadedFile;
    private boolean defaultImage;
}
