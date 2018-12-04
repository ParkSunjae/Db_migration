package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class VoraBooks {
    private String kyoboId;
    private String imgPcUrl;
    private String imgMokUrl;
    private String lcatecory;
    private String mcatecory;
    private String scatecory;
    private String bookNm;
    private String author;
    private String contentDes;
    private String isbn;
    private String pubNm;
    private String pubYmd;
    private String kyoboPcUrl;
    private String kyoboMokUrl;
}
