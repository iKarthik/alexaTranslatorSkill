import org.junit.Test;
import translator.storage.S3Helper;
import translator.textToSpeech.TextToSpeechConverterVoiceRSS;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Karthik on 3/20/16.
 */
public class SaveToS3Test {


    @Test
    public void testSaveToS3() throws Exception {
        byte[] byteArray = TextToSpeechConverterVoiceRSS.toSpeech("en-in", "How are you doing");
        assertNotNull(byteArray);
        String etag = S3Helper.saveToS3(new byte[0], "translateaudiofiles", System.currentTimeMillis() + ".mp3");
        System.out.println(etag);
    }


    @Test
    public void getFromS3() throws Exception{
        String lang = S3Helper.getLangFromS3();
        System.out.println(lang);
        S3Helper.saveLangToS3("Indian");
        System.out.println(S3Helper.getLangFromS3());
    }
}
