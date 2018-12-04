package kr.co.kyobo.vora.service.mail;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import kr.co.kyobo.vora.common.api.UriEntity;
import kr.co.kyobo.vora.common.api.UriMethod;
import kr.co.kyobo.vora.common.api.UriVersion;
import kr.co.kyobo.vora.common.encrypt.AES256Util;
import kr.co.kyobo.vora.model.api.MailDto;
import kr.co.kyobo.vora.model.database.Member;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

@Slf4j
@Service
public class SendMailServiceImpl implements SendMailService {

    static final private String CONFIGSET = "ConfigSet";
    static final String TEXTBODY = "This email was sent through Amazon SES using the AWS SDK for Java.";

    @Value("${aws.bucket}")
    String bucket;

    @Value("${mail.image.path}")
    String mail;

    @Value("${aws.ses.accesskey}")
    private String accessKey;

    @Value("${aws.ses.secretkey}")
    private String secretKey;

    @Value("${base.mail.cert.uri.backend}")
    private String backendUri;

    @Value("${base.mail.cert.uri.frontend}")
    private String frontendUri;

    @Override
    public Boolean send(MailDto mailDto) {
        try {
            BasicAWSCredentials creds = new BasicAWSCredentials(this.accessKey, this.secretKey);
            AmazonSimpleEmailService client =
                    AmazonSimpleEmailServiceClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(creds))
                            // Replace US_WEST_2 with the AWS Region you're using for
                            // Amazon SES.
                            .withRegion(Regions.US_WEST_2)
                            .build();
            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(new Destination().withToAddresses(mailDto.getTo()))
                    .withMessage(new Message().withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(mailDto.getHtmlBody()))
                                    .withText(new Content().withCharset("UTF-8").withData(TEXTBODY)))
                            .withSubject(new Content().withCharset("UTF-8").withData(mailDto.getSubject()))).withSource(mailDto.getFrom());
                    // Comment or remove the next line if you are not using a
                    // configuration set
