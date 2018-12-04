package kr.co.kyobo.vora.controller;

import kr.co.kyobo.vora.service.file.AmazonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/storage/")
public class FileController {

    @Value("{userImage.path}")
    private String profilePath;

    @Value("{postImage.path}")
    private String feedPath;

    @Autowired
    private AmazonClient amazonClient;

    @PostMapping("/uploadFileMember")
    public String uploadFileProfile(@RequestPart(value = "file") MultipartFile file) {
        return this.amazonClient.uploadFile(file, profilePath);
    }

    @PostMapping("/uploadFileFeed")
    public String uploadFileFeed(@RequestPart(value = "file") MultipartFile file) {
        return this.amazonClient.uploadFile(file, feedPath);
    }

    @DeleteMapping("/deleteFileMember")
    public String deleteFileProfile(@RequestPart(value = "url") String fileUrl) {
        return this.amazonClient.deleteFileFromS3Bucket(fileUrl, profilePath);
    }

    @DeleteMapping("/deleteFileFeed")
    public String deleteFileFeed(@RequestPart(value = "url") String fileUrl) {
        return this.amazonClient.deleteFileFromS3Bucket(fileUrl, feedPath);
    }
}
