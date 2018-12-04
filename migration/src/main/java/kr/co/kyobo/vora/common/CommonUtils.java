package kr.co.kyobo.vora.common;

import kr.co.kyobo.vora.model.database.mysql.Files;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommonUtils {

    @Value("${userImage.path}")
    private static String memberPath;
    @Value("${userImageThm.path}")
    private static String memberThumbnailPath;
    @Value("${postImage.path}")
    private static String feedPath;
    @Value("${postImageThm.path}")
    private static String feedThumbnailPath;
    @Value("${aws.endPoint.Url}")
    private static String endpointUrl;
    @Value("${aws.bucket}")
    private static String bucketName;

    public static Timestamp changeDateFormat(String str) {
        if(str.contains("/")){
            String[] cm = str.split("/");

            str = cm[2] + "-" + cm[0] + "-" + cm[1];
        }

        if(str.length() < 11){
            str = str + " 00:00:00";
        }

        String change = "";
        Boolean check = false;
        if (str.contains("오후") || str.contains("오전")) {
            if (str.contains("오후")) {
                check = true;
            }
            change = timeSpliiters(str, check);
        } else {
            change = str;
        }

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse(change, df);
        return Timestamp.valueOf(ldt);
    }

    public static String timeSpliiters(String str, Boolean checker) {
        String splitter[] = null;
        if (checker) {
            splitter = str.split(" 오후 ");
        } else {
            splitter = str.split(" 오전 ");
        }

        String times[] = splitter[1].split(":");

        int c = 12, rtn = 0;
        if (checker) {
            rtn = Integer.parseInt(times[0]) + c;
        } else {
            rtn = Integer.parseInt(times[0]);
        }

        String hour = (rtn == 24 ? "00" : rtn+"");

        if(hour.length() < 2){
            hour = "0"+hour;
        }


        String adder = hour + ":" + times[1] + ":" + times[2];

        return splitter[0] + " " + adder;

    }

    public static Files getObjectFromString(String fileName, String type){
        String[] strs = fileName.split(":");
        Files files = new Files();
        if(type.equals("feed")){
            files.setFileName(endpointUrl + File.separator + bucketName + File.separator + feedPath + strs[0]);
            files.setFileThumbnail(endpointUrl + File.separator + bucketName + File.separator + feedThumbnailPath + strs[0]);
        }else{
            files.setFileName(endpointUrl + File.separator + bucketName + File.separator + memberPath + strs[0]);
            files.setFileThumbnail(endpointUrl + File.separator + bucketName + File.separator + memberThumbnailPath + strs[0]);
        }
        files.setFileType(strs[2]);
        return files;
    }
}
