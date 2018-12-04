package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class Files extends BaseObject{
    private String fileName;
    private String fileThumbnail;
    private String fileType;
}
