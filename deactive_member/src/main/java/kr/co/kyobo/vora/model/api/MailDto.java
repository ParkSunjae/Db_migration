package kr.co.kyobo.vora.model.api;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 메일 발송을 위한 DTO 객체
 */
@Data
public class MailDto {
    private String from = "vora.kyobo@gmail.com";
    private List<String> to = new ArrayList<>();
    private String subject;
    private String content;
    private String htmlBody;
}
