package kr.co.kyobo.vora.service.file;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
public class AmazonClient {
    private AmazonS3 s3client;

    private static final String FILE_PATH_SEPARATOR = "/";

    @Value("${aws.endPoint.Url}")
    private String endpointUrl;
    @Value("${aws.bucket}")
    private String bucketName;
    @Value("${aws.accesskey}")
    private String accessKey;
    @Value("${aws.secretkey}")
    private String secretKey;
    @Value("${aws.region}")
    private String regionName;

    @Value("${tempImage.path}")
    private String tempPath;

    @PostConstruct
    private void initializeAmazon() {
        BasicAWSCredentials creds = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(creds)).withRegion(regionName).build();
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }
    private String generateThumbnailFileName(String fileName) {
        return new Date().getTime() + "-thm-" + fileName.replace(" ", "_");
    }

    private void uploadFileTos3bucket(String path, String fileName, File file) {
        s3client.putObject(new PutObjectRequest(bucketName + File.separator + path, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public String uploadFile(MultipartFile multipartFile, String secondPath) {
        String fileUrl = "";
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            fileUrl = endpointUrl + File.separator + bucketName + File.separator + secondPath + File.separator + fileName;
            uploadFileTos3bucket(secondPath, fileName, file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }
    public String uploadFileThumbnail(MultipartFile multipartFile, String secondPath) {
        String fileUrl = "";
        try {
            File file = makeThumbnailFile(multipartFile);
            String fileName = generateThumbnailFileName(multipartFile.getOriginalFilename());
            fileUrl = endpointUrl + File.separator + bucketName + File.separator + secondPath + File.separator + fileName;
            uploadFileTos3bucket(secondPath, fileName, file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }

    public File makeThumbnailFile(MultipartFile multipartFile) throws IOException {
        String filename = generateThumbnailFileName(multipartFile.getOriginalFilename());
        String[] split = filename.split("\\.");

        File original = convertMultiPartToFile(multipartFile);
        File thumbnail = new File(this.tempPath + File.separator + filename);
        if (original.exists()) {
            thumbnail.getParentFile().mkdirs();
            Thumbnails.of(original).scale(0.5).outputFormat(split[1]).toFile(thumbnail);
        }
        original.delete();
        return thumbnail;
    }

    public String deleteFileFromS3Bucket(String fileUrl, String secondPath) {
        String fileName = String.format(fileUrl.substring(fileUrl.lastIndexOf("/") + 1)) ;
        s3client.deleteObject(new DeleteObjectRequest(bucketName + File.separator + secondPath,  fileName));
        return "Successfully deleted";
    }

}
