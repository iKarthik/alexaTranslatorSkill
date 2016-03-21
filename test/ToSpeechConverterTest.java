import org.junit.Test;
import translator.textToSpeech.TextToSpeechConverterGoogleAPI;
import translator.textToSpeech.TextToSpeechConverterVoiceRSS;
import translator.speechAssets.LanguageToCode;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Karthik on 3/20/16.
 */
public class ToSpeechConverterTest {

    @Test
    public void testTextToSpeechConverter() throws Exception{
        String code = LanguageToCode.getVoicRssLanguageCode("Chinese");
        byte[] byteArray = TextToSpeechConverterVoiceRSS.toSpeech(code, "How are you doing");
        assertNotNull(byteArray);
    }

    @Test
    public void testTextToSpeechUsingGoogle() throws Exception {
        String code = "en-us";
        byte[] byteArray = TextToSpeechConverterGoogleAPI.toSpeech(code, "hello");
        assertNotNull(byteArray);
    }
}
