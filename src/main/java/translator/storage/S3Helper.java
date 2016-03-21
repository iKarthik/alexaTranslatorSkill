package translator.storage;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * Created by Karthik on 3/20/16.
 */
public class S3Helper {

    public static String saveToS3(byte[] byteArray, String bucketName, String fileName) throws Exception {
        System.out.println("Saving bytearray to S3");
        InputStream isFromFirstData = new ByteArrayInputStream(byteArray);
        AWSCredentials credentials = new BasicAWSCredentials("AKIAIED74ANCXMLNU4IA", "qLyhXIVxGskt0+npOfa7Fpb3nyUT3ql1vazzOYEY");
        AmazonS3 s3client = new AmazonS3Client(credentials);
        PutObjectResult result = s3client.putObject(new PutObjectRequest(bucketName, fileName, isFromFirstData, new ObjectMetadata()));
        System.out.println("Successfully saved to S3");
        return result.getETag();
    }

    public static String getLangFromS3() throws IOException {
        AWSCredentials credentials = new BasicAWSCredentials("AKIAIED74ANCXMLNU4IA", "qLyhXIVxGskt0+npOfa7Fpb3nyUT3ql1vazzOYEY");
        AmazonS3 s3client = new AmazonS3Client(credentials);
        S3Object object = s3client.getObject(new GetObjectRequest("translatelanguage", "language.txt"));
        StringWriter writer = new StringWriter();
        IOUtils.copy(object.getObjectContent(), writer);
        return writer.toString();
    }

    public static String saveLangToS3(String lang) throws Exception {
        InputStream isFromFirstData = new ByteArrayInputStream(lang.getBytes());
        AWSCredentials credentials = new BasicAWSCredentials("AKIAIED74ANCXMLNU4IA", "qLyhXIVxGskt0+npOfa7Fpb3nyUT3ql1vazzOYEY");
        AmazonS3 s3client = new AmazonS3Client(credentials);
        PutObjectResult result = s3client.putObject(new PutObjectRequest("translatelanguage", "language.txt", isFromFirstData, new ObjectMetadata()));
        System.out.println("Successfully saved to S3");
        return result.getETag();
    }
}
