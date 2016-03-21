import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.lambda.runtime.Client;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.*;
import java.net.URL;


/**
 * Created by Karthik on 3/19/16.
 */
public class VoiceRss {

    public static void main(String[] args) throws Exception {
        foo();
    }

    private static void foo() throws Exception {
        URL link = new URL("https://api.voicerss.org/?key=YOUR_API_KEY&src=hello&hl=en-in");

        //Code to download
        InputStream in = new BufferedInputStream(link.openStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n = 0;
        while (-1!=(n=in.read(buf)))
        {
            out.write(buf, 0, n);
        }

        InputStream isFromFirstData = new ByteArrayInputStream(out.toByteArray());


        AWSCredentials credentials = new BasicAWSCredentials("YOUR_ACCESS_KEY", "YOUR_SECRET_KEY");
        AmazonS3 s3client = new AmazonS3Client(credentials);
        String bucketName = "samplebooblibucket1";
        s3client.createBucket(bucketName);

        String fileName =  "testvideo.mp4";
        s3client.putObject(new PutObjectRequest(bucketName, fileName, isFromFirstData, new ObjectMetadata()));
        out.close();
        in.close();
        byte[] response = out.toByteArray();
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(response);
        fos.close();
    }
}