//                    .withConfigurationSetName(CONFIGSET);
            client.sendEmail(request);
            log.info("Email sent!");
            return true;
        } catch (Exception ex) {
            log.info("The email was not sent. Error message: "+ ex.getMessage());
            return false;
        }
    }

    @Override
    public String titleVerify() {
        return "VORA와 함께하게 된 것을 환영해요!";
    }

    @Override
    public String titlePassword() {
        return "비밀번호를 변경해주세요";
    }

    @Override
    public String titleDeActive() {
        return "휴면회원 안내 메일입니다";
    }

    @Override
    public String titleInAvtice() {
        return "휴면상태 해지를 위해 본인 확인 메일을 보내드립니다.";
    }

    @Override
    public String contentVerification(Member member) throws GeneralSecurityException, UnsupportedEncodingException, EncoderException {

        int memberIdx = member.getIdx();
        String encriypt = this.makeSixTeenCharacters(memberIdx);
        URLCodec codec = new URLCodec();

        String mailDir = bucket + File.separator + mail;


        String certUrls = backendUri + UriVersion.API_VERSION_PUBLIC + UriEntity.MAIL + UriMethod.MAIL_VERIFICATION;
        return "\n" +
                "<!DOCTYPE html>\n" +
                "<html lang=\"ko\">\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no\" />\n" +
                "<title>교보문고</title>\n" +
                "<style>\n" +
                "\t@charset \"utf-8\";\n" +
                "\t@import url('https://fonts.googleapis.com/css?family=Noto+Sans');\n" +
                "</style>\n" +
                "</head>\n" +
                "<body style=\"margin: 0; padding: 0;\">\n" +
                "\t<div style=\"width: 700px; margin: 0 auto; background-color: #f2f2f4\" align=\"center\">\n" +
                "\t\t<div style=\"width: 550px; margin: 0 auto; padding: 190px 0; text-align: center;\">\n" +
                "\t\t\t<strong style=\"display: block; margin-bottom: 47px;\">\n" +
                "\t\t\t\t<img src=\"https://s3.ap-northeast-2.amazonaws.com/"+mailDir+"/login_logo.png\" alt=\"vora로고\" style=\"vertical-align: top; cursor: pointer\" />\n" +
                "\t\t\t</strong>\n" +
                "\t\t\t<p style=\"margin: 0; font-family: 'Noto Sans', 'sans-serif'; font-size: 21px; font-weight: 500; color: #333;\">VORA와 함께하게 된 것을 환영해요!</p>\n" +
                "\t\t\t<p style=\"line-height: 24px; margin: 20px 0 0 0; font-family: 'Noto Sans', 'sans-serif'; font-size: 16px; font-weight: normal; color: #333;\">안녕하세요 <span style=\"font-size: 18px; font-weight: 500;\">" + member.getNickName() + "</span>님<br>\n" +
                "\t\t\tVORA의 모든 서비스를 자유롭게 사용하기 위해<br>\n" +
                "\t\t\t아래 버튼을 클릭하여 이메일 인증을 진행해주세요</p>\n" +
                "\n" +
                "\t\t\t<form action=\""+certUrls+"\" method=\"post\">\n" +
                "\t\t\t<input type=\"hidden\" name=\"encrypt\" value=\""+codec.encode(encriypt)+"\">\n" +
                "\t\t\t<button type=\"submit\" style=\"position: relative; display: block; width: 400px; margin: 50px auto 0; text-decoration: none;background: none; border: 0; cursor: pointer;\">\n" +
                "\t\t\t\t<img src=\"https://s3.ap-northeast-2.amazonaws.com/"+mailDir+"/btn_mail01.png\" alt=\"VORA 시작하기\" style=\"border: 0; vertical-align: top;\" />\n" +
                "\t\t\t</button>\n" +
                "\t\t\t</form>\n" +
                "\t\t\t<div style=\"margin-top:57px;\">\n" +
                "\t\t\t\t<p style=\"margin: 0; font-family: 'Noto Sans', 'sans-serif'; font-size:12px; color:#c7c7c7;\">\n" +
                "\t\t\t\t\t<a href=\"#\" style=\"color: #c7c7c7; text-decoration:none; \">VORA소개</a> · \n" +
                "\t\t\t\t\t<a href=\"#\" style=\"color: #c7c7c7; text-decoration:none; \">개인정보처리방침</a> · \n" +
                "\t\t\t\t\t<a href=\"#\" style=\"color: #c7c7c7; text-decoration:none; \">이용약관</a>\n" +
                "\t\t\t\t</p>\n" +
                "\t\t\t\t<p style=\"margin-bottom:17px; font-family: 'Noto Sans', 'sans-serif'; font-size:12px; color:#c7c7c7;\">\n" +
                "\t\t\t\t\t<a href=\"#\" style=\"color: #c7c7c7; text-decoration:none; \">\n" +
                "\t\t\t\t\t\t<img src=\"https://s3.ap-northeast-2.amazonaws.com/"+mailDir+"/iphone_icon.png\" alt=\"\" style=\"margin-right: 5px; border: 0; vertical-align: top;\"/>\n" +
                "\t\t\t\t\t\tiPhone 앱\n" +
                "\t\t\t\t\t</a>\n" +
                "\t\t\t\t\t<a href=\"#\" style=\"padding-left:17px; text-decoration:none; color: #c7c7c7;\">\n" +
                "\t\t\t\t\t\t<img src=\"https://s3.ap-northeast-2.amazonaws.com/"+mailDir+"/android_icon.png\" alt=\"\" style=\"margin-right: 5px; border: 0; vertical-align: top;\"/>\n" +
                "\t\t\t\t\t\tAndroid 앱\n" +
                "\t\t\t\t\t</a>\n" +
                "\t\t\t\t</p>\n" +
                "\t\t\t\t<p style=\"font-family: 'Noto Sans', 'sans-serif'; font-size: 12px; color: #c7c7c7\">Copyright @ KYOBO BOOK CENTRE.</p>\n" +
                "\t\t\t</div>\n" +
                "\t\t</div>\n" +
                "\t</div>\n" +
                "</body>\n" +
                "</html>\n";
    }

    private String makeSixTeenCharacters(int idx) throws GeneralSecurityException, UnsupportedEncodingException {

        String original = idx + "";

        if(original.length() < 16){
            int cnt = 16 - original.length();
            for(int i=0; i<cnt; i++){
                original = original + " ";
            }
        }

        AES256Util aes256Util = new AES256Util("avansoft-dev-lab");
        String enc = aes256Util.encrypt(original);
        return enc;
    }

    @Override
    public String changePwdMail(Member member, String password) {
        String mailDir = bucket + File.separator + mail;
        return "\n" +
                "<!DOCTYPE html>\n" +
                "<html lang=\"ko\">\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no\" />\n" +
                "<title>교보문고</title>\n" +
                "<style>\n" +
                "\t@charset \"utf-8\";\n" +
                "\t@import url('https://fonts.googleapis.com/css?family=Noto+Sans');\n" +
                "</style>\n" +
                "</head>\n" +
                "<body style=\"margin: 0; padding: 0;\">\n" +
                "\t<div style=\"width: 700px; margin: 0 auto; background-color: #f2f2f4\" align=\"center\">\n" +
                "\t\t<div style=\"width: 550px; margin: 0 auto; padding: 190px 0; text-align: center;\">\n" +
                "\t\t\t<strong style=\"display: block; margin-bottom: 47px;\">\n" +
                "\t\t\t\t<img src=\"https://s3.ap-northeast-2.amazonaws.com/"+mailDir+"/login_logo.png\" alt=\"vora로고\" style=\"vertical-align: top; cursor: pointer\" />\n" +
                "\t\t\t</strong>\n" +
                "\t\t\t<p style=\"line-height: 24px; margin: 0 0 20px 0; font-family: 'Noto Sans', 'sans-serif'; font-size: 16px; font-weight: normal; color: #333;\">\n" +
                "\t\t\t\t안녕하세요 <span style=\"font-size: 18px; font-weight: 500;\">"+member.getNickName()+"</span>님<br>\n" +
                "\t\t\t\t아래 임시 비밀번호로 로그인 후 꼭 비밀번호를 변경해주세요.\n" +
                "\t\t\t</p>\n" +
                "\t\t\t<div style=\"width: 400px; height: 65px; line-height: 65px; margin: 0 auto; background-color: #e8e8e8; color: #96278f;\">\n" +
                "\t\t\t\t<p style=\"margin: 0; font-size: 18px;\">"+password+"</p>\n" +
                "\t\t\t</div>\n" +
                "\t\t\t<a href=\""+frontendUri+"\" style=\"position: relative; display: block; width: 400px; margin: 50px auto 0; text-decoration: none;\">\n" +
                "\t\t\t\t<img src=\"https://s3.ap-northeast-2.amazonaws.com/"+mailDir+"/btn_mail01.png\" alt=\"VORA 시작하기\" style=\"border: 0; vertical-align: top;\" />\n" +
                "\t\t\t</a>\n" +
                "\t\t\t<div style=\"margin-top:57px;\">\n" +
                "\t\t\t\t<p style=\"margin: 0; font-family: 'Noto Sans', 'sans-serif'; font-size:12px; color:#c7c7c7;\">\n" +
                "\t\t\t\t\t<a href=\"#\" style=\"color: #c7c7c7; text-decoration:none; \">VORA소개</a> · \n" +
                "\t\t\t\t\t<a href=\"#\" style=\"color: #c7c7c7; text-decoration:none; \">개인정보처리방침</a> · \n" +
                "\t\t\t\t\t<a href=\"#\" style=\"color: #c7c7c7; text-decoration:none; \">이용약관</a>\n" +
                "\t\t\t\t</p>\n" +
                "\t\t\t\t<p style=\"margin-bottom:17px; font-family: 'Noto Sans', 'sans-serif'; font-size:12px; color:#c7c7c7;\">\n" +
                "\t\t\t\t\t<a href=\"#\" style=\"color: #c7c7c7; text-decoration:none; \">\n" +
                "\t\t\t\t\t\t<img src=\"https://s3.ap-northeast-2.amazonaws.com/"+mailDir+"/iphone_icon.png\" alt=\"\" style=\"margin-right: 5px; border: 0; vertical-align: top;\"/>\n" +
                "\t\t\t\t\t\tiPhone 앱\n" +
                "\t\t\t\t\t</a>\n" +
                "\t\t\t\t\t<a href=\"#\" style=\"padding-left:17px; text-decoration:none; color: #c7c7c7;\">\n" +
                "\t\t\t\t\t\t<img src=\"https://s3.ap-northeast-2.amazonaws.com/"+mailDir+"/android_icon.png\" alt=\"\" style=\"margin-right: 5px; border: 0; vertical-align: top;\"/>\n" +
                "\t\t\t\t\t\tAndroid 앱\n" +
                "\t\t\t\t\t</a>\n" +
                "\t\t\t\t</p>\n" +
                "\t\t\t\t<p style=\"font-family: 'Noto Sans', 'sans-serif'; font-size: 12px; color: #c7c7c7\">Copyright @ KYOBO BOOK CENTRE.</p>\n" +
                "\t\t\t</div>\n" +
                "\t\t</div>\n" +
                "\t</div>\n" +
                "</body>\n" +
                "</html>\n";
    }

    @Override
    public String deActiveAccountMail(Member member) {
        String mailDir = bucket + File.separator + mail;
        return "<!DOCTYPE html>\n" +
                "<html lang=\"ko\">\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no\" />\n" +
                "<title>교보문고</title>\n" +
                "<style>\n" +
                "\t@charset \"utf-8\";\n" +
                "\t@import url('https://fonts.googleapis.com/css?family=Noto+Sans');\n" +
                "</style>\n" +
                "</head>\n" +
                "<body style=\"margin: 0; padding: 0;\">\n" +
                "\t<div style=\"width: 700px; margin: 0 auto; background-color: #f2f2f4\" align=\"center\">\n" +
                "\t\t<div style=\"width: 550px; margin: 0 auto; padding: 190px 0; text-align: center;\">\n" +
                "\t\t\t<strong style=\"display: block; margin-bottom: 47px;\">\n" +
                "\t\t\t\t<img src=\"https://s3.ap-northeast-2.amazonaws.com/"+mailDir+"/login_logo.png\" alt=\"vora로고\" style=\"vertical-align: top; cursor: pointer\" />\n" +
                "\t\t\t</strong>\n" +
                "\t\t\t<p style=\"line-height: 24px; margin: 0 0 20px 0; font-family: 'Noto Sans', 'sans-serif'; font-size: 21px; font-weight: normal; color: #333;\">\n" +
                "\t\t\t\t안녕하세요 <span style=\"font-size: 23px; font-weight: 500;\">"+member.getNickName()+"</span>님<br>\n" +
                "\t\t\t\t휴면회원 안내 메일입니다\n" +
                "\t\t\t</p>\n" +
                "\t\t\t<p style=\"line-height: 24px; font-family: 'Noto Sans', 'sans-serif'; font-size: 16px; font-weight: normal; color: #333; margin: 0 0 20px 0;\">\n" +
                "\t\t\t\tVORA 에서는 고객님의 개인정보 보호를 위하여<br/>\n" +
                "\t\t\t\t아래와 같이 휴면계정 대상 예정을 안내드립니다.<br/>\n" +
                "\t\t\t\t휴면회원 전환 예정일 전까지 VORA에 접속하셔서 로그인을 해주세요.<br/>\n" +
                "\t\t\t\t로그인을 하시면, 휴면상태가 되지 않습니다.\n" +
                "\t\t\t</p>\n" +
                "\t\t\t<table style=\"width: 100%; border: 1px solid #bbb; border-collapse: collapse; font-family: 'Noto Sans', 'sans-serif';\">\n" +
                "\t\t\t\t<thead></thead>\n" +
                "\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t<tr style=\"border-bottom: 1px solid #ddd;\">\n" +
                "\t\t\t\t\t\t<th style=\"font-size: 15px; color: #666; text-align: left; padding: 15px 20px; font-weight: normal; background-color: #e8e8e8;\">휴면회원(분리보관)대상</th>\n" +
                "\t\t\t\t\t\t<td style=\"padding: 5px; font-size: 15px; color: #666; text-align: center;\">YYYY-MM-DD 이후 로그인 이력이 없는 회원</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr style=\"border-bottom: 1px solid #ddd;\">\n" +
                "\t\t\t\t\t\t<th style=\"font-size: 15px; color: #666; text-align: left; padding: 15px 20px; font-weight: normal; background-color: #e8e8e8;\">휴면회원전환 예정일</th>\n" +
                "\t\t\t\t\t\t<td style=\"padding: 5px; font-size: 15px; color: #666; text-align: center;\">YYYY-MM-DD</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t<th style=\"font-size: 15px; color: #666; text-align: left; padding: 15px 20px; font-weight: normal; background-color: #e8e8e8;\">개인정보 보호 대상</th>\n" +
                "\t\t\t\t\t\t<td style=\"padding: 5px; font-size: 15px; color: #666; text-align: center;\">회원정보(이메일 주소, 연락처 등)</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t</tbody>\n" +
                "\t\t\t</table>\n" +
                "\t\t\t<div style=\"margin: 20px auto 0; text-align: left;\">\n" +
                "\t\t\t\t<p style=\"margin: 0 0 10px 0; font-size: 15px; font-weight: 600; color: #333;\">관련법령</p>\n" +
                "\t\t\t\t<p style=\"margin: 0 0 10px 0; font-size: 14px; font-weight: 600; color: #333;\">정보통신망 이용촉진 및 정보보호 등에 관한 법률 제29조(개인정보의 파기)</p>\n" +
                "\t\t\t\t<p style=\"margin: 0 0 10px 0; font-size: 14px; font-weight: normal; line-height: 1.5; color: #333;\">\n" +
                "\t\t\t\t\t정보통신서비스 제공자등은 정보통신서비스를 1년의 기간 동안 이용하지 아니하는 이용자의 개인정보를 보호하기 위하여 대통령령으로 정하는 바에 따라 개인정보의 파기 등 필요한 조치를 취하여야 한다.\n" +
                "\t\t\t\t</p>\n" +
                "\t\t\t\t<p style=\"margin: 0 0 10px 0; font-size: 14px; font-weight: 600; color: #333;\">정보통신망 이용촉진 및 정보보호 등에 관한 법률 시행령 제16조(개인정보의 파기 등)</p>\n" +
                "\t\t\t\t<p style=\"margin: 0 0 10px 0; font-size: 14px; font-weight: normal; line-height: 1.5; color: #333;\">\n" +
                "\t\t\t\t\t정보통신서비스 제공자등은 이용자가 정보통신서비스를 법 제29조 제2항의 기간 동안 이용하지 아니하는 경우에는 이용자의 개인정보를 해당 기간 경과 후 즉시 파기하거나 다른 이용자의 개인정보와 분리하여 별도로 저장·관리하여야 한다.\n" +
                "\t\t\t\t</p>\n" +
                "\t\t\t</div>\n" +
                "\t\t\t<a href=\""+frontendUri+"\" style=\"position: relative; display: block; width: 400px; margin: 50px auto 0; text-decoration: none;\">\n" +
                "\t\t\t\t<img src=\"https://s3.ap-northeast-2.amazonaws.com/"+mailDir+"/btn_mail01.png\" alt=\"VORA 시작하기\" style=\"border: 0; vertical-align: top;\" />\n" +
                "\t\t\t</a>\n" +
                "\t\t\t<div style=\"margin-top:57px;\">\n" +
                "\t\t\t\t<p style=\"margin: 0; font-family: 'Noto Sans', 'sans-serif'; font-size:12px; color:#c7c7c7;\">\n" +
                "\t\t\t\t\t<a href=\"#\" style=\"color: #c7c7c7; text-decoration:none; \">VORA소개</a> · \n" +
                "\t\t\t\t\t<a href=\"#\" style=\"color: #c7c7c7; text-decoration:none; \">개인정보처리방침</a> · \n" +
                "\t\t\t\t\t<a href=\"#\" style=\"color: #c7c7c7; text-decoration:none; \">이용약관</a>\n" +
                "\t\t\t\t</p>\n" +
                "\t\t\t\t<p style=\"margin-bottom:17px; font-family: 'Noto Sans', 'sans-serif'; font-size:12px; color:#c7c7c7;\">\n" +
                "\t\t\t\t\t<a href=\"#\" style=\"color: #c7c7c7; text-decoration:none; \">\n" +
                "\t\t\t\t\t\t<img src=\"https://s3.ap-northeast-2.amazonaws.com/"+mailDir+"/iphone_icon.png\" alt=\"\" style=\"margin-right: 5px; border: 0; vertical-align: top;\"/>\n" +
                "\t\t\t\t\t\tiPhone 앱\n" +
                "\t\t\t\t\t</a>\n" +
                "\t\t\t\t\t<a href=\"#\" style=\"padding-left:17px; text-decoration:none; color: #c7c7c7;\">\n" +
                "\t\t\t\t\t\t<img src=\"https://s3.ap-northeast-2.amazonaws.com/"+mailDir+"/android_icon.png\" alt=\"\" style=\"margin-right: 5px; border: 0; vertical-align: top;\"/>\n" +
                "\t\t\t\t\t\tAndroid 앱\n" +
                "\t\t\t\t\t</a>\n" +
                "\t\t\t\t</p>\n" +
                "\t\t\t\t<p style=\"font-family: 'Noto Sans', 'sans-serif'; font-size: 12px; color: #c7c7c7\">Copyright @ KYOBO BOOK CENTRE.</p>\n" +
                "\t\t\t</div>\n" +
                "\t\t</div>\n" +
                "\t</div>\n" +
                "</body>\n" +
                "</html>\n";
    }

    @Override
    public String inActiveAccountMail(Member member) {
        String mailDir = bucket + File.separator + mail;
        return "\n" +
                "<!DOCTYPE html>\n" +
                "<html lang=\"ko\">\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no\" />\n" +
                "<title>교보문고</title>\n" +
                "<style>\n" +
                "\t@charset \"utf-8\";\n" +
                "\t@import url('https://fonts.googleapis.com/css?family=Noto+Sans');\n" +
                "</style>\n" +
                "</head>\n" +
                "<body style=\"margin: 0; padding: 0;\">\n" +
                "\t<div style=\"width: 700px; margin: 0 auto; background-color: #f2f2f4\" align=\"center\">\n" +
                "\t\t<div style=\"width: 550px; margin: 0 auto; padding: 190px 0; text-align: center;\">\n" +
                "\t\t\t<strong style=\"display: block; margin-bottom: 47px;\">\n" +
                "\t\t\t\t<img src=\"https://s3.ap-northeast-2.amazonaws.com/"+mailDir+"/login_logo.png\" alt=\"vora로고\" style=\"vertical-align: top; cursor: pointer\" />\n" +
                "\t\t\t</strong>\n" +
                "\t\t\t<p style=\"line-height: 24px; margin: 0 0 20px 0; font-family: 'Noto Sans', 'sans-serif'; font-size: 21px; font-weight: normal; color: #333;\">\n" +
                "\t\t\t\t안녕하세요 <span style=\"font-size: 23px; font-weight: 500;\">"+member.getNickName()+"</span>님<br>\n" +
                "\t\t\t\t현재 휴면 고객입니다.\n" +
                "\t\t\t</p>\n" +
                "\t\t\t<p style=\"line-height: 24px; font-family: 'Noto Sans', 'sans-serif'; font-size: 16px; font-weight: normal; color: #333; margin: 0 0 20px 0;\">\n" +
                "\t\t\t\t휴면상태 해지를 위해 본인 확인 메일을 보내드립니다.<br/>\n" +
                "\t\t\t\t아래 버튼을 클릭 하여 휴면상태를 해지해주세요.\n" +
                "\t\t\t</p>\n" +
                "\n" +
                "\t\t\t<a href=\""+frontendUri+"\" style=\"position: relative; display: block; width: 400px; margin: 50px auto 0; text-decoration: none;\">\n" +
                "\t\t\t\t<img src=\"https://s3.ap-northeast-2.amazonaws.com/"+mailDir+"/btn_mail01.png\" alt=\"VORA 시작하기\" style=\"border: 0; vertical-align: top;\" />\n" +
                "\t\t\t</a>\n" +
                "\t\t\t<div style=\"margin-top: 57px;\">\n" +
                "\t\t\t\t<p style=\"margin: 0; font-family: 'Noto Sans', 'sans-serif'; font-size:12px; color:#c7c7c7;\">\n" +
                "\t\t\t\t\t<a href=\"#\" style=\"color: #c7c7c7; text-decoration:none; \">VORA소개</a> · \n" +
                "\t\t\t\t\t<a href=\"#\" style=\"color: #c7c7c7; text-decoration:none; \">개인정보처리방침</a> · \n" +
                "\t\t\t\t\t<a href=\"#\" style=\"color: #c7c7c7; text-decoration:none; \">이용약관</a>\n" +
                "\t\t\t\t</p>\n" +
                "\t\t\t\t<p style=\"margin-bottom:17px; font-family: 'Noto Sans', 'sans-serif'; font-size:12px; color:#c7c7c7;\">\n" +
                "\t\t\t\t\t<a href=\"#\" style=\"color: #c7c7c7; text-decoration:none; \">\n" +
                "\t\t\t\t\t\t<img src=\"https://s3.ap-northeast-2.amazonaws.com/"+mailDir+"/iphone_icon.png\" alt=\"\" style=\"margin-right: 5px; border: 0; vertical-align: top;\"/>\n" +
                "\t\t\t\t\t\tiPhone 앱\n" +
                "\t\t\t\t\t</a>\n" +
                "\t\t\t\t\t<a href=\"#\" style=\"padding-left:17px; text-decoration:none; color: #c7c7c7;\">\n" +
                "\t\t\t\t\t\t<img src=\"https://s3.ap-northeast-2.amazonaws.com/"+mailDir+"/android_icon.png\" alt=\"\" style=\"margin-right: 5px; border: 0; vertical-align: top;\"/>\n" +
                "\t\t\t\t\t\tAndroid 앱\n" +
                "\t\t\t\t\t</a>\n" +
                "\t\t\t\t</p>\n" +
                "\t\t\t\t<p style=\"font-family: 'Noto Sans', 'sans-serif'; font-size: 12px; color: #c7c7c7\">Copyright @ KYOBO BOOK CENTRE.</p>\n" +
                "\t\t\t</div>\n" +
                "\t\t</div>\n" +
                "\t</div>\n" +
                "</body>\n" +
                "</html>\n";
    }
}
